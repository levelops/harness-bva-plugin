package io.harness.plugins.harness_bva.exceptions;

public class EnvironmentVariableNotDefinedException extends IllegalArgumentException{
    public EnvironmentVariableNotDefinedException() {
        super();
    }

    public EnvironmentVariableNotDefinedException(final String message) {
        super(message);
    }
}
