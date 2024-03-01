package com.rschwartz.bankingapi.accounts.application.port.out;

import com.rschwartz.bankingapi.accounts.application.domain.model.Account;

public interface UpdateAccountBalancePort {

  Account save(Account account);

}
