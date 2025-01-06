package com.tqt.personal_finance_tracking.schedule;

import com.tqt.personal_finance_tracking.contants.Contants;
import com.tqt.personal_finance_tracking.service.BotTeleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class SendMessageTasks {

    @Autowired
    private BotTeleService botTeleService;

    @Scheduled(cron = "0 0 13 * * ?")
    public void runAt13h() {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(Contants.CHAT_ID_BOSS);
        sendMessage.setParseMode(ParseMode.MARKDOWNV2);
        sendMessage.setText("*Bạn đã chi tiêu gì mới không, hãy nhập thêm chi tiêu*");
        botTeleService.sendToClient(sendMessage);
    }

    @Scheduled(cron = "0 47 23 * * ?")
    public void runAt2347() {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(Contants.CHAT_ID_BOSS);
        sendMessage.setParseMode(ParseMode.MARKDOWNV2);
        sendMessage.setText("*Bạn đã chi tiêu gì mới không, hãy nhập thêm chi tiêu*");
        botTeleService.sendToClient(sendMessage);
    }



    @Scheduled(cron = "0 0 22 * * ?")
    public void runAt22h() {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(Contants.CHAT_ID_BOSS);
        sendMessage.setParseMode(ParseMode.MARKDOWNV2);
        sendMessage.setText("*Bạn đã chi tiêu gì mới không, hãy nhập thêm chi tiêu*");
        botTeleService.sendToClient(sendMessage);

    }
}
