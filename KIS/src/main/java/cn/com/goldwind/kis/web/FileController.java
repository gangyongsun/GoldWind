package cn.com.goldwind.kis.web;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.com.goldwind.kis.utils.FileUtil;

/**
 * 文件上传下载控制类
 * 
 * @author alvin
 *
 */
@Controller
@RequestMapping("/file")
public class FileController {
	private static Log log = LogFactory.getLog(FileController.class);
	/**
	 * 文件上传路径，以后换成文件服务器路径
	 */
	private static final String fileUploadPath = "/Users/alvin/Downloads/ProjectUploadFolder/";

	/**
	 * 初始页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "index")
	public ModelAndView index() {
		return new ModelAndView("fileViews/filesViews_index");
	}

	/**
	 * 上传文件页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "uploadFilePage")
	public ModelAndView uploadFilePage() {
		return new ModelAndView("fileViews/fileUpload");
	}

	/**
	 * 文件上传
	 * @param map
	 * @param files
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping("uploadFiles")
	public ModelAndView uploadFiles(ModelMap map, @RequestParam("files") MultipartFile[] files, String username, String password) {
		List<String> fileNames = new ArrayList<String>();
		// 判断file数组不能为空并且长度大于0
		if (files != null && files.length > 0) {
			// 循环获取file数组中得文件
			for (int i = 0; i < files.length; i++) {
				MultipartFile file = files[i];
				// 保存文件
				System.out.println("True&False : " + FileUtil.saveFile(fileUploadPath, file));
				fileNames.add(file.getOriginalFilename());
			}
		}
		log.info("2UserName:" + username);
		log.info("2Password:" + password);
		map.put("uploadPath", fileUploadPath);
		map.put("fileNames", fileNames);
		return new ModelAndView("fileViews/fileList");
	}

	/**
	 * 文件列表页面
	 * @param map
	 * @return
	 */
	@RequestMapping("filelist")
	public ModelAndView filelist(ModelMap map) {
		String[] fileNames = new File(fileUploadPath).list();
		List<String> filFullNames = new ArrayList<String>();
		for (String fileName : fileNames) {
			if (!fileName.startsWith(".")) {
				filFullNames.add(fileName);
			}
		}
		map.put("uploadPath", fileUploadPath);
		map.put("fileNames", filFullNames);
		return new ModelAndView("fileViews/filelist");
	}

	/**
	 * 文件下载
	 * 
	 * @param fileName
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("download")
	public void down(String[] fileNames, HttpServletResponse response) throws Exception {
		for (String fileName : fileNames) {
			log.info("fileName: " + fileUploadPath + fileName);
		}
		int fileNums = fileNames.length;
		if (fileNums > 1) {
			FileUtil.download(fileNames, fileUploadPath, response);// 多文件下载zip
		} else {
			FileUtil.download(fileNames[0], fileUploadPath, response);// 单文件下载
		}
	}

}