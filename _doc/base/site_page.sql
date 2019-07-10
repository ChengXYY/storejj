CREATE TABLE `site_page` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tpl_id` int(11) DEFAULT '0',
  `code` varchar(50) DEFAULT NULL,
  `title` varchar(500) DEFAULT NULL,
  `css` text,
  `content` longtext,
  `create_time` timestamp NULL DEFAULT NULL,
  `create_by` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;