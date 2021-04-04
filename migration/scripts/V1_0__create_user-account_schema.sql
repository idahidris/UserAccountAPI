
CREATE TABLE IF NOT EXISTS user_status(
id int NOT NULL AUTO_INCREMENT  PRIMARY KEY,
name varchar(20)
);



CREATE TABLE IF NOT EXISTS user_role(
id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
name varchar(20)
);


CREATE TABLE IF NOT EXISTS user_account(
id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
title varchar(20),
firstname varchar(20),
lastname varchar(20),
email varchar(50) NOT NULL,
mobile varchar(50),
password varchar(50),
role int,
registered timestamp,
verified varchar(1),
verified_date timestamp,
deactivated_date timestamp,
status int

);

ALTER TABLE user_account ADD CONSTRAINT user_account_role FOREIGN KEY (role) REFERENCES user_role (id) ON UPDATE NO ACTION ON DELETE NO ACTION;

ALTER TABLE user_account ADD CONSTRAINT user_account_status FOREIGN KEY (status) REFERENCES user_status (id) ON UPDATE NO ACTION ON DELETE NO ACTION;
commit;