package io.harness.plugins.harness_bva.extensions;

import hudson.Extension;

import org.apache.commons.lang3.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;

import java.lang.invoke.MethodHandles;
import java.util.logging.Logger;

public class ManagedJobConfig extends DescribedJobConfig {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    public boolean isUseParamFilter() {
        return StringUtils.isNotBlank(getParamName()) || StringUtils.isNotBlank(getParamValue());
    }
    @DataBoundConstructor
    public ManagedJobConfig(
            String jobName,
            boolean useParamFilter,
            String paramName,
            String paramValue) {
        super(jobName, paramName, paramValue);
    }

    /**
     * Descriptor for this class.
     */
    @Extension
    static public class DescriptorImpl extends DescribedJobConfigDescriptor {
        /**
         * Returns the kind name of this UpdateSite.
         *
         * shown when select UpdateSite to create.
         *
         * @return the kind name of the site
         * @see hudson.model.Descriptor#getDisplayName()
         */
        @Override
        public String getDisplayName() {
            return "Job Configuration";
        }
    }
}
