package com.tqt.personal_finance_tracking.service;

import com.google.gson.Gson;
import com.tqt.personal_finance_tracking.contants.Contants;
import com.tqt.personal_finance_tracking.dto.Expense;
import com.tqt.personal_finance_tracking.model.Amount;
import com.tqt.personal_finance_tracking.model.Date;
import com.tqt.personal_finance_tracking.model.Notes;
import com.tqt.personal_finance_tracking.model.NotionPageResponse;
import com.tqt.personal_finance_tracking.model.gemini.Response;
import com.tqt.personal_finance_tracking.model.notion.*;
import com.tqt.personal_finance_tracking.notation.NotionService;
import com.tqt.personal_finance_tracking.model.*;
import com.tqt.personal_finance_tracking.util.BotUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Slf4j
@Service
public class MessageService {

    @Autowired
    private XAIService xaiService;

    @Autowired
    private BotTeleService botTeleService;

    @Autowired
    private NotionService notionService;

    @Autowired
    private GeminiAPIClient geminiApiService;


    public void handleMessage(Update update) {
        if (update.hasCallbackQuery()){
            handleCallBack(update);
            return;
        }
        if (update.getMessage().getText().contains("/")){
            handleCommand(update);
            return;
        }
        Message message = update.getMessage();
        String text = message.getText();
        String chatId = String.valueOf(message.getChatId());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setParseMode(ParseMode.MARKDOWNV2);
        if (!text.isEmpty()){
            String promptBuilt = String.format(Contants.PROMPT_EXTRACT_TEXT, text);
            try {
                Response responseGemi = geminiApiService.callGenerativeAPI(promptBuilt);
                Expense expense = parseJson(responseGemi.getCandidates().get(0).getContent().getParts().get(0).getText());
                if (expense != null && expense.getAmount() != null && !expense.getAmount().equals("0")){
                    sendMessage.setText(BotUtils.buildExpenseMessage(expense));
                    NotionPageResponse rp = pushToNotion(expense);
                    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                    inlineKeyboardMarkup.setKeyboard(BotUtils.buildCallBackButton(rp.getId()));
                    sendMessage.setReplyMarkup(inlineKeyboardMarkup);
                } else {
                    sendMessage.setText("*Không xác định chi tiêu, hãy nhập lại*");
                }
            }catch (Exception e){
                sendMessage.setText("*Không xác định chi tiêu, hãy nhập lại*");
            }
            botTeleService.sendToClient(sendMessage);
        }
    }

    private void handleCommand(Update update) {
        String command = update.getMessage().getText();
        SendMessage sendMessage;
        switch (command){
            case "/report" :
                sendMessage = BotUtils.buildListButtonReport();
                break;
            default:
                sendMessage = SendMessage.builder().text("Invalid").build();
        }
        sendMessage.setText("List Button Report");
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        botTeleService.sendToClient(sendMessage);
    }

    private void handleCallBack(Update update) {
        if (update.getCallbackQuery().getData() != null){
            String data = update.getCallbackQuery().getData();
            SendMessage replyMessage = new SendMessage();
            replyMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
            if (data.contains("delete")){
                String idPage = data.split(":")[1];
                NotionPageResponse response = notionService.deletePage(idPage);
                Integer replyChatId = update.getCallbackQuery().getMessage().getMessageId();
                if (response != null){
                    replyMessage.setText("Removed");
                    replyMessage.setReplyToMessageId(replyChatId);
                }
            } else if (data.contains("today")){
                handleReport(update);
            } else {
                replyMessage.setText("Not action!");
            }
            botTeleService.sendToClient(replyMessage);
        }
    }


    public static Expense parseJson(String jsonString) {
        String cleanedJson = "";
        try {
             cleanedJson = jsonString
                    .replaceAll("```json", "")
                    .replaceAll("```", "")
                    .replaceAll("\\\\n", "")
                    .trim();

            Gson gson = new Gson();
            return gson.fromJson(cleanedJson, Expense.class);
        } catch (Exception e) {
            log.error("Error parsing json Expense, response: {}", cleanedJson);
            return null;
        }
    }

    private NotionPageResponse pushToNotion(Expense expense){
        if (expense == null) return null;
        NotionProperties properties = new NotionProperties();
        TextWrapper textWrapper = new TextWrapper(
                new Content(expense.getTitle())
        );
        TextWrapper textWrapperNote = new TextWrapper(
                new Content(expense.getNote())
        );
        IdWrapper idWrapper = new IdWrapper("15c0d9d80cda81adaaa7cb34b5d9a94d");
        properties.setSource(new Source(List.of(textWrapper)));
        properties.setAmount(new Amount(Double.parseDouble(expense.getAmount())));
        properties.setMonth(new Month(List.of(idWrapper)));
        properties.setDate(new Date(new DateContent(getFormattedDate(expense.getDateType()))));
        properties.setNotes(new Notes(List.of(textWrapperNote)));
        properties.setType(new Type(new NameWrapper(expense.getType())));

        String databaseId = expense.getFundType().equals("Income") ? Contants.DATABASE_INCOME_ID : Contants.DATABASE_EXPESENS_ID;
        return notionService.insertPage(properties, databaseId);
    }

    public static String getFormattedDate(String input) {
        SimpleDateFormat isoDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();

        String result;
        switch (input) {
            case "Today":
                result = isoDateFormat.format(calendar.getTime());
                break;
            case "Yesterday":
                calendar.add(Calendar.DATE, -1);
                result = isoDateFormat.format(calendar.getTime());
                break;
            case "None":
            default:
                result = isoDateFormat.format(calendar.getTime());
                break;
        }
        return result;

    }


    private void handleReport(Update update) {
        String data = update.getCallbackQuery().getData();
        SendMessage sendMessage = new SendMessage();
        if (data.equals("today")){

            DateFilter dateFilter = new DateFilter();
            dateFilter.setEquals(getFormattedDate("Today"));
            dateFilter.setEquals("2025-01-05");
            Filter filter = new Filter("Date", dateFilter);
            NotionQuery notionQuery = new NotionQuery(filter);
            NotionQueryResponse response = notionService.queryDatabase(notionQuery);
            List<Properties> properties = new ArrayList<>();
            if (response != null){
                for (Result result : response.getResults()) {
                   properties.add(result.getProperties());
                }
            }
            sendMessage = BotUtils.buildReportMessage(properties);
        }

        String chatId = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
        sendMessage.setChatId(chatId);
        sendMessage.setParseMode(ParseMode.MARKDOWNV2);
        botTeleService.sendToClient(sendMessage);

    }



}
