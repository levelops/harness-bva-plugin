package io.harness.plugins.harness_bva.services;


import io.harness.plugins.harness_bva.models.JobConfig;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class JobMatcherServiceTest {
    @Test
    public void test() {
        Assert.assertFalse(JobMatcherService.doesMatch(new JobConfig(Pattern.compile("^build.*$"), null, null), null, null));
        Assert.assertFalse(JobMatcherService.doesMatch(new JobConfig(Pattern.compile("^build.*$"), null, null), "", null));

        Assert.assertFalse(JobMatcherService.doesMatch(new JobConfig(Pattern.compile("^build.*$"), Pattern.compile("^branch$"), Pattern.compile("^dev.*$")), null, null));
        Assert.assertFalse(JobMatcherService.doesMatch(new JobConfig(Pattern.compile("^build.*$"), Pattern.compile("^branch$"), Pattern.compile("^dev.*$")), "", null));


        Assert.assertFalse(JobMatcherService.doesMatch(new JobConfig(Pattern.compile("^build.*$"), null, null), "abcd", null));
        Assert.assertFalse(JobMatcherService.doesMatch(new JobConfig(Pattern.compile("^build.*$"), null, null), "abcd build commons", null));
        Assert.assertTrue(JobMatcherService.doesMatch(new JobConfig(Pattern.compile("^build.*$"), null, null), "build", null));
        Assert.assertTrue(JobMatcherService.doesMatch(new JobConfig(Pattern.compile("^build.*$"), null, null), "build commons", null));

        Assert.assertFalse(JobMatcherService.doesMatch(new JobConfig(Pattern.compile("^build.*$"), Pattern.compile("^branch$"), Pattern.compile("^dev.*$")), "build commons", null));
        Assert.assertFalse(JobMatcherService.doesMatch(new JobConfig(Pattern.compile("^build.*$"), Pattern.compile("^branch$"), Pattern.compile("^dev.*$")), "build commons", new HashMap<>()));

        Map<String, String> params = new HashMap<>();
        params.put("abcd", "dev");

        Assert.assertFalse(JobMatcherService.doesMatch(new JobConfig(Pattern.compile("^build.*$"), Pattern.compile("^branch$"), Pattern.compile("^dev.*$")), "build commons", params));

        params.put("branch", "abcd");
        Assert.assertFalse(JobMatcherService.doesMatch(new JobConfig(Pattern.compile("^build.*$"), Pattern.compile("^branch$"), Pattern.compile("^dev.*$")), "build commons", params));

        params.put("branch", "dev");
        Assert.assertTrue(JobMatcherService.doesMatch(new JobConfig(Pattern.compile("^build.*$"), Pattern.compile("^branch$"), Pattern.compile("^dev.*$")), "build commons", params));

        params.put("branch", "dev us");
        Assert.assertTrue(JobMatcherService.doesMatch(new JobConfig(Pattern.compile("^build.*$"), Pattern.compile("^branch$"), Pattern.compile("^dev.*$")), "build commons", params));

        Assert.assertFalse(JobMatcherService.doesMatch((List<JobConfig>) null, "build commons", params));
        Assert.assertFalse(JobMatcherService.doesMatch(new ArrayList<>(), "build commons", params));

        Assert.assertFalse(JobMatcherService.doesMatch(new ArrayList<>(), null, params));
        Assert.assertFalse(JobMatcherService.doesMatch(new ArrayList<>(), "", params));

        List<JobConfig> jobConfigs = new ArrayList<>();
        jobConfigs.add(new JobConfig(Pattern.compile("^build.*$"), Pattern.compile("^branch$"), Pattern.compile("^dev.*$")));

        Assert.assertFalse(JobMatcherService.doesMatch(jobConfigs, "build commons", null));
        Assert.assertTrue(JobMatcherService.doesMatch(jobConfigs, "build commons", params));
    }
}