package com.rschwartz.bankingapi.accounts.application.domain.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.rschwartz.bankingapi.accounts.application.domain.Person;
import com.rschwartz.bankingapi.accounts.application.domain.model.AccountId;
import com.rschwartz.bankingapi.accounts.application.domain.model.Transaction;
import com.rschwartz.bankingapi.accounts.application.domain.service.exception.ThresholdExceededException;
import com.rschwartz.bankingapi.accounts.application.port.in.useCase.dto.SendMoneyInput;
import com.rschwartz.bankingapi.accounts.application.port.out.LoadAccountPort;
import com.rschwartz.bankingapi.accounts.application.port.out.LoadPersonPort;
import com.rschwartz.bankingapi.accounts.application.port.out.NotificationBacenPort;
import com.rschwartz.bankingapi.accounts.application.port.out.RegisterTransactionPort;
import com.rschwartz.bankingapi.accounts.application.port.out.SearchTransactionsPort;
import com.rschwartz.bankingapi.common.template.BaseFixture;
import com.rschwartz.bankingapi.common.template.domain.AccountTemplate;
import com.rschwartz.bankingapi.common.template.domain.TransactionTemplate;
import com.rschwartz.bankingapi.common.template.input.SendMoneyInputTemplate;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SendMoneyServiceTest {

  @InjectMocks
  private SendMoneyService service;

  @Mock
  private LoadAccountPort loadAccountPort;

  @Mock
  private SearchTransactionsPort searchTransactionsPort;

  @Mock
  private LoadPersonPort loadPersonPort;

  @Mock
  private RegisterTransactionPort registerTransactionPort;
  @Mock
  private NotificationBacenPort notificationBacenPort;

  @BeforeAll
  static void setUp() {
    FixtureFactoryLoader.loadTemplates(BaseFixture.INPUT.getPath());
  }

  @Test
  @DisplayName("Should get error when source account does not exist.")
  void sourceAccountNotFound() {

    final SendMoneyInput input = Fixture.from(SendMoneyInput.class)
        .gimme(SendMoneyInputTemplate.VALID);

    final Long sourceAccountId = input.getSourceAccountId();
    when(loadAccountPort.findById(sourceAccountId))
        .thenReturn(Optional.empty());

    final EntityNotFoundException result = assertThrows(EntityNotFoundException.class,
        () -> service.execute(input));

    final String message = String.format("Account %s not found.", sourceAccountId);
    assertEquals(message, result.getMessage());

    verify(loadAccountPort, times(1))
        .findById(anyLong());

    verify(searchTransactionsPort, never())
        .search(any(AccountId.class), any(LocalDateTime.class));

    verify(loadPersonPort, never())
        .findById(anyLong());

    verify(registerTransactionPort, never())
        .save(any(Transaction.class));

    verify(notificationBacenPort, never())
        .send(any(Transaction.class), any(Person.class));
  }

  @Test
  @DisplayName("Should get error when daily threshold is exceeded.")
  void dailyThresholdExceeded() {

    final SendMoneyInput input = Fixture.from(SendMoneyInput.class)
        .gimme(SendMoneyInputTemplate.VALID);

    final Long sourceAccountId = input.getSourceAccountId();
    when(loadAccountPort.findById(sourceAccountId))
        .thenReturn(Optional.of(AccountTemplate.getTemplateOne()));

    final List<Transaction> transactions = List.of(
        TransactionTemplate.getValidRestoreWithdrawTransferTemplate(new BigDecimal(250)),
        TransactionTemplate.getValidRestoreWithdrawTransferTemplate(new BigDecimal(350)),
        TransactionTemplate.getValidRestoreWithdrawTransferTemplate(new BigDecimal(400))
    );
    when(searchTransactionsPort.search(any(AccountId.class), any(LocalDateTime.class)))
        .thenReturn(transactions);

    final ThresholdExceededException result = assertThrows(ThresholdExceededException.class,
        () -> service.execute(input));

    final String message = "Maximum threshold for transferring money exceeded: tried to transfer 10 but threshold available is 0.";
    assertEquals(message, result.getMessage());

    verify(loadAccountPort, times(1))
        .findById(anyLong());

    verify(searchTransactionsPort, times(1))
        .search(any(AccountId.class), any(LocalDateTime.class));

    verify(loadPersonPort, never())
        .findById(anyLong());

    verify(registerTransactionPort, never())
        .save(any(Transaction.class));

    verify(notificationBacenPort, never())
        .send(any(Transaction.class), any(Person.class));
  }

  @Test
  @DisplayName("Should get error when target account does not exist.")
  void targetAccountNotFound() {

    final SendMoneyInput input = Fixture.from(SendMoneyInput.class)
        .gimme(SendMoneyInputTemplate.VALID);

    final Long sourceAccountId = input.getSourceAccountId();
    when(loadAccountPort.findById(sourceAccountId))
        .thenReturn(Optional.of(AccountTemplate.getTemplateOne()));

    final Long targetAccountId = input.getTargetAccountId();
    when(loadAccountPort.findById(targetAccountId))
        .thenReturn(Optional.empty());

    final EntityNotFoundException result = assertThrows(EntityNotFoundException.class,
        () -> service.execute(input));

    final String message = String.format("Account %s not found.", targetAccountId);
    assertEquals(message, result.getMessage());

    verify(loadAccountPort, times(2))
        .findById(anyLong());

    verify(loadPersonPort, never())
        .findById(anyLong());

    verify(registerTransactionPort, never())
        .save(any(Transaction.class));

    verify(notificationBacenPort, never())
        .send(any(Transaction.class), any(Person.class));
  }

  @Disabled("verificar como funciona a integração no client")
  @Test
  @DisplayName("Should get error when person does not exist.")
  void personNotFound() {

    final SendMoneyInput input = Fixture.from(SendMoneyInput.class)
        .gimme(SendMoneyInputTemplate.VALID);

    final Long sourceAccountId = input.getSourceAccountId();
    when(loadAccountPort.findById(sourceAccountId))
        .thenReturn(Optional.of(AccountTemplate.getTemplateOne()));

    final Long targetAccountId = input.getTargetAccountId();
    when(loadAccountPort.findById(targetAccountId))
        .thenReturn(Optional.of(AccountTemplate.getTemplateTwo()));

    // FIXME
    when(loadPersonPort.findById(anyLong()))
        .thenReturn(null);

    assertDoesNotThrow(() -> service.execute(input));

    verify(loadAccountPort, times(2))
        .findById(anyLong());

    verify(loadPersonPort, times(1))
        .findById(anyLong());

    verify(registerTransactionPort, never())
        .save(any(Transaction.class));

    verify(notificationBacenPort, never())
        .send(any(Transaction.class), any(Person.class));
  }

  @Test
  @DisplayName("Should send money.")
  void sendMoney() {

    final SendMoneyInput input = Fixture.from(SendMoneyInput.class)
        .gimme(SendMoneyInputTemplate.VALID);

    final Long sourceAccountId = input.getSourceAccountId();
    when(loadAccountPort.findById(sourceAccountId))
        .thenReturn(Optional.of(AccountTemplate.getTemplateOne()));

    final Long targetAccountId = input.getTargetAccountId();
    when(loadAccountPort.findById(targetAccountId))
        .thenReturn(Optional.of(AccountTemplate.getTemplateTwo()));

    assertDoesNotThrow(() -> service.execute(input));

    verify(loadAccountPort, times(2))
        .findById(anyLong());

    verify(loadPersonPort, times(1))
        .findById(anyLong());

    verify(registerTransactionPort, times(2))
        .save(any(Transaction.class));

    verify(notificationBacenPort, never())
        .send(any(Transaction.class), any(Person.class));
  }

}
