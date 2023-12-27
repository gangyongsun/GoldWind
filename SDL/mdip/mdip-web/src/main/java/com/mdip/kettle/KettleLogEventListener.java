package com.mdip.kettle;

import java.io.File;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.vfs2.FileObject;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.logging.FileLoggingEventListener;
import org.pentaho.di.core.logging.KettleLogLayout;
import org.pentaho.di.core.logging.KettleLoggingEvent;
import org.pentaho.di.core.logging.KettleLoggingEventListener;
import org.pentaho.di.core.logging.LogMessage;
import org.pentaho.di.core.logging.LoggingRegistry;
import org.pentaho.di.core.vfs.KettleVFS;

import com.mdip.common.util.FileUtil;
import com.mdip.common.util.PropertiesUtil;
import com.mdip.common.util.ZipCompress;

public class KettleLogEventListener implements KettleLoggingEventListener {

	private static final String ConfigPropertyFile = "kettle.properties";// mdip-web项目中的Kettle配置文件名字
	private static final String LOG_SIZE = PropertiesUtil.getValueByKey("log.file.size", ConfigPropertyFile);
	private static final String LOG_SUFFIX = PropertiesUtil.getValueByKey("log.file.suffix", ConfigPropertyFile);
	private static final long logSize = "".equals(LOG_SIZE) ? 0 : Long.valueOf(LOG_SIZE) * 1024 * 1024;

	private String filename;
	private FileObject file;

	public FileObject getFile() {
		return file;
	}

	private OutputStream outputStream;
	private KettleLogLayout layout;

	private KettleException exception;
	private String logChannelId;

	/**
	 * Log all log lines to the specified file
	 *
	 * @param filename
	 * @param append
	 * @throws KettleException
	 */
	public KettleLogEventListener(String filename, boolean append) throws KettleException {
		this(null, filename, append);
	}

	/**
	 * Log only lines belonging to the specified log channel ID or one of it's
	 * children (grandchildren) to the specified file.
	 *
	 * @param logChannelId
	 * @param filename
	 * @param append
	 * @throws KettleException
	 */
	public KettleLogEventListener(String logChannelId, String filename, boolean append) throws KettleException {
		this.logChannelId = logChannelId;
		this.filename = filename;
		this.layout = new KettleLogLayout(true);
		this.exception = null;

		file = KettleVFS.getFileObject(filename);
		outputStream = null;
		try {
			outputStream = KettleVFS.getOutputStream(file, append);
		} catch (Exception e) {
			throw new KettleException("Unable to create a logging event listener to write to file '" + filename + "'",
					e);
		}
	}

	@Override
	public void eventAdded(KettleLoggingEvent event) {

		try {
			Object messageObject = event.getMessage();
			if (messageObject instanceof LogMessage) {
				boolean logToFile = false;

				// 如果log文件超过指定大小，则新创建一个
				if (file!=null && file.getContent().getSize() >= logSize) {
					String path = getWinDir(filename);
					String newFileName = getNewFileName(path);
					ZipCompress.zip(path, path.substring(0, path.lastIndexOf("/") + 1) + newFileName + ".zip");
					FileUtil.clearInfoForFile(path);
				}

				if (logChannelId == null) {
					logToFile = true;
				} else {
					LogMessage message = (LogMessage) messageObject;
					// This should be fast enough cause cached.
					List<String> logChannelChildren = LoggingRegistry.getInstance().getLogChannelChildren(logChannelId);
					// This could be non-optimal, consider keeping the list
					// sorted in the logging registry
					logToFile = Const.indexOfString(message.getLogChannelId(), logChannelChildren) >= 0;
				}

				if (logToFile) {
					String logText = layout.format(event);
					outputStream.write(logText.getBytes());
					outputStream.write(Const.CR.getBytes());
				}
			}
		} catch (Exception e) {
			exception = new KettleException("Unable to write to logging event to file '" + filename + "'", e);
		}
	}

	public void close() throws KettleException {
		try {
			if (outputStream != null) {
				outputStream.close();
			}
		} catch (Exception e) {
			throw new KettleException("Unable to close output of file '" + filename + "'", e);
		}
	}

	public KettleException getException() {
		return exception;
	}

	public void setException(KettleException exception) {
		this.exception = exception;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	/**
	 * 返回新加的zip文件的文件名，如“×××.0.zip”
	 * @param path
	 * @return
	 */
	private String getNewFileName(String path) {
		File dir = new File(path.substring(0, path.lastIndexOf("/")));
		String fileName = path.substring(path.lastIndexOf("/") + 1);
		int maxFileIndex = 0;
		String newFileName = "";
		if (dir.isDirectory()) {
			String[] files = dir.list();

			for (String file : files) {
				if (file.startsWith(fileName.split(LOG_SUFFIX)[0])) {
					String[] str = file.split(".zip")[0].split(LOG_SUFFIX)[0].split("\\.");
					int index = str.length == 2 ? Integer.parseInt(str[1]) + 1 : 0;
					if (index > maxFileIndex) {
						maxFileIndex = index;
					}
				}
			}
		}
		newFileName = fileName.substring(0, fileName.lastIndexOf(LOG_SUFFIX)) + "." + maxFileIndex + LOG_SUFFIX;
		return newFileName;
	}

	/**
	 * 对于Windows系统上的文件路径，如果没有磁盘符，则加上D：
	 * @param path
	 * @return
	 */
	private String getWinDir(String path) {
		String os = System.getProperty("os.name");
		if (path.startsWith("/") && os.toLowerCase().startsWith("win")) {
			return "D:" + path;
		}
		return path;
	}
}
