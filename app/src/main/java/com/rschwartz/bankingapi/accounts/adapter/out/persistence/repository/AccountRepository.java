package com.rschwartz.bankingapi.accounts.adapter.out.persistence.repository;

import com.rschwartz.bankingapi.accounts.adapter.out.persistence.entity.AccountJpaEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountJpaEntity, Long> {

  Optional<AccountJpaEntity> findByIdAndActiveTrue(Long id);

}
