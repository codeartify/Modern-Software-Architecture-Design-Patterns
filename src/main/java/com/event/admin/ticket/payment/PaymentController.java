package com.event.admin.ticket.payment;

import com.event.admin.ticket.model.Payment;
import com.event.admin.ticket.model.PaymentRequest;
import com.event.admin.ticket.payment.domain.PaymentMethod;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class PaymentController {

    private final JdbcTemplate jdbcTemplate;

    public PaymentController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Endpoint to process a payment
    @PostMapping("/tickets/payment")
    @Transactional
    public ResponseEntity<Payment> processPayment(@Valid @RequestBody PaymentRequest paymentRequest) {
        log.info("Processing payment...");
        log.info("Payment request: {}", paymentRequest);


        var paymentMethod = PaymentMethod.valueOf(paymentRequest.getPaymentMethod().toUpperCase());

        var paymentFactory = PaymentUseCaseFactory.createPaymentUseCase(jdbcTemplate, paymentMethod);

        Payment payment = paymentFactory.createPayment(paymentRequest);

        updatePayment(payment);

        return ResponseEntity.ok(payment);

    }

    private void updatePayment(Payment payment) {
        String query = "INSERT INTO payment (amount, payment_method, description, successful) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(query, payment.getAmount(), payment.getPaymentMethod(), payment.getDescription(), payment.isSuccessful());
    }

}
