CREATE TABLE IF NOT EXISTS event
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS ticket
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    price    DECIMAL(15, 2) NOT NULL,
    type     VARCHAR(255)   NOT NULL,
    qr_code  VARCHAR(255),
    event_id BIGINT,
    CONSTRAINT fk_event
        FOREIGN KEY (event_id)
            REFERENCES event (id)
);
