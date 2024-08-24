INSERT INTO Properties (address, city, state, postal_code, property_type, num_units, owner_name, purchase_date,
                        purchase_price)
VALUES ('123 Maple Street', 'Springfield', 'IL', '62704', 'Apartment', 10, 'John Doe', '2020-05-15', 500000.00),
       ('456 Oak Avenue', 'Springfield', 'IL', '62704', 'House', 1, 'Jane Smith', '2019-03-10', 250000.00),
       ('789 Pine Lane', 'Springfield', 'IL', '62704', 'Condo', 5, 'Michael Johnson', '2021-07-22', 350000.00);

INSERT INTO Tenants (first_name, last_name, phone_number, email, date_of_birth)
VALUES ('Alice', 'Brown', '555-1234', 'alice.brown@example.com', '1985-01-15'),
       ('Bob', 'Green', '555-5678', 'bob.green@example.com', '1990-06-20'),
       ('Carol', 'White', '555-9012', 'carol.white@example.com', '1978-11-30');

INSERT INTO Rentals (property_id, tenant_id, unit_number, lease_start_date, lease_end_date, monthly_rent,
                     deposit_amount, lease_agreement)
VALUES (1, 1, 'Unit 1A', '2022-01-01', '2022-12-31', 1200.00, 1200.00, 'lease_1a.pdf'),
       (2, 2, 'House', '2021-06-01', '2022-05-31', 1500.00, 1500.00, 'lease_house.pdf'),
       (3, 3, 'Unit 2B', '2022-03-01', '2023-02-28', 1000.00, 1000.00, 'lease_2b.pdf');

INSERT INTO Payments (rental_id, payment_date, amount, payment_method, payment_status)
VALUES (1, '2022-01-05', 1200.00, 'Credit Card', 'Paid'),
       (1, '2022-02-05', 1200.00, 'Bank Transfer', 'Paid'),
       (2, '2022-05-05', 1500.00, 'Credit Card', 'Paid'),
       (3, '2022-03-05', 1000.00, 'Cash', 'Paid'),
       (3, '2022-04-05', 1000.00, 'Bank Transfer', 'Paid');

INSERT INTO Maintenance_Requests (property_id, tenant_id, request_date, description, status, resolution_date)
VALUES (1, 1, '2022-02-15', 'Leaking faucet in the bathroom', 'Completed', '2022-02-20'),
       (2, 2, '2022-04-01', 'Air conditioning not working', 'In Progress', NULL),
       (3, 3, '2022-06-10', 'Broken window in the living room', 'Pending', NULL),
       (1, 1, '2022-03-10', 'Heating system not working properly', 'Completed', '2022-03-15'),
       (1, 1, '2022-05-22', 'Kitchen sink clogged', 'Completed', '2022-05-25'),
       (2, 2, '2022-07-01', 'Roof leakage in the bedroom', 'In Progress', NULL),
       (3, 3, '2022-08-05', 'Door lock jammed', 'Completed', '2022-08-07'),
       (1, 1, '2022-09-12', 'Broken light fixture in the hallway', 'Pending', NULL),
       (2, 2, '2022-10-18', 'Pest infestation in the basement', 'Completed', '2022-10-20'),
       (3, 3, '2022-11-23', 'Water heater malfunction', 'In Progress', NULL),
       (3, 3, '2023-01-14', 'Mold in the bathroom', 'Pending', NULL),
       (1, 1, '2023-02-03', 'Cracked window in the living room', 'In Progress', NULL),
       (2, 2, '2023-03-08', 'Garage door not opening', 'Pending', NULL);
