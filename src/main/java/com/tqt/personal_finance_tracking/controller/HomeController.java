package com.tqt.personal_finance_tracking.controller;

import com.tqt.personal_finance_tracking.contants.Contants;
import com.tqt.personal_finance_tracking.model.*;
import com.tqt.personal_finance_tracking.notation.NotionService;
import com.tqt.personal_finance_tracking.model.Date;
import com.tqt.personal_finance_tracking.model.xai.ChatCompletion;
import com.tqt.personal_finance_tracking.service.XAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.*;


@Controller
public class HomeController {

    @Autowired
    private NotionService notionService;

    @Autowired
    private XAIService xaiService;

    @GetMapping("/")
    public String index() {
        return "home/index";
    }


    @GetMapping("/notion/page")
    public Map<String, Object> getNotionPageData() {
        return notionService.getPageData();
    }

    @GetMapping("/notion/users")
    public Map<String, Object> getNotionUsers() {
        return notionService.getUsers();
    }

    @GetMapping("/notion/database")
    public Map<String, Object> getDatabase() {
        return notionService.retrieveDatabase();
    }


    @GetMapping("/notion/insert-page")
    public Map<String, Object> insertPage(Principal principal) {
        NotionProperties properties = new NotionProperties();
        TextWrapper textWrapper = new TextWrapper(
                new Content("Source content")
        );
        TextWrapper textWrapperNote = new TextWrapper(
                new Content("Notes content")
        );
        IdWrapper idWrapper = new IdWrapper("15c0d9d80cda81adaaa7cb34b5d9a94d");
        properties.setSource(new Source(List.of(textWrapper)));
        properties.setAmount(new Amount(998775));
        properties.setMonth(new Month(List.of(idWrapper)));
        properties.setDate(new Date(new DateContent("2024-12-18")));
        properties.setNotes(new Notes(List.of(textWrapperNote)));
        properties.setType(new Type(new NameWrapper("Salary")));


        ChatCompletion responseAI = xaiService.callXAI("Hãy phân tích đoạn văn bản dưới đây và trích xuất thông tin chi tiêu. Đầu ra phải là một chuỗi JSON với các trường sau: 'type': Một trong các loại sau: ['Housing', 'Food & Dining', 'Transportation', 'Medicine', 'Family Support', 'Investments', 'Books', 'Learning', 'Entertainment', 'Charity', 'Hair Cut']. 'amount': Số tiền đã chi tiêu (chuyển đổi sang định dạng số nguyên nếu cần). 'dateType': Một trong các giá trị ['Today', 'Yesterday', 'None'] để chỉ ngày liên quan đến chi tiêu (nếu có). 'note': Các ghi chú mô tả liên quan đến khoản chi tiêu. Đoạn văn bản: 'Đi taxi ra sân bay 160k tip thêm 20k' Ví dụ: Đoạn văn: 'Cơm trưa 35k hôm nay' Kết quả: {'type': 'Food & Dining', 'amount': '35000', 'dateType': 'Today', 'note': 'cơm trưa'} Đoạn văn: 'Mua sách 120k' Kết quả: {'type': 'Books', 'amount': '120000', 'dateType': 'None', 'note': 'mua sách'} Hãy đảm bảo: Loại chi tiêu ('type') được xác định chính xác dựa trên nội dung. Số tiền ('amount') được chuyển đổi về dạng số nguyên và đơn vị VNĐ. Ngày ('dateType') được suy luận đúng từ từ khóa như 'hôm nay' (Today), 'ngày mai' (Tomorrow), hoặc không có thông tin ngày (None). Ghi chú ('note') ngắn gọn, mô tả nội dung chính. Đầu ra chỉ bao gồm chuỗi JSON, không kèm bất kỳ nội dung nào khác.");

        notionService.insertPage(properties, Contants.DATABASE_INCOME_ID);
        return new HashMap<>();
    }
    

}
