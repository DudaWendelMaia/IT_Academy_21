package com.MariaEduardaWendelMaia.BallitChampionship;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableJpaRepositories(basePackages = {"com.MariaEduardaWendelMaia.BallitChampionship.repository"})
@EntityScan(basePackages = {"com.MariaEduardaWendelMaia.BallitChampionship.entity"})
public class BallitChampionshipApplication {

	public static void main(String[] args) {
		SpringApplication.run(BallitChampionshipApplication.class, args);
	}

}
