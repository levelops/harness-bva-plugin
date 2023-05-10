package io.harness.plugins.harness_bva.services;

import io.harness.plugins.harness_bva.models.JenkinsGeneralConfig;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

public class JenkinsConfigServiceTest {
    Path slaveToMasterSecurityKillSwitchPath = new File(getClass().getClassLoader().getResource("configs/agent-to-master-access-control/slave-to-master-security-kill-switch-disabled").toURI()).toPath();
    Path locationConfigurationPath = new File(getClass().getClassLoader().getResource("configs/location-configuration/jenkins.model.JenkinsLocationConfiguration-local.xml").toURI()).toPath();

    public JenkinsConfigServiceTest() throws URISyntaxException {
    }

    @Test
    public void testSecurityRealm1() throws URISyntaxException {
        URL url = this.getClass().getClassLoader().getResource("configs/security-realm/jenkins-config-1.xml");
        File configFile = new File(url.toURI());
        JenkinsConfigService service = new JenkinsConfigService();
        JenkinsGeneralConfig config = service.readConfig(configFile.toPath(), slaveToMasterSecurityKillSwitchPath, locationConfigurationPath);
        Assert.assertNotNull(config);
        Assert.assertEquals(config.getSecurityRealm(), JenkinsGeneralConfig.SECURITY_REALM.ACTIVE_DIRECTORY);
        Assert.assertEquals(config.getAuthorizationType(), JenkinsGeneralConfig.AUTHORIZATION_TYPE.ROLE_BASED_STRATEGY);
    }

    @Test
    public void testSecurityRealm2() throws URISyntaxException {
        URL url = this.getClass().getClassLoader().getResource("configs/security-realm/jenkins-config-2.xml");
        File configFile = new File(url.toURI());
        JenkinsConfigService service = new JenkinsConfigService();
        JenkinsGeneralConfig config = service.readConfig(configFile.toPath(), slaveToMasterSecurityKillSwitchPath, locationConfigurationPath);
        Assert.assertNotNull(config);
        Assert.assertEquals(config.getSecurityRealm(), JenkinsGeneralConfig.SECURITY_REALM.DELEGATE_TO_SERVLET_CONTAINER);
        Assert.assertEquals(config.getAuthorizationType(), JenkinsGeneralConfig.AUTHORIZATION_TYPE.LOGGED_IN_USERS_CAN_DO_ANYTHING);
    }

    @Test
    public void testSecurityRealm3() throws URISyntaxException {
        URL url = this.getClass().getClassLoader().getResource("configs/security-realm/jenkins-config-3.xml");
        File configFile = new File(url.toURI());
        JenkinsConfigService service = new JenkinsConfigService();
        JenkinsGeneralConfig config = service.readConfig(configFile.toPath(), slaveToMasterSecurityKillSwitchPath, locationConfigurationPath);
        Assert.assertNotNull(config);
        Assert.assertEquals(config.getSecurityRealm(), JenkinsGeneralConfig.SECURITY_REALM.JENKINS_OWN_DATABASE);
        Assert.assertEquals(config.getAuthorizationType(), JenkinsGeneralConfig.AUTHORIZATION_TYPE.LOGGED_IN_USERS_CAN_DO_ANYTHING);
    }

    @Test
    public void testSecurityRealm4() throws URISyntaxException {
        URL url = this.getClass().getClassLoader().getResource("configs/security-realm/jenkins-config-4.xml");
        File configFile = new File(url.toURI());
        JenkinsConfigService service = new JenkinsConfigService();
        JenkinsGeneralConfig config = service.readConfig(configFile.toPath(), slaveToMasterSecurityKillSwitchPath, locationConfigurationPath);
        Assert.assertNotNull(config);
        Assert.assertEquals(config.getSecurityRealm(), JenkinsGeneralConfig.SECURITY_REALM.LDAP);
        Assert.assertEquals(config.getAuthorizationType(), JenkinsGeneralConfig.AUTHORIZATION_TYPE.ROLE_BASED_STRATEGY);
    }

