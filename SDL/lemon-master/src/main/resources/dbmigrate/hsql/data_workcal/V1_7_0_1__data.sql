

-- 0表示每周工作日的规则，1表示假期，2表示调休导致的当天上班，3表示调休导致的当前放假


INSERT INTO WORKCAL_RULE(ID,YEAR,WEEK,NAME,STATUS,TYPE_ID,TENANT_ID) VALUES(1601,2016,2,'周一',0,1,1);
INSERT INTO WORKCAL_RULE(ID,YEAR,WEEK,NAME,STATUS,TYPE_ID,TENANT_ID) VALUES(1602,2016,3,'周二',0,1,1);
INSERT INTO WORKCAL_RULE(ID,YEAR,WEEK,NAME,STATUS,TYPE_ID,TENANT_ID) VALUES(1603,2016,4,'周三',0,1,1);
INSERT INTO WORKCAL_RULE(ID,YEAR,WEEK,NAME,STATUS,TYPE_ID,TENANT_ID) VALUES(1604,2016,5,'周四',0,1,1);
INSERT INTO WORKCAL_RULE(ID,YEAR,WEEK,NAME,STATUS,TYPE_ID,TENANT_ID) VALUES(1605,2016,6,'周五',0,1,1);


INSERT INTO WORKCAL_PART(ID,SHIFT,START_TIME,END_TIME,RULE_ID,TENANT_ID) VALUES(1601,0,'9:00', '12:00',1601,1);
INSERT INTO WORKCAL_PART(ID,SHIFT,START_TIME,END_TIME,RULE_ID,TENANT_ID) VALUES(1602,1,'13:00','18:00',1601,1);
INSERT INTO WORKCAL_PART(ID,SHIFT,START_TIME,END_TIME,RULE_ID,TENANT_ID) VALUES(1603,0,'9:00', '12:00',1602,1);
INSERT INTO WORKCAL_PART(ID,SHIFT,START_TIME,END_TIME,RULE_ID,TENANT_ID) VALUES(1604,1,'13:00','18:00',1602,1);
INSERT INTO WORKCAL_PART(ID,SHIFT,START_TIME,END_TIME,RULE_ID,TENANT_ID) VALUES(1605,0,'9:00', '12:00',1603,1);
INSERT INTO WORKCAL_PART(ID,SHIFT,START_TIME,END_TIME,RULE_ID,TENANT_ID) VALUES(1606,1,'13:00','18:00',1603,1);
INSERT INTO WORKCAL_PART(ID,SHIFT,START_TIME,END_TIME,RULE_ID,TENANT_ID) VALUES(1607,0,'9:00', '12:00',1604,1);
INSERT INTO WORKCAL_PART(ID,SHIFT,START_TIME,END_TIME,RULE_ID,TENANT_ID) VALUES(1608,1,'13:00','18:00',1604,1);
INSERT INTO WORKCAL_PART(ID,SHIFT,START_TIME,END_TIME,RULE_ID,TENANT_ID) VALUES(1609,0,'9:00', '12:00',1605,1);
INSERT INTO WORKCAL_PART(ID,SHIFT,START_TIME,END_TIME,RULE_ID,TENANT_ID) VALUES(1610,1,'13:00','18:00',1605,1);


