-- schema-mysql.sql
DROP TABLE IF EXISTS ACCOUNT;

CREATE TABLE ACCOUNT (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    balance DECIMAL(19, 2) NOT NULL
);

INSERT INTO ACCOUNT (name, balance) VALUES ('MySQL User One', 1000.00);
INSERT INTO ACCOUNT (name, balance) VALUES ('MySQL User Two', 1500.00);
INSERT INTO ACCOUNT (name, balance) VALUES ('MySQL User Three', 2000.00); -- For rollback tests
