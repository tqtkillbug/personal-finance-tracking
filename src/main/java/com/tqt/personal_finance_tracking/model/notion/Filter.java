package com.tqt.personal_finance_tracking.model.notion;

public class Filter {
    private String property;
    private DateFilter date;

    public Filter(String property, DateFilter date) {
        this.property = property;
        this.date = date;
    }

    // Getter và Setter
    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public DateFilter getDate() {
        return date;
    }

    public void setDate(DateFilter date) {
        this.date = date;
    }
}
