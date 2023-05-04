package io.harness.plugins.harness_bva.extensions;

import hudson.Extension;
import hudson.model.Run;
import hudson.model.listeners.RunListener;

import java.lang.invoke.MethodHandles;
import java.util.logging.Logger;

@Extension
public class JobRunListener extends RunListener<Run> {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @Override
    public void onFinalized(Run run) {

    }

}
