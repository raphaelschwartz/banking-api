package com.rschwartz.bankingapi.accounts.application.domain.model;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public class TransactionDetail {

  private static final Integer MINIMUM_SIZE = 10;
  private static final Integer MAXIMUM_SIZE = 50;

  protected static final String DETAIL_REQUIRED_ERROR_MESSAGE = "Must provide a detail.";
  protected static final String DETAIL_MINIMUM_SIZE_ERROR_MESSAGE = "Create date must be greater or equal to 10.";
  protected static final String DETAIL_MAXIMUM_SIZE_ERROR_MESSAGE = "Create date must must be less or equal to 50.";

  private final String value;

  public TransactionDetail(final String detail) {
    validate(detail);
    this.value = detail;
  }

  private void validate(final String detail) {

    if (StringUtils.isBlank(detail)) {
      throw new IllegalArgumentException(DETAIL_REQUIRED_ERROR_MESSAGE);
    }

    if (isMinimumSizeInvalid(detail)) {
      throw new IllegalArgumentException(DETAIL_MINIMUM_SIZE_ERROR_MESSAGE);
    }

    if (isMaximumSizeInvalid(detail)) {
      throw new IllegalArgumentException(DETAIL_MAXIMUM_SIZE_ERROR_MESSAGE);
    }

  }

  private boolean isMinimumSizeInvalid(final String detail) {
    return detail.length() < MINIMUM_SIZE;
  }

  private boolean isMaximumSizeInvalid(final String detail) {
    return detail.length() > MAXIMUM_SIZE;
  }

}
