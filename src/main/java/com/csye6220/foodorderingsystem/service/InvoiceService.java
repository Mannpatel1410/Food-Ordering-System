package com.csye6220.foodorderingsystem.service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.csye6220.foodorderingsystem.model.Cart;
import com.csye6220.foodorderingsystem.model.CartItem;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPCell;

@Service
public class InvoiceService {
	
    public byte[] generateInvoice(Cart cart, int orderId, String address) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            Paragraph header = new Paragraph("ORDER INVOICE", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
            header.setAlignment(Element.ALIGN_CENTER);
            header.setSpacingAfter(20f);
            document.add(header);

            PdfPTable metaTable = new PdfPTable(2);
            metaTable.setWidthPercentage(100);
            metaTable.setSpacingAfter(20f);
            
            addMetaCell(metaTable, "Order ID:", "#" + orderId);
            addMetaCell(metaTable, "Date:", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            addMetaCell(metaTable, "Delivery Address:", address);
            
            document.add(metaTable);

            PdfPTable itemsTable = new PdfPTable(4);
            itemsTable.setWidthPercentage(100);
            itemsTable.setWidths(new float[]{3, 1, 1, 1}); // Item,price,quantity and subtotal
            
            // Add Table Headers
            addHeader(itemsTable, "Item");
            addHeader(itemsTable, "Price");
            addHeader(itemsTable, "Qty");
            addHeader(itemsTable, "Subtotal");

            // Add Table Rows
            for (CartItem item : cart.getItems()) {
                addCell(itemsTable, item.getMenuItem().getName());
                addCell(itemsTable, "$" + String.format("%.2f", item.getMenuItem().getPrice()));
                addCell(itemsTable, String.valueOf(item.getQuantity()));
                addCell(itemsTable, "$" + String.format("%.2f", item.getSubtotal()));
            }

            document.add(itemsTable);

            Paragraph total = new Paragraph(
                "TOTAL: $" + String.format("%.2f", cart.getTotalPrice()),
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14));
            total.setAlignment(Element.ALIGN_RIGHT);
            total.setSpacingBefore(20f);
            document.add(total);

            Paragraph footer = new Paragraph("Thank you for your order!",FontFactory.getFont(FontFactory.HELVETICA, 12));
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setSpacingBefore(30f);
            document.add(footer);

        } finally {
            document.close();
        }

        return outputStream.toByteArray();
    }

    private void addMetaCell(PdfPTable table, String label, String value) {
        table.addCell(createCell(label, FontFactory.HELVETICA_BOLD, Element.ALIGN_LEFT));
        table.addCell(createCell(value, FontFactory.HELVETICA, Element.ALIGN_LEFT));
    }

    private void addHeader(PdfPTable table, String text) {
        PdfPCell cell = createCell(text, FontFactory.HELVETICA_BOLD, Element.ALIGN_CENTER);
        cell.setBackgroundColor(new BaseColor(220, 220, 220));
        table.addCell(cell);
    }

    private void addCell(PdfPTable table, String text) {
        table.addCell(createCell(text, FontFactory.HELVETICA, Element.ALIGN_LEFT));
    }

    private PdfPCell createCell(String text, String font, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text, FontFactory.getFont(font, 12)));
        cell.setHorizontalAlignment(alignment);
        cell.setPadding(5f);
        return cell;
    }
}
