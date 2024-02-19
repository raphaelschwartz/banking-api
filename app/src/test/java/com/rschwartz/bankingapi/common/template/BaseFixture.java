package com.rschwartz.bankingapi.common.template;

import lombok.Getter;

@Getter
public enum BaseFixture {

  DOMAIN("com.rschwartz.bankingapi.common.template.domain"),
  ENTITY("com.rschwartz.bankingapi.common.template.entity"),
  OUTPUT("com.rschwartz.bankingapi.common.template.output"),
  REQUEST("com.rschwartz.bankingapi.common.template.request"),
  FLUENT_VALIDATOR("com.rschwartz.bankingapi.common.template.fluentvalidator");

  private final String path;

  BaseFixture(final String path) {
    this.path = path;
  }

}
