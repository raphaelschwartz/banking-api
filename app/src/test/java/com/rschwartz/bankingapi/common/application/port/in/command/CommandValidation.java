package com.rschwartz.bankingapi.common.application.port.in.command;

import jakarta.validation.ConstraintViolation;
import java.util.Set;

public class CommandValidation {

  protected static final String MESSAGE_TEMPLATE_NOT_NULL = "{jakarta.validation.constraints.NotNull.message}";
  protected static final String MESSAGE_MIN = "{jakarta.validation.constraints.Min.message}";

  protected static boolean hasError(
      final Set<ConstraintViolation<?>> violations,
      final String field,
      final String message
  ) {

    return violations.stream()
        .anyMatch(
            error -> field.equals(error.getPropertyPath().toString())
                && message.equals(error.getMessageTemplate())
        );
  }

}
