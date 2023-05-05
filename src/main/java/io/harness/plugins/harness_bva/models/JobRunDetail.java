package io.harness.plugins.harness_bva.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Set;

public class JobRunDetail {
    @JsonProperty("job_name")
    private String jobName;

    @JsonProperty("job_run_params")
    private List<JobRunParam> jobRunParams;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("start_time")
    private long startTime;

    @JsonProperty("result")
    private String result;

    @JsonProperty("duration")
    private long duration;

    @JsonProperty("build_number")
    private long buildNumber;

    @JsonProperty("upstream_job_run")
    private JobRunDetail upstreamJobRun;

    @JsonProperty("branch_name")
    private String branchName;

    @JsonProperty("job_full_name")
    private String jobFullName;

    @JsonProperty("module_name")
    private String moduleName;

    @JsonProperty("job_normalized_full_name")
    private String jobNormalizedFullName;

    @JsonProperty("trigger_chain")
    private Set<JobTrigger> triggerChain;

    public JobRunDetail(String jobName, List<JobRunParam> jobRunParams, String userId, long startTime,
                        String result, long duration, long buildNumber, JobRunDetail upstreamJobRun,
                        String branchName, String jobFullName, String moduleName, String jobNormalizedFullName,
                        Set<JobTrigger> triggerChain) {
        this.jobName = jobName;
        this.jobRunParams = jobRunParams;
        this.userId = userId;
        this.startTime = startTime;
        this.result = result;
        this.duration = duration;
        this.buildNumber = buildNumber;
        this.upstreamJobRun = upstreamJobRun;
        this.branchName = branchName;
        this.jobFullName = jobFullName;
        this.moduleName = moduleName;
        this.jobNormalizedFullName = jobNormalizedFullName;
        this.triggerChain = triggerChain;
    }

    public String getJobName() {
        return jobName;
    }

    public List<JobRunParam> getJobRunParams() {
        return jobRunParams;
    }

    public String getUserId() {
        return userId;
    }

    public long getStartTime() {
        return startTime;
    }

    public String getResult() {
        return result;
    }

    public long getDuration() {
        return duration;
    }

    public long getBuildNumber() {
        return buildNumber;
    }

    public JobRunDetail getUpstreamJobRun() {
        return upstreamJobRun;
    }

    public String getBranchName() {
        return branchName;
    }

    public String getJobFullName() {
        return jobFullName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getJobNormalizedFullName() {
        return jobNormalizedFullName;
    }

    public Set<JobTrigger> getTriggerChain() {
        return triggerChain;
    }

    @Override
    public String toString() {
        return "JobRunDetail{" +
                "jobName='" + jobName + '\'' +
                ", jobRunParams=" + jobRunParams +
                ", userId='" + userId + '\'' +
                ", startTime=" + startTime +
                ", result='" + result + '\'' +
                ", duration=" + duration +
                ", buildNumber=" + buildNumber +
                ", upstreamJobRun=" + upstreamJobRun +
                ", branchName='" + branchName + '\'' +
                ", jobFullName='" + jobFullName + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", jobNormalizedFullName='" + jobNormalizedFullName + '\'' +
                ", triggerChain=" + triggerChain +
                '}';
    }
}