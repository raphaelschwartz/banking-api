package com.rschwartz.bankingapi.balance.application.port.out;

import com.rschwartz.bankingapi.balance.domain.Balance;
import java.util.Optional;

public interface GetBalanceByAccountNumberPort {

  Optional<Balance> execute(String accountNumber);

}
