package com.mdip.web.biz.kettlemanage.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.pentaho.di.job.Job;
import org.pentaho.di.trans.Trans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mdip.common.util.DateUtil;
import com.mdip.common.util.FileUtil;
import com.mdip.common.util.PropertiesUtil;
import com.mdip.common.util.ZipCompress;
import com.mdip.web.biz.kettlemanage.entity.KettleJobEntity;
import com.mdip.web.biz.kettlemanage.service.IKettleService;
import com.mdip.web.biz.kettlemanage.service.impl.KettleServiceImpl;
import com.mdip.web.framework.base.cache.CacheUtils;
import com.mdip.web.framework.base.controller.BaseController;

/**
 * kettle作业调度Controller
 * 
 * @author yonggang
 *
 */
@Controller
@RequestMapping("/kettle")
public class KettleController extends BaseController {
	private static final String ConfigPropertyFile = "kettle.properties";// mdip-web项目中的Kettle配置文件名字
	private static final String JOBS_DIR = PropertiesUtil.getValueByKey("job.dir.jobs", ConfigPropertyFile);
	@Autowired
	@Qualifier("kettleService")
	IKettleService kettleService;

	/**
	 * 启动作业
	 * 
	 * @param jobId
	 *            作业ID
	 * @return
	 */
	@RequestMapping("/startJob")
	public @ResponseBody boolean startJobById(@RequestParam("jobId") String jobId) {
		String[] jobIds = jobId.split("&amp;");
		for (String id : jobIds) {
			if (!"".equals(id)) {
				kettleService.startJobById(id);
			}
		}
		return true;
	}

	/**
	 * 停止作业
	 * 
	 * @param jobId
	 *            作业ID
	 * @return
	 */
	@RequestMapping("/stopJob")
	public @ResponseBody boolean stopJobById(@RequestParam("jobId") String jobId) {
		String[] jobIds = jobId.split("&amp;");
		for (String id : jobIds) {
			if (!"".equals(id)) {
				kettleService.stopJobById(id);
			}
		}
		return true;
	}

	/**
	 * 删除Kettle作业(包括 :数据库、 作业文件)
	 * 
	 * @param jobId
	 *            作业id
	 * @param jobFileDir
	 *            作业存储目录
	 * @return
	 */
	@RequestMapping("/deleteJob")
	public @ResponseBody boolean deleteJob(@RequestParam("jobIds") String jobIds, @RequestParam("jobFileDirs") String jobFileDirs) {
		Map<String, String> jobIdDirMap = new HashMap<String, String>();
		String[] jobFileDirsMapSplitWithDoubleColon = jobFileDirs.split(";;");
		for (String item : jobFileDirsMapSplitWithDoubleColon) {
			String[] jobIdDirPair = item.split("::");
			jobIdDirMap.put(jobIdDirPair[0].trim(), jobIdDirPair[1]);
		}
		String[] jobIdList = jobIds.split("&amp;");
		for (String jobId : jobIdList) {
			String jobStatus = kettleService.getJobStatusById(jobId);
			if (Trans.STRING_STOPPED.equals(jobStatus) || Trans.STRING_FINISHED.equals(jobStatus) || Trans.STRING_FINISHED_WITH_ERRORS.equals(jobStatus)) {
				boolean flag = kettleService.delteJobById(jobId.trim());
				if (flag) {
					String relativeDir = jobIdDirMap.get(jobId.trim());
					FileUtil.deleteDir(relativeDir);
				}
			}
		}
		return true;
	}

	/**
	 * 作业列表
	 * 
	 * @param model
	 *            页面返回model
	 * @return
	 */
	@RequestMapping("/list")
	public String getAllJobList(Model model, @RequestParam("runStatus") String runStatus) {
		List<KettleJobEntity> list = null;
		if (null != runStatus && !"".equals(runStatus)) {
			list = kettleService.getJobsByRunStatus(runStatus);
		} else {
			list = kettleService.getAllJobInfo();
		}
		setActureStatus(list);// 设置list中jobEntity的状态
		model.addAttribute("jobList", list);
		return "/kettle/list :: joblist-main-content";
	}

