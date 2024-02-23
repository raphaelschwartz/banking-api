package com.rschwartz.bankingapi.accounts.adapter.in.web.exception;

import br.com.fluentvalidator.context.ValidationResult;
import br.com.fluentvalidator.exception.ValidationException;

public class SendMoneyRequestValidatorException extends ValidationException {

  public SendMoneyRequestValidatorException(final ValidationResult result) {
    super(result);
  }

}