    @Test
    public void testSecurityRealm5() throws URISyntaxException {
        URL url = this.getClass().getClassLoader().getResource("configs/security-realm/jenkins-config-5.xml");
        File configFile = new File(url.toURI());
        JenkinsConfigService service = new JenkinsConfigService();
        JenkinsGeneralConfig config = service.readConfig(configFile.toPath(), slaveToMasterSecurityKillSwitchPath, locationConfigurationPath);
        Assert.assertNotNull(config);
        Assert.assertEquals(config.getSecurityRealm(), JenkinsGeneralConfig.SECURITY_REALM.UNIX_USER_GROUP_DATABASE);
        Assert.assertEquals(config.getAuthorizationType(), JenkinsGeneralConfig.AUTHORIZATION_TYPE.ROLE_BASED_STRATEGY);
    }

    @Test
    public void testAuthorization1() throws URISyntaxException {
        URL url = this.getClass().getClassLoader().getResource("configs/authorization/jenkins-config-1.xml");
        File configFile = new File(url.toURI());
        JenkinsConfigService service = new JenkinsConfigService();
        JenkinsGeneralConfig config = service.readConfig(configFile.toPath(), slaveToMasterSecurityKillSwitchPath, locationConfigurationPath);
        Assert.assertNotNull(config);
        Assert.assertEquals(config.getSecurityRealm(), JenkinsGeneralConfig.SECURITY_REALM.JENKINS_OWN_DATABASE);
        Assert.assertEquals(config.getAuthorizationType(), JenkinsGeneralConfig.AUTHORIZATION_TYPE.ANYONE_CAN_DO_ANYTHING);
    }

    @Test
    public void testAuthorization2() throws URISyntaxException {
        URL url = this.getClass().getClassLoader().getResource("configs/authorization/jenkins-config-2.xml");
        File configFile = new File(url.toURI());
        JenkinsConfigService service = new JenkinsConfigService();
        JenkinsGeneralConfig config = service.readConfig(configFile.toPath(), slaveToMasterSecurityKillSwitchPath, locationConfigurationPath);
        Assert.assertNotNull(config);
        Assert.assertEquals(config.getSecurityRealm(), JenkinsGeneralConfig.SECURITY_REALM.JENKINS_OWN_DATABASE);
        Assert.assertEquals(config.getAuthorizationType(), JenkinsGeneralConfig.AUTHORIZATION_TYPE.LEGACY_MODE);
    }

    @Test
    public void testAuthorization3() throws URISyntaxException {
        URL url = this.getClass().getClassLoader().getResource("configs/authorization/jenkins-config-3.xml");
        File configFile = new File(url.toURI());
        JenkinsConfigService service = new JenkinsConfigService();
        JenkinsGeneralConfig config = service.readConfig(configFile.toPath(), slaveToMasterSecurityKillSwitchPath, locationConfigurationPath);
        Assert.assertNotNull(config);
        Assert.assertEquals(config.getSecurityRealm(), JenkinsGeneralConfig.SECURITY_REALM.JENKINS_OWN_DATABASE);
        Assert.assertEquals(config.getAuthorizationType(), JenkinsGeneralConfig.AUTHORIZATION_TYPE.LOGGED_IN_USERS_CAN_DO_ANYTHING);
    }

    @Test
    public void testAuthorization4() throws URISyntaxException {
        URL url = this.getClass().getClassLoader().getResource("configs/authorization/jenkins-config-4.xml");
        File configFile = new File(url.toURI());
        JenkinsConfigService service = new JenkinsConfigService();
        JenkinsGeneralConfig config = service.readConfig(configFile.toPath(), slaveToMasterSecurityKillSwitchPath, locationConfigurationPath);
        Assert.assertNotNull(config);
        Assert.assertEquals(config.getSecurityRealm(), JenkinsGeneralConfig.SECURITY_REALM.JENKINS_OWN_DATABASE);
        Assert.assertEquals(config.getAuthorizationType(), JenkinsGeneralConfig.AUTHORIZATION_TYPE.MATRIX_BASED_SECURITY);
    }

