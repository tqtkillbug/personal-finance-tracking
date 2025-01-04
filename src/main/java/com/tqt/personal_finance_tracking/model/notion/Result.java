package com.tqt.personal_finance_tracking.model.notion;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Result {
    private String object;
    private String id;
    @JsonProperty("created_time")
    private String createdTime;
    @JsonProperty("last_edited_time")
    private String lastEditedTime;
    private User createdBy;
    private User lastEditedBy;
    private Parent parent;
    private boolean archived;
    @JsonProperty("in_trash")
    private boolean inTrash;
    private Properties properties;
    private String url;
    @JsonProperty("public_url")
    private String publicUrl;
}
