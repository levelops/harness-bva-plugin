package io.harness.plugins.harness_bva.models;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.ArrayList;
import java.util.List;

@JacksonXmlRootElement(localName = "hudson.model.UserIdMapper")
public class UsersConfig {
    private Integer version;

    @JacksonXmlElementWrapper(localName = "idToDirectoryNameMap")
    private ConcurrentHashMap idToDirectoryNameMap;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public ConcurrentHashMap getIdToDirectoryNameMap() {
        return idToDirectoryNameMap;
    }

    public void setIdToDirectoryNameMap(ConcurrentHashMap idToDirectoryNameMap) {
        this.idToDirectoryNameMap = idToDirectoryNameMap;
    }

    public final static class ConcurrentHashMap {
        @JacksonXmlElementWrapper(useWrapping = false)
        private List<Entry> entry;

        public List<Entry> getEntry() {
            return entry;
        }

        public void setEntry(List<Entry> entry) {
            this.entry = entry;
        }

        public final static class Entry {
            @JacksonXmlElementWrapper(useWrapping = false, localName = "string")
            private List<String> string = new ArrayList<>();

            public List<String> getString() {
                return string;
            }

            public void setString(List<String> data) {
                this.string = data;
            }
        }
    }

}