package com.rschwartz.bankingapi.accounts.adapter.out.persistence.mapper;

import com.rschwartz.bankingapi.accounts.adapter.out.entity.ActivityJpaEntity;
import com.rschwartz.bankingapi.accounts.domain.Activity;
import org.springframework.stereotype.Component;

@Component
public class ActivityMapper {

  public ActivityJpaEntity mapToJpaEntity(final Activity activity) {

    return null;
    /*return new ActivityJpaEntity(
        activity.getId() == null ? null : activity.getId().getValue(),
        activity.getTimestamp(),
        1L,
        1L,
        1L,
        //activity.getOwnerAccountId().getValue(),
        //activity.getSourceAccountId().getValue(),
        //activity.getTargetAccountId().getValue(),
        activity.getMoney().getAmount().longValue());

     */
  }

}
