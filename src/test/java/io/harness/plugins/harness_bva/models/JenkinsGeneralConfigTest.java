package io.harness.plugins.harness_bva.models;

import org.junit.Assert;
import org.junit.Test;

public class JenkinsGeneralConfigTest {
    @Test
    public void testSecurityRealmParse(){
        Assert.assertEquals(JenkinsGeneralConfig.SECURITY_REALM.ACTIVE_DIRECTORY, JenkinsGeneralConfig.SECURITY_REALM.parseRealm("hudson.plugins.active_directory.ActiveDirectorySecurityRealm"));
        Assert.assertEquals(JenkinsGeneralConfig.SECURITY_REALM.DELEGATE_TO_SERVLET_CONTAINER, JenkinsGeneralConfig.SECURITY_REALM.parseRealm("hudson.security.LegacySecurityRealm"));
        Assert.assertEquals(JenkinsGeneralConfig.SECURITY_REALM.JENKINS_OWN_DATABASE, JenkinsGeneralConfig.SECURITY_REALM.parseRealm("hudson.security.HudsonPrivateSecurityRealm"));
        Assert.assertEquals(JenkinsGeneralConfig.SECURITY_REALM.LDAP, JenkinsGeneralConfig.SECURITY_REALM.parseRealm("hudson.security.LDAPSecurityRealm"));
        Assert.assertEquals(JenkinsGeneralConfig.SECURITY_REALM.UNIX_USER_GROUP_DATABASE, JenkinsGeneralConfig.SECURITY_REALM.parseRealm("hudson.security.PAMSecurityRealm"));
        Assert.assertEquals(JenkinsGeneralConfig.SECURITY_REALM.OTHER, JenkinsGeneralConfig.SECURITY_REALM.parseRealm("hudson.security.unknown"));
        Assert.assertEquals(JenkinsGeneralConfig.SECURITY_REALM.OTHER, JenkinsGeneralConfig.SECURITY_REALM.parseRealm(null));
        Assert.assertEquals(JenkinsGeneralConfig.SECURITY_REALM.OTHER, JenkinsGeneralConfig.SECURITY_REALM.parseRealm(""));
    }

    @Test
    public void testAuthorizationTypeParse(){
        Assert.assertEquals(JenkinsGeneralConfig.AUTHORIZATION_TYPE.ANYONE_CAN_DO_ANYTHING, JenkinsGeneralConfig.AUTHORIZATION_TYPE.parseAuthorization("hudson.security.AuthorizationStrategy$Unsecured"));
        Assert.assertEquals(JenkinsGeneralConfig.AUTHORIZATION_TYPE.LEGACY_MODE, JenkinsGeneralConfig.AUTHORIZATION_TYPE.parseAuthorization("hudson.security.LegacyAuthorizationStrategy"));
        Assert.assertEquals(JenkinsGeneralConfig.AUTHORIZATION_TYPE.LOGGED_IN_USERS_CAN_DO_ANYTHING, JenkinsGeneralConfig.AUTHORIZATION_TYPE.parseAuthorization("hudson.security.FullControlOnceLoggedInAuthorizationStrategy"));
        Assert.assertEquals(JenkinsGeneralConfig.AUTHORIZATION_TYPE.MATRIX_BASED_SECURITY, JenkinsGeneralConfig.AUTHORIZATION_TYPE.parseAuthorization("hudson.security.GlobalMatrixAuthorizationStrategy"));
        Assert.assertEquals(JenkinsGeneralConfig.AUTHORIZATION_TYPE.PROJECT_BASED_MATRIX_AUTHORIZATION_STRATEGY, JenkinsGeneralConfig.AUTHORIZATION_TYPE.parseAuthorization("hudson.security.ProjectMatrixAuthorizationStrategy"));
        Assert.assertEquals(JenkinsGeneralConfig.AUTHORIZATION_TYPE.ROLE_BASED_STRATEGY, JenkinsGeneralConfig.AUTHORIZATION_TYPE.parseAuthorization("com.michelin.cio.hudson.plugins.rolestrategy.RoleBasedAuthorizationStrategy"));
        Assert.assertEquals(JenkinsGeneralConfig.AUTHORIZATION_TYPE.OTHER, JenkinsGeneralConfig.AUTHORIZATION_TYPE.parseAuthorization(""));
    }

    @Test
    public void testJNLPProtocols(){
        JenkinsGeneralConfig.JNLPProtocols jnlpProtocols = JenkinsGeneralConfig.JNLPProtocols.builder().build();
        Assert.assertNotNull(jnlpProtocols);
        Assert.assertEquals(false, jnlpProtocols.isJnlp1ProtocolEnabled());
        Assert.assertEquals(false, jnlpProtocols.isJnlp2ProtocolEnabled());
        Assert.assertEquals(false, jnlpProtocols.isJnlp3ProtocolEnabled());
        Assert.assertEquals(true, jnlpProtocols.isJnlp4ProtocolEnabled());
    }
}