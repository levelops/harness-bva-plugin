package io.harness.plugins.harness_bva.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;
import org.apache.commons.collections.CollectionUtils;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class JobTrigger {
    @JsonProperty("id")
    private String id;
    @JsonProperty("job_run_number")
    private String buildNumber;
    @JsonProperty("type")
    private String type;
    @JsonProperty("direct_parents")
    private Set<JobTrigger> triggers;

    public JobTrigger(final @Nonnull String id, final @Nonnull String type) {
        this.id = id;
        this.type = type;
        this.buildNumber = null;
        this.triggers = null;
    }

    public JobTrigger(final @Nonnull String id, final @Nonnull String type, final String buildNumber, final Set<JobTrigger> triggers) {
        this.id = id;
        this.type = type;
        this.buildNumber = buildNumber;
        this.triggers = triggers;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    public Set<JobTrigger> getTriggers() {
        return triggers;
    }


    @Override
    public String toString() {
        StringBuilder triggersBuilder = new StringBuilder();
        if (CollectionUtils.isNotEmpty(triggers)) {
            for (JobTrigger trigger:triggers) {
                triggersBuilder.append(trigger.toString()).append(",");
            }
            triggersBuilder.deleteCharAt(triggersBuilder.length()-1);
        }
        String triggersStr = triggersBuilder.toString();
        return "{" +
                (Strings.isNullOrEmpty(id) ? "" : " \"id\":\"" + id + "\", ") +
                (Strings.isNullOrEmpty(buildNumber) ? "" : " \"job_run_number\":\"" + buildNumber + "\", ") +
                (Strings.isNullOrEmpty(type) ? "" : "\"type\":\"" + type + "\"") +
                (Strings.isNullOrEmpty(triggersStr) ? "" : ", \"direct_parents\": [" + triggersStr + "]") +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof JobTrigger)) {
            return false;
        }
        JobTrigger jobTrigger = (JobTrigger) o;
        return Objects.equals(id, jobTrigger.id) && Objects.equals(buildNumber, jobTrigger.buildNumber) && Objects.equals(type, jobTrigger.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, buildNumber, type);
    }
}
