package io.harness.plugins.harness_bva.services;

import hudson.model.Run;
import io.harness.plugins.harness_bva.models.JobRunDetail;
import io.harness.plugins.harness_bva.models.JobRunDetailLite;
import io.harness.plugins.harness_bva.models.JobType;
import io.harness.plugins.harness_bva.models.Report;
import io.harness.plugins.harness_bva.plugins.HarnessBVAPluginImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.time.Instant;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JobRunProcessorService {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    public void processJobRun(Run run, File hudsonHome, HarnessBVAPluginImpl plugin) {
        Instant now = Instant.now();

        //Step 1 : Parse Job Run
        JobRunParserService jobRunParserService = new JobRunParserService();
        JobRunDetail jobRunDetail = jobRunParserService.parseJobRun(run, hudsonHome);
        if (jobRunDetail == null) {
            return;
        }
        LOGGER.log(Level.FINE, "Starting processing complete event jobFullName={0}, build number = {1}", new Object[]{jobRunDetail.getJobFullName(), jobRunDetail.getBuildNumber()});
        LOGGER.finest("jobRunDetail = " + jobRunDetail);

        //Step 2: Persist Heartbeat
        HeartbeatService heartbeatService = new HeartbeatService(plugin.getExpandedPluginDir());
        heartbeatService.writeHeartBeat(now.getEpochSecond());

        //Step 3: Persist Job Run Details
        LOGGER.info("JobRunPersistanceService Starting");
        JobRunPersistanceService jobRunPersistanceService = new JobRunPersistanceService();
        jobRunPersistanceService.persistJobRun(jobRunDetail,plugin, now);
        LOGGER.info("JobRunPersistanceService Completed");
    }


    private void generateReportForJobTye(File expandedPluginDir, JobType jobType, Instant now, ReportProcessor reportProcessor) {
        JobRunStorageService jobRunStorageService = new JobRunStorageService(expandedPluginDir);
        List<File> files = jobRunStorageService.listFilesNewerThan(jobType, now, 7);
        LOGGER.log(Level.INFO, "Listing Files jobType={0}, files = {1}", new Object[]{jobType.getFilePrefix(), files});
        for (File f : files) {
            LOGGER.log(Level.INFO, "Processing File jobType={0}, file = {1}", new Object[]{jobType.getFilePrefix(), f.toString()});
            try {
                try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        // process the line.
                        LOGGER.log(Level.INFO, "Processing Line jobType={0}, file = {1}, line = {2}", new Object[]{jobType.getFilePrefix(), f.toString(), line});
                        JobRunDetailLite jobRunDetailLite = JobRunDetailLite.fromCSVString(line);
                        LOGGER.log(Level.INFO, "Processing Obj jobType={0}, file = {1}, obj = {2}", new Object[]{jobType.getFilePrefix(), f.toString(), jobRunDetailLite});
                        reportProcessor.append(jobType, jobRunDetailLite);
                    }
                }
            } catch (IOException e) {
                //log
            }
        }
    }
    public Report generateReport(File expandedPluginDir, Instant now) {
        ReportProcessor reportProcessor = new ReportProcessor();
        for (JobType j : JobType.values()) {
            generateReportForJobTye(expandedPluginDir, j, now, reportProcessor);
        }
        Report report = reportProcessor.build();
        return report;
    }
}
