package com.rschwartz.bankingapi.accounts.aplication.service;

import com.rschwartz.bankingapi.accounts.aplication.port.in.useCase.SendMoneyUseCase;
import com.rschwartz.bankingapi.accounts.aplication.port.in.useCase.dto.SendMoneyInput;
import com.rschwartz.bankingapi.accounts.aplication.port.out.AccountLock;
import com.rschwartz.bankingapi.accounts.aplication.port.out.LoadAccountPortError;
import com.rschwartz.bankingapi.accounts.aplication.port.out.UpdateAccountStatePort;
import com.rschwartz.bankingapi.accounts.domain.AccountNew;
import com.rschwartz.bankingapi.accounts.domain.AccountNew.AccountId;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class SendMoneyService implements SendMoneyUseCase {

  private final LoadAccountPortError loadAccountPort;
  private final AccountLock accountLock;
  private final UpdateAccountStatePort updateAccountStatePort;

  @Override
  public boolean execute(final SendMoneyInput input) {

    checkThreshold(input);

    final LocalDateTime baselineDate = LocalDateTime.now().minusDays(10);

    final AccountNew sourceAccount = loadAccountPort.execute(
        input.getSourceAccountId(),
        baselineDate);

    final AccountNew targetAccount = loadAccountPort.execute(
        input.getTargetAccountId(),
        baselineDate);

    final AccountId sourceAccountId = sourceAccount.getId()
        .orElseThrow(() -> new IllegalStateException("expected source account ID not to be empty"));
    final AccountId targetAccountId = targetAccount.getId()
        .orElseThrow(() -> new IllegalStateException("expected target account ID not to be empty"));

    accountLock.lockAccount(sourceAccountId);
    if (!sourceAccount.withdraw(input.getMoney(), targetAccountId)) {
      accountLock.releaseAccount(sourceAccountId);
      return false;
    }

    accountLock.lockAccount(targetAccountId);
    if (!targetAccount.deposit(input.getMoney(), sourceAccountId)) {
      accountLock.releaseAccount(sourceAccountId);
      accountLock.releaseAccount(targetAccountId);
      return false;
    }

    updateAccountStatePort.execute(sourceAccount);
    updateAccountStatePort.execute(targetAccount);

    accountLock.releaseAccount(sourceAccountId);
    accountLock.releaseAccount(targetAccountId);

    // send notification Bacen
    return true;
  }

  private void checkThreshold(final SendMoneyInput command) {

    /*if (command.money().isGreaterThan(moneyTransferProperties.getMaximumTransferThreshold())){
      throw new ThresholdExceededException(moneyTransferProperties.getMaximumTransferThreshold(), command.money());
    }
     */

  }

}
