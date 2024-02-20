package com.rschwartz.bankingapi.accounts.application.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rschwartz.bankingapi.accounts.aplication.port.out.LoadAccountPort;
import com.rschwartz.bankingapi.accounts.aplication.port.out.dto.AccountOutput;
import com.rschwartz.bankingapi.accounts.aplication.service.LoadAccountService;
import com.rschwartz.bankingapi.accounts.domain.Account;
import com.rschwartz.bankingapi.common.template.domain.AccountTemplate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LoadAccountServiceTest {

  private static final String ACCOUNT_NUMBER = "012345";

  @InjectMocks
  private LoadAccountService service;

  @Mock
  private LoadAccountPort port;

  @BeforeAll
  static void setUp() {
    // FIXME
  }

  @Test
  @DisplayName("Should get optional empty when balance does not exist.")
  void balanceNotFound() {

    when(port.execute(anyString()))
        .thenReturn(Optional.empty());

    final Optional<AccountOutput> result = service.execute(ACCOUNT_NUMBER);

    assertAll(() -> {
      assertNotNull(result);
      assertFalse(result.isPresent());
    });

    verify(port, times(1))
        .execute(anyString());
  }

  @Test
  @DisplayName("Should get balance by account number.")
  void balanceExists() {

    final Account balance = AccountTemplate.getValidTemplate();

    when(port.execute(anyString()))
        .thenReturn(Optional.of(balance));

    final Optional<AccountOutput> result = service.execute(ACCOUNT_NUMBER);

    assertAll(() -> {
      assertNotNull(result);
      assertTrue(result.isPresent());
    });

    verify(port, times(1))
        .execute(anyString());
  }

}
