package io.harness.plugins.harness_bva.services;

import com.google.common.io.Files;
import io.harness.plugins.harness_bva.models.JobType;
import io.harness.plugins.harness_bva.utils.DateUtils;
import io.harness.plugins.harness_bva.utils.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.harness.plugins.harness_bva.common.Common.UTF_8;

public class JobRunStorageServiceTest {
    @Test
    public void testListFilesOlderThan() throws IOException {
        List<String> expectedFileNames = new ArrayList<>();

        JobType jobType = JobType.BUILD;
        File tempDir = Files.createTempDir();
        File dataDir = new File(tempDir, "data");
        FileUtils.createDirectoryRecursively(dataDir);

        Instant now = Instant.ofEpochSecond(1683621240l);
        for (int i=0; i < 30; i++) {
            Instant current = now.minus(i, ChronoUnit.DAYS);
            Instant startOfDay = DateUtils.toStartOfDay(current);
            File file = new File(dataDir, jobType.getFilePrefix() + "-" + startOfDay.getEpochSecond() + ".txt");
            java.nio.file.Files.write(file.toPath(), " ".getBytes(UTF_8));
        }

        try {
            JobRunStorageService jobRunStorageService = new JobRunStorageService(tempDir);
            List<File> files = jobRunStorageService.listFilesNewerThan(jobType, now, 7);
            Assert.assertEquals(7, files.size());

            expectedFileNames.add("build-1683072000.txt");
            expectedFileNames.add("build-1683158400.txt");
            expectedFileNames.add("build-1683244800.txt");
            expectedFileNames.add("build-1683331200.txt");
            expectedFileNames.add("build-1683417600.txt");
            expectedFileNames.add("build-1683504000.txt");
            expectedFileNames.add("build-1683590400.txt");
            List<String> actualFileNames = files.stream().map(f -> f.getName()).collect(Collectors.toList()).stream().sorted().collect(Collectors.toList());
            Assert.assertEquals(expectedFileNames, actualFileNames);;

            files = jobRunStorageService.listFilesOlderThan(jobType, now, 7);
            Assert.assertEquals(30-7, files.size());

            expectedFileNames.clear();
            expectedFileNames.add("build-1681084800.txt");
            expectedFileNames.add("build-1681171200.txt");
            expectedFileNames.add("build-1681257600.txt");
            expectedFileNames.add("build-1681344000.txt");
            expectedFileNames.add("build-1681430400.txt");
            expectedFileNames.add("build-1681516800.txt");
            expectedFileNames.add("build-1681603200.txt");
            expectedFileNames.add("build-1681689600.txt");
            expectedFileNames.add("build-1681776000.txt");
            expectedFileNames.add("build-1681862400.txt");
            expectedFileNames.add("build-1681948800.txt");
            expectedFileNames.add("build-1682035200.txt");
            expectedFileNames.add("build-1682121600.txt");
            expectedFileNames.add("build-1682208000.txt");
            expectedFileNames.add("build-1682294400.txt");
            expectedFileNames.add("build-1682380800.txt");
            expectedFileNames.add("build-1682467200.txt");
            expectedFileNames.add("build-1682553600.txt");
            expectedFileNames.add("build-1682640000.txt");
            expectedFileNames.add("build-1682726400.txt");
            expectedFileNames.add("build-1682812800.txt");
            expectedFileNames.add("build-1682899200.txt");
            expectedFileNames.add("build-1682985600.txt");
            actualFileNames = files.stream().map(f -> f.getName()).collect(Collectors.toList()).stream().sorted().collect(Collectors.toList());
            Assert.assertEquals(expectedFileNames, actualFileNames);

            File[] children = dataDir.listFiles();
            for(File child : children){
                child.delete();
            }
            dataDir.delete();
            tempDir.delete();
        } finally {
        }
    }
}