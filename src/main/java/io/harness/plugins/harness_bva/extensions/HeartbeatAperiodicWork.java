package io.harness.plugins.harness_bva.extensions;

import groovy.util.logging.Log4j2;
import hudson.Extension;
import hudson.model.AperiodicWork;
import io.harness.plugins.harness_bva.models.JobType;
import io.harness.plugins.harness_bva.plugins.HarnessBVAPluginImpl;
import io.harness.plugins.harness_bva.services.HeartbeatService;
import io.harness.plugins.harness_bva.services.JobRunStorageService;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@Log4j2
@Extension
public class HeartbeatAperiodicWork extends AperiodicWork {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());


    @Override
    public long getRecurrencePeriod() {
        return TimeUnit.MINUTES.toMillis(1);
    }

    @Override
    public AperiodicWork getNewInstance() {
        return new HeartbeatAperiodicWork();
    }

    @Override
    protected void doAperiodicRun() {
        LOGGER.log(Level.INFO, "Harness BVA - Periodic Job Run");
        Instant now = Instant.now();
        HarnessBVAPluginImpl plugin = HarnessBVAPluginImpl.getInstance();

        JobRunStorageService jobRunStorageService = new JobRunStorageService(plugin.getExpandedPluginDir());
        for (JobType j : JobType.values()) {
            List<File> files = jobRunStorageService.listFilesOlderThan(j, now, 7);
            for (File f : files) {
                f.delete();
            }
        }
        LOGGER.log(Level.INFO, "Harness BVA - Periodic Job Run - Completed");
    }
}
