DROP TABLE IF EXISTS `store`;
CREATE TABLE `store` (
  `store_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) DEFAULT NULL,
  `branch` VARCHAR(100) DEFAULT NULL,
  `area` VARCHAR(100) DEFAULT NULL,
  `tel` VARCHAR(30) DEFAULT NULL,
  `address` VARCHAR(100) DEFAULT NULL,
  `latitude` float(9,6) NULL,
  `longtitude` float(9,6) NULL,
  `category` text NULL,
  `menu` text NULL,
  PRIMARY KEY (`store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `bhour`;
CREATE TABLE `bhour` (
  `bhour_id` int(11) NOT NULL AUTO_INCREMENT,
  `store_id` int(11) DEFAULT NULL,
  `type` int(1) DEFAULT NULL,
  `week_type` int(1) DEFAULT NULL,
  `mon` int(1) DEFAULT NULL,
  `tue` int(1) DEFAULT NULL,
  `wed` int(1) DEFAULT NULL,
  `thu` int(1) DEFAULT NULL,
  `fri` int(1) DEFAULT NULL,
  `sat` int(1) DEFAULT NULL,
  `sun` int(1) DEFAULT NULL,
  `start_time` time DEFAULT NULL,
  `end_time` time DEFAULT NULL,
  `etc` text(126) DEFAULT NULL,
  PRIMARY KEY (`bhour_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `review_info`;
CREATE TABLE `review_info` (
  `review_id` int NOT NULL AUTO_INCREMENT,
  `store_id` int(11) DEFAULT NULL,
  `writer_id` int(11) DEFAULT NULL,
  `gender` int(1) DEFAULT NULL,
  `born_year` varchar(4) DEFAULT NULL,
  `total_score` int(1) DEFAULT NULL,
  `content` text DEFAULT NULL,
  `reg_time` datetime DEFAULT NULL,
  PRIMARY KEY (`review_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;