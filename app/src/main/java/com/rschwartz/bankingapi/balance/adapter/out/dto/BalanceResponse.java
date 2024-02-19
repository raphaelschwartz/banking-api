package com.rschwartz.bankingapi.balance.adapter.out.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@Schema(name = "Balance")
public class BalanceResponse {

  @Schema(description = "Account number", example = "0123456789")
  private final String accountNumber;

  @Schema(description = "Account balance", example = "10.99")
  private final BigDecimal value;

  @Schema(description = "Account limit", example = "1500")
  private final String limit;

  @Schema(description = "Update Date", example = "0123456789")
  private final LocalDateTime updateDate;

}
