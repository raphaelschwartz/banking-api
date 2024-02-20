package com.rschwartz.bankingapi.accounts.adapter.in.web;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.rschwartz.bankingapi.accounts.adapter.out.dto.AccountResponse;
import com.rschwartz.bankingapi.accounts.aplication.port.in.useCase.LoadAccountUseCase;
import com.rschwartz.bankingapi.accounts.aplication.port.out.dto.AccountOutput;
import com.rschwartz.bankingapi.common.template.BaseFixture;
import com.rschwartz.bankingapi.common.template.output.AccountOutputTemplate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class LoadAccountControllerTest {

  private static final String ACCOUNT_NUMBER = "012345";

  @InjectMocks
  private LoadAccountController controller;

  @Mock
  private LoadAccountUseCase useCase;

  @BeforeAll
  static void setUp() {
    FixtureFactoryLoader.loadTemplates(BaseFixture.OUTPUT.getPath());
  }

  @Test
  @DisplayName("Should get error when balance does not exist.")
  void balanceNotFound() {

    when(useCase.execute(anyString()))
        .thenReturn(Optional.empty());

    final ResponseEntity<AccountResponse> result = controller.findByAccountNumber(ACCOUNT_NUMBER);

    assertAll(() -> {
      assertNotNull(result);
      assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
      assertNull(result.getBody());
    });

    verify(useCase, times(1))
        .execute(anyString());
  }

  @Test
  @DisplayName("Should get balance by account number.")
  void balanceExists() {

    final AccountOutput output = Fixture.from(AccountOutput.class)
        .gimme(AccountOutputTemplate.VALID);

    when(useCase.execute(anyString()))
        .thenReturn(Optional.of(output));

    final ResponseEntity<AccountResponse> result = controller.findByAccountNumber(ACCOUNT_NUMBER);

    assertAll(() -> {
      assertNotNull(result);
      assertEquals(HttpStatus.OK, result.getStatusCode());
      assertNotNull(result.getBody());
    });

    verify(useCase, times(1))
        .execute(anyString());
  }

}