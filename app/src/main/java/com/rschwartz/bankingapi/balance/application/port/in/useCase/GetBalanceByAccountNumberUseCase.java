package com.rschwartz.bankingapi.balance.application.port.in.useCase;

import com.rschwartz.bankingapi.balance.application.port.out.dto.BalanceOutput;
import java.util.Optional;

public interface GetBalanceByAccountNumberUseCase {

  Optional<BalanceOutput> execute(String accountNumber);

}
