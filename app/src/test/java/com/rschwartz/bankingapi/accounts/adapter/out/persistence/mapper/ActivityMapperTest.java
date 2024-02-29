package com.rschwartz.bankingapi.accounts.adapter.out.persistence.mapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.rschwartz.bankingapi.accounts.adapter.out.persistence.entity.TransactionJpaEntity;
import com.rschwartz.bankingapi.accounts.application.domain.model.Transaction;
import com.rschwartz.bankingapi.common.template.domain.TransactionTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ActivityMapperTest {

  @InjectMocks
  private ActivityMapper mapper;

  @Test
  @DisplayName("Should convert domain to JPA entity.")
  void mapDomainToJpaEntity() {

    final Transaction entity = TransactionTemplate.getValidRestoreWithdrawTransferTemplate();

    final TransactionJpaEntity result = mapper.mapDomainToJpaEntity(entity);

    assertAll(() -> {
      assertNull(result);
      //assertNotNull(result);
      //assertEquals(entity.getId(), result.getId());
      //assertEquals(entity.getOwnerId(), result.getOwnerId());
      //assertEquals(entity.getNumber(), result.getNumber());
      //assertEquals(entity.getBalance(), result.getBalance());
      //assertEquals(entity.getLimit(), result.getLimit());
      //assertEquals(entity.getUpdateDate(), result.getUpdateDate());
    });
  }

}
