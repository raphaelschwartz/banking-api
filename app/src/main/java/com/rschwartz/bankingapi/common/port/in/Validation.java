package com.rschwartz.bankingapi.common.port.in;


import static jakarta.validation.Validation.buildDefaultValidatorFactory;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;

public class Validation {

  private static final Validator validator = buildDefaultValidatorFactory().getValidator();

  public static <T> void validate(final T subject) {

    final Set<ConstraintViolation<T>> violations = validator.validate(subject);

    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }

  }

}

