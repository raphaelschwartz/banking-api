package com.rschwartz.bankingapi.accounts.adapter.in.web;

import com.rschwartz.bankingapi.accounts.adapter.in.web.dto.SendMoneyRequest;
import com.rschwartz.bankingapi.accounts.adapter.in.web.exception.SendMoneyRequestValidatorException;
import com.rschwartz.bankingapi.accounts.adapter.in.web.validator.SendMoneyRequestValidator;
import com.rschwartz.bankingapi.accounts.aplication.port.in.useCase.SendMoneyUseCase;
import com.rschwartz.bankingapi.accounts.aplication.port.in.useCase.dto.SendMoneyInput;
import com.rschwartz.bankingapi.accounts.domain.AccountNew.AccountId;
import com.rschwartz.bankingapi.accounts.domain.Money;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.math.BigInteger;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    value = "v1/accounts/send",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
@Tag(name = "Account API", description = "Account Controller")
public class SendMoneyController {

  private final SendMoneyRequestValidator validator;
  private final SendMoneyUseCase useCase;

  @Operation(summary = "Get a balance by account id", description = "Returns the account balance")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Found the balance"), // FIXME
      @ApiResponse(responseCode = "400", description = "Not found - The balance was not found", content = @Content), // FIXME
      @ApiResponse(responseCode = "500", description =  "Internal server error - Something went wrong", content = @Content)
  })
  @PostMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void sendMoney(@RequestBody final SendMoneyRequest request) {

    validator.validate(request)
            .isInvalidThrow(SendMoneyRequestValidatorException.class);

    // FIXME add mapper
    useCase.execute(new SendMoneyInput(
        new AccountId(UUID.randomUUID()),
        new AccountId(UUID.randomUUID()),
        //new AccountId(Long.valueOf(request.getSourceAccountId())),
        //new AccountId(Long.valueOf(request.getSourceAccountId())),
        new Money(new BigInteger(request.getAmount()))
    ));
  }

}
