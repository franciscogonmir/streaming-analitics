package com.analytics.infrastructure.api.advice;

import com.analytics.domain.exception.InvalidParameterException;
import com.analytics.domain.exception.StatsNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@ControllerAdvice
public class DataStreamControllerAdvice {

    @ResponseBody
    @ExceptionHandler(StatsNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Map<String,String> statsNotFoundHandler(StatsNotFoundException exception) {
        return Map.of("Message: ",exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(InvalidParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Map<String,String> invalidParameterHandler(InvalidParameterException exception) {
        return Map.of("Message: ",exception.getMessage());
    }
}
