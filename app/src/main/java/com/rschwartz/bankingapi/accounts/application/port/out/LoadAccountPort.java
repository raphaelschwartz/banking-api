package com.rschwartz.bankingapi.accounts.application.port.out;

import com.rschwartz.bankingapi.accounts.application.domain.model.Account;
import java.util.Optional;

public interface LoadAccountPort {

  Optional<Account> findById(Long id);

}
