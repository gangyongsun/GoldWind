SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `t_key_info`;
CREATE TABLE `t_key_info`  (
 `key_info_id` int(10) AUTO_INCREMENT,
  `chinese` varchar(64) DEFAULT NULL,
  `english` varchar(64) DEFAULT NULL,
  `form` varchar(32)  DEFAULT 'Full Name',
  `key_info_image` varchar(64)  DEFAULT NULL,
  `key_info_remark` varchar(128) DEFAULT NULL,
  `key_info_subject` varchar(32) DEFAULT NULL,
  `key_info_function` varchar(32) DEFAULT NULL,
  `key_info_position` varchar(32)  DEFAULT NULL,
  PRIMARY KEY (`key_info_id`) USING BTREE
) ENGINE = InnoDB ROW_FORMAT = Compact;


SET FOREIGN_KEY_CHECKS = 1;