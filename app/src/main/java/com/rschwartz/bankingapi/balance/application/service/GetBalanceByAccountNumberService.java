package com.rschwartz.bankingapi.balance.application.service;

import com.rschwartz.bankingapi.balance.application.port.in.useCase.GetBalanceByAccountNumberUseCase;
import com.rschwartz.bankingapi.balance.application.port.out.GetBalanceByAccountNumberPort;
import com.rschwartz.bankingapi.balance.application.port.out.dto.BalanceOutput;
import com.rschwartz.bankingapi.balance.domain.Balance;
import java.math.BigDecimal;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetBalanceByAccountNumberService implements GetBalanceByAccountNumberUseCase {

  private final GetBalanceByAccountNumberPort port;

  @Override
  public Optional<BalanceOutput> execute(final String accountNumber) {

    return port.execute(accountNumber)
        .map(this::convert);
  }

  private BalanceOutput convert(final Balance balance) {

    return new BalanceOutput(
        balance.getAccountNumber(),
        new BigDecimal(balance.getValue()), // FIXME
        balance.getLimit(),
        balance.getUpdateDate());
  }

}
