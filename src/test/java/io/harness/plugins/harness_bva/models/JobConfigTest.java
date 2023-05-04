package io.harness.plugins.harness_bva.models;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class JobConfigTest {
    @Test
    public void testValidate() {
        ImmutablePair<List<JobConfig>, String> actual = null;

        actual = JobConfig.validate(null);
        Assert.assertEquals(JobConfig.EMPTY_RESULT, actual);

        actual = JobConfig.validate("");
        Assert.assertEquals(JobConfig.EMPTY_RESULT, actual);

        actual = JobConfig.validate("[]");
        Assert.assertEquals(JobConfig.EMPTY_RESULT, actual);

        actual = JobConfig.validate("{]");
        Assert.assertTrue(CollectionUtils.isEmpty(actual.getLeft()));
        Assert.assertEquals(JobConfig.INVALID_JSON, actual.getRight());

        actual = JobConfig.validate("[{\"jobName\": \"\"}, {\"jobName\": \"\"}]");
        Assert.assertTrue(CollectionUtils.isEmpty(actual.getLeft()));
        Assert.assertEquals("jobName cannot be null or empty" + "\n", actual.getRight());

        actual = JobConfig.validate("[{\"jobName\": \"\"}, {\"jobName\": \"n1\", \"paramName\": \"k1\", \"paramValue\": \"v1\"}, {\"jobName\": \"\"}, {\"jobName\": \"n2\", \"paramName\": \"k2\", \"paramValue\": \"\"}, {\"jobName\": \"n3\", \"paramName\": \"\", \"paramValue\": \"v3\"}, {\"jobName\": \"n4\", \"paramName\": \"\", \"paramValue\": \"\"}, {\"jobName\": \"{]\"}, {\"jobName\": \"n5\", \"paramName\": \"{]\", \"paramValue\": \"v5\"}, {\"jobName\": \"n6\", \"paramName\": \"k6\", \"paramValue\": \"{]\"}]");
        List<JobConfig> expected = new ArrayList<>();
        expected.add(new JobConfig(Pattern.compile("n1"), Pattern.compile("k1"), Pattern.compile("v1")));
        expected.add(new JobConfig(Pattern.compile("n4"), null, null));


        Assert.assertEquals(expected, actual.getLeft());
        Assert.assertNotNull(actual.getRight());
//        Assert.assertEquals("jobName cannot be null or empty\n" +
//                "jobName 'n2' paramName is 'k2' paramValue is '' . Both paramName & paramValue should be specified or both should be null/empty\n" +
//                "jobName 'n3' paramName is '' paramValue is 'v3' . Both paramName & paramValue should be specified or both should be null/empty\n" +
//                "jobName '{]' regex/name pattern is invalid! Illegal repetition near index 1\n" +
//                "jobName 'n5' name '{]' regex/name pattern for name is invalid! Illegal repetition near index 1\n" +
//                "jobName 'n6' value '{]' regex/name pattern for value is invalid! Illegal repetition near index 1\n", actual.getRight());


    }

}