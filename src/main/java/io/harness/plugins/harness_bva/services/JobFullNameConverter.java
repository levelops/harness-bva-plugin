package io.harness.plugins.harness_bva.services;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class JobFullNameConverter {
    public static String convertJobFullNameToJobNormalizedFullName(String jobFullName){
        if(StringUtils.isBlank(jobFullName)){
            return jobFullName;
        }
        String [] parts = jobFullName.split("/");
        if((parts == null) || (parts.length < 2)){
            return jobFullName;
        }

        List<String> resultParts = new ArrayList<>();
        for(String part : parts) {
            if("jobs".equalsIgnoreCase(part)){
                continue;
            }
            if("branches".equalsIgnoreCase(part)){
                continue;
            }
            if("modules".equalsIgnoreCase(part)){
                continue;
            }
            resultParts.add(part);
        }
        String blueOceanJobPath = StringUtils.join(resultParts, "/");
        return blueOceanJobPath;
    }
}