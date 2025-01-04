package com.tqt.personal_finance_tracking.model.notion;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class Properties {
    @JsonProperty("Month")
    private Relation month;
    @JsonProperty("Amount")
    private Amount amount;
    @JsonProperty("Type")
    private Select type;
    @JsonProperty("Tags")
    private Select tags;
    @JsonProperty("Date")
    private Date date;
    @JsonProperty("Notes")
    private Notes notes;
    @JsonProperty("Source")
    private Title source;
}
