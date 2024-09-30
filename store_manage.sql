CREATE DATABASE store_management;
USE store_management;
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE customers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    address VARCHAR(200),
    phone_number VARCHAR(20),
    email VARCHAR(100)
);

CREATE TABLE products (
    product_id VARCHAR(50) PRIMARY KEY,
    brand VARCHAR(100),
    model VARCHAR(100),
    operating_system VARCHAR(100),
    screen_size VARCHAR(50),
    cpu VARCHAR(100),
    ram INT,
    storage_capacity INT,
    rear_camera VARCHAR(100),
    front_camera VARCHAR(100),
    battery VARCHAR(50),
    charging_technology VARCHAR(100),
    connectivity VARCHAR(100),
    design_and_material VARCHAR(100),
    price DECIMAL(10, 2)
);

CREATE TABLE invoices (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    product_id VARCHAR(50),
    quantity INT,
    total_price DECIMAL(10, 2),
    invoice_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);
