package com.invoice.management.setup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.invoice.management")
public class InvoiceManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(InvoiceManagementApplication.class, args);
    }

}
