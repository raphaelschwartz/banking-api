package com.rschwartz.bankingapi.accounts.adapter.out.repository;

import com.rschwartz.bankingapi.accounts.adapter.out.entity.AccountJpaEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountJpaEntity, Long> {

  Optional<AccountJpaEntity> findByNumber(String number);

}
