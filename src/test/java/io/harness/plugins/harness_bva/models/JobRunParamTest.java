package io.harness.plugins.harness_bva.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.harness.plugins.harness_bva.utils.JsonUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JobRunParamTest {
    @Test
    public void testSerialize() throws IOException {
        List<JobRunParam> expected = new ArrayList<>();
        expected.add(new JobRunParam("BooleanParameterValue", "BooleanP", "true"));
        expected.add(new JobRunParam("StringParameterValue", "ChoiceP", "c1,c2"));
        expected.add(new JobRunParam("StringParameterValue", "StringP", "sd1"));
        expected.add(new JobRunParam("TextParameterValue", "MultiP", "md1l1\nmd1l2\nmd1l3"));

        ObjectMapper mapper = JsonUtils.get();
        String serialized = mapper.writeValueAsString(expected);
        Assert.assertEquals(serialized, "[{\"type\":\"BooleanParameterValue\",\"name\":\"BooleanP\",\"value\":\"true\"},{\"type\":\"StringParameterValue\",\"name\":\"ChoiceP\",\"value\":\"c1,c2\"},{\"type\":\"StringParameterValue\",\"name\":\"StringP\",\"value\":\"sd1\"},{\"type\":\"TextParameterValue\",\"name\":\"MultiP\",\"value\":\"md1l1\\nmd1l2\\nmd1l3\"}]");

        //mapper.readValue(valuesString, mapper.getTypeFactory().constructCollectionType(List.class, KvData.Value.class))
        List<JobRunParam> actual = mapper.readValue(serialized, mapper.getTypeFactory().constructCollectionType(List.class, JobRunParam.class));

        Assert.assertEquals(actual.size(), expected.size());
        for(int i =0; i < actual.size(); i++){
            Assert.assertEquals(actual.get(i), expected.get(i));
        }
    }

}