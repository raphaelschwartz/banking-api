package com.rschwartz.bankingapi.accounts.application.domain.service.exception;

import com.rschwartz.bankingapi.accounts.application.domain.model.Money;

public class ThresholdExceededException extends RuntimeException {

  public ThresholdExceededException(final Money amount, final Money thresholdAvailable) {
    super(String.format(
        "Maximum threshold for transferring money exceeded: tried to transfer %s but threshold available is %s.",
        amount.getValue(), thresholdAvailable.getValue()));
  }

}
