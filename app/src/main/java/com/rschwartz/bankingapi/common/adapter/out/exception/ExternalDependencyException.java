package com.rschwartz.bankingapi.common.adapter.out.exception;

public class ExternalDependencyException extends RuntimeException {

  private static final String MESSAGE = "Unable to process request.";

  public ExternalDependencyException() {
    super(MESSAGE);
  }

}
