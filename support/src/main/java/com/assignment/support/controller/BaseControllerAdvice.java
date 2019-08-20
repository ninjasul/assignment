package com.assignment.support.controller;

import com.assignment.support.dto.BaseResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
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
        log.error("EntityNotFoundException occurred!");
        return new BaseResponseDto(NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BaseResponseDto wrongData() {
        log.error("DataIntegrityViolationException occurred!");
        return new BaseResponseDto(FAIL);
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BaseResponseDto wrongData2() {
        log.error("InvalidDataAccessApiUsageException occurred!");
        return new BaseResponseDto(FAIL);
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BaseResponseDto wrongNumberData() {
        log.error("NumberFormatException occurred!");
        return new BaseResponseDto(FAIL);
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public BaseResponseDto exceptionOccurred() {
        log.error("Exception occurred!");
        return new BaseResponseDto(FAIL);
    }
}