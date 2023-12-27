package com.mdip.filePoller;

import java.io.File;

/**
 * 文件操作的回调方法
 * 
 * @author yonggang
 *
 */
public abstract class FileActionCallback {

	/**
	 * 删除
	 * 
	 * @param file
	 *            文件
	 */
	public void delete(File file) {
	};

	/**
	 * 修改
	 * 
	 * @param file
	 *            文件
	 */
	public void modify(File file) {
	};

	/**
	 * 创建
	 * 
	 * @param file
	 *            文件
	 */
	public void create(File file) {
	};

}
