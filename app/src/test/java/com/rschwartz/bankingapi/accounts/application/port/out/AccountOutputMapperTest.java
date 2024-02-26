package com.rschwartz.bankingapi.accounts.application.port.out;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.rschwartz.bankingapi.accounts.application.domain.model.Account;
import com.rschwartz.bankingapi.accounts.application.port.out.dto.AccountOutput;
import com.rschwartz.bankingapi.accounts.application.port.out.mapper.AccountOutputMapper;
import com.rschwartz.bankingapi.common.template.domain.AccountTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AccountOutputMapperTest {

  @InjectMocks
  private AccountOutputMapper mapper;

  @Test
  @DisplayName("Should convert domain to output.")
  void mapDomainToOutput() {

    final Account domain = AccountTemplate.getTemplateOne();

    final AccountOutput result = mapper.mapDomainToOutput(domain);

    assertAll(() -> {
      assertNotNull(result);
      assertEquals(domain.getId().getValue(), result.getId());
      assertEquals(domain.getOwnerId().getValue(), result.getOwnerId());
      assertEquals(domain.getNumber(), result.getNumber());
      assertEquals(domain.getBalance().getValue(), result.getBalance());
      assertEquals(domain.getUpdateDate().getValue(), result.getUpdateDate());
    });
  }

}
