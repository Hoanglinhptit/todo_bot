//package com.example
//
//import jakarta.json.bind.Jsonb
//import okhttp3.OkHttpClient
//import okhttp3.Request
//import okhttp3.Response
//import jakarta.json.bind.Jsonb
//import jakarta.json.bind.JsonbBuilder
//import okhttp3.MediaType
//import okhttp3.RequestBody
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.stereotype.Component
//import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient
//import org.telegram.telegrambots.longpolling.BotSession
//import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer
//import org.telegram.telegrambots.longpolling.starter.AfterBotRegistration
//import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot
//import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer
//import org.telegram.telegrambots.meta.TelegramUrl
//import org.telegram.telegrambots.meta.api.methods.send.SendMessage
//import org.telegram.telegrambots.meta.api.objects.Update
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException
//import org.telegram.telegrambots.meta.generics.TelegramClient
//
//
//@Component
//class TelegramBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {
//    private static final OkHttpClient client = new OkHttpClient()
//
//    private TelegramClient telegramClient
//    private String botToken
//
//    private TelegramUrl telegramUrl
//
//    @Autowired
//    TelegramBot(@Value('${telegram.bot.token}') String botToken) {
//        this.botToken = botToken;
//        telegramClient = new OkHttpTelegramClient("7023268217:AAGhTqdpt4PxvEf_5dJIKXpnd76LP_jXG3c")
//    }
//
//    @Override
//    String getBotToken() {
//        return botToken
//    }
//
//    @Override
//    LongPollingUpdateConsumer getUpdatesConsumer() {
//        return this
//    }
//
//    @Override
//    void consume(Update update) {
//        if (update.hasMessage() && update.getMessage().hasText()) {
//            String messageText = update.getMessage().getText().trim()
//            long chatId = update.getMessage().getChatId()
//
//            String responseText = handleCommand(messageText, chatId)
//            SendMessage message = SendMessage // Create a message object
//                    .builder()
//                    .chatId(chatId)
//                    .text(responseText)
//                    .build()
//
//
//            try {
//                telegramClient.execute(message)
//            } catch (TelegramApiException e) {
//                e.printStackTrace()
//            }
//        }
//    }
//
//    String handleCommand(String command, Long chatId) {
//        def parts = command.split("\\s+", 2)
//        println("parts:" + parts)
//        def commandName = parts[0].toLowerCase()
//        def matcher = command =~ /<([^>]+)>/
//        def task = matcher ? matcher[0][1] : null
//        switch (commandName) {
//            case '/create':
//                if (parts.size() == 2) {
//                    return makeRestApiRequest("create", chatId, task as String)
//                }
//                return "Usage: /create <task>"
//            case '/list':
//                return makeRestApiRequest("list", chatId, task as String)
//            case '/update':
//                if (parts.size() == 2) {
//                    return makeRestApiRequest("update", chatId, parts[1])
//                }
//                return "Usage: /update <task_id> <new_task>"
//            case '/delete':
//                if (parts.size() == 2) {
//                    return makeRestApiRequest("delete", chatId, parts[1])
//                }
//                return "Usage: /delete <task_id>"
//
//            default:
//                return "Invalid command. Available commands: /create, /list, /update, /delete, /logout"
//        }
//    }
//
//    String makeRestApiRequest(String action, Long chatId, String task) {
//        def apiUrl = "http://localhost:8080/api/tasks/${action}"
//        def requestBodyMap = [chatId: chatId, task: task]
//
//        Jsonb jsonb = JsonbBuilder.create()
//        String requestBodyJsonString = jsonb.toJson(requestBodyMap)
//        RequestBody requestBodyJson = RequestBody.create(MediaType.parse("application/json"), requestBodyJsonString)
//
//        Request request = new Request.Builder()
//                .url(apiUrl)
//                .post(requestBodyJson as RequestBody)
//                .addHeader("Content-Type", "application/json")
//                .build()
//
//
//
//        try {
//            Response response = client.newCall(request).execute()
//            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response)
//            return response.body().string()
//        } catch (Exception e) {
//            e.printStackTrace()
//            return "Error while making API request."
//        }
//    }
//
//
////    String generateResponseFromOpenAI(String inputText) {
////        println(inputText)
////        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
////            def openaiApiKey = openaiApiKey
////            def OPENAI_COMPLETIONS_URL = "https://api.openai.com/v1/completions"
////
////            HttpPost httpPost = new HttpPost(OPENAI_COMPLETIONS_URL)
////            httpPost.setHeader("Authorization", "Bearer " + openaiApiKey)
////            httpPost.setHeader("Content-Type", "application/json")
////
////            def requestBodyMap = [
////                    prompt: inputText,
////                    max_tokens: 50,
////                    model: "gpt-3.5-turbo" // or whichever model you intend to use
////            ]
////            def jsonBuilder = new JsonBuilder(requestBodyMap)
////            StringEntity requestBody = new StringEntity(jsonBuilder.toString())
////            httpPost.setEntity(requestBody)
////
////            String response = httpClient.execute(httpPost, { httpResponse ->
////                EntityUtils.toString(httpResponse.getEntity())
////            })
////            return response
////        } catch (Exception e) {
////            e.printStackTrace()
////            return "Sorry, I couldn't generate a response at the moment."
////        }
////    }
//    @AfterBotRegistration
//    void afterRegistration(BotSession botSession) {
//        println("Registered bot running state is: " + botSession.isRunning() + " get telegram url" + botSession.getTelegramUrlSupplier())
//    }
//}
