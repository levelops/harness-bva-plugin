package io.harness.plugins.harness_bva.services;

import io.harness.plugins.harness_bva.models.LevelOpsJenkinsReport;
import io.harness.plugins.harness_bva.utils.FileUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Properties;

import static java.nio.charset.StandardCharsets.UTF_8;

public class LevelOpsJenkinsReportOutputService {
    private final LevelOpsJenkinsReport report;

    public LevelOpsJenkinsReportOutputService(LevelOpsJenkinsReport report) {
        this.report = report;
    }

    public void outputHtml(final File outputFile) throws IOException {
        Properties p = new Properties();
        p.setProperty("resource.loader", "class");
        p.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

//        p.setProperty("resource.loader", "file");
//        p.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

        VelocityEngine ve = new VelocityEngine();
        ve.init(p);

        Template t = ve.getTemplate("report/jenkins-report.vm");

        VelocityContext vc = new VelocityContext();
        vc.put("r", report);

        StringWriter sw = new StringWriter();
        t.merge(vc, sw);

        String outputReport = sw.toString();

        FileUtils.createFileRecursively(outputFile);
        Files.write(outputFile.toPath(), outputReport.getBytes(UTF_8), StandardOpenOption.CREATE);
    }

    //region Builder
    public static LevelOpsJenkinsReportOutputServiceBuilder builder(){
        return new LevelOpsJenkinsReportOutputServiceBuilder();
    }
    public static final class LevelOpsJenkinsReportOutputServiceBuilder{
        private LevelOpsJenkinsReport report;

        public LevelOpsJenkinsReport getReport() {
            return report;
        }

        public LevelOpsJenkinsReportOutputServiceBuilder setReport(LevelOpsJenkinsReport report) {
            this.report = report;
            return this;
        }

        public LevelOpsJenkinsReportOutputService build(){
            return new LevelOpsJenkinsReportOutputService(report);
        }
    }
    //endregion
}