    @Test
    public void testAuthorization5() throws URISyntaxException {
        URL url = this.getClass().getClassLoader().getResource("configs/authorization/jenkins-config-5.xml");
        File configFile = new File(url.toURI());
        JenkinsConfigService service = new JenkinsConfigService();
        JenkinsGeneralConfig config = service.readConfig(configFile.toPath(), slaveToMasterSecurityKillSwitchPath, locationConfigurationPath);
        Assert.assertNotNull(config);
        Assert.assertEquals(config.getSecurityRealm(), JenkinsGeneralConfig.SECURITY_REALM.JENKINS_OWN_DATABASE);
        Assert.assertEquals(config.getAuthorizationType(), JenkinsGeneralConfig.AUTHORIZATION_TYPE.PROJECT_BASED_MATRIX_AUTHORIZATION_STRATEGY);
    }

    @Test
    public void testAuthorization6() throws URISyntaxException {
        URL url = this.getClass().getClassLoader().getResource("configs/authorization/jenkins-config-6.xml");
        File configFile = new File(url.toURI());
        JenkinsConfigService service = new JenkinsConfigService();
        JenkinsGeneralConfig config = service.readConfig(configFile.toPath(), slaveToMasterSecurityKillSwitchPath, locationConfigurationPath);
        Assert.assertNotNull(config);
        Assert.assertEquals(config.getSecurityRealm(), JenkinsGeneralConfig.SECURITY_REALM.UNIX_USER_GROUP_DATABASE);
        Assert.assertEquals(config.getAuthorizationType(), JenkinsGeneralConfig.AUTHORIZATION_TYPE.ROLE_BASED_STRATEGY);
    }

    @Test
    public void testCSRFEnabled() throws URISyntaxException {
        URL url = this.getClass().getClassLoader().getResource("configs/csrf/jenkins-config-csrf-enabled.xml");
        File configFile = new File(url.toURI());
        JenkinsConfigService service = new JenkinsConfigService();
        JenkinsGeneralConfig config = service.readConfig(configFile.toPath(), slaveToMasterSecurityKillSwitchPath, locationConfigurationPath);
        Assert.assertNotNull(config);
        Assert.assertEquals(config.getSecurityRealm(), JenkinsGeneralConfig.SECURITY_REALM.JENKINS_OWN_DATABASE);
        Assert.assertEquals(config.getAuthorizationType(), JenkinsGeneralConfig.AUTHORIZATION_TYPE.LOGGED_IN_USERS_CAN_DO_ANYTHING);
        Assert.assertEquals(config.getCsrf().isPreventCSRF(), true);
        Assert.assertEquals(config.getCsrf().getCrumbIssuer(), "hudson.security.csrf.DefaultCrumbIssuer");
        Assert.assertEquals(config.getCsrf().getExcludeClientIPFromCrumb(), true);
    }

    @Test
    public void testCSRFDisabled() throws URISyntaxException {
        URL url = this.getClass().getClassLoader().getResource("configs/csrf/jenkins-config-csrf-disabled.xml");
        File configFile = new File(url.toURI());
        JenkinsConfigService service = new JenkinsConfigService();
        JenkinsGeneralConfig config = service.readConfig(configFile.toPath(), slaveToMasterSecurityKillSwitchPath, locationConfigurationPath);
        Assert.assertNotNull(config);
        Assert.assertEquals(config.getSecurityRealm(), JenkinsGeneralConfig.SECURITY_REALM.JENKINS_OWN_DATABASE);
        Assert.assertEquals(config.getAuthorizationType(), JenkinsGeneralConfig.AUTHORIZATION_TYPE.LOGGED_IN_USERS_CAN_DO_ANYTHING);
        Assert.assertEquals(config.getCsrf().isPreventCSRF(), false);
        Assert.assertEquals(config.getCsrf().getCrumbIssuer(), null);
        Assert.assertEquals(config.getCsrf().getExcludeClientIPFromCrumb(), null);
    }

