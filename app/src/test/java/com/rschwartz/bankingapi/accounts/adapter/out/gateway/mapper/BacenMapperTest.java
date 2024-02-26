package com.rschwartz.bankingapi.accounts.adapter.out.gateway.mapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.rschwartz.bankingapi.accounts.adapter.out.gateway.client.dto.request.NotificationBacenRequest;
import com.rschwartz.bankingapi.accounts.application.domain.Person;
import com.rschwartz.bankingapi.accounts.application.domain.model.Transaction;
import com.rschwartz.bankingapi.common.template.domain.PersonTemplate;
import com.rschwartz.bankingapi.common.template.domain.TransactionTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BacenMapperTest {

  @InjectMocks
  private BacenMapper mapper;

  @Test
  @DisplayName("Should convert domain to DTO.")
  void mapDomainToDTO() {

    final Transaction transaction = TransactionTemplate.getValidRestoreWithdrawTransferTemplate();
    final Person person = PersonTemplate.getValidTemplate();

    final NotificationBacenRequest result = mapper.mapDomainToDTO(transaction, person);

    assertAll(() -> {
      assertNotNull(result);
      assertEquals(transaction.getKey().getValue(), result.getKey());
      assertEquals(transaction.getCreateDate().getValue(), result.getCreateDate());
      assertEquals(transaction.getType().toString(), result.getType());
      assertEquals(transaction.getAmount(), result.getAmount());
      assertEquals(transaction.getAccountId().getValue(), result.getAccountId());
      assertEquals(person.getFullName(), result.getFullName());
      assertEquals(transaction.getDetail().getValue(), result.getDetail());
    });
  }

}