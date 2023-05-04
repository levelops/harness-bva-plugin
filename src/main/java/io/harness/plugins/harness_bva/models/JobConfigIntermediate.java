package io.harness.plugins.harness_bva.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class JobConfigIntermediate {
    @JsonProperty("jobName")
    private String jobName;
    @JsonProperty("paramName")
    private String paramName;
    @JsonProperty("paramValue")
    private String paramValue;

    public JobConfigIntermediate() {

    }
    public JobConfigIntermediate(String jobName, String paramName, String paramValue) {
        this.jobName = jobName;
        this.paramName = paramName;
        this.paramValue = paramValue;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JobConfigIntermediate jobConfigIntermediate = (JobConfigIntermediate) o;

        return new EqualsBuilder().append(jobName, jobConfigIntermediate.jobName).append(paramName, jobConfigIntermediate.paramName).append(paramValue, jobConfigIntermediate.paramValue).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(jobName).append(paramName).append(paramValue).toHashCode();
    }

    @Override
    public String toString() {
        return "JobConfig{" +
                "jobName='" + jobName + '\'' +
                ", paramName='" + paramName + '\'' +
                ", paramValue='" + paramValue + '\'' +
                '}';
    }
}
