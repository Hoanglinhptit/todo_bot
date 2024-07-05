package com.example.starter;

import org.springframework.context.annotation.Import;
import org.telegram.telegrambots.longpolling.starter.TelegramBotStarterConfiguration;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({TelegramBotStarterConfiguration.class})
public @interface EnableTelegramBot {
}