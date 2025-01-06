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

    @Scheduled(cron = "0 05 13 * * ?")
    public void runAt1305() {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(Contants.CHAT_ID_BOSS);
        sendMessage.setParseMode(ParseMode.MARKDOWNV2);
        sendMessage.setText("*Bạn đã chi tiêu gì mới không, hãy nhập thêm chi tiêu*");
        botTeleService.sendToClient(sendMessage);
    }

    @Scheduled(cron = "0 30 19 * * ?")
    public void runAt1930() {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(Contants.CHAT_ID_BOSS);
        sendMessage.setParseMode(ParseMode.MARKDOWNV2);
        sendMessage.setText("*Bạn đã chi tiêu gì mới không, hãy nhập thêm chi tiêu*");
        botTeleService.sendToClient(sendMessage);
    }

    @Scheduled(cron = "0 15 22 * * ?")
    public void runAt2215() {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(Contants.CHAT_ID_BOSS);
        sendMessage.setParseMode(ParseMode.MARKDOWNV2);
        sendMessage.setText("*Bạn đã chi tiêu gì mới không, hãy nhập thêm chi tiêu*");
        botTeleService.sendToClient(sendMessage);
    }


}
