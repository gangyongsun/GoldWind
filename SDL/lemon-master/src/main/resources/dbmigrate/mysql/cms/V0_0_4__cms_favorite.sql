

-------------------------------------------------------------------------------
--  cms favorite
-------------------------------------------------------------------------------
CREATE TABLE CMS_FAVORITE(
        ID BIGINT NOT NULL,
	SUBJECT VARCHAR(200),
	CREATE_TIME TIMESTAMP,
	USER_ID VARCHAR(200),
	ARTICLE_ID BIGINT,
	COMMENT_ID BIGINT,
        CONSTRAINT PK_CMS_FAVORITE PRIMARY KEY(ID),
	CONSTRAINT FK_CMS_FAVORITE_ARTICLE FOREIGN KEY(ARTICLE_ID) REFERENCES CMS_ARTICLE(ID),
	CONSTRAINT FK_CMS_FAVORITE_COMMENT FOREIGN KEY(COMMENT_ID) REFERENCES CMS_COMMENT(ID)
) ENGINE=INNODB CHARSET=UTF8;
