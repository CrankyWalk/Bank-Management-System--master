CREATE DATABASE IF NOT EXISTS bank_assessment;
USE bank_assessment;

CREATE TABLE IF NOT EXISTS accounts (
  account_number VARCHAR(32) PRIMARY KEY,
  username VARCHAR(80) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  balance DECIMAL(15,2) NOT NULL DEFAULT 0.00,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS transactions (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  account_number VARCHAR(32) NOT NULL,
  type ENUM('DEPOSIT','WITHDRAWAL') NOT NULL,
  amount DECIMAL(15,2) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_transactions_account FOREIGN KEY (account_number)
    REFERENCES accounts(account_number),
  CONSTRAINT chk_transaction_amount CHECK (amount > 0)
);
