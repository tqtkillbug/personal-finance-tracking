package com.tqt.personal_finance_tracking.model.notion;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotionQuery {
    private Filter filter;

    public NotionQuery(Filter filter) {
        this.filter = filter;
    }

    // Getter và Setter
    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }
}
