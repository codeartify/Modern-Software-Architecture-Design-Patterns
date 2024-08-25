package com.event.admin.ticket.payment.domain;

import java.util.UUID;

public record QrCode(String value) {

    public static QrCode create() {
        return new QrCode("http://example.com/qr?ticket=" + UUID.randomUUID());
    }
}
