package com.rschwartz.bankingapi.common.adapter.in.web.validator;

import br.com.fluentvalidator.context.ValidationResult;

public class RequestValidator {

  protected boolean hasError(
      final ValidationResult result,
      final String field,
      final String message
  ) {

    return result.getErrors()
        .stream()
        .anyMatch(error -> field.equals(error.getField()) && message.equals(error.getMessage()));
  }

}
