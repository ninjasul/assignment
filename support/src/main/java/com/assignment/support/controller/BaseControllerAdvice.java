package com.assignment.support.controller;

import com.assignment.support.dto.BaseResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

import static com.assignment.support.dto.BaseResponseDto.FAIL;
import static com.assignment.support.dto.BaseResponseDto.NOT_FOUND;

@RestControllerAdvice
@Slf4j
public class BaseControllerAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BaseResponseDto emptyResultData() {
        log.debug("EntityNotFoundException occurred!");
        return new BaseResponseDto(NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BaseResponseDto exceptionOccurred() {
        log.debug("Exception occurred!");
        return new BaseResponseDto(FAIL);
    }
}