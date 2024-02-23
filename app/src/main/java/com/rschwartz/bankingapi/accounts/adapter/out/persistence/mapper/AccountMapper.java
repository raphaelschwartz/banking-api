package com.rschwartz.bankingapi.accounts.adapter.out.persistence.mapper;

import com.rschwartz.bankingapi.accounts.adapter.out.persistence.entity.AccountJpaEntity;
import com.rschwartz.bankingapi.accounts.aplication.domain.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

  public Account mapEntityToDomain(final AccountJpaEntity entity) {

    return Account.restore(
        entity.getId(),
        entity.getOwnerId(),
        entity.getNumber(),
        entity.getBalance(),
        entity.getAvailableLimit(),
        entity.getUpdateDate(),
        entity.getStatus());
  }

}
