package ru.urfu;

/**
 * Интерфейс для ботов
 */
public interface Bot {

    /**
     * Общая логика для ботов
     */
    Logic logic = new Logic();

    /**
     * Функция старта
     */
    void startBot();

    /**
     * Отправка сообщения
     */
    void sendText(Long chatId, String message);
}
