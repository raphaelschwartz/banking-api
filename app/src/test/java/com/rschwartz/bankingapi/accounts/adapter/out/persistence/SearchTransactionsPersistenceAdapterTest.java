package com.rschwartz.bankingapi.accounts.adapter.out.persistence;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.rschwartz.bankingapi.accounts.adapter.out.persistence.entity.TransactionJpaEntity;
import com.rschwartz.bankingapi.accounts.adapter.out.persistence.mapper.TransactionMapper;
import com.rschwartz.bankingapi.accounts.adapter.out.persistence.repository.TransactionRepository;
import com.rschwartz.bankingapi.accounts.application.domain.model.AccountId;
import com.rschwartz.bankingapi.accounts.application.domain.model.Transaction;
import com.rschwartz.bankingapi.common.template.BaseFixture;
import com.rschwartz.bankingapi.common.template.domain.TransactionTemplate;
import com.rschwartz.bankingapi.common.template.entity.TransactionJpaEntityTemplate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SearchTransactionsPersistenceAdapterTest {

  @InjectMocks
  private SearchTransactionsPersistenceAdapter adapter;

  @Mock
  private TransactionRepository repository;

  @Mock
  private TransactionMapper mapper;

  @BeforeAll
  static void setUp() {
    FixtureFactoryLoader.loadTemplates(BaseFixture.ENTITY.getPath());
  }

  @Test
  @DisplayName("Should get a list of transactions.")
  void searchTransactions() {

    final List<TransactionJpaEntity> transactions = Fixture.from(TransactionJpaEntity.class)
        .gimme(1, TransactionJpaEntityTemplate.VALID_WITHDRAW);

    when(repository.findAllByAccountIdAndCreateDateGreaterThanEqual(anyLong(), any(LocalDateTime.class)))
        .thenReturn(transactions);

    final Transaction transaction = TransactionTemplate.getValidRestoreWithdrawTransferTemplate();
    when(mapper.mapJpaEntityToDomain(any(TransactionJpaEntity.class)))
        .thenReturn(transaction);

    final List<Transaction> result = adapter.search(new AccountId(1L), LocalDate.now().atStartOfDay());

    assertAll(() -> {
      assertNotNull(result);
      assertEquals(1, result.size());
    });

    verify(repository, times(1))
        .findAllByAccountIdAndCreateDateGreaterThanEqual(anyLong(), any(LocalDateTime.class));

    verify(mapper, times(1))
        .mapJpaEntityToDomain(any(TransactionJpaEntity.class));
  }

}
