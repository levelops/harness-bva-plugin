package io.harness.plugins.harness_bva.services;

import io.harness.plugins.harness_bva.models.Plugin;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.UTF_8;

public class JenkinsPluginService {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    public static final String MANIFEST_FILE_NAME = "MANIFEST.MF";
    public static final String META_INF_DIRECTORY_NAME = "META-INF";

    private static final Pattern EXTENSION_NAME = Pattern.compile("^Extension-Name: (.*)$", Pattern.MULTILINE);
    private static final Pattern LONG_NAME = Pattern.compile("^Long-Name: (.*)$", Pattern.MULTILINE);
    private static final Pattern SHORT_NAME = Pattern.compile("^Short-Name: (.*)$", Pattern.MULTILINE);
    private static final Pattern SPECIFICATION_TITLE = Pattern.compile("^Specification-Title: (.*)$", Pattern.MULTILINE);
    private static final Pattern IMPLEMENTATION_TITLE = Pattern.compile("^Implementation-Title: (.*)$", Pattern.MULTILINE);
    private static final Pattern IMPLEMENTATION_VERSION = Pattern.compile("^Implementation-Version: (.*)$", Pattern.MULTILINE);
    private static final Pattern URL = Pattern.compile("^Url: (.*)$", Pattern.MULTILINE);
    private static final Pattern BUILD_JDK = Pattern.compile("^Build-Jdk: (.*)$", Pattern.MULTILINE);

    public static JenkinsPluginService getInstance(){
        return new JenkinsPluginService();
    }

    public List<Plugin> parsePlugins(final Path pluginsDirectory){
        if((pluginsDirectory == null) || (!pluginsDirectory.toFile().exists())) {
            return Collections.emptyList();
        }

        List<Plugin> plugins = new ArrayList<>();
        Queue<File> dirs = new LinkedList<>();
        dirs.offer(pluginsDirectory.toFile());
        while (dirs.peek() != null){
            File currentDir = dirs.poll();
            File[] children = currentDir.listFiles();
            if (children == null){
                continue;
            }
            for(File currentChild : children){
                if (currentChild == null){
                    continue;
                }
                if(currentChild.isDirectory()){
                    dirs.offer(currentChild);
                } else {
                    String fileName = currentChild.getName();
                    if(! MANIFEST_FILE_NAME.equals(fileName)){
                        continue;
                    }
                    File parent = currentChild.getParentFile();
                    if (! META_INF_DIRECTORY_NAME.equals(parent.getName())){
                        continue;
                    }

                    Plugin plugin = parsePluginManifest(currentChild);
                    if (plugin != null) {
                        plugins.add(plugin);
                    }
                }
            }
        }
        return plugins;
    }

    private Plugin parsePluginManifest(File pluginManifestFile){
        if (pluginManifestFile == null) {
            return null;
        }
        String data = null;
        try {
            data = new String(Files.readAllBytes(pluginManifestFile.toPath()), UTF_8);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "IOException reading plugin manifest file ! " + pluginManifestFile.toString(), e);
            return null;
        }

        Plugin.PluginBuilder builder = Plugin.builder();

        Matcher matcher = EXTENSION_NAME.matcher(data);
        if(matcher.find()){
            builder.extensionName(matcher.group(1));
        }

        matcher = LONG_NAME.matcher(data);
        if(matcher.find()){
            builder.longName(matcher.group(1));
        }

        matcher = SHORT_NAME.matcher(data);
        if(matcher.find()){
            builder.shortName(matcher.group(1));
        }

        matcher = SPECIFICATION_TITLE.matcher(data);
        if(matcher.find()){
            builder.specificationTitle(matcher.group(1));
        }

        matcher = IMPLEMENTATION_TITLE.matcher(data);
        if(matcher.find()){
            builder.implementationTitle(matcher.group(1));
        }

        matcher = IMPLEMENTATION_VERSION.matcher(data);
        if(matcher.find()){
            builder.implementationVersion(matcher.group(1));
        }

        matcher = URL.matcher(data);
        if(matcher.find()){
            builder.url(matcher.group(1));
        }

        matcher = BUILD_JDK.matcher(data);
        if(matcher.find()){
            builder.buildJdk(matcher.group(1));
        }

        return builder.build();
    }
}