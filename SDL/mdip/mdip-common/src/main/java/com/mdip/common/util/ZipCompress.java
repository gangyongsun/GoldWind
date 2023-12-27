package com.mdip.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.alibaba.druid.util.StringUtils;

/**
 * zip压缩解压缩
 * 
 * @author alvinsun
 *
 */
public class ZipCompress {

	/**
	 * 压缩文件或文件夹
	 * 
	 * @param sourceFileName
	 *            待压缩的文件或文件夹
	 * @param zipFileName
	 *            目的zip文件
	 */
	public static void zip(String sourceFileName, String zipFileName) {
		ZipOutputStream zos = null;
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(zipFileName);
			zos = new ZipOutputStream(fos); // 创建zip输出流
			bos = new BufferedOutputStream(zos);// 创建缓冲输出流
			File sourceFile = new File(sourceFileName);
			compress(zos, bos, sourceFile, sourceFile.getName());// 方法调用
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			IOUtil.closeQuietly(bos, zos, fos);
		}
	}

	/**
	 * 压缩文件
	 * 
	 * @param zos
	 * @param bos
	 * @param sourceFile
	 *            待压缩的文件或文件夹
	 * @param base
	 */
	private static void compress(ZipOutputStream zos, BufferedOutputStream bos, File sourceFile, String base) {
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		try {
			if (sourceFile.isDirectory()) {
				File[] flist = sourceFile.listFiles();// 取出文件夹中的文件（或子文件夹）
				if (flist.length == 0) {
					// 如果文件夹为空，则只需在目的地zip文件中写入一个目录进入点
					zos.putNextEntry(new ZipEntry(base + "/"));
					System.out.println(base + "/");
				}
				// 如果文件夹不为空，则递归调用compress，文件夹中的每一个文件（或文件夹）进行压缩
				for (int i = 0; i < flist.length; i++) {
					compress(zos, bos, flist[i], base + "/" + flist[i].getName());
				}
			} else {
				// 如果不是目录，则先写入目录进入点，之后将文件写入zip文件中
				// System.out.println("========" + base);
				zos.putNextEntry(new ZipEntry(base));
				fis = new FileInputStream(sourceFile);
				bis = new BufferedInputStream(fis);
				int count, bufferLen = 1024;
				byte data[] = new byte[bufferLen];
				while ((count = bis.read(data, 0, bufferLen)) != -1) {
					zos.write(data, 0, count);// 将源文件写入到zip文件中
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
	 * 解压文件
	 * 
	 * @param zipFileName
	 *            压缩文件路径
	 */
	public static void unzip(String zipFileName) {
		InputStream is = null;
		ZipInputStream zis = null;
		BufferedOutputStream bos = null;
		OutputStream os = null;
		try {
			if (!StringUtils.isEmpty(zipFileName)) {
				File zipFile = new File(zipFileName);
				if (zipFile.exists()) {
					System.out.println("zip文件已存在，名为：" + zipFile.getName());
					is = new FileInputStream(zipFile);
					zis = new ZipInputStream(is);
					ZipEntry entry = null;
					while ((entry = zis.getNextEntry()) != null) {
						System.out.println("entry:" + entry.getName());
						if (!entry.isDirectory()) {
							File file = new File(zipFile.getParent(), entry.getName());
							if (!file.getParentFile().exists()) {
								file.getParentFile().mkdirs();// 创建文件父目录
							}
							// 写入文件
							os = new FileOutputStream(file);
							bos = new BufferedOutputStream(os);
							int read = 0;
							byte[] buffer = new byte[1024 * 10];
							while ((read = zis.read(buffer, 0, buffer.length)) != -1) {
								bos.write(buffer, 0, read);
							}
							bos.flush();
						}
					}
					zis.closeEntry();
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			IOUtil.closeQuietly(bos, os, zis, is);
		}
	}

	/**
	 * 解压文件
	 * 
	 * @param zipFileName
	 *            压缩文件路径
	 */
	public static void unzip(File zipFile) {
		InputStream is = null;
		ZipInputStream zis = null;
		BufferedOutputStream bos = null;
		OutputStream os = null;
		try {
			if (null != zipFile && zipFile.exists()) {
				is = new FileInputStream(zipFile);
				zis = new ZipInputStream(is);
				ZipEntry entry = null;
				while ((entry = zis.getNextEntry()) != null) {
					System.out.println("entry" + entry.getName());
					if (!entry.isDirectory()) {
						File dstFile = new File(zipFile.getParent(), entry.getName());
						if (!dstFile.getParentFile().exists()) {
							dstFile.getParentFile().mkdirs();// 创建文件父目录
						}
						// 写入文件
						os = new FileOutputStream(dstFile);
						bos = new BufferedOutputStream(os);
						int read = 0;
						byte[] buffer = new byte[1024 * 16];
						while ((read = zis.read(buffer, 0, buffer.length)) != -1) {
							bos.write(buffer, 0, read);
						}
						bos.flush();
						IOUtil.closeQuietly(bos, os);
					}
				}
				zis.closeEntry();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			IOUtil.closeQuietly(bos, os, zis, is);
		}
	}

	/**
	 * 解压文件到指定目录，解压后的文件名和之前一致
	 * 
	 * @param zipFile
	 *            待解压的zip文件
	 * @param destDir
	 *            指定目录
	 */
	public static void unzip(File zipFile, String destDir) {
		FileUtil.createFolder(destDir);// 指定目录不存在就创建
		InputStream is = null;
		OutputStream os = null;
		ZipFile zip = null;
		try {
			if (null != zipFile && zipFile.exists()) {
				zip = new ZipFile(zipFile, Charset.forName("GBK"));// 解决中文文件夹乱码
				Enumeration<? extends ZipEntry> entries = zip.entries();
				while (entries.hasMoreElements()) {
					ZipEntry zipEntry = (ZipEntry) entries.nextElement();
					// 判断是否为文件夹
					if (!zipEntry.isDirectory()) {
						is = zip.getInputStream(zipEntry);
						String entryName;
						if (FileUtil.isDirectoryEndWithSlash(destDir)) {
							entryName = destDir + zipEntry.getName();
						} else {
							entryName = destDir + "/" + zipEntry.getName();
						}
						os = new FileOutputStream(entryName);
						byte[] buf = new byte[1024];
						int len;
						while ((len = is.read(buf)) > 0) {
							os.write(buf, 0, len);
						}
						os.flush();
					}
					IOUtil.closeQuietly(os, is);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtil.closeQuietly(os, is, zip);
		}
	}

	/**
	 * 解压缩zip包
	 * 
	 * @param zipFile
	 *            zip文件的全路径
	 * @param destPath
	 *            解压后的文件保存的路径
	 * @param includeZipFileName
	 *            解压后的文件保存的路径是否包含压缩文件的文件名。true-包含；false-不包含
	 */
	public static void unzip(File zipFile, String destPath, boolean includeZipFileName) {
		// 如果解压后的文件保存路径包含压缩文件的文件名，则追加该文件名到解压路径
		if (includeZipFileName) {
			String fileName = zipFile.getName();
			if (!StringUtils.isEmpty(fileName)) {
				fileName = fileName.substring(0, fileName.lastIndexOf("."));
			}
			destPath = destPath + "/" + fileName;
		}
		// 创建解压缩文件保存的路径
		File destPathDir = new File(destPath);
		FileUtil.createFolder(destPathDir);

		// 开始解压
		ZipFile zip = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			if (null != zipFile && zipFile.exists()) {
				zip = new ZipFile(zipFile, Charset.forName("GBK"));
			}
			Enumeration<? extends ZipEntry> entries =zip.entries();
			// 循环对压缩包里的每一个文件进行解压
			ZipEntry entry = null;
			String entryFilePath = null;
			String entryDirPath = null;
			File entryFile = null;
			File entryDir = null;
			int index = 0;
			int count = 0;
			int bufferSize = 1024;
			byte[] buffer = new byte[bufferSize];
			while (entries.hasMoreElements()) {
				entry = entries.nextElement();
				entryFilePath = destPath + File.separator + entry.getName();// 构建压缩包中一个文件解压后保存的文件全路径
				index = entryFilePath.lastIndexOf(File.separator);// 构建解压后保存的文件夹路径
				if (index != -1) {
					entryDirPath = entryFilePath.substring(0, index);
				} else {
					entryDirPath = "";
				}
				// 如果文件夹路径不存在，则创建文件夹
				entryDir = new File(entryDirPath);
				FileUtil.createFolder(entryDir);

				// 创建解压文件
				entryFile = new File(entryFilePath);
				if (entryFile.exists()) {
					// 检测文件是否允许删除，如果不允许删除，将会抛出SecurityException
					SecurityManager securityManager = new SecurityManager();
					securityManager.checkDelete(entryFilePath);
					entryFile.delete();// 删除已存在的目标文件
				}

				// 写入文件
				bos = new BufferedOutputStream(new FileOutputStream(entryFile));
				bis = new BufferedInputStream(zip.getInputStream(entry));
				while ((count = bis.read(buffer, 0, bufferSize)) != -1) {
					bos.write(buffer, 0, count);
				}
				bos.flush();
				IOUtil.closeQuietly(bos, bis);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtil.closeQuietly(bos, bis, zip);
		}
	}

}
