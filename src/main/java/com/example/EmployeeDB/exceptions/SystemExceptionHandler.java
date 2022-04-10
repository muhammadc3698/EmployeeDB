package com.example.EmployeeDB.exceptions;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SystemExceptionHandler
{
    @ExceptionHandler(value = {RequestException.class})
    public ResponseEntity<Object> handleRequestExceptions (RequestException excep)
    {
        Exception newException = new Exception(excep.getMessage(), HttpStatus.BAD_REQUEST, ZonedDateTime.now(ZoneId.of("Z")));
        return new ResponseEntity<>(newException, HttpStatus.BAD_REQUEST);

    }
}