    @Test
    public void testSlaveAgentPortFixed() throws URISyntaxException {
        URL url = this.getClass().getClassLoader().getResource("configs/slave-agent-ports/jenkins-config-slave-agent-port-fixed.xml");
        File configFile = new File(url.toURI());
        JenkinsConfigService service = new JenkinsConfigService();
        JenkinsGeneralConfig config = service.readConfig(configFile.toPath(), slaveToMasterSecurityKillSwitchPath, locationConfigurationPath);
        Assert.assertNotNull(config);
        Assert.assertEquals(config.getSecurityRealm(), JenkinsGeneralConfig.SECURITY_REALM.JENKINS_OWN_DATABASE);
        Assert.assertEquals(config.getAuthorizationType(), JenkinsGeneralConfig.AUTHORIZATION_TYPE.LOGGED_IN_USERS_CAN_DO_ANYTHING);
        Assert.assertEquals(config.getCsrf().isPreventCSRF(), false);
        Assert.assertEquals(config.getCsrf().getCrumbIssuer(), null);
        Assert.assertEquals(config.getCsrf().getExcludeClientIPFromCrumb(), null);
        Assert.assertNotNull(config.getSlaveAgentPort());
        Assert.assertEquals(config.getSlaveAgentPort().getPortType(), JenkinsGeneralConfig.PORT_TYPE.FIXED);
        Assert.assertEquals(config.getSlaveAgentPort().getPortNumber(), new Integer(50000));
    }

    @Test
    public void testSlaveAgentPortRandom() throws URISyntaxException {
        URL url = this.getClass().getClassLoader().getResource("configs/slave-agent-ports/jenkins-config-slave-agent-port-random.xml");
        File configFile = new File(url.toURI());
        JenkinsConfigService service = new JenkinsConfigService();
        JenkinsGeneralConfig config = service.readConfig(configFile.toPath(), slaveToMasterSecurityKillSwitchPath, locationConfigurationPath);
        Assert.assertNotNull(config);
        Assert.assertEquals(config.getSecurityRealm(), JenkinsGeneralConfig.SECURITY_REALM.JENKINS_OWN_DATABASE);
        Assert.assertEquals(config.getAuthorizationType(), JenkinsGeneralConfig.AUTHORIZATION_TYPE.LOGGED_IN_USERS_CAN_DO_ANYTHING);
        Assert.assertEquals(config.getCsrf().isPreventCSRF(), false);
        Assert.assertEquals(config.getCsrf().getCrumbIssuer(), null);
        Assert.assertEquals(config.getCsrf().getExcludeClientIPFromCrumb(), null);
        Assert.assertNotNull(config.getSlaveAgentPort());
        Assert.assertEquals(config.getSlaveAgentPort().getPortType(), JenkinsGeneralConfig.PORT_TYPE.RANDOM);
        Assert.assertEquals(config.getSlaveAgentPort().getPortNumber(), null);
    }

    @Test
    public void testSlaveAgentPortDisabled() throws URISyntaxException {
        URL url = this.getClass().getClassLoader().getResource("configs/slave-agent-ports/jenkins-config-slave-agent-port-disabled.xml");
        File configFile = new File(url.toURI());
        JenkinsConfigService service = new JenkinsConfigService();
        JenkinsGeneralConfig config = service.readConfig(configFile.toPath(), slaveToMasterSecurityKillSwitchPath, locationConfigurationPath);
        Assert.assertNotNull(config);
        Assert.assertEquals(config.getSecurityRealm(), JenkinsGeneralConfig.SECURITY_REALM.JENKINS_OWN_DATABASE);
        Assert.assertEquals(config.getAuthorizationType(), JenkinsGeneralConfig.AUTHORIZATION_TYPE.LOGGED_IN_USERS_CAN_DO_ANYTHING);
        Assert.assertEquals(config.getCsrf().isPreventCSRF(), false);
        Assert.assertEquals(config.getCsrf().getCrumbIssuer(), null);
        Assert.assertEquals(config.getCsrf().getExcludeClientIPFromCrumb(), null);
        Assert.assertNotNull(config.getSlaveAgentPort());
        Assert.assertEquals(config.getSlaveAgentPort().getPortType(), JenkinsGeneralConfig.PORT_TYPE.DISABLED);
        Assert.assertEquals(config.getSlaveAgentPort().getPortNumber(), null);
    }

