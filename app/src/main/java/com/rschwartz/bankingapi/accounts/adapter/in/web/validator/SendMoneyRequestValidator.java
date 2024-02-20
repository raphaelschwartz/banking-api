package com.rschwartz.bankingapi.accounts.adapter.in.web.validator;

import static br.com.fluentvalidator.predicate.LogicalPredicate.not;
import static br.com.fluentvalidator.predicate.ObjectPredicate.nullValue;
import static br.com.fluentvalidator.predicate.StringPredicate.stringEmptyOrNull;
import static br.com.fluentvalidator.predicate.StringPredicate.stringSizeGreaterThanOrEqual;
import static br.com.fluentvalidator.predicate.StringPredicate.stringSizeLessThanOrEqual;

import br.com.fluentvalidator.AbstractValidator;
import com.rschwartz.bankingapi.accounts.adapter.in.web.dto.SendMoneyRequest;
import org.springframework.stereotype.Component;

@Component
public class SendMoneyRequestValidator extends AbstractValidator<SendMoneyRequest> {

  protected static final String FIELD_SOURCE_ACCOUNT_ID = "sourceAccountId";
  protected static final String FIELD_TARGET_ACCOUNT_ID = "TargetAccountId";
  protected static final String FIELD_AMOUNT = "amount";

  protected static final String MESSAGE_REQUIRED_FIELD = "Required parameter.";
  protected static final String MESSAGE_MINIMUM_SIZE_5_INVALID = "Field must be greater or equal to 5.";
  protected static final String MESSAGE_MAXIMUM_SIZE_10_INVALID = "Field must be less or equal to 10.";

  @Override
  public void rules() {

    ruleFor(SendMoneyRequest::getSourceAccountId)
        .must(not(stringEmptyOrNull()))
        .withMessage(MESSAGE_REQUIRED_FIELD)
        .withFieldName(FIELD_SOURCE_ACCOUNT_ID)

        .must(stringSizeGreaterThanOrEqual(5))
        .when(not(stringEmptyOrNull()))
        .withMessage(MESSAGE_MINIMUM_SIZE_5_INVALID)
        .withFieldName(FIELD_SOURCE_ACCOUNT_ID)

        .must(stringSizeLessThanOrEqual(10))
        .when(not(stringEmptyOrNull()))
        .withMessage(MESSAGE_MAXIMUM_SIZE_10_INVALID)
        .withFieldName(FIELD_SOURCE_ACCOUNT_ID)
        .critical();

    ruleFor(SendMoneyRequest::getTargetAccountId)
        .must(not(nullValue()))
        .withMessage(MESSAGE_REQUIRED_FIELD)
        .withFieldName(FIELD_TARGET_ACCOUNT_ID)

        .must(stringSizeGreaterThanOrEqual(5))
        .when(not(stringEmptyOrNull()))
        .withMessage(MESSAGE_MINIMUM_SIZE_5_INVALID)
        .withFieldName(FIELD_TARGET_ACCOUNT_ID)

        .must(stringSizeLessThanOrEqual(10))
        .when(not(stringEmptyOrNull()))
        .withMessage(MESSAGE_MAXIMUM_SIZE_10_INVALID)
        .withFieldName(FIELD_TARGET_ACCOUNT_ID)
        .critical();

    ruleFor(SendMoneyRequest::getAmount)
        .must(not(nullValue()))
        .withMessage(MESSAGE_REQUIRED_FIELD)
        .withFieldName(FIELD_AMOUNT)
        .critical();
  }

}
