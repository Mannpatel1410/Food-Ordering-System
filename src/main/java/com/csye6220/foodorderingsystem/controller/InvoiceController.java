package com.csye6220.foodorderingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.csye6220.foodorderingsystem.DAO.UserDAO;
import com.csye6220.foodorderingsystem.model.Cart;
import com.csye6220.foodorderingsystem.model.User;
import com.csye6220.foodorderingsystem.service.InvoiceService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/invoice")
public class InvoiceController {
	
    @Autowired
    private InvoiceService invoiceService;
    
    @Autowired
    private UserDAO userDAO;

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadInvoice(HttpSession session) {
        Integer orderID = (Integer) session.getAttribute("completedOrderId");
        Cart cartInvoice = (Cart) session.getAttribute("cartInvoice");
        
        String userEmail = (String) session.getAttribute("userEmail");
        String address = "";
        if (userEmail != null) {
            User user = userDAO.findByEmail(userEmail);
            if (user != null && user.getAddress() != null) {
            	address = user.getAddress();
            }
        }
        
        if (orderID == null || cartInvoice == null) {
            return ResponseEntity.notFound().build();
        }
        
        try {
            byte[] invoice = invoiceService.generateInvoice(cartInvoice, orderID, address);
            return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "attachment; filename=order_" + orderID + "_invoice.pdf")
                .body(invoice);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
