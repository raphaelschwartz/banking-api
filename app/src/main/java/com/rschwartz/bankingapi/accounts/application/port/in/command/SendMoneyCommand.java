package com.rschwartz.bankingapi.accounts.application.port.in.command;

import static com.rschwartz.bankingapi.common.port.in.Validation.validate;

import com.rschwartz.bankingapi.accounts.application.port.in.command.validator.annotation.PositiveAmount;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record SendMoneyCommand(
    @Min(1)
    @NotNull
    Long sourceAccountId,
    @NotNull
    @Min(1)
    Long targetAccountId,

    @NotNull
    @PositiveAmount
    BigDecimal amount
) {

  public SendMoneyCommand(
      final Long sourceAccountId,
      final Long targetAccountId,
      final BigDecimal amount
  ) {
    this.sourceAccountId = sourceAccountId;
    this.targetAccountId = targetAccountId;
    this.amount = amount;
    validate(this);
  }

}
