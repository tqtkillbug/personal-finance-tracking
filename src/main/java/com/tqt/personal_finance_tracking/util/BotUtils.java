package com.tqt.personal_finance_tracking.util;


import com.tqt.personal_finance_tracking.dto.Expense;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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


    public static String buildExpenseMessage(Expense expense) {
        StringBuilder messageBuilder = new StringBuilder();

        messageBuilder.append("📋 *Confirmed ").append(expense.getFundType()).append("💰*\n\n");

        messageBuilder.append("💡 *Title:* ").append(escapeMarkdown(expense.getTitle())).append("\n\n");
        messageBuilder.append("🛍️ *Type:* ").append(escapeMarkdown(expense.getType())).append("\n\n");
        messageBuilder.append("💵 *Amount:* ").append(escapeMarkdown(formatCurrency(expense.getAmount()))).append("\n\n");
        messageBuilder.append("📅 *Date:* ").append(escapeMarkdown(expense.getDateType())).append("\n\n");
        messageBuilder.append("📝 *Notes:* ").append(escapeMarkdown(expense.getNote().isEmpty() ? "" : expense.getNote())).append("\n");


        return messageBuilder.toString();
    }

    private static String escapeMarkdown(String text) {
        return text.replace("_", "\\_")
                .replace("*", "\\*")
                .replace("[", "\\[")
                .replace("]", "\\]")
                .replace("(", "\\(")
                .replace(")", "\\)")
                .replace("~", "\\~")
                .replace("`", "\\`")
                .replace(">", "\\>")
                .replace("#", "\\#")
                .replace("+", "\\+")
                .replace("-", "\\-")
                .replace("=", "\\=")
                .replace("|", "\\|")
                .replace("{", "\\{")
                .replace("}", "\\}")
                .replace(".", "\\.")
                .replace("!", "\\!");
    }
    private static String formatCurrency(String amount) {
        try {
            double amountDouble = Double.parseDouble(amount.replace(",", ""));
            NumberFormat currencyFormat = NumberFormat.getInstance(Locale.forLanguageTag("vi-VN"));
            return currencyFormat.format(amountDouble) + " VND";
        } catch (NumberFormatException e) {
            return amount + " VND";
        }
    }

}
