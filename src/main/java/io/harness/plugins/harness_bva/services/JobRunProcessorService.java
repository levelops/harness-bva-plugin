package io.harness.plugins.harness_bva.services;

import hudson.model.Run;
import io.harness.plugins.harness_bva.models.JobRunDetail;
import io.harness.plugins.harness_bva.plugins.HarnessBVAPluginImpl;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.time.Instant;
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
}
