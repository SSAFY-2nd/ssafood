CREATE TABLE `liketo` (
  `uid` int NOT NULL,
  `store_id` int NOT NULL,
  `isLike` tinyint NOT NULL,
  PRIMARY KEY (`store_id`,`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin