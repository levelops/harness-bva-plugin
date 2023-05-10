package io.harness.plugins.harness_bva.plugins;

import hudson.Plugin;
import hudson.util.FormValidation;
import io.harness.plugins.harness_bva.exceptions.EnvironmentVariableNotDefinedException;
import io.harness.plugins.harness_bva.extensions.HarnessMgmtLink;
import io.harness.plugins.harness_bva.internal.JobConfig;
import io.harness.plugins.harness_bva.utils.Utils;
import jenkins.model.Jenkins;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import org.kohsuke.stapler.verb.POST;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static io.harness.plugins.harness_bva.common.Common.DATA_DIR_NAME;


public class HarnessBVAPluginImpl extends Plugin {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    public static final String PLUGIN_SHORT_NAME = "propelo-job-reporter";

    //region Data Members
    private String jenkinsInstanceName = "Jenkins Instance";
    private String pluginPath = "${JENKINS_HOME}/harness-bva";
    private List<JobConfig> buildConfigs = new ArrayList<>();
    private List<JobConfig> deploymentConfigs = new ArrayList<>();
    private List<JobConfig> rollbackConfigs = new ArrayList<>();
    //endregion

    //region CSTOR
    //ToDo: This is deprecated! Fix soon.
    public HarnessBVAPluginImpl() {
    }
    //endregion

    //region Start
    @Override
    public void start() throws Exception {
        super.start();
        load();
        LOGGER.fine("'" + HarnessMgmtLink.PLUGIN_DISPLAY_NAME + "' plugin initialized.");
    }
    //endregion

    //region GetInstance
    public static HarnessBVAPluginImpl getInstance() {
        final Jenkins jenkins = Jenkins.getInstanceOrNull();
        if (jenkins != null) {
            return jenkins.getPlugin(HarnessBVAPluginImpl.class);
        }
        else {
            return null;
        }
    }
    //endregion

    //region HudsonHome
    public File getHudsonHome() {
        Jenkins jenkins = Jenkins.getInstanceOrNull();
        return (jenkins == null) ? null : jenkins.getRootDir();
    }
    //endregion

    //region Getter & Setter
    public String getJenkinsInstanceName() {
        return jenkinsInstanceName;
    }

    public void setJenkinsInstanceName(String jenkinsInstanceName) {
        this.jenkinsInstanceName = jenkinsInstanceName;
    }

    /**
     * Get the Propelo plugin path as entered by the user. May contain environment variables.
     *
     * If you need a path that can be used as is (env. vars expanded), please use @link{getExpandedPluginPath}.
     *
     * @return the path as entered by the user.
     */
    public String getPluginPath() {
        return pluginPath;
    }

    public void setPluginPath(String pluginPath) {
        this.pluginPath = pluginPath;
    }

    public List<JobConfig> getBuildConfigs() {
        return buildConfigs;
    }

    public void setBuildConfigs(List<JobConfig> buildConfigs) {
        this.buildConfigs = buildConfigs;
    }

    public List<JobConfig> getDeploymentConfigs() {
        return deploymentConfigs;
    }

    public void setDeploymentConfigs(List<JobConfig> deploymentConfigs) {
        this.deploymentConfigs = deploymentConfigs;
    }

    public List<JobConfig> getRollbackConfigs() {
        return rollbackConfigs;
    }

    public void setRollbackConfigs(List<JobConfig> rollbackConfigs) {
        this.rollbackConfigs = rollbackConfigs;
    }
    //endregion

    //region ToString
    @Override
    public String toString() {
        return "HarnessBVAPluginImpl{" +
                "jenkinsInstanceName='" + jenkinsInstanceName + '\'' +
                ", pluginPath='" + pluginPath + '\'' +
                ", buildConfigs='" + buildConfigs + '\'' +
                ", deploymentConfigs='" + deploymentConfigs + '\'' +
                ", rollbackConfigs='" + rollbackConfigs + '\'' +
                '}';
    }
    //endregion

    public String getPluginVersionString() {
        LOGGER.log(Level.FINEST, "getPluginVersionString starting");
        String pluginVersionString = "";
                //Jenkins.get().getPluginManager().getPlugin(PropeloPluginImpl.PLUGIN_SHORT_NAME).getVersion();
        LOGGER.log(Level.FINEST, "getPluginVersionString completed pluginVersionString = {0}", pluginVersionString);
        return pluginVersionString;
    }



    //region Dir Functions
    /**
     * @return the pluginPath path with possibly contained environment variables expanded.
     */
    public String getExpandedPluginPath() {
        if (StringUtils.isBlank(pluginPath)) {
            return pluginPath;
        }
        String expandedPath = "";
        try {
            expandedPath = Utils.expandEnvironmentVariables(pluginPath);
        } catch (final EnvironmentVariableNotDefinedException evnde) {
            LOGGER.log(Level.SEVERE, evnde.getMessage() + " Using unexpanded path.");
            expandedPath = pluginPath;
        }

        return expandedPath;
    }

    public File getExpandedPluginDir() {
        return new File(this.getExpandedPluginPath());
    }

    public boolean isExpandedPluginPathNullOrEmpty(){
        return StringUtils.isEmpty(getExpandedPluginPath());
    }
    public File getDataDirectory() {
        return new File(getExpandedPluginDir(), DATA_DIR_NAME);
    }
    //endregion

    //region Checks
    public FormValidation doCheckJenkinsInstanceName(final StaplerRequest res, final StaplerResponse rsp,
                                                     @QueryParameter("value") final String jenkinsInstanceName) {
        if((jenkinsInstanceName == null) || (jenkinsInstanceName.length() == 0)){
            return FormValidation.error("Jenkins Instance Name should not be null or empty.");
        } else {
            return FormValidation.ok();
        }
    }
    @POST
    public FormValidation doCheckPluginPath(final StaplerRequest res, final StaplerResponse rsp,
                                            @QueryParameter("value") final String path) {
        Jenkins.get().checkPermission(Jenkins.ADMINISTER);
        if ((path == null) || path.trim().isEmpty()) {
            return FormValidation.error("Reports path must not be empty.");
        }

        String expandedPathMessage = "";
        String expandedPath = "";
        try {
            expandedPath = Utils.expandEnvironmentVariables(path);
        } catch (final EnvironmentVariableNotDefinedException evnd) {
            return FormValidation.error(evnd.getMessage());
        }
        if (!expandedPath.equals(path)) {
            expandedPathMessage = String.format("The path will be expanded to '%s'.%n%n", expandedPath);
        }

        final File expandedPluginPath = new File(expandedPath);
        if (expandedPluginPath.exists()){
            if (!expandedPluginPath.isDirectory()) {
                return FormValidation.error(expandedPathMessage
                        + "A file with this name exists, thus a directory with the same name cannot be created.");
            }
        } else {
            if (!expandedPath.trim().equals(expandedPath)) {
                return FormValidation.warning(expandedPathMessage
                        + "Path contains leading and/or trailing whitespaces - is this intentional?");
            }
            File dataDirectory = getDataDirectory();
            if(!dataDirectory.exists()) {
                if(!dataDirectory.mkdirs()){
                    return FormValidation.error(dataDirectory + " The data directory could not be created. Please check path and write permissions.");
                }
            }
        }
        if (!expandedPathMessage.isEmpty()) {
            return FormValidation.ok(expandedPathMessage.substring(0, expandedPathMessage.length() - 2));
        } else {
            return FormValidation.ok();
        }
    }

    private FormValidation checkJobConfigs(String jobConfigs) {
        ImmutablePair<List<io.harness.plugins.harness_bva.models.JobConfig>, String> result = io.harness.plugins.harness_bva.models.JobConfig.validate(jobConfigs);
        String error = result.getRight();
        if (StringUtils.isBlank(error)) {
            return FormValidation.ok();
        } else {
            return FormValidation.error(error);
        }
    }
    public FormValidation doCheckBuildJobConfigs(final StaplerRequest res, final StaplerResponse rsp,
                                                     @QueryParameter("value") final String buildJobConfigs) {
        return checkJobConfigs(buildJobConfigs);
    }
    public FormValidation doCheckDeploymentJobConfigs(final StaplerRequest res, final StaplerResponse rsp,
                                                 @QueryParameter("value") final String deploymentJobConfigs) {
        return checkJobConfigs(deploymentJobConfigs);
    }
    public FormValidation doCheckRollbackJobConfigs(final StaplerRequest res, final StaplerResponse rsp,
                                                 @QueryParameter("value") final String rollbackJobConfigs) {
        return checkJobConfigs(rollbackJobConfigs);
    }
    //endregion
}
