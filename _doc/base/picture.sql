CREATE TABLE `picture` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(200) DEFAULT NULL,
  `intro` varchar(500) DEFAULT NULL,
  `link` varchar(500) DEFAULT NULL COMMENT '跳转链接',
  `name` varchar(500) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `size` int(11) DEFAULT NULL,
  `ext` varchar(20) DEFAULT NULL,
  `create_by` varchar(200) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;