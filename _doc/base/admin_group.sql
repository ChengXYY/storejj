CREATE TABLE `admin_group` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `auth` varchar(600) DEFAULT NULL,
  `sort` int(4) NOT NULL DEFAULT '99',
  `parent_id` int(4) DEFAULT '0',
  `create_time` timestamp NULL DEFAULT NULL,
  `create_by` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;