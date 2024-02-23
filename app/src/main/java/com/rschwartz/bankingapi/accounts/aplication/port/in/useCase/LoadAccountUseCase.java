package com.rschwartz.bankingapi.accounts.aplication.port.in.useCase;

import com.rschwartz.bankingapi.accounts.aplication.port.out.dto.AccountOutput;
import java.util.Optional;

public interface LoadAccountUseCase {

  Optional<AccountOutput> execute(Long id);

}
