package com.mdip.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.druid.util.StringUtils;

/**
 * 文件操作公共类
 * 
 * @author yonggang
 *
 */
public class FileUtil {
	/**
	 * 判断文件是否是二进制文件
	 * 
	 * @param fileName
	 *            文件名
	 * @return
	 * @throws IOException
	 */
	public static boolean isBinary(String fileName) {
		File file = null;
		if (!StringUtils.isEmpty(fileName)) {
			file = new File(fileName);
		}
		return isBinary(file);
	}

	/**
	 * 判断文件是否是二进制文件
	 * 
	 * @param file
	 *            文件
	 * @return
	 * @throws IOException
	 */
	public static boolean isBinary(File file) {
		boolean flag = false;
		FileInputStream fis = null;
		try {
			if (null != file && file.length() > 0) {
				fis = new FileInputStream(file);
				long len = file.length();
				for (int i = 0; i < (int) len; i++) {
					int j = fis.read();
					if (j < 32 && j != 9 && j != 10 && j != 13) {
						flag = true;
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtil.closeQuietly(fis);
		}
		return flag;
	}

	/**
	 * A方法追加文件：使用RandomAccessFile
	 * 
	 * @param fileName
	 *            文件名
	 * @param content
	 *            追加的内容
	 */
	public static void appendMethodA(String fileName, String content) {
		RandomAccessFile randomFile = null;
		try {
			if (!StringUtils.isEmpty(fileName)) {
				randomFile = new RandomAccessFile(fileName, "rw");// 按读写方式打开一个随机访问文件流
				long fileLength = randomFile.length();// 文件长度，字节数
				randomFile.seek(fileLength);// 将写文件指针移到文件尾
				randomFile.writeBytes(content);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtil.closeQuietly(randomFile);
		}
	}

	/**
	 * B方法追加文件：使用FileWriter
	 * 
	 * @param fileName
	 * @param content
	 */
	public static void appendMethodB(String fileName, String content) {
		FileWriter writer = null;
		try {
			if (!StringUtils.isEmpty(fileName)) {
				writer = new FileWriter(fileName, true);// 打开一个写文件器，第二个参数true表示以追加形式写文件
				writer.write(content);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtil.closeQuietly(writer);
		}
	}

	/**
	 * 把文件拷贝的另一个地方
	 * 
	 * @param srcFile
	 *            源文件
	 * @param dstFile
	 *            目标文件
	 */
	public static void copyFile(File srcFile, File dstFile) {
		int BUFFER_SIZE = 16 * 1024;
		InputStream in = null;
		OutputStream out = null;
		try {
			if (null != srcFile && srcFile.length() > 0) {
				in = new BufferedInputStream(new FileInputStream(srcFile), BUFFER_SIZE);
				out = new BufferedOutputStream(new FileOutputStream(dstFile), BUFFER_SIZE);
				byte[] buffer = new byte[BUFFER_SIZE];
				while (in.read(buffer) > 0) {
					out.write(buffer);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtil.closeQuietly(in, out);
		}
	}

	/**
	 * 创建文件
	 * 
	 * @param file
	 *            文件路径
	 * @return 被创建的文件
	 */
	public static File createFile(File file) {
		try {
			if (!file.exists()) {
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				file.createNewFile();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	/**
	 * 创建文件
	 * 
	 * @param fileName
	 *            文件路径
	 * @return 被创建的文件
	 */
	public static File createFile(String fileName) {
		File file = null;
		if (!StringUtils.isEmpty(fileName)) {
			file = new File(fileName);// 创建文件对象
			file = createFile(file);
		}
		return file;
	}

	/**
	 * 流式写文件
	 * 
	 * @param is
	 *            输入流
	 * @param fos
	 *            输出流
	 * @param bufferSize
	 *            缓冲大小
	 * @throws IOException
	 */
	public static void writeFileWithBuffer(InputStream is, OutputStream fos, int bufferSize) throws IOException {
		byte[] buf = new byte[bufferSize];
		int len;
		while ((len = is.read(buf)) > 0) {
			fos.write(buf, 0, len);
		}
	}

	/**
	 * 写文件
	 * 
	 * @param file
	 *            文件
	 * @param content
	 *            文件内容
	 * @param append
	 *            追加
	 */
	public static void writeFile(File file, String content, boolean append) {
		FileOutputStream out = null;
		try {
			if (!file.exists()) {
				createFile(file);
			}
			out = new FileOutputStream(file, append);
			if (!"".equals(content)) {
				StringBuffer sb = new StringBuffer();
				sb.append(content);
				out.write(sb.toString().getBytes("utf-8"));
				out.flush();
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtil.closeQuietly(out);
		}
	}

	/**
	 * 写文件
	 * 
	 * @param fileName
	 *            文件名
	 * @param content
	 *            文件内容
	 * @param append
	 *            追加
	 */
	public static void writeFile(String fileName, String content, boolean append) {
		if (!StringUtils.isEmpty(fileName)) {
			File file = createFile(fileName);// 调用方法创建文件
			writeFile(file, content, append);
		}
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @param fileName
	 *            文件名
	 * @return
	 */
	public static String getExtensionName(String fileName) {
		String ext = "";
		if (null != fileName && !"".equals(fileName)) {
			int dot = fileName.lastIndexOf('.');
			if (dot > -1 && dot < (fileName.length() - 1)) {
				ext = fileName.substring(dot + 1);
			}
		}
		return ext;
	}

	/**
	 * 获取不带扩展名的文件名
	 * 
	 * @param fileName
	 *            文件名
	 * @return
	 */
	public static String getFileNameWithoutExtension(String fileName) {
		String file_name = "";
		if (null != fileName && !"".equals(fileName)) {
			int dot = fileName.lastIndexOf('.');
			if (dot > -1 && dot < fileName.length()) {
				file_name = fileName.substring(0, dot);
			}
		}
		return file_name;
	}

	/**
	 * 获取文件夹内文件列表
	 * 
	 * @param filePath
	 *            文件路径
	 * @param extension
	 *            扩展名
	 * @return
	 */
	public static List<File> listFiles(String filePath, String extension) {
		List<File> fileList = new ArrayList<File>();// 文件存储列表
		if (!StringUtils.isEmpty(filePath)) {
			File directory = new File(filePath);// 文件目录
			if (directory.isDirectory()) {
				File[] files = directory.listFiles();// 列出文件名
				for (File file : files) {
					if (!file.isDirectory()) {
						// String extensionName =
						// getExtensionName(file.getName());
						// if
						// (extensionName.equalsIgnoreCase(extension.trim().toLowerCase()))
						// {
						// fileList.add(file);
						// }
						if (file.getAbsolutePath().endsWith(extension)) {
							fileList.add(file);
						}
					}
				}
				files = null;
			}
		}
		return fileList;
	}

	/**
	 * 获取指定文件夹下某种后缀的文件个数
	 * 
	 * @param filePath
	 * @param extension
	 * @return
	 */
	public static int getFilesSum(String filePath, String extension) {
		int sum = 0;
		if (!StringUtils.isEmpty(filePath)) {
			File directory = new File(filePath);// 文件目录
			if (directory.isDirectory()) {
				File[] fileList = directory.listFiles(new FilenameFilter() {
					@Override
					public boolean accept(File dir, String name) {
						if (name.endsWith(extension)) {
							return true;
						}
						return false;
					}
				});
				sum = null == fileList ? 0 : fileList.length;
				fileList = null;
			}
		}
		return sum;
	}

	/**
	 * 获取指定文件夹下所有文件个数
	 * 
	 * @param filePath
	 * @return
	 */
	public static int getFilesSum(String filePath) {
		return getFilesSum(filePath, "");
	}

	/**
	 * 获取指定文件夹下指定后缀的文件个数
	 * 
	 * @param folder
	 * @param extension
	 * @return
	 */
	public static int getFilesSum(File folder, String extension) {
		int sum = 0;
		if (null != folder && folder.isDirectory()) {
			File[] fileList = folder.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					if (name.endsWith(extension)) {
						return true;
					}
					return false;
				}
			});
			sum = null == fileList ? 0 : fileList.length;
			fileList = null;
		}

		return sum;
	}

	/**
	 * 获取指定文件夹下所有文件个数
	 * 
	 * @param folder
	 * @return
	 */
	public static int getFilesSum(File folder) {
		return getFilesSum(folder, "");
	}

	/**
	 * 以字节为单位读取文件,一次读取一个字节
	 * <p>
	 * 常用于读二进制文件，如图片、声音、影像等文件。
	 * 
	 * @param fileName
	 *            文件的名
	 */
	public static void readFileByBytes(String fileName) {
		System.out.println("以字节为单位读取文件内容，一次读一个字节：");
		InputStream is = null;
		try {
			if (!StringUtils.isEmpty(fileName)) {
				File file = new File(fileName);
				is = new FileInputStream(file);
				int temp;
				while ((temp = is.read()) != -1) {
					System.out.println(temp);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtil.closeQuietly(is);
		}
	}

	/**
	 * 以字节为单位读取文件,一次读取多个字节
	 * <p>
	 * 常用于读二进制文件，如图片、声音、影像等文件。
	 * 
	 * @param file
	 *            文件
	 * @param number
	 *            一次读取的字节数
	 */
	public static byte[] readFileByBytes(File file, int number) {
		InputStream is = null;
		byte[] temp = new byte[number];
		try {
			if (null != file && file.length() > 0) {
				is = new FileInputStream(file);
				System.out.println("文件：" + file.getName());
				while ((is.read(temp)) != -1) {
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtil.closeQuietly(is);
		}
		return temp;
	}

	/**
	 * 字节转换为浮点
	 * 
	 * @param b
	 *            字节（至少4个字节）
	 * @param index
	 *            开始位置
	 * @return
	 */
	public static float byte2float(byte[] b, int index) {
		int l;
		l = b[index + 0];
		l &= 0xff;
		l |= ((long) b[index + 1] << 8);
		l &= 0xffff;
		l |= ((long) b[index + 2] << 16);
		l &= 0xffffff;
		l |= ((long) b[index + 3] << 24);
		return Float.intBitsToFloat(l);
	}

	/**
	 * 以字节为单位读取文件,一次读取多个字节
	 * <p>
	 * 常用于读二进制文件，如图片、声音、影像等文件。
	 * 
	 * @param fileName
	 *            文件的名
	 * @param number
	 *            一次读取的字节数
	 */
	public static byte[] readFileByBytes(String fileName, int number) {
		File file = null;
		if (!StringUtils.isEmpty(fileName)) {
			file = new File(fileName);
		}
		return readFileByBytes(file, number);
	}

	/**
	 * 以字符为单位读取文件<br>
	 * 常用于读文本，数字等类型的文件
	 * 
	 * @param file
	 *            文件名
	 * @param encode
	 *            编码
	 */
	public static void readFileByChars(File file, String encode) {
		System.out.println("以字符为单位读取文件内容，一次读一个字节：");
		Reader reader = null;
		InputStream is = null;
		try {
			if (null != file && file.length() > 0) {
				is = new FileInputStream(file);
				reader = new InputStreamReader(is, encode);
				int tempchar;
				while ((tempchar = reader.read()) != -1) {
					// 对于windows下，rn这两个字符在一起时，表示一个换行。
					// 但如果这两个字符分开显示时，会换两次行。
					// 因此，屏蔽掉r，或者屏蔽n。否则，将会多出很多空行。
					if (((char) tempchar) != 'r') {
						System.out.print((char) tempchar);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtil.closeQuietly(reader, is);
		}
	}

	/**
	 * 以字符为单位读取文件<br>
	 * 常用于读文本，数字等类型的文件
	 * 
	 * @param fileName
	 *            文件名
	 * @param encode
	 *            编码
	 */
	public static void readFileByChars(String fileName, String encode) {
		System.out.println("以字符为单位读取文件内容，一次读一个字节：");
		if (!StringUtils.isEmpty(fileName)) {
			File file = new File(fileName);
			readFileByChars(file, encode);
		}
	}

	/**
	 * 以字符为单位读取文件,一次读多个字符<br>
	 * 常用于读文本，数字等类型的文件
	 * 
	 * @param fileName
	 *            文件名
	 * @param number
	 *            读取的字符数
	 * @param encode
	 *            编码
	 */
	public static void readFileByChars(String fileName, int number, String encode) {
		System.out.println("以字符为单位读取文件内容，一次读多个字节：");
		Reader reader = null;
		InputStream is = null;
		try {
			if (!StringUtils.isEmpty(fileName)) {
				File file = new File(fileName);
				is = new FileInputStream(file);
				reader = new InputStreamReader(is, encode);
				char[] tempchars = new char[number];// 一次读多个字符
				int num = 0;
				// 读入多个字符到字符数组中，num为一次读取字符数
				while ((num = reader.read(tempchars)) != -1) {
					// 同样屏蔽掉r不显示
					if ((num == tempchars.length) && (tempchars[tempchars.length - 1] != 'r')) {
						System.out.print(tempchars);
					} else {
						for (int i = 0; i < num; i++) {
							if (tempchars[i] != 'r') {
								System.out.print(tempchars[i]);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtil.closeQuietly(reader, is);
		}
	}

	/**
	 * 按编码读文件
	 * 
	 * @param fileName
	 *            文件名
	 * @param encode
	 *            编码
	 * @return
	 */
	public static List<String> readFileWithSpecificEncode(String fileName, String encode) {
		List<String> lines = null;
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			lines = new ArrayList<String>();
			fis = new FileInputStream(fileName);
			isr = new InputStreamReader(fis, encode);
			br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtil.closeQuietly(br, isr, fis);
		}
		return lines;
	}

	/**
	 * 以行为单位读取文件<br>
	 * 常用于读面向行的格式化文件
	 * 
	 * @param fileName
	 *            文件名
	 * @return
	 */
	public static List<String> readFileByLines(String fileName) {
		System.out.println("以行为单位读取文件内容，一次读一整行：");
		List<String> lineLists = null;
		BufferedReader bufferedReader = null;
		FileReader fileReader = null;
		try {
			if (!StringUtils.isEmpty(fileName)) {
				File file = new File(fileName);
				fileReader = new FileReader(file);
				bufferedReader = new BufferedReader(fileReader);
				String temp = null;
				lineLists = new ArrayList<String>();
				// 一次读入一行，直到读入null为文件结束
				while ((temp = bufferedReader.readLine()) != null) {
					lineLists.add(temp);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtil.closeQuietly(bufferedReader, fileReader);
		}
		return lineLists;
	}

	/**
	 * 替换文件中某行值
	 * 
	 * @param fileName
	 *            文件
	 * @param reglex
	 *            被替换行的前一行正则匹配
	 * @param value
	 *            替换内容
	 */
	public static void replaceSpecificLineValue(String fileName, String regex, String value) {
		RandomAccessFile randomFile = null;
		try {
			if (!StringUtils.isEmpty(fileName)) {
				randomFile = new RandomAccessFile(fileName, "rw");// 打开一个随机访问文件流
				String line = "";
				while ((line = randomFile.readLine()) != null) {
					if (line.startsWith(regex)) {
						randomFile.writeBytes(value);
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtil.closeQuietly(randomFile);
		}
	}

	/**
	 * 显示输入流中还剩的字节数
	 *
	 * @param in
	 */
	private static int showAvailableBytes(InputStream in) {
		int count = 0;
		try {
			count = in.available();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 创建目录
	 * 
	 * @param folderPath
	 */
	public static void createFolder(File pathFile) {
		if (!pathFile.exists() || !pathFile.isDirectory()) {
			pathFile.mkdirs();
		}
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param file
	 * @return
	 */
	public static boolean fileExist(String fileName) {
		boolean flag = false;
		if (!StringUtils.isEmpty(fileName)) {
			File file = new File(fileName);
			flag = fileExist(file);
		}
		return flag;
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param file
	 * @return
	 */
	public static boolean fileExist(File file) {
		boolean flag = false;
		if (null != file && file.exists() && file.isFile()) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 创建目录
	 * 
	 * @param folderPath
	 */
	public static void createFolder(String folderPath) {
		if (!StringUtils.isEmpty(folderPath)) {
			File pathFile = new File(folderPath);
			createFolder(pathFile);
		}
	}

	/**
	 * 文件写到输出流
	 * 
	 * @param file
	 *            文件
	 * @param os
	 *            输出流
	 */
	public static void file2OutputSteam(File file, OutputStream os) {
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		try {
			if (file.exists()) {
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				byte[] buf = new byte[1024];
				int len = 0;
				while ((len = bis.read(buf)) > 0) {
					os.write(buf, 0, len);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtil.closeQuietly(bis, fis);
		}
	}

	/**
	 * 判断文件夹是否以斜杠结尾，没有加入斜杠
	 * 
	 * @param filePath
	 * @return
	 */
	public static String setPathEndWithSlash(String filePath) {
		if (!isDirectoryEndWithSlash(filePath)) {
			filePath = filePath + "/";
		}
		return filePath;
	}

	/**
	 * 判断目录是否以斜杠结尾
	 * 
	 * @param path
	 *            目录名
	 * @return
	 */
	public static boolean isDirectoryEndWithSlash(String path) {
		return path.endsWith("/") || path.endsWith("\\");
	}

	/**
	 * 递归删除目录下所有文件,但保留目录
	 * 
	 * @param fileName
	 *            将要删除的文件目录
	 * @return
	 */
	public static boolean deleteFilesInDir(String fileName) {
		File file = null;
		if (!StringUtils.isEmpty(fileName)) {
			file = new File(fileName);
		}
		return deleteFilesInDir(file);
	}

	/**
	 * 递归删除目录下的所有文件及子目录下所有文件
	 * 
	 * @param fileName
	 *            将要删除的文件目录
	 * @return
	 */
	public static boolean deleteDir(String fileName) {
		File file = null;
		if (!StringUtils.isEmpty(fileName)) {
			file = new File(fileName);
		}
		return deleteDir(file);
	}

	/**
	 * 递归删除目录下所有文件,但保留目录
	 * 
	 * @param directory
	 *            将要处理的文件目录
	 * @return
	 */
	public static boolean deleteFilesInDir(File directory) {
		boolean flag = false;
		if (null != directory && directory.isDirectory()) {
			String[] children = directory.list();
			// 递归删除目录中的子目录下
			for (int i = 0; i < children.length; i++) {
				flag = deleteFilesInDir(new File(directory, children[i]));
			}
		}
		if (directory.isFile()) {
			flag = directory.delete();
		}
		return flag;
	}

	/**
	 * 递归删除目录及目录下所有文件
	 * 
	 * @param directory
	 *            将要删除的文件目录
	 * @return
	 */
	public static boolean deleteDir(File directory) {
		if (null != directory && directory.isDirectory()) {
			String[] children = directory.list();
			// 递归删除目录中的子目录下
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(directory, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return directory.delete();
	}

	/**
	 * 
	 * @param filePath
	 *            文件目录
	 * @return
	 * @throws IOException
	 */
	public byte[] getContent(String filePath) throws IOException {
		File file = null;
		if (!StringUtils.isEmpty(filePath)) {
			file = new File(filePath);
		}

		long fileSize = file.length();
		if (fileSize > Integer.MAX_VALUE) {
			System.out.println("file too big...");
			return null;
		}

		FileInputStream fis = new FileInputStream(file);
		byte[] buffer = new byte[(int) fileSize];
		int offset = 0;
		int numRead = 0;
		while (offset < buffer.length && (numRead = fis.read(buffer, offset, buffer.length - offset)) >= 0) {
			offset += numRead;
		}
		// 确保所有数据均被读取
		if (offset != buffer.length) {
			IOUtil.closeQuietly(fis);
			throw new IOException("Could not completely read file " + file.getName());
		}
		IOUtil.closeQuietly(fis);
		return buffer;
	}

	/**
	 * the traditional io way读取二进制文件
	 * 
	 * @param fileName
	 *            二进制文件名
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByteArray(String fileName) {
		File file = null;
		if (!StringUtils.isEmpty(fileName)) {
			file = new File(fileName);
		}
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		ByteArrayOutputStream baos = null;
		if (file.exists()) {
			try {
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				baos = new ByteArrayOutputStream((int) file.length());
				int buf_size = 1024;
				byte[] buffer = new byte[buf_size];
				int len = 0;
				while (-1 != (len = bis.read(buffer, 0, buf_size))) {
					baos.write(buffer, 0, len);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				IOUtil.closeQuietly(baos, bis, fis);
			}
		}
		return baos.toByteArray();
	}

	/**
	 * NIO way读取二进制文件
	 * <p>
	 * 
	 * @param fileName
	 *            二进制文件名
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByteArrayWithNIO(String fileName) {
		File file = null;
		if (!StringUtils.isEmpty(fileName)) {
			file = new File(fileName);
		}
		FileInputStream fis = null;
		FileChannel fileChannel = null;
		ByteBuffer byteBuffer = null;
		if (file.exists()) {
			try {
				fis = new FileInputStream(file);
				fileChannel = fis.getChannel();
				byteBuffer = ByteBuffer.allocate((int) fileChannel.size());
				while ((fileChannel.read(byteBuffer)) > 0) {
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				IOUtil.closeQuietly(fileChannel, fis);
			}
		}
		return byteBuffer.array();
	}

	/**
	 * 读取二进制文件
	 * <p>
	 * Mapped File way MappedByteBuffer 可以在处理大文件时，提升性能
	 * 
	 * @param fileName
	 *            二进制文件名
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByteArray4LargeFile(String fileName) {
		RandomAccessFile rac = null;
		FileChannel fileChannel = null;
		byte[] bytes = null;
		try {
			rac = new RandomAccessFile(fileName, "r");
			fileChannel = rac.getChannel();
			MappedByteBuffer byteBuffer = fileChannel.map(MapMode.READ_ONLY, 0, fileChannel.size()).load();
			// System.out.println(byteBuffer.isLoaded());
			bytes = new byte[(int) fileChannel.size()];
			if (byteBuffer.remaining() > 0) {
				byteBuffer.get(bytes, 0, byteBuffer.remaining());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtil.closeQuietly(fileChannel, rac);
		}
		return bytes;
	}

	/**
	 * 获取文件最后修改时间
	 * 
	 * @param fileName
	 *            文件名
	 * @return 毫秒数
	 */
	public static long getFileLastModifyTime(String fileName) {
		File file = new File(fileName);
		return getFileLastModifyTime(file);
	}

	/**
	 * 获取文件最后修改时间
	 * 
	 * @param file
	 *            文件
	 * @return 毫秒数
	 */
	public static long getFileLastModifyTime(File file) {
		long lastModifiedTime = 0;
		if (null != file) {
			lastModifiedTime = file.lastModified();
		}
		return lastModifiedTime;
	}

	/**
	 * 获取文件最后修改时间
	 * 
	 * @param file
	 *            文件
	 * @param pattern
	 *            格式，例如"yyyy-MM-dd HH:mm:ss"
	 * 
	 * @return 日期字符串
	 */
	public static String getFileModifyDate(File file, String pattern) {
		return new SimpleDateFormat(pattern).format(new Date(getFileLastModifyTime(file)));
	}

	/**
	 * 获取文件夹内的文件夹
	 * 
	 * @param directory
	 *            文件夹目录
	 */
	public static void deleteOldFilesInFolders(String directory, String subFolder, int hours) {
		if (!StringUtils.isEmpty(directory)) {
			deleteOldFilesInFolders(new File(directory), subFolder, hours);
		}
	}

	/**
	 * 获取文件夹内的文件夹
	 * 
	 * @param directory
	 *            文件夹目录
	 */
	public static void deleteOldFilesInFolders(File directory, String subFolder, int hours) {
		if (directory.isDirectory()) {
			File[] files = directory.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					deleteOldFilesInFolders(file, subFolder, hours);
					if (file.getAbsolutePath().endsWith(subFolder.trim())) {
						FileUtil.deleteOldFilesInFolders(file.getAbsolutePath(), hours);
					}
				}
			}
			files = null;
		}
	}

	/**
	 * 递归删除目录下所有文件,但保留目录
	 * 
	 * @param fileName
	 *            将要删除的文件目录
	 * @param hours
	 *            最后修改时间超过的小时数
	 * @return
	 */
	public static boolean deleteOldFilesInFolders(String fileName, int hourts) {
		File file = null;
		if (!StringUtils.isEmpty(fileName)) {
			file = new File(fileName);
		}
		return deleteOldFilesInFolders(file, hourts);
	}

	/**
	 * 递归删除目录下所有的文件最后修改日期到现在，超过指定小时数的文件,但保留目录
	 * 
	 * @param directory
	 *            将要处理的文件目录
	 * @param hours
	 *            最后修改时间超过的小时数
	 * @return
	 */
	public static boolean deleteOldFilesInFolders(File directory, Integer hours) {
		boolean flag = false;
		if (null != directory && directory.isDirectory()) {
			String[] children = directory.list();
			// 递归删除目录中的子目录下
			for (int i = 0; i < children.length; i++) {
				flag = deleteOldFilesInFolders(new File(directory, children[i]), hours);
			}
		}
		if (directory.isFile()) {
			if (DateUtil.pastHours(getFileLastModifyTime(directory)) > hours) {
				System.err.println("file <" + directory.getAbsolutePath() + "> was modified at " + getFileModifyDate(directory, "yyyy-MM-dd HH:mm:ss") + " deleted successfully!");// DateUtil.pastHours(getFileLastModifyTime(directory)));//getFileModifyDate
				flag = directory.delete();
			}
		}
		return flag;
	}

	/**
	 * 统计文件或文件夹内所有文件大小
	 * 
	 * @param file
	 *            文件夹或文件
	 * @return 大小,单位为M
	 */
	public static double getDirSize(File file) {
		if (null != file && file.exists()) {
			double size = 0;
			// 如果是目录则递归计算其内容的总大小
			if (file.isDirectory()) {
				File[] children = file.listFiles();
				for (File f : children) {
					size += getDirSize(f);
				}
			} else {
				size = (double) file.length() / 1024 / 1024;
			}
			return size;
		} else {
			// System.out.println("文件或者文件夹不存在，请检查路径是否正确！");
			return 0.0;
		}
	}

	/**
	 * 清空已有的文件内容，以便下次重新写入新的内容
	 * 
	 * @param fileName
	 *            文件名（含路径）
	 */
	public static void clearInfoForFile(String fileName) {
		clearInfoForFile(new File(fileName));
	}

	/**
	 * 清空已有的文件内容，以便下次重新写入新的内容
	 * 
	 * @param File
	 *            文件
	 */
	public static void clearInfoForFile(File file) {
		FileWriter fileWriter = null;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			fileWriter = new FileWriter(file);
			fileWriter.write("");
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtil.closeQuietly(fileWriter);
		}
	}
}