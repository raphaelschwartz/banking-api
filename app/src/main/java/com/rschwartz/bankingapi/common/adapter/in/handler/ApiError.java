package com.rschwartz.bankingapi.common.adapter.in.handler;

import lombok.Data;

@Data
public class ApiError {

  private String code;

  private String message;

  public ApiError(final String code, final String message) {
    this.code = code;
    this.message = message;
  }

}
