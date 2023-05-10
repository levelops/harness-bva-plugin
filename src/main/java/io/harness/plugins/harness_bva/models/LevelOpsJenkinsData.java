package io.harness.plugins.harness_bva.models;

import org.apache.commons.lang.StringUtils;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LevelOpsJenkinsData {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    private final JenkinsGeneralConfig config;
    private final Map<String, User> users;
    private final Map<String, Plugin> plugins;

    public LevelOpsJenkinsData(JenkinsGeneralConfig config, Map<String, User> users, Map<String, Plugin> plugins) {
        this.config = config;
        this.users = users;
        this.plugins = plugins;
    }

    public JenkinsGeneralConfig getConfig() {
        return config;
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public Map<String, Plugin> getPlugins() {
        return plugins;
    }

    public static LevelOpsJenkinsDataBuilder builder() {
        return new LevelOpsJenkinsDataBuilder();
    }

    public static final class LevelOpsJenkinsDataBuilder {
        private JenkinsGeneralConfig config;
        private List<User> users;
        private List<Plugin> plugins;

        public JenkinsGeneralConfig getConfig() {
            return config;
        }

        public LevelOpsJenkinsDataBuilder config(JenkinsGeneralConfig config) {
            this.config = config;
            return this;
        }

        public List<User> getUsers() {
            return users;
        }

        public LevelOpsJenkinsDataBuilder users(List<User> users) {
            this.users = users;
            return this;
        }

        public List<Plugin> getPlugins() {
            return plugins;
        }

        public LevelOpsJenkinsDataBuilder plugins(List<Plugin> plugins) {
            this.plugins = plugins;
            return this;
        }

        private Map<String, User> buildUsers(final List<User> users) {
            if (users == null) {
                return null;
            }
            Map<String, User> usersMap = new HashMap<>();
            for (User user : users) {
                if (user == null) {
                    continue;
                }
                String userName = user.getUserName();
                if (StringUtils.isBlank(userName)) {
                    LOGGER.warning("User has invalid userName! " + user.toString());
                    continue;
                }
                if (usersMap.containsKey(userName)) {
                    LOGGER.warning("Duplicate user found for user name: " + userName);
                } else {
                    usersMap.put(userName, user);
                }
            }
            return usersMap;
        }

        private Map<String, Plugin> buildPlugins(final List<Plugin> plugins) {
            if (plugins == null) {
                return null;
            }
            Map<String, Plugin> pluginMap = new HashMap<>();
            for (Plugin plugin : plugins) {
                if (plugin == null) {
                    continue;
                }
                String extensionName = plugin.getExtensionName();
                if (StringUtils.isBlank(extensionName)) {
                    LOGGER.log(Level.WARNING, "Plugin extensionName is invalid! {0}", plugin);
                    continue;
                }
                if (pluginMap.containsKey(extensionName)) {
                    LOGGER.warning("Duplicate plugin found with extensionName = " + extensionName);
                } else {
                    pluginMap.put(extensionName, plugin);
                }
            }
            return pluginMap;
        }

        public LevelOpsJenkinsData build() {
            return new LevelOpsJenkinsData(getConfig(), buildUsers(getUsers()), buildPlugins(getPlugins()));
        }
    }
}