    @Test
    public void testJNLPProtocolsInsecureDisabledSecureDisabled() throws URISyntaxException {
        URL url = this.getClass().getClassLoader().getResource("configs/jnlp/jenkins-config-jnlp-insecure-disabled-secure-disabled.xml");
        File configFile = new File(url.toURI());
        JenkinsConfigService service = new JenkinsConfigService();
        JenkinsGeneralConfig config = service.readConfig(configFile.toPath(), slaveToMasterSecurityKillSwitchPath, locationConfigurationPath);
        Assert.assertNotNull(config);
        Assert.assertEquals(config.getSecurityRealm(), JenkinsGeneralConfig.SECURITY_REALM.JENKINS_OWN_DATABASE);
        Assert.assertEquals(config.getAuthorizationType(), JenkinsGeneralConfig.AUTHORIZATION_TYPE.LOGGED_IN_USERS_CAN_DO_ANYTHING);
        Assert.assertEquals(config.getCsrf().isPreventCSRF(), false);
        Assert.assertEquals(config.getCsrf().getCrumbIssuer(), null);
        Assert.assertEquals(config.getCsrf().getExcludeClientIPFromCrumb(), null);
        Assert.assertNotNull(config.getSlaveAgentPort());
        Assert.assertEquals(config.getSlaveAgentPort().getPortType(), JenkinsGeneralConfig.PORT_TYPE.FIXED);
        Assert.assertEquals(config.getSlaveAgentPort().getPortNumber(), new Integer(50000));
        Assert.assertNotNull(config.getJnlpProtocols());
        Assert.assertEquals(config.getJnlpProtocols().isJnlp1ProtocolEnabled(), false);
        Assert.assertEquals(config.getJnlpProtocols().isJnlp2ProtocolEnabled(), false);
        Assert.assertEquals(config.getJnlpProtocols().isJnlp3ProtocolEnabled(), false);
        Assert.assertEquals(config.getJnlpProtocols().isJnlp4ProtocolEnabled(), false);
    }
    @Test
    public void testJNLPProtocolsInsecureDisabledSecureEnabled() throws URISyntaxException {
        URL url = this.getClass().getClassLoader().getResource("configs/jnlp/jenkins-config-jnlp-insecure-disabled-secure-enabled.xml");
        File configFile = new File(url.toURI());
        JenkinsConfigService service = new JenkinsConfigService();
        JenkinsGeneralConfig config = service.readConfig(configFile.toPath(), slaveToMasterSecurityKillSwitchPath, locationConfigurationPath);
        Assert.assertNotNull(config);
        Assert.assertEquals(config.getSecurityRealm(), JenkinsGeneralConfig.SECURITY_REALM.JENKINS_OWN_DATABASE);
        Assert.assertEquals(config.getAuthorizationType(), JenkinsGeneralConfig.AUTHORIZATION_TYPE.LOGGED_IN_USERS_CAN_DO_ANYTHING);
        Assert.assertEquals(config.getCsrf().isPreventCSRF(), false);
        Assert.assertEquals(config.getCsrf().getCrumbIssuer(), null);
        Assert.assertEquals(config.getCsrf().getExcludeClientIPFromCrumb(), null);
        Assert.assertNotNull(config.getSlaveAgentPort());
        Assert.assertEquals(config.getSlaveAgentPort().getPortType(), JenkinsGeneralConfig.PORT_TYPE.FIXED);
        Assert.assertEquals(config.getSlaveAgentPort().getPortNumber(), new Integer(50000));
        Assert.assertNotNull(config.getJnlpProtocols());
        Assert.assertEquals(config.getJnlpProtocols().isJnlp1ProtocolEnabled(), false);
        Assert.assertEquals(config.getJnlpProtocols().isJnlp2ProtocolEnabled(), false);
        Assert.assertEquals(config.getJnlpProtocols().isJnlp3ProtocolEnabled(), false);
        Assert.assertEquals(config.getJnlpProtocols().isJnlp4ProtocolEnabled(), true);
    }

