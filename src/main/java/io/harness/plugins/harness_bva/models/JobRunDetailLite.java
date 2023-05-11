package io.harness.plugins.harness_bva.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

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
    private  static void processJobTrigger(JobTrigger jt, AtomicInteger manualTriggersCount) {
        if (jt == null) {
            return;
        }
        if ("UserIdCause".equals(jt.getType())) {
            manualTriggersCount.incrementAndGet();
        }
        if (CollectionUtils.isEmpty(jt.getTriggers())) {
            return;
        }
        for (JobTrigger c : jt.getTriggers()) {
            processJobTrigger(c, manualTriggersCount);
        }
    }
    private static String determineTriggerType(Set<JobTrigger> triggerChain) {
        if(CollectionUtils.isEmpty(triggerChain)) {
            return "MANUAL";
        }
        AtomicInteger manualTriggersCount = new AtomicInteger();
        for (JobTrigger current : triggerChain) {
            processJobTrigger(current, manualTriggersCount);
        }
        if (manualTriggersCount.get() > 0) {
            return "MANUAL";
        } else {
            return "AUTOMATED";
        }
    }

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
        /*
        triggerChain jobFullName=Build-Aggregations-Service, build number = 1,591, triggerChain = [{"id":"viraj","type":"UserIdCause"}]
        triggerChain jobFullName=Deploy-Aggregations-Service, build number = 1,098, triggerChain = [{"id":"Build-Aggregations-Service","job_run_number":"1591","type":"UpstreamCause","direct_parents":[{"id":"viraj","type":"UserIdCause"}]}]
        triggerChain jobFullName=GitOps, build number = 7,956, triggerChain = [{"id":"SCMTrigger","type":"GitHubPushCause"}]
        triggerChain jobFullName=Reports_Post_Deployment_Test_Run_Foo_Customer_Dev, build number = 5,077, triggerChain = [{"id":"GitOps","job_run_number":"7956","type":"UpstreamCause","direct_parents":[{"id":"SCMTrigger","type":"GitHubPushCause"}]}]
         */
        String triggerType = determineTriggerType(j.getTriggerChain());
        return new JobRunDetailLite(j.getJobName(),j.getJobFullName(), j.getJobNormalizedFullName(),
                params, j.getBuildNumber(), j.getUserId(), j.getStartTime(), j.getResult(), j.getDuration(), triggerType
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

    public static JobRunDetailLite fromCSVString (final String input) {
        if (StringUtils.isBlank(input)) {
            return null;
        }
        String[] parts = input.split(",");
        if((parts == null) || (parts.length != 10)) {
            return null;
        }
        return new JobRunDetailLite(parts[1],
                parts[2],
                parts[3],
                Collections.emptyMap(),
                Long.parseLong(parts[4]),
                parts[5],
                Long.parseLong(parts[6]),
                parts[7],
                Long.parseLong(parts[8]),
                parts[9]
        );
    }

}
