package com.rschwartz.bankingapi.accounts.adapter.out.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@Schema(name = "Account")
public class AccountResponse {

  @Schema(description = "External Id", example = "dcabcfb1-e19d-46d6-9524-d55f308a3d61")
  private UUID externalId;

  @Schema(description = "Owner Id", example = "cd42a2ab-2af4-4391-ac92-b41e76a13e57")
  private UUID ownerId;

  @Schema(description = "Number", example = "0123456789")
  private final String number;

  @Schema(description = "Balance", example = "10.99")
  private final BigDecimal balance;

  @Schema(description = "Available Limit", example = "1500")
  private final BigDecimal limit;

  @Schema(description = "Update Date", example = "0123456789")
  private final LocalDateTime updateDate;

}
