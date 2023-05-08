package io.harness.plugins.harness_bva.services;

import io.harness.plugins.harness_bva.utils.FileUtils;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import static io.harness.plugins.harness_bva.common.Common.UTF_8;

public class HeartbeatService {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    public static final String HEARTBEAT_DIR_NAME = "heartbeat";
    public static final String HEARTBEAT_FILE = "heartbeat.txt";
    private final File expandedPluginDir;

    public HeartbeatService(File expandedPluginDir) {
        this.expandedPluginDir = expandedPluginDir;
    }

    private File buildHeartbeatDir () {
        return new File(this.expandedPluginDir, HEARTBEAT_DIR_NAME);
    }

    private File buildHeartbeatFile () {
        return new File(buildHeartbeatDir(), HEARTBEAT_FILE);
    }

    public Long readLatestHeartBeat() {
        File heartbeatFile = buildHeartbeatFile();
        if (! heartbeatFile.exists()) {
            return null;
        }
        try {
            return Long.valueOf(new String(Files.readAllBytes(heartbeatFile.toPath()), UTF_8));
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "HeartbeatService.readLatestHeartBeat Error reading HeartbeatFile!", e);
            return null;
        }

    }

    public boolean writeHeartBeat(Long heartbeat) {
        if (heartbeat == null) {
            return true;
        }

        //Read jenkins instance file in expandedPathDir if exists
        File heartbeatDir = buildHeartbeatDir();

        try {
            FileUtils.createDirectoryRecursively(heartbeatDir);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "HeartbeatService.writeHeartBeat Error creating heartbeatDir!", e);
            return false;
        }

        File heartbeatFile = buildHeartbeatFile();
        try {
            Files.write(heartbeatFile.toPath(), String.valueOf(heartbeat).getBytes(UTF_8));
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "HeartbeatService.writeHeartBeat Error upserting heartbeatFile!", e);
            return false;
        }
        return true;
    }
}
