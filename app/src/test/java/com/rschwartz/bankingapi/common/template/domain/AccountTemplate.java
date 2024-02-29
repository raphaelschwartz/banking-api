package com.rschwartz.bankingapi.common.template.domain;

import com.rschwartz.bankingapi.accounts.application.domain.model.Account;
import com.rschwartz.bankingapi.accounts.application.domain.model.AccountBalance;
import com.rschwartz.bankingapi.accounts.application.domain.model.AccountId;
import com.rschwartz.bankingapi.accounts.application.domain.model.AccountStatus;
import com.rschwartz.bankingapi.accounts.application.domain.model.AuditDate;
import com.rschwartz.bankingapi.accounts.application.domain.model.EntityId;
import com.rschwartz.bankingapi.accounts.application.domain.model.Money;
import java.time.LocalDateTime;

public class AccountTemplate {

  public static Account getTemplateOne() {

    return Account.restore(
        new AccountId(1L),
        new EntityId(1234L),
        "0001",
        new AccountBalance(Money.ofInteger(100)),
        new AuditDate(LocalDateTime.now().minusMinutes(30)),
        AccountStatus.ACTIVE);
  }

  public static Account getTemplateTwo() {

    return Account.restore(
        new AccountId(2L),
        new EntityId(5678L),
        "0002",
        new AccountBalance(Money.ofInteger(150)),
        new AuditDate(LocalDateTime.now().minusMinutes(30)),
        AccountStatus.ACTIVE);
  }

}
