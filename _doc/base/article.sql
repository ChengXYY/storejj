CREATE TABLE `article` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(200) DEFAULT NULL,
  `title` varchar(500) DEFAULT NULL,
  `author` varchar(200) DEFAULT NULL,
  `content` longtext,
  `create_by` varchar(200) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;