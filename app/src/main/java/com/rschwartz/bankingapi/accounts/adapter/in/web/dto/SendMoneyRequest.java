package com.rschwartz.bankingapi.accounts.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class SendMoneyRequest {

  @Schema(description = "Account number", example = "0123456789")
  private final String sourceAccountId;

  private final String targetAccountId;

  @Schema(description = "Account balance", example = "10.99")
  private final String amount;

}
