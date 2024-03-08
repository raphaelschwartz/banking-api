package com.rschwartz.bankingapi.accounts.application.port.in.command.validator.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.rschwartz.bankingapi.accounts.application.port.in.command.validator.PositiveAmountValidator;
import jakarta.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.math.BigDecimal;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = PositiveAmountValidator.class)
@Documented
public @interface PositiveAmount {

    String message() default "must be greater than zero";

    Class<?>[] groups() default {};

    Class<? extends BigDecimal>[] payload() default {};

}
