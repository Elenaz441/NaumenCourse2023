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
        Bot tgBot = new TelegramBot(System.getenv("BOT_TG_NAME"), System.getenv("BOT_TG_TOKEN"));
        tgBot.startBot();
        Bot vkBot = new VkBot(System.getenv("BOT_VK_ID"), System.getenv("BOT_VK_TOKEN"));
        vkBot.startBot();
    }
}