INSERT INTO WORKCAL_RULE(ID,NAME,YEAR,WORK_DATE,STATUS,TYPE_ID,TENANT_ID) VALUES(1611,'元旦',      2016,'2016-01-01 00:00:00',1,1,1);
INSERT INTO WORKCAL_RULE(ID,NAME,YEAR,WORK_DATE,STATUS,TYPE_ID,TENANT_ID) VALUES(1612,'春节调休',  2016,'2016-02-06 00:00:00',2,1,1);
INSERT INTO WORKCAL_RULE(ID,NAME,YEAR,WORK_DATE,STATUS,TYPE_ID,TENANT_ID) VALUES(1613,'春节',      2016,'2016-02-07 00:00:00',1,1,1);
INSERT INTO WORKCAL_RULE(ID,NAME,YEAR,WORK_DATE,STATUS,TYPE_ID,TENANT_ID) VALUES(1614,'春节',      2016,'2016-02-08 00:00:00',1,1,1);
INSERT INTO WORKCAL_RULE(ID,NAME,YEAR,WORK_DATE,STATUS,TYPE_ID,TENANT_ID) VALUES(1615,'春节',      2016,'2016-02-09 00:00:00',1,1,1);
INSERT INTO WORKCAL_RULE(ID,NAME,YEAR,WORK_DATE,STATUS,TYPE_ID,TENANT_ID) VALUES(1616,'春节',      2016,'2016-02-10 00:00:00',1,1,1);
INSERT INTO WORKCAL_RULE(ID,NAME,YEAR,WORK_DATE,STATUS,TYPE_ID,TENANT_ID) VALUES(1617,'春节',      2016,'2016-02-11 00:00:00',1,1,1);
INSERT INTO WORKCAL_RULE(ID,NAME,YEAR,WORK_DATE,STATUS,TYPE_ID,TENANT_ID) VALUES(1618,'春节',      2016,'2016-02-12 00:00:00',1,1,1);
INSERT INTO WORKCAL_RULE(ID,NAME,YEAR,WORK_DATE,STATUS,TYPE_ID,TENANT_ID) VALUES(1619,'春节',      2016,'2016-02-13 00:00:00',1,1,1);
INSERT INTO WORKCAL_RULE(ID,NAME,YEAR,WORK_DATE,STATUS,TYPE_ID,TENANT_ID) VALUES(1620,'春节调休',  2016,'2016-02-14 00:00:00',2,1,1);
INSERT INTO WORKCAL_RULE(ID,NAME,YEAR,WORK_DATE,STATUS,TYPE_ID,TENANT_ID) VALUES(1621,'清明节',    2016,'2016-04-04 00:00:00',1,1,1);
INSERT INTO WORKCAL_RULE(ID,NAME,YEAR,WORK_DATE,STATUS,TYPE_ID,TENANT_ID) VALUES(1622,'劳动节',    2016,'2016-05-01 00:00:00',1,1,1);
INSERT INTO WORKCAL_RULE(ID,NAME,YEAR,WORK_DATE,STATUS,TYPE_ID,TENANT_ID) VALUES(1623,'劳动节补休',2016,'2016-05-02 00:00:00',3,1,1);
INSERT INTO WORKCAL_RULE(ID,NAME,YEAR,WORK_DATE,STATUS,TYPE_ID,TENANT_ID) VALUES(1624,'端午节',    2016,'2016-06-09 00:00:00',1,1,1);
INSERT INTO WORKCAL_RULE(ID,NAME,YEAR,WORK_DATE,STATUS,TYPE_ID,TENANT_ID) VALUES(1625,'端午节补休',2016,'2016-06-10 00:00:00',3,1,1);
INSERT INTO WORKCAL_RULE(ID,NAME,YEAR,WORK_DATE,STATUS,TYPE_ID,TENANT_ID) VALUES(1626,'端午节调休',2016,'2016-06-12 00:00:00',2,1,1);
INSERT INTO WORKCAL_RULE(ID,NAME,YEAR,WORK_DATE,STATUS,TYPE_ID,TENANT_ID) VALUES(1627,'中秋节',    2016,'2016-09-15 00:00:00',1,1,1);
INSERT INTO WORKCAL_RULE(ID,NAME,YEAR,WORK_DATE,STATUS,TYPE_ID,TENANT_ID) VALUES(1628,'中秋节补休',2016,'2016-09-16 00:00:00',3,1,1);
INSERT INTO WORKCAL_RULE(ID,NAME,YEAR,WORK_DATE,STATUS,TYPE_ID,TENANT_ID) VALUES(1629,'中秋节调休',2016,'2016-09-18 00:00:00',2,1,1);
INSERT INTO WORKCAL_RULE(ID,NAME,YEAR,WORK_DATE,STATUS,TYPE_ID,TENANT_ID) VALUES(1630,'国庆节',    2016,'2016-10-03 00:00:00',1,1,1);
INSERT INTO WORKCAL_RULE(ID,NAME,YEAR,WORK_DATE,STATUS,TYPE_ID,TENANT_ID) VALUES(1631,'国庆节',    2016,'2016-10-04 00:00:00',1,1,1);
INSERT INTO WORKCAL_RULE(ID,NAME,YEAR,WORK_DATE,STATUS,TYPE_ID,TENANT_ID) VALUES(1632,'国庆节',    2016,'2016-10-05 00:00:00',1,1,1);
INSERT INTO WORKCAL_RULE(ID,NAME,YEAR,WORK_DATE,STATUS,TYPE_ID,TENANT_ID) VALUES(1633,'国庆节',    2016,'2016-10-06 00:00:00',1,1,1);
INSERT INTO WORKCAL_RULE(ID,NAME,YEAR,WORK_DATE,STATUS,TYPE_ID,TENANT_ID) VALUES(1634,'国庆节',    2016,'2016-10-07 00:00:00',1,1,1);
INSERT INTO WORKCAL_RULE(ID,NAME,YEAR,WORK_DATE,STATUS,TYPE_ID,TENANT_ID) VALUES(1635,'国庆节调休',2016,'2016-10-08 00:00:00',2,1,1);
INSERT INTO WORKCAL_RULE(ID,NAME,YEAR,WORK_DATE,STATUS,TYPE_ID,TENANT_ID) VALUES(1636,'国庆节调休',2016,'2016-10-09 00:00:00',2,1,1);


