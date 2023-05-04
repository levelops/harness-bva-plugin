package io.harness.plugins.harness_bva.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.harness.plugins.harness_bva.utils.JsonUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.logging.Level;


public class JobConfig {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    private static final ObjectMapper MAPPER = JsonUtils.get();
    public static final String EXAMPLE = "[{\"jobName\": \"name1\"}, {\"jobName\": \"name2\", \"paramName\": \"branch\", \"paramValue\": \"dev\"}]";
    public static final String INVALID_JSON = "Input is invalid ! Example of valid input is: " +  EXAMPLE;
    public static final ImmutablePair<List<JobConfig>, String> EMPTY_RESULT = ImmutablePair.of(Collections.emptyList(), null);


    //region Data Members
    private Pattern jobName;
    private Pattern paramName;
    private Pattern paramValue;
    //endregion

    //region CSTOR
    public JobConfig(Pattern jobName, Pattern paramName, Pattern paramValue) {
        this.jobName = jobName;
        this.paramName = paramName;
        this.paramValue = paramValue;
    }
    //endregion

    //region Getter
    public Pattern getJobName() {
        return jobName;
    }
    public Pattern getParamName() {
        return paramName;
    }
    public Pattern getParamValue() {
        return paramValue;
    }
    //endregion


    //region Equals & HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JobConfig jobConfig = (JobConfig) o;

        return new EqualsBuilder().append(String.valueOf(jobName), String.valueOf(jobConfig.jobName)).append(String.valueOf(paramName), String.valueOf(jobConfig.paramName)).append(String.valueOf(paramValue), String.valueOf(jobConfig.paramValue)).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(String.valueOf(jobName)).append(String.valueOf(paramName)).append(String.valueOf(paramValue)).toHashCode();
    }
    //endregion

    /**
     * Validate the input string.
     * Returns all errors.
     * @param input
     * @return
     */
    public static ImmutablePair<List<JobConfig>, String> validate (final String input) {
        if (StringUtils.isBlank(input)) {
            return EMPTY_RESULT;
        }
        List<JobConfigIntermediate> jobConfigIntermediates = null;
        try {
            jobConfigIntermediates = MAPPER.readValue(input, MAPPER.getTypeFactory().constructCollectionType(List.class, JobConfigIntermediate.class));
        } catch (IOException e) {
            return ImmutablePair.of(Collections.emptyList(), INVALID_JSON);
        }
        if (CollectionUtils.isEmpty(jobConfigIntermediates)) {
            return EMPTY_RESULT;
        }

        List<JobConfig> jobConfigs = new ArrayList<>();
        Set<String> errors = new LinkedHashSet<>();

        for(JobConfigIntermediate c : jobConfigIntermediates) {
            if (StringUtils.isBlank(c.getJobName())) {
                errors.add("jobName cannot be null or empty");
                continue;
            }
            Pattern jobName = null;
            Pattern key = null;
            Pattern value = null;

            try {
                jobName = Pattern.compile(c.getJobName());
            } catch (PatternSyntaxException e) {
                //LOGGER.log(Level.SEVERE, "Error", e);
                errors.add("jobName '" + c.getJobName() + "' regex/name pattern is invalid! " + e.getDescription() + " near index " + e.getIndex());
                continue;
            }

            boolean keyIsNullOrEmpty = StringUtils.isBlank(c.getParamName());
            boolean valueIsNullOrEmpty = StringUtils.isBlank(c.getParamValue());
            if (keyIsNullOrEmpty && valueIsNullOrEmpty) {
                jobConfigs.add(new JobConfig(jobName, key, value));
                continue;
            } else if (keyIsNullOrEmpty ^ valueIsNullOrEmpty) {
                errors.add("jobName '" + c.getJobName() + "' paramName is '" + c.getParamName() + "' paramValue is '" + c.getParamValue() + "' . Both paramName & paramValue should be specified or both should be null/empty");
                continue;
            } else {
                try {
                    key = Pattern.compile(c.getParamName());
                } catch (PatternSyntaxException e) {
                    errors.add("jobName '" + c.getJobName() + "' name '" + c.getParamName() + "' regex/name pattern for name is invalid! " + e.getDescription() + " near index " + e.getIndex());
                    continue;
                }
                try {
                    value = Pattern.compile(c.getParamValue());
                } catch (PatternSyntaxException e) {
                    errors.add("jobName '" + c.getJobName() + "' value '" + c.getParamValue() + "' regex/name pattern for value is invalid! " + e.getDescription() + " near index " + e.getIndex());
                    continue;
                }
                jobConfigs.add(new JobConfig(jobName, key, value));
            }
        }

        StringBuilder sb = new StringBuilder();
        Iterator<String> i = errors.iterator();
        while( i.hasNext() ) {
            sb.append(i.next()).append("\n");
        }
        String error = sb.toString();
        return ImmutablePair.of(jobConfigs, error);

    }

    public static List<JobConfig> fromString(final String input) {
        ImmutablePair<List<JobConfig>, String> validationResult = validate(input);
        List<JobConfig> jobConfigs = validationResult.getLeft();
        return jobConfigs;
    }
}
