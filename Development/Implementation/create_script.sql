CREATE DATABASE IF NOT EXISTS `checker`
    CHARACTER SET utf8
    COLLATE utf8_czech_ci;

USE `checker`;

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';


DROP TABLE IF EXISTS `checkup`;
DROP TABLE IF EXISTS `checkup_has_option`;
DROP TABLE IF EXISTS `domain`;
DROP TABLE IF EXISTS `graph`;
DROP TABLE IF EXISTS `header`;
DROP TABLE IF EXISTS `login`;
DROP TABLE IF EXISTS `message`;
DROP TABLE IF EXISTS `option`;
DROP TABLE IF EXISTS `user`;

CREATE TABLE `checkup`
(
	`id_checkup` INTEGER NOT NULL AUTO_INCREMENT,
	`checking_created` DATETIME NOT NULL,
	`state` VARCHAR(50) NOT NULL,
	`checking_finished` DATETIME,
	`start_point` VARCHAR(2048) NOT NULL,
	`max_depth` INTEGER,
	`user` VARCHAR(254) NOT NULL,
	`checking_interval` INTEGER,
    `page_limit` INTEGER,
	PRIMARY KEY (`id_checkup`),
	KEY (`user`)
) ENGINE = InnoDB;


CREATE TABLE `checkup_has_option`
(
	`checkup` INTEGER NOT NULL,
	`option` VARCHAR(50) NOT NULL,
	PRIMARY KEY (`checkup`, `option`),
	KEY (`checkup`),
	KEY (`option`)
) ENGINE = InnoDB;


CREATE TABLE `domain`
(
	`id_domain` INTEGER NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(255) NOT NULL,
	`checking` INTEGER NOT NULL,
	PRIMARY KEY (`id_domain`),
	KEY (`checking`)
) ENGINE = InnoDB;


CREATE TABLE `graph`
(
	`id_graph` INTEGER NOT NULL AUTO_INCREMENT,
	`output` MEDIUMTEXT NOT NULL,
    `graph_type` VARCHAR(20) NOT NULL,
	`checkup` INTEGER NOT NULL,
	PRIMARY KEY (`id_graph`),
	KEY (`checkup`)
) ENGINE = InnoDB;


CREATE TABLE `header`
(
	`id_header` INTEGER NOT NULL AUTO_INCREMENT,
	`key` VARCHAR(100) NOT NULL,
	`value` VARCHAR(255) NOT NULL,
	`checkup` INTEGER NOT NULL,
	PRIMARY KEY (`id_header`),
	KEY (`checkup`)
) ENGINE = InnoDB;


CREATE TABLE `login`
(
	`id_login` INTEGER NOT NULL AUTO_INCREMENT,
	`address` VARCHAR(50) NOT NULL,
	`time` DATETIME NOT NULL,
	`user` VARCHAR(254) NOT NULL,
	PRIMARY KEY (`id_login`),
	KEY (`user`)
) ENGINE = InnoDB;


CREATE TABLE `message`
(
	`id` INTEGER NOT NULL AUTO_INCREMENT,
	`discriminator` VARCHAR(200) NOT NULL,
	`message` TEXT NOT NULL,
	`page` VARCHAR(2048) NOT NULL,
	`column` INTEGER,
	`row` INTEGER,
	`error_code` INTEGER,
	`resource` VARCHAR(50) NOT NULL,
	`checkup` INTEGER NOT NULL,
	PRIMARY KEY (`id`),
	KEY (`checkup`)
) ENGINE = InnoDB;


CREATE TABLE `option`
(
	`id_option` VARCHAR(50) NOT NULL,
	PRIMARY KEY (`id_option`)
) ENGINE = InnoDB;


CREATE TABLE `user`
(
	`email` VARCHAR(254) NOT NULL,
	`password` VARCHAR(64) NOT NULL,
	`salt` VARCHAR(64) NOT NULL,
	`name` VARCHAR(100) NOT NULL,
	`surname` VARCHAR(100) NOT NULL,
	`registration_date` DATE NOT NULL,
	PRIMARY KEY (`email`)
) ENGINE = InnoDB;


ALTER TABLE `checkup` ADD CONSTRAINT `FK_checkup_user` 
	FOREIGN KEY (`user`) REFERENCES `user` (`email`)
	ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `checkup_has_option` ADD CONSTRAINT `FK_checkup_has_option_checkup` 
	FOREIGN KEY (`checkup`) REFERENCES `checkup` (`id_checkup`)
	ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `checkup_has_option` ADD CONSTRAINT `FK_checkup_has_option_option` 
	FOREIGN KEY (`option`) REFERENCES `option` (`id_option`)
	ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `domain` ADD CONSTRAINT `FK_domain_checkup` 
	FOREIGN KEY (`checking`) REFERENCES `checkup` (`id_checkup`)
	ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `graph` ADD CONSTRAINT `FK_graph_checkup` 
	FOREIGN KEY (`checkup`) REFERENCES `checkup` (`id_checkup`)
	ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `header` ADD CONSTRAINT `FK_header_checkup` 
	FOREIGN KEY (`checkup`) REFERENCES `checkup` (`id_checkup`)
	ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `login` ADD CONSTRAINT `FK_login_user` 
	FOREIGN KEY (`user`) REFERENCES `user` (`email`)
	ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE `message` ADD CONSTRAINT `FK_message_checkup` 
	FOREIGN KEY (`checkup`) REFERENCES `checkup` (`id_checkup`)
	ON DELETE NO ACTION ON UPDATE NO ACTION;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
