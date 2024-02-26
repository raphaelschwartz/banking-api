package com.rschwartz.bankingapi.accounts.application.domain.model;

import static com.rschwartz.bankingapi.accounts.application.domain.model.Account.ACCOUNT_NUMBER_IDENTICAL_ERROR_MESSAGE;
import static com.rschwartz.bankingapi.accounts.application.domain.model.Account.ACCOUNT_NUMBER_REQUIRED_ERROR_MESSAGE;
import static com.rschwartz.bankingapi.accounts.application.domain.model.Account.AMOUNT_MINIMUM_ERROR_MESSAGE;
import static com.rschwartz.bankingapi.accounts.application.domain.model.Account.BALANCE_REQUIRED_ERROR_MESSAGE;
import static com.rschwartz.bankingapi.accounts.application.domain.model.Account.MONEY_REQUIRED_ERROR_MESSAGE;
import static com.rschwartz.bankingapi.accounts.application.domain.model.Account.NUMBER_REQUIRED_ERROR_MESSAGE;
import static com.rschwartz.bankingapi.accounts.application.domain.model.Account.STATUS_REQUIRED_ERROR_MESSAGE;
import static com.rschwartz.bankingapi.accounts.application.domain.model.Account.TRANSACTION_KEY_REQUIRED_ERROR_MESSAGE;
import static com.rschwartz.bankingapi.accounts.application.domain.model.Account.UPDATE_DATE_REQUIRED_ERROR_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.rschwartz.bankingapi.common.template.domain.AccountTemplate;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AccountTest {

  private static final AccountId ID = new AccountId(1L);
  private static final EntityId OWNER_ID = new EntityId(1234L);
  private static final String NUMBER = "0001";
  private static final AccountBalance BALANCE = new AccountBalance(Money.ofInteger(100));
  private static final AuditDate UPDATE_DATE = new AuditDate(LocalDateTime.now().minusMinutes(30));
  private static final AccountStatus STATUS = AccountStatus.ACTIVE;

  private static final Money MONEY = Money.ofInteger(1);
  private static final String SOURCE_ACCOUNT_NUMBER = "00112233";
  private static final String TARGET_ACCOUNT_NUMBER = "00445566";
  private static final TransactionKey KEY = new TransactionKey(UUID.randomUUID());

  @Test
  @DisplayName("Should get error when id is null in restore.")
  void idRequiredErrorInRestore() {

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> Account.restore(null, OWNER_ID, NUMBER, BALANCE, UPDATE_DATE, STATUS));

    assertEquals(Account.ID_REQUIRED_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should get error when owner id is null in restore.")
  void ownerIdRequiredErrorInRestore() {

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> Account.restore(ID, null, NUMBER, BALANCE, UPDATE_DATE, STATUS));

    assertEquals(Account.OWNER_ID_REQUIRED_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should get error when number is null in restore.")
  void numberRequiredErrorInRestore() {

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> Account.restore(ID, OWNER_ID, null, BALANCE, UPDATE_DATE, STATUS));

    assertEquals(NUMBER_REQUIRED_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should get error when balance is null in restore.")
  void balanceRequiredErrorInRestore() {

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> Account.restore(ID, OWNER_ID, NUMBER, null, UPDATE_DATE, STATUS));

    assertEquals(BALANCE_REQUIRED_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should get error when update date is null in restore.")
  void updateDateRequiredErrorInRestore() {

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> Account.restore(ID, OWNER_ID, NUMBER, BALANCE, null, STATUS));

    assertEquals(UPDATE_DATE_REQUIRED_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should get error when status is null in restore.")
  void statusRequiredErrorInRestore() {

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> Account.restore(ID, OWNER_ID, NUMBER, BALANCE, UPDATE_DATE, null));

    assertEquals(STATUS_REQUIRED_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should restore a account.")
  void restoreAccount() {

    final Account result = Account.restore(ID, OWNER_ID, NUMBER, BALANCE, UPDATE_DATE, STATUS);

    assertAll(() -> {
      assertNotNull(result);
      assertEquals(ID, result.getId());
      assertEquals(OWNER_ID, result.getOwnerId());
      assertEquals(NUMBER, result.getNumber());
      assertEquals(BALANCE, result.getBalance());
      assertEquals(UPDATE_DATE, result.getUpdateDate());
      assertEquals(STATUS, result.getStatus());
    });
  }

  @Test
  @DisplayName("Should get error when money is null in withdraw.")
  void moneyRequiredErrorInWithdraw() {

    final Account sourceAccount = AccountTemplate.getTemplateOne();

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> sourceAccount.withdraw(null, TARGET_ACCOUNT_NUMBER));

    assertEquals(MONEY_REQUIRED_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should get error when amount is less than zero in withdraw.")
  void moneyMinimumValueErrorInWithdraw() {

    final Account sourceAccount = AccountTemplate.getTemplateOne();

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> sourceAccount.withdraw(Money.ZERO, TARGET_ACCOUNT_NUMBER));

    assertEquals(AMOUNT_MINIMUM_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should get error when account number is null in withdraw.")
  void accountNumberRequiredErrorInWithdraw() {

    final Account sourceAccount = AccountTemplate.getTemplateOne();

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> sourceAccount.withdraw(MONEY, null));

    assertEquals(ACCOUNT_NUMBER_REQUIRED_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should get error when account number is the same in withdraw.")
  void accountNumberIdenticalErrorInWithdraw() {

    final Account sourceAccount = AccountTemplate.getTemplateOne();

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> sourceAccount.withdraw(MONEY, sourceAccount.getNumber()));

    assertEquals(ACCOUNT_NUMBER_IDENTICAL_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should create a withdraw.")
  void createWithdraw() {

    final Account sourceAccount = AccountTemplate.getTemplateOne();
    final Account targetAccount = AccountTemplate.getTemplateTwo();

    final Transaction result = sourceAccount.withdraw(MONEY, targetAccount.getNumber());

    assertAll(() -> {
      assertNotNull(result);
      assertNull(result.getId());
      assertEquals(TransactionType.WITHDRAW, result.getType());
      assertEquals(String.format("Trânsferência enviada para %s", targetAccount.getNumber()),
          result.getDetail().getValue());
      assertNotNull(result.getCreateDate());
      assertEquals(MONEY, result.getAmount());
      assertEquals(sourceAccount.getId().getValue(), result.getAccountId().getValue());
      assertEquals(sourceAccount.getBalance().getValue().subtract(MONEY.getValue()), result.getBalanceAfter().getValue());
      assertNotNull(result.getKey());
    });
  }

  @Test
  @DisplayName("Should get error when money is null in deposit.")
  void moneyRequiredErrorInDeposit() {

    final Account targetAccount = AccountTemplate.getTemplateTwo();

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> targetAccount.deposit(null, SOURCE_ACCOUNT_NUMBER, KEY));

    assertEquals(MONEY_REQUIRED_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should get error when amount is less than zero in deposit.")
  void moneyMinimumValueErrorInDeposit() {

    final Account targetAccount = AccountTemplate.getTemplateTwo();

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> targetAccount.deposit(Money.ZERO, SOURCE_ACCOUNT_NUMBER, KEY));

    assertEquals(AMOUNT_MINIMUM_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should get error when account number is null in deposit.")
  void accountNumberRequiredErrorInDeposit() {

    final Account targetAccount = AccountTemplate.getTemplateTwo();

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> targetAccount.deposit(MONEY, null, KEY));

    assertEquals(ACCOUNT_NUMBER_REQUIRED_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should get error when account number is the same in deposit.")
  void accountNumberIdenticalErrorInDeposit() {

    final Account targetAccount = AccountTemplate.getTemplateTwo();

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> targetAccount.deposit(MONEY, targetAccount.getNumber(), KEY));

    assertEquals(ACCOUNT_NUMBER_IDENTICAL_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should get error when transaction key is null in deposit.")
  void transactionKeyRequiredErrorInDeposit() {

    final Account targetAccount = AccountTemplate.getTemplateTwo();

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> targetAccount.deposit(MONEY, SOURCE_ACCOUNT_NUMBER, null));

    assertEquals(TRANSACTION_KEY_REQUIRED_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should create a deposit.")
  void createDeposit() {

    final Account targetAccount = AccountTemplate.getTemplateTwo();

    final Transaction result = targetAccount.deposit(MONEY, SOURCE_ACCOUNT_NUMBER, KEY);

    assertAll(() -> {
      assertNotNull(result);
      assertNull(result.getId());
      assertEquals(TransactionType.DEPOSIT, result.getType());
      assertEquals(String.format("Trânsferência recebida de %s", SOURCE_ACCOUNT_NUMBER),
          result.getDetail().getValue());
      assertNotNull(result.getCreateDate());
      assertEquals(MONEY, result.getAmount());
      assertEquals(targetAccount.getId().getValue(), result.getAccountId().getValue());
      assertEquals(targetAccount.getBalance().getValue().add(MONEY.getValue()), result.getBalanceAfter().getValue());
      assertEquals(KEY, result.getKey());
    });
  }

}
