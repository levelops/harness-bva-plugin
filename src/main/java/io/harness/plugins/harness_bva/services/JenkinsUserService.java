package io.harness.plugins.harness_bva.services;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.harness.plugins.harness_bva.models.User;
import io.harness.plugins.harness_bva.models.UserData;
import io.harness.plugins.harness_bva.models.UsersConfig;
import io.harness.plugins.harness_bva.utils.XmlUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JenkinsUserService {
    private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    public static final String USERS_LIST_DIR = "users";
    public static final String CONFIG_XML = "config.xml";

    public List<User> readUsers(Path usersDirectory){
        if (usersDirectory == null) {
            return Collections.emptyList();
        }
        File usersSummaryFile = new File(usersDirectory.toFile(), CONFIG_XML);
        if(!usersSummaryFile.exists()) {
            return Collections.emptyList();
        }

        List<User> users = new ArrayList<>();
        XmlMapper xmlMapper = XmlUtils.getObjectMapper();
        try {
            UsersConfig usersConfig = xmlMapper.readValue(usersSummaryFile, UsersConfig.class);
            if((usersConfig == null) || (usersConfig.getIdToDirectoryNameMap() == null)){
                return Collections.emptyList();
            }
            for(UsersConfig.ConcurrentHashMap.Entry entry : usersConfig.getIdToDirectoryNameMap().getEntry()){
                if ((entry== null) || (CollectionUtils.isEmpty(entry.getString())) || (entry.getString().size() <2)){
                    continue;
                }
                String userName = entry.getString().get(0);
                String fileName = entry.getString().get(1);
                if(StringUtils.isBlank(fileName)){
                    continue;
                }
                File usersDetailsDir = new File(usersDirectory.toFile(), fileName);
                File userFile = new File(usersDetailsDir, CONFIG_XML);
                if(!userFile.exists()){
                    continue;
                }
                UserData user = xmlMapper.readValue(userFile, UserData.class);
                if(user == null){
                    continue;
                }
                String emailAddress = null;
                if ((user.getProperties() != null) && (user.getProperties().getMailer() != null)){
                    emailAddress = user.getProperties().getMailer().getEmailAddress();
                }
                User newUser = new User(user.getId(), user.getFullName(), emailAddress);
                users.add(newUser);
            }
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "IO Exception!!", e);
        }
        return users;
    }
}