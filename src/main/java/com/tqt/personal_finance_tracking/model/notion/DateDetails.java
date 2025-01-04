package com.tqt.personal_finance_tracking.model.notion;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DateDetails {
    private String start;
    private String end;
    @JsonProperty("time_zone")
    private String timeZone;
}
