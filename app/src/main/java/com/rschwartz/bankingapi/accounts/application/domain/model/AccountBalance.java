package com.rschwartz.bankingapi.accounts.application.domain.model;

import static java.util.Objects.isNull;

import java.math.BigDecimal;
import lombok.Getter;

@Getter
public class AccountBalance {

	protected static final String BALANCE_REQUIRED_ERROR_MESSAGE = "Must provide a balance.";
	protected static final String AMOUNT_REQUIRED_ERROR_MESSAGE = "Must provide a amount.";
	protected static final String INSUFFICIENT_FUNDS_ERROR_MESSAGE = "Insufficient balance for transfer.";

	private final BigDecimal value;

	public AccountBalance(final Money balance) {
		validate(balance);
		this.value = balance.getValue();
	}

	private void validate(final Money balance) {

		if (isNull(balance)) {
			throw new IllegalArgumentException(BALANCE_REQUIRED_ERROR_MESSAGE);
		}

	}

	public AccountBalance debit(final Money amount) {

		validateDebit(amount);

		return new AccountBalance(Money.ofDecimal(value).minus(amount));
	}

	private void validateDebit(final Money amount) {

		if (isNull(amount)) {
			throw new IllegalArgumentException(AMOUNT_REQUIRED_ERROR_MESSAGE);
		}

		if (isInsufficientBalanceForTransfer(amount)) {
			throw new IllegalArgumentException(INSUFFICIENT_FUNDS_ERROR_MESSAGE);
		}

	}

	private boolean isInsufficientBalanceForTransfer(final Money money) {
		return money.isGreaterThan(Money.ofDecimal(this.value));
	}

	public AccountBalance credit(final Money amount) {

		validateCredit(amount);

		return new AccountBalance(Money.ofDecimal(this.value).plus(amount));
	}

	private void validateCredit(final Money money) {

		if (isNull(money)) {
			throw new IllegalArgumentException(AMOUNT_REQUIRED_ERROR_MESSAGE);
		}

	}

}
