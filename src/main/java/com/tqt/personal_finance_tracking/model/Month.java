package com.tqt.personal_finance_tracking.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Month {

    @JsonProperty("relation")
    private List<IdWrapper> relation;
}
