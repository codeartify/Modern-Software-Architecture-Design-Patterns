-- Creating the Properties table
CREATE TABLE IF NOT EXISTS Properties
(
    property_id    INT AUTO_INCREMENT PRIMARY KEY,
    address        VARCHAR(255) NOT NULL,
    city           VARCHAR(100) NOT NULL,
    state          VARCHAR(50)  NOT NULL,
    postal_code    VARCHAR(20),
    property_type  VARCHAR(50), -- e.g., Apartment, House, Condo
    num_units      INT,         -- Number of units in the property
    owner_name     VARCHAR(100),
    purchase_date  DATE,
    purchase_price DECIMAL(15, 2)
);

-- Creating the Tenants table
CREATE TABLE IF NOT EXISTS Tenants
(
    tenant_id     INT AUTO_INCREMENT PRIMARY KEY,
    first_name    VARCHAR(100) NOT NULL,
    last_name     VARCHAR(100) NOT NULL,
    phone_number  VARCHAR(20),
    email         VARCHAR(100),
    date_of_birth DATE
);

-- Creating the Rentals table
CREATE TABLE IF NOT EXISTS Rentals
(
    rental_id        INT AUTO_INCREMENT PRIMARY KEY,
    property_id      INT,
    tenant_id        INT,
    unit_number      VARCHAR(50),
    lease_start_date DATE,
    lease_end_date   DATE,
    monthly_rent     DECIMAL(10, 2),
    deposit_amount   DECIMAL(10, 2),
    lease_agreement  VARCHAR(255), -- Path to the lease agreement document
    FOREIGN KEY (property_id) REFERENCES Properties (property_id),
    FOREIGN KEY (tenant_id) REFERENCES Tenants (tenant_id)
);

-- Creating the Payments table
CREATE TABLE IF NOT EXISTS Payments
(
    payment_id     INT AUTO_INCREMENT PRIMARY KEY,
    rental_id      INT,
    payment_date   DATE,
    amount         DECIMAL(10, 2),
    payment_method VARCHAR(50), -- e.g., Credit Card, Bank Transfer, Cash
    payment_status VARCHAR(50), -- e.g., Paid, Pending, Overdue
    FOREIGN KEY (rental_id) REFERENCES Rentals (rental_id)
);

-- Creating the MaintenanceRequests table
CREATE TABLE IF NOT EXISTS Maintenance_Requests
(
    request_id      INT AUTO_INCREMENT PRIMARY KEY,
    property_id     INT,
    tenant_id       INT,
    request_date    DATE,
    description     TEXT,
    status          VARCHAR(50), -- e.g., Pending, In Progress, Completed
    resolution_date DATE,
    FOREIGN KEY (property_id) REFERENCES Properties (property_id),
    FOREIGN KEY (tenant_id) REFERENCES Tenants (tenant_id)
);
