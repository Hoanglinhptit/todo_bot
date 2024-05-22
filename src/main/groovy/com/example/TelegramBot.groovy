package com.example

import groovy.json.JsonBuilder
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

@Component
class TelegramBot extends TelegramLongPollingBot {
    @Value('${telegram.bot.token}')
    private String botToken

    @Value('${telegram.bot.name}')
    private String botUsername

    @Value('${telegram.bot.server}')
    private String serverUrl

    @Autowired
    private DefaultBotOptions defaultBotOptions

    TelegramBot(){
        super("7023268217:AAGhTqdpt4PxvEf_5dJIKXpnd76LP_jXG3c")
    }

    @Override
    void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText().trim()
            long chatId = update.getMessage().getChatId()

            String responseText = handleCommand(messageText, chatId)
            SendMessage message = new SendMessage()
            message.setChatId(chatId)
            message.setText(responseText)

            try {
                execute(message)
            } catch (TelegramApiException e) {
                e.printStackTrace()
            }
        }
    }

    String handleCommand(String command, Long chatId) {
        def parts = command.split("\\s+", 2)
        println("parts:"+ parts)
        def commandName = parts[0].toLowerCase()
        def matcher = command =~ /<([^>]+)>/
        def task = matcher ? matcher[0][1] : null
        switch (commandName) {
            case '/create':
                if (parts.size() == 2) {
                    return makeRestApiRequest("create", chatId, task)
                }
                return "Usage: /create <task>"
            case '/list':
                return makeRestApiRequest("list", chatId, task)
            case '/update':
                if (parts.size() == 2) {
                    return makeRestApiRequest("update", chatId, parts[1])
                }
                return "Usage: /update <task_id> <new_task>"
            case '/delete':
                if (parts.size() == 2) {
                    return makeRestApiRequest("delete", chatId, parts[1])
                }
                return "Usage: /delete <task_id>"

            default:
                return "Invalid command. Available commands: /create, /list, /update, /delete, /logout"
        }
    }

    String makeRestApiRequest(String action, Long chatId, String task) {
        def apiUrl = "http://localhost:8080/api/tasks/${action}"
        def httpClient = HttpClients.createDefault()
        def httpPost = new HttpPost(apiUrl)
        httpPost.setHeader("Content-Type", "application/json")

        def requestBodyMap = [chatId: chatId, task: task]
        def requestBodyJson = new JsonBuilder(requestBodyMap).toString()
        httpPost.setEntity(new StringEntity(requestBodyJson))

        try {
            def response = httpClient.execute(httpPost)
            def responseString = EntityUtils.toString(response.getEntity())
            httpClient.close()
            return responseString
        } catch (Exception e) {
            e.printStackTrace()
            return "Error while making API request."
        }
    }


//    String generateResponseFromOpenAI(String inputText) {
//        println(inputText)
//        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
//            def openaiApiKey = openaiApiKey
//            def OPENAI_COMPLETIONS_URL = "https://api.openai.com/v1/completions"
//
//            HttpPost httpPost = new HttpPost(OPENAI_COMPLETIONS_URL)
//            httpPost.setHeader("Authorization", "Bearer " + openaiApiKey)
//            httpPost.setHeader("Content-Type", "application/json")
//
//            def requestBodyMap = [
//                    prompt: inputText,
//                    max_tokens: 50,
//                    model: "gpt-3.5-turbo" // or whichever model you intend to use
//            ]
//            def jsonBuilder = new JsonBuilder(requestBodyMap)
//            StringEntity requestBody = new StringEntity(jsonBuilder.toString())
//            httpPost.setEntity(requestBody)
//
//            String response = httpClient.execute(httpPost, { httpResponse ->
//                EntityUtils.toString(httpResponse.getEntity())
//            })
//            return response
//        } catch (Exception e) {
//            e.printStackTrace()
//            return "Sorry, I couldn't generate a response at the moment."
//        }
//    }

    @Override
    String getBotUsername() {
        return botUsername
    }

    @Override
    String getBotToken() {
        return botToken
    }
}
