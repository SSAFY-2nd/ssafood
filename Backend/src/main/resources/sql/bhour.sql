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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
