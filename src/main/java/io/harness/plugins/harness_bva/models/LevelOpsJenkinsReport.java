package io.harness.plugins.harness_bva.models;

public class LevelOpsJenkinsReport {
    private static final String PASS = "PASS";
    private static final String FAIL = "FAIL";
    private static final String IN_CONCLUSIVE = "IN CONCLUSIVE";

    //region Data Members
    private String jenkinsUrl;
    private String adminEmails;

    private String securityRealm;
    private String securityRealmPassFail;
    private String securityRealmColor;

    private String authorizationType;
    private String authorizationTypePassFail;
    private String authorizationTypeColor;

    private boolean csrfPreventionEnabled;
    private String csrfPreventionPassFail;
    private String csrfPreventionColor;

    private boolean jnlp1ProtocolEnabled;
    private String jnlp1ProtocolPassFail;
    private String jnlp1ProtocolColor;

    private boolean jnlp2ProtocolEnabled;
    private String jnlp2ProtocolPassFail;
    private String jnlp2ProtocolColor;

    private boolean jnlp3ProtocolEnabled;
    private String jnlp3ProtocolPassFail;
    private String jnlp3ProtocolColor;

    private boolean jnlp4ProtocolEnabled;
    private String jnlp4ProtocolPassFail;
    private String jnlp4ProtocolColor;

    private boolean tlsEnabledForMasterSlaveCommunication;
    private String tlsSettingPassFail;
    private String tlsSettingColor;

    private boolean pluginZapInstalled;
    private String pluginZapPassFail;
    private String pluginZapColor;

    private boolean pluginBrakemanInstalled;
    private String pluginBrakemanPassFail;
    private String pluginBrakemanColor;

    private boolean pluginAuditTrailInstalled;
    private String pluginAuditTrailPassFail;
    private String pluginAuditTrailColor;
    //endregion

    //region CSTOR
    public LevelOpsJenkinsReport(String jenkinsUrl, String adminEmails, String securityRealm, String securityRealmPassFail, String securityRealmColor, String authorizationType, String authorizationTypePassFail, String authorizationTypeColor, boolean csrfPreventionEnabled, String csrfPreventionPassFail, String csrfPreventionColor, boolean jnlp1ProtocolEnabled, String jnlp1ProtocolPassFail, String jnlp1ProtocolColor, boolean jnlp2ProtocolEnabled, String jnlp2ProtocolPassFail, String jnlp2ProtocolColor, boolean jnlp3ProtocolEnabled, String jnlp3ProtocolPassFail, String jnlp3ProtocolColor, boolean jnlp4ProtocolEnabled, String jnlp4ProtocolPassFail, String jnlp4ProtocolColor, boolean tlsEnabledForMasterSlaveCommunication, String tlsSettingPassFail, String tlsSettingColor, boolean pluginZapInstalled, String pluginZapPassFail, String pluginZapColor, boolean pluginBrakemanInstalled, String pluginBrakemanPassFail, String pluginBrakemanColor, boolean pluginAuditTrailInstalled, String pluginAuditTrailPassFail, String pluginAuditTrailColor) {
        this.jenkinsUrl = jenkinsUrl;
        this.adminEmails = adminEmails;
        this.securityRealm = securityRealm;
        this.securityRealmPassFail = securityRealmPassFail;
        this.securityRealmColor = securityRealmColor;
        this.authorizationType = authorizationType;
        this.authorizationTypePassFail = authorizationTypePassFail;
        this.authorizationTypeColor = authorizationTypeColor;
        this.csrfPreventionEnabled = csrfPreventionEnabled;
        this.csrfPreventionPassFail = csrfPreventionPassFail;
        this.csrfPreventionColor = csrfPreventionColor;
        this.jnlp1ProtocolEnabled = jnlp1ProtocolEnabled;
        this.jnlp1ProtocolPassFail = jnlp1ProtocolPassFail;
        this.jnlp1ProtocolColor = jnlp1ProtocolColor;
        this.jnlp2ProtocolEnabled = jnlp2ProtocolEnabled;
        this.jnlp2ProtocolPassFail = jnlp2ProtocolPassFail;
        this.jnlp2ProtocolColor = jnlp2ProtocolColor;
        this.jnlp3ProtocolEnabled = jnlp3ProtocolEnabled;
        this.jnlp3ProtocolPassFail = jnlp3ProtocolPassFail;
        this.jnlp3ProtocolColor = jnlp3ProtocolColor;
        this.jnlp4ProtocolEnabled = jnlp4ProtocolEnabled;
        this.jnlp4ProtocolPassFail = jnlp4ProtocolPassFail;
        this.jnlp4ProtocolColor = jnlp4ProtocolColor;
        this.tlsEnabledForMasterSlaveCommunication = tlsEnabledForMasterSlaveCommunication;
        this.tlsSettingPassFail = tlsSettingPassFail;
        this.tlsSettingColor = tlsSettingColor;
        this.pluginZapInstalled = pluginZapInstalled;
        this.pluginZapPassFail = pluginZapPassFail;
        this.pluginZapColor = pluginZapColor;
        this.pluginBrakemanInstalled = pluginBrakemanInstalled;
        this.pluginBrakemanPassFail = pluginBrakemanPassFail;
        this.pluginBrakemanColor = pluginBrakemanColor;
        this.pluginAuditTrailInstalled = pluginAuditTrailInstalled;
        this.pluginAuditTrailPassFail = pluginAuditTrailPassFail;
        this.pluginAuditTrailColor = pluginAuditTrailColor;
    }
    //endregion

