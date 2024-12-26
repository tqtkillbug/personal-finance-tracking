package com.tqt.personal_finance_tracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.tqt.personal_finance_tracking")
public class PersonalFinanceTrackingApplication {

    public static void main(final String[] args) {
        SpringApplication.run(PersonalFinanceTrackingApplication.class, args);
    }

}
