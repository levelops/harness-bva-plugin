package io.harness.plugins.harness_bva.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.harness.plugins.harness_bva.utils.JsonUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;



public class JobNameDetailsTest {
    private final static ObjectMapper MAPPER = JsonUtils.get();
    @Test
    public void test() throws IOException {
        JobNameDetails jobNameDetails = new JobNameDetails("leetcode2", "master", "BBMaven1New/jobs/leetcode2/branches/master", "module-name", "BBMaven1New/leetcode2/master");
        String serialized = MAPPER.writeValueAsString(jobNameDetails);
        JobNameDetails actual = MAPPER.readValue(serialized, JobNameDetails.class);
        Assert.assertEquals(jobNameDetails, actual);
    }

}