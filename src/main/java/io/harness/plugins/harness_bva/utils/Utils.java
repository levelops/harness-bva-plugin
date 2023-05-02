package io.harness.plugins.harness_bva.utils;

import io.harness.plugins.harness_bva.exceptions.EnvironmentVariableNotDefinedException;

import java.util.Map;

public class Utils {
    private static final String START_ENV_VAR_TOKEN = "${";
    private static final String END_ENV_VAR_TOKEN = "}";

    public static String expandEnvironmentVariables(final String path) throws EnvironmentVariableNotDefinedException {
        return internalExpandEnvironmentVariables(path, System.getenv());
    }

    // For unit testing purposes only. Use @link {expandEnvironmentVariables}.
    protected static String internalExpandEnvironmentVariables(final String path,
                                                               final Map<String, String> environmentVariables) throws EnvironmentVariableNotDefinedException {

        String tmpPath = path;
        final StringBuilder newPath = new StringBuilder();
        boolean done = false;
        while (!done) {
            if (tmpPath.contains(START_ENV_VAR_TOKEN)) {
                final int startIdx = tmpPath.indexOf(START_ENV_VAR_TOKEN);
                final int endIdx = tmpPath.indexOf(END_ENV_VAR_TOKEN, startIdx + START_ENV_VAR_TOKEN.length());

                if (endIdx != -1) {
                    final String envVar = tmpPath.substring(startIdx + START_ENV_VAR_TOKEN.length(), endIdx);
                    final String envVarValue = environmentVariables.get(envVar);
                    if (envVarValue == null) {
                        final String message = String
                                .format(
                                        "Environment variable '%s' was specified in path '%s', but it is not defined in the system's environment variables.",
                                        envVar, path);
                        throw new EnvironmentVariableNotDefinedException(message);
                    }
                    newPath.append(tmpPath.substring(0, startIdx));
                    newPath.append(envVarValue);
                    tmpPath = tmpPath.substring(endIdx + END_ENV_VAR_TOKEN.length());
                } else {
                    newPath.append(tmpPath);
                    done = true;
                }
                if (tmpPath.isEmpty()) {
                    done = true;
                }
            } else {
                newPath.append(tmpPath);
                done = true;
            }
        }

        return newPath.toString();
    }
}
