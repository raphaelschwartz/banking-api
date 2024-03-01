package com.rschwartz.bankingapi.accounts.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateAccountBalancePersistenceAdapterTest {

  @InjectMocks
  private UpdateAccountBalancePersistenceAdapter adapter;
  @Mock
  private AccountMapper mapper;

  @Mock
  private AccountRepository repository;

  @BeforeAll
  static void setUp() {
    FixtureFactoryLoader.loadTemplates(BaseFixture.ENTITY.getPath());
  }

  @Test
  @DisplayName("Should update a account.")
  void save() {

    final AccountJpaEntity entity = Fixture.from(AccountJpaEntity.class)
        .gimme(AccountJpaEntityTemplate.VALID);
    when(mapper.mapDomainToJpaEntity(any(Account.class)))
        .thenReturn(entity);

    when(repository.save(any(AccountJpaEntity.class)))
        .thenReturn(entity);

    final Account domain = AccountTemplate.getTemplateOne();
    when(mapper.mapJpaEntityToDomain(any(AccountJpaEntity.class)))
        .thenReturn(domain);

    final Account result = adapter.save(domain);
    assertNotNull(result);

    verify(mapper, times(1))
        .mapDomainToJpaEntity(any(Account.class));

    verify(repository, times(1))
        .save(any(AccountJpaEntity.class));

    verify(mapper, times(1))
        .mapJpaEntityToDomain(any(AccountJpaEntity.class));
  }

}
