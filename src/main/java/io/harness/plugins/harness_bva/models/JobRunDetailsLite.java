package io.harness.plugins.harness_bva.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class JobRunDetailsLite {
    @JsonProperty("job_name")
    private String jobName;

    @JsonProperty("job_full_name")
    private String jobFullName;

    @JsonProperty("job_normalized_full_name")
    private String jobNormalizedFullName;

    @JsonProperty("job_run_params")
    private Map<String, String> jobRunParams;

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
    @JsonProperty("trigger_type")
    private String triggerType;
}