    @Test
    public void testJNLPProtocolsInsecureEnabledSecureDisabled() throws URISyntaxException {
        URL url = this.getClass().getClassLoader().getResource("configs/jnlp/jenkins-config-jnlp-insecure-enabled-secure-disabled.xml");
        File configFile = new File(url.toURI());
        JenkinsConfigService service = new JenkinsConfigService();
        JenkinsGeneralConfig config = service.readConfig(configFile.toPath(), slaveToMasterSecurityKillSwitchPath, locationConfigurationPath);
        Assert.assertNotNull(config);
        Assert.assertEquals(config.getSecurityRealm(), JenkinsGeneralConfig.SECURITY_REALM.JENKINS_OWN_DATABASE);
        Assert.assertEquals(config.getAuthorizationType(), JenkinsGeneralConfig.AUTHORIZATION_TYPE.LOGGED_IN_USERS_CAN_DO_ANYTHING);
        Assert.assertEquals(config.getCsrf().isPreventCSRF(), false);
        Assert.assertEquals(config.getCsrf().getCrumbIssuer(), null);
        Assert.assertEquals(config.getCsrf().getExcludeClientIPFromCrumb(), null);
        Assert.assertNotNull(config.getSlaveAgentPort());
        Assert.assertEquals(config.getSlaveAgentPort().getPortType(), JenkinsGeneralConfig.PORT_TYPE.FIXED);
        Assert.assertEquals(config.getSlaveAgentPort().getPortNumber(), new Integer(50000));
        Assert.assertNotNull(config.getJnlpProtocols());
        Assert.assertEquals(config.getJnlpProtocols().isJnlp1ProtocolEnabled(), true);
        Assert.assertEquals(config.getJnlpProtocols().isJnlp2ProtocolEnabled(), true);
        Assert.assertEquals(config.getJnlpProtocols().isJnlp3ProtocolEnabled(), true);
        Assert.assertEquals(config.getJnlpProtocols().isJnlp4ProtocolEnabled(), false);
    }

    @Test
    public void testJNLPProtocolsInsecureEnabledSecureEnabled() throws URISyntaxException {
        URL url = this.getClass().getClassLoader().getResource("configs/jnlp/jenkins-config-jnlp-insecure-enabled-secure-enabled.xml");
        File configFile = new File(url.toURI());

        URL killSwitchUrl = this.getClass().getClassLoader().getResource("configs/agent-to-master-access-control/slave-to-master-security-kill-switch-enabled");
        File killSwitchFile = new File(killSwitchUrl.toURI());


        JenkinsConfigService service = new JenkinsConfigService();
        JenkinsGeneralConfig config = service.readConfig(configFile.toPath(), killSwitchFile.toPath(), locationConfigurationPath);
        Assert.assertNotNull(config);
        Assert.assertEquals(config.getSecurityRealm(), JenkinsGeneralConfig.SECURITY_REALM.JENKINS_OWN_DATABASE);
        Assert.assertEquals(config.getAuthorizationType(), JenkinsGeneralConfig.AUTHORIZATION_TYPE.LOGGED_IN_USERS_CAN_DO_ANYTHING);
        Assert.assertEquals(config.getCsrf().isPreventCSRF(), false);
        Assert.assertEquals(config.getCsrf().getCrumbIssuer(), null);
        Assert.assertEquals(config.getCsrf().getExcludeClientIPFromCrumb(), null);
        Assert.assertNotNull(config.getSlaveAgentPort());
        Assert.assertEquals(config.getSlaveAgentPort().getPortType(), JenkinsGeneralConfig.PORT_TYPE.FIXED);
        Assert.assertEquals(config.getSlaveAgentPort().getPortNumber(), new Integer(50000));
        Assert.assertNotNull(config.getJnlpProtocols());
        Assert.assertEquals(config.getJnlpProtocols().isJnlp1ProtocolEnabled(), true);
        Assert.assertEquals(config.getJnlpProtocols().isJnlp2ProtocolEnabled(), true);
        Assert.assertEquals(config.getJnlpProtocols().isJnlp3ProtocolEnabled(), true);
        Assert.assertEquals(config.getJnlpProtocols().isJnlp4ProtocolEnabled(), true);
        Assert.assertEquals(config.isAgentToMasterAccessControlEnabled(), false);
    }

