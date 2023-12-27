package com.mdip.filePoller;

import java.io.File;

/**
 * 继承Thread类，可以设置线程运行状态，启动、停止
 * 
 * @author yonggang
 *
 */
public class Watcher2 extends Thread {
	private boolean running = false;
	private File file;// 要监控的文件

	/**
	 * 构造方法
	 * 
	 * @param file
	 */
	public Watcher2(File file) {
		this.file = file;
		System.out.println("正在监视文件夹:" + file.getAbsolutePath() + "的变化");
	}

	/**
	 * 覆盖父类的run方法
	 */
	@Override
	public void run() {
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
		System.out.println("线程结束...");
	}

	/**
	 * 启动,覆盖父类的start方法
	 */
	@Override
	public void start() {
		this.running = true;// 将running置为true，表示线程需要运行
		super.start();
	}

	/**
	 * 启动线程
	 */
	public void startThread() {
		System.out.println("启动线程....");
		this.start();
	}

	/**
	 * 停止线程
	 */
	public void stopThread() {
		System.out.println("结束线程....");
		this.setRunning(false);
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

}
