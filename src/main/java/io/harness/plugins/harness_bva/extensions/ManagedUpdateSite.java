package io.harness.plugins.harness_bva.extensions;

import hudson.Extension;
import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;

import java.util.logging.Logger;

public class ManagedUpdateSite extends DescribedUpdateSite
{
    private static Logger LOGGER = Logger.getLogger(ManagedUpdateSite.class.getName());

    private String caCertificate;

    /**
     * Returns the CA certificate to verify the signature.
     *
     * This is useful when the UpdateSite is signed with a self-signed private key.
     *
     * @return the CA certificate
     */
    public String getCaCertificate()
    {
        return caCertificate;
    }

    /**
     * Set the CA certificate to verify the signature.
     *
     * Mainly for testing Purpose.
     *
     * @param caCertificate CA certificate
     */
    public void setCaCertificate(String caCertificate)
    {
        this.caCertificate = caCertificate;
    }

    /**
     * Returns whether to use CA certificate.
     *
     * @return whether to use CA certificate
     */
    public boolean isUseCaCertificate()
    {
        return getCaCertificate() != null;
    }

    private boolean disabled;

    /**
     * Returns whether this site is disabled.
     *
     * When disabled, plugins in this site gets unavailable.
     *
     * @return the whether this site is disabled
     */
    @Override
    public boolean isDisabled()
    {
        return disabled;
    }

    private String note;

    /**
     * Returns the note
     *
     * Note is only used for the displaying purpose.
     *
     * @return the note
     */
    @Override
    public String getNote()
    {
        return note;
    }

    /**
     * Create a new instance
     *
     * @param id id for the site
     * @param url URL for the site
     * @param useCaCertificate whether to use a specified CA certificate
     * @param caCertificate CA certificate to verify the site
     * @param note note
     * @param disabled {@code true} to disable the site
     */
    @DataBoundConstructor
    public ManagedUpdateSite(
            String id,
            String url,
            boolean useCaCertificate,
            String caCertificate,
            String note,
            boolean disabled
    )
    {
        super(id, url);
        this.caCertificate = useCaCertificate? StringUtils.trim(caCertificate):null;
        this.note = note;
        this.disabled = disabled;
    }



    /**
     * Descriptor for this class.
     */
    @Extension
    static public class DescriptorImpl extends DescribedUpdateSiteDescriptopr
    {
        /**
         * Returns the kind name of this UpdateSite.
         *
         * shown when select UpdateSite to create.
         *
         * @return the kind name of the site
         * @see hudson.model.Descriptor#getDisplayName()
         */
        @Override
        public String getDisplayName()
        {
            return "VA Fix Later";
        }



    }
}
