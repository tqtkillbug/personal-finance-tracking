package com.tqt.personal_finance_tracking.model;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class NotionProperties {
    @JsonProperty("Source")
    private Source source;

    @JsonProperty("Amount")
    private Amount amount;

    @JsonProperty("Month")
    private Month month;

    @JsonProperty("Date")
    private Date date;

    @JsonProperty("Notes")
    private Notes notes;

    @JsonProperty("Type")
    private Type type;
}
