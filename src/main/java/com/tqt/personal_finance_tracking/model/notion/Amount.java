package com.tqt.personal_finance_tracking.model.notion;

import lombok.Data;

@Data
public class Amount {
    private String id;
    private String type;
    private double number;
}
