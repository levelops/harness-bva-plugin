package io.harness.plugins.harness_bva.services;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileNameExtractor {
    private static final Pattern P = Pattern.compile("[a-z]*\\-(\\d*)\\.txt");

    public static Long extractTimestamp(String input) {
        if(StringUtils.isBlank(input)) {
            return null;
        }
        final Matcher matcher = P.matcher(input);

        if (!matcher.matches()) {
            return null;
        }
        Long result = Long.parseLong(matcher.group(1));
        return result;
    }
}
