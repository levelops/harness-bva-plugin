package io.harness.plugins.harness_bva.services;


import org.junit.Assert;
import org.junit.Test;

public class FileNameExtractorTest {
    @Test
    public void test() {
        Assert.assertNull(FileNameExtractor.extractTimestamp(null));
        Assert.assertNull(FileNameExtractor.extractTimestamp(""));
        Assert.assertNull(FileNameExtractor.extractTimestamp("abc"));
        Assert.assertEquals(1683590400l, FileNameExtractor.extractTimestamp("build-1683590400.txt").longValue());
        Assert.assertEquals(1683590401l, FileNameExtractor.extractTimestamp("deployment-1683590401.txt").longValue());
        Assert.assertEquals(1683590402l, FileNameExtractor.extractTimestamp("rollback-1683590402.txt").longValue());

        Assert.assertNull(FileNameExtractor.extractTimestamp("BUILD-1683590400.TXT"));
        Assert.assertNull(FileNameExtractor.extractTimestamp("DEPLOYMENT-1683590401.TXT"));
        Assert.assertNull(FileNameExtractor.extractTimestamp("ROLLBACK-1683590402.TXT"));
    }
}