package ru.urfu;

import ru.urfu.Telegram.TelegramBot;
import ru.urfu.VK.VkBot;

/**
 * Класс для запуска бота
 */
public class RunnerBot {

    /**
     * Запуск бота на платформах Vk и telegram
     */
    public void run() {
        Bot tgBot = new Bot(System.getenv("BOT_TG_NAME"), System.getenv("BOT_TG_TOKEN"));
        runTelegramBot(tgBot);
        Bot vkBot = new Bot(System.getenv("BOT_VK_ID"), System.getenv("BOT_VK_TOKEN"));
        runVkBot(vkBot);
    }

    /**
     * Запуск телеграм-бота
     */
    private void runTelegramBot(Bot bot) {
        TelegramBot tgBot = new TelegramBot(bot);
        tgBot.startTelegramBot();
    }

    /**
     * Запуск Vk-бота
     */
    private void runVkBot(Bot bot) {
        VkBot vkBot = new VkBot(bot);
        vkBot.listenForMessages();
    }
}
