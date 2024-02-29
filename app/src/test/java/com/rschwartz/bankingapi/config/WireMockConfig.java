package com.rschwartz.bankingapi.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class WireMockConfig {

  @Bean(initMethod = "start", destroyMethod = "stop")
  public WireMockServer mockUserService() {
    return new WireMockServer(1030);
  }

  @Bean(initMethod = "start", destroyMethod = "stop")
  public WireMockServer mockBacenService() {
    return new WireMockServer(1031);
  }

}
