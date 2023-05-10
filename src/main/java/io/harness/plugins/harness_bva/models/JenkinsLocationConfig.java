package io.harness.plugins.harness_bva.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.regex.Pattern;

@JacksonXmlRootElement(localName = "jenkins.model.JenkinsLocationConfiguration")
public class JenkinsLocationConfig {
    public static final Pattern ADDRESS_NOT_CONFIGURED = Pattern.compile(".*address not configured yet.*");
    public static final String LOCATION_CONFIGURATION_FILE_NAME = "jenkins.model.JenkinsLocationConfiguration.xml";

    private String adminAddress;
    private String jenkinsUrl;

    //region Getter
    public String getAdminAddress() {
        return adminAddress;
    }
    public void setAdminAddress(String adminAddress) {
        this.adminAddress = adminAddress;
    }
    public String getJenkinsUrl() {
        return jenkinsUrl;
    }
    public void setJenkinsUrl(String jenkinsUrl) {
        this.jenkinsUrl = jenkinsUrl;
    }
    //endregion

}