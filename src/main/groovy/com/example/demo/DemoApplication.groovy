package com.example.demo

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

import org.telegram.telegrambots.starter.EnableTelegramBot;

@SpringBootApplication
@EnableTelegramBot
class DemoApplication {
	static void main(String[] args) {
				SpringApplication.run(DemoApplication, args)
	}
}
