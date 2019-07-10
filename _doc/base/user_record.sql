CREATE TABLE `user_record` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `amount` int(11) DEFAULT NULL COMMENT '消费额  分',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `create_by` varchar(200) DEFAULT NULL COMMENT '操作员',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户消费记录';