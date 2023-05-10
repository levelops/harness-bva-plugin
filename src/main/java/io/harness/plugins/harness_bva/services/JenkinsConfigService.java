package io.harness.plugins.harness_bva.services;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.common.base.Preconditions;
import io.harness.plugins.harness_bva.models.JenkinsConfig;
import io.harness.plugins.harness_bva.models.JenkinsGeneralConfig;
import io.harness.plugins.harness_bva.models.JenkinsLocationConfig;
import io.harness.plugins.harness_bva.utils.XmlUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.charset.StandardCharsets.UTF_8;

public class JenkinsConfigService {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    public static JenkinsConfigService getInstance(){
        return new JenkinsConfigService();
    }

    public JenkinsGeneralConfig readConfig(final Path jenkinsConfigFile, final Path jenkinsSlaveToMasterKillSwitch, final Path jenkinsLocationConfigFile){
        Preconditions.checkNotNull(jenkinsConfigFile, "Input Jenkins Config file cannot be null");
        Preconditions.checkNotNull(jenkinsSlaveToMasterKillSwitch, "Input Jenkins Slave to Master Kill Switch file cannot be null");
        Preconditions.checkNotNull(jenkinsLocationConfigFile, "Input Jenkins Location Config file cannot be null");
        XmlMapper xmlMapper = XmlUtils.getObjectMapper();
        try {
            JenkinsConfig jenkinsConfig = xmlMapper.readValue(jenkinsConfigFile.toFile(), JenkinsConfig.class);

            JenkinsGeneralConfig.SECURITY_REALM securityRealm = null;
            if ((jenkinsConfig != null) && (jenkinsConfig.getSecurityRealm() != null)){
                securityRealm = JenkinsGeneralConfig.SECURITY_REALM.parseRealm(jenkinsConfig.getSecurityRealm().getData());
            }
            JenkinsGeneralConfig.AUTHORIZATION_TYPE authorizationType = null;
            if ((jenkinsConfig != null) && (jenkinsConfig.getAuthorizationStrategy() != null)){
                authorizationType = JenkinsGeneralConfig.AUTHORIZATION_TYPE.parseAuthorization(jenkinsConfig.getAuthorizationStrategy().getData());
            }

            JenkinsGeneralConfig.CSRF csrf = parseCSRF(jenkinsConfig);
            JenkinsGeneralConfig.SlaveAgentPort slaveAgentPort = parseSlaveAgentPort(jenkinsConfig);
            JenkinsGeneralConfig.JNLPProtocols jnlpProtocols = parseJNLPProtocols(jenkinsConfig);
            boolean isAgentToMasterAccessControlEnabled = checkIfAgentToMasterAccessControlEnabled(jenkinsSlaveToMasterKillSwitch);
            JenkinsGeneralConfig.JenkinsLocator jenkinsLocator = parseJenkinsLocatorConfig(jenkinsLocationConfigFile, xmlMapper);

            JenkinsGeneralConfig jenkinsGeneralConfig = JenkinsGeneralConfig.builder()
                    .securityRealm(securityRealm)
                    .authorizationType(authorizationType)
                    .csrf(csrf)
                    .slaveAgentPort(slaveAgentPort)
                    .jnlpProtocols(jnlpProtocols)
                    .agentToMasterAccessControlEnabled(isAgentToMasterAccessControlEnabled)
                    .locatorConfig(jenkinsLocator)
                    .build();

            return jenkinsGeneralConfig;
        } catch (IOException e) {
            LOGGER.log(Level.WARNING,"IO Exceptions!!", e);
            return null;
        }
    }

    private JenkinsGeneralConfig.CSRF parseCSRF(JenkinsConfig jenkinsConfig){
        if(jenkinsConfig == null){
            return null;
        }
        JenkinsGeneralConfig.CSRF.CSRFBuilder csrfBuilder = JenkinsGeneralConfig.CSRF.builder();
        if(jenkinsConfig.getCrumbIssuer() != null){
            csrfBuilder.preventCSRF(true).crumbIssuer(jenkinsConfig.getCrumbIssuer().getData());
            if(jenkinsConfig.getCrumbIssuer().getExcludeClientIPFromCrumb() != null){
                csrfBuilder.excludeClientIPFromCrumb(jenkinsConfig.getCrumbIssuer().getExcludeClientIPFromCrumb());
            }
        } else {
            csrfBuilder.preventCSRF(false);
        }
        JenkinsGeneralConfig.CSRF csrf = csrfBuilder.build();
        LOGGER.log(Level.FINEST, "csrf = {0}", csrf);
        return csrf;
    }

    private JenkinsGeneralConfig.SlaveAgentPort parseSlaveAgentPort(JenkinsConfig jenkinsConfig){
        if(jenkinsConfig == null){
            return null;
        }
        Integer rawPortNumber = jenkinsConfig.getSlaveAgentPort();
        JenkinsGeneralConfig.PORT_TYPE portType = JenkinsGeneralConfig.PORT_TYPE.parsePort(jenkinsConfig.getSlaveAgentPort());
        Integer sanitizedPortNumber = (portType == JenkinsGeneralConfig.PORT_TYPE.FIXED) ? rawPortNumber : null;
        JenkinsGeneralConfig.SlaveAgentPort slaveAgentPort = JenkinsGeneralConfig.SlaveAgentPort.builder()
                .portNumber(sanitizedPortNumber)
                .portType(portType).build();
        LOGGER.log(Level.FINEST, "slaveAgentPort = {0}", slaveAgentPort);
        return slaveAgentPort;
    }

