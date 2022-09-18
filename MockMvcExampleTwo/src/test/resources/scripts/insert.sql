CREATE SCHEMA IF NOT EXISTS dbtest;
CREATE TABLE IF NOT EXISTS ACCOUNT(
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    PASSWORD VARCHAR(100) NOT NULL,
    isAdmin SMALLINT DEFAULT FALSE
);
INSERT INTO ACCOUNT(id,email,password,isAdmin) VALUES (1,'admin@example.com','admin',1);
INSERT INTO ACCOUNT(id,email,password,isAdmin) VALUES (2,'user@gmail.com','user',0);
INSERT INTO ACCOUNT(id,email,password,isAdmin) VALUES (3,'test@example.com','test',0);