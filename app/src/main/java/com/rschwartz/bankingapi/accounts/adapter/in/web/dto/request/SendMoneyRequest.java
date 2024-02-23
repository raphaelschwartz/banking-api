package com.rschwartz.bankingapi.accounts.adapter.in.web.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class SendMoneyRequest {

  @Schema(description = "Source account id", example = "12345")
  private final Long sourceAccountId;

  @Schema(description = "Target account id", example = "56789")
  private final Long targetAccountId;

  @Schema(description = "Account balance", example = "10.99")
  private final String amount;

}
