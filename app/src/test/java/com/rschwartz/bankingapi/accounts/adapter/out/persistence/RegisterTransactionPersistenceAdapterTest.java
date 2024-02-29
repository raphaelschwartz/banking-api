package com.rschwartz.bankingapi.accounts.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.rschwartz.bankingapi.accounts.adapter.out.persistence.entity.TransactionJpaEntity;
import com.rschwartz.bankingapi.accounts.adapter.out.persistence.mapper.TransactionMapper;
import com.rschwartz.bankingapi.accounts.adapter.out.persistence.repository.TransactionRepository;
import com.rschwartz.bankingapi.accounts.application.domain.model.Transaction;
import com.rschwartz.bankingapi.common.template.BaseFixture;
import com.rschwartz.bankingapi.common.template.domain.TransactionTemplate;
import com.rschwartz.bankingapi.common.template.entity.TransactionJpaEntityTemplate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RegisterTransactionPersistenceAdapterTest {

  @InjectMocks
  private RegisterTransactionPersistenceAdapter adapter;
  @Mock
  private TransactionMapper mapper;

  @Mock
  private TransactionRepository repository;

  @BeforeAll
  static void setUp() {
    FixtureFactoryLoader.loadTemplates(BaseFixture.ENTITY.getPath());
  }

  @Test
  @DisplayName("Should save a transaction.")
  void save() {

    final TransactionJpaEntity entity = Fixture.from(TransactionJpaEntity.class)
        .gimme(TransactionJpaEntityTemplate.VALID_WITHDRAW);
    when(mapper.mapDomainToJpaEntity(any(Transaction.class)))
        .thenReturn(entity);

    when(repository.save(any(TransactionJpaEntity.class)))
        .thenReturn(entity);

    final Transaction domain = TransactionTemplate.getValidRestoreWithdrawTransferTemplate();
    when(mapper.mapJpaEntityToDomain(any(TransactionJpaEntity.class)))
        .thenReturn(domain);

    final Transaction result = adapter.save(domain);
    assertNotNull(result);

    verify(mapper, times(1))
        .mapDomainToJpaEntity(any(Transaction.class));

    verify(repository, times(1))
        .save(any(TransactionJpaEntity.class));

    verify(mapper, times(1))
        .mapJpaEntityToDomain(any(TransactionJpaEntity.class));
  }

}
