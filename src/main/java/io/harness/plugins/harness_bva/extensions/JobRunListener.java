package io.harness.plugins.harness_bva.extensions;

import hudson.Extension;
import hudson.model.Run;
import hudson.model.listeners.RunListener;
import io.harness.plugins.harness_bva.plugins.HarnessBVAPluginImpl;
import io.harness.plugins.harness_bva.services.JobRunProcessorService;

import java.lang.invoke.MethodHandles;
import java.util.logging.Logger;

@Extension
public class JobRunListener extends RunListener<Run> {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    @Override
    public void onFinalized(Run run) {
        HarnessBVAPluginImpl plugin = HarnessBVAPluginImpl.getInstance();

        JobRunProcessorService jobRunProcessorService = new JobRunProcessorService();
        jobRunProcessorService.processJobRun(run, plugin.getHudsonHome(), plugin);
    }
}
