package io.harness.plugins.harness_bva.extensions;

import com.google.common.base.Predicate;
import hudson.Extension;
import hudson.model.Hudson;
import hudson.model.ManagementLink;
import io.harness.plugins.harness_bva.internal.Sites;
import io.harness.plugins.harness_bva.internal.JobConfig;
import io.harness.plugins.harness_bva.plugins.HarnessBVAPluginImpl;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import org.kohsuke.stapler.verb.POST;

import javax.annotation.Nullable;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Map;
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
    public List<JobConfig> getBuildConfigsList() {
        List<JobConfig> abc = HarnessBVAPluginImpl.getInstance().getBuildConfigs();
        LOGGER.log(Level.SEVERE, "Imp getBuildConfigsList = {0}",
                new Object[] { abc });
        return abc;
    }
    public List<JobConfig> getDeploymentConfigsList() {
        List<JobConfig> abc = HarnessBVAPluginImpl.getInstance().getDeploymentConfigs();
        LOGGER.log(Level.SEVERE, "Imp getDeploymentConfigsList = {0}",
                new Object[] { abc });
        return abc;
    }
    public List<JobConfig> getRollbackConfigsList() {
        List<JobConfig> abc = HarnessBVAPluginImpl.getInstance().getRollbackConfigs();
        LOGGER.log(Level.SEVERE, "Imp getRollbackConfigsList = {0}",
                new Object[] { abc });
        return abc;
    }

    public List<DescribedJobConfigDescriptor> getUpdateSiteDescriptorList() {
        return DescribedJobConfig.all();
    }
    @POST
    public void doSaveSettings(final StaplerRequest res, final StaplerResponse rsp,
                               @QueryParameter("pluginPath") final String pluginPath,
                               @QueryParameter("jenkinsInstanceName") final String jenkinsInstanceName,
                               @QueryParameter("buildJobConfigs") final String buildJobConfigs,
                               @QueryParameter("deploymentJobConfigs") final String deploymentJobConfigs,
                               @QueryParameter("rollbackJobConfigs") final String rollbackJobConfigs,
                               @Sites Map<String,List<JobConfig>> parsed
    ) throws IOException {
        /*
        Map<String, String[]> abc = res.getParameterMap();
        String data = JsonUtils.get().writeValueAsString(abc);
        LOGGER.log(Level.SEVERE, "Imp doSaveSettings, data = {0}",
                new Object[] { data });
        String json = abc.getOrDefault("json", new String[0])[0];
        String data2 = JsonUtils.get().writeValueAsString(json);
        LOGGER.log(Level.SEVERE, "Imp doSaveSettings, data2 = {0}",
                new Object[] { data2 });
        HarnessBVAPluginImpl copy = JsonUtils.get().readValue(json, HarnessBVAPluginImpl.class);
        LOGGER.log(Level.SEVERE, "Imp doSaveSettings, copy = {0}",
                new Object[] { copy });

        LOGGER.log(Level.SEVERE, "Imp doSaveSettings, build = {0}",
                new Object[] { parsed.get("build") });
        LOGGER.log(Level.SEVERE, "Imp doSaveSettings, deployment = {0}",
                new Object[] { parsed.get("deployment") });
        LOGGER.log(Level.SEVERE, "Imp doSaveSettings, rollback = {0}",
                new Object[] { parsed.get("rollback") });
         */


        LOGGER.log(Level.FINE, "Starting doSaveSettings, pluginPath = {0}, jenkinsInstanceName = {1}, buildJobConfigs = {2}, deploymentJobConfigs = {3}, rollbackJobConfigs = {4}",
                new Object[] { pluginPath, jenkinsInstanceName, buildJobConfigs, deploymentJobConfigs, rollbackJobConfigs });

        Hudson.getInstance().checkPermission(Hudson.ADMINISTER);

        final HarnessBVAPluginImpl plugin = HarnessBVAPluginImpl.getInstance();
        plugin.setPluginPath(pluginPath);
        plugin.setJenkinsInstanceName(jenkinsInstanceName);
        plugin.setBuildJobConfigs(buildJobConfigs);
        plugin.setDeploymentJobConfigs(deploymentJobConfigs);
        plugin.setRollbackJobConfigs(rollbackJobConfigs);
        plugin.setBuildConfigs(parsed.get("build"));
        plugin.setDeploymentConfigs(parsed.get("deployment"));
        plugin.setRollbackConfigs(parsed.get("rollback"));
        plugin.save();
        LOGGER.log(Level.CONFIG, "Saving plugin settings done. plugin = {0}", plugin);

        final HarnessBVAPluginImpl pluginRead = HarnessBVAPluginImpl.getInstance();
//        LOGGER.log(Level.SEVERE, "Imp doSaveSettings, read servers = {0}",
//                new Object[] { pluginRead.getServers() });
        rsp.sendRedirect(res.getContextPath() + "/" + PLUGIN_NAME);
    }

    public HarnessBVAPluginImpl getConfiguration() {
        return HarnessBVAPluginImpl.getInstance();
    }
}
