package com.rschwartz.bankingapi.accounts.adapter.out.persistence.mapper;

import com.rschwartz.bankingapi.accounts.adapter.out.persistence.entity.TransactionJpaEntity;
import com.rschwartz.bankingapi.accounts.application.domain.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class ActivityMapper {

  public TransactionJpaEntity mapDomainToJpaEntity(final Transaction transaction) {

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
