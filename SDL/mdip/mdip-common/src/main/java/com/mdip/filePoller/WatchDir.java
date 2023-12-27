package com.mdip.filePoller;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件夹监控
 * 
 * @author yonggang
 *
 */
public class WatchDir {
	private final WatchService watcher;
	private final Map<WatchKey, Path> monitorPathMap;
	private final boolean subDir;

	@SuppressWarnings("unchecked")
	static <T> WatchEvent<T> cast(WatchEvent<?> event) {
		return (WatchEvent<T>) event;
	}

	/**
	 * 构造方法
	 * 
	 * @param file
	 *            仅文件目录
	 * @param subDir
	 *            是否监听子目录
	 * @param callback
	 *            回调函数
	 * @throws Exception
	 */
	public WatchDir(File file, boolean subDir, FileActionCallback callback) throws Exception {
		if (null != file && file.isDirectory()) {
			this.watcher = FileSystems.getDefault().newWatchService();
			this.monitorPathMap = new HashMap<WatchKey, Path>();
			this.subDir = subDir;

			Path dir = Paths.get(file.getAbsolutePath());// 拿到目录绝对路径
			if (subDir) {
				registerAll(dir);// 观察指定的目录，并且包括子目录
			} else {
				register(dir);// 观察指定的目录，并不且包括子目录
			}
			processEvents(callback);
		} else {
			throw new Exception(file.getAbsolutePath() + " is not a directory!");
		}
	}

	/**
	 * 观察指定的目录
	 * 
	 * @param dir
	 *            指定的目录
	 * @throws IOException
	 */
	private void register(Path dir) throws IOException {
		WatchKey key = dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
		monitorPathMap.put(key, dir);
	}

	/**
	 * 观察指定的目录，并且包括子目录
	 * 
	 * @param start
	 * @throws IOException
	 */
	private void registerAll(final Path start) throws IOException {
		Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				register(dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}

	/**
	 * 执行回调函数
	 * 
	 * @param callback
	 *            回调函数
	 */
	void processEvents(FileActionCallback callback) {
		while (true) {
			WatchKey key;
			try {
				key = watcher.take();
			} catch (InterruptedException x) {
				return;
			}

			Path dir = monitorPathMap.get(key);// 根据watchKey获取监控目录
			if (null == dir) {
				System.err.println("操作未识别");
				continue;
			}

			for (WatchEvent<?> event : key.pollEvents()) {
				/**
				 * 事件可能丢失或遗弃
				 */
				Kind<?> kind = event.kind();
				if (kind == StandardWatchEventKinds.OVERFLOW) {
					continue;
				}

				/**
				 * 目录内的变化可能是文件或者目录
				 */
				WatchEvent<Path> watchEvent = cast(event);
				Path name = watchEvent.context();
				Path child = dir.resolve(name);
				File file = child.toFile();

				if (kind.name().equals(FileAction.DELETE.getValue())) {
					callback.delete(file);
				} else if (kind.name().equals(FileAction.CREATE.getValue())) {
					callback.create(file);
				} else if (kind.name().equals(FileAction.MODIFY.getValue())) {
					callback.modify(file);
				} else {
					continue;
				}

				/**
				 * 如果文件夹被创建，则递归监控，之后注册目录及其子目录
				 */
				if (subDir && (kind == StandardWatchEventKinds.ENTRY_CREATE)) {
					try {
						if (Files.isDirectory(child, NOFOLLOW_LINKS)) {
							registerAll(child);
						}
					} catch (IOException x) {
						// ignore to keep sample readbale
					}
				}
			}

			boolean valid = key.reset();
			if (!valid) {
				/**
				 * 移除不可访问的目录,因为有可能目录被移除，就会无法访问
				 */
				monitorPathMap.remove(key);
				/**
				 * 如果待监控的目录都不存在了，就中断执行
				 */
				if (monitorPathMap.isEmpty()) {
					break;
				}
			}
		}
	}

}
