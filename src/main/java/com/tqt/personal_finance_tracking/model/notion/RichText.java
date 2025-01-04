package com.tqt.personal_finance_tracking.model.notion;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RichText {
    private String type;
    private Text text;
    private Annotations annotations;
    @JsonProperty("plain_text")
    private String plainText;
    private String href;

}
