package com.tqt.personal_finance_tracking.service;

import com.tqt.personal_finance_tracking.config.telegram.BotNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class BotTeleService {

    @Autowired
    private BotNotification botNotification;


    public void sendToClient(SendMessage sendMessage) {
        botNotification.sendMessge(sendMessage);
    }


}
