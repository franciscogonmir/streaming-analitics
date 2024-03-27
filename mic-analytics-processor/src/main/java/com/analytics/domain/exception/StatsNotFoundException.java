package com.analytics.domain.exception;

public class StatsNotFoundException extends RuntimeException {

    public StatsNotFoundException(final String id) {
        super("Could not find stats with id ->  " + id);
    }
}
