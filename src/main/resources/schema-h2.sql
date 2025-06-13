-- schema-h2.sql
DROP TABLE IF EXISTS ACCOUNT;

CREATE TABLE ACCOUNT (
    id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    balance DECIMAL(19, 2) NOT NULL
);

INSERT INTO ACCOUNT (id, name, balance) VALUES (1, 'H2 User One', 1000.00);
INSERT INTO ACCOUNT (id, name, balance) VALUES (2, 'H2 User Two', 1500.00);
INSERT INTO ACCOUNT (id, name, balance) VALUES (3, 'H2 User Three', 2000.00); -- For rollback tests
