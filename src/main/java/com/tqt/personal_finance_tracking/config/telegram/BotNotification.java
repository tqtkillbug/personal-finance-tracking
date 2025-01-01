package com.tqt.personal_finance_tracking.config.telegram;


import com.tqt.personal_finance_tracking.config.AppConfig;
import com.tqt.personal_finance_tracking.service.MessageService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;



@Component
@Slf4j
public class BotNotification extends TelegramLongPollingBot {

    @Autowired
    @Lazy
    private MessageService messageService;

    private final AppConfig appConfig;

    @Autowired
    public BotNotification(AppConfig appConfig) {
        this.appConfig = appConfig;
    }


    public String getBotUsername() {
        return appConfig.getUsername();
    }

    @Override
    public String getBotToken() {
        return appConfig.getToken();
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
       messageService.handleMessage(update);
    }

    public void sendMessge(SendMessage message){
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Send message to telegram error!");
            e.printStackTrace();
        }
    }


}
