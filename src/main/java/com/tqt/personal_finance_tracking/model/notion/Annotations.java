package com.tqt.personal_finance_tracking.model.notion;

import lombok.Data;

@Data
public class Annotations {
    private boolean bold;
    private boolean italic;
    private boolean strikethrough;
    private boolean underline;
    private boolean code;
    private String color;
}
