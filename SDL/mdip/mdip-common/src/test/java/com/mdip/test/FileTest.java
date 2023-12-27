package com.mdip.test;

import java.io.File;
import java.text.DecimalFormat;

import org.junit.Test;

import com.mdip.common.util.FileUtil;

/**
 * 文件测试类
 * 
 * @author alvinsun
 *
 */
public class FileTest {
	//
	// @Test
	// public void testFileModifyTime() {
	// File file = new File("D:/tmp/KettleDir/ddd/jobs/kettle.properties");
	// long lastModifiedTime = FileUtil.getFileLastModifyTime(file);
	// System.err.println(lastModifiedTime);
	// String pattern = "yyyy-MM-dd HH:mm:ss";
	// System.out.println(FileUtil.getFileModifyDate(file, pattern));
	// }
	//
	// @Test
	// public void testReadFileWithSpecificEncode() {
	// String fileName = "D:/煤/矿压/矿压数据库建表语句和数据/shouli.txt";
	// List<String> lineLists = FileUtil.readFileWithSpecificEncode(fileName,
	// "GBK");
	// for (String line : lineLists) {
	// System.out.println(line);
	// }
	// }
	//
	// @Test
	// public void testReadFile() {
	// String fileName = "D:/煤/矿压/矿压数据库建表语句和数据/shouli.txt";
	// FileUtil.readFileByChars(fileName, 30, "GBK");
	// }
	//
	// @Test
	// public void testReadBinaryFile() {
	// String fileName = "Z:\\chris\\PRON\\20170724D.REC";
	// List<List<Float>> superList =
	// OutputBusiness.getHistoryOutputList(fileName);
	//
	// for (int i = 0; i < superList.size(); i++) {
	// System.out.print("皮带：" + i + " :");
	// for (int j = 0; j < superList.get(i).size(); j++) {
	// System.out.print(" 班次：" + j + "：");
	// System.out.printf("%-9.2f", superList.get(i).get(j));
	// }
	// System.out.println();
	// }
	// }
	//
	// @Test
	// public void testListFiles() {
	// // String directory = "D:/煤/产量监控/增矿/RECORD/";
	// String directory = "D:/煤/产量监控/产量监控数据/record/record/";
	// List<File> fileList = FileUtil.listFiles(directory, "D.REC");
	// List<List<Float>> shiftLists = new ArrayList<List<Float>>();
	// for (int i = 0; i < fileList.size(); i++) {
	// System.out.println("第 " + i + " 个文件");
	// shiftLists =
	// OutputBusiness.getHistoryOutputList(fileList.get(i).getAbsolutePath());
	// System.out.println("文件名：" + fileList.get(i).getAbsolutePath());
	// for (int j = 0; j < shiftLists.size(); j++) {
	// System.out.print("皮带：" + j + " :");
	// for (int o = 0; o < shiftLists.get(j).size(); o++) {
	// System.out.print(" 班次：" + o + "：");
	// System.out.printf("%-9.2f", shiftLists.get(j).get(o));
	// }
	// System.out.println();
	// }
	// }
	// }
	//
	// @Test
	// public void testZip() {
	// String zipFileName = "D://煤//testdir//jobs.zip";
	// String sourceFileName = "D://煤//testdir//jobs";
	// ZipCompress.zip(sourceFileName, zipFileName);
	// }
	//
	// @Test
	// public void testUnzip() {
	// String zipFileName = "D:/uploadTestFolder/rtdata.zip";
	// ZipCompress.unzip(zipFileName);
	// }
	//
	// @Test
	// public void testCopyAndUnzipFile() {
	// File srcFile = new File("C://jobs.zip");
	// File dstFile = FileUtil.createFile("D://uploadTestFolder//jobs.zip");//
	// 目的文件
	// FileUtil.copyFile(srcFile, dstFile);// 拷贝文件到目的文件
	// ZipCompress.unzip(dstFile);// 解压文件
	// }
	//
	// @Test
	// public void testGetExt() {
	// String zipFileName = "jobs.zip";
	// String ext = FileUtil.getFileNameWithoutExtension(zipFileName);
	// System.out.println(ext);
	// }
	//
	// @Test
	// public void testCreateFolder() {
	// FileUtil.createFolder("D:/uploadTestFolder/a/b/c/d/e/f/g");
	// }
	//
	// /**
	// * 解压到指定目录
	// *
	// * @param zipPath
	// * @param descDir
	// */
	// @Test
	// public void unZipFiles() {
	// String zipFileName = "D:/uploadTestFolderA/jobs.zip";
	// String descDir = "D:/uploadTestFolder/kafkaOperation";
	//
	// ZipCompress.unzip(new File(zipFileName), descDir);
	// }
	//
	// /**
	// * 递归删除目录下所有超过指定小时数的文件,但保留目录
	// */
	// @Test
	// public void deleteOlderFilesInDir() {
	// FileUtil.deleteOldFilesInFolders("D:/logs", 168);
	// }
	//
	// /**
	// * 递归删除目录下所有文件,但保留目录
	// */
	// @Test
	// public void testDeleteFilesInDir() {
	// FileUtil.deleteFilesInDir("D:/logs");
	// }
	//
	// /**
	// * 递归删除目录及目录下的文件
	// */
	// @Test
	// public void testDeleteDir() {
	// FileUtil.deleteDir("D:/logs");
	// }
	//
	// @Test
	// public void testRandAccess() {
	// FileUtil.replaceSpecificLineValue("c:/kettle.properties",
	// "#kettleWorkingDirectory",
	// "kettle.workingdir=D:/lantrack/Tiefeng/KettleDir/");
	// }
	//
	// @Test
	// public void testNewUnzip() {
	// File zipfile = new
	// File("D:/uploadTestFolderA/zengkuang_product_etl.zip");
	// ZipCompress.unzip(zipfile, "D:/tmp/KettleDir/test", true);
	// }

//	@Test
//	public void testGetDirSize() {
//		File file = new File("D:/tmp/KettleDir/nankuang_product/log/");
//		double size=FileUtil.getDirSize(file);
//		System.out.println(new DecimalFormat("#.00").format(size));
//	}

}
