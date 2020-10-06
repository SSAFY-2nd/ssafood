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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
