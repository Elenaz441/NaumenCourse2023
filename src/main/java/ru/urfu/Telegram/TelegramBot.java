package ru.urfu.Telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.urfu.Bot;
import ru.urfu.Logic;

/**
 * Класс для телеграм-бота
 */
public class TelegramBot extends TelegramLongPollingBot implements Bot {
    
    private final String botName;
    private final String botToken;
    private final Logic logic;

    /**
     * Конструктор для телеграм-бота
     */
    public TelegramBot(String botName, String botToken, Logic logic) {
        this.botName = botName;
        this.botToken = botToken;
        this.logic = logic;
    }

    @Override
    public String getBotUsername() {
        return this.botName;
    }

    @Override
    public String getBotToken() {
        return this.botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message msg = update.getMessage();
        String response = logic.formResponse(msg.getText());
        sendText(msg.getFrom().getId(), response);
    }

    /**
     * Функция старта
     */
    @Override
    public void startBot() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(this);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Отправка сообщения
     */
    @Override
    public void sendText(Long chatId, String message) {
        SendMessage sm = SendMessage.builder()
                .chatId(Long.toString(chatId))
                .text(message).build();
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
