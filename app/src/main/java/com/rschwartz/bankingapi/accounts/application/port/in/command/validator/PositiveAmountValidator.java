package com.rschwartz.bankingapi.accounts.application.port.in.command.validator;




import static java.util.Objects.isNull;

import com.rschwartz.bankingapi.accounts.application.port.in.command.validator.annotation.PositiveAmount;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class PositiveAmountValidator implements ConstraintValidator<PositiveAmount, BigDecimal> {

    @Override
    public boolean isValid(final BigDecimal value, final ConstraintValidatorContext context) {

        if (isNull(value)) {
            return true;
        }

        return isPositive(value);
    }

    private boolean isPositive(final BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) > 0;
    }

}
