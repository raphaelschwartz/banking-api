package com.rschwartz.bankingapi.accounts.aplication.port.in.useCase.dto;

import com.rschwartz.bankingapi.accounts.aplication.domain.Money;
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

  private Money money;

}
