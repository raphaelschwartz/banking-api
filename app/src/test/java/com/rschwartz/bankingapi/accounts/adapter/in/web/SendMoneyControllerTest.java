package com.rschwartz.bankingapi.accounts.adapter.in.web;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fluentvalidator.context.Error;
import br.com.fluentvalidator.context.ValidationResult;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.rschwartz.bankingapi.accounts.adapter.in.web.dto.request.SendMoneyRequest;
import com.rschwartz.bankingapi.accounts.adapter.in.web.exception.SendMoneyRequestValidatorException;
import com.rschwartz.bankingapi.accounts.adapter.in.web.validator.SendMoneyRequestValidator;
import com.rschwartz.bankingapi.accounts.application.port.in.command.SendMoneyCommand;
import com.rschwartz.bankingapi.accounts.application.port.in.useCase.SendMoneyUseCase;
import com.rschwartz.bankingapi.common.template.BaseFixture;
import com.rschwartz.bankingapi.common.template.fluent_validator.ErrorFluentValidatorTemplate;
import com.rschwartz.bankingapi.common.template.request.SendMoneyRequestTemplate;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SendMoneyControllerTest {

  @InjectMocks
  private SendMoneyController controller;

  @Mock
  private SendMoneyRequestValidator validator;

  @Mock
  private SendMoneyUseCase useCase;

  @BeforeAll
  static void setUp() {

    FixtureFactoryLoader.loadTemplates(BaseFixture.FLUENT_VALIDATOR.getPath());
    FixtureFactoryLoader.loadTemplates(BaseFixture.REQUEST.getPath());
  }

  @Test
  @DisplayName("Should get error when the request has inconsistent data.")
  void errorValidation() {

    final List<Error> errors = Fixture.from(Error.class)
        .gimme(1, ErrorFluentValidatorTemplate.INVALID);

    when(validator.validate(any(SendMoneyRequest.class)))
        .thenReturn(ValidationResult.fail(errors));

    final SendMoneyRequest request = Fixture.from(SendMoneyRequest.class)
        .gimme(SendMoneyRequestTemplate.VALID);

    assertThrows(SendMoneyRequestValidatorException.class,
        () -> controller.sendMoney(request));

    verify(validator, times(1))
        .validate(any(SendMoneyRequest.class));

    verify(useCase, never())
        .execute(any(SendMoneyCommand.class));
  }

  @Test
  @DisplayName("Should send money.")
  void success() {

    when(validator.validate(any(SendMoneyRequest.class)))
        .thenReturn(ValidationResult.ok());

    final SendMoneyRequest request = Fixture.from(SendMoneyRequest.class)
        .gimme(SendMoneyRequestTemplate.VALID);

    assertDoesNotThrow(() -> controller.sendMoney(request));

    verify(validator, times(1))
        .validate(any(SendMoneyRequest.class));

    verify(useCase, times(1))
        .execute(any(SendMoneyCommand.class));
  }

}
