package io.harness.plugins.harness_bva.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobRunDetailLite {
    //region Data Members
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
    //endregion

    //region Getter
    public String getJobName() {
        return jobName;
    }

    public String getJobFullName() {
        return jobFullName;
    }

    public String getJobNormalizedFullName() {
        return jobNormalizedFullName;
    }

    public Map<String, String> getJobRunParams() {
        return jobRunParams;
    }

    public long getBuildNumber() {
        return buildNumber;
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

    public String getTriggerType() {
        return triggerType;
    }
    //endregion

    //region CSTOR
    public JobRunDetailLite(String jobName, String jobFullName, String jobNormalizedFullName, Map<String, String> jobRunParams, long buildNumber, String userId, long startTime, String result, long duration, String triggerType) {
        this.jobName = jobName;
        this.jobFullName = jobFullName;
        this.jobNormalizedFullName = jobNormalizedFullName;
        this.jobRunParams = jobRunParams;
        this.buildNumber = buildNumber;
        this.userId = userId;
        this.startTime = startTime;
        this.result = result;
        this.duration = duration;
        this.triggerType = triggerType;
    }
    //endregion

    //region Converter
    public static JobRunDetailLite fromJobRunDetail (JobRunDetail j) {
        Map<String, String> params = new HashMap<>();
        if (CollectionUtils.isNotEmpty(j.getJobRunParams())) {
            for (JobRunParam p : j.getJobRunParams()) {
                if ((StringUtils.isBlank(p.getName())) || (StringUtils.isBlank(p.getValue()))) {
                    continue;
                }
                params.put(p.getName(), p.getValue());
            }
        }
        return new JobRunDetailLite(j.getJobName(),j.getJobFullName(), j.getJobNormalizedFullName(),
                params, j.getBuildNumber(), j.getUserId(), j.getStartTime(), j.getResult(), j.getDuration(), "MANUAL"
        );
    }
    //endregion

    public String toCSVString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Instant.now().getEpochSecond()).append(",")
                .append(this.jobName).append(",")
                .append(this.jobFullName).append(",")
                .append(this.jobNormalizedFullName).append(",")
                .append(this.buildNumber).append(",")
                .append(this.userId).append(",")
                .append(this.startTime).append(",")
                .append(this.result).append(",")
                .append(this.duration).append(",")
                .append(this.triggerType).append("\n");
        String csvString = sb.toString();
        return csvString;
    }
}
