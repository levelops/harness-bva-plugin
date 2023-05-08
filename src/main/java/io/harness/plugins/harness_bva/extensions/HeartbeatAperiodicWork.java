package io.harness.plugins.harness_bva.extensions;

import groovy.util.logging.Log4j2;
import hudson.Extension;
import hudson.model.AperiodicWork;
import io.harness.plugins.harness_bva.plugins.HarnessBVAPluginImpl;
import io.harness.plugins.harness_bva.services.HeartbeatService;

import java.lang.invoke.MethodHandles;
import java.time.Instant;
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
        HarnessBVAPluginImpl plugin = HarnessBVAPluginImpl.getInstance();
        HeartbeatService hb = new HeartbeatService(plugin.getExpandedPluginDir());
        hb.writeHeartBeat(Instant.now().getEpochSecond());
        LOGGER.log(Level.INFO, "Harness BVA - Periodic Job Run - Completed");
    }
}
