package com.tqt.personal_finance_tracking.model.xai;

import lombok.Data;

@Data
public  class Message {
    private String role;
    private String content;
    private Object refusal;
}