package com.tqt.personal_finance_tracking.model.notion;

import lombok.Data;

@Data
public class DateFilter {
    private String property = "Date";
    private DateCondition date;
}