    //region Getter
    public String getJenkinsUrl() {
        return jenkinsUrl;
    }

    public String getAdminEmails() {
        return adminEmails;
    }

    public String getSecurityRealm() {
        return securityRealm;
    }

    public String getSecurityRealmPassFail() {
        return securityRealmPassFail;
    }

    public String getSecurityRealmColor() {
        return securityRealmColor;
    }

    public String getAuthorizationType() {
        return authorizationType;
    }

    public String getAuthorizationTypePassFail() {
        return authorizationTypePassFail;
    }

    public String getAuthorizationTypeColor() {
        return authorizationTypeColor;
    }

    public boolean isCsrfPreventionEnabled() {
        return csrfPreventionEnabled;
    }

    public String getCsrfPreventionPassFail() {
        return csrfPreventionPassFail;
    }

    public String getCsrfPreventionColor() {
        return csrfPreventionColor;
    }

    public boolean isJnlp1ProtocolEnabled() {
        return jnlp1ProtocolEnabled;
    }

    public String getJnlp1ProtocolPassFail() {
        return jnlp1ProtocolPassFail;
    }

    public String getJnlp1ProtocolColor() {
        return jnlp1ProtocolColor;
    }

    public boolean isJnlp2ProtocolEnabled() {
        return jnlp2ProtocolEnabled;
    }

    public String getJnlp2ProtocolPassFail() {
        return jnlp2ProtocolPassFail;
    }

    public String getJnlp2ProtocolColor() {
        return jnlp2ProtocolColor;
    }

    public boolean isJnlp3ProtocolEnabled() {
        return jnlp3ProtocolEnabled;
    }

    public String getJnlp3ProtocolPassFail() {
        return jnlp3ProtocolPassFail;
    }

    public String getJnlp3ProtocolColor() {
        return jnlp3ProtocolColor;
    }

    public boolean isJnlp4ProtocolEnabled() {
        return jnlp4ProtocolEnabled;
    }

    public String getJnlp4ProtocolPassFail() {
        return jnlp4ProtocolPassFail;
    }

    public String getJnlp4ProtocolColor() {
        return jnlp4ProtocolColor;
    }

    public boolean isTlsEnabledForMasterSlaveCommunication() {
        return tlsEnabledForMasterSlaveCommunication;
    }

