package com.rschwartz.bankingapi.common.template.client;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.rschwartz.bankingapi.accounts.adapter.out.gateway.client.dto.response.UserResponse;

public class UserResponseTemplate implements TemplateLoader {

  public static final String VALID = "valid";

  @Override
  public void load() {
    getValidTemplate();
  }

  private static void getValidTemplate() {

    Fixture.of(UserResponse.class).addTemplate(VALID, new Rule() {
      {
        add("id", 1L);
        add("fullName", "Name Fake");
      }
    });

  }

}
