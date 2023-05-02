package io.harness.plugins.harness_bva.extensions;

import hudson.model.Hudson;
import hudson.model.ManagementLink;
import hudson.util.Secret;
import io.harness.plugins.harness_bva.plugins.PropeloPluginImpl;
import jenkins.model.Jenkins;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import org.kohsuke.stapler.verb.POST;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                               @QueryParameter("levelOpsApiKey") final String levelOpsApiKey,
                               @QueryParameter("levelOpsPluginPath") final String levelOpsPluginPath,
                               @QueryParameter("jenkinsBaseUrl") final String jenkinsBaseUrl,
                               @QueryParameter("jenkinsUserName") final String jenkinsUserName,
                               @QueryParameter("jenkinsUserToken") final String jenkinsUserToken,
                               @QueryParameter("bullseyeXmlResultPaths") final String bullseyeXmlResultPaths,
                               @QueryParameter("productIds") final String productIds,
                               @QueryParameter("jenkinsInstanceName") final String jenkinsInstanceName,
                               @QueryParameter("trustAllCertificates") final boolean trustAllCertificates
    ) throws IOException {
        LOGGER.log(Level.FINE, "Starting doSaveSettings, levelOpsApiKey = {0}, levelOpsPluginPath = {1}, " +
                        "jenkinsBaseUrl = {2}, jenkinsUserName = {3}, jenkinsUserToken = {4}, productIds = {5}, jenkinsInstanceName = {6}, trustAllCertificates = {7}, bullseyeXmlResultPaths = {8}",
                new Object[] {levelOpsApiKey, levelOpsPluginPath, jenkinsBaseUrl, jenkinsUserName, jenkinsUserToken, productIds, jenkinsInstanceName, trustAllCertificates,bullseyeXmlResultPaths});

        Hudson.getInstance().checkPermission(Hudson.ADMINISTER);

        final PropeloPluginImpl plugin = PropeloPluginImpl.getInstance();
        plugin.setLevelOpsApiKey(Secret.fromString(levelOpsApiKey));
        plugin.setLevelOpsPluginPath(levelOpsPluginPath);
        plugin.setJenkinsBaseUrl(Jenkins.get().getRootUrl());
        plugin.setJenkinsUserName(jenkinsUserName);
        plugin.setJenkinsUserToken(Secret.fromString(jenkinsUserToken));
        plugin.setBullseyeXmlResultPath(bullseyeXmlResultPaths);
        plugin.setProductIds(productIds);
        plugin.setJenkinsInstanceName(jenkinsInstanceName);
        plugin.setTrustAllCertificates(trustAllCertificates);
        plugin.save();
        LOGGER.log(Level.CONFIG, "Saving plugin settings done. plugin = {0}", plugin);
        rsp.sendRedirect(res.getContextPath() + "/" + PLUGIN_NAME);
    }

    public PropeloPluginImpl getConfiguration() {
        return PropeloPluginImpl.getInstance();
    }

    public String getJenkinsStatus() {
        return getConfiguration().getJenkinsStatus();
    }
}
