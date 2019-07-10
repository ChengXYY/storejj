CREATE TABLE `admin` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `account` varchar(50) DEFAULT NULL,
  `password` varchar(200) DEFAULT NULL,
  `salt` varchar(200) DEFAULT NULL,
  `group_id` int(4) DEFAULT NULL,
  `last_login_ip` varchar(100) DEFAULT NULL,
  `last_login_time` datetime DEFAULT NULL,
  `login_count` int(10) DEFAULT '0',
  `create_time` timestamp NULL DEFAULT NULL,
  `parent_id` int(11) DEFAULT '0',
  `create_by` varchar(200) DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;