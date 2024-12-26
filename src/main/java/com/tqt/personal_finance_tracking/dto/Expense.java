package com.tqt.personal_finance_tracking.dto;

import lombok.Data;

@Data
public class Expense {
    private String title;
    private String fundType;
    private String type;
    private String amount;
    private String dateType;
    private String note;
}