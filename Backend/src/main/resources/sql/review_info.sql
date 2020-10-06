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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;