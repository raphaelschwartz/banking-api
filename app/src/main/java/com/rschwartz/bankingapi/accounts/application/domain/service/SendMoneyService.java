package com.rschwartz.bankingapi.accounts.application.domain.service;

import com.rschwartz.bankingapi.accounts.application.domain.Person;
import com.rschwartz.bankingapi.accounts.application.domain.model.Account;
import com.rschwartz.bankingapi.accounts.application.domain.model.AccountId;
import com.rschwartz.bankingapi.accounts.application.domain.model.Money;
import com.rschwartz.bankingapi.accounts.application.domain.model.Transaction;
import com.rschwartz.bankingapi.accounts.application.domain.model.TransactionType;
import com.rschwartz.bankingapi.accounts.application.domain.service.exception.ThresholdExceededException;
import com.rschwartz.bankingapi.accounts.application.port.in.useCase.SendMoneyUseCase;
import com.rschwartz.bankingapi.accounts.application.port.in.useCase.dto.SendMoneyInput;
import com.rschwartz.bankingapi.accounts.application.port.out.LoadAccountPort;
import com.rschwartz.bankingapi.accounts.application.port.out.LoadPersonPort;
import com.rschwartz.bankingapi.accounts.application.port.out.NotificationBacenPort;
import com.rschwartz.bankingapi.accounts.application.port.out.RegisterTransactionPort;
import com.rschwartz.bankingapi.accounts.application.port.out.SearchTransactionsPort;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class SendMoneyService implements SendMoneyUseCase {

  private static final Money MAXIMUM_TRANSFER_THRESHOLD = Money.ofInteger(1_000L);

  private final LoadAccountPort loadAccountPort;
  private final LoadPersonPort loadPersonPort;
  private final SearchTransactionsPort searchTransactionsPort;
  private final RegisterTransactionPort registerTransactionPort;
  private final NotificationBacenPort notificationBacenPort;

  @Override
  public void execute(final SendMoneyInput input) {

    final Account sourceAccount = getAccount(input.getSourceAccountId());
    checkThreshold(input);

    final Account targetAccount = getAccount(input.getTargetAccountId());
    final Person person = getPerson(sourceAccount.getOwnerId().getValue());

    final Transaction withdraw = sourceAccount.withdraw(input.getAmount(), targetAccount.getNumber());
    final Transaction deposit = targetAccount.deposit(input.getAmount(), sourceAccount.getNumber(), withdraw.getKey());

    registerTransactionPort.save(withdraw);
    registerTransactionPort.save(deposit);

    sourceAccount.updateBalance();
    targetAccount.updateBalance();

    //sendNotificationToBacen(transactions, person);
  }

  private Account getAccount(final Long id) {

    return loadAccountPort.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(String.format("Account %s not found.", id)));
  }

  private void checkThreshold(final SendMoneyInput input) {

    final Money totalTransactionsOfDay = getTotalTransactions(input.getSourceAccountId());
    final Money thresholdAvailable = MAXIMUM_TRANSFER_THRESHOLD.minus(totalTransactionsOfDay);

    if (isDailyThresholdExceeded(input.getAmount(), thresholdAvailable)) {
      throw new ThresholdExceededException(input.getAmount(), thresholdAvailable);
    }

  }

  private Money getTotalTransactions(final Long sourceAccountId) {

    final LocalDateTime baselineDate = LocalDate.now().atStartOfDay();

    return searchTransactionsPort.search(new AccountId(sourceAccountId), baselineDate)
        .stream()
        .filter(transaction -> TransactionType.WITHDRAW.equals(transaction.getType()))
        .map(Transaction::getAmount)
        .reduce(Money.ZERO, Money::plus);
  }

  private boolean isDailyThresholdExceeded(final Money amount, final Money thresholdAvailable) {
    return amount.isGreaterThan(thresholdAvailable);
  }

  private Person getPerson(final Long ownerId) {
    return loadPersonPort.findById(ownerId);
  }

  private void sendNotificationToBacen(final List<Transaction> transactions, final Person person) {

    final Transaction transaction = transactions.stream()
        .filter(iten -> "TRANSFER_SENT".equals(iten.getType()))
        .findAny()
        .get();

    notificationBacenPort.send(transaction, person);
  }

}
