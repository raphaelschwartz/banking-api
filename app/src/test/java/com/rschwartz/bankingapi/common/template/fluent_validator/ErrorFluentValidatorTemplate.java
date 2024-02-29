package com.rschwartz.bankingapi.common.template.fluent_validator;

import br.com.fluentvalidator.context.Error;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

public class ErrorFluentValidatorTemplate implements TemplateLoader {

  public static final String INVALID = "invalid";

  @Override
  public void load() {
    getValidTemplate();
  }

  private static void getValidTemplate() {

    Fixture.of(Error.class).addTemplate(INVALID, new Rule() {
      {
        add("code", "1");
        add("field", "name");
        add("attemptedValue", "2");
        add("message", "Required parameter.");
      }
    });

  }

}
