package io.harness.plugins.harness_bva.services;

import org.junit.Assert;
import org.junit.Test;

public class JobFullNameConverterTest {
    @Test
    public void test(){
        Assert.assertEquals(null, JobFullNameConverter.convertJobFullNameToJobNormalizedFullName(null));
        Assert.assertEquals("", JobFullNameConverter.convertJobFullNameToJobNormalizedFullName(""));
        Assert.assertEquals("    ", JobFullNameConverter.convertJobFullNameToJobNormalizedFullName("    "));

        Assert.assertEquals("Pipe2", JobFullNameConverter.convertJobFullNameToJobNormalizedFullName("Pipe2"));
        Assert.assertEquals("com.wordnik$swagger-codegen_2.9.1", JobFullNameConverter.convertJobFullNameToJobNormalizedFullName("com.wordnik$swagger-codegen_2.9.1"));
        Assert.assertEquals("Update-commons", JobFullNameConverter.convertJobFullNameToJobNormalizedFullName("Update-commons"));
        Assert.assertEquals("BBMaven1New/leetcode", JobFullNameConverter.convertJobFullNameToJobNormalizedFullName("BBMaven1New/jobs/leetcode"));
        Assert.assertEquals("BBMaven1New/leetcode/master", JobFullNameConverter.convertJobFullNameToJobNormalizedFullName("BBMaven1New/jobs/leetcode/branches/master"));
        Assert.assertEquals("Folder1/Folder2/BBMaven1New/leetcode", JobFullNameConverter.convertJobFullNameToJobNormalizedFullName("Folder1/jobs/Folder2/jobs/BBMaven1New/jobs/leetcode"));
        Assert.assertEquals("Folder1/Folder2/BBMaven1New/leetcode/master", JobFullNameConverter.convertJobFullNameToJobNormalizedFullName("Folder1/jobs/Folder2/jobs/BBMaven1New/jobs/leetcode/branches/master"));
        Assert.assertEquals("TestBlueOcean", JobFullNameConverter.convertJobFullNameToJobNormalizedFullName("TestBlueOcean"));
        Assert.assertEquals("TestBlueOcean/master", JobFullNameConverter.convertJobFullNameToJobNormalizedFullName("TestBlueOcean/branches/master"));

        Assert.assertEquals("pipeline-1", JobFullNameConverter.convertJobFullNameToJobNormalizedFullName("pipeline-1"));
        Assert.assertEquals("pipeline-1/master", JobFullNameConverter.convertJobFullNameToJobNormalizedFullName("pipeline-1/branches/master"));
        Assert.assertEquals("pipeline-int-1", JobFullNameConverter.convertJobFullNameToJobNormalizedFullName("pipeline-int-1"));
        Assert.assertEquals("pipeline-int-1/master", JobFullNameConverter.convertJobFullNameToJobNormalizedFullName("pipeline-int-1/branches/master"));
    }
}