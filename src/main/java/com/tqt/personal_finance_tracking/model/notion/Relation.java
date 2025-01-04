package com.tqt.personal_finance_tracking.model.notion;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Relation {
    private String id;
    private String type;
    private List<RelationItem> relation;
    @JsonProperty("has_more")
    private boolean hasMore;
}
