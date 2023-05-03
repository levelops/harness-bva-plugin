package io.harness.plugins.harness_bva.extensions;

import hudson.Extension;
import hudson.model.Hudson;
import hudson.model.ManagementLink;
import hudson.util.Secret;
import io.harness.plugins.harness_bva.plugins.HarnessBVAPluginImpl;
import jenkins.model.Jenkins;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import org.kohsuke.stapler.verb.POST;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.logging.Level;
import java.util.logging.Logger;

@Extension
public class HarnessMgmtLink extends ManagementLink {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    public static final String PLUGIN_NAME = "harness-bva";
    public static final String PLUGIN_DISPLAY_NAME = "Harness BVA Plugin";
    public static final String PLUGIN_DESCRIPTION = "Harness BVA Plugin.";

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

    @POST
    public void doSaveSettings(final StaplerRequest res, final StaplerResponse rsp,
                               @QueryParameter("pluginPath") final String pluginPath,
                               @QueryParameter("jenkinsInstanceName") final String jenkinsInstanceName,
                               @QueryParameter("buildJobConfigs") final String buildJobConfigs,
                               @QueryParameter("deploymentJobConfigs") final String deploymentJobConfigs,
                               @QueryParameter("rollbackJobConfigs") final String rollbackJobConfigs
    ) throws IOException {
        LOGGER.log(Level.FINE, "Starting doSaveSettings, pluginPath = {0}, jenkinsInstanceName = {1}, buildJobConfigs = {2}, deploymentJobConfigs = {3}, rollbackJobConfigs = {4}",
                new Object[] { pluginPath, jenkinsInstanceName, buildJobConfigs, deploymentJobConfigs, rollbackJobConfigs });

        Hudson.getInstance().checkPermission(Hudson.ADMINISTER);

        final HarnessBVAPluginImpl plugin = HarnessBVAPluginImpl.getInstance();
        plugin.setPluginPath(pluginPath);
        plugin.setJenkinsInstanceName(jenkinsInstanceName);
        plugin.setBuildJobConfigs(buildJobConfigs);
        plugin.setDeploymentJobConfigs(deploymentJobConfigs);
        plugin.setRollbackJobConfigs(rollbackJobConfigs);
        plugin.save();
        LOGGER.log(Level.CONFIG, "Saving plugin settings done. plugin = {0}", plugin);
        rsp.sendRedirect(res.getContextPath() + "/" + PLUGIN_NAME);
    }

    public HarnessBVAPluginImpl getConfiguration() {
        return HarnessBVAPluginImpl.getInstance();
    }
}
