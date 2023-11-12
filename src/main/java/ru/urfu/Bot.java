package ru.urfu;

/**
 * Интерфейс для ботов
 */
public interface Bot {

    /**
     * Функция старта
     */
    void startBot();

    /**
     * Отправка сообщения
     */
    void sendText(Long chatId, String message);
}
