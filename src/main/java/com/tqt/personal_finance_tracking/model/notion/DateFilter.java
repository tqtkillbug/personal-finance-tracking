package com.tqt.personal_finance_tracking.model.notion;

import lombok.Data;

@Data
public class DateFilter {
    private String on_or_after;
    private String on_or_before;
    private String equals;
    private String after;
    private String before;
}
