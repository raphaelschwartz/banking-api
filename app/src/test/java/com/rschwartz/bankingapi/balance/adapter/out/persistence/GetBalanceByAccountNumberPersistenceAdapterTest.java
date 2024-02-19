package com.rschwartz.bankingapi.balance.adapter.out.persistence;

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
import com.rschwartz.bankingapi.balance.adapter.out.entity.BalanceJpaEntity;
import com.rschwartz.bankingapi.balance.adapter.out.repository.BalanceRepository;
import com.rschwartz.bankingapi.balance.domain.Balance;
import com.rschwartz.bankingapi.common.template.BaseFixture;
import com.rschwartz.bankingapi.common.template.entity.BalanceJpaEntityTemplate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetBalanceByAccountNumberPersistenceAdapterTest {

  private static final String ACCOUNT_NUMBER = "012345";

  @InjectMocks
  private GetBalanceByAccountNumberPersistenceAdapter adapter;

  @Mock
  private BalanceRepository repository;

  @BeforeAll
  static void setUp() {
    FixtureFactoryLoader.loadTemplates(BaseFixture.ENTITY.getPath());
  }

  @Test
  @DisplayName("Should get optional empty when balance does not exist.")
  void balanceNotFound() {

    when(repository.findByAccountNumber(anyString()))
        .thenReturn(Optional.empty());

    final Optional<Balance> result = adapter.execute(ACCOUNT_NUMBER);

    assertAll(() -> {
      assertNotNull(result);
      assertFalse(result.isPresent());
    });

    verify(repository, times(1))
        .findByAccountNumber(anyString());
  }

  @Test
  @DisplayName("Should get balance by account number.")
  void balanceExists() {

    final BalanceJpaEntity entity = Fixture.from(BalanceJpaEntity.class)
        .gimme(BalanceJpaEntityTemplate.VALID);

    when(repository.findByAccountNumber(anyString()))
        .thenReturn(Optional.of(entity));

    final Optional<Balance> result = adapter.execute(ACCOUNT_NUMBER);

    assertAll(() -> {
      assertNotNull(result);
      assertTrue(result.isPresent());
    });

    verify(repository, times(1))
        .findByAccountNumber(anyString());
  }

}
