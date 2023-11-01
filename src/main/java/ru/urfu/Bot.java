package ru.urfu;

/**
 * Класс бота
 */
public class Bot {

    private String botName;
    private String botToken;

    /**
     * Конструктор бота
     */
    public Bot(String botName, String botToken) {
        this.botName = botName;
        this.botToken = botToken;
    }

    /**
     * Геттер для названия бота
     */
    public String getBotName() {
        return botName;
    }

    /**
     * Геттер для токена бота
     */
    public String getBotToken() {
        return botToken;
    }

    /**
     * Формирование ответа пользователю
     */
    public String formResponse(String message) {
        return "Ваше сообщение: " + message;
    }
}
