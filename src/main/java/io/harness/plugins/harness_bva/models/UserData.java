package io.harness.plugins.harness_bva.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

public class UserData {
    private String id;
    private String fullName;
    private Properties properties;

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public Properties getProperties() {
        return properties;
    }

    public final static class Properties {
        @JacksonXmlElementWrapper(localName = "hudson.tasks.Mailer_-UserProperty")
        private Mailer mailer;

        public Mailer getMailer() {
            return mailer;
        }

        public final static class Mailer {
            private String emailAddress;

            public String getEmailAddress() {
                return emailAddress;
            }
        }
    }
}