	/**
	 * 设置list中jobEntity的状态
	 * 
	 * @param list
	 */
	private void setActureStatus(List<KettleJobEntity> list) {
		Map<String, Job> jobMap = KettleServiceImpl.getJobMap();//(Map<String, Job>) CacheUtils.get(KettleServiceImpl.KETTLE_CACHE, KettleServiceImpl.KETTLE_JOB_MAP);// 从ehCache获取jobMap
		/**
		 * 如果jobMap里有job，则设置对应的状态，否则就默认设置为停止状态
		 */
		for (KettleJobEntity jobEntity : list) {
			String jobId = jobEntity.getId();
			if (null != jobMap && jobMap.containsKey(jobId)) {
				jobEntity.setRunStatus(jobMap.get(jobId).getStatus());
			} else {
				jobEntity.setRunStatus(Trans.STRING_STOPPED);
			}
			kettleService.update(jobEntity);// 状态同步到数据库
		}
	}

	/**
	 * 根据jobName查询作业
	 * 
	 * @param model
	 *            页面返回模型
	 * @param jobName
	 *            作业名称
	 * @return
	 */
	@RequestMapping("/getJobsByName")
	public String getJobList(Model model, @RequestParam("jobName") String jobName) {
		List<KettleJobEntity> list = kettleService.getJobsByName(jobName);
		setActureStatus(list);// 设置list中jobEntity的状态
		model.addAttribute("jobList", list);
		return "/kettle/list :: joblist-main-content";
	}

	/**
	 * 根据多条件查询作业
	 * 
	 * @param model
	 *            页面返回模型
	 * @param jobName
	 *            作业名称
	 * @param jobType
	 *            作业类型
	 * @param jobStatus
	 *            作业状态
	 * @return
	 */
	@RequestMapping("/getJobsByConditions")
	public String getJobList(Model model, @RequestParam("jobName") String jobName, @RequestParam("jobType") String jobType, @RequestParam("runStatus") String runStatus) {
		List<KettleJobEntity> list = kettleService.getJobsByConditions(jobName, jobType, runStatus);
		setActureStatus(list);// 设置list中jobEntity的状态
		model.addAttribute("jobList", list);
		return "/kettle/list :: joblist-main-content";
	}

	/**
	 * 上传文件
	 * 
	 * @param file
	 *            文件
	 * @param jobName
	 *            作业名
	 * @param jobDescription
	 *            作业描述
	 * @return
	 */
	@RequestMapping(value = "/uploadZipFile")
	public @ResponseBody KettleJobEntity uploadFile(@RequestParam("inputKettleFile") MultipartFile file, @RequestParam("inputJobName") String jobName,
			@RequestParam("inputJobType") String inputJobType, @RequestParam("inputJobDescription") String jobDescription) {
		String kettleDir = PropertiesUtil.getValueByKey("kettle.dir", "kettle.properties");// 从kettle配置文件取出jobs上传路径
		String jobFileDir;// 作业上传后的根目录
		if (FileUtil.isDirectoryEndWithSlash(kettleDir)) {
			jobFileDir = kettleDir + jobName;
		} else {
			jobFileDir = kettleDir + SEPERATOR + jobName;
		}
		String jobsDir;// 上传的作业文件要写到jobs目录
		jobsDir = createKettlePropPath(jobFileDir);// 拼接kettle jobs目录

		File targetFile = new File(jobsDir, file.getOriginalFilename());
		FileUtil.createFolder(targetFile);
		KettleJobEntity jobInfoEntity = null;
		try {
			file.transferTo(targetFile);
			ZipCompress.unzip(targetFile, jobsDir, false);// 解压文件
			// 用kettle.properties力的kettleDir配置替换掉kettle.properties中的配置
			FileUtil.replaceSpecificLineValue(jobsDir + "kettle.properties", KETTLE_WORKING_DIR_DESC, KETTLE_WORKING_DIR_KEY + "=" + jobFileDir);
			// 从kettle.properties文件中获取job入口文件配置
			String jobFileName = PropertiesUtil.getValueByKey("start.job", jobsDir, "kettle.properties");

			jobInfoEntity = new KettleJobEntity();
			jobInfoEntity.setJobFileName(jobFileName);
			jobInfoEntity.setJobName(jobName);
			jobInfoEntity.setJobType(inputJobType);
			jobInfoEntity.setJobDescription(jobDescription);
			jobInfoEntity.setJobStatus(1);
			jobInfoEntity.setRunStatus(Trans.STRING_STOPPED);
			jobInfoEntity.setJobFileDir(jobFileDir);
			jobInfoEntity.setLastUpdate(DateUtil.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));
			kettleService.saveJobInfo(jobInfoEntity);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return jobInfoEntity;
	}

