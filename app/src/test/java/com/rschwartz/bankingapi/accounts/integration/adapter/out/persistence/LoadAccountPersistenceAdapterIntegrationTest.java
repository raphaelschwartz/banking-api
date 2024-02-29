package com.rschwartz.bankingapi.accounts.integration.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.rschwartz.bankingapi.BankingApiApplication;
import com.rschwartz.bankingapi.accounts.adapter.out.persistence.LoadAccountPersistenceAdapter;
import com.rschwartz.bankingapi.accounts.adapter.out.persistence.repository.AccountRepository;
import com.rschwartz.bankingapi.accounts.application.domain.model.Account;
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
  @DisplayName("Should get optional empty when account does not exist.")
  void accountNotFound() {

    final Optional<Account> result = adapter.findById(999L);

    assertAll(() -> {
      assertNotNull(result);
      assertTrue(result.isEmpty());
    });

  }

  @Test
  @DisplayName("Should get account by id.")
  void accountExists() {

    final Optional<Account> result = adapter.findById(123L);

    assertAll(() -> {
      assertNotNull(result);
      assertTrue(result.isPresent());
    });

  }

}
