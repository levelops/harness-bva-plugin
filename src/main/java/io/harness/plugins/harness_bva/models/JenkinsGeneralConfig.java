package io.harness.plugins.harness_bva.models;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JenkinsGeneralConfig {
    private final JenkinsLocator locatorConfig;
    private final SECURITY_REALM securityRealm;
    private final AUTHORIZATION_TYPE authorizationType;
    private final CSRF csrf;
    private final SlaveAgentPort slaveAgentPort;
    private final JNLPProtocols jnlpProtocols;
    private final boolean agentToMasterAccessControlEnabled;

    public JenkinsGeneralConfig(JenkinsLocator locatorConfig, SECURITY_REALM securityRealm, AUTHORIZATION_TYPE authorizationType, CSRF csrf, SlaveAgentPort slaveAgentPort, JNLPProtocols jnlpProtocols, boolean agentToMasterAccessControlEnabled) {
        this.locatorConfig = locatorConfig;
        this.securityRealm = securityRealm;
        this.authorizationType = authorizationType;
        this.csrf = csrf;
        this.slaveAgentPort = slaveAgentPort;
        this.jnlpProtocols = jnlpProtocols;
        this.agentToMasterAccessControlEnabled = agentToMasterAccessControlEnabled;
    }

    //region SECURITY_REALM
    public enum SECURITY_REALM{
        ACTIVE_DIRECTORY(Pattern.compile("^.*\\.ActiveDirectorySecurityRealm$"), "Active Directory"),
        DELEGATE_TO_SERVLET_CONTAINER(Pattern.compile("^.*\\.LegacySecurityRealm$"), "Delegate to servlet container"),
        JENKINS_OWN_DATABASE(Pattern.compile("^.*\\.HudsonPrivateSecurityRealm$"), "Jenkins' own user database"),
        LDAP(Pattern.compile("^.*\\.LDAPSecurityRealm$"), "LDAP"),
        UNIX_USER_GROUP_DATABASE(Pattern.compile("^.*\\.PAMSecurityRealm$"), "Unix user/group database"),
        OTHER(null, "Unknown");

        private final Pattern pattern;
        private final String humanReadableText;

        SECURITY_REALM(Pattern pattern, String humanReadableText) {
            this.pattern = pattern;
            this.humanReadableText = humanReadableText;
        }

        public Pattern getPattern() {
            return pattern;
        }
        public String getHumanReadableText() {
            return humanReadableText;
        }

        public static SECURITY_REALM parseRealm(final String input){
            if(StringUtils.isBlank(input)){
                return SECURITY_REALM.OTHER;
            }
            for(SECURITY_REALM sr : values()){
                if(sr.getPattern() == null){
                    continue;
                }
                Matcher matcher = sr.getPattern().matcher(input);
                if(matcher.matches()){
                    return sr;
                }
            }
            return SECURITY_REALM.OTHER;
        }
        public static Boolean isSecure(final SECURITY_REALM securityRealm){
            if(securityRealm == null){
                return null;
            }
            switch (securityRealm){
                case JENKINS_OWN_DATABASE:
                    return Boolean.FALSE;
                case OTHER:
                    return null;
                default:
                    return Boolean.TRUE;
            }
        }
    }
    //endregion
    //region AUTHORIZATION_TYPE
    public enum AUTHORIZATION_TYPE{
        ANYONE_CAN_DO_ANYTHING(Pattern.compile("^.*\\.AuthorizationStrategy\\$Unsecured$"), "Anyone can do anything"),
        LEGACY_MODE(Pattern.compile("^.*\\.LegacyAuthorizationStrategy$"), "Legacy mode"),
        LOGGED_IN_USERS_CAN_DO_ANYTHING(Pattern.compile("^.*\\.FullControlOnceLoggedInAuthorizationStrategy$"), "Logged-in users can do anything"),
        MATRIX_BASED_SECURITY(Pattern.compile("^.*\\.GlobalMatrixAuthorizationStrategy$"), "Matrix-based security"),
        PROJECT_BASED_MATRIX_AUTHORIZATION_STRATEGY(Pattern.compile("^.*\\.ProjectMatrixAuthorizationStrategy$"), "Project-based Matrix Authorization Strategy"),
        ROLE_BASED_STRATEGY(Pattern.compile("^.*\\.RoleBasedAuthorizationStrategy$"), "Role-Based Strategy"),
        OTHER(null, "Unknown");

        private final Pattern pattern;
        private final String humanReadableText;

        public Pattern getPattern() {
            return pattern;
        }
        public String getHumanReadableText() {
            return humanReadableText;
        }

        AUTHORIZATION_TYPE(Pattern pattern, String humanReadableText) {
            this.pattern = pattern;
            this.humanReadableText = humanReadableText;
        }

        public static AUTHORIZATION_TYPE parseAuthorization(final String input){
            for(AUTHORIZATION_TYPE authorizationType : values()){
                if(authorizationType.getPattern() == null){
                    continue;
                }
                Matcher matcher = authorizationType.getPattern().matcher(input);
                if(matcher.matches()){
                    return authorizationType;
                }
            }
            return AUTHORIZATION_TYPE.OTHER;
        }
        public static Boolean isSecure(final AUTHORIZATION_TYPE authorizationType){
            if (authorizationType == null){
                return null;
            }
            switch (authorizationType){
                case ANYONE_CAN_DO_ANYTHING:
                case LOGGED_IN_USERS_CAN_DO_ANYTHING:
                    return Boolean.FALSE;
                case OTHER:
                    return null;
                default:
                    return Boolean.TRUE;
            }
        }
    }
    //endregion

    //region CSRF
    public static final class CSRF{
        private final boolean preventCSRF;
        private final String crumbIssuer;
        private final Boolean excludeClientIPFromCrumb;

        public CSRF(boolean preventCSRF, String crumbIssuer, Boolean excludeClientIPFromCrumb) {
            this.preventCSRF = preventCSRF;
            this.crumbIssuer = crumbIssuer;
            this.excludeClientIPFromCrumb = excludeClientIPFromCrumb;
        }

        public static CSRFBuilder builder(){
            return new CSRFBuilder();
        }

        //region Getter
        public boolean isPreventCSRF() {
            return preventCSRF;
        }
        public String getCrumbIssuer() {
            return crumbIssuer;
        }
        public Boolean getExcludeClientIPFromCrumb() {
            return excludeClientIPFromCrumb;
        }
        //endregion
        //region toString
        @Override
        public String toString() {
            return "CSRF{" +
                    "preventCSRF=" + preventCSRF +
                    ", crumbIssuer='" + crumbIssuer + '\'' +
                    ", excludeClientIPFromCrumb=" + excludeClientIPFromCrumb +
                    '}';
        }
        //endregion

        //region CSRFBuilder
        public static final class CSRFBuilder{
            private boolean preventCSRF;
            private String crumbIssuer;
            private Boolean excludeClientIPFromCrumb;

            public CSRFBuilder preventCSRF(boolean preventCSRF) {
                this.preventCSRF = preventCSRF;
                return this;
            }
            public CSRFBuilder crumbIssuer(String crumbIssuer) {
                this.crumbIssuer = crumbIssuer;
                return this;
            }
            public CSRFBuilder excludeClientIPFromCrumb(Boolean excludeClientIPFromCrumb) {
                this.excludeClientIPFromCrumb = excludeClientIPFromCrumb;
                return this;
            }
            public CSRF build(){
                return new CSRF(preventCSRF, crumbIssuer, excludeClientIPFromCrumb);
            }
        }
        //endregion
    }
    //endregion

    //region PORT_TYPE
    public enum PORT_TYPE{
        FIXED,
        RANDOM,
        DISABLED;
        public static PORT_TYPE parsePort(final Integer inputPort){
            if(inputPort == null){
                return null;
            }
            if(inputPort == -1){
                return PORT_TYPE.DISABLED;
            } else if (inputPort == 0){
                return PORT_TYPE.RANDOM;
            } else {
                return PORT_TYPE.FIXED;
            }
        }
    }
    //endregion

    //region SlaveAgentPort
    public static final class SlaveAgentPort {
        private final PORT_TYPE portType;
        private final Integer portNumber;

        public SlaveAgentPort(PORT_TYPE portType, Integer portNumber) {
            this.portType = portType;
            this.portNumber = portNumber;
        }
        public static SlaveAgentPortBuilder builder(){
            return new SlaveAgentPortBuilder();
        }
        //region Getters
        public PORT_TYPE getPortType() {
            return portType;
        }
        public Integer getPortNumber() {
            return portNumber;
        }
        //endregion
        //region toString
        @Override
        public String toString() {
            return "SlaveAgentPort{" +
                    "portType=" + portType +
                    ", portNumber=" + portNumber +
                    '}';
        }
        //endregion
        //region SlaveAgentPortBuilder
        public static final class SlaveAgentPortBuilder {
            private PORT_TYPE portType;
            private Integer portNumber;

            public SlaveAgentPortBuilder portType(PORT_TYPE portType) {
                this.portType = portType;
                return this;
            }

            public SlaveAgentPortBuilder portNumber(Integer portNumber) {
                this.portNumber = portNumber;
                return this;
            }
            public SlaveAgentPort build(){
                return new SlaveAgentPort(portType, portNumber);
            }
        }
        //endregion
    }
    //endregion

    //region JNLPProtocols
    public static final class JNLPProtocols {
        private final boolean jnlp1ProtocolEnabled;
        private final boolean jnlp2ProtocolEnabled;
        private final boolean jnlp3ProtocolEnabled;
        private final boolean jnlp4ProtocolEnabled;

        public JNLPProtocols(boolean jnlp1ProtocolEnabled, boolean jnlp2ProtocolEnabled, boolean jnlp3ProtocolEnabled, boolean jnlp4ProtocolEnabled) {
            this.jnlp1ProtocolEnabled = jnlp1ProtocolEnabled;
            this.jnlp2ProtocolEnabled = jnlp2ProtocolEnabled;
            this.jnlp3ProtocolEnabled = jnlp3ProtocolEnabled;
            this.jnlp4ProtocolEnabled = jnlp4ProtocolEnabled;
        }
        public static JNLPProtocolsBuilder builder(){
            return new JNLPProtocolsBuilder();
        }
        //region Getters
        public boolean isJnlp1ProtocolEnabled() {
            return jnlp1ProtocolEnabled;
        }
        public boolean isJnlp2ProtocolEnabled() {
            return jnlp2ProtocolEnabled;
        }
        public boolean isJnlp3ProtocolEnabled() {
            return jnlp3ProtocolEnabled;
        }
        public boolean isJnlp4ProtocolEnabled() {
            return jnlp4ProtocolEnabled;
        }
        //endregion
        //region toString
        @Override
        public String toString() {
            return "JNLPProtocols{" +
                    "jnlp1ProtocolEnabled=" + jnlp1ProtocolEnabled +
                    ", jnlp2ProtocolEnabled=" + jnlp2ProtocolEnabled +
                    ", jnlp3ProtocolEnabled=" + jnlp3ProtocolEnabled +
                    ", jnlp4ProtocolEnabled=" + jnlp4ProtocolEnabled +
                    '}';
        }
        //endregion
        //region JNLPProtocolsBuilder
        public static final class JNLPProtocolsBuilder {
            private boolean jnlp1ProtocolEnabled = false;
            private boolean jnlp2ProtocolEnabled = false;
            private boolean jnlp3ProtocolEnabled = false;
            private boolean jnlp4ProtocolEnabled = true;

            public JNLPProtocolsBuilder jnlp1ProtocolEnabled(boolean jnlp1ProtocolEnabled) {
                this.jnlp1ProtocolEnabled = jnlp1ProtocolEnabled;
                return this;
            }
            public JNLPProtocolsBuilder jnlp2ProtocolEnabled(boolean jnlp2ProtocolEnabled) {
                this.jnlp2ProtocolEnabled = jnlp2ProtocolEnabled;
                return this;
            }
            public JNLPProtocolsBuilder jnlp3ProtocolEnabled(boolean jnlp3ProtocolEnabled) {
                this.jnlp3ProtocolEnabled = jnlp3ProtocolEnabled;
                return this;
            }
            public JNLPProtocolsBuilder jnlp4ProtocolEnabled(boolean jnlp4ProtocolEnabled) {
                this.jnlp4ProtocolEnabled = jnlp4ProtocolEnabled;
                return this;
            }

            public JNLPProtocols build(){
                return new JNLPProtocols(jnlp1ProtocolEnabled, jnlp2ProtocolEnabled, jnlp3ProtocolEnabled, jnlp4ProtocolEnabled);
            }
        }
        //endregion
    }
    //endregion

    //region Authorization
    public static final class Authorization{
        private final AUTHORIZATION_TYPE type;
        private final Boolean allowAnonymousReadAccess;

        public Authorization(AUTHORIZATION_TYPE type, Boolean allowAnonymousReadAccess) {
            this.type = type;
            this.allowAnonymousReadAccess = allowAnonymousReadAccess;
        }
        public static AuthorizationBuilder builder(){
            return new AuthorizationBuilder();
        }
        //region Getters
        public AUTHORIZATION_TYPE getType() {
            return type;
        }
        public Boolean getAllowAnonymousReadAccess() {
            return allowAnonymousReadAccess;
        }
        //endregion
        //region AuthorizationBuilder
        public static final class AuthorizationBuilder{
            private AUTHORIZATION_TYPE type;
            private Boolean allowAnonymousReadAccess;

            public AuthorizationBuilder type(AUTHORIZATION_TYPE type) {
                this.type = type;
                return this;
            }
            public AuthorizationBuilder allowAnonymousReadAccess(Boolean allowAnonymousReadAccess) {
                this.allowAnonymousReadAccess = allowAnonymousReadAccess;
                return this;
            }
            public Authorization build(){
                return new Authorization(type, allowAnonymousReadAccess);
            }
        }
        //endregion
    }
    //endregion

    //region JenkinsLocator
    //./com.google.jenkins.plugins.googlecontainerregistryauth.GoogleContainerRegistryCredentialGlobalConfig.xml
    //./jenkins.model.JenkinsLocationConfiguration.xml
    public static final class JenkinsLocator {
        private final String adminEmailAddress;
        private final String jenkinsUrl;

        public JenkinsLocator(String adminEmailAddress, String jenkinsUrl) {
            this.adminEmailAddress = adminEmailAddress;
            this.jenkinsUrl = jenkinsUrl;
        }

        //region toString
        @Override
        public String toString() {
            return "JenkinsLocator{" +
                    "adminEmailAddress='" + adminEmailAddress + '\'' +
                    ", jenkinsUrl='" + jenkinsUrl + '\'' +
                    '}';
        }
        //endregion

        public static JenkinsLocatorBuilder builder(){
            return new JenkinsLocatorBuilder();
        }
        //region Getter
        public String getAdminEmailAddress() {
            return adminEmailAddress;
        }
        public String getJenkinsUrl() {
            return jenkinsUrl;
        }
        //endregion
        //region JenkinsLocatorBuilder
        public static final class JenkinsLocatorBuilder {
            private String adminEmailAddress;
            private String jenkinsUrl;

            public JenkinsLocatorBuilder adminEmailAddress(String adminEmailAddress) {
                this.adminEmailAddress = adminEmailAddress;
                return this;
            }
            public JenkinsLocatorBuilder jenkinsUrl(String jenkinsUrl) {
                this.jenkinsUrl = jenkinsUrl;
                return this;
            }
            public JenkinsLocator build(){
                return new JenkinsLocator(adminEmailAddress, jenkinsUrl);
            }
        }
        //endregion
    }
    //endregion

    public static JenkinsGeneralConfigBuilder builder(){
        return new JenkinsGeneralConfigBuilder();
    }

    //region Getters
    public JenkinsLocator getLocatorConfig() {
        return locatorConfig;
    }
    public SECURITY_REALM getSecurityRealm() {
        return securityRealm;
    }
    public AUTHORIZATION_TYPE getAuthorizationType() {
        return authorizationType;
    }
    public CSRF getCsrf() {
        return csrf;
    }
    public SlaveAgentPort getSlaveAgentPort() {
        return slaveAgentPort;
    }
    public JNLPProtocols getJnlpProtocols() {
        return jnlpProtocols;
    }
    public boolean isAgentToMasterAccessControlEnabled() {
        return agentToMasterAccessControlEnabled;
    }
    //endregion

    //region toString
    @Override
    public String toString() {
        return "JenkinsGeneralConfig{" +
                "locatorConfig=" + locatorConfig +
                ", securityRealm=" + securityRealm +
                ", authorizationType=" + authorizationType +
                ", csrf=" + csrf +
                ", slaveAgentPort=" + slaveAgentPort +
                ", jnlpProtocols=" + jnlpProtocols +
                ", agentToMasterAccessControlEnabled=" + agentToMasterAccessControlEnabled +
                '}';
    }
    //endregion

    //region JenkinsGeneralConfigBuilder
    public static final class JenkinsGeneralConfigBuilder{
        private JenkinsLocator locatorConfig;
        private SECURITY_REALM securityRealm;
        private AUTHORIZATION_TYPE authorizationType;
        private CSRF csrf;
        private SlaveAgentPort slaveAgentPort;
        private JNLPProtocols jnlpProtocols;
        private boolean agentToMasterAccessControlEnabled;

        public JenkinsGeneralConfigBuilder locatorConfig(JenkinsLocator locatorConfig) {
            this.locatorConfig = locatorConfig;
            return this;
        }

        public JenkinsGeneralConfigBuilder securityRealm(SECURITY_REALM securityRealm) {
            this.securityRealm = securityRealm;
            return this;
        }

        public JenkinsGeneralConfigBuilder authorizationType(AUTHORIZATION_TYPE authorizationType) {
            this.authorizationType = authorizationType;
            return this;
        }

        public JenkinsGeneralConfigBuilder csrf(CSRF csrf) {
            this.csrf = csrf;
            return this;
        }

        public JenkinsGeneralConfigBuilder slaveAgentPort(SlaveAgentPort slaveAgentPort) {
            this.slaveAgentPort = slaveAgentPort;
            return this;
        }

        public JenkinsGeneralConfigBuilder jnlpProtocols(JNLPProtocols jnlpProtocols) {
            this.jnlpProtocols = jnlpProtocols;
            return this;
        }

        public JenkinsGeneralConfigBuilder agentToMasterAccessControlEnabled(boolean agentToMasterAccessControlEnabled) {
            this.agentToMasterAccessControlEnabled = agentToMasterAccessControlEnabled;
            return this;
        }

        public JenkinsGeneralConfig build(){
            return new JenkinsGeneralConfig(locatorConfig, securityRealm, authorizationType, csrf, slaveAgentPort, jnlpProtocols, agentToMasterAccessControlEnabled);
        }
    }
    //endregion
}