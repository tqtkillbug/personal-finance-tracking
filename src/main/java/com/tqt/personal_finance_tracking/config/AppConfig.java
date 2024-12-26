package com.tqt.personal_finance_tracking.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "app")
@Getter
public class AppConfig {
    private String notionApiKey;
    private String botTeleToken;
    private String botTeleUsername;
    private String xaiKey;

}