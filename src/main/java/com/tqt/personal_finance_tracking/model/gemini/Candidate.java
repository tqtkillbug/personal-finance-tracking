package com.tqt.personal_finance_tracking.model.gemini;

import lombok.Data;

@Data
public class Candidate {
    private Content content;
    private String finishReason;
    private double avgLogprobs;

}
