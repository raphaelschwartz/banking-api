package com.rschwartz.bankingapi.accounts.integration.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.redis.testcontainers.RedisContainer;
import com.rschwartz.bankingapi.accounts.adapter.out.persistence.LoadAccountPersistenceAdapter;
import com.rschwartz.bankingapi.accounts.adapter.out.persistence.entity.AccountJpaEntity;
import com.rschwartz.bankingapi.accounts.adapter.out.persistence.mapper.AccountMapper;
import com.rschwartz.bankingapi.accounts.adapter.out.persistence.repository.AccountRepository;
import com.rschwartz.bankingapi.accounts.application.domain.model.Account;
import com.rschwartz.bankingapi.accounts.application.domain.model.AccountBalance;
import com.rschwartz.bankingapi.accounts.application.domain.model.AccountId;
import com.rschwartz.bankingapi.accounts.application.domain.model.AccountStatus;
import com.rschwartz.bankingapi.accounts.application.domain.model.AuditDate;
import com.rschwartz.bankingapi.accounts.application.domain.model.EntityId;
import com.rschwartz.bankingapi.accounts.application.domain.model.Money;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers(disabledWithoutDocker = true)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql({"/data/integration/account/LoadAccountPersistenceAdapterTest.sql"})
@EnableCaching
public class LoadAccountPersistenceAdapterIntegrationTest {

  private static final Long ACCOUNT_ID = 123L;

  @Autowired
  private LoadAccountPersistenceAdapter adapter;

  @Autowired
  private AccountRepository repository;

  @Autowired
  private AccountMapper mapper;

  @Container
  @ServiceConnection
  private static final RedisContainer REDIS_CONTAINER = new RedisContainer(
      DockerImageName.parse("redis:latest")).withExposedPorts(6379);

  @Test
  @DisplayName("Should validate that redis is running.")
  void verifyRedisIsRunning() {
    assertTrue(REDIS_CONTAINER.isRunning());
  }

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

    final Optional<Account> result = adapter.findById(ACCOUNT_ID);

    assertAll(() -> {
      assertNotNull(result);
      assertTrue(result.isPresent());
    });

    final Account account = Account.restore(
        new AccountId(ACCOUNT_ID),
        new EntityId(1234L),
        "0001",
        new AccountBalance(Money.ofInteger(100)),
        new AuditDate(LocalDateTime.now().minusMinutes(30)),
        AccountStatus.ACTIVE);

    final AccountJpaEntity accountJpaEntity = mapper.mapDomainToJpaEntity(account);
    repository.save(accountJpaEntity);

    final Optional<Account> cacheableAccount = adapter.findById(ACCOUNT_ID);
    assertEquals(cacheableAccount, result);
  }

}
