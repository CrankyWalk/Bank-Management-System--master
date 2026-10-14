-- Legacy Swing application schema. Run in MySQL Workbench.
-- This creates the tables expected by Signup, Signup2, Signup3 and banking screens.
-- If you already have these tables with data, back up your database before changing anything.
CREATE DATABASE IF NOT EXISTS bankSystem;
USE bankSystem;

CREATE TABLE IF NOT EXISTS signup (
  formno VARCHAR(32) PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  fname VARCHAR(100) NOT NULL,
  dob VARCHAR(30),
  gender VARCHAR(20),
  email VARCHAR(150),
  marital VARCHAR(30),
  address VARCHAR(255),
  city VARCHAR(100),
  pincode VARCHAR(20),
  state VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS Signuptwo (
  formno VARCHAR(32) PRIMARY KEY,
  religion VARCHAR(40),
  category VARCHAR(40),
  income VARCHAR(40),
  education VARCHAR(60),
  occupation VARCHAR(60),
  pan VARCHAR(30),
  aadhar VARCHAR(30),
  senior_citizen VARCHAR(10),
  existing_account VARCHAR(10)
);

CREATE TABLE IF NOT EXISTS signupthree (
  formno VARCHAR(32) PRIMARY KEY,
  account_type VARCHAR(60) NOT NULL,
  cardno VARCHAR(32) NOT NULL UNIQUE,
  pin VARCHAR(16) NOT NULL,
  services VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS login (
  formno VARCHAR(32) PRIMARY KEY,
  cardno VARCHAR(32) NOT NULL UNIQUE,
  pin VARCHAR(16) NOT NULL
);

CREATE TABLE IF NOT EXISTS bank (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  pin VARCHAR(16) NOT NULL,
  date VARCHAR(40) NOT NULL,
  type VARCHAR(30) NOT NULL,
  amount DECIMAL(15,2) NOT NULL,
  INDEX idx_bank_pin_date (pin, date)
);
