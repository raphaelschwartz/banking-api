package com.rschwartz.bankingapi.common.template;

import lombok.Getter;

@Getter
public enum BaseFixture {

  ENTITY("com.rschwartz.bankingapi.common.template.entity"),
  OUTPUT("com.rschwartz.bankingapi.common.template.output"),
  REQUEST("com.rschwartz.bankingapi.common.template.request"),
  RESPONSE("com.rschwartz.bankingapi.common.template.response"),
  FLUENT_VALIDATOR("com.rschwartz.bankingapi.common.template.fluentvalidator");

  private final String path;

  BaseFixture(final String path) {
    this.path = path;
  }

}
