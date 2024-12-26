package com.tqt.personal_finance_tracking.model;

import lombok.Data;

@Data
public class NotionPageResponse {
    private String object;
    private int status;
    private String id;
    private String icon;
//    private NotionParent parent;
//    private boolean archived;
//    private boolean in_trash;
//    private NotionProperties properties;
//    private String url;
//    private String public_url;
}