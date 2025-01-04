package com.tqt.personal_finance_tracking.model.notion;

public class DateFilter {
    private String on_or_after;
    private String on_or_before;

    public DateFilter(String on_or_after, String on_or_before) {
        this.on_or_after = on_or_after;
        this.on_or_before = on_or_before;
    }

    public String getOn_or_after() {
        return on_or_after;
    }

    public void setOn_or_after(String on_or_after) {
        this.on_or_after = on_or_after;
    }

    public String getOn_or_before() {
        return on_or_before;
    }

    public void setOn_or_before(String on_or_before) {
        this.on_or_before = on_or_before;
    }
}
