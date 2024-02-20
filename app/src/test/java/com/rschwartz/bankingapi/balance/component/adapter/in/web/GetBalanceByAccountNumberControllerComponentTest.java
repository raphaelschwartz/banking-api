package com.rschwartz.bankingapi.balance.component.adapter.in.web;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.rschwartz.bankingapi.balance.adapter.out.dto.BalanceResponse;
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
@Sql({"/data/component/balance/GetBalanceByAccountNumber.sql"})
public class GetBalanceByAccountNumberControllerComponentTest extends ComponentController {

  private static final String BASE_URI = "/v1/balances";

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  @DisplayName("Should get error when balance does not exist.")
  void balanceNotFound() {

    final String uri = String.format("%s/%s", BASE_URI, 10L);

    final ParameterizedTypeReference<List<ApiError>> responseType = new ParameterizedTypeReference<>() {
    };

    final ResponseEntity<List<ApiError>> result = restTemplate.exchange(
        uri,
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
  @DisplayName("Should get balance by account number.")
  void balanceExists() {

    final String accountNumber = "0123456789";
    final String uri = String.format("%s/%s", BASE_URI, accountNumber);

    final ResponseEntity<BalanceResponse> result = restTemplate
        .exchange(uri, HttpMethod.GET, new HttpEntity<>(getHttpHeaders()), BalanceResponse.class);

    assertAll(() -> {
      assertEquals(result.getStatusCode(), HttpStatus.OK);

      final BalanceResponse response = result.getBody();
      assert response != null; // FIXME
      assertEquals(accountNumber, response.getAccountNumber());
      assertNotNull(response.getAccountNumber());
      assertNotNull(response.getValue());
      assertNotNull(response.getLimit());
      assertNotNull(response.getUpdateDate());
    });

  }

}
