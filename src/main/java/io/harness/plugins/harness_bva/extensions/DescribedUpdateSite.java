package io.harness.plugins.harness_bva.extensions;

import hudson.DescriptorExtensionList;
import hudson.ExtensionPoint;
import hudson.model.Describable;
import io.harness.plugins.harness_bva.internal.UpdateSite;
import jenkins.model.Jenkins;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

abstract public class DescribedUpdateSite extends UpdateSite implements Describable<DescribedUpdateSite>, ExtensionPoint
{
    /**
     * Constructor
     *
     * @param id id for the site
     * @param url URL for the site
     */
    public DescribedUpdateSite(String id, String url)
    {
        super(StringUtils.trim(id), StringUtils.trim(url));
    }

    /**
     * Returns whether this UpdateSite is disabled.
     *
     * Returning true makes Jenkins ignore the plugins in this UpdateSite.
     *
     * @return whether this UpdateSite is disabled.
     */
    public boolean isDisabled()
    {
        return false;
    }

    /**
     * Returns note
     *
     * Provided for users to note about this UpdateSite.
     * Used only for displaying purpose.
     *
     * @return note
     */
    public String getNote()
    {
        return "";
    }



    /**
     * Returns all DescribedUpdateSite classes registered to Jenkins.
     *
     * @return the list of Descriptor of DescribedUpdateSite subclasses.
     */
    static public DescriptorExtensionList<DescribedUpdateSite, DescribedUpdateSiteDescriptopr> all()
    {
        return Jenkins.getActiveInstance().getDescriptorList(DescribedUpdateSite.class);
    }


    /**
     * Returns the descriptor for this class.
     *
     * @return the descriptor for this class.
     * @see hudson.model.Describable#getDescriptor()
     */
    @Override
    public DescribedUpdateSiteDescriptopr getDescriptor()
    {
        return (DescribedUpdateSiteDescriptopr) Jenkins.getActiveInstance().getDescriptorOrDie(getClass());
    }
}
