package com.tqt.personal_finance_tracking.model.gemini;

import lombok.Data;

@Data
public class UsageMetadata {
    private int promptTokenCount;
    private int candidatesTokenCount;
    private int totalTokenCount;

}
