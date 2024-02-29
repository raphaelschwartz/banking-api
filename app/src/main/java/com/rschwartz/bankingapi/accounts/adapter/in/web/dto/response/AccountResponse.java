package com.rschwartz.bankingapi.accounts.adapter.in.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Account")
public class AccountResponse {

  @Schema(description = "Id", example = "12345")
  private Long id;

  @Schema(description = "Owner Id", example = "45678")
  private Long ownerId;

  @Schema(description = "Number", example = "0123456789")
  private String number;

  @Schema(description = "Balance", example = "10.99")
  private BigDecimal balance;

  @Schema(description = "Update date", example = "0123456789")
  private LocalDateTime updateDate;

}