    @Test
    public void testSlaveToMasterSecurityKillSwitchDisabled() throws URISyntaxException {
        URL url = this.getClass().getClassLoader().getResource("configs/jnlp/jenkins-config-jnlp-insecure-enabled-secure-enabled.xml");
        File configFile = new File(url.toURI());

        URL killSwitchUrl = this.getClass().getClassLoader().getResource("configs/agent-to-master-access-control/slave-to-master-security-kill-switch-disabled");
        File killSwitchFile = new File(killSwitchUrl.toURI());

        JenkinsConfigService service = new JenkinsConfigService();
        JenkinsGeneralConfig config = service.readConfig(configFile.toPath(), killSwitchFile.toPath(), locationConfigurationPath);
        Assert.assertNotNull(config);
        Assert.assertEquals(config.getSecurityRealm(), JenkinsGeneralConfig.SECURITY_REALM.JENKINS_OWN_DATABASE);
        Assert.assertEquals(config.getAuthorizationType(), JenkinsGeneralConfig.AUTHORIZATION_TYPE.LOGGED_IN_USERS_CAN_DO_ANYTHING);
        Assert.assertEquals(config.getCsrf().isPreventCSRF(), false);
        Assert.assertEquals(config.getCsrf().getCrumbIssuer(), null);
        Assert.assertEquals(config.getCsrf().getExcludeClientIPFromCrumb(), null);
        Assert.assertNotNull(config.getSlaveAgentPort());
        Assert.assertEquals(config.getSlaveAgentPort().getPortType(), JenkinsGeneralConfig.PORT_TYPE.FIXED);
        Assert.assertEquals(config.getSlaveAgentPort().getPortNumber(), new Integer(50000));
        Assert.assertNotNull(config.getJnlpProtocols());
        Assert.assertEquals(config.getJnlpProtocols().isJnlp1ProtocolEnabled(), true);
        Assert.assertEquals(config.getJnlpProtocols().isJnlp2ProtocolEnabled(), true);
        Assert.assertEquals(config.getJnlpProtocols().isJnlp3ProtocolEnabled(), true);
        Assert.assertEquals(config.getJnlpProtocols().isJnlp4ProtocolEnabled(), true);
        Assert.assertEquals(config.isAgentToMasterAccessControlEnabled(), true);
    }

