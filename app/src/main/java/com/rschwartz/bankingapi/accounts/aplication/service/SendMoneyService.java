package com.rschwartz.bankingapi.accounts.aplication.service;

import com.rschwartz.bankingapi.accounts.aplication.domain.Account;
import com.rschwartz.bankingapi.accounts.aplication.domain.Person;
import com.rschwartz.bankingapi.accounts.aplication.domain.Transaction;
import com.rschwartz.bankingapi.accounts.aplication.port.in.useCase.SendMoneyUseCase;
import com.rschwartz.bankingapi.accounts.aplication.port.in.useCase.dto.SendMoneyInput;
import com.rschwartz.bankingapi.accounts.aplication.port.out.LoadAccountPort;
import com.rschwartz.bankingapi.accounts.aplication.port.out.LoadPersonPort;
import com.rschwartz.bankingapi.accounts.aplication.port.out.NotificationBacenPort;
import com.rschwartz.bankingapi.accounts.aplication.port.out.RegisterTransactionPort;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class SendMoneyService implements SendMoneyUseCase {


  private final LoadAccountPort loadAccountPort;
  private final LoadPersonPort loadPersonPort;
  private final RegisterTransactionPort registerTransactionPort;
  private final NotificationBacenPort notificationBacenPort;

  @Override
  public void execute(final SendMoneyInput input) {

    final Account accountSource = getAccount(input.getSourceAccountId());
    final Account accountTarget = getAccount(input.getSourceAccountId());
    final Person person = getPerson(accountTarget.getOwnerId());

    final List<Transaction> transactions = Transaction.transferBetweenAccounts(accountSource,
        input.getMoney(), accountTarget, getDailyTransactions(accountSource));

    save(transactions);
    sendNotificationToBacen(transactions, person);

    accountSource.updateBalance();
    accountTarget.updateBalance();
  }

  private Account getAccount(final Long id) {

    return loadAccountPort.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(String.format("%s %s not found", "Account", id)));
  }

  private Person getPerson(final Long ownerId) {
    return loadPersonPort.findById(ownerId);
  }

  private BigDecimal getDailyTransactions(final Account accountSource) {
    // TODO must add find activities
    return new BigDecimal(100);
  }

  private void save(final List<Transaction> transactions) {

    transactions.stream()
        .forEach(registerTransactionPort::save);

  }
  private void sendNotificationToBacen(final List<Transaction> transactions, final Person person) {

    final Transaction transaction = transactions.stream()
        .filter(iten -> "TRANSFER_SENT".equals(iten.getType()))
        .findAny()
        .get();

    notificationBacenPort.send(transaction, person);
  }

}
