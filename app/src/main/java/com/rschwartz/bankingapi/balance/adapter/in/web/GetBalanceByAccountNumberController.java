package com.rschwartz.bankingapi.balance.adapter.in.web;

import com.rschwartz.bankingapi.balance.adapter.out.dto.BalanceResponse;
import com.rschwartz.bankingapi.balance.application.port.in.useCase.GetBalanceByAccountNumberUseCase;
import com.rschwartz.bankingapi.balance.application.port.out.dto.BalanceOutput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    value = "v1/balances",
    produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
@Tag(name = "Balance API", description = "Balance Controller")
public class GetBalanceByAccountNumberController {

  private final GetBalanceByAccountNumberUseCase useCase;

  @Operation(summary = "Get a balance by account id", description = "Returns the account balance")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found the balance"),
      @ApiResponse(responseCode = "404", description = "Not found - The balance was not found", content = @Content),
      @ApiResponse(responseCode = "500", description =  "Internal server error - Something went wrong", content = @Content)
  })
  @GetMapping("/{id}")
  public ResponseEntity<BalanceResponse> findByAccountNumber(
      @Parameter(description = "Account Number", example = "0123456")
      @PathVariable("id") final String accountNumber
  ) {

    return useCase
        .execute(accountNumber)
        .map(this::convert)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  private BalanceResponse convert(final BalanceOutput output) {

    return new BalanceResponse(
        output.getAccountNumber(),
        output.getValue(),
        output.getLimit(),
        output.getUpdateDate());
  }

}
