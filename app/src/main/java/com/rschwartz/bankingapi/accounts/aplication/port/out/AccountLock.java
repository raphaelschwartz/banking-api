package com.rschwartz.bankingapi.accounts.aplication.port.out;

import com.rschwartz.bankingapi.accounts.domain.AccountNew.AccountId;

public interface AccountLock {

	void lockAccount(AccountId accountId);

	void releaseAccount(AccountId accountId);

}
