package io.harness.plugins.harness_bva.services;

import com.google.common.io.Files;
import io.harness.plugins.harness_bva.models.JobRunDetailLite;
import io.harness.plugins.harness_bva.models.JobType;
import io.harness.plugins.harness_bva.utils.DateUtils;
import io.harness.plugins.harness_bva.utils.FileUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static io.harness.plugins.harness_bva.common.Common.DATA_DIR_NAME;
import static io.harness.plugins.harness_bva.common.Common.UTF_8;

public class JobRunStorageService {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    private final File expandedPluginDir;

    public JobRunStorageService(File expandedPluginDir) {
        this.expandedPluginDir = expandedPluginDir;
    }

    private File buildJobRunFilePath(File expandedPluginDir, String filePrefix, Instant runCompletionTime) {
        File dataDir = new File(expandedPluginDir, DATA_DIR_NAME);
        return new File(dataDir, filePrefix + "-" + DateUtils.toStartOfDay(runCompletionTime).getEpochSecond() + ".txt");
    }
    public boolean write(JobType jobType, Instant runCompletionTime, JobRunDetailLite jobRunDetailLite) {
        File filePath = buildJobRunFilePath(expandedPluginDir, jobType.getFilePrefix(), runCompletionTime);
        try {
            FileUtils.createFileRecursively(filePath);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error creating data file !" + filePath.toString() , e);
            return false;
        }
        String jobRunData = jobRunDetailLite.toCSVString();
        try {
            Files.append(jobRunData, filePath, UTF_8);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error appending data file !" + filePath.toString() , e);
            return false;
        }
        return true;
    }

    private List<File> getAllFilesWithPrefix(JobType jobType) {
        File dataDir = new File(expandedPluginDir, DATA_DIR_NAME);
        File[] allFilesWithPrefix = dataDir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith(jobType.getFilePrefix());
            }
        });
        if (allFilesWithPrefix == null) {
            Collections.emptyList();
        }
        return Arrays.asList(allFilesWithPrefix);
    }

    public List<File> listFilesOlderThan(JobType jobType, Instant now, int olderThanDays) {
        Long olderThanThreshold = DateUtils.toStartOfDay(now.minus(olderThanDays - 1, ChronoUnit.DAYS)).getEpochSecond();

        List<File> files = new ArrayList<>();
        List<File> allFilesWithPrefix = getAllFilesWithPrefix(jobType);
        for (File f: allFilesWithPrefix) {
            String fileName = f.getName();
            Long timestamp = FileNameExtractor.extractTimestamp(fileName);
            if (timestamp == null) {
                continue;
            }
            if (timestamp < olderThanThreshold) {
                files.add(f);
            }
        }
        return files;
    }

    public List<File> listFilesNewerThan(JobType jobType, Instant now, int lessThanOrEqualToDays) {
        Long lessThanOrEqualToThreshold = DateUtils.toStartOfDay(now.minus(lessThanOrEqualToDays - 1, ChronoUnit.DAYS)).getEpochSecond();

        List<File> files = new ArrayList<>();
        List<File> allFilesWithPrefix = getAllFilesWithPrefix(jobType);
        for (File f: allFilesWithPrefix) {
            String fileName = f.getName();
            Long timestamp = FileNameExtractor.extractTimestamp(fileName);
            if (timestamp == null) {
                continue;
            }
            if (timestamp >= lessThanOrEqualToThreshold) {
                files.add(f);
            }
        }
        return files;
    }
}
