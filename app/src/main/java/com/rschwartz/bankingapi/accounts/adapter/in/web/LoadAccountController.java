package com.rschwartz.bankingapi.accounts.adapter.in.web;

import com.rschwartz.bankingapi.accounts.adapter.in.web.dto.response.AccountResponse;
import com.rschwartz.bankingapi.accounts.adapter.in.web.mapper.AccountResponseMapper;
import com.rschwartz.bankingapi.accounts.application.port.in.useCase.LoadAccountUseCase;
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
    value = "v1/accounts",
    produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
@Tag(name = "Account API", description = "Account Controller")
public class LoadAccountController {

  private final LoadAccountUseCase useCase;
  private final AccountResponseMapper mapper;

  @Operation(summary = "Get account by account id.", description = "Returns the account.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found the account."),
      @ApiResponse(responseCode = "404", description = "Not found - The account was not found.", content = @Content),
      @ApiResponse(responseCode = "500", description =  "Internal server error - Something went wrong.", content = @Content)
  })
  @GetMapping("/{id}")
  public ResponseEntity<AccountResponse> findById(
      @Parameter(description = "Account id", example = "12345")
      @PathVariable final Long id
  ) {

    return useCase
        .execute(id)
        .map(mapper::mapOutputToResponse)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

}
