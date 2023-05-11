package io.harness.plugins.harness_bva.extensions;

import hudson.Extension;
import hudson.model.Hudson;
import hudson.model.ManagementLink;
import io.harness.plugins.harness_bva.internal.Sites;
import io.harness.plugins.harness_bva.internal.JobConfig;
import io.harness.plugins.harness_bva.models.Report;
import io.harness.plugins.harness_bva.plugins.HarnessBVAPluginImpl;
import io.harness.plugins.harness_bva.services.HeartbeatService;
import io.harness.plugins.harness_bva.services.HudsonMonitoring;
import io.harness.plugins.harness_bva.services.JenkinsConfigService;
import io.harness.plugins.harness_bva.services.JenkinsPluginService;
import io.harness.plugins.harness_bva.services.JobRunProcessorService;
import io.harness.plugins.harness_bva.utils.FileUtils;
import io.harness.plugins.harness_bva.utils.JsonUtils;
import jenkins.model.Jenkins;
import org.apache.commons.lang3.StringUtils;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import org.kohsuke.stapler.verb.POST;

import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static io.harness.plugins.harness_bva.common.Common.HARNESS_BVA_JOBS_REPORT_FILE_NAME;
import static io.harness.plugins.harness_bva.common.Common.LEVELOPS_JENKINS_HTML_REPORT_FILE_NAME;
import static io.harness.plugins.harness_bva.common.Common.UTF_8;

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
                               @Sites Map<String,List<JobConfig>> parsed
    ) throws IOException {
        LOGGER.log(Level.FINE, "Starting doSaveSettings, pluginPath = {0}, jenkinsInstanceName = {1}",
                new Object[] { pluginPath, jenkinsInstanceName });

        Hudson.getInstance().checkPermission(Hudson.ADMINISTER);

        final HarnessBVAPluginImpl plugin = HarnessBVAPluginImpl.getInstance();
        plugin.setPluginPath(pluginPath);
        plugin.setBuildConfigs(parsed.get("build"));
        plugin.setDeploymentConfigs(parsed.get("deployment"));
        plugin.setRollbackConfigs(parsed.get("rollback"));
        plugin.save();
        LOGGER.log(Level.CONFIG, "Saving plugin settings done. plugin = {0}", plugin);

        final HarnessBVAPluginImpl pluginRead = HarnessBVAPluginImpl.getInstance();
        rsp.sendRedirect(res.getContextPath() + "/" + PLUGIN_NAME);
    }

    @POST
    public void doSaveHeartbeat(final StaplerRequest res, final StaplerResponse rsp) throws IOException {
        rsp.sendRedirect(res.getContextPath() + "/" + PLUGIN_NAME);
    }

    public void doDownloadJobsReport(final StaplerRequest res, final StaplerResponse rsp) throws IOException {
        LOGGER.finest("Starting download report.");

        final HarnessBVAPluginImpl plugin = HarnessBVAPluginImpl.getInstance();

        File reportsDirectory = new File(plugin.getExpandedPluginPath(), "reports");
        FileUtils.createDirectoryRecursively(reportsDirectory);

        File reportsPath = new File(reportsDirectory, HARNESS_BVA_JOBS_REPORT_FILE_NAME);
        LOGGER.finest("reportsPath = " + reportsPath.toString());

        JobRunProcessorService jobRunProcessorService = new JobRunProcessorService();
        Report report = jobRunProcessorService.generateReport(plugin.getExpandedPluginDir(), Instant.now());
        String reportString = JsonUtils.get().writeValueAsString(report);
        Files.write(reportsPath.toPath(), reportString.getBytes(UTF_8));

        try {
            rsp.serveFile(res, reportsPath.toURL());
            return;
        } catch (ServletException e) {
            LOGGER.log(Level.SEVERE,"ServletException trying to download the report!!", e );
        }
    }

    private File getHudsonHome() {
        Jenkins jenkins = Jenkins.getInstanceOrNull();
        return (jenkins == null) ? null : jenkins.getRootDir();
    }

    public void doDownloadSecurityReport(final StaplerRequest res, final StaplerResponse rsp) throws IOException {
        LOGGER.info("Starting download report.");


        final HarnessBVAPluginImpl plugin = HarnessBVAPluginImpl.getInstance();
        if(StringUtils.isBlank(plugin.getPluginPath())) {
            LOGGER.log(Level.SEVERE, "LevelOps Plugin Directory is invalid, cannot download report! path: ");
            return;
        }
        try {
            HudsonMonitoring hudsonMonitoring = new HudsonMonitoring(plugin.getExpandedPluginDir(), getHudsonHome(), JenkinsConfigService.getInstance(), JenkinsPluginService.getInstance());
            hudsonMonitoring.monitor();
        } catch (Exception e){
            LOGGER.log(Level.SEVERE, "Exception", e);
        }

        File reportsDir = new File(plugin.getExpandedPluginDir(), "reports");
        File reportsPath = new File(reportsDir, LEVELOPS_JENKINS_HTML_REPORT_FILE_NAME);
        LOGGER.info("reportsPath = " + reportsPath.toString());
        boolean reportExists = reportsPath.exists();
        LOGGER.info("reportExists = " + reportExists);
        if(!reportExists){
            return;
        }
        try {
            rsp.serveFile(res, reportsPath.toURL());
            return;
        } catch (ServletException e) {
            LOGGER.log(Level.SEVERE,"ServletException trying to download the report!!", e );
        }
    }

    public HarnessBVAPluginImpl getConfiguration() {
        return HarnessBVAPluginImpl.getInstance();
    }

    public String getHeartbeatStatus() {
        HarnessBVAPluginImpl  plugin = getConfiguration();
        HeartbeatService hbService = new HeartbeatService(plugin.getExpandedPluginDir());
        Long latestHeartbeat = hbService.readLatestHeartBeat();
        if (latestHeartbeat == null) {
            return "UNKNOWN";
        }
        Instant latest = Instant.ofEpochSecond(latestHeartbeat);
        if (latest == null) {
            return "UNKNOWN";
        }
        return latest.toString();
    }
}
