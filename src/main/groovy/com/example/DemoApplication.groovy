package com.example

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import com.example.starter.EnableTelegramBot
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import com.example.entities.Task
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException


@SpringBootApplication
@EnableJpaRepositories(basePackages ="com.example.repositories")
@EntityScan(basePackageClasses = Task.class)
@EnableTelegramBot
class DemoApplication {
	static void main(String[] args) {
				SpringApplication.run(DemoApplication, args)
	}


}
