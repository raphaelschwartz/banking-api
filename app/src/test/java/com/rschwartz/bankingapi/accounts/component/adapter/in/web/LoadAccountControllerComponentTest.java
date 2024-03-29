package com.rschwartz.bankingapi.accounts.component.adapter.in.web;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.rschwartz.bankingapi.accounts.adapter.in.web.dto.response.AccountResponse;
import com.rschwartz.bankingapi.common.adapter.in.handler.ApiError;
import com.rschwartz.bankingapi.common.adapter.in.web.component.ComponentController;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class LoadAccountControllerComponentTest extends ComponentController {

  private static final Long ACCOUNT_ID = 1L;
  private static final String URL = String.format("/v1/accounts/%s", ACCOUNT_ID);

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  @DisplayName("Should get error when account does not exist.")
  @Sql({"/data/component/account/load_account/account-not-found.sql"})
  void accountNotFound() {

    final ParameterizedTypeReference<List<ApiError>> responseType = new ParameterizedTypeReference<>() {
    };

    final ResponseEntity<List<ApiError>> result = restTemplate.exchange(
        URL,
        HttpMethod.GET,
        new HttpEntity<>(getHttpHeaders()),
        responseType
    );

    assertAll(() -> {
      assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
      assertNull(result.getBody());
    });

  }

  @Test
  @DisplayName("Should get account by id.")
  @Sql({"/data/component/account/load_account/account-exists.sql"})
  void accountExists() {

    final ResponseEntity<AccountResponse> result = restTemplate
        .exchange(URL, HttpMethod.GET, new HttpEntity<>(getHttpHeaders()), AccountResponse.class);

    assertAll(() -> {
      assertEquals(result.getStatusCode(), HttpStatus.OK);
      assertNotNull(result.getBody());

      final AccountResponse response = result.getBody();
      assertEquals(ACCOUNT_ID, response.getId());
      assertNotNull(response.getOwnerId());
      assertNotNull(response.getNumber());
      assertNotNull(response.getBalance());
      assertNotNull(response.getUpdateDate());
    });

  }

}
