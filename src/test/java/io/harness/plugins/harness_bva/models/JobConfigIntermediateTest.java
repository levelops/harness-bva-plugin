package io.harness.plugins.harness_bva.models;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.harness.plugins.harness_bva.utils.JsonUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JobConfigIntermediateTest {
    private static ObjectMapper MAPPER = JsonUtils.get();

    @Test
    public void testDeserialize() throws IOException {
        String data = "[{\"jobName\": \"name1\"}, {\"jobName\": \"name2\", \"paramName\": \"branch\", \"paramValue\": \"dev\"}]";
        List<JobConfigIntermediate> jobConfigIntermediates = MAPPER.readValue(data, MAPPER.getTypeFactory().constructCollectionType(List.class, JobConfigIntermediate.class));
        Assert.assertNotNull(jobConfigIntermediates);
        Assert.assertEquals(2, jobConfigIntermediates.size());

        List<JobConfigIntermediate> expected = new ArrayList<>();
        expected.add(new JobConfigIntermediate("name1", null, null));
        expected.add(new JobConfigIntermediate("name2", "branch", "dev"));

        Assert.assertEquals(expected, jobConfigIntermediates);
    }

}