    public String getTlsSettingPassFail() {
        return tlsSettingPassFail;
    }

    public String getTlsSettingColor() {
        return tlsSettingColor;
    }

    public boolean isPluginZapInstalled() {
        return pluginZapInstalled;
    }

    public String getPluginZapPassFail() {
        return pluginZapPassFail;
    }

    public String getPluginZapColor() {
        return pluginZapColor;
    }

    public boolean isPluginBrakemanInstalled() {
        return pluginBrakemanInstalled;
    }

    public String getPluginBrakemanPassFail() {
        return pluginBrakemanPassFail;
    }

    public String getPluginBrakemanColor() {
        return pluginBrakemanColor;
    }

    public boolean isPluginAuditTrailInstalled() {
        return pluginAuditTrailInstalled;
    }

    public String getPluginAuditTrailPassFail() {
        return pluginAuditTrailPassFail;
    }

    public String getPluginAuditTrailColor() {
        return pluginAuditTrailColor;
    }

    //endregion

    public static final String getPassOrFail(final Boolean isSecure){
        if(isSecure == null){
            return IN_CONCLUSIVE;
        } else if(Boolean.TRUE.equals(isSecure)){
            return PASS;
        } else {
            return FAIL;
        }
    }

    //region LevelOpsJenkinsReportBuilder
    public static LevelOpsJenkinsReportBuilder builder(){
        return new LevelOpsJenkinsReportBuilder();
    }

    public static final class LevelOpsJenkinsReportBuilder {
        //region Data Members
        private String jenkinsUrl;
        private String adminEmails;
        private String securityRealm;
        private String securityRealmPassFail;
        private String authorizationType;
        private String authorizationTypePassFail;

        private boolean csrfPreventionEnabled;
        private String csrfPreventionPassFail;

        private boolean jnlp1ProtocolEnabled;
        private String jnlp1ProtocolPassFail;
        private boolean jnlp2ProtocolEnabled;
        private String jnlp2ProtocolPassFail;
        private boolean jnlp3ProtocolEnabled;
        private String jnlp3ProtocolPassFail;
        private boolean jnlp4ProtocolEnabled;
        private String jnlp4ProtocolPassFail;

        private boolean tlsEnabledForMasterSlaveCommunication;
        private String tlsSetttingPassFail;

        private boolean pluginZapInstalled;
        private String pluginZapPassFail;

        private boolean pluginBrakemanInstalled;
        private String pluginBrakemanPassFail;

        private boolean pluginAuditTrailInstalled;
        private String pluginAuditTrailPassFail;
        //endregion

        //region Getters Setters
        public String getJenkinsUrl() {
            return jenkinsUrl;
        }

        public LevelOpsJenkinsReportBuilder jenkinsUrl(String jenkinsUrl) {
            this.jenkinsUrl = jenkinsUrl;
            return this;
        }

        public String getAdminEmails() {
            return adminEmails;
        }

        public LevelOpsJenkinsReportBuilder adminEmails(String adminEmails) {
            this.adminEmails = (adminEmails == null) ? "" : adminEmails;
            return this;
        }

        public String getSecurityRealm() {
            return securityRealm;
        }

        public LevelOpsJenkinsReportBuilder securityRealm(String securityRealm) {
            this.securityRealm = securityRealm;
            return this;
        }

        public String getSecurityRealmPassFail() {
            return securityRealmPassFail;
        }

        public LevelOpsJenkinsReportBuilder securityRealmPassFail(String securityRealmPassFail) {
            this.securityRealmPassFail = securityRealmPassFail;
            return this;
        }

        public String getAuthorizationType() {
            return authorizationType;
        }

        public LevelOpsJenkinsReportBuilder authorizationType(String authorizationType) {
            this.authorizationType = authorizationType;
            return this;
        }

        public String getAuthorizationTypePassFail() {
            return authorizationTypePassFail;
        }

