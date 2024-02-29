package com.rschwartz.bankingapi.accounts.adapter.out.persistence.mapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.rschwartz.bankingapi.accounts.adapter.out.persistence.entity.TransactionJpaEntity;
import com.rschwartz.bankingapi.accounts.application.domain.model.Transaction;
import com.rschwartz.bankingapi.common.template.BaseFixture;
import com.rschwartz.bankingapi.common.template.domain.TransactionTemplate;
import com.rschwartz.bankingapi.common.template.entity.TransactionJpaEntityTemplate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransactionMapperTest {

  @InjectMocks
  private TransactionMapper mapper;

  @BeforeAll
  static void setUp() {
    FixtureFactoryLoader.loadTemplates(BaseFixture.ENTITY.getPath());
  }

  @Test
  @DisplayName("Should convert domain to JPA entity.")
  void mapDomainToJpaEntity() {

    final Transaction domain = TransactionTemplate.getValidCreateDebitWithdrawTransferTemplate();

    final TransactionJpaEntity result = mapper.mapDomainToJpaEntity(domain);

    assertAll(() -> {
      assertNotNull(result);
      assertNull(result.getId());
      assertEquals(domain.getType().toString(), result.getType());
      assertEquals(domain.getDetail().getValue(), result.getDetail());
      assertEquals(domain.getCreateDate().getValue(), result.getCreateDate());
      assertEquals(domain.getAmount().getValue(), result.getAmount());
      assertEquals(domain.getAccountId().getValue(), result.getAccountId());
      assertEquals(domain.getBalanceAfter().getValue(), result.getBalanceAfter());
      assertEquals(domain.getKey().getValue(), result.getKey());
    });
  }

  @Test
  @DisplayName("Should convert JPA entity to domain.")
  void mapJpaEntityToDomain() {

    final TransactionJpaEntity entity = Fixture.from(TransactionJpaEntity.class)
        .gimme(TransactionJpaEntityTemplate.VALID_WITHDRAW);

    final Transaction result = mapper.mapJpaEntityToDomain(entity);

    assertAll(() -> {
      assertNotNull(result);
      assertEquals(entity.getId(), result.getId().getValue());
      assertEquals(entity.getType(), result.getType().toString());
      assertEquals(entity.getDetail(), result.getDetail().getValue());
      assertEquals(entity.getCreateDate(), result.getCreateDate().getValue());
      assertEquals(entity.getAmount(), result.getAmount().getValue());
      assertEquals(entity.getAccountId(), result.getAccountId().getValue());
      assertEquals(entity.getBalanceAfter(), result.getBalanceAfter().getValue());
      assertEquals(entity.getKey(), result.getKey().getValue());
    });
  }

}
