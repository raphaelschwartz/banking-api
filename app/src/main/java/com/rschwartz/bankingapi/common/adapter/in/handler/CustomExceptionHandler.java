package com.rschwartz.bankingapi.common.adapter.in.handler;

import br.com.fluentvalidator.exception.ValidationException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<List<ApiError>> handleValidationException(final ValidationException ex) {

    log.error("Handling invalid request body {}", ex.getCause());

    final List<ApiError> errors = ex.getValidationResult()
        .getErrors()
        .stream()
        .map(error -> new ApiError(error.getField(), error.getMessage()))
        .collect(Collectors.toList());

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

}
