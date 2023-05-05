package io.harness.plugins.harness_bva.internal;

import org.kohsuke.stapler.export.Exported;

public class JobConfig {
    private final String jobName;
    private final String paramName;
    private final String paramValue;

    public JobConfig(String jobName, String paramName, String paramValue) {
        this.jobName = jobName;
        this.paramName = paramName;
        this.paramValue = paramValue;
    }
    @Exported
    public String getJobName() {
        return jobName;
    }

    public String getParamName() {
        return paramName;
    }

    public String getParamValue() {
        return paramValue;
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