	/**
	 * kettle作业目录处理
	 * 
	 * @param filePath
	 *            kettle作业主目录
	 * @return kettle作业jobs目录
	 */
	private String createKettlePropPath(String filePath) {
		return FileUtil.setPathEndWithSlash(filePath) + JOBS_DIR + SEPERATOR;
	}

	/**
	 * 设置页面对象model
	 * 
	 * @param model
	 *            页面对象
	 * @param jobFileDir
	 *            kettle作业主目录
	 */
	private void setModelOfKettleProp(Model model, String jobFileDir) {
		String kettlePropPath = createKettlePropPath(jobFileDir);// kettle.properties文件路径
		Set<Entry<Object, Object>> entrys = PropertiesUtil.getEntrySet(kettlePropPath, KETTLE_PROP_FILE);
		List<Entry<Object, Object>> entryList = convertSetToList(entrys);
		model.addAttribute("entrys", entryList);
	}

	/**
	 * 将无序set转换为有序list
	 * 
	 * @param entrys
	 * @return
	 */
	private List<Entry<Object, Object>> convertSetToList(Set<Entry<Object, Object>> entrys) {
		/* 将Set集合转为List,这样获得的list并不能有序排列 */
		List<Entry<Object, Object>> entryList = new ArrayList<Entry<Object, Object>>(entrys);

		/* 将list有序排列 */
		Collections.sort(entryList, new Comparator<Entry<Object, Object>>() {
			public int compare(Entry<Object, Object> entryA, Entry<Object, Object> entryB) {
				int result = entryA.getKey().toString().compareTo(entryB.getKey().toString()); // 按照key排列
				return result;
			}
		});
		return entryList;
	}

	/**
	 * 加载kettle job 配置文件参数
	 * 
	 * @param model
	 *            返回前台页面模型
	 * @param jobFileDir
	 *            配置文件路径
	 * @return
	 */
	@RequestMapping("/loadParams")
	public String loadParams(Model model, @RequestParam("jobFileDir") String jobFileDir) {
		setModelOfKettleProp(model, jobFileDir);
		return "/kettle/fragments/kettle_table :: table-params";
	}

	/**
	 * 提交参数修改
	 * 
	 * @param model
	 * @param strParams
	 * @return
	 */
	@RequestMapping("/setParams")
	public String setParams(Model model, @RequestParam("strParams") String strParams, @RequestParam("jobFileDirId") String jobFileDirId) {
		String kettlePropPath = createKettlePropPath(jobFileDirId);// kettle.properties文件路径
		String[] entrys = strParams.split(";;");// 分割字符串
		Map<String, String> keyValueMap = new HashMap<String, String>();
		for (String entry : entrys) {
			String[] keyValue = entry.split("::");// 分割字符串
			keyValueMap.put(keyValue[0], keyValue[1]);
		}
		PropertiesUtil.setValueByKey(keyValueMap, kettlePropPath, KETTLE_PROP_FILE);
		setModelOfKettleProp(model, jobFileDirId);

		return "/kettle/fragments/kettle_table :: table-params";
	}

	/**
	 * 根据jobId加载Job属性
	 * 
	 * @param model
	 * @param jobId
	 * @return
	 */
	@RequestMapping("/loadJobInfo")
	public String loadJobInfo(Model model, @RequestParam("jobId") String jobId) {
		KettleJobEntity jobEntity = null;
		if (StringUtils.isNotBlank(jobId)) {
			jobEntity = kettleService.getJobInfoById(jobId);// 查询数据库获取jobEntity对象
		}
		model.addAttribute("jobEntity", jobEntity);
		return "/kettle/fragments/kettle_table :: table-props";
	}

	/**
	 * 提交属性修改
	 * 
	 * @param model
	 * @param strParams
	 * @param jobId
	 * @return
	 */
	@RequestMapping("/updateJobInfo")
	public String updateJobInfo(Model model, @RequestParam("jobId") String jobId, @RequestParam("jobDescription") String jobDescription, @RequestParam("jobType") String jobType,
			@RequestParam("remarks") String remarks) {
		KettleJobEntity jobEntity = kettleService.getJobInfoById(jobId);
		jobEntity.setJobDescription(jobDescription);
		jobEntity.setJobType(jobType);
		jobEntity.setRemarks(remarks);
		jobEntity.setLastUpdate(DateUtil.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));
		kettleService.update(jobEntity);
		model.addAttribute("jobEntity", jobEntity);
		return "/kettle/fragments/kettle_table :: table-props";
	}

}
