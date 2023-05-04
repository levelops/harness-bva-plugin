package io.harness.plugins.harness_bva.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Set;

public class JobRunDetails {
    @JsonProperty("job_name")
    private String jobName;

    @JsonProperty("job_full_name")
    private String jobFullName;

    @JsonProperty("job_normalized_full_name")
    private String jobNormalizedFullName;

    @JsonProperty("job_run_params")
    private List<JobRunParam> jobRunParams;

    @JsonProperty("build_number")
    private long buildNumber;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("start_time")
    private long startTime;
    @JsonProperty("result")
    private String result;
    @JsonProperty("duration")
    private long duration;
    @JsonProperty("branch_name")
    private String branchName;
    @JsonProperty("module_name")
    private String moduleName;
    @JsonProperty("trigger_chain")
    private Set<JobTrigger> triggerChain;
}
