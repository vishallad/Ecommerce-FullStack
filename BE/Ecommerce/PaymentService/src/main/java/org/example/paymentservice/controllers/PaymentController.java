package org.example.paymentservice.controllers;

import org.example.paymentservice.dto.GeneratePaymentLinkDtoReq;
import org.example.paymentservice.dto.GeneratePaymentLinkRes;
import org.example.paymentservice.servicies.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/payment")
public class PaymentController {

    PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping()
    public ResponseEntity<GeneratePaymentLinkRes> initiatePayment(@RequestBody GeneratePaymentLinkDtoReq req) throws Exception {
        String link = paymentService.initiatePayment(req.getOrderId(),req.getProductId());
        GeneratePaymentLinkRes rs = new GeneratePaymentLinkRes();
        rs.setLink(link);
        return ResponseEntity.ok(rs);
    }
}
