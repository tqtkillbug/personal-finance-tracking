package com.tqt.personal_finance_tracking.model.xai;

import lombok.Data;

import java.util.List;

@Data
public class ChatCompletion {
    private String id;
    private String object;
    private long created;
    private String model;
    private List<Choice> choices;
    private String systemFingerprint;
}
