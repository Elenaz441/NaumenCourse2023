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
import ru.urfu.Logic;

import java.util.List;
import java.util.Random;

/**
 * Класс для vk-бота
 */
public class VkBot implements Bot {

    private final VkApiClient vk;
    private final GroupActor actor;
    private int ts;
    private final Random random;
    private final Logic logic;

    /**
     * Конструктор для vk-бота
     */
    public VkBot(String botName, String botToken, Logic logic) {
        TransportClient transportClient = new HttpTransportClient();
        vk = new VkApiClient(transportClient);
        try {
            actor = new GroupActor(Integer.valueOf(botName), botToken);
            ts = vk.messages().getLongPollServer(actor).execute().getTs();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        random = new Random();
        this.logic = logic;
    }

    @Override
    public void startBot() {
        while (true) {
            List<Message> messages = getMessages();
            if (messages != null && !messages.isEmpty()) {
                messages.forEach(message -> {
                    String response = logic.formResponse(message.getText());
                    sendText(message.getFromId().longValue(), response);
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

    @Override
    public void sendText(Long chatId, String message) {
        try {
            vk.messages().send(actor).
                    message(message).
                    userId(chatId.intValue()).
                    randomId(random.nextInt(10000)).
                    execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
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
}
