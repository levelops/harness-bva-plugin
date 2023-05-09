package io.harness.plugins.harness_bva.services;

import io.harness.plugins.harness_bva.models.JobRunDetailLite;
import io.harness.plugins.harness_bva.models.JobType;
import io.harness.plugins.harness_bva.models.Report;

public class ReportProcessor {
    int buildJobRunsCount = 0;
    int deploymentJobRunsCount = 0;
    int rollbackJobRunsCount = 0;

    int buildSuccessfulJobRunsCount = 0;
    int buildFailedJobRunsCount = 0;

    long buildTimeTotal = 0;
    int buildsCount = 0;

    int manualTriggerBuildsCount = 0;
    int automatedTriggerBuildsCount = 0;

    public void append(JobType jobType, JobRunDetailLite jobRunDetailLite) {
        if (jobRunDetailLite == null) {
            return;
        }
        if (jobType == JobType.BUILD) {
            buildJobRunsCount ++;
            /*
             ABORTED
             NOT_BUILT
             FAILURE
             SUCCESS
             UNSTABLE
             */
            if ("SUCCESS".equals(jobRunDetailLite.getResult())) {
                buildSuccessfulJobRunsCount ++;
            } else {
                buildFailedJobRunsCount ++;
            }
            if (jobRunDetailLite.getDuration() >= 0) {
                buildTimeTotal += jobRunDetailLite.getDuration();
                buildsCount++;
            }
            if ("MANUAL".equals(jobRunDetailLite.getTriggerType())) {
                manualTriggerBuildsCount++;
            } else {
                automatedTriggerBuildsCount++;
            }
        } else if (jobType == JobType.DEPLOYMENT) {
            deploymentJobRunsCount ++;
        } else if (jobType == JobType.ROLLBACK) {
            rollbackJobRunsCount ++;
        }
    }
    public Report build() {
        Double buildSuccessRate = (buildSuccessfulJobRunsCount) / (double) (buildSuccessfulJobRunsCount + buildFailedJobRunsCount);
        Double averageBuildTime = (buildTimeTotal) / (double) buildsCount;
        return new Report(deploymentJobRunsCount, rollbackJobRunsCount, buildSuccessRate, averageBuildTime, manualTriggerBuildsCount, automatedTriggerBuildsCount);
    }
}
