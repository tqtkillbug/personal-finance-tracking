package com.tqt.personal_finance_tracking.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "app")
@Getter
@Setter
public class AppConfig {
    private String apikey;
    private String token;
    private String username;
    private String xaikey;
    private String geminiKey;

}