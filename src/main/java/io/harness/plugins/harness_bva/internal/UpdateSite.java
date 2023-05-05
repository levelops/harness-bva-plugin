package io.harness.plugins.harness_bva.internal;

import org.kohsuke.stapler.export.Exported;

public class UpdateSite {
    private final String id;
    private final String url;

    public UpdateSite(String id, String url) {
        this.id = id;
        this.url = url;
    }

    @Exported
    public String getId() {
        return this.id;
    }

    public String getUrl() {
        return url;
    }
}
