package com.rschwartz.bankingapi.accounts.aplication.port.out;

import com.rschwartz.bankingapi.accounts.domain.AccountNew;
import com.rschwartz.bankingapi.accounts.domain.AccountNew.AccountId;
import java.time.LocalDateTime;

public interface LoadAccountPortError {
  AccountNew execute(AccountId accountId, LocalDateTime baselineDate);

}
