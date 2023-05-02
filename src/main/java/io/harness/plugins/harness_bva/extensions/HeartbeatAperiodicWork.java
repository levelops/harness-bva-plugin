package io.harness.plugins.harness_bva.extensions;

import groovy.util.logging.Log4j2;
import hudson.Extension;
import hudson.model.AperiodicWork;

import java.lang.invoke.MethodHandles;
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
    }
}
