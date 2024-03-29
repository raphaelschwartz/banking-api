package com.rschwartz.bankingapi.accounts.application.port.in.useCase.dto;

import com.rschwartz.bankingapi.accounts.application.domain.model.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SendMoneyInput {

  private Long sourceAccountId;

  private Long targetAccountId;

  private Money amount;

}
