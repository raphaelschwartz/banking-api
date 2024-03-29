package com.rschwartz.bankingapi.accounts.application.domain.service;

import com.rschwartz.bankingapi.accounts.application.port.in.useCase.LoadAccountUseCase;
import com.rschwartz.bankingapi.accounts.application.port.out.LoadAccountPort;
import com.rschwartz.bankingapi.accounts.application.port.out.dto.AccountOutput;
import com.rschwartz.bankingapi.accounts.application.port.out.mapper.AccountOutputMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoadAccountService implements LoadAccountUseCase {

  private final LoadAccountPort port;
  private final AccountOutputMapper mapper;

  @Override
  public Optional<AccountOutput> execute(final Long id) {

    return port.findById(id)
        .map(mapper::mapDomainToOutput);
  }

}
