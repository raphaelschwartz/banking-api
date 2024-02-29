package com.rschwartz.bankingapi.accounts.integration.adapter.in.web;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.rschwartz.bankingapi.accounts.adapter.in.web.SendMoneyController;
import com.rschwartz.bankingapi.accounts.adapter.in.web.dto.request.SendMoneyRequest;
import com.rschwartz.bankingapi.accounts.adapter.in.web.mapper.AccountResponseMapper;
import com.rschwartz.bankingapi.accounts.adapter.in.web.validator.SendMoneyRequestValidator;
import com.rschwartz.bankingapi.accounts.application.port.in.useCase.SendMoneyUseCase;
import com.rschwartz.bankingapi.common.adapter.in.handler.CustomExceptionHandler;
import com.rschwartz.bankingapi.common.adapter.in.web.integration.PostMappingControllerTest;
import com.rschwartz.bankingapi.common.template.BaseFixture;
import com.rschwartz.bankingapi.common.template.request.SendMoneyRequestTemplate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class SendMoneyControllerIntegrationTest extends PostMappingControllerTest {

  private static final String BASE_URI = "/v1/accounts/send";
  private static final String FIELD_SOURCE_ACCOUNT_ID = "sourceAccountId";
  private static final String FIELD_TARGET_ACCOUNT_ID = "TargetAccountId";
  private static final String FIELD_AMOUNT = "amount";

  protected static final String MESSAGE_REQUIRED_FIELD = "Required parameter.";

  @InjectMocks
  private SendMoneyController controller;

  @Autowired
  private MockMvc mockMvc;

  @Spy
  private SendMoneyRequestValidator validator;

  @Mock
  private SendMoneyUseCase useCase;

  @Mock
  private AccountResponseMapper mapper;

  @BeforeEach
  public void setUpMocks() {

    mockMvc =
        MockMvcBuilders.standaloneSetup(controller)
            .setControllerAdvice(new CustomExceptionHandler())
            .build();
  }

  @BeforeAll
  static void setUp() {
    FixtureFactoryLoader.loadTemplates(BaseFixture.REQUEST.getPath());
  }

  @Test
  @DisplayName("Should get error when has no body.")
  void withoutBody() throws Exception {

    mockMvc
        .perform(post(BASE_URI))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$").exists())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("[0].code", equalTo("body")))
        .andExpect(jsonPath("[0].message", equalTo("Body is required.")));
  }

  @Test
  @DisplayName("Should get error when body is invalid.")
  void invalidBody() throws Exception {

    mockMvc
        .perform(invalidBody(BASE_URI))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$").exists())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("[0].code", equalTo("body")))
        .andExpect(jsonPath("[0].message", equalTo("Malformed JSON request.")));
  }

  @Test
  @DisplayName("Should get error when required fields have no value.")
  void shouldReturnCreateErrorRequiredFieldsTest() throws Exception {

    final SendMoneyRequest request = Fixture.from(SendMoneyRequest.class)
        .gimme(SendMoneyRequestTemplate.INVALID_REQUIRED);

    mockMvc
        .perform(post(BASE_URI, request))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$").exists())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$[*].code", containsInAnyOrder(
            FIELD_SOURCE_ACCOUNT_ID,
            FIELD_TARGET_ACCOUNT_ID,
            FIELD_AMOUNT
        )))
        .andExpect(jsonPath("$[*].message", containsInAnyOrder(
            MESSAGE_REQUIRED_FIELD,
            MESSAGE_REQUIRED_FIELD,
            MESSAGE_REQUIRED_FIELD
        )));
  }

  @Test
  @DisplayName("Should send money.")
  void success() throws Exception {

    final SendMoneyRequest request = Fixture.from(SendMoneyRequest.class)
        .gimme(SendMoneyRequestTemplate.VALID);

    mockMvc
        .perform(post(BASE_URI, request))
        .andExpect(status().isNoContent())
        .andExpect(jsonPath("$").doesNotExist());
  }

}
