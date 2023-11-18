package ru.urfu.bot;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BotLogicTest {

    FakeBot bot;
    BotLogic logic;
    User user;

    /**
     * Функция, которая выполняется перед каждым тестом, заполняет все поля
     */
    @Before
    public void  setAllFields() {
        bot = new FakeBot();
        logic = new BotLogic(bot);
        user = new User(1L);
    }

    /**
     * Тестирование команды /test, если все ответы пользователя правильные
     */
    @Test
    public void processCommandTest() {
        logic.processCommand(user, "/test");
        assertEquals(bot.getMessages().size(), 1);
        assertEquals("Вычислите степень: 10^2", bot.getMessages().get(0));
        assertEquals(State.TEST, user.getState());

        logic.processCommand(user, "100");
        assertEquals("Правильный ответ!", bot.getMessages().get(1));
        assertEquals("Сколько будет 2 + 2 * 2", bot.getMessages().get(2));

        logic.processCommand(user, "6");
        assertEquals("Правильный ответ!", bot.getMessages().get(3));
        assertEquals("Тест завершен", bot.getMessages().get(4));
        assertEquals(State.INIT, user.getState());
    }

    /**
     * Тестирование команды /test, если пользователь дал неправильный ответ
     */
    @Test
    public void processCommandTestIfAnswerIncorrect() {
        logic.processCommand(user, "/test");

        logic.processCommand(user, "10000000");
        assertEquals("Вы ошиблись, верный ответ: 100", bot.getMessages().get(1));

        logic.processCommand(user, "6");
        assertEquals("Тест завершен", bot.getMessages().get(4));
        assertEquals(State.INIT, user.getState());

        assertEquals(1, user.getWrongAnswerQuestions().size());
        assertEquals("Вычислите степень: 10^2", user.getWrongAnswerQuestions().get(0).getText());
    }

    /**
     * Тестирование команды /notify
     */
    @Test
    public void processCommandNotify() throws InterruptedException {
        logic.processCommand(user, "/notify");
        assertEquals("Введите текст напоминания", bot.getMessages().get(0));

        logic.processCommand(user, "Проверка напоминаний");
        assertNotNull(user.getNotification());
        assertEquals("Через сколько секунд напомнить?", bot.getMessages().get(1));

        logic.processCommand(user, "1");
        assertEquals("Напоминание установлено", bot.getMessages().get(2));
        assertEquals(3, bot.getMessages().size());

        Thread.sleep(1010);

        assertEquals(4, bot.getMessages().size());
        assertEquals(
                "Сработало напоминание: 'Проверка напоминаний'",
                bot.getMessages().get(3));
    }

    /**
     * Тестирование команды /repeat
     */
    @Test
    public void processCommandRepeat() {
        logic.processCommand(user, "/test");
        logic.processCommand(user, "10000000");
        logic.processCommand(user, "6");

        logic.processCommand(user, "/repeat");
        assertEquals("Вычислите степень: 10^2", bot.getMessages().get(5));
        assertEquals(State.REPEAT, user.getState());

        logic.processCommand(user, "100");
        assertEquals("Правильный ответ!", bot.getMessages().get(6));
        assertEquals("Тест завершен", bot.getMessages().get(7));
        assertEquals(0, user.getWrongAnswerQuestions().size());
        assertEquals(State.INIT, user.getState());
    }
}