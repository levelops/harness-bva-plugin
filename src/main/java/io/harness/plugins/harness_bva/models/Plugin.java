package io.harness.plugins.harness_bva.models;

public class Plugin {
    private final String extensionName;
    private final String longName;
    private final String shortName;
    private final String specificationTitle;

    private final String implementationTitle;
    private final String implementationVersion;
    private final String url;
    private final String buildJdk;

    //region CSTOR
    public Plugin(String extensionName, String longName, String shortName, String specificationTitle, String implementationTitle, String implementationVersion, String url, String buildJdk) {
        this.extensionName = extensionName;
        this.longName = longName;
        this.shortName = shortName;
        this.specificationTitle = specificationTitle;
        this.implementationTitle = implementationTitle;
        this.implementationVersion = implementationVersion;
        this.url = url;
        this.buildJdk = buildJdk;
    }
    //endregion

    //region Getter
    public String getExtensionName() {
        return extensionName;
    }
    public String getLongName() {
        return longName;
    }
    public String getShortName() {
        return shortName;
    }
    public String getSpecificationTitle() {
        return specificationTitle;
    }
    public String getImplementationTitle() {
        return implementationTitle;
    }
    public String getImplementationVersion() {
        return implementationVersion;
    }
    public String getUrl() {
        return url;
    }
    public String getBuildJdk() {
        return buildJdk;
    }
    //endregion

    public static PluginBuilder builder(){
        return new PluginBuilder();
    }

    @Override
    public String toString() {
        return "Plugin{" +
                "extensionName='" + extensionName + '\'' +
                ", longName='" + longName + '\'' +
                ", shortName='" + shortName + '\'' +
                ", specificationTitle='" + specificationTitle + '\'' +
                ", implementationTitle='" + implementationTitle + '\'' +
                ", implementationVersion='" + implementationVersion + '\'' +
                ", url='" + url + '\'' +
                ", buildJdk='" + buildJdk + '\'' +
                '}';
    }

    //region PluginBuilder
    public static class PluginBuilder {
        private String extensionName;
        private String longName;
        private String shortName;
        private String specificationTitle;

        private String implementationTitle;
        private String implementationVersion;
        private String url;
        private String buildJdk;


        public PluginBuilder extensionName(String extensionName) {
            this.extensionName = extensionName;
            return this;
        }

        public PluginBuilder longName(String longName) {
            this.longName = longName;
            return this;
        }

        public PluginBuilder shortName(String shortName) {
            this.shortName = shortName;
            return this;
        }

        public PluginBuilder specificationTitle(String specificationTitle) {
            this.specificationTitle = specificationTitle;
            return this;
        }

        public PluginBuilder implementationTitle(String implementationTitle) {
            this.implementationTitle = implementationTitle;
            return this;
        }

        public PluginBuilder implementationVersion(String implementationVersion) {
            this.implementationVersion = implementationVersion;
            return this;
        }

        public PluginBuilder url(String url) {
            this.url = url;
            return this;
        }

        public PluginBuilder buildJdk(String buildJdk) {
            this.buildJdk = buildJdk;
            return this;
        }

        public Plugin build(){
            return new Plugin(extensionName, longName, shortName, specificationTitle, implementationTitle, implementationVersion, url, buildJdk);
        }
    }
    //endregion
}