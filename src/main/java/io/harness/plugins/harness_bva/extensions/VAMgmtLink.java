package io.harness.plugins.harness_bva.extensions;

import com.fasterxml.jackson.annotation.JsonProperty;
import hudson.Extension;
import hudson.RelativePath;
import hudson.XmlFile;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import hudson.model.ManagementLink;
import hudson.util.FormApply;
import hudson.util.FormValidation;
import io.harness.plugins.harness_bva.plugins.HarnessBVAPluginImpl;
import jenkins.model.Jenkins;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.HttpResponse;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@Extension
public class VAMgmtLink extends ManagementLink  {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    public static final String PLUGIN_NAME = "va-harness-bva";
    public static final String PLUGIN_DISPLAY_NAME = "VA Harness BVA Plugin";
    public static final String PLUGIN_DESCRIPTION = "VA Harness BVA Plugin.";

    //region Data Members
    private Config config;
    //endregion

    //region Override
    @Override
    public String getDisplayName() {
        return PLUGIN_DISPLAY_NAME;
    }
    @Override
    public String getIconFileName() {
        // return Jenkins.get().getRootUrl() + "plugin/propelo-job-reporter/images/48x48/propelo_logo.png";
        // return "propelo_logo.png";
        // return "/plugin/propelo-job-reporter/images/propelo_logo.png";
        return "package.png";
    }
    @Override
    public String getUrlName() {
        return PLUGIN_NAME;
    }
    @Override
    public String getDescription() {
        return PLUGIN_DESCRIPTION;
    }
    //endregion

    //region Getter & Setter
    public Config getConfig() {
        return config;
    }
    public void setConfig(Config config) {
        this.config = config;
    }

    //endregion


    public XmlFile getConfigFile() {
        return new XmlFile(new File(Jenkins.get().getRootDir(), "stuff.xml"));
    }
    public VAMgmtLink() throws IOException {
        XmlFile xml = getConfigFile();
        if (xml.exists()) {
            xml.unmarshal(this);
        }
    }

    public HttpResponse doConfigSubmit(StaplerRequest req) throws ServletException, IOException {
        config = null; // otherwise bindJSON will never clear it once set
        req.bindJSON(this, req.getSubmittedForm());
        getConfigFile().write(this);
        return FormApply.success(".");
    }

    public static final class Config extends AbstractDescribableImpl<Config> {

        private String jenkinsInstanceName = "Jenkins Instance";
        private String pluginPath = "${JENKINS_HOME}/harness-bva";
        private List<JobConfigDAO> servers = new ArrayList<>();

        @DataBoundConstructor
        public Config(String jenkinsInstanceName, String pluginPath, List<JobConfigDAO> servers) {
            this.jenkinsInstanceName = jenkinsInstanceName;
            this.pluginPath = pluginPath;
            this.servers = servers;
        }

        public String getJenkinsInstanceName() {
            return jenkinsInstanceName;
        }

        public String getPluginPath() {
            return pluginPath;
        }

        public List<JobConfigDAO> getServers() {
            return servers;
        }

        @Extension public static class DescriptorImpl extends Descriptor<Config> {}
    }

    public static class JobConfigDAO extends AbstractDescribableImpl<JobConfigDAO> {
        @JsonProperty("jobName")
        public String jobName;
        @JsonProperty("paramName")
        public String paramName;
        @JsonProperty("paramValue")
        public String paramValue;

        @DataBoundConstructor
        public JobConfigDAO(String jobName, String paramName, String paramValue) {
            this.jobName = jobName;
            this.paramName = paramName;
            this.paramValue = paramValue;
        }

        public String getJobName() {
            return jobName;
        }
        public String getParamName() {
            return paramName;
        }
        public String getParamValue() {
            return paramValue;
        }

        @Extension
        public static class DescriptorImpl extends Descriptor<JobConfigDAO> {
            public FormValidation doCheckJobName(@QueryParameter String value) {
                return FormValidation.ok();
            }
            public FormValidation doCheckParamName(@QueryParameter String value) {
                return FormValidation.ok();
            }
            public FormValidation doCheckParamValue(@QueryParameter String value) {
                return FormValidation.ok();
            }
        }
    }
}

