package com.rschwartz.bankingapi.accounts.adapter.out.persistence.mapper;

import com.rschwartz.bankingapi.accounts.adapter.out.entity.AccountJpaEntity;
import com.rschwartz.bankingapi.accounts.adapter.out.entity.ActivityJpaEntity;
import com.rschwartz.bankingapi.accounts.domain.AccountNew;
import com.rschwartz.bankingapi.accounts.domain.Activity;
import com.rschwartz.bankingapi.accounts.domain.Activity.ActivityId;
import com.rschwartz.bankingapi.accounts.domain.ActivityWindow;
import com.rschwartz.bankingapi.accounts.domain.Money;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

  public AccountNew mapToDomain(
      final AccountJpaEntity account,
      final List<ActivityJpaEntity> activities,
      final Long withdrawalBalance,
      final Long depositBalance) {

    final Money baselineBalance = Money.subtract(
        Money.of(depositBalance),
        Money.of(withdrawalBalance));

    return AccountNew.withId(
        null,//new AccountId(account.getId()),
        baselineBalance,
        mapToActivityWindow(activities));
  }

  private ActivityWindow mapToActivityWindow(final List<ActivityJpaEntity> activities) {

    final List<Activity> mappedActivities = new ArrayList<>();

    for (final ActivityJpaEntity activity : activities) {
      mappedActivities.add(new Activity(
          new ActivityId(activity.getId()),
          null,//new AccountId(activity.getOwnerAccountId()),
          null,//new AccountId(activity.getSourceAccountId()),
          null, //new AccountId(activity.getTargetAccountId()),
          activity.getCreateDate(),
          null));
          //Money.of(activity.getAmount())));
    }

    return new ActivityWindow(mappedActivities);
  }

}
