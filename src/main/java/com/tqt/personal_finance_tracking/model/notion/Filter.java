package com.tqt.personal_finance_tracking.model.notion;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Filter {
//    private List<Map<String, DateFilter>> and;
    private List<DateFilter> and;
}
