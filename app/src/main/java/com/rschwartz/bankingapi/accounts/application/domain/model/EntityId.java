package com.rschwartz.bankingapi.accounts.application.domain.model;

import java.util.Objects;
import lombok.Getter;

@Getter
public class EntityId {

  private static final Long ZERO = 0L;

  protected static final String ID_REQUIRED_ERROR_MESSAGE = "Must provide a id.";
  protected static final String ID_NEGATIVE_ERROR_MESSAGE = "Id must be positive.";
  protected static final String ID_MINIMUM_VALUE_ERROR_MESSAGE = "Id must be greater than zero.";

  private final Long value;

  public EntityId(final Long value) {
    validate(value);
    this.value = value;
  }

  private void validate(final Long value) {

    if (Objects.isNull(value)) {
      throw new IllegalArgumentException(ID_REQUIRED_ERROR_MESSAGE);
    }

    if (isNegative(value)) {
      throw new IllegalArgumentException(ID_NEGATIVE_ERROR_MESSAGE);
    }

    if (isEqualToZero(value)) {
      throw new IllegalArgumentException(ID_MINIMUM_VALUE_ERROR_MESSAGE);
    }

  }

  private boolean isNegative(final Long value) {
    return ZERO > value;
  }

  private boolean isEqualToZero(final Long value) {
    return ZERO.equals(value);
  }

}
