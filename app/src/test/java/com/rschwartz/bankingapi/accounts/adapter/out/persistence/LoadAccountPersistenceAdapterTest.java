package com.rschwartz.bankingapi.accounts.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.rschwartz.bankingapi.accounts.adapter.out.persistence.entity.AccountJpaEntity;
import com.rschwartz.bankingapi.accounts.adapter.out.persistence.mapper.AccountMapper;
import com.rschwartz.bankingapi.accounts.adapter.out.persistence.repository.AccountRepository;
import com.rschwartz.bankingapi.accounts.application.domain.model.Account;
import com.rschwartz.bankingapi.common.template.BaseFixture;
import com.rschwartz.bankingapi.common.template.domain.AccountTemplate;
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

  private static final Long ACCOUNT_ID = 12345L;

  @InjectMocks
  private LoadAccountPersistenceAdapter adapter;

  @Mock
  private AccountRepository repository;

  @Mock
  private AccountMapper mapper;

  @BeforeAll
  static void setUp() {
    FixtureFactoryLoader.loadTemplates(BaseFixture.ENTITY.getPath());
  }

  @Test
  @DisplayName("Should get optional empty when account does not exist.")
  void accountNotFound() {

    when(repository.findByIdAndActiveTrue(anyLong()))
        .thenReturn(Optional.empty());

    final Optional<Account> result = adapter.findById(ACCOUNT_ID);

    assertAll(() -> {
      assertNotNull(result);
      assertFalse(result.isPresent());
    });

    verify(repository, times(1))
        .findByIdAndActiveTrue(anyLong());

    verify(mapper, never())
        .mapJpaEntityToDomain(any(AccountJpaEntity.class));
  }

  @Test
  @DisplayName("Should get account by id.")
  void accountExists() {

    final AccountJpaEntity entity = Fixture.from(AccountJpaEntity.class)
        .gimme(AccountJpaEntityTemplate.VALID);
    when(repository.findByIdAndActiveTrue(anyLong()))
        .thenReturn(Optional.of(entity));

    final Account account = AccountTemplate.getTemplateOne();
    when(mapper.mapJpaEntityToDomain(any(AccountJpaEntity.class)))
        .thenReturn(account);

    final Optional<Account> result = adapter.findById(ACCOUNT_ID);

    assertAll(() -> {
      assertNotNull(result);
      assertTrue(result.isPresent());
    });

    verify(repository, times(1))
        .findByIdAndActiveTrue(anyLong());

    verify(mapper, times(1))
        .mapJpaEntityToDomain(any(AccountJpaEntity.class));
  }

}
