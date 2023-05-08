package io.harness.plugins.harness_bva.services;

import io.harness.plugins.harness_bva.internal.JobConfig;
import io.harness.plugins.harness_bva.models.JobRunDetail;
import io.harness.plugins.harness_bva.models.JobRunDetailLite;
import io.harness.plugins.harness_bva.models.JobType;
import io.harness.plugins.harness_bva.plugins.HarnessBVAPluginImpl;
import org.apache.commons.collections.CollectionUtils;

import java.lang.invoke.MethodHandles;
import java.time.Instant;
import java.util.List;
import java.util.logging.Logger;

public class JobRunPersistanceService {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    private void persistBuildJobRun(JobRunDetailLite jobRunDetailLite, List<JobConfig> jobConfigs, JobType jobType, Instant runCompletionTime, JobRunStorageService jobRunStorageService) {
        List<io.harness.plugins.harness_bva.models.JobConfig> jcs = io.harness.plugins.harness_bva.models.JobConfig.fromDAO(jobConfigs);
        if (CollectionUtils.isEmpty(jcs)) {
            return;
        }
        if (! JobMatcherService.doesMatch(jcs, jobRunDetailLite.getJobName(), jobRunDetailLite.getJobRunParams()) ) {
            return;
        }
        jobRunStorageService.write(jobType, runCompletionTime, jobRunDetailLite);
    }

    private void persistBuildJobRun(JobRunDetailLite jobRunDetailLite, HarnessBVAPluginImpl plugin, Instant runCompletionTime, JobRunStorageService jobRunStorageService) {
        persistBuildJobRun(jobRunDetailLite, plugin.getBuildConfigs(), JobType.BUILD, runCompletionTime, jobRunStorageService);
    }
    private void persistDeploymentJobRun(JobRunDetailLite jobRunDetailLite, HarnessBVAPluginImpl plugin, Instant runCompletionTime, JobRunStorageService jobRunStorageService) {
        persistBuildJobRun(jobRunDetailLite, plugin.getDeploymentConfigs(), JobType.DEPLOYMENT, runCompletionTime, jobRunStorageService);
    }
    private void persistRollbackJobRun(JobRunDetailLite jobRunDetailLite, HarnessBVAPluginImpl plugin, Instant runCompletionTime, JobRunStorageService jobRunStorageService) {
        persistBuildJobRun(jobRunDetailLite, plugin.getRollbackConfigs(), JobType.ROLLBACK, runCompletionTime, jobRunStorageService);
    }
    public void persistJobRun(JobRunDetail jobRunDetail, HarnessBVAPluginImpl plugin, Instant runCompletionTime) {
        JobRunStorageService jobRunStorageService = new JobRunStorageService(plugin.getExpandedPluginDir());

        JobRunDetailLite jobRunDetailLite = JobRunDetailLite.fromJobRunDetail(jobRunDetail);
        persistBuildJobRun(jobRunDetailLite, plugin, runCompletionTime, jobRunStorageService);
        persistDeploymentJobRun(jobRunDetailLite, plugin, runCompletionTime, jobRunStorageService);
        persistRollbackJobRun(jobRunDetailLite, plugin, runCompletionTime, jobRunStorageService);
    }
}
