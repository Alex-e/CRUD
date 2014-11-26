CREATE DATABASE IF NOT EXISTS test DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
GRANT ALL privileges ON test.* TO root@localhost;
USE test;
CREATE TABLE User
  (
     id            INT(8) PRIMARY KEY AUTO_INCREMENT,
     name          VARCHAR(25),
     age           INT,
     isAdmin       BIT,
     createdDate   TIMESTAMP DEFAULT NOW()
  ); 