package com.example.demo

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.starter.EnableTelegramBot;

@SpringBootApplication
@EnableTelegramBot
class DemoApplication {
	static void main(String[] args) {
//			try {
//				TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class)
//				botsApi.registerBot(new TelegramBot())
				SpringApplication.run(DemoApplication, args)
//			}catch (TelegramApiException e){
//				e.printStackTrace()
//			}
	}
}