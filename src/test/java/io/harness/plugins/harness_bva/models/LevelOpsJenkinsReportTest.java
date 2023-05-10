package io.harness.plugins.harness_bva.models;

import io.harness.plugins.harness_bva.services.LevelOpsJenkinsReportOutputService;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
public class LevelOpsJenkinsReportTest {
    private final String PASS = "PASS";
    private final String FAIL = "FAIL";
    @Test
    public void testReportOutput() throws IOException {
        LevelOpsJenkinsReport report = LevelOpsJenkinsReport.builder()
                .jenkinsUrl("https://jenkins.dev.levelops.io/")
                .adminEmails("virajajgaonkar@gmail.com")
                .securityRealm(JenkinsGeneralConfig.SECURITY_REALM.JENKINS_OWN_DATABASE.getHumanReadableText())
                .securityRealmPassFail(FAIL)
                .authorizationType(JenkinsGeneralConfig.AUTHORIZATION_TYPE.ROLE_BASED_STRATEGY.getHumanReadableText())
                .authorizationTypePassFail(PASS)
                .csrfPreventionEnabled(false)
                .csrfPreventionPassFail(FAIL)
                .jnlp1ProtocolEnabled(true).jnlp1ProtocolPassFail(FAIL)
                .jnlp2ProtocolEnabled(true).jnlp2ProtocolPassFail(FAIL)
                .jnlp3ProtocolEnabled(true).jnlp3ProtocolPassFail(FAIL)
                .jnlp4ProtocolEnabled(false).jnlp4ProtocolPassFail(FAIL)
                .tlsEnabledForMasterSlaveCommunication(true).tlsSetttingPassFail(PASS)
                .pluginZapInstalled(false).pluginZapPassFail(FAIL)
                .pluginBrakemanInstalled(true).pluginBrakemanPassFail(PASS)
                .pluginAuditTrailInstalled(false).pluginAuditTrailPassFail(FAIL)
                .build();

        LevelOpsJenkinsReportOutputService outputService = LevelOpsJenkinsReportOutputService.builder().setReport(report).build();
        Path hmlOutput = Files.createTempFile("LevelOps-", "-JenkinsReport.html");
        outputService.outputHtml(hmlOutput.toFile());
    }

}