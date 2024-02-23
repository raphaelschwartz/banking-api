package com.rschwartz.bankingapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BankingApiApplication {

	public static void main(final String[] args) {
		SpringApplication.run(BankingApiApplication.class, args);
	}

}
