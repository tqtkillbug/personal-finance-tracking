package com.tqt.personal_finance_tracking.model.notion;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Notes {
    private String id;
    private String type;
    @JsonProperty("rich_text")
    private List<RichText> richText;
}
