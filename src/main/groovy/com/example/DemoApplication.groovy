package com.example

import com.example.starter.EnableTelegramBot
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import com.example.entities.Task

@SpringBootApplication
@EnableJpaRepositories(basePackages ="com.example.repositories")
@EntityScan(basePackageClasses = Task.class)
@EnableTelegramBot
class DemoApplication {
	static void main(String[] args) {
				SpringApplication.run(DemoApplication, args)
	}
}
