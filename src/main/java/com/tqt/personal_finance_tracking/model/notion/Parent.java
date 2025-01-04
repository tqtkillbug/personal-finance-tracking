package com.tqt.personal_finance_tracking.model.notion;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Parent {

    private String type;
    @JsonProperty("database_id")
    private String databaseId;

}
