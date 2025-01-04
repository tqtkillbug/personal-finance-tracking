package com.tqt.personal_finance_tracking.model.notion;

import lombok.Data;

import java.util.List;

@Data
public class Title {
    private String id;
    private String type;
    private List<TitleItem> title;
}
