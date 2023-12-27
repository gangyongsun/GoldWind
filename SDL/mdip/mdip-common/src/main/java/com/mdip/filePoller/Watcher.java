package com.mdip.filePoller;

import java.io.File;

/**
 * 实现Runnable借口
 * 
 * @author yonggang
 *
 */
public class Watcher implements Runnable {
	private File file;// 要监控的文件

	/**
	 * 构造方法
	 * 
	 * @param file
	 *            要监控的文件
	 */
	public Watcher(File file) {
		this.file = file;
	}

	@Override
	public void run() {
		System.out.println("正在监视文件夹:" + file.getAbsolutePath() + "的变化");
		try {
			new WatchDir(file, true, new FileActionCallback() {
				@Override
				public void create(File file) {
					System.out.println("文件已创建\t" + file.getAbsolutePath());
				}

				@Override
				public void delete(File file) {
					System.out.println("文件已删除\t" + file.getAbsolutePath());
				}

				@Override
				public void modify(File file) {
					System.out.println("文件已修改\t" + file.getAbsolutePath());
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
