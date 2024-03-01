package com.rschwartz.bankingapi.accounts.adapter.out.persistence.mapper;

import com.rschwartz.bankingapi.accounts.adapter.out.persistence.entity.AccountJpaEntity;
import com.rschwartz.bankingapi.accounts.application.domain.model.Account;
import com.rschwartz.bankingapi.accounts.application.domain.model.AccountBalance;
import com.rschwartz.bankingapi.accounts.application.domain.model.AccountId;
import com.rschwartz.bankingapi.accounts.application.domain.model.AccountStatus;
import com.rschwartz.bankingapi.accounts.application.domain.model.AuditDate;
import com.rschwartz.bankingapi.accounts.application.domain.model.EntityId;
import com.rschwartz.bankingapi.accounts.application.domain.model.Money;
import com.rschwartz.bankingapi.common.annotations.Mapper;

@Mapper
public class AccountMapper {

  public Account mapJpaEntityToDomain(final AccountJpaEntity entity) {

    return Account.restore(
        new AccountId(entity.getId()),
        new EntityId(entity.getOwnerId()),
        entity.getNumber(),
        new AccountBalance(Money.ofDecimal(entity.getBalance())),
        new AuditDate(entity.getUpdateDate()),
        AccountStatus.valueOf(entity.getStatus()));
  }

  public AccountJpaEntity mapDomainToJpaEntity(final Account account) {

    return new AccountJpaEntity(
        account.getId().getValue(),
        account.getNumber(),
        account.getStatus().toString(),
        account.getOwnerId().getValue(),
        account.getBalance().getValue(),
        account.getUpdateDate().getValue(),
        Boolean.TRUE
    );
  }

}
