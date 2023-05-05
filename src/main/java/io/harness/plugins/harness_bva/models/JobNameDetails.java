package io.harness.plugins.harness_bva.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobNameDetails {
    @JsonProperty("job_name")
    private String jobName;

    @JsonProperty("branch_name")
    private String branchName;

    @JsonProperty("job_full_name")
    private String jobFullName;

    @JsonProperty("module_name")
    private String moduleName;

    @JsonProperty("job_normalized_full_name")
    private String jobNormalizedFullName;

    public JobNameDetails() {
    }

    public JobNameDetails(String jobName, String branchName, String jobFullName, String moduleName, String jobNormalizedFullName) {
        this.jobName = jobName;
        this.branchName = branchName;
        this.jobFullName = jobFullName;
        this.moduleName = moduleName;
        this.jobNormalizedFullName = jobNormalizedFullName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getJobFullName() {
        return jobFullName;
    }

    public void setJobFullName(String jobFullName) {
        this.jobFullName = jobFullName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getJobNormalizedFullName() {
        return jobNormalizedFullName;
    }

    public void setJobNormalizedFullName(String jobNormalizedFullName) {
        this.jobNormalizedFullName = jobNormalizedFullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobNameDetails that = (JobNameDetails) o;
        return Objects.equal(jobName, that.jobName) &&
                Objects.equal(branchName, that.branchName) &&
                Objects.equal(jobFullName, that.jobFullName) &&
                Objects.equal(moduleName, that.moduleName) &&
                Objects.equal(jobNormalizedFullName, that.jobNormalizedFullName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(jobName, branchName, jobFullName, moduleName, jobNormalizedFullName);
    }

    @Override
    public String toString() {
        return "JobNameDetails{" +
                "jobName='" + jobName + '\'' +
                ", branchName='" + branchName + '\'' +
                ", jobFullName='" + jobFullName + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", jobNormalizedFullName='" + jobNormalizedFullName + '\'' +
                '}';
    }
}