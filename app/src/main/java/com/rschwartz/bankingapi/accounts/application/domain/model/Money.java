package com.rschwartz.bankingapi.accounts.application.domain.model;

import java.math.BigDecimal;
import java.util.Objects;
import lombok.Getter;

@Getter
public class Money {

	protected static final String AMOUNT_REQUIRED_ERROR_MESSAGE = "Must provide a amount.";
	protected static final String AMOUNT_NEGATIVE_ERROR_MESSAGE = "Amount must be positive.";

	public static Money ZERO = Money.ofInteger(0L);

	private final BigDecimal value;

	public Money(final BigDecimal amount) {
		validate(amount);
		this.value = amount;
	}

	private void validate(final BigDecimal amount) {

		if (Objects.isNull(amount)) {
			throw new IllegalArgumentException(AMOUNT_REQUIRED_ERROR_MESSAGE);
		}

		if (isNegative(amount)) {
			throw new IllegalArgumentException(AMOUNT_NEGATIVE_ERROR_MESSAGE);
		}

	}

	private boolean isNegative(final BigDecimal amount){
		return amount.compareTo(BigDecimal.ZERO) < 0;
	}

	public static Money ofDecimal(final BigDecimal value) {
		return new Money(value);
	}
	public static Money ofInteger(final long value) {
		return new Money(BigDecimal.valueOf(value));
	}

	public Money minus(final Money money) {
		return new Money(this.value.subtract(money.getValue()));
	}

	public Money plus(final Money money){
		return new Money(this.value.add(money.getValue()));
	}

	public boolean isGreaterThan(final Money money) {
		return this.value.compareTo(money.getValue()) >= 1;
	}

}
