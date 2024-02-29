package com.rschwartz.bankingapi.accounts.adapter.in.web;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.rschwartz.bankingapi.accounts.adapter.in.web.dto.response.AccountResponse;
import com.rschwartz.bankingapi.accounts.adapter.in.web.mapper.AccountResponseMapper;
import com.rschwartz.bankingapi.accounts.application.port.in.useCase.LoadAccountUseCase;
import com.rschwartz.bankingapi.accounts.application.port.out.dto.AccountOutput;
import com.rschwartz.bankingapi.common.template.BaseFixture;
import com.rschwartz.bankingapi.common.template.output.AccountOutputTemplate;
import com.rschwartz.bankingapi.common.template.response.AccountResponseTemplate;
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

  private static final Long ACCOUNT_ID = 12345L;

  @InjectMocks
  private LoadAccountController controller;

  @Mock
  private LoadAccountUseCase useCase;

  @Mock
  private AccountResponseMapper mapper;

  @BeforeAll
  static void setUp() {
    FixtureFactoryLoader.loadTemplates(BaseFixture.OUTPUT.getPath());
    FixtureFactoryLoader.loadTemplates(BaseFixture.RESPONSE.getPath());
  }

  @Test
  @DisplayName("Should get error when account does not exist.")
  void accountNotFound() {

    when(useCase.execute(anyLong()))
        .thenReturn(Optional.empty());

    final ResponseEntity<AccountResponse> result = controller.findById(ACCOUNT_ID);

    assertAll(() -> {
      assertNotNull(result);
      assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
      assertNull(result.getBody());
    });

    verify(useCase, times(1))
        .execute(anyLong());

    verify(mapper, never())
        .mapOutputToResponse(any(AccountOutput.class));
  }

  @Test
  @DisplayName("Should get account by id.")
  void accountExists() {

    final AccountOutput output = Fixture.from(AccountOutput.class)
        .gimme(AccountOutputTemplate.VALID);
    when(useCase.execute(anyLong()))
        .thenReturn(Optional.of(output));

    final AccountResponse response = Fixture.from(AccountResponse.class)
        .gimme(AccountResponseTemplate.VALID);
    when(mapper.mapOutputToResponse(any(AccountOutput.class)))
        .thenReturn(response);

    final ResponseEntity<AccountResponse> result = controller.findById(ACCOUNT_ID);

    assertAll(() -> {
      assertNotNull(result);
      assertEquals(HttpStatus.OK, result.getStatusCode());
      assertNotNull(result.getBody());
    });

    verify(useCase, times(1))
        .execute(anyLong());

    verify(mapper, times(1))
        .mapOutputToResponse(any(AccountOutput.class));
  }

}