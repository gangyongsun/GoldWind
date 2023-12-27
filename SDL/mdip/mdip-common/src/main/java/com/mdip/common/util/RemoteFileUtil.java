package com.mdip.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;

/**
 * 操作远程共享文件夹类
 * 
 * @author alvinsun
 *
 */
public class RemoteFileUtil {
	private String remoteHostIp; // 远程主机IP
	private String account; // 登陆账户
	private String password; // 登陆密码
	private String shareDocName; // 共享文件夹名称
	private String config = "remotefile.properties";// 配置文件名称

	/**
	 * 默认构造函数
	 */
	public RemoteFileUtil() {
		this.remoteHostIp = PropertiesUtil.getValueByKey("REMOTE_HOST_IP", config);
		this.account = PropertiesUtil.getValueByKey("LOGIN_ACCOUNT", config);
		this.password = PropertiesUtil.getValueByKey("LOGIN_PASSWORD", config);
		this.shareDocName = PropertiesUtil.getValueByKey("SHARE_DOC_NAME", config);
	}

	/**
	 * 构造函数
	 * 
	 * @param remoteHostIp
	 *            远程主机Ip
	 * @param account
	 *            登陆账户
	 * @param password
	 *            登陆密码
	 * @param sharePath
	 *            共享文件夹路径
	 */
	public RemoteFileUtil(String remoteHostIp, String account, String password, String shareDocName) {
		this.remoteHostIp = remoteHostIp;
		this.account = account;
		this.password = password;
		this.shareDocName = shareDocName;
	}

	/**
	 * 对远程共享文件进行读取所有行
	 * 
	 * @param remoteFileName
	 *            文件名 说明：参数为共享目录下的相对路径
	 *            若远程文件的路径为：shareDoc\test.txt,则参数为test.txt(其中shareDoc为共享目录名称);
	 *            若远程文件的路径为：shareDoc\doc\text.txt,则参数为doc\text.txt;
	 * @return 文件的所有行
	 */
	public List<String> readFile(String remoteFileName) {
		SmbFile smbFile = null;
		BufferedReader reader = null;
		List<String> resultLines = null;
		try {
			// 构建连接字符串,并取得文件连接
			String conStr = "smb://" + account + ":" + password + "@" + remoteHostIp + "/" + shareDocName + "/"
					+ remoteFileName;

			smbFile = new SmbFile(conStr);
			reader = new BufferedReader(new InputStreamReader(new SmbFileInputStream(smbFile)));

			// 循环对文件进行读取
			String line = reader.readLine();
			if (line != null && line.length() > 0) {
				resultLines = new ArrayList<String>();
			}
			while (line != null) {
				resultLines.add(line);
				line = reader.readLine();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (SmbException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultLines;
	}

	/**
	 * 获取所有远程共享文件
	 * 
	 * @return 所有文件
	 */
	public List<String> getFiles() {
		List<String> fileList = null;
		try {
			String conStr = "smb://" + account + ":" + password + "@" + remoteHostIp + "/" + shareDocName + "/";
			SmbFile smbFile = new SmbFile(conStr);
			String[] files = smbFile.list();
			
			fileList = new ArrayList<String>();
			for (int i = 0; i < files.length; i++) {
				fileList.add(files[i]);
			}
		} catch (SmbException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileList;
	}

	/**
	 * 循环获取文件夹内所有匹配的文件
	 * 
	 * @param strPath
	 *            路径
	 * @param subStr
	 *            匹配字符
	 * @return
	 */
	public List<String> refreshFileList(String strPath, String subStr) {
		List<String> filelist = new ArrayList<String>();
		File dir = new File(strPath);
		if (dir.exists() && dir.isDirectory()) {
			File[] files = dir.listFiles();
			int numberOfFiles = files.length;
			if (numberOfFiles > 0) {
				for (int i = 0; i < numberOfFiles; i++) {
					if (!files[i].isDirectory()) {
						if (files[i].getName().indexOf(subStr) >= 0) {
							filelist.add(files[i].getName());
						}
					}
				}
			}
		}
		return filelist;
	}
}