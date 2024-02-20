package com.rschwartz.bankingapi.accounts.integration.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.rschwartz.bankingapi.BankingApiApplication;
import com.rschwartz.bankingapi.accounts.adapter.out.persistence.LoadAccountPersistenceAdapter;
import com.rschwartz.bankingapi.accounts.adapter.out.repository.AccountRepository;
import com.rschwartz.bankingapi.accounts.domain.Account;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(classes = BankingApiApplication.class)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql({"/data/integration/account/LoadAccountPersistenceAdapterTest.sql"})
public class LoadAccountPersistenceAdapterIntegrationTest {

  @Autowired
  private LoadAccountPersistenceAdapter adapter;

  @Autowired
  private AccountRepository repository;

  @Test
  @DisplayName("Should get optional empty when balance does not exist.")
  void balanceNotFound() {

    final Optional<Account> result = adapter.execute("1");

    assertAll(() -> {
      assertNotNull(result);
      assertTrue(result.isEmpty());
    });

  }

  @Test
  @DisplayName("Should get balance by account number.")
  void balanceExists() {

    final Optional<Account> result = adapter.execute("0123456789");

    assertAll(() -> {
      assertNotNull(result);
      assertTrue(result.isPresent());
    });

  }

}