    @Test
    public void testLocationConfigurationLocal() throws URISyntaxException {
        URL url = this.getClass().getClassLoader().getResource("configs/jnlp/jenkins-config-jnlp-insecure-enabled-secure-disabled.xml");
        File configFile = new File(url.toURI());

        Path locationConfigFilePath = new File(this.getClass().getClassLoader().getResource("configs/location-configuration/jenkins.model.JenkinsLocationConfiguration-local.xml").toURI()).toPath();

        JenkinsConfigService service = new JenkinsConfigService();
        JenkinsGeneralConfig config = service.readConfig(configFile.toPath(), slaveToMasterSecurityKillSwitchPath, locationConfigFilePath);
        Assert.assertNotNull(config);
        Assert.assertEquals(config.getSecurityRealm(), JenkinsGeneralConfig.SECURITY_REALM.JENKINS_OWN_DATABASE);
        Assert.assertEquals(config.getAuthorizationType(), JenkinsGeneralConfig.AUTHORIZATION_TYPE.LOGGED_IN_USERS_CAN_DO_ANYTHING);
        Assert.assertEquals(config.getCsrf().isPreventCSRF(), false);
        Assert.assertEquals(config.getCsrf().getCrumbIssuer(), null);
        Assert.assertEquals(config.getCsrf().getExcludeClientIPFromCrumb(), null);
        Assert.assertNotNull(config.getSlaveAgentPort());
        Assert.assertEquals(config.getSlaveAgentPort().getPortType(), JenkinsGeneralConfig.PORT_TYPE.FIXED);
        Assert.assertEquals(config.getSlaveAgentPort().getPortNumber(), new Integer(50000));
        Assert.assertNotNull(config.getJnlpProtocols());
        Assert.assertEquals(config.getJnlpProtocols().isJnlp1ProtocolEnabled(), true);
        Assert.assertEquals(config.getJnlpProtocols().isJnlp2ProtocolEnabled(), true);
        Assert.assertEquals(config.getJnlpProtocols().isJnlp3ProtocolEnabled(), true);
        Assert.assertEquals(config.getJnlpProtocols().isJnlp4ProtocolEnabled(), false);
        Assert.assertNotNull(config.getLocatorConfig());
        Assert.assertEquals(config.getLocatorConfig().getAdminEmailAddress(), null);
        Assert.assertEquals(config.getLocatorConfig().getJenkinsUrl(), "http://localhost:8080/");
    }

    @Test
    public void testLocationConfigurationDev() throws URISyntaxException {
        URL url = this.getClass().getClassLoader().getResource("configs/jnlp/jenkins-config-jnlp-insecure-enabled-secure-disabled.xml");
        File configFile = new File(url.toURI());

        Path locationConfigFilePath = new File(this.getClass().getClassLoader().getResource("configs/location-configuration/jenkins.model.JenkinsLocationConfiguration-dev.xml").toURI()).toPath();

        JenkinsConfigService service = new JenkinsConfigService();
        JenkinsGeneralConfig config = service.readConfig(configFile.toPath(), slaveToMasterSecurityKillSwitchPath, locationConfigFilePath);
        Assert.assertNotNull(config);
        Assert.assertEquals(config.getSecurityRealm(), JenkinsGeneralConfig.SECURITY_REALM.JENKINS_OWN_DATABASE);
        Assert.assertEquals(config.getAuthorizationType(), JenkinsGeneralConfig.AUTHORIZATION_TYPE.LOGGED_IN_USERS_CAN_DO_ANYTHING);
        Assert.assertEquals(config.getCsrf().isPreventCSRF(), false);
        Assert.assertEquals(config.getCsrf().getCrumbIssuer(), null);
        Assert.assertEquals(config.getCsrf().getExcludeClientIPFromCrumb(), null);
        Assert.assertNotNull(config.getSlaveAgentPort());
        Assert.assertEquals(config.getSlaveAgentPort().getPortType(), JenkinsGeneralConfig.PORT_TYPE.FIXED);
        Assert.assertEquals(config.getSlaveAgentPort().getPortNumber(), new Integer(50000));
        Assert.assertNotNull(config.getJnlpProtocols());
        Assert.assertEquals(config.getJnlpProtocols().isJnlp1ProtocolEnabled(), true);
        Assert.assertEquals(config.getJnlpProtocols().isJnlp2ProtocolEnabled(), true);
        Assert.assertEquals(config.getJnlpProtocols().isJnlp3ProtocolEnabled(), true);
        Assert.assertEquals(config.getJnlpProtocols().isJnlp4ProtocolEnabled(), false);
        Assert.assertNotNull(config.getLocatorConfig());
        Assert.assertEquals(config.getLocatorConfig().getAdminEmailAddress(), "viraj@levelops.io");
        Assert.assertEquals(config.getLocatorConfig().getJenkinsUrl(), "https://jenkins.dev.levelops.io/");
    }

}