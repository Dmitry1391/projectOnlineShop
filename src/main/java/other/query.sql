CREATE DATABASE `onlineshop`;
CREATE TABLE IF NOT EXISTS `onlineshop`.`admin`
(
    `admin_id` INT(11) NOT NULL AUTO_INCREMENT,
    `admin_name` VARCHAR(45) NOT NULL,
    `admin_login` VARCHAR(50) NOT NULL DEFAULT 'login',
    `admin_password` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`admin_id`),
    UNIQUE INDEX `admin_login` (`admin_login`)
)
    COLLATE='utf8_general_ci'
    ENGINE=InnoDB
    AUTO_INCREMENT=1
;
CREATE TABLE IF NOT EXISTS `onlineshop`.`clients`
(
    `client_id` INT(11) NOT NULL AUTO_INCREMENT,
    `client_name` VARCHAR(50) NOT NULL,
    `client_login` VARCHAR(50) NOT NULL,
    `client_password` VARCHAR(50) NOT NULL,
    `black_list` TINYINT(4) NOT NULL DEFAULT '0',
    PRIMARY KEY (`client_id`),
    UNIQUE INDEX `client_login` (`client_login`)
)
    COLLATE='utf8_general_ci'
    ENGINE=InnoDB
    AUTO_INCREMENT=1
;
CREATE TABLE IF NOT EXISTS `onlineshop`.`products`
(
    `product_id` INT(11) NOT NULL AUTO_INCREMENT,
    `product_name` VARCHAR(45) NOT NULL,
    `product_price` DOUBLE NOT NULL,
    PRIMARY KEY (`product_id`)
)
    COLLATE='utf8_general_ci'
    ENGINE=InnoDB
    AUTO_INCREMENT=1
;
CREATE TABLE IF NOT EXISTS `onlineshop`.`orders`
(
    `order_id` INT(11) NOT NULL AUTO_INCREMENT,
    `client_id` INT(11) NOT NULL,
    `order_status` VARCHAR(50) NOT NULL DEFAULT '',
    `total_cost` DOUBLE NOT NULL DEFAULT '0',
    PRIMARY KEY (`order_id`),
    INDEX `client_order_fk_idx` (`client_id`),
    CONSTRAINT `FK_user` FOREIGN KEY (`client_id`) REFERENCES `clients` (`client_id`) ON UPDATE CASCADE ON DELETE CASCADE
)
    COLLATE='utf8_general_ci'
    ENGINE=InnoDB
    AUTO_INCREMENT=1
;
CREATE TABLE IF NOT EXISTS `onlineshop`.`order_details`
(
    `id_details` INT(11) NOT NULL AUTO_INCREMENT,
    `order_id` INT(11) NOT NULL,
    `product_id` INT(11) NOT NULL,
    PRIMARY KEY (`id_details`),
    INDEX `fk_product_id_idx` (`product_id`),
    INDEX `fk_order_id` (`order_id`),
    CONSTRAINT `FK_order_id` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT `FK_product_id` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON UPDATE CASCADE ON DELETE CASCADE
)
    COLLATE='utf8_general_ci'
    ENGINE=InnoDB
    AUTO_INCREMENT=1
;