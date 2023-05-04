package io.harness.plugins.harness_bva.services;

import io.harness.plugins.harness_bva.models.JobConfig;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;

public class JobMatcherService {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    private static final boolean MATCHES = true;
    private static final boolean DOES_NOT_MATCH = false;

    public static boolean doesMatch(final JobConfig c, final String jobName, final Map<String, String> params) {
        if (StringUtils.isBlank(jobName)) {
            return DOES_NOT_MATCH;
        }

        Matcher jobMatcher = c.getJobName().matcher(jobName);
        if(! jobMatcher.matches()) {
            return DOES_NOT_MATCH;
        }
        boolean paramMatchNeeded = (c.getParamName() != null) && (c.getParamValue() != null);
        if (! paramMatchNeeded) {
            return MATCHES;
        }
        if(MapUtils.isEmpty(params)) {
            return DOES_NOT_MATCH;
        }
        for(Map.Entry<String, String> e : params.entrySet()) {
            Matcher keyMatcher = c.getParamName().matcher(e.getKey());
            Matcher valueMatcher = c.getParamValue().matcher(e.getValue());
            if ((keyMatcher.matches()) && (valueMatcher.matches())) {
                return MATCHES;
            }
        }
        return DOES_NOT_MATCH;
    }

    public static boolean doesMatch(final List<JobConfig> jobConfigs, final String jobName, final Map<String, String> params) {
        if(CollectionUtils.isEmpty(jobConfigs)) {
            return DOES_NOT_MATCH;
        }
        if (StringUtils.isBlank(jobName)) {
            return DOES_NOT_MATCH;
        }
        for (JobConfig c : jobConfigs) {
            boolean matches = doesMatch(c, jobName, params);
            if(matches) {
                return MATCHES;
            }
        }
        return DOES_NOT_MATCH;
    }

}
