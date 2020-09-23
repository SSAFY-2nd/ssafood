package com.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = {"*"})
@RestControllerAdvice
@Slf4j
public class GlobalRestExceptinonHandler {
    @ExceptionHandler(value = { RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> internalServerError(Exception e){
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("msg", e.getMessage());
        return resultMap;
    }
}