        public LevelOpsJenkinsReportBuilder authorizationTypePassFail(String authorizationTypePassFail) {
            this.authorizationTypePassFail = authorizationTypePassFail;
            return this;
        }

        public boolean isCsrfPreventionEnabled() {
            return csrfPreventionEnabled;
        }

        public LevelOpsJenkinsReportBuilder csrfPreventionEnabled(boolean csrfPreventionEnabled) {
            this.csrfPreventionEnabled = csrfPreventionEnabled;
            return this;
        }

        public String getCsrfPreventionPassFail() {
            return csrfPreventionPassFail;
        }

        public LevelOpsJenkinsReportBuilder csrfPreventionPassFail(String csrfPreventionPassFail) {
            this.csrfPreventionPassFail = csrfPreventionPassFail;
            return this;
        }

        public boolean isJnlp1ProtocolEnabled() {
            return jnlp1ProtocolEnabled;
        }

        public LevelOpsJenkinsReportBuilder jnlp1ProtocolEnabled(boolean jnlp1ProtocolEnabled) {
            this.jnlp1ProtocolEnabled = jnlp1ProtocolEnabled;
            return this;
        }

        public String getJnlp1ProtocolPassFail() {
            return jnlp1ProtocolPassFail;
        }

        public LevelOpsJenkinsReportBuilder jnlp1ProtocolPassFail(String jnlp1ProtocolPassFail) {
            this.jnlp1ProtocolPassFail = jnlp1ProtocolPassFail;
            return this;
        }

        public boolean isJnlp2ProtocolEnabled() {
            return jnlp2ProtocolEnabled;
        }

        public LevelOpsJenkinsReportBuilder jnlp2ProtocolEnabled(boolean jnlp2ProtocolEnabled) {
            this.jnlp2ProtocolEnabled = jnlp2ProtocolEnabled;
            return this;
        }

        public String getJnlp2ProtocolPassFail() {
            return jnlp2ProtocolPassFail;
        }

        public LevelOpsJenkinsReportBuilder jnlp2ProtocolPassFail(String jnlp2ProtocolPassFail) {
            this.jnlp2ProtocolPassFail = jnlp2ProtocolPassFail;
            return this;
        }

        public boolean isJnlp3ProtocolEnabled() {
            return jnlp3ProtocolEnabled;
        }

        public LevelOpsJenkinsReportBuilder jnlp3ProtocolEnabled(boolean jnlp3ProtocolEnabled) {
            this.jnlp3ProtocolEnabled = jnlp3ProtocolEnabled;
            return this;
        }

        public String getJnlp3ProtocolPassFail() {
            return jnlp3ProtocolPassFail;
        }

        public LevelOpsJenkinsReportBuilder jnlp3ProtocolPassFail(String jnlp3ProtocolPassFail) {
            this.jnlp3ProtocolPassFail = jnlp3ProtocolPassFail;
            return this;
        }

        public boolean isJnlp4ProtocolEnabled() {
            return jnlp4ProtocolEnabled;
        }

        public LevelOpsJenkinsReportBuilder jnlp4ProtocolEnabled(boolean jnlp4ProtocolEnabled) {
            this.jnlp4ProtocolEnabled = jnlp4ProtocolEnabled;
            return this;
        }

        public String getJnlp4ProtocolPassFail() {
            return jnlp4ProtocolPassFail;
        }

        public LevelOpsJenkinsReportBuilder jnlp4ProtocolPassFail(String jnlp4ProtocolPassFail) {
            this.jnlp4ProtocolPassFail = jnlp4ProtocolPassFail;
            return this;
        }

        public boolean isTlsEnabledForMasterSlaveCommunication() {
            return tlsEnabledForMasterSlaveCommunication;
        }

        public LevelOpsJenkinsReportBuilder tlsEnabledForMasterSlaveCommunication(boolean tlsEnabledForMasterSlaveCommunication) {
            this.tlsEnabledForMasterSlaveCommunication = tlsEnabledForMasterSlaveCommunication;
            return this;
        }

