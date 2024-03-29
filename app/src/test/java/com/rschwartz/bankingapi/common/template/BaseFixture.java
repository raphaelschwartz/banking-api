package com.rschwartz.bankingapi.common.template;

import lombok.Getter;

@Getter
public enum BaseFixture {

  CLIENT("com.rschwartz.bankingapi.common.template.client"),
  ENTITY("com.rschwartz.bankingapi.common.template.entity"),
  INPUT("com.rschwartz.bankingapi.common.template.input"),
  OUTPUT("com.rschwartz.bankingapi.common.template.output"),
  REQUEST("com.rschwartz.bankingapi.common.template.request"),
  RESPONSE("com.rschwartz.bankingapi.common.template.response"),
  FLUENT_VALIDATOR("com.rschwartz.bankingapi.common.template.fluent_validator");

  private final String path;

  BaseFixture(final String path) {
    this.path = path;
  }

}