INSERT INTO WORKCAL_PART(ID,SHIFT,START_TIME,END_TIME,RULE_ID,TENANT_ID) VALUES(1611,0,'9:00', '12:00',1612,1);
INSERT INTO WORKCAL_PART(ID,SHIFT,START_TIME,END_TIME,RULE_ID,TENANT_ID) VALUES(1612,1,'13:00','18:00',1612,1);
INSERT INTO WORKCAL_PART(ID,SHIFT,START_TIME,END_TIME,RULE_ID,TENANT_ID) VALUES(1613,0,'9:00', '12:00',1620,1);
INSERT INTO WORKCAL_PART(ID,SHIFT,START_TIME,END_TIME,RULE_ID,TENANT_ID) VALUES(1614,1,'13:00','18:00',1620,1);
INSERT INTO WORKCAL_PART(ID,SHIFT,START_TIME,END_TIME,RULE_ID,TENANT_ID) VALUES(1615,0,'9:00', '12:00',1626,1);
INSERT INTO WORKCAL_PART(ID,SHIFT,START_TIME,END_TIME,RULE_ID,TENANT_ID) VALUES(1616,1,'13:00','18:00',1626,1);
INSERT INTO WORKCAL_PART(ID,SHIFT,START_TIME,END_TIME,RULE_ID,TENANT_ID) VALUES(1617,0,'9:00', '12:00',1629,1);
INSERT INTO WORKCAL_PART(ID,SHIFT,START_TIME,END_TIME,RULE_ID,TENANT_ID) VALUES(1618,1,'13:00','18:00',1629,1);
INSERT INTO WORKCAL_PART(ID,SHIFT,START_TIME,END_TIME,RULE_ID,TENANT_ID) VALUES(1619,0,'9:00', '12:00',1635,1);
INSERT INTO WORKCAL_PART(ID,SHIFT,START_TIME,END_TIME,RULE_ID,TENANT_ID) VALUES(1620,1,'13:00','18:00',1635,1);
INSERT INTO WORKCAL_PART(ID,SHIFT,START_TIME,END_TIME,RULE_ID,TENANT_ID) VALUES(1621,0,'9:00', '12:00',1636,1);
INSERT INTO WORKCAL_PART(ID,SHIFT,START_TIME,END_TIME,RULE_ID,TENANT_ID) VALUES(1622,1,'13:00','18:00',1636,1);

