package com.invoice.management.controller;


import com.invoice.management.service.PdfService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class InvoiceController {

    @Autowired
    private PdfService pdfService;


    @RequestMapping("/invoice-form")
    public String showForm() {
        return "invoice-form";  // Returns the front-end UI
    }

    @PostMapping("/generate-invoice")
    public void generateInvoice(@RequestParam Map<String, Object> formData, HttpServletResponse response) throws Exception {

        var pdfBytes = pdfService.generatePdfFromForm(formData, "invoice");

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=invoice.pdf");

        try (var responseOutputStream = response.getOutputStream()) {
            responseOutputStream.write(pdfBytes);
        }
    }

}

