package io.harness.plugins.harness_bva.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Report {
    @JsonProperty("deploymentFrequency")
    private Integer deploymentFrequency;
    @JsonProperty("rollbackFrequency")
    private Integer rollbackFrequency;
    @JsonProperty("buildSuccessRate")
    private Double buildSuccessRate;
    @JsonProperty("averageBuildTime")
    private Double averageBuildTime;

    @JsonProperty("manualTriggerBuilds")
    private Integer manualTriggerBuilds;

    @JsonProperty("automatedTriggerBuilds")
    private Integer automatedTriggerBuilds;

    public Report(Integer deploymentFrequency, Integer rollbackFrequency, Double buildSuccessRate, Double averageBuildTime, Integer manualTriggerBuilds, Integer automatedTriggerBuilds) {
        this.deploymentFrequency = deploymentFrequency;
        this.rollbackFrequency = rollbackFrequency;
        this.buildSuccessRate = buildSuccessRate;
        this.averageBuildTime = averageBuildTime;
        this.manualTriggerBuilds = manualTriggerBuilds;
        this.automatedTriggerBuilds = automatedTriggerBuilds;
    }

    @Override
    public String toString() {
        return "Report{" +
                "deploymentFrequency=" + deploymentFrequency +
                ", rollbackFrequency=" + rollbackFrequency +
                ", buildSuccessRate=" + buildSuccessRate +
                ", averageBuildTime=" + averageBuildTime +
                ", manualTriggerBuilds=" + manualTriggerBuilds +
                ", automatedTriggerBuilds=" + automatedTriggerBuilds +
                '}';
    }
}
