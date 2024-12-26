package com.tqt.personal_finance_tracking.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Date {

    @JsonProperty("date")
    private DateContent date;
}
