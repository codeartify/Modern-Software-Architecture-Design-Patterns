INSERT INTO events (name)
VALUES ('Spring Boot Workshop'),
       ('Java Conference'),
       ('Microservices Summit'),
       ('Cloud Computing Expo'),
       ('AI and Machine Learning Seminar');

INSERT INTO tickets (price, type, qrCode, event_id)
VALUES (100.00, 'VIP', 'http://example.com/qr/1', 1),
       (50.00, 'Standard', 'http://example.com/qr/2', 1),
       (150.00, 'VIP', 'http://example.com/qr/3', 2),
       (75.00, 'Standard', 'http://example.com/qr/4',2),
       (200.00, 'VIP', 'http://example.com/qr/5',3);

INSERT INTO organizer (companyName, contactName)
VALUES ('Codeartify GmbH', 'John Doe'),
       ('Tech Corp', 'Jane Smith'),
       ('Innovate Inc.', 'Alice Johnson'),
       ('Dev Solutions', 'Bob Brown'),
       ('AI Pioneers', 'Charlie Black');

INSERT INTO bill (buyerCompanyName, buyerName, amount, iban, description, organizerCompanyName, creationDate, paid)
VALUES ('Codeartify GmbH', 'John Doe', 1200.00, 'DE89370400440532013000', 'Payment for Spring Boot Workshop',
        'Codeartify GmbH', '2024-08-16', false),
       ('Tech Corp', 'Jane Smith', 1500.00, 'DE89370400440532013001', 'Payment for Java Conference', 'Tech Corp',
        '2024-08-17', false),
       ('Innovate Inc.', 'Alice Johnson', 1800.00, 'DE89370400440532013002', 'Payment for Microservices Summit',
        'Innovate Inc.', '2024-08-18', true);

INSERT INTO discountcode (code, discountPercentage, applicableTicketType)
VALUES ('DISCOUNT50', 50.00, 'VIP'),
       ('DISCOUNT10', 10.00, 'Standard'),
       ('SUMMER20', 20.00, null);

INSERT INTO notification (recipient, subject, message)
VALUES ('John Doe', 'Payment Successful', 'Your payment for Spring Boot Workshop was successful.'),
       ('Jane Smith', 'New Bill Issued', 'A new bill has been issued to your company.'),
       ('Alice Johnson', 'Payment Reminder', 'This is a reminder to pay for the Microservices Summit.');

INSERT INTO payment (amount, paymentMethod, description, successful)
VALUES (1200.00, 'Credit Card', 'Payment for Spring Boot Workshop', true),
       (1500.00, 'Bank Transfer', 'Payment for Java Conference', true),
       (1800.00, 'Credit Card', 'Payment for Microservices Summit', true);
