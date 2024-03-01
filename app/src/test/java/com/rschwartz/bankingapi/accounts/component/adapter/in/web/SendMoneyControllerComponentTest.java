package com.rschwartz.bankingapi.accounts.component.adapter.in.web;

import static java.nio.charset.Charset.defaultCharset;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.util.StreamUtils.copyToString;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.rschwartz.bankingapi.accounts.adapter.in.web.dto.request.SendMoneyRequest;
import com.rschwartz.bankingapi.accounts.application.domain.model.Account;
import com.rschwartz.bankingapi.accounts.application.port.out.LoadAccountPort;
import com.rschwartz.bankingapi.common.adapter.in.handler.ApiError;
import com.rschwartz.bankingapi.common.adapter.in.web.component.ComponentController;
import com.rschwartz.bankingapi.common.template.BaseFixture;
import com.rschwartz.bankingapi.common.template.request.SendMoneyRequestTemplate;
import com.rschwartz.bankingapi.config.WireMockConfig;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@EnableFeignClients
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { WireMockConfig.class })
public class SendMoneyControllerComponentTest extends ComponentController {

  private static final String URL = "/v1/accounts/send";
  private static final String ERROR_BODY_CODE = "body";
  private static final String ERROR_EXTERNAL_CODE = "external";
  private static final String PATH_USERS = "/users";
  private static final String PATH_NOTIFICATIONS = "/notifications";

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private WireMockServer mockUserService;

  @Autowired
  private WireMockServer mockBacenService;

  @Autowired
  private LoadAccountPort loadAccountPort;

  @BeforeAll
  static void setUp() {
    FixtureFactoryLoader.loadTemplates(BaseFixture.REQUEST.getPath());
  }

  @Test
  @DisplayName("Should get error when source account does not exist.")
  @Sql({"/data/component/account/send_money/source-account-not-found.sql"})
  void sourceAccountNotFound() {

    final SendMoneyRequest body = Fixture.from(SendMoneyRequest.class)
        .gimme(SendMoneyRequestTemplate.VALID);

    final ParameterizedTypeReference<List<ApiError>> responseType = new ParameterizedTypeReference<>() {
    };

    final ResponseEntity<List<ApiError>> result = restTemplate.exchange(
        URL,
        HttpMethod.POST,
        new HttpEntity<>(body, getHttpHeaders()),
        responseType
    );

    assertAll(() -> {
      assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
      assertNotNull(result.getBody());
      assertEquals(1, result.getBody().size());

      final ApiError apiError = result.getBody().get(0);
      assertEquals(ERROR_BODY_CODE, apiError.getCode());

      final String message = String.format("Account %s not found.", body.getSourceAccountId());
      assertEquals(message, apiError.getMessage());
    });

  }

  @Test
  @DisplayName("Should get error when daily threshold is exceeded.")
  @Sql({"/data/component/account/send_money/daily-threshold-exceeded.sql"})
  void dailyThresholdExceeded() {

    final SendMoneyRequest body = Fixture.from(SendMoneyRequest.class)
        .gimme(SendMoneyRequestTemplate.VALID);

    final ParameterizedTypeReference<List<ApiError>> responseType = new ParameterizedTypeReference<>() {
    };

    final ResponseEntity<List<ApiError>> result = restTemplate.exchange(
        URL,
        HttpMethod.POST,
        new HttpEntity<>(body, getHttpHeaders()),
        responseType
    );

    assertAll(() -> {
      assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
      assertNotNull(result.getBody());
      assertEquals(1, result.getBody().size());

      final ApiError apiError = result.getBody().get(0);
      assertEquals(ERROR_BODY_CODE, apiError.getCode());

      final String message = "Maximum threshold for transferring money exceeded: tried to transfer 50.00 but threshold available is 20.00.";
      assertEquals(message, apiError.getMessage());
    });

  }

  @Test
  @DisplayName("Should get error when person does not exist.")
  @Sql({"/data/component/account/send_money/person-not-found.sql"})
  void personNotFound() {

    mockUserService.stubFor(WireMock.get(WireMock.urlEqualTo(PATH_USERS.concat("/1234")))
        .willReturn(
            WireMock.aResponse()
                .withStatus(HttpStatus.NOT_FOUND.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        )
    );

    final SendMoneyRequest body = Fixture.from(SendMoneyRequest.class)
        .gimme(SendMoneyRequestTemplate.VALID);
    final ParameterizedTypeReference<List<ApiError>> responseType = new ParameterizedTypeReference<>() {
    };

    final ResponseEntity<List<ApiError>> result = restTemplate.exchange(
        URL,
        HttpMethod.POST,
        new HttpEntity<>(body, getHttpHeaders()),
        responseType
    );

    assertAll(() -> {
      assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, result.getStatusCode());
      assertNotNull(result.getBody());
      assertEquals(1, result.getBody().size());

      final ApiError apiError = result.getBody().get(0);
      assertEquals(ERROR_EXTERNAL_CODE, apiError.getCode());

      final String message = "Unable to process request.";
      assertEquals(message, apiError.getMessage());
    });

  }

