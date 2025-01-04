package com.tqt.personal_finance_tracking.model.notion;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class NotionQueryResponse {
    private String object;
    private List<Result> results;
    @JsonProperty("next_cursor")
    private String nextCursor;
    @JsonProperty("has_more")
    private boolean hasMore;
    private String type;
    @JsonProperty("page_or_database")
    private Object pageOrDatabase;
    @JsonProperty("request_id")
    private String requestId;
}
