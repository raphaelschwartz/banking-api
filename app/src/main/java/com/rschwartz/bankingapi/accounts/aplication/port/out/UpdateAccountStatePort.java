package com.rschwartz.bankingapi.accounts.aplication.port.out;

import com.rschwartz.bankingapi.accounts.domain.AccountNew;

public interface UpdateAccountStatePort {

  void execute(AccountNew account);

}
