package com.repeat.telegram;

import com.repeat.telegram.message.MessageBuilder;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class RepeatBot extends TelegramLongPollingBot {

    private final Logger logger = LoggerFactory.getLogger(RepeatBot.class);
    private final String username;
    private final MessageBuilder messageBuilder;

    public RepeatBot(@Value("${app.telegram.username}") String username,
                     @Value("${app.telegram.token}") String botToken,
                     MessageBuilder messageBuilder) throws TelegramApiException {
        super(botToken);
        this.username = username;
        this.messageBuilder = messageBuilder;

        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
        System.out.println("Бот успешно запущен!");
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();

        if (message != null && message.hasText()) {
            Long chatId = message.getChatId();
            String text = message.getText();
            String senderName = message.getFrom().getUserName();
            SendMessage sendMessage;

            logger.info("Входящее сообщение от {} (ID: {}): {}", senderName, chatId, text);

            switch (text){
                case "/start":
                    sendMessage = messageBuilder.buildMessage(chatId,"Добро пожаловать! " +
                            "Я готов повторять ваши сообщения!");
                    break;
                default:
                    sendMessage = messageBuilder.buildMessage(chatId, text);
                    break;
            }

            execute(sendMessage);
            logger.info("Исходящее сообщение для {} (ID: {}): {}", senderName, chatId, sendMessage.getText());
        }

    }

    @Override
    public String getBotUsername() {
        return username;
    }
}
