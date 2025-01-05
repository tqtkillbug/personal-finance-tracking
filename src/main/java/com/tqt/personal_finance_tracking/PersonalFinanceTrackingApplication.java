package com.tqt.personal_finance_tracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication(scanBasePackages = "com.tqt.personal_finance_tracking")
@Configuration
@EnableScheduling
public class PersonalFinanceTrackingApplication extends SpringBootServletInitializer {

    public static void main(final String[] args) {
        SpringApplication.run(PersonalFinanceTrackingApplication.class, args);
    }

}
