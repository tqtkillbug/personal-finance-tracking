package com.tqt.personal_finance_tracking.util;


import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class BotUtils {

    public static List<List<InlineKeyboardButton>> buildCallBackButton(String pageId) {
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        InlineKeyboardButton b1 = new InlineKeyboardButton();
        b1.setText("Delete");
        b1.setCallbackData("delete:" + pageId);
        InlineKeyboardButton b2 = new InlineKeyboardButton();
        b2.setText("Change");
        b2.setCallbackData("change:" + pageId);
        row1.add(b1);
        row1.add(b2);
        keyboard.add(row1);
        return keyboard;
    }
}
