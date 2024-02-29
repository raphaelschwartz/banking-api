package com.rschwartz.bankingapi.common.template.request;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.rschwartz.bankingapi.accounts.adapter.in.web.dto.request.SendMoneyRequest;

public class SendMoneyRequestTemplate implements TemplateLoader {

  public static final String VALID = "valid";
  public static final String INVALID_REQUIRED = "invalid_required";
  public static final String INVALID_SOURCE_ACCOUNT_ID = "invalid_source_account_id";
  public static final String INVALID_TARGET_ACCOUNT_ID = "invalid_target_account_id";
  public static final String INVALID_THRESHOLD_EXCEEDED = "invalid_threshold_exceeded";
  public static final String INVALID_INSUFFICIENT_FUNDS = "invalid_insufficient_funds";

  @Override
  public void load() {
    getValidTemplate();
    getInvalidTemplate();
  }

  private static void getValidTemplate() {

    Fixture.of(SendMoneyRequest.class).addTemplate(VALID, new Rule() {
      {
        add("sourceAccountId", 1L);
        add("targetAccountId", 2L);
        add("amount", "15.00");
      }
    });
  }

  private static void getInvalidTemplate() {

    Fixture.of(SendMoneyRequest.class).addTemplate(INVALID_REQUIRED, new Rule() {
      {
        add("sourceAccountId", null);
        add("targetAccountId", null);
        add("amount", null);
      }
    });

    Fixture.of(SendMoneyRequest.class).addTemplate(INVALID_SOURCE_ACCOUNT_ID)
        .inherits(VALID, new Rule() {
          {
            add("sourceAccountId", 999L);
          }
        });

    Fixture.of(SendMoneyRequest.class).addTemplate(INVALID_TARGET_ACCOUNT_ID)
        .inherits(VALID, new Rule() {
          {
            add("targetAccountId", 999L);
          }
        });

    Fixture.of(SendMoneyRequest.class).addTemplate(INVALID_THRESHOLD_EXCEEDED, new Rule() {
      {
        add("sourceAccountId", 3L);
        add("targetAccountId", 4L);
        add("amount", "70.00");
      }
    });

    Fixture.of(SendMoneyRequest.class).addTemplate(INVALID_INSUFFICIENT_FUNDS)
        .inherits(VALID, new Rule() {
          {
            add("amount", "180.00");
          }
        });

  }

}