  @Test
  @DisplayName("Should get error when target account does not exist.")
  @Sql({"/data/component/account/send_money/target-account-not-found.sql"})
  void targetAccountNotFound() throws IOException {

    mockPerson(1234L);

    final SendMoneyRequest body = Fixture.from(SendMoneyRequest.class)
        .gimme(SendMoneyRequestTemplate.VALID);

    final ParameterizedTypeReference<List<ApiError>> responseType = new ParameterizedTypeReference<>() {
    };

    final ResponseEntity<List<ApiError>> result = restTemplate.exchange(
        URL,
        HttpMethod.POST,
        new HttpEntity<>(body, getHttpHeaders()),
        responseType
    );

    assertAll(() -> {
      assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
      assertNotNull(result.getBody());
      assertEquals(1, result.getBody().size());

      final ApiError apiError = result.getBody().get(0);
      assertEquals(ERROR_BODY_CODE, apiError.getCode());

      final String message = String.format("Account %s not found.", body.getTargetAccountId());
      assertEquals(message, apiError.getMessage());
    });

  }

  @Test
  @DisplayName("Should get error when there is no enough balance.")
  @Sql({"/data/component/account/send_money/insufficient-funds.sql"})
  void insufficientFunds() throws IOException {

    mockPerson(1234L);
    mockPerson(5678L);

    final SendMoneyRequest body = Fixture.from(SendMoneyRequest.class)
        .gimme(SendMoneyRequestTemplate.VALID);
    final ParameterizedTypeReference<List<ApiError>> responseType = new ParameterizedTypeReference<>() {
    };

    final ResponseEntity<List<ApiError>> result = restTemplate.exchange(
        URL,
        HttpMethod.POST,
        new HttpEntity<>(body, getHttpHeaders()),
        responseType
    );

    assertAll(() -> {
      assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
      assertNotNull(result.getBody());
      assertEquals(1, result.getBody().size());

      final ApiError apiError = result.getBody().get(0);
      assertEquals(ERROR_BODY_CODE, apiError.getCode());
      assertEquals("Insufficient balance for transfer.", apiError.getMessage());
    });

  }

  @Test
  @DisplayName("Should send money.")
  @Sql({"/data/component/account/send_money/send-money.sql"})
  void sendMoney() throws IOException {

    mockPerson(1234L);
    mockPerson(5678L);

    mockBacenService.stubFor(WireMock.post(PATH_NOTIFICATIONS)
        .willReturn(
            WireMock.aResponse()
                .withStatus(HttpStatus.NO_CONTENT.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
        )
    );

    final SendMoneyRequest request = Fixture.from(SendMoneyRequest.class)
        .gimme(SendMoneyRequestTemplate.VALID);
    final ParameterizedTypeReference<List<ApiError>> responseType = new ParameterizedTypeReference<>() {
    };

    final ResponseEntity<List<ApiError>> result = restTemplate.exchange(
        URL,
        HttpMethod.POST,
        new HttpEntity<>(request, getHttpHeaders()),
        responseType
    );

    assertAll(() -> {
      assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
      assertNull(result.getBody());
    });

    final Optional<Account> sourceAccount = loadAccountPort.findById(request.getSourceAccountId());
    assertAll(() -> {
      assertTrue(sourceAccount.isPresent());
      assertEquals(new BigDecimal("50.00"), sourceAccount.get().getBalance().getValue());
    });

    final Optional<Account> targetAccount = loadAccountPort.findById(request.getTargetAccountId());
    assertAll(() -> {
      assertTrue(targetAccount.isPresent());
      assertEquals(new BigDecimal("200.00"), targetAccount.get().getBalance().getValue());
    });

  }

  private void mockPerson(final Long id) throws IOException {

    final String uri = String.format("%s/%s", PATH_USERS, id);

    mockUserService.stubFor(WireMock.get(WireMock.urlEqualTo(uri))
        .willReturn(
            WireMock.aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody(
                    copyToString(
                        this.getClass().getClassLoader().getResourceAsStream("payload/users/find-by-id.json"),
                        defaultCharset()
                    )
                )
        )
    );
  }

}
