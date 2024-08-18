INSERT INTO event (name, tickets_per_buyer)
VALUES ('Spring Boot Workshop', 4),
       ('Java Conference', 3),
       ('Microservices Summit', 10),
       ('Cloud Computing Expo', 10),
       ('AI and Machine Learning Seminar', 2);

INSERT INTO ticket (price, type, qr_code, event_id)
VALUES (100.00, 'VIP', '', 1),
       (100.00, 'VIP', '', 1),
       (50.00, 'Standard', '', 1),
       (50.00, 'Standard', '', 1),
       (50.00, 'Standard', '', 1),
       (50.00, 'Standard', '', 1),
       (50.00, 'Standard', '', 1),
       (50.00, 'Standard', '', 1),
       (50.00, 'Standard', '', 1),
       (50.00, 'Standard', '', 1),
       (50.00, 'Standard', '', 1),
       (50.00, 'Standard', '', 1),
       (50.00, 'Standard', '', 1),
       (50.00, 'Standard', '', 1),
       (150.00, 'VIP', '', 2),
       (150.00, 'VIP', '', 2),
       (150.00, 'VIP', '', 2),
       (150.00, 'VIP', '', 2),
       (75.00, 'Standard', '', 2),
       (75.00, 'Standard', '', 2),
       (75.00, 'Standard', '', 2),
       (75.00, 'Standard', '', 2),
       (75.00, 'Standard', '', 2),
       (75.00, 'Standard', '', 2),
       (75.00, 'Standard', '', 2),
       (75.00, 'Standard', '', 2),
       (75.00, 'Standard', '', 2),
       (200.00, 'VIP', '', 3),
       (200.00, 'VIP', '', 3),
       (200.00, 'VIP', '', 3),
       (200.00, 'VIP', '', 3),
       (200.00, 'VIP', '', 3),
       (200.00, 'VIP', '', 3),
       (200.00, 'VIP', '', 3);

INSERT INTO organizer (company_name, contact_name)
VALUES ('Codeartify GmbH', 'John Doe'),
       ('Tech Corp', 'Jane Smith'),
       ('Innovate Inc.', 'Alice Johnson'),
       ('Dev Solutions', 'Bob Brown'),
       ('AI Pioneers', 'Charlie Black');

INSERT INTO bill (buyer_company_name, buyer_name, amount, iban, description, organizer_company_name, creation_date, paid)
VALUES ('Codeartify GmbH', 'John Doe', 1200.00, 'DE89370400440532013000', 'Payment for Spring Boot Workshop',
        'Codeartify GmbH', '2024-08-16', false),
       ('Tech Corp', 'Jane Smith', 1500.00, 'DE89370400440532013001', 'Payment for Java Conference', 'Tech Corp',
        '2024-08-17', false),
       ('Innovate Inc.', 'Alice Johnson', 1800.00, 'DE89370400440532013002', 'Payment for Microservices Summit',
        'Innovate Inc.', '2024-08-18', true);

INSERT INTO discount_code (code, discount_percentage, applicable_ticket_type)
VALUES ('DISCOUNT50', 50.00, 'VIP'),
       ('DISCOUNT10', 10.00, 'Standard'),
       ('SUMMER20', 20.00, 'Standard');

INSERT INTO notification (recipient, subject, message)
VALUES ('John Doe', 'Payment Successful', 'Your payment for Spring Boot Workshop was successful.'),
       ('Jane Smith', 'New Bill Issued', 'A new bill has been issued to your company.'),
       ('Alice Johnson', 'Payment Reminder', 'This is a reminder to pay for the Microservices Summit.');

INSERT INTO payment (amount, payment_method, description, successful)
VALUES (1200.00, 'Credit Card', 'Payment for Spring Boot Workshop', true),
       (1500.00, 'Bank Transfer', 'Payment for Java Conference', true),
       (1800.00, 'Credit Card', 'Payment for Microservices Summit', true);

INSERT INTO booker (username, email, company_name)
VALUES ('johndoe', 'johndoe@gmail.com', ''),
       ('janesmith', 'janedoe@host.com', ''),
       ('alicejohnson', 'alice.johnson@innovate.inc', 'Innovate Inc.'),
       ('bobbrown', 'bb@devsolutions.com', 'Dev Solutions'),
       ('charlieblack', 'charlie.black@aipioneers.com', 'AI Pioneers'),
       ('davidwhite', 'dwhite@tech.corp', 'Tech Corp'),
       ('evegreen', 'eve.green@supersoft.ware', 'SuperSoftware GmbH');
