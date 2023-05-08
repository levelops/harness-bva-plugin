package io.harness.plugins.harness_bva.models;

public enum JobType {
    BUILD("build"),
    DEPLOYMENT("deployment"),
    ROLLBACK("rollback");

    private final String filePrefix;

    JobType(String dirName) {
        this.filePrefix = dirName;
    }

    public String getFilePrefix() {
        return filePrefix;
    }
}
