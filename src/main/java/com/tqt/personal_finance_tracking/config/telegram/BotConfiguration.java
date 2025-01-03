package com.tqt.personal_finance_tracking.config.telegram;

import com.tqt.personal_finance_tracking.config.AppConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class BotConfiguration {

    @Autowired
    private AppConfig appConfig;

    @Bean
    public BotNotification botNotificationInit() {
        try {
            BotNotification botNotification = new BotNotification(appConfig);
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(botNotification);
            return botNotification;
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return new BotNotification(appConfig);
    }

    @Bean
    public RestTemplate restTemplate() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));
    }

}