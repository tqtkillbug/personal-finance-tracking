package com.tqt.personal_finance_tracking.model.gemini;

import lombok.Data;

import java.util.List;

@Data
public class Content {
    private List<Part> parts;
    private String role;

}
