package com.rschwartz.bankingapi.accounts.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.rschwartz.bankingapi.accounts.adapter.out.entity.AccountJpaEntity;
import com.rschwartz.bankingapi.accounts.adapter.out.repository.AccountRepository;
import com.rschwartz.bankingapi.accounts.domain.Account;
import com.rschwartz.bankingapi.common.template.BaseFixture;
import com.rschwartz.bankingapi.common.template.entity.AccountJpaEntityTemplate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LoadAccountPersistenceAdapterTest {

  private static final String ACCOUNT_NUMBER = "012345";

  @InjectMocks
  private LoadAccountPersistenceAdapter adapter;

  @Mock
  private AccountRepository repository;

  @BeforeAll
  static void setUp() {
    FixtureFactoryLoader.loadTemplates(BaseFixture.ENTITY.getPath());
  }

  @Test
  @DisplayName("Should get optional empty when balance does not exist.")
  void balanceNotFound() {

    when(repository.findByNumber(anyString()))
        .thenReturn(Optional.empty());

    final Optional<Account> result = adapter.execute(ACCOUNT_NUMBER);

    assertAll(() -> {
      assertNotNull(result);
      assertFalse(result.isPresent());
    });

    verify(repository, times(1))
        .findByNumber(anyString());
  }

  @Test
  @DisplayName("Should get balance by account number.")
  void balanceExists() {

    final AccountJpaEntity entity = Fixture.from(AccountJpaEntity.class)
        .gimme(AccountJpaEntityTemplate.VALID);

    when(repository.findByNumber(anyString()))
        .thenReturn(Optional.of(entity));

    final Optional<Account> result = adapter.execute(ACCOUNT_NUMBER);

    assertAll(() -> {
      assertNotNull(result);
      assertTrue(result.isPresent());
    });

    verify(repository, times(1))
        .findByNumber(anyString());
  }

}
