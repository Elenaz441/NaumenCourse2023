package ru.urfu.Telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.urfu.Bot;

/**
 * Класс для телеграм-бота
 */
public class TelegramBot extends TelegramLongPollingBot {

    Bot bot;

    /**
     * Конструктор для телеграм-бота
     * @param bot - логика нашего бота
     */
    public TelegramBot(Bot bot) {
        this.bot = bot;
    }

    /**
     * Начать работу телеграм-бота
     */
    public void startTelegramBot() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(this);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return bot.getBotName();
    }

    @Override
    public String getBotToken() {
        return bot.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message msg = update.getMessage();
        String response = bot.formResponse(msg.getText());
        sendText(msg.getFrom().getId(), response);
    }

    /**
     * Отправка сообщений
     */
    private void sendText(Long userId, String message){
        SendMessage sm = SendMessage.builder()
                .chatId(userId.toString())
                .text(message).build();
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
