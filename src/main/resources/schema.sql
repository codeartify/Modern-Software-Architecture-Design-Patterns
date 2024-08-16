CREATE TABLE IF NOT EXISTS bill
(
    id                   BIGINT AUTO_INCREMENT PRIMARY KEY,
    buyerCompanyName     VARCHAR(255),
    buyerName            VARCHAR(255),
    amount               DECIMAL(15, 2),
    iban                 VARCHAR(255),
    description          VARCHAR(255),
    organizerCompanyName VARCHAR(255),
    creationDate         DATE,
    paid                 BOOLEAN
);

CREATE TABLE IF NOT EXISTS discountcode
(
    id                   BIGINT AUTO_INCREMENT PRIMARY KEY,
    code                 VARCHAR(255),
    discountPercentage   DECIMAL(15, 2),
    applicableTicketType VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS event
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS notification
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    recipient VARCHAR(255),
    subject   VARCHAR(255),
    message   VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS organizer
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    companyName VARCHAR(255),
    contactName VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS payment
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    amount        DECIMAL(15, 2),
    paymentMethod VARCHAR(255),
    description   VARCHAR(255),
    successful    BOOLEAN
);

CREATE TABLE IF NOT EXISTS ticket
(
    id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    price  DECIMAL(15, 2),
    type   VARCHAR(255),
    qrCode VARCHAR(255),
    event  VARCHAR(255)
);
