package com.tqt.personal_finance_tracking.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Source {

    @JsonProperty("title")
    private List<TextWrapper> title;
}
