package com.rschwartz.bankingapi.accounts.domain;

import java.math.BigInteger;
import lombok.NonNull;
import lombok.Value;

@Value
public class Money {

	public static Money ZERO = Money.of(0L);

	@NonNull
	private final BigInteger amount;

	public boolean isPositiveOrZero(){
		return this.amount.compareTo(BigInteger.ZERO) >= 0;
	}

	public boolean isNegative(){
		return this.amount.compareTo(BigInteger.ZERO) < 0;
	}

	public boolean isPositive(){
		return this.amount.compareTo(BigInteger.ZERO) > 0;
	}

	public boolean isGreaterThanOrEqualTo(final Money money){
		return this.amount.compareTo(money.amount) >= 0;
	}

	public boolean isGreaterThan(final Money money){
		return this.amount.compareTo(money.amount) >= 1;
	}

	public static Money of(final long value) {
		return new Money(BigInteger.valueOf(value));
	}

	public static Money add(final Money a, final Money b) {
		return new Money(a.amount.add(b.amount));
	}

	public Money minus(final Money money){
		return new Money(this.amount.subtract(money.amount));
	}

	public Money plus(final Money money){
		return new Money(this.amount.add(money.amount));
	}

	public static Money subtract(final Money a, final Money b) {
		return new Money(a.amount.subtract(b.amount));
	}

	public Money negate(){
		return new Money(this.amount.negate());
	}

}
