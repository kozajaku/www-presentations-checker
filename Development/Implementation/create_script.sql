CREATE DATABASE IF NOT EXISTS `checker`
    CHARACTER SET utf8
    COLLATE utf8_czech_ci;

USE `checker`;

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';


DROP TABLE IF EXISTS `check`;
DROP TABLE IF EXISTS `domain`;
DROP TABLE IF EXISTS `login`;
DROP TABLE IF EXISTS `user`;

CREATE TABLE `check`
(
	`id_check` INTEGER NOT NULL AUTO_INCREMENT,
	`checking_created` DATETIME NOT NULL,
	`state` VARCHAR(50) NOT NULL,
	`result_log` MEDIUMTEXT,
	`graph` MEDIUMTEXT,
	`checking_finished` DATETIME,
	`start_point` VARCHAR(255) NOT NULL,
	`max_depth` INTEGER,
	`check_HTML` BOOL NOT NULL,
	`check_CSS` BOOL NOT NULL,
	`check_CSS_redundancy` BOOL NOT NULL,
	`check_links` BOOL NOT NULL,
	`user` INTEGER NOT NULL,
	PRIMARY KEY (`id_check`),
	KEY (`user`)
) ENGINE = InnoDB;


CREATE TABLE `domain`
(
	`id_domain` INTEGER NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(255) NOT NULL,
	`request_interval` INTEGER,
	`checking` INTEGER NOT NULL,
	PRIMARY KEY (`id_domain`),
	KEY (`checking`)
) ENGINE = InnoDB;


CREATE TABLE `login`
(
	`id_login` INTEGER NOT NULL AUTO_INCREMENT,
	`address` VARCHAR(50) NOT NULL,
	`time` DATETIME NOT NULL,
	`user` INTEGER NOT NULL,
	PRIMARY KEY (`id_login`),
	KEY (`user`)
) ENGINE = InnoDB;


CREATE TABLE `user`
(
	`id_user` INTEGER NOT NULL AUTO_INCREMENT,
	`email` VARCHAR(254) NOT NULL,
	`password` VARCHAR(64) NOT NULL,
	`salt` VARCHAR(64) NOT NULL,
	`name` VARCHAR(100) NOT NULL,
	`surname` VARCHAR(100) NOT NULL,
	`registration_date` DATE NOT NULL,
	PRIMARY KEY (`id_user`)
) ENGINE = InnoDB;

ALTER TABLE `check` ADD CONSTRAINT `FK_check_user` 
	FOREIGN KEY (`user`) REFERENCES `user` (`id_user`)
	ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `domain` ADD CONSTRAINT `FK_domain_check` 
	FOREIGN KEY (`checking`) REFERENCES `check` (`id_check`)
	ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `login` ADD CONSTRAINT `FK_login_user` 
	FOREIGN KEY (`user`) REFERENCES `user` (`id_user`)
	ON DELETE CASCADE ON UPDATE CASCADE;
                    
SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
                    