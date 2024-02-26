package com.rschwartz.bankingapi.accounts.component.adapter.in.web;

import static java.nio.charset.Charset.defaultCharset;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.util.StreamUtils.copyToString;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.rschwartz.bankingapi.accounts.adapter.in.web.dto.request.SendMoneyRequest;
import com.rschwartz.bankingapi.common.adapter.in.handler.ApiError;
import com.rschwartz.bankingapi.common.adapter.in.web.component.ComponentController;
import com.rschwartz.bankingapi.common.template.BaseFixture;
import com.rschwartz.bankingapi.common.template.request.SendMoneyRequestTemplate;
import com.rschwartz.bankingapi.config.WireMockConfig;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
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
@Sql({"/data/component/account/SendMoney.sql"})
@EnableFeignClients
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { WireMockConfig.class })
public class SendMoneyControllerComponentTest extends ComponentController {

  private static final String URL = "/v1/accounts/send";
  private static final String ERROR_CODE = "body";
  private static final String PATH_USERS = "/users";

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private WireMockServer mockUserService;

  @BeforeAll
  static void setUp() {
    FixtureFactoryLoader.loadTemplates(BaseFixture.REQUEST.getPath());
  }

  @Test
  @DisplayName("Should get error when source account does not exist.")
  void sourceAccountNotFound() {

    final SendMoneyRequest body = Fixture.from(SendMoneyRequest.class)
        .gimme(SendMoneyRequestTemplate.INVALID_SOURCE_ACCOUNT_ID);

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
      assertEquals(ERROR_CODE, apiError.getCode());

      final String message = String.format("Account %s not found.", body.getSourceAccountId());
      assertEquals(message, apiError.getMessage());
    });

  }

  @Test
  @DisplayName("Should get error when daily threshold is exceeded.")
  void dailyThresholdExceeded() {

    final SendMoneyRequest body = Fixture.from(SendMoneyRequest.class)
        .gimme(SendMoneyRequestTemplate.INVALID_THRESHOLD_EXCEEDED);

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
      assertEquals(ERROR_CODE, apiError.getCode());

      final String message = "Maximum threshold for transferring money exceeded: tried to transfer 70.00 but threshold available is 50.00.";
      assertEquals(message, apiError.getMessage());
    });

  }

  @Disabled
  @Test
  @DisplayName("Should get error when target account does not exist.")
  void targetAccountNotFound() {

    final SendMoneyRequest body = Fixture.from(SendMoneyRequest.class)
        .gimme(SendMoneyRequestTemplate.INVALID_TARGET_ACCOUNT_ID);
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
      assertEquals(ERROR_CODE, apiError.getCode());

      final String message = String.format("Account %s not found.", body.getTargetAccountId());
      assertEquals(message, apiError.getMessage());
    });

  }

  @Disabled
  @Test
  @DisplayName("Should get error when person does not exist.")
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
      assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
      assertNotNull(result.getBody());
      assertEquals(1, result.getBody().size());

      final ApiError apiError = result.getBody().get(0);
      assertEquals(ERROR_CODE, apiError.getCode());

      final String message = String.format("Account %s not found.", body.getTargetAccountId());
      assertEquals(message, apiError.getMessage());
    });

  }

  @Test
  @DisplayName("Should get error when there is no enough balance.")
  void insufficientFunds() throws IOException {

    mockUserService.stubFor(WireMock.get(WireMock.urlEqualTo(PATH_USERS.concat("/1234")))
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

    final SendMoneyRequest body = Fixture.from(SendMoneyRequest.class)
        .gimme(SendMoneyRequestTemplate.INVALID_INSUFFICIENT_FUNDS);
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
      assertEquals(ERROR_CODE, apiError.getCode());
      assertEquals("Insufficient balance for transfer.", apiError.getMessage());
    });

  }

  @Test
  @DisplayName("Should send money.")
  void sendMoney() throws IOException {

    mockUserService.stubFor(WireMock.get(WireMock.urlEqualTo(PATH_USERS.concat("/1234")))
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
      assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
      assertNull(result.getBody());
    });

  }

}
