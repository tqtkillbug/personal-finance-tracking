package com.tqt.personal_finance_tracking.contants;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class Contants {

    public static final String PROMPT_EXTRACT_TEXT_VI = "Phân tích đoạn văn bản dưới đây để trích xuất thông tin chi tiêu và xuất ra chuỗi JSON với các trường:'title' : là nội dung chính của chi tiêu, 'fundType' : một trong ['Income', 'Expenses'] để nhận diện loại của nguồn tiền vào hoặc ra, 'type': Một trong ['Housing', 'Food & Dining', 'Transportation', 'Medicine', 'Family Support', 'Investments', 'Books', 'Learning', 'Entertainment', 'Charity', 'Hair Cut', 'Shopping', 'Unknow', 'Salary', 'Repayment']. 'amount': Số tiền đã chi tiêu (định dạng số nguyên VNĐ). 'dateType': Một trong ['Today', 'Yesterday', 'None'] (xác định dựa trên từ khóa liên quan đến ngày). 'note': Thông tin thêm ngoài nội dung chi tiêu chính, ví dụ như gửi xe... . Đoạn văn bản cần phân tích: '%s' Yêu cầu đầu ra (JSON): Ví dụ: Đoạn văn: 'Cơm trưa 35k hôm nay gửi xe 5k' Kết quả: {'title' : 'Cơm trưa', 'fundType' : 'Expenses', 'type': 'Food & Dining', 'amount': '40000', 'dateType': 'Today', 'note': 'gửi xe 5k'} Đoạn văn: 'Mua sách 120k' Kết quả: {'title' : 'Mua sách', 'type': 'Books', 'fundType' : 'Expenses', 'amount': '120000', 'dateType': 'None', 'note': 'mua sách'} Đoạn văn: 'nhận lương 15tr' Kết quả: {'title' : 'Nhận lương', 'type': 'Salary', 'fundType' : 'Income', 'amount': '15000000', 'dateType': 'None', 'note': ''}. Hãy nhớ rằng chỉ trả về chuỗi json và không có bất kì kí tự nào khác.";
    public static final String PROMPT_EXTRACT_TEXT = "Analyze the following text to extract spending information and output a JSON string with the fields: 'title': the main content of the expense or income. 'fundType': one of ['Income', 'Expenses'] to identify the type of incoming or outgoing money. 'type': one of ['Housing', 'Food & Dining', 'Transportation', 'Medicine', 'Family Support', 'Investments', 'Books', 'Learning', 'Entertainment', 'Charity', 'Hair Cut', 'Shopping', 'Unknow', 'Salary', 'Repayment', Invest, Laundry Fee, Charity, Service Fee, Remittance]. 'amount': the amount spent (formatted as an integer in VND). 'dateType': one of ['Today', 'Yesterday', 'None'] (determined by keywords related to the date). 'note': additional information outside the main expense or income content, e.g., parking fees. Text to analyze: '%s' Output format (JSON): For example: Text: 'Cơm trưa 35k hôm nay gửi xe 5k' Result: {'title': 'Cơm trưa', 'fundType': 'Expenses', 'type': 'Food & Dining', 'amount': '40000', 'dateType': 'Today', 'note': 'gửi xe 5k'} Text: 'Mua sách 120k' Result: {'title': 'Mua sách', 'fundType': 'Expenses', 'type': 'Books', 'amount': '120000', 'dateType': 'None', 'note': 'mua sách'} Text: 'nhận lương 15tr' Result: {'title': 'Nhận lương', 'fundType': 'Income', 'type': 'Salary', 'amount': '15000000', 'dateType': 'None', 'note': ''} Remember to return only the JSON string without any extra characters.";

    public static final String DATABASE_INCOME_ID = "15c0d9d80cda81f4bbf6ee9c948c875c";
    public static final String DATABASE_EXPESENS_ID = "15c0d9d80cda8109a136cf5f627eef51";

    public static final String CHAT_ID_BOSS = "904114553";

    public static final Map<String, String> MAP_RELATION_MONTH = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("01", "15c0d9d80cda81adaaa7cb34b5d9a94d"),
            new AbstractMap.SimpleEntry<>("02", "15c0d9d80cda816f84a7fbe7be67c8e3"),
            new AbstractMap.SimpleEntry<>("03", "15c0d9d80cda8169bf20c64109881c97"),
            new AbstractMap.SimpleEntry<>("04", "15c0d9d80cda8155924dfc28c7383c6b"),
            new AbstractMap.SimpleEntry<>("05", "15c0d9d80cda819e83c0fd82f0ee7d29"),
            new AbstractMap.SimpleEntry<>("06", "15c0d9d80cda810b8c1be7e2d3ab7897"),
            new AbstractMap.SimpleEntry<>("07", "15c0d9d80cda81a69dc0f34ac9d08e5a"),
            new AbstractMap.SimpleEntry<>("08", "15c0d9d80cda81e5be11eaf1b6d5e603"),
            new AbstractMap.SimpleEntry<>("09", "15c0d9d80cda81b18535dc9e959973f7"),
            new AbstractMap.SimpleEntry<>("10", "15c0d9d80cda812bb846c1572eba6aa8"),
            new AbstractMap.SimpleEntry<>("11", "15c0d9d80cda8178b05cf79aec565dcc"),
            new AbstractMap.SimpleEntry<>("12", "15c0d9d80cda8106b587d8be064aaf19")
    );
}

