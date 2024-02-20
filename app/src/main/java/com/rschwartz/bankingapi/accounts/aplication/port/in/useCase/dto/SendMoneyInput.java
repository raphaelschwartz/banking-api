package com.rschwartz.bankingapi.accounts.aplication.port.in.useCase.dto;

import com.rschwartz.bankingapi.accounts.domain.AccountNew.AccountId;
import com.rschwartz.bankingapi.accounts.domain.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SendMoneyInput {

  private AccountId sourceAccountId;

  private AccountId targetAccountId;

  private Money money;

}
