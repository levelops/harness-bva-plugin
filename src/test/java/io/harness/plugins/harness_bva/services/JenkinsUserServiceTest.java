package io.harness.plugins.harness_bva.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.harness.plugins.harness_bva.models.User;
import io.harness.plugins.harness_bva.models.UsersConfig;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JenkinsUserServiceTest {
    @Test
    public void test() throws URISyntaxException, JsonProcessingException {
        URL url = this.getClass().getClassLoader().getResource("users");
        File usersDir = new File(url.toURI());
        JenkinsUserService service = new JenkinsUserService();
        List<User> users = service.readUsers(usersDir.toPath());
        Assert.assertNotNull(users);
        Assert.assertEquals(users.size(), 2);
        Assert.assertEquals(users.get(0), new User("viraj", "Viraj Ajgaonkar", "viraj@levelops.io"));
        Assert.assertEquals(users.get(1), new User("testRead", "test Read", "testRead@levelops.io"));
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

        String serializedData = mapper.writeValueAsString(users);
        Assert.assertNotNull(serializedData);
    }

    @Test
    public void testSerealize1() throws JsonProcessingException {
        UsersConfig.ConcurrentHashMap.Entry entry1 = new UsersConfig.ConcurrentHashMap.Entry();
        entry1.setString(Arrays.asList("viraj", "viraj_9148271262097944704"));

        UsersConfig.ConcurrentHashMap.Entry entry2 = new UsersConfig.ConcurrentHashMap.Entry();
        entry2.setString(Arrays.asList("testread", "testRead_5786375524866123611"));

        List<UsersConfig.ConcurrentHashMap.Entry> entries = new ArrayList<>();
        entries.add(entry1);
        entries.add(entry2);

        UsersConfig.ConcurrentHashMap map = new UsersConfig.ConcurrentHashMap();
        map.setEntry(entries);
        UsersConfig usersConfig = new UsersConfig();
        usersConfig.setVersion(1);
        usersConfig.setIdToDirectoryNameMap(map);

        XmlMapper xmlMapper = new XmlMapper();
        String data = xmlMapper.writeValueAsString(usersConfig);

        Assert.assertNotNull(data);
    }

}