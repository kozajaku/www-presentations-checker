SET FOREIGN_KEY_CHECKS=0;



DROP TABLE IF EXISTS `check`;
DROP TABLE IF EXISTS `domain`;
DROP TABLE IF EXISTS `login`;
DROP TABLE IF EXISTS `user`;

CREATE TABLE `check`
(
	`id_check` INTEGER NOT NULL AUTO_INCREMENT,
	`checking_created` TIME NOT NULL,
	`state` VARCHAR(50) NOT NULL,
	`result_log` MEDIUMTEXT,
	`graph` MEDIUMTEXT,
	`checking_finished` TIME,
	`start_point` VARCHAR(255) NOT NULL,
	`max_depth` INTEGER,
	`check_HTML` BOOL NOT NULL,
	`check_CSS` BOOL NOT NULL,
	`check_CSS_redundancy` BOOL NOT NULL,
	`check_links` BOOL NOT NULL,
	`user` INTEGER NOT NULL,
	PRIMARY KEY (`id_check`),
	KEY (`user`)
) ;


CREATE TABLE `domain`
(
	`id_domain` INTEGER NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(255) NOT NULL,
	`request_interval` INTEGER,
	`checking` INTEGER NOT NULL,
	PRIMARY KEY (`id_domain`),
	KEY (`checking`)
) ;


CREATE TABLE `login`
(
	`id_login` INTEGER NOT NULL AUTO_INCREMENT,
	`address` VARCHAR(50) NOT NULL,
	`time` TIME NOT NULL,
	`user` INTEGER NOT NULL,
	PRIMARY KEY (`id_login`),
	KEY (`user`)
) ;


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
) ;



SET FOREIGN_KEY_CHECKS=1;


ALTER TABLE `check` ADD CONSTRAINT `FK_check_user` 
	FOREIGN KEY (`user`) REFERENCES `user` (`id_user`)
	ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `domain` ADD CONSTRAINT `FK_domain_check` 
	FOREIGN KEY (`checking`) REFERENCES `check` (`id_check`)
	ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `login` ADD CONSTRAINT `FK_login_user` 
	FOREIGN KEY (`user`) REFERENCES `user` (`id_user`)
	ON DELETE CASCADE ON UPDATE CASCADE;
