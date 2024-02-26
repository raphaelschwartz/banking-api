package com.rschwartz.bankingapi.accounts.application.domain.model;

import static com.rschwartz.bankingapi.accounts.application.domain.model.Transaction.ACCOUNT_ID_REQUIRED_ERROR_MESSAGE;
import static com.rschwartz.bankingapi.accounts.application.domain.model.Transaction.AMOUNT_REQUIRED_ERROR_MESSAGE;
import static com.rschwartz.bankingapi.accounts.application.domain.model.Transaction.BALANCE_REQUIRED_ERROR_MESSAGE;
import static com.rschwartz.bankingapi.accounts.application.domain.model.Transaction.CREATE_DATE_REQUIRED_ERROR_MESSAGE;
import static com.rschwartz.bankingapi.accounts.application.domain.model.Transaction.DETAIL_REQUIRED_ERROR_MESSAGE;
import static com.rschwartz.bankingapi.accounts.application.domain.model.Transaction.ID_REQUIRED_ERROR_MESSAGE;
import static com.rschwartz.bankingapi.accounts.application.domain.model.Transaction.KEY_REQUIRED_ERROR_MESSAGE;
import static com.rschwartz.bankingapi.accounts.application.domain.model.Transaction.TYPE_REQUIRED_ERROR_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TransactionTest {

  private static final EntityId ID = new EntityId(123L);
  private static final TransactionType TYPE = TransactionType.WITHDRAW;
  private static final TransactionDetail ACCOUNT_DETAIL = new TransactionDetail("Trânsferência enviada para 67890");
  private static final AuditDate CREATE_DATE = new AuditDate(LocalDateTime.now().minusSeconds(2));
  private static final Money AMOUNT = Money.ofInteger(1);
  private static final AccountId ACCOUNT_ID = new AccountId(12345L);
  private static final AccountBalance BALANCE_AFTER = new AccountBalance(Money.ofDecimal(BigDecimal.TEN));
  private static final TransactionKey KEY = new TransactionKey(UUID.randomUUID());

  @Test
  @DisplayName("Should get error when detail is null in create debit.")
  void detailRequiredErrorInCreateDebit() {

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> Transaction.createDebit(null, AMOUNT, ACCOUNT_ID, BALANCE_AFTER));

    assertEquals(DETAIL_REQUIRED_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should get error when amount is null in create debit.")
  void amountRequiredErrorInCreateDebit() {

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> Transaction.createDebit(ACCOUNT_DETAIL, null, ACCOUNT_ID, BALANCE_AFTER));

    assertEquals(AMOUNT_REQUIRED_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should get error when detail is null in create debit.")
  void accountIdRequiredErrorInCreateDebit() {

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> Transaction.createDebit(ACCOUNT_DETAIL, AMOUNT, null, BALANCE_AFTER));

    assertEquals(ACCOUNT_ID_REQUIRED_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should get error when balance after is null in create debit.")
  void balanceAfterRequiredErrorInCreateDebit() {

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> Transaction.createDebit(ACCOUNT_DETAIL, AMOUNT, ACCOUNT_ID, null));

    assertEquals(BALANCE_REQUIRED_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should create a transaction debit.")
  void createTransactionDebit() {

    final Transaction result = Transaction.createDebit(ACCOUNT_DETAIL, AMOUNT, ACCOUNT_ID, BALANCE_AFTER);

    assertAll(() -> {
      assertNotNull(result);
      assertNull(result.getId());
      assertEquals(TransactionType.WITHDRAW, result.getType());
      assertEquals(ACCOUNT_DETAIL, result.getDetail());
      assertNotNull(result.getCreateDate());
      assertEquals(AMOUNT, result.getAmount());
      assertEquals(ACCOUNT_ID, result.getAccountId());
      assertEquals(BALANCE_AFTER, result.getBalanceAfter());
      assertNotNull(result.getKey());
    });
  }

  @Test
  @DisplayName("Should get error when detail is null in create credit.")
  void detailRequiredErrorInCreateCredit() {

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> Transaction.createCredit(null, AMOUNT, ACCOUNT_ID, BALANCE_AFTER, KEY));

    assertEquals(DETAIL_REQUIRED_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should get error when amount is null in create credit.")
  void amountRequiredErrorInCreateCredit() {

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> Transaction.createCredit(ACCOUNT_DETAIL, null, ACCOUNT_ID, BALANCE_AFTER, KEY));

    assertEquals(AMOUNT_REQUIRED_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should get error when detail is null in create credit.")
  void accountIdRequiredErrorInCreateCredit() {

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> Transaction.createCredit(ACCOUNT_DETAIL, AMOUNT, null, BALANCE_AFTER, KEY));

    assertEquals(ACCOUNT_ID_REQUIRED_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should get error when balance after is null in create credit.")
  void balanceAfterRequiredErrorInCreateCredit() {

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> Transaction.createCredit(ACCOUNT_DETAIL, AMOUNT, ACCOUNT_ID, null, KEY));

    assertEquals(BALANCE_REQUIRED_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should get error when key after is null in create credit.")
  void keyRequiredErrorInCreateCredit() {

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> Transaction.createCredit(ACCOUNT_DETAIL, AMOUNT, ACCOUNT_ID, BALANCE_AFTER, null));

    assertEquals(KEY_REQUIRED_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should create a transaction credit.")
  void createTransactionCredit() {

    final Transaction result = Transaction.createCredit(ACCOUNT_DETAIL, AMOUNT, ACCOUNT_ID, BALANCE_AFTER, KEY);

    assertAll(() -> {
      assertNotNull(result);
      assertNull(result.getId());
      assertEquals(TransactionType.DEPOSIT, result.getType());
      assertEquals(ACCOUNT_DETAIL, result.getDetail());
      assertNotNull(result.getCreateDate());
      assertEquals(AMOUNT, result.getAmount());
      assertEquals(ACCOUNT_ID, result.getAccountId());
      assertEquals(BALANCE_AFTER, result.getBalanceAfter());
      assertEquals(KEY, result.getKey());
    });
  }

  @Test
  @DisplayName("Should get error when id is null in restore.")
  void idRequiredErrorInRestore() {

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> Transaction.restore(null, TYPE, ACCOUNT_DETAIL, CREATE_DATE, AMOUNT, ACCOUNT_ID, BALANCE_AFTER, KEY));

    assertEquals(ID_REQUIRED_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should get error when type is null in restore.")
  void typeRequiredErrorInRestore() {

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> Transaction.restore(ID, null, ACCOUNT_DETAIL, CREATE_DATE, AMOUNT, ACCOUNT_ID, BALANCE_AFTER, KEY));

    assertEquals(TYPE_REQUIRED_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should get error when detail is null in restore.")
  void detailRequiredErrorInRestore() {

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> Transaction.restore(ID, TYPE, null, CREATE_DATE, AMOUNT, ACCOUNT_ID, BALANCE_AFTER, KEY));

    assertEquals(DETAIL_REQUIRED_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should get error when create date is null in restore.")
  void createDateRequiredErrorInRestore() {

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> Transaction.restore(ID, TYPE, ACCOUNT_DETAIL, null, AMOUNT, ACCOUNT_ID, BALANCE_AFTER, KEY));

    assertEquals(CREATE_DATE_REQUIRED_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should get error when amount is null in restore.")
  void amountRequiredErrorInRestore() {

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> Transaction.restore(ID, TYPE, ACCOUNT_DETAIL, CREATE_DATE, null, ACCOUNT_ID, BALANCE_AFTER, KEY));

    assertEquals(AMOUNT_REQUIRED_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should get error when detail is null in restore.")
  void accountIdRequiredErrorInRestore() {

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> Transaction.restore(ID, TYPE, ACCOUNT_DETAIL, CREATE_DATE, AMOUNT, null, BALANCE_AFTER, KEY));

    assertEquals(ACCOUNT_ID_REQUIRED_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should get error when balance after is null in restore.")
  void balanceAfterRequiredErrorInRestore() {

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> Transaction.restore(ID, TYPE, ACCOUNT_DETAIL, CREATE_DATE, AMOUNT, ACCOUNT_ID, null, KEY));

    assertEquals(BALANCE_REQUIRED_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should get error when key after is null in restore.")
  void keyRequiredErrorInRestore() {

    final IllegalArgumentException result = assertThrows(IllegalArgumentException.class,
        () -> Transaction.restore(ID, TYPE, ACCOUNT_DETAIL, CREATE_DATE, AMOUNT, ACCOUNT_ID, BALANCE_AFTER, null));

    assertEquals(KEY_REQUIRED_ERROR_MESSAGE, result.getMessage());
  }

  @Test
  @DisplayName("Should restore a transaction.")
  void restoreTransaction() {

    final Transaction result = Transaction.restore(ID, TYPE, ACCOUNT_DETAIL, CREATE_DATE, AMOUNT, ACCOUNT_ID, BALANCE_AFTER, KEY);

    assertAll(() -> {
      assertNotNull(result);
      assertEquals(ID, result.getId());
      assertEquals(TYPE, result.getType());
      assertEquals(ACCOUNT_DETAIL, result.getDetail());
      assertEquals(CREATE_DATE, result.getCreateDate());
      assertEquals(AMOUNT, result.getAmount());
      assertEquals(ACCOUNT_ID, result.getAccountId());
      assertEquals(BALANCE_AFTER, result.getBalanceAfter());
      assertEquals(KEY, result.getKey());
    });
  }

}
