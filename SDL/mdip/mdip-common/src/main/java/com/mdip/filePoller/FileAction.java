package com.mdip.filePoller;

/**
 * 文件变动行为枚举
 * 
 * @author yonggang
 *
 */
public enum FileAction {
	DELETE("ENTRY_DELETE"), CREATE("ENTRY_CREATE"), MODIFY("ENTRY_MODIFY");
	private String value;

	FileAction(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
