package com.rschwartz.bankingapi.accounts.application.domain.model;

import java.util.Objects;
import lombok.Getter;

@Getter
public class AccountId {

  private static final Long ZERO = 0L;

  protected static final String ACCOUNT_ID_REQUIRED_ERROR_MESSAGE = "Must provide a account id.";
  protected static final String ACCOUNT_ID_NEGATIVE_ERROR_MESSAGE = "Account id must be positive.";
  protected static final String ACCOUNT_ID_MINIMUM_VALUE_ERROR_MESSAGE = "Account id must be greater than zero.";

  private final Long value;

  public AccountId(final Long id) {
    validate(id);
    this.value = id;
  }

  private void validate(final Long id) {

    if (Objects.isNull(id)) {
      throw new IllegalArgumentException(ACCOUNT_ID_REQUIRED_ERROR_MESSAGE);
    }

    if (isNegative(id)) {
      throw new IllegalArgumentException(ACCOUNT_ID_NEGATIVE_ERROR_MESSAGE);
    }

    if (isEqualToZero(id)) {
      throw new IllegalArgumentException(ACCOUNT_ID_MINIMUM_VALUE_ERROR_MESSAGE);
    }

  }

  private boolean isNegative(final Long id) {
    return ZERO > id;
  }

  private boolean isEqualToZero(final Long id) {
    return ZERO.equals(id);
  }

}
