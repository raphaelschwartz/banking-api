package com.rschwartz.bankingapi.accounts.adapter.in.web.validator;

import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.ObjectPredicate.nullValue;

import br.com.fluentvalidator.AbstractValidator;
import com.rschwartz.bankingapi.accounts.adapter.in.web.dto.request.SendMoneyRequest;
import org.springframework.stereotype.Component;

@Component
public class SendMoneyRequestValidator extends AbstractValidator<SendMoneyRequest> {

  protected static final String FIELD_SOURCE_ACCOUNT_ID = "sourceAccountId";
  protected static final String FIELD_TARGET_ACCOUNT_ID = "TargetAccountId";
  protected static final String FIELD_AMOUNT = "amount";

  protected static final String MESSAGE_REQUIRED_FIELD = "Required parameter.";

  @Override
  public void rules() {

    ruleFor(SendMoneyRequest::getSourceAccountId)
        .must(not(nullValue()))
        .withMessage(MESSAGE_REQUIRED_FIELD)
        .withFieldName(FIELD_SOURCE_ACCOUNT_ID)
        .critical();

    ruleFor(SendMoneyRequest::getTargetAccountId)
        .must(not(nullValue()))
        .withMessage(MESSAGE_REQUIRED_FIELD)
        .withFieldName(FIELD_TARGET_ACCOUNT_ID)
        .critical();

    ruleFor(SendMoneyRequest::getAmount)
        .must(not(nullValue()))
        .withMessage(MESSAGE_REQUIRED_FIELD)
        .withFieldName(FIELD_AMOUNT)
        .critical();
  }

}
