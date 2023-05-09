package io.harness.plugins.harness_bva.models;

import com.fasterxml.jackson.annotation.*;

public class Report {
    @JsonProperty("deploymentFrequency")
    private Integer deploymentFrequency;
    @JsonProperty("rollbackFrequency")
    private Integer rollbackFrequency;
    @JsonProperty("buildSuccessRate")
    private Double buildSuccessRate;
    @JsonProperty("averageBuildTime")
    private Double averageBuildTime;

    public Report(Integer deploymentFrequency, Integer rollbackFrequency, Double buildSuccessRate, Double averageBuildTime) {
        this.deploymentFrequency = deploymentFrequency;
        this.rollbackFrequency = rollbackFrequency;
        this.buildSuccessRate = buildSuccessRate;
        this.averageBuildTime = averageBuildTime;
    }

    @Override
    public String toString() {
        return "Report{" +
                "deploymentFrequency=" + deploymentFrequency +
                ", rollbackFrequency=" + rollbackFrequency +
                ", buildSuccessRate=" + buildSuccessRate +
                ", averageBuildTime=" + averageBuildTime +
                '}';
    }
}
