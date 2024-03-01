package com.rschwartz.bankingapi.accounts.adapter.out.persistence.mapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.rschwartz.bankingapi.accounts.adapter.out.persistence.entity.AccountJpaEntity;
import com.rschwartz.bankingapi.accounts.application.domain.model.Account;
import com.rschwartz.bankingapi.common.template.BaseFixture;
import com.rschwartz.bankingapi.common.template.domain.AccountTemplate;
import com.rschwartz.bankingapi.common.template.entity.AccountJpaEntityTemplate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountMapperTest {

  @InjectMocks
  private AccountMapper mapper;

  @BeforeAll
  static void setUp() {
    FixtureFactoryLoader.loadTemplates(BaseFixture.ENTITY.getPath());
  }

  @Test
  @DisplayName("Should convert JPA entity to domain.")
  void mapJpaEntityToDomain() {

    final AccountJpaEntity entity = Fixture.from(AccountJpaEntity.class)
        .gimme(AccountJpaEntityTemplate.VALID);

    final Account result = mapper.mapJpaEntityToDomain(entity);

    assertAll(() -> {
      assertNotNull(result);
      assertEquals(entity.getId(), result.getId().getValue());
      assertEquals(entity.getOwnerId(), result.getOwnerId().getValue());
      assertEquals(entity.getNumber(), result.getNumber());
      assertEquals(entity.getBalance(), result.getBalance().getValue());
      assertEquals(entity.getUpdateDate(), result.getUpdateDate().getValue());
      assertEquals(entity.getStatus(), result.getStatus().toString());
    });

  }

  @Test
  @DisplayName("Should convert domain to JPA entity.")
  void mapDomainToJpaEntity() {

    final Account domain = AccountTemplate.getTemplateOne();

    final AccountJpaEntity result = mapper.mapDomainToJpaEntity(domain);

    assertAll(() -> {
      assertNotNull(result);
      assertEquals(domain.getId().getValue(), result.getId());
      assertEquals(domain.getOwnerId().getValue(), result.getOwnerId());
      assertEquals(domain.getNumber(), result.getNumber());
      assertEquals(domain.getBalance().getValue(), result.getBalance());
      assertEquals(domain.getUpdateDate().getValue(), result.getUpdateDate());
      assertEquals(domain.getStatus().toString(), result.getStatus());
      assertTrue(result.getActive());
    });

  }

}