        public String getTlsSetttingPassFail() {
            return tlsSetttingPassFail;
        }

        public LevelOpsJenkinsReportBuilder tlsSetttingPassFail(String tlsSetttingPassFail) {
            this.tlsSetttingPassFail = tlsSetttingPassFail;
            return this;
        }

        public boolean isPluginZapInstalled() {
            return pluginZapInstalled;
        }

        public LevelOpsJenkinsReportBuilder pluginZapInstalled(boolean pluginZapInstalled) {
            this.pluginZapInstalled = pluginZapInstalled;
            return this;
        }

        public String getPluginZapPassFail() {
            return pluginZapPassFail;
        }

        public LevelOpsJenkinsReportBuilder pluginZapPassFail(String pluginZapPassFail) {
            this.pluginZapPassFail = pluginZapPassFail;
            return this;
        }

        public boolean isPluginBrakemanInstalled() {
            return pluginBrakemanInstalled;
        }

        public LevelOpsJenkinsReportBuilder pluginBrakemanInstalled(boolean pluginBrakemanInstalled) {
            this.pluginBrakemanInstalled = pluginBrakemanInstalled;
            return this;
        }

        public String getPluginBrakemanPassFail() {
            return pluginBrakemanPassFail;
        }

        public LevelOpsJenkinsReportBuilder pluginBrakemanPassFail(String pluginBrakemanPassFail) {
            this.pluginBrakemanPassFail = pluginBrakemanPassFail;
            return this;
        }

        public boolean isPluginAuditTrailInstalled() {
            return pluginAuditTrailInstalled;
        }

        public LevelOpsJenkinsReportBuilder pluginAuditTrailInstalled(boolean pluginAuditTrailInstalled) {
            this.pluginAuditTrailInstalled = pluginAuditTrailInstalled;
            return this;
        }

        public String getPluginAuditTrailPassFail() {
            return pluginAuditTrailPassFail;
        }

        public LevelOpsJenkinsReportBuilder pluginAuditTrailPassFail(String pluginAuditTrailPassFail) {
            this.pluginAuditTrailPassFail = pluginAuditTrailPassFail;
            return this;
        }
        //endregion

        public static String getColor(final String passFail){
            if("PASS".equals(passFail)){
                return "text-success";
            } else if ("FAIL".equals(passFail)){
                return "text-danger";
            } else {
                return "text-info";
            }
        }

        public LevelOpsJenkinsReport build(){
            return new LevelOpsJenkinsReport(jenkinsUrl, adminEmails,
                    securityRealm, securityRealmPassFail, getColor(securityRealmPassFail),
                    authorizationType, authorizationTypePassFail, getColor(authorizationTypePassFail),
                    csrfPreventionEnabled, csrfPreventionPassFail, getColor(csrfPreventionPassFail),
                    jnlp1ProtocolEnabled, jnlp1ProtocolPassFail, getColor(jnlp1ProtocolPassFail),
                    jnlp2ProtocolEnabled, jnlp2ProtocolPassFail, getColor(jnlp2ProtocolPassFail),
                    jnlp3ProtocolEnabled, jnlp3ProtocolPassFail, getColor(jnlp3ProtocolPassFail),
                    jnlp4ProtocolEnabled, jnlp4ProtocolPassFail, getColor(jnlp4ProtocolPassFail),
                    tlsEnabledForMasterSlaveCommunication, tlsSetttingPassFail, getColor(tlsSetttingPassFail),
                    pluginZapInstalled, pluginZapPassFail, getColor(pluginZapPassFail),
                    pluginBrakemanInstalled, pluginBrakemanPassFail, getColor(pluginBrakemanPassFail),
                    pluginAuditTrailInstalled, pluginAuditTrailPassFail, getColor(pluginAuditTrailPassFail));
        }
    }
    //endregion
}