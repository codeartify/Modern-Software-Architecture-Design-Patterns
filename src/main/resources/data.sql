-- Insert sample data into the Event table
INSERT INTO event (name)
VALUES ('Spring Boot Workshop'),
       ('Java Conference'),
       ('Microservices Summit'),
       ('Cloud Computing Expo'),
       ('AI and Machine Learning Seminar'),
       ('DevOps Day'),
       ('Cybersecurity Conference'),
       ('Data Science Meetup'),
       ('Blockchain Summit'),
       ('IoT Conference');


-- Insert sample data into the Ticket table
INSERT INTO ticket (price, type, qr_code, event_id)
VALUES (100.00, 'VIP', 'http://example.com/qr/1', 1),
       (50.00, 'Standard', 'http://example.com/qr/2', 1),
       (150.00, 'VIP', 'http://example.com/qr/3', 2),
       (75.00, 'Standard', 'http://example.com/qr/4', 2),
       (200.00, 'VIP', 'http://example.com/qr/5', 3),
       (100.00, 'Standard', 'http://example.com/qr/6', 3),
       (180.00, 'VIP', 'http://example.com/qr/7', 4),
       (90.00, 'Standard', 'http://example.com/qr/8', 4),
       (250.00, 'VIP', 'http://example.com/qr/9', 5),
       (120.00, 'Standard', 'http://example.com/qr/10', 5),
       (220.00, 'VIP', 'http://example.com/qr/11', 6),
       (110.00, 'Standard', 'http://example.com/qr/12', 6),
       (300.00, 'VIP', 'http://example.com/qr/13', 7),
       (150.00, 'Standard', 'http://example.com/qr/14', 7),
       (180.00, 'VIP', 'http://example.com/qr/15', 8),
       (100.00, 'Standard', 'http://example.com/qr/16', 8),
       (275.00, 'VIP', 'http://example.com/qr/17', 9),
       (130.00, 'Standard', 'http://example.com/qr/18', 9),
       (190.00, 'VIP', 'http://example.com/qr/19', 10),
       (95.00, 'Standard', 'http://example.com/qr/20', 10);
