package com.rschwartz.bankingapi.accounts.aplication.port.out;

import com.rschwartz.bankingapi.accounts.domain.Account;
import java.util.Optional;

public interface LoadAccountPort {

  Optional<Account> execute(String accountNumber);

}
