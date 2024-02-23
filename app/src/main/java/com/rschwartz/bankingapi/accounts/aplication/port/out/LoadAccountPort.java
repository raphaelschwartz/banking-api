package com.rschwartz.bankingapi.accounts.aplication.port.out;

import com.rschwartz.bankingapi.accounts.aplication.domain.Account;
import java.util.Optional;

public interface LoadAccountPort {

  Optional<Account> findById(Long id);

}
