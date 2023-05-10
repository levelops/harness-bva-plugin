package io.harness.plugins.harness_bva.services;


import io.harness.plugins.harness_bva.models.Plugin;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class JenkinsPluginServiceTest {
    @Test
    public void testJenkinsPluginService() throws URISyntaxException {
        URL pluginsUrl = this.getClass().getClassLoader().getResource("plugins");
        File pluginsFile = new File(pluginsUrl.toURI());

        JenkinsPluginService service = new JenkinsPluginService();
        List<Plugin> plugins = service.parsePlugins(pluginsFile.toPath());
        Assert.assertNotNull(plugins);
        Assert.assertEquals(plugins.size(), 105);
    }

    @Test
    public void testParsePluginManifest() throws URISyntaxException {
        URL levelOpsPluginsUrl = this.getClass().getClassLoader().getResource("plugins/levelops");
        File levelOpsPluginsFile = new File(levelOpsPluginsUrl.toURI());

        JenkinsPluginService service = new JenkinsPluginService();
        List<Plugin> plugins = service.parsePlugins(levelOpsPluginsFile.toPath());
        Assert.assertNotNull(plugins);
        Assert.assertEquals(plugins.size(), 1);
        Assert.assertNotNull(plugins.get(0));
        Assert.assertEquals(plugins.get(0).getExtensionName(), "levelops");
        Assert.assertEquals(plugins.get(0).getLongName(), "LevelOps Plugin");
        Assert.assertEquals(plugins.get(0).getShortName(), "levelops");
        Assert.assertEquals(plugins.get(0).getSpecificationTitle(), "Upload scan artifacts to LevelOps API");
        Assert.assertEquals(plugins.get(0).getImplementationTitle(), "levelops");
        Assert.assertEquals(plugins.get(0).getImplementationVersion(), "1.0.0-SNAPSHOT");
        Assert.assertEquals(plugins.get(0).getUrl(), "https://app.levelops.io");
        Assert.assertEquals(plugins.get(0).getBuildJdk(), "1.8.0_231");
    }

}