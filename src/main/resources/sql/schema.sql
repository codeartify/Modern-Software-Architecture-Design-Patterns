CREATE TABLE IF NOT EXISTS event
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    tickets_per_booker INT
);

CREATE TABLE IF NOT EXISTS ticket
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    price     DECIMAL(15, 2),
    type      VARCHAR(255),
    qr_code   VARCHAR(255),
    booker_id BIGINT,
    is_paid BOOLEAN,
    event_id  BIGINT,
    CONSTRAINT fk_event
        FOREIGN KEY (event_id)
            REFERENCES event(id)
);

CREATE TABLE IF NOT EXISTS bill
(
    id                     BIGINT AUTO_INCREMENT PRIMARY KEY,
    buyer_company_name     VARCHAR(255),
    buyer_name             VARCHAR(255),
    amount                 DECIMAL(15, 2),
    iban                   VARCHAR(255),
    description            VARCHAR(255),
    organizer_company_name VARCHAR(255),
    creation_date          DATE,
    paid                   BOOLEAN
);

CREATE TABLE IF NOT EXISTS discount_code
(
    id                    BIGINT AUTO_INCREMENT PRIMARY KEY,
    code                  VARCHAR(255),
    discount_percentage   DECIMAL(15, 2),
    applicable_ticket_type VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS notification
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    recipient  VARCHAR(255),
    subject    VARCHAR(255),
    message    VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS organizer
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    company_name  VARCHAR(255),
    contact_name  VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS payment
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    amount         DECIMAL(15, 2),
    payment_method VARCHAR(255),
    description    VARCHAR(255),
    successful     BOOLEAN
);

CREATE TABLE IF NOT EXISTS booker (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255),
    email VARCHAR(255),
    company_name VARCHAR(255)
);
