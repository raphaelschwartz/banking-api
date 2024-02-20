package com.rschwartz.bankingapi.accounts.adapter.out.persistence;

import com.rschwartz.bankingapi.accounts.aplication.port.out.AccountLock;
import com.rschwartz.bankingapi.accounts.domain.AccountNew.AccountId;
import org.springframework.stereotype.Component;

@Component
class NoOpAccountLock implements AccountLock {

  @Override
  public void lockAccount(final AccountId accountId) {
    // TODO
  }

  @Override
  public void releaseAccount(final AccountId accountId) {
    // TODO
  }

}
