package com.rschwartz.bankingapi.accounts.adapter.in.web.validator;

import static com.rschwartz.bankingapi.accounts.adapter.in.web.validator.SendMoneyRequestValidator.FIELD_AMOUNT;
import static com.rschwartz.bankingapi.accounts.adapter.in.web.validator.SendMoneyRequestValidator.FIELD_SOURCE_ACCOUNT_ID;
import static com.rschwartz.bankingapi.accounts.adapter.in.web.validator.SendMoneyRequestValidator.FIELD_TARGET_ACCOUNT_ID;
import static com.rschwartz.bankingapi.accounts.adapter.in.web.validator.SendMoneyRequestValidator.MESSAGE_REQUIRED_FIELD;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.com.fluentvalidator.context.ValidationResult;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.rschwartz.bankingapi.accounts.adapter.in.web.dto.request.SendMoneyRequest;
import com.rschwartz.bankingapi.common.adapter.in.web.validator.RequestValidator;
import com.rschwartz.bankingapi.common.template.BaseFixture;
import com.rschwartz.bankingapi.common.template.request.SendMoneyRequestTemplate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SendMoneyRequestValidatorTest extends RequestValidator {

  @InjectMocks
  private SendMoneyRequestValidator validator;

  @BeforeAll
  static void setUp() {
    FixtureFactoryLoader.loadTemplates(BaseFixture.REQUEST.getPath());
  }

  @Test
  @DisplayName("Should get error when required fields have null values.")
  void errorRequired() {

    final SendMoneyRequest request = Fixture.from(SendMoneyRequest.class)
        .gimme(SendMoneyRequestTemplate.INVALID_REQUIRED);

    final ValidationResult result = validator.validate(request);

    assertAll(() -> {
      assertNotNull(result);
      assertFalse(result.isValid());
      assertEquals(3, result.getErrors().size());
      assertTrue(hasError(result, FIELD_SOURCE_ACCOUNT_ID, MESSAGE_REQUIRED_FIELD));
      assertTrue(hasError(result, FIELD_TARGET_ACCOUNT_ID, MESSAGE_REQUIRED_FIELD));
      assertTrue(hasError(result, FIELD_AMOUNT, MESSAGE_REQUIRED_FIELD));
    });
  }

  @Test
  @DisplayName("Should validate all data successfully.")
  void success() {

    final SendMoneyRequest request = Fixture.from(SendMoneyRequest.class)
        .gimme(SendMoneyRequestTemplate.VALID);

    final ValidationResult result = validator.validate(request);

    assertAll(() -> {
      assertNotNull(result);
      assertTrue(result.isValid());
      assertEquals(0, result.getErrors().size());
    });
  }

}