package com.invoice.management.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @GetMapping("/health-check")
    public String getInvoice() {
        return "Invoice Management App is Up and Running";  // Returns the front-end UI
    }
}
