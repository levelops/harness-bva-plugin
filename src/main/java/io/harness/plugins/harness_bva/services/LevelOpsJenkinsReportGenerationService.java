package io.harness.plugins.harness_bva.services;

import io.harness.plugins.harness_bva.models.JenkinsGeneralConfig;
import io.harness.plugins.harness_bva.models.LevelOpsJenkinsReport;
import io.harness.plugins.harness_bva.models.Plugin;
import io.harness.plugins.harness_bva.models.User;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import static io.harness.plugins.harness_bva.models.LevelOpsJenkinsReport.getPassOrFail;

public class LevelOpsJenkinsReportGenerationService {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    private final JenkinsGeneralConfig jenkinsGeneralConfig;
    private final List<User> users;
    private final List<Plugin> plugins;

    public LevelOpsJenkinsReportGenerationService(JenkinsGeneralConfig jenkinsGeneralConfig, List<User> users, List<Plugin> plugins) {
        this.jenkinsGeneralConfig = jenkinsGeneralConfig;
        this.users = users;
        this.plugins = plugins;
    }

    public LevelOpsJenkinsReport generateReport(){
        LevelOpsJenkinsReport.LevelOpsJenkinsReportBuilder bldr = LevelOpsJenkinsReport.builder();
        if (jenkinsGeneralConfig != null) {
            if (jenkinsGeneralConfig.getLocatorConfig() != null) {
                bldr.jenkinsUrl(jenkinsGeneralConfig.getLocatorConfig().getJenkinsUrl())
                        .adminEmails(jenkinsGeneralConfig.getLocatorConfig().getAdminEmailAddress());
            }

            JenkinsGeneralConfig.SECURITY_REALM securityRealm = jenkinsGeneralConfig.getSecurityRealm();
            if (securityRealm != null) {
                bldr.securityRealm(securityRealm.getHumanReadableText())
                        .securityRealmPassFail(getPassOrFail(JenkinsGeneralConfig.SECURITY_REALM.isSecure(securityRealm)));
            }

            JenkinsGeneralConfig.AUTHORIZATION_TYPE authType = jenkinsGeneralConfig.getAuthorizationType();
            if (authType != null) {
                bldr.authorizationType(authType.getHumanReadableText())
                        .authorizationTypePassFail(getPassOrFail(JenkinsGeneralConfig.AUTHORIZATION_TYPE.isSecure(authType)));
            }

            if (jenkinsGeneralConfig.getCsrf() != null) {
                bldr.csrfPreventionEnabled(jenkinsGeneralConfig.getCsrf().isPreventCSRF())
                        .csrfPreventionPassFail(getPassOrFail(jenkinsGeneralConfig.getCsrf().isPreventCSRF()));
            }

            if (jenkinsGeneralConfig.getJnlpProtocols() != null) {
                bldr.jnlp1ProtocolEnabled(jenkinsGeneralConfig.getJnlpProtocols().isJnlp1ProtocolEnabled())
                        .jnlp1ProtocolPassFail(getPassOrFail(!jenkinsGeneralConfig.getJnlpProtocols().isJnlp1ProtocolEnabled()))
                        .jnlp2ProtocolEnabled(jenkinsGeneralConfig.getJnlpProtocols().isJnlp2ProtocolEnabled())
                        .jnlp2ProtocolPassFail(getPassOrFail(!jenkinsGeneralConfig.getJnlpProtocols().isJnlp2ProtocolEnabled()))
                        .jnlp3ProtocolEnabled(jenkinsGeneralConfig.getJnlpProtocols().isJnlp3ProtocolEnabled())
                        .jnlp3ProtocolPassFail(getPassOrFail(!jenkinsGeneralConfig.getJnlpProtocols().isJnlp3ProtocolEnabled()))
                        .jnlp4ProtocolEnabled(jenkinsGeneralConfig.getJnlpProtocols().isJnlp4ProtocolEnabled())
                        .jnlp4ProtocolPassFail(getPassOrFail(jenkinsGeneralConfig.getJnlpProtocols().isJnlp4ProtocolEnabled()));
            }
        }

        bldr.tlsEnabledForMasterSlaveCommunication(true)
                .tlsSetttingPassFail(getPassOrFail(true));

        Set<String> extensionNames = new HashSet<>();
        if (CollectionUtils.isNotEmpty(plugins)) {
            for (Plugin plugin : plugins) {
                if (StringUtils.isNotBlank(plugin.getExtensionName())) {
                    extensionNames.add(plugin.getExtensionName());
                }
            }
        }

        boolean pluginZapInstalled = extensionNames.contains("zap");
        bldr.pluginZapInstalled(pluginZapInstalled)
                .pluginZapPassFail(getPassOrFail(pluginZapInstalled));

        boolean pluginBrakemanInstalled = extensionNames.contains("brakeman");
        bldr.pluginBrakemanInstalled(pluginBrakemanInstalled)
                .pluginBrakemanPassFail(getPassOrFail(pluginBrakemanInstalled));

        boolean pluginAuditTrailInstalled = extensionNames.contains("audit-trail");
        bldr.pluginAuditTrailInstalled(pluginAuditTrailInstalled)
                .pluginAuditTrailPassFail(getPassOrFail(pluginAuditTrailInstalled));

        LevelOpsJenkinsReport report = bldr.build();
        return report;
    }

    //region Builder
    public static LevelOpsJenkinsReportGenerationServiceBuilder builder(){
        return new LevelOpsJenkinsReportGenerationServiceBuilder();
    }
    public static final class LevelOpsJenkinsReportGenerationServiceBuilder {
        private JenkinsGeneralConfig jenkinsGeneralConfig;
        private List<User> users;
        private List<Plugin> plugins;

        public JenkinsGeneralConfig getJenkinsGeneralConfig() {
            return jenkinsGeneralConfig;
        }

        public LevelOpsJenkinsReportGenerationServiceBuilder jenkinsGeneralConfig(JenkinsGeneralConfig jenkinsGeneralConfig) {
            this.jenkinsGeneralConfig = jenkinsGeneralConfig;
            return this;
        }

        public List<User> getUsers() {
            return users;
        }

        public LevelOpsJenkinsReportGenerationServiceBuilder users(List<User> users) {
            this.users = users;
            return this;
        }

        public List<Plugin> getPlugins() {
            return plugins;
        }

        public LevelOpsJenkinsReportGenerationServiceBuilder plugins(List<Plugin> plugins) {
            this.plugins = plugins;
            return this;
        }

        public LevelOpsJenkinsReportGenerationService build(){
            return new LevelOpsJenkinsReportGenerationService(jenkinsGeneralConfig, users, plugins);
        }
    }
    //endregion
}