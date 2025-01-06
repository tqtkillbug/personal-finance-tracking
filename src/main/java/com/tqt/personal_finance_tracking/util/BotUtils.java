package com.tqt.personal_finance_tracking.util;


import com.tqt.personal_finance_tracking.dto.Expense;
import com.tqt.personal_finance_tracking.model.notion.Properties;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
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
        row1.add(b1);
//        InlineKeyboardButton b2 = new InlineKeyboardButton();
//        b2.setText("Change");
//        b2.setCallbackData("change:" + pageId);
//        row1.add(b2);
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

    public static SendMessage buildReportMessage(List<Properties> propertiesList) {
        StringBuilder message = new StringBuilder();
        if (propertiesList == null || propertiesList.isEmpty()) return null;

        String date = escapeMarkdownV(propertiesList.get(0).getDate().getDate().getStart());
        message.append("*Report Summary ").append(date).append(" *\n");
        double totalAmount = 0;

        for (Properties properties : propertiesList) {
            String source = escapeMarkdownV(properties.getSource().getTitle().get(0).getPlainText());
            double dAmount = properties.getAmount().getNumber();
            totalAmount = totalAmount + dAmount;
            String type = escapeMarkdownV(properties.getType().getSelect().getName());
            String notes = properties.getNotes() != null && !properties.getNotes().getRichText().isEmpty()
                    ? escapeMarkdownV(properties.getNotes().getRichText().get(0).getPlainText()) : "N/A";

            message.append("\n*Source:* ").append(source)
                    .append("\n*Amount:* ").append(escapeMarkdownV(formatToVND(dAmount)))
                    .append("\n*Type:* ").append(type)
                    .append("\n*Notes:* ").append(notes)
                    .append("\n\\-\\-\\-");
        }
        message.append("\n\\-\\-\\-\\-\\-\\-\\-\\-\\-\\-\\-\\-\\-\\-\\-\\-\\-\\-\\-\\-\\-\\-\\-\\-\\-\\-\\-\\-\\-\\-\\-\\-\\-\\-\\-\\-\\-\\-\\-\\-\\-\\-\\-");
        message.append("*\nTotal : *").append(escapeMarkdownV(formatToVND((totalAmount))));


        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message.toString());
        sendMessage.enableMarkdownV2(true);

        return sendMessage;
    }

    public static String escapeMarkdownV(String text) {
        if (text == null) {
            return "";
        }
        return text.replaceAll("([_\\*\\[\\]\\(\\)~`>#+\\-=|{}\\.!])", "\\\\$1");
    }

    public static String formatToVND(double amount) {
        try {
            NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            return vndFormat.format(amount);
        } catch (NumberFormatException e) {
            return String.valueOf(amount);
        }
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

    public static SendMessage buildListButtonReport() {
        SendMessage sendMessage = new SendMessage();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        List<InlineKeyboardButton> row1 = new ArrayList<>();
        InlineKeyboardButton b1 = new InlineKeyboardButton();
        b1.setText("Today");
        b1.setCallbackData("today");
        row1.add(b1);

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        InlineKeyboardButton b12 = new InlineKeyboardButton();
        b12.setText("Yesterday");
        b12.setCallbackData("yesterday");
        row2.add(b12);
        InlineKeyboardButton b13 = new InlineKeyboardButton();
        b13.setText("This Week");
        b13.setCallbackData("thisweek");
        row2.add(b13);
        InlineKeyboardButton b14 = new InlineKeyboardButton();
        b14.setText("This Month");
        b14.setCallbackData("thismonth");
        row2.add(b14);
        keyboard.add(row1);
        keyboard.add(row2);
        inlineKeyboardMarkup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }
}
