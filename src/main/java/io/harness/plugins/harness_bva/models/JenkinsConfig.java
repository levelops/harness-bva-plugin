package io.harness.plugins.harness_bva.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "hudson")
public class JenkinsConfig {
    public static final String JNLP_1_PROTOCOL = "JNLP-connect";
    public static final String JNLP_2_PROTOCOL = "JNLP2-connect";
    public static final String JNLP_3_PROTOCOL = "JNLP3-connect";
    public static final String JNLP_4_PROTOCOL = "JNLP4-connect";
    public static final String SLAVE_TO_MASTER_ACCESS_CONTROL_FILE = "secrets/slave-to-master-security-kill-switch";

    private SecurityRealm securityRealm;
    private AuthorizationStrategy authorizationStrategy;
    private CrumbIssuer crumbIssuer;
    private Integer slaveAgentPort;

    private EnabledDisabledAgentProtocols enabledAgentProtocols;
    private EnabledDisabledAgentProtocols disabledAgentProtocols;


    //region Getter Setter
    public SecurityRealm getSecurityRealm() {
        return securityRealm;
    }

    public void setSecurityRealm(SecurityRealm securityRealm) {
        this.securityRealm = securityRealm;
    }

    public AuthorizationStrategy getAuthorizationStrategy() {
        return authorizationStrategy;
    }

    public void setAuthorizationStrategy(AuthorizationStrategy authorizationStrategy) {
        this.authorizationStrategy = authorizationStrategy;
    }

    public CrumbIssuer getCrumbIssuer() {
        return crumbIssuer;
    }

    public void setCrumbIssuer(CrumbIssuer crumbIssuer) {
        this.crumbIssuer = crumbIssuer;
    }

    public Integer getSlaveAgentPort() {
        return slaveAgentPort;
    }

    public void setSlaveAgentPort(Integer slaveAgentPort) {
        this.slaveAgentPort = slaveAgentPort;
    }

    public EnabledDisabledAgentProtocols getEnabledAgentProtocols() {
        return enabledAgentProtocols;
    }

    public void setEnabledAgentProtocols(EnabledDisabledAgentProtocols enabledAgentProtocols) {
        this.enabledAgentProtocols = enabledAgentProtocols;
    }

    public EnabledDisabledAgentProtocols getDisabledAgentProtocols() {
        return disabledAgentProtocols;
    }

    public void setDisabledAgentProtocols(EnabledDisabledAgentProtocols disabledAgentProtocols) {
        this.disabledAgentProtocols = disabledAgentProtocols;
    }

    //endregion

    //region JNLP - Agent Protocols
    public static final class EnabledDisabledAgentProtocols {
        @JacksonXmlElementWrapper(useWrapping = false)
        private List<String> string;

        public List<String> getString() {
            return string;
        }

        public void setString(List<String> string) {
            this.string = string;
        }
    }
    //endregion


    //region Crumb Issuer
    public static final class CrumbIssuer {
        @JacksonXmlProperty(isAttribute = true, localName = "class")
        private String data;

        @JacksonXmlProperty
        private Boolean excludeClientIPFromCrumb;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public Boolean getExcludeClientIPFromCrumb() {
            return excludeClientIPFromCrumb;
        }

        public void setExcludeClientIPFromCrumb(Boolean excludeClientIPFromCrumb) {
            this.excludeClientIPFromCrumb = excludeClientIPFromCrumb;
        }
    }
    //endregion

    //region Security Realm
    public static final class SecurityRealm {
        @JacksonXmlProperty(isAttribute = true, localName = "class")
        private String data;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
    //endregion

    //region AuthorizationStrategy
    public static final class AuthorizationStrategy {
        @JacksonXmlProperty(isAttribute = true, localName = "class")
        private String data;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
    //endregion
}