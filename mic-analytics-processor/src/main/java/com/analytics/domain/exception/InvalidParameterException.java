package com.analytics.domain.exception;

public class InvalidParameterException extends RuntimeException {
    public InvalidParameterException(final String parameter) {
        super("The received parameter: '"+ parameter + "' does not exist ");
    }
}
