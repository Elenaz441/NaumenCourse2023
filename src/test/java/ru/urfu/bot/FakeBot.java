package ru.urfu.bot;

import java.util.ArrayList;
import java.util.List;

/**
 * Поведение бота для тестирования
 */
public class FakeBot implements Bot {

    private final List<String> messages = new ArrayList<>();

    public List<String> getMessages() {
        return messages;
    }

    /**
     * Отправка сообщения пользователю
     *
     * @param chatId  идентификатор чата
     * @param message текст сообщения
     */
    @Override
    public void sendMessage(Long chatId, String message) {
        messages.add(message);
    }
}