    private JenkinsGeneralConfig.JNLPProtocols parseJNLPProtocols(JenkinsConfig jenkinsConfig){
        if(jenkinsConfig == null){
            return null;
        }
        JenkinsGeneralConfig.JNLPProtocols.JNLPProtocolsBuilder jnlpProtocolsBuilder = JenkinsGeneralConfig.JNLPProtocols.builder();
        parseEnabledProtocols(jnlpProtocolsBuilder, jenkinsConfig);
        parseDisabledProtocols(jnlpProtocolsBuilder, jenkinsConfig);
        JenkinsGeneralConfig.JNLPProtocols jnlpProtocols = jnlpProtocolsBuilder.build();
        LOGGER.log(Level.FINEST, "jnlpProtocols = {0}", jnlpProtocols);
        return jnlpProtocols;
    }

    private JenkinsGeneralConfig.JenkinsLocator parseJenkinsLocatorConfig (final Path jenkinsLocationConfigFilePath, XmlMapper xmlMapper){
        File jenkinsLocationConfigFile = jenkinsLocationConfigFilePath.toFile();
        if(! jenkinsLocationConfigFile.exists()){
            return null;
        }
        JenkinsLocationConfig jenkinsLocationConfig = null;
        try {
            jenkinsLocationConfig = xmlMapper.readValue(jenkinsLocationConfigFile, JenkinsLocationConfig.class);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error parsing Jenkins Location Config!", e);
            return null;
        }
        if (jenkinsLocationConfig == null){
            return null;
        }
        JenkinsGeneralConfig.JenkinsLocator.JenkinsLocatorBuilder locatorBuilder = JenkinsGeneralConfig.JenkinsLocator.builder();
        if (StringUtils.isNotBlank(jenkinsLocationConfig.getAdminAddress())) {
            if (!JenkinsLocationConfig.ADDRESS_NOT_CONFIGURED.matcher(jenkinsLocationConfig.getAdminAddress()).matches()) {
                locatorBuilder.adminEmailAddress(jenkinsLocationConfig.getAdminAddress());
            }
        }
        locatorBuilder.jenkinsUrl(jenkinsLocationConfig.getJenkinsUrl());
        JenkinsGeneralConfig.JenkinsLocator jenkinsLocator = locatorBuilder.build();
        LOGGER.log(Level.FINEST, "jenkinsLocator = {0}", jenkinsLocator);
        return jenkinsLocator;
    }

    private boolean checkIfAgentToMasterAccessControlEnabled(final Path jenkinsSlaveToMasterKillSwitch){
        if(!Files.exists(jenkinsSlaveToMasterKillSwitch)){
            return true;
        }
        String killSwitchValue = null;

        try {
            killSwitchValue = new String(Files.readAllBytes(jenkinsSlaveToMasterKillSwitch), UTF_8);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error reading the Slave To Master Kill Switch file at = " + jenkinsSlaveToMasterKillSwitch.toString(), e);
            return true;
        }
        if (StringUtils.isBlank(killSwitchValue)){
            return true;
        }
        killSwitchValue = killSwitchValue.trim();
        Boolean isKillSwitchEnabled = Boolean.parseBoolean(killSwitchValue);
        return !isKillSwitchEnabled;
    }

    private void parseEnabledProtocols(JenkinsGeneralConfig.JNLPProtocols.JNLPProtocolsBuilder jnlpProtocolsBuilder, final JenkinsConfig jenkinsConfig){
        if(jenkinsConfig.getEnabledAgentProtocols() == null){
            return;
        }
        parseEnabledDisabledProtocols(jnlpProtocolsBuilder, jenkinsConfig.getEnabledAgentProtocols().getString(), true);
    }

    private void parseDisabledProtocols(JenkinsGeneralConfig.JNLPProtocols.JNLPProtocolsBuilder jnlpProtocolsBuilder, final JenkinsConfig jenkinsConfig){
        if(jenkinsConfig.getDisabledAgentProtocols() == null){
            return;
        }
        parseEnabledDisabledProtocols(jnlpProtocolsBuilder, jenkinsConfig.getDisabledAgentProtocols().getString(), false);
    }

    private void parseEnabledDisabledProtocols(JenkinsGeneralConfig.JNLPProtocols.JNLPProtocolsBuilder jnlpProtocolsBuilder, final List<String> protocols, boolean isEnabled){
        if(CollectionUtils.isEmpty(protocols)){
            return;
        }
        for(String currentProtocol : protocols){
            if (JenkinsConfig.JNLP_1_PROTOCOL.equals(currentProtocol)){
                jnlpProtocolsBuilder.jnlp1ProtocolEnabled(isEnabled);
            } else if (JenkinsConfig.JNLP_2_PROTOCOL.equals(currentProtocol)){
                jnlpProtocolsBuilder.jnlp2ProtocolEnabled(isEnabled);
            } else if (JenkinsConfig.JNLP_3_PROTOCOL.equals(currentProtocol)){
                jnlpProtocolsBuilder.jnlp3ProtocolEnabled(isEnabled);
            }else if (JenkinsConfig.JNLP_4_PROTOCOL.equals(currentProtocol)){
                jnlpProtocolsBuilder.jnlp4ProtocolEnabled(isEnabled);
            }
        }
    }

}