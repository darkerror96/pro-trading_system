/* stocks table */
drop table if exists `stocks`;
CREATE TABLE `stocks` (
 `ticker` varchar(10) NOT NULL,
 `full_name` tinytext NOT NULL,
 `bid` double NOT NULL,
 `ask` double NOT NULL,
 `last` double NOT NULL,
 PRIMARY KEY (`ticker`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/* banks table */
drop table if exists `banks`;
CREATE TABLE `banks` (
 `id` int(11) NOT NULL,
 `owner` tinytext NOT NULL,
 `name` tinytext NOT NULL,
 `acc_num` tinytext NOT NULL,
 `routing_num` tinytext NOT NULL,
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/* credit_cards table */
drop table if exists `credit_cards`;
CREATE TABLE `credit_cards` (
 `id` int(11) NOT NULL,
 `owner` tinytext NOT NULL,
 `cc_type` tinytext NOT NULL,
 `number` tinytext NOT NULL,
 `exp_month` tinyint(2) NOT NULL,
 `exp_year` smallint(4) NOT NULL,
 `cvv` tinytext NOT NULL,
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/* address table */
drop table if exists `address`;
CREATE TABLE `address` (
 `id` int(11) NOT NULL,
 `street1` varchar(30) NOT NULL,
 `street2` varchar(30) DEFAULT NULL,
 `city` varchar(30) NOT NULL,
 `state` varchar(20) NOT NULL,
 `zip` varchar(10) NOT NULL,
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/* accounts table */
drop table if exists `accounts`;
CREATE TABLE `accounts` (
 `id` int(11) NOT NULL COMMENT 'login.id gets copied to this id',
 `first_name` varchar(20) NOT NULL,
 `last_name` varchar(20) NOT NULL,
 `dob` date NOT NULL,
 `ssn` varchar(12) NOT NULL,
 `email` varchar(40) NOT NULL,
 `address_id` int(11) DEFAULT NULL,
 `mailing_address_id` int(11) DEFAULT NULL,
 `is_verified` tinyint(1) DEFAULT NULL,
 UNIQUE KEY `id` (`id`),
 KEY `mailing address id foreign key` (`mailing_address_id`),
 KEY `address id foreign key` (`address_id`),
 CONSTRAINT `address id foreign key` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`),
 CONSTRAINT `mailing address id foreign key` FOREIGN KEY (`mailing_address_id`) REFERENCES `address` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/* positions table */
drop table if exists `positions`;
CREATE TABLE `positions` (
 `id` int(11) NOT NULL AUTO_INCREMENT,
 `account_id` int(11) NOT NULL,
 `side` int(11) NOT NULL COMMENT '-1 for short, 1 for long',
 `size` int(11) NOT NULL,
 `price` double NOT NULL,
 `symbol` varchar(10) NOT NULL,
 `is_open` tinyint(1) NOT NULL,
 `creation_date` date NOT NULL,
 PRIMARY KEY (`id`),
 KEY `account_id foreign key` (`account_id`),
 CONSTRAINT `account_id foreign key` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

/* orders table */
drop table if exists `orders`;
CREATE TABLE `orders` (
 `id` int(11) NOT NULL AUTO_INCREMENT,
 `order_type` enum('BUY','SELL','SELL_SHORT','BUY_TO_COVER') NOT NULL,
 `price_type` enum('MARKET','LIMIT','STOP','') NOT NULL,
 `time_in_force` enum('DAY','GTX','GTC','') NOT NULL,
 `order_status` enum('PENDING','EXECUTED','CANCELLED','') NOT NULL,
 `account_id` int(11) NOT NULL,
 `symbol` varchar(10) NOT NULL,
 `size` int(11) NOT NULL,
 `stop_price` double NOT NULL,
 `creation_date` date NOT NULL,
 PRIMARY KEY (`id`),
 KEY `order account_id foreign key` (`account_id`) USING BTREE,
 CONSTRAINT `order_account_id foreign key` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

/* login table */
drop table if exists `login`;
CREATE TABLE `login` (
 `id` int(11) NOT NULL AUTO_INCREMENT,
 `username` varchar(20) NOT NULL,
 `password` varchar(20) NOT NULL,
 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;