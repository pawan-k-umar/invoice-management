package com.invoice.management.service;

import com.lowagie.text.DocumentException;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

@Service
public class PdfService {

    private final TemplateEngine templateEngine;

    public PdfService(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public byte[] generatePdfFromForm(Map<String, Object> formData, String template) throws DocumentException {
        var price = Double.parseDouble((String) formData.get("itemPrice"));
        var quantity = Integer.parseInt((String) formData.get("itemQuantity"));
        var taxRate = Double.parseDouble((String) formData.get("taxRate"));
        if ((price <= 0) || (quantity <= 0) || (taxRate <= 0)) {
            setTotalAmount(formData, price, quantity, taxRate);
        }
        var context = new Context();
        context.setVariables(formData);

        var html = templateEngine.process(template, context);

        try (var outputStream = new ByteArrayOutputStream()) {
            var renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream);
            return outputStream.toByteArray();
        } catch (IOException exception) {
            throw new DocumentException(exception);
        }
    }

    private static void setTotalAmount(Map<String, Object> formData, double price, int quantity, double taxRate) {

        // Calculate subtotal and total
        var subtotal = price * quantity;
        // Add calculated values to formData map
        formData.put("itemSubtotal", String.format("%.2f", subtotal));
        var total = subtotal * (1 + (taxRate / 100));
        formData.put("totalAmount", String.format("%.2f", total));
    }
}
