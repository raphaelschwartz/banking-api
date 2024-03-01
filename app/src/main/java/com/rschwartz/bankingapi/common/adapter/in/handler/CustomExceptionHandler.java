package com.rschwartz.bankingapi.common.adapter.in.handler;

import br.com.fluentvalidator.exception.ValidationException;
import com.fasterxml.jackson.core.io.JsonEOFException;
import com.rschwartz.bankingapi.accounts.application.domain.service.exception.ThresholdExceededException;
import com.rschwartz.bankingapi.common.adapter.out.exception.ExternalDependencyException;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      final HttpMessageNotReadableException ex,
      final HttpHeaders headers,
      final HttpStatusCode status,
      final WebRequest request
  ) {

    if (ex.getCause() instanceof JsonEOFException) {
      log.error("Erro interceptado: {}", ex.getMessage());
    } else {
      log.error("Erro interceptado.", ex.getCause());
    }

    final String message = ex.getCause() instanceof JsonEOFException
        ? "Malformed JSON request."
        : "Body is required.";

    final List<ApiError> errors = List.of(new ApiError("body", message));

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }
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

  @ExceptionHandler(value = {EntityNotFoundException.class, IllegalArgumentException.class, ThresholdExceededException.class})
  private ResponseEntity<Object> handleBadRequest(final RuntimeException ex,
      final WebRequest request) {

    log.error("Handling business rule {}", ex.getMessage());

    final List<ApiError> errors = List.of(new ApiError("body", ex.getMessage()));

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = ExternalDependencyException.class)
  private ResponseEntity<Object> unprocessableEntityRequest(final RuntimeException ex,
      final WebRequest request) {

    log.error("Handling unprocessable entity {}", ex.getMessage());

    final List<ApiError> errors = List.of(new ApiError("external", ex.getMessage()));

    return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
  }

}
