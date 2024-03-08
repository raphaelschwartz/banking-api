package com.rschwartz.bankingapi.accounts.adapter.in.web;

import com.rschwartz.bankingapi.accounts.adapter.in.web.dto.request.SendMoneyRequest;
import com.rschwartz.bankingapi.accounts.adapter.in.web.exception.SendMoneyRequestValidatorException;
import com.rschwartz.bankingapi.accounts.adapter.in.web.validator.SendMoneyRequestValidator;
import com.rschwartz.bankingapi.accounts.application.port.in.command.SendMoneyCommand;
import com.rschwartz.bankingapi.accounts.application.port.in.useCase.SendMoneyUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.math.BigDecimal;
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

  @Operation(summary = "Transfer money.", description = "Transfer money between accounts.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Tranfer sent."),
      @ApiResponse(responseCode = "400", description = "Bad Request - The balance was not found.", content = @Content),
      @ApiResponse(responseCode = "500", description =  "Internal server error - Something went wrong.", content = @Content)
  })
  @PostMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void sendMoney(@RequestBody final SendMoneyRequest request) {

    validator.validate(request)
            .isInvalidThrow(SendMoneyRequestValidatorException.class);

    useCase.execute(new SendMoneyCommand(
        request.getSourceAccountId(),
        request.getTargetAccountId(),
        new BigDecimal(request.getAmount())
    ));
  }

}
