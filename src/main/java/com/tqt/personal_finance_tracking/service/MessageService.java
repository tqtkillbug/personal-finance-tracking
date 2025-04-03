package com.tqt.personal_finance_tracking.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.tqt.personal_finance_tracking.config.AppConfig;
import com.tqt.personal_finance_tracking.contants.Contants;
import com.tqt.personal_finance_tracking.dto.Expense;
import com.tqt.personal_finance_tracking.model.Amount;
import com.tqt.personal_finance_tracking.model.Date;
import com.tqt.personal_finance_tracking.model.Notes;
import com.tqt.personal_finance_tracking.model.NotionPageResponse;
import com.tqt.personal_finance_tracking.model.gemini.Response;
import com.tqt.personal_finance_tracking.model.notion.*;
import com.tqt.personal_finance_tracking.model.notion.Properties;
import com.tqt.personal_finance_tracking.notation.NotionService;
import com.tqt.personal_finance_tracking.model.*;
import com.tqt.personal_finance_tracking.util.BotUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class MessageService {

    @Autowired
    private BotTeleService botTeleService;

    @Autowired
    private NotionService notionService;

    @Autowired
    private GeminiAPIClient geminiApiService;

    @Autowired
    private AssemblyAIService assemblyAIService;

    @Autowired
    private AppConfig appConfig;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;


    public void handleMessage(Update update) throws InterruptedException {
        if (update.hasMessage() && (update.getMessage().hasAudio() || update.getMessage().hasVoice())) {
         handleVoiceMessage(update);
        return;
        }
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
        handleTextInput(text, chatId);
    }

    private void handleTextInput(String text, String chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setParseMode(ParseMode.MARKDOWNV2);
        botTeleService.sendActionTyping(chatId);
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


    private void handleVoiceMessage(Update update) throws InterruptedException {
        if (update.hasMessage() && (update.getMessage().hasAudio() || update.getMessage().hasVoice())) {
            String fileId = "";
            if (update.getMessage().hasVoice()) {
                fileId = update.getMessage().getVoice().getFileId();
            } else if (update.getMessage().hasAudio()) {
                fileId = update.getMessage().getAudio().getFileId();
            }
            String filePath = getFilePath(fileId);
            if (filePath == null) {
                log.error("Không lấy được file_path cho fileId: {}", fileId);
                return;
            }
            String telegramFileUrl = "https://api.telegram.org/file/bot" + appConfig.getToken() + "/" + filePath;
            log.info("URL file audio: {}", telegramFileUrl);
            String transcriptText = assemblyAIService.transcribe(telegramFileUrl);

            String responseText;
            responseText = Objects.requireNonNullElse(transcriptText, "Có lỗi xảy ra khi xử lý audio.");

            Message message = update.getMessage();
            String chatId = String.valueOf(message.getChatId());
            handleTextInput(responseText, chatId);
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
            } else if (data.contains("today") || data.contains("yesterday") || data.contains("thisweek")){
                handleReport(update);
                return;
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
        properties.setSource(new Source(List.of(textWrapper)));
        properties.setAmount(new Amount(Double.parseDouble(expense.getAmount())));
        properties.setDate(new Date(new DateContent(getFormattedDate(expense.getDateType(), "yyyy-MM-dd"))));
        String month = getFormattedDate(expense.getDateType(), "MM");
        String relationId = Contants.MAP_RELATION_MONTH.get(month);
        IdWrapper idWrapper = new IdWrapper(relationId);
        properties.setMonth(new Month(List.of(idWrapper)));
        properties.setNotes(new Notes(List.of(textWrapperNote)));
        properties.setType(new Type(new NameWrapper(expense.getType())));
        String databaseId = expense.getFundType().equals("Income") ? Contants.DATABASE_INCOME_ID : Contants.DATABASE_EXPESENS_ID;
        return notionService.insertPage(properties, databaseId);
    }

    public static String getFormattedDate(String input, String format) {
        SimpleDateFormat isoDateFormat = new SimpleDateFormat(format);
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
        if (data.equals("today") || data.equals("yesterday")) {
            String formattedDate = getFormattedDate(data.equals("today") ? "Today" : "Yesterday", "yyyy-MM-dd");
            List<Properties> properties = fetchPropertiesByDate(formattedDate);
            sendMessage = BotUtils.buildReportMessage(properties);
        } else if (data.equals("thisweek")) {
            DateOfWeek dateOfWeek = BotUtils.getMondayAndSundayOfCurrentWeek();
            List<Properties> properties = fetchPropertiesByRangDate(dateOfWeek.getMonDay(), dateOfWeek.getSunDay());
            sendMessage = BotUtils.buildReportMessage(properties);
        }

        String chatId = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
        sendMessage.setChatId(chatId);
        sendMessage.setParseMode(ParseMode.MARKDOWNV2);
        botTeleService.sendToClient(sendMessage);

    }

    private List<Properties> fetchPropertiesByDate(String date) {
        DateCondition condition = new DateCondition();
        condition.setEquals(date);
        DateFilter dateFilter = new DateFilter();
        dateFilter.setDate(condition);
        List<DateFilter> listDateFilter = new ArrayList<>();
        listDateFilter.add(dateFilter);
        Filter filter = new Filter();
        filter.setAnd(listDateFilter);
        NotionQuery notionQuery = new NotionQuery(filter);
        return getListPropertiesFromNotion(notionQuery);
    }

    private List<Properties> getListPropertiesFromNotion(NotionQuery query) {
        NotionQueryResponse response = notionService.queryDatabase(query);

        List<Properties> properties = new ArrayList<>();
        if (response != null) {
            for (Result result : response.getResults()) {
                properties.add(result.getProperties());
            }
        }
        return properties;
    }

    private List<Properties> fetchPropertiesByRangDate(String from, String to) {
        DateFilter dateFilter = new DateFilter();
        DateCondition condition = new DateCondition();
        condition.setOn_or_after(from);
        dateFilter.setDate(condition);

        DateFilter dateFilter1 = new DateFilter();
        DateCondition condition1 = new DateCondition();
        condition1.setOn_or_before(to);
        dateFilter1.setDate(condition1);

        List<DateFilter> maps = new ArrayList<>();
        maps.add(dateFilter);
        maps.add(dateFilter1);
        Filter filter = new Filter();
        filter.setAnd(maps);

        NotionQuery notionQuery = new NotionQuery(filter);
        return getListPropertiesFromNotion(notionQuery);
    }

    /**
     * Gọi API getFile của Telegram để lấy file_path dựa trên fileId.
     *
     * @param fileId ID của file được gửi từ Telegram.
     * @return file_path nếu thành công, ngược lại trả về null.
     */
    public String getFilePath(String fileId) {
        String url = "https://api.telegram.org/bot" + appConfig.getToken() + "/getFile?file_id=" + fileId;
        try {
            String response = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(response);
            if (root.path("ok").asBoolean(false)) {
                JsonNode fileNode = root.path("result");
                if (fileNode.has("file_path")) {
                    return fileNode.get("file_path").asText();
                }
            }
        } catch (Exception e) {
            log.error("Lỗi khi lấy file_path từ Telegram cho fileId: {}", fileId, e);
        }
        return null;
    }



}
