package com.rschwartz.bankingapi.accounts.application.port.in.useCase;

import com.rschwartz.bankingapi.accounts.application.port.out.dto.AccountOutput;
import java.util.Optional;

public interface LoadAccountUseCase {

  Optional<AccountOutput> execute(Long id);

}
