package ru.urfu;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Класс тестирования логики бота
 */
public class LogicTest extends TestCase {

    Logic logic = new Logic();

    /**
     * Тест для функции {@link Logic#formResponse(String)}
     */
    public void testFormResponse() {
        String incomingText = "Привет!";
        String botAnswer = logic.formResponse(incomingText);
        Assert.assertEquals("Ваше сообщение: Привет!", botAnswer);
    }
}