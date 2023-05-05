package io.harness.plugins.harness_bva.extensions;

import hudson.DescriptorExtensionList;
import hudson.ExtensionPoint;
import hudson.model.Describable;
import io.harness.plugins.harness_bva.internal.JobConfig;
import jenkins.model.Jenkins;
import org.apache.commons.lang.StringUtils;

abstract public class DescribedJobConfig extends JobConfig implements Describable<DescribedJobConfig>, ExtensionPoint {
    /**
     * Constructor
     *
     * @param jobName
     * @param paramName
     * @param paramValue
     */
    public DescribedJobConfig(String jobName, String paramName, String paramValue) {
        super(StringUtils.trim(jobName), StringUtils.trim(paramName), StringUtils.trim(paramValue));
    }

    /**
     * Returns all DescribedUpdateSite classes registered to Jenkins.
     *
     * @return the list of Descriptor of DescribedUpdateSite subclasses.
     */
    static public DescriptorExtensionList<DescribedJobConfig, DescribedJobConfigDescriptor> all() {
        return Jenkins.getActiveInstance().getDescriptorList(DescribedJobConfig.class);
    }

    /**
     * Returns the descriptor for this class.
     *
     * @return the descriptor for this class.
     * @see hudson.model.Describable#getDescriptor()
     */
    @Override
    public DescribedJobConfigDescriptor getDescriptor() {
        return (DescribedJobConfigDescriptor) Jenkins.getActiveInstance().getDescriptorOrDie(getClass());
    }
}
