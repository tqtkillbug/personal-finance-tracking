package com.tqt.personal_finance_tracking.model.xai;

import lombok.Data;

@Data
public class Choice {
    private int index;
    private Message message;
    private String finishReason;
}
