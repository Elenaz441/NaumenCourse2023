package ru.urfu.VK;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;
import ru.urfu.Bot;

import java.util.List;
import java.util.Random;

/**
 * Класс для vk-бота
 */
public class VkBot {

    private Bot bot;
    private VkApiClient vk;
    private GroupActor actor;
    private int ts;
    private Random random;

    /**
     * Конструктор для vk-бота
     * @param bot - логика нашего бота
     */
    public VkBot(Bot bot) {
        this.bot = bot;
        TransportClient transportClient = new HttpTransportClient();
        vk = new VkApiClient(transportClient);
        try {
            actor = new GroupActor(Integer.valueOf(bot.getBotName()), bot.getBotToken());
            ts = vk.messages().getLongPollServer(actor).execute().getTs();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        random = new Random();
    }

    /**
     * Прослушиватель входящих сообщений
     */
    public void listenForMessages() {
        while (true) {
            List<Message> messages = getMessages();
            if (messages != null && !messages.isEmpty()) {
                messages.forEach(message -> {
                    String response = bot.formResponse(message.getText());
                    sendText(message, response);
                });
            }
            try {
                ts = vk.messages().getLongPollServer(actor).execute().getTs();
            } catch (ApiException | ClientException e) {e.printStackTrace();}

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Получить все входящие сообщения
     */
    private List<Message> getMessages() {
        MessagesGetLongPollHistoryQuery historyQuery =  vk.messages().getLongPollHistory(actor).ts(ts);
        try {
            return historyQuery.execute().getMessages().getItems();
        } catch (ApiException | ClientException e) {e.printStackTrace();}
        return null;
    }

    /**
     * Отправить ответ на сообщение
     */
    private void sendText(Message message, String response) {
        try {
            vk.messages().send(actor).
                    message(response).
                    userId(message.getFromId()).
                    randomId(random.nextInt(10000)).
                    execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
    }
}
