package io.harness.plugins.harness_bva.services;

import io.harness.plugins.harness_bva.models.JenkinsConfig;
import io.harness.plugins.harness_bva.models.JenkinsGeneralConfig;
import io.harness.plugins.harness_bva.models.JenkinsLocationConfig;
import io.harness.plugins.harness_bva.models.LevelOpsJenkinsData;
import io.harness.plugins.harness_bva.models.LevelOpsJenkinsReport;
import io.harness.plugins.harness_bva.models.Plugin;
import io.harness.plugins.harness_bva.models.User;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static io.harness.plugins.harness_bva.common.Common.LEVELOPS_JENKINS_HTML_REPORT_FILE_NAME;
import static io.harness.plugins.harness_bva.common.Common.LEVELOPS_JENKINS_HTML_REPORT_FILE_NAME_TEMP;
import static io.harness.plugins.harness_bva.services.JenkinsUserService.USERS_LIST_DIR;

public class HudsonMonitoring {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    private final File expandedPluginDir;
    private final File hudsonHome;
    private final JenkinsConfigService jenkinsConfigService;
    private final JenkinsPluginService jenkinsPluginService;


    public HudsonMonitoring(File expandedPluginDir, File hudsonHome, JenkinsConfigService jenkinsConfigService, JenkinsPluginService jenkinsPluginService) {
        this.expandedPluginDir = expandedPluginDir;
        this.hudsonHome = hudsonHome;
        this.jenkinsConfigService = jenkinsConfigService;
        this.jenkinsPluginService = jenkinsPluginService;
    }


    public void monitor()  {
        LOGGER.finest("Performing monitoring.");
        LevelOpsJenkinsData levelOpsJenkinsData = createAndPersistMonitoringReport();
    }

    private LevelOpsJenkinsData createAndPersistMonitoringReport() {
        JenkinsGeneralConfig jenkinsGeneralConfig = monitorGlobalXmls();
        List<User> users =  monitorUsersList();
        List<Plugin> plugins = monitorPlugins();

        LevelOpsJenkinsData levelOpsJenkinsData = LevelOpsJenkinsData.builder()
                .config(jenkinsGeneralConfig).users(users).plugins(plugins).build();
        LOGGER.log(Level.FINEST, "levelOpsJenkinsData = {0}", levelOpsJenkinsData);

        LevelOpsJenkinsReportGenerationService generationService = LevelOpsJenkinsReportGenerationService.builder()
                .jenkinsGeneralConfig(jenkinsGeneralConfig).users(users).plugins(plugins).build();

        LevelOpsJenkinsReport report = generationService.generateReport();
        LOGGER.log(Level.FINEST, "report = {0}", report);
        persistReport(report);
        return levelOpsJenkinsData;
    }

    private void persistReport(LevelOpsJenkinsReport report) {
        if (report == null) {
            LOGGER.log(Level.SEVERE, "LevelOpsJenkinsReport is null will not persist!");
            return;
        }
        File reportDir = new File(this.expandedPluginDir, "reports");
        File htmlReportFileTemp = new File(reportDir, LEVELOPS_JENKINS_HTML_REPORT_FILE_NAME_TEMP);
        LevelOpsJenkinsReportOutputService outputService = LevelOpsJenkinsReportOutputService.builder().setReport(report).build();
        try {
            outputService.outputHtml(htmlReportFileTemp);
            LOGGER.finest("Successfully wrote temp report file = " + htmlReportFileTemp);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error writing temp Jenkins Report Html!", e);
            return;
        }

        File htmlReportFile = new File(reportDir, LEVELOPS_JENKINS_HTML_REPORT_FILE_NAME);
        try {
            Files.move(htmlReportFileTemp.toPath(), htmlReportFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            LOGGER.finest("Successfully moved report file to = " + htmlReportFile);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error writing final Jenkins Report Html!", e);
        }
    }

    private List<Plugin> monitorPlugins(){
        LOGGER.finest("Monitoring plugins...");
        File pluginsDirectory = new File(hudsonHome, "plugins");
        List<Plugin> plugins = jenkinsPluginService.parsePlugins(pluginsDirectory.toPath());
        LOGGER.finest("DONE Monitoring plugins...");
        return plugins;
    }

    private JenkinsGeneralConfig monitorGlobalXmls() {
        LOGGER.finest("Monitoring global configuration files...");

        File globalConfigFile = new File(hudsonHome, "config.xml");
        File killSwitchFile = new File(hudsonHome, JenkinsConfig.SLAVE_TO_MASTER_ACCESS_CONTROL_FILE);
        File locationConfigurationFile = new File(hudsonHome, JenkinsLocationConfig.LOCATION_CONFIGURATION_FILE_NAME);

        JenkinsGeneralConfig jenkinsGeneralConfig = jenkinsConfigService.readConfig(globalConfigFile.toPath(), killSwitchFile.toPath(), locationConfigurationFile.toPath());
        LOGGER.log(Level.FINEST, "jenkinsGeneralConfig = {0}", jenkinsGeneralConfig);
        LOGGER.fine("DONE backing up global configuration files.");
        return jenkinsGeneralConfig;
    }

    private List<User> monitorUsersList() {
        LOGGER.finest("Monitoring Users List...");
        JenkinsUserService userService = new JenkinsUserService();
        List<User> users = userService.readUsers(Paths.get(hudsonHome.getAbsolutePath(), USERS_LIST_DIR));
        LOGGER.finest("DONE Monitoring Users List...");
        return users;
    }
}
