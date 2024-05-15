package com.example.demo

import groovy.json.JsonBuilder
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
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

    @Value('${openai.api.key}')
    private String openaiApiKey

    private ArrayList<String> name;


    TelegramBot() {
        super('7023268217:AAGhTqdpt4PxvEf_5dJIKXpnd76LP_jXG3c')
//        super()
    }

    @Override
    void onUpdateReceived(Update update) {

        println(update)
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message_text = update.getMessage().getText()
            long chat_id = update.getMessage().getChatId()
//            String responseText = handleCommand(message_text.trim(), chat_id)
            SendMessage message = new SendMessage()
            message.setChatId(chat_id)
//            message.setText(responseText)
            message.setText("hello")
            try {
                execute(message)
            } catch (TelegramApiException e) {
                e.printStackTrace()
            }
        }
    }
//
//    String handleCommand(String command, Long chatId) {
//        def parts = command.split("\\s+", 2)
//        def commandName = parts[0].toLowerCase()
//
//        switch (commandName) {
//            case '/create':
//                if (parts.size() == 2) {
//                    // Make HTTP request to your REST API
//                    String apiResponse = makeRestApiRequest("CREATE", chatId, parts[1])
//                    return "Response from API: ${apiResponse}"
//                }
//                break
//
//            case '/list':
//                // Handle list command
//                break
//            case '/update':
//                // Handle update command
//                break
//
//            case '/delete':
//                // Handle delete command
//                break
//
//            default:
//                return "Invalid command. Available commands: /create, /list, /update, /delete"
//        }
//
//        return "Invalid command usage. Please check the command syntax."
//    }
//
//    String makeRestApiRequest(String action, Long chatId, String task) {
//        // Construct your REST API URL
//        def apiUrl = "http://yourapi.com/${action}"
//
//        // Create HTTP client
//        def httpClient = HttpClients.createDefault()
//
//        // Create HTTP POST request
//        def httpPost = new HttpPost(apiUrl)
//        httpPost.setHeader("Content-Type", "application/json")
//
//        // Construct request body
//        def requestBodyMap = [
//                chatId: chatId,
//                task  : task
//        ]
//        def requestBodyJson = new JsonBuilder(requestBodyMap).toString()
//        httpPost.setEntity(new StringEntity(requestBodyJson))
//
//        // Execute request and get response
//        def response = httpClient.execute(httpPost)
//        def responseString = EntityUtils.toString(response.getEntity())
//        httpClient.close()
//
//        return responseString
//    }


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
