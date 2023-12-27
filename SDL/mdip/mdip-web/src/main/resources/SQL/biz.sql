-- ----------------------------
-- Table structure for JOB_INFO
-- ----------------------------
DROP TABLE IF EXISTS `JOB_INFO`;
CREATE TABLE `JOB_INFO` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `JOB_NAME` varchar(50) DEFAULT NULL,
  `JOB_FILE_DIR` varchar(200) DEFAULT NULL,
  `JOB_FILE_NAME` varchar(50) DEFAULT NULL,
  `JOB_DESCRIPTION` varchar(200) DEFAULT NULL,
  `JOB_TYPE` varchar(2) DEFAULT NULL,
  `JOB_STATUS` int(11) DEFAULT NULL,
  `RUN_STATUS` varchar(20) DEFAULT NULL,
  `LAST_UPDATE` varchar(19) DEFAULT NULL,
  `START_TIME` varchar(19) DEFAULT NULL,
  `END_TIME` varchar(19) DEFAULT NULL,
  `RUN_TIME` bigint(20) DEFAULT NULL,
  `LAST_RECORD_COUNT` int(11) DEFAULT NULL,
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `log_folder_size` char(6) DEFAULT '0' COMMENT 'log文件夹大小',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Trigger structure for KETTLE_JOB_LOG
-- ----------------------------
DROP TABLE IF EXISTS `KETTLE_JOB_LOG`;
CREATE TABLE `KETTLE_JOB_LOG` (
  `JOBID` varchar(64) DEFAULT NULL,
  `JOBNAME` varchar(255) DEFAULT NULL,
  `STATUS` varchar(15) DEFAULT NULL,
  `STARTDATE` datetime DEFAULT NULL,
  `ENDDATE` datetime DEFAULT NULL,
  `id` varchar(64) NOT NULL COMMENT '编号',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `FILES_READ` int(11) DEFAULT '0',
  `FILES_SUCCESS` int(11) DEFAULT '0',
  `FILES_FAIL` int(11) DEFAULT '0',  
  `FILES_REAL_IN` int(11) DEFAULT '0',
  `FILES_REAL_OUT` int(11) DEFAULT '0',
  `FILES_REAL_SUM` int(11) DEFAULT '0',
  `FILES_HISTORY_IN` int(11) DEFAULT '0',
  `FILES_HISTORY_OUT` int(11) DEFAULT '0',
  `FILES_HISTORY_SUM` int(11) DEFAULT '0',
  `FILES_DB_IN` int(11) DEFAULT '0',
  `FILES_DB_OUT` int(11) DEFAULT '0',
  `FILES_DB_SUM` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `IDX_JOB_LOG_1` (`JOBID`),
  KEY `IDX_JOB_LOG_2` (`STATUS`,`JOBNAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;