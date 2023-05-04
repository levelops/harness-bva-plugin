package io.harness.plugins.harness_bva.plugins;

import hudson.Plugin;
import hudson.util.FormValidation;
import hudson.util.Secret;
import io.harness.plugins.harness_bva.exceptions.EnvironmentVariableNotDefinedException;
import io.harness.plugins.harness_bva.extensions.HarnessMgmtLink;
import io.harness.plugins.harness_bva.models.JobConfig;
import io.harness.plugins.harness_bva.utils.DateUtils;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static io.harness.plugins.harness_bva.common.Common.REPORTS_DIR_NAME;

public class HarnessBVAPluginImpl extends Plugin {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    private static final String DATA_DIR_NAME = "run-complete-data";
    public static final String PLUGIN_SHORT_NAME = "propelo-job-reporter";

    //region Data Members
    private String jenkinsInstanceName = "Jenkins Instance";
    private String pluginPath = "${JENKINS_HOME}/harness-bva";
    private String buildJobConfigs = "";
    private String deploymentJobConfigs = "";
    private String rollbackJobConfigs = "";
    private long heartbeatDuration = 60;
    private long configUpdatedAt = System.currentTimeMillis();
    //endregion

    //ToDo: This is deprecated! Fix soon.
    public HarnessBVAPluginImpl() {
    }

    @Override
    public void start() throws Exception {
        super.start();
        load();
        LOGGER.fine("'" + HarnessMgmtLink.PLUGIN_DISPLAY_NAME + "' plugin initialized.");
    }

    public static HarnessBVAPluginImpl getInstance() {
        final Jenkins jenkins = Jenkins.getInstanceOrNull();
        if (jenkins != null) {
            return jenkins.getPlugin(HarnessBVAPluginImpl.class);
        }
        else {
            return null;
        }
    }

    public File getHudsonHome() {
        Jenkins jenkins = Jenkins.getInstanceOrNull();
        return (jenkins == null) ? null : jenkins.getRootDir();
    }

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

    public String getBuildJobConfigs() {
        return buildJobConfigs;
    }

    public void setBuildJobConfigs(String buildJobConfigs) {
        this.buildJobConfigs = buildJobConfigs;
    }

    public String getDeploymentJobConfigs() {
        return deploymentJobConfigs;
    }

    public void setDeploymentJobConfigs(String deploymentJobConfigs) {
        this.deploymentJobConfigs = deploymentJobConfigs;
    }

    public String getRollbackJobConfigs() {
        return rollbackJobConfigs;
    }

    public void setRollbackJobConfigs(String rollbackJobConfigs) {
        this.rollbackJobConfigs = rollbackJobConfigs;
    }

    public void setPluginPath(String pluginPath) {
        this.pluginPath = pluginPath;
    }

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

    public long getConfigUpdatedAt() {
        return configUpdatedAt;
    }

    public void setConfigUpdatedAt(long configUpdatedAt) {
        this.configUpdatedAt = configUpdatedAt;
    }

    public long getHeartbeatDuration() {
        return heartbeatDuration;
    }

    public void setHeartbeatDuration(long heartbeatDuration) {
        this.heartbeatDuration = heartbeatDuration;
    }

    public File getExpandedPluginDir() {
        return new File(this.getExpandedPluginPath());
    }

    public boolean isExpandedPluginPathNullOrEmpty(){
        return StringUtils.isEmpty(getExpandedPluginPath());
    }

    private File buildReportsDirectory(String pluginPath){
        return new File(pluginPath,REPORTS_DIR_NAME);
    }
    public File getReportsDirectory() {
        return buildReportsDirectory(this.getExpandedPluginPath());
    }
    //endregion

    public String getPluginVersionString() {
        LOGGER.log(Level.FINEST, "getPluginVersionString starting");
        String pluginVersionString = "";
                //Jenkins.get().getPluginManager().getPlugin(PropeloPluginImpl.PLUGIN_SHORT_NAME).getVersion();
        LOGGER.log(Level.FINEST, "getPluginVersionString completed pluginVersionString = {0}", pluginVersionString);
        return pluginVersionString;
    }

    private File buildDataDirectory(String pluginPath) {
        LOGGER.log(Level.FINEST, "buildDataDirectory starting");
        File dataDirectory = new File(pluginPath, DATA_DIR_NAME);
        LOGGER.log(Level.FINEST, "buildDataDirectory completed = {0}", dataDirectory);
        return dataDirectory;
    }
    public File getDataDirectory() {
        return buildDataDirectory(this.getExpandedPluginPath());
    }

    private File buildDataDirectoryWithVersion(String pluginPath) {
        LOGGER.log(Level.FINEST, "buildDataDirectoryWithVersion starting");
        String dataDirWithVersionName = DATA_DIR_NAME + "-" + getPluginVersionString();
        LOGGER.log(Level.FINEST, "dataDirWithVersionName = {0}", dataDirWithVersionName);
        File dataDirectoryWithVersion = new File(pluginPath, dataDirWithVersionName);
        LOGGER.log(Level.FINEST, "buildDataDirectoryWithVersion completed = {0}", dataDirectoryWithVersion);
        return dataDirectoryWithVersion;
    }
    public File getDataDirectoryWithVersion() {
        return buildDataDirectoryWithVersion(this.getExpandedPluginPath());
    }

    public File getDataDirectoryWithRotation() {
        File dataDirWithVersion = buildDataDirectoryWithVersion(this.getExpandedPluginPath());
        LOGGER.log(Level.FINEST, "dataDirWithVersion = {0}", dataDirWithVersion);
        File dataDirWithRotation = new File(dataDirWithVersion, DateUtils.getDateFormattedDirName());
        LOGGER.log(Level.FINEST, "dataDirWithRotation = {0}", dataDirWithRotation);
        return dataDirWithRotation;
    }

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
            File reportsDirectory = buildReportsDirectory(expandedPath);
            if(!reportsDirectory.exists()) {
                if(!reportsDirectory.mkdirs()){
                    return FormValidation.error(reportsDirectory + " The Reports directory could not be created. Please check path and write permissions.");
                }
            }
            File dataDirectoryWithVersion = buildDataDirectoryWithVersion(expandedPath);
            if(!dataDirectoryWithVersion.exists()) {
                if(!dataDirectoryWithVersion.mkdirs()){
                    return FormValidation.error(dataDirectoryWithVersion + " The data directory with version could not be created. Please check path and write permissions.");
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
        ImmutablePair<List<JobConfig>, String> result = JobConfig.validate(jobConfigs);
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
