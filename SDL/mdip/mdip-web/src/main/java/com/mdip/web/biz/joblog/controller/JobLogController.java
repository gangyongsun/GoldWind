package com.mdip.web.biz.joblog.controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.pentaho.di.job.Job;
import org.pentaho.di.trans.Trans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mdip.common.util.DateUtil;
import com.mdip.common.util.FileUtil;
import com.mdip.common.util.IOUtil;
import com.mdip.common.util.PropertiesUtil;
import com.mdip.web.biz.joblog.entity.JobLogEntity;
import com.mdip.web.biz.joblog.service.IJobLogService;
import com.mdip.web.biz.kettlemanage.entity.KettleJobEntity;
import com.mdip.web.biz.kettlemanage.service.IKettleService;
import com.mdip.web.biz.kettlemanage.service.impl.KettleServiceImpl;
import com.mdip.web.framework.base.cache.CacheUtils;
import com.mdip.web.framework.base.controller.BaseController;

@Controller
@RequestMapping("/joblog")
public class JobLogController extends BaseController {
	private static final String ConfigPropertyFile = "kettle.properties";// mdip-web项目中的Kettle配置文件名字
	private static final String LOG_DIR = PropertiesUtil.getValueByKey("job.dir.log", ConfigPropertyFile);
	@Autowired
	IJobLogService jobLogService;

	@Autowired
	@Qualifier("kettleService")
	IKettleService kettleService;

	/**
	 * 列出jobLog
	 * 
	 * @param model
	 *            页面返回模型
	 * @return
	 */
	@RequestMapping("/list")
	public String getAllJobLogList(Model model) {
		List<JobLogEntity> list = jobLogService.getAllJobLogList();
		setActureStatus(list);
		model.addAttribute("logList", list);
		return "/joblog/logs :: joblogs-main-content";
	}

	/**
	 * 根据条件查询jobLog
	 * 
	 * @param model
	 * @param jobName
	 *            jobLog名字
	 * @param startDatetime
	 *            job启动时间
	 * @param endDatetime
	 *            job结束时间
	 * @param jobStatus
	 *            job状态
	 * @return
	 */
	@RequestMapping("/getJobLogsByConditions")
	public String getJobLogsByConditions(Model model, @RequestParam("jobName") String jobName, @RequestParam("jobStatus") String jobStatus, @RequestParam("startDatetime") String startDatetime,
			@RequestParam("endDatetime") String endDatetime) {
		List<JobLogEntity> list = jobLogService.getJobLogsByConditions(jobName, startDatetime, endDatetime, jobStatus);
		setActureStatus(list);
		model.addAttribute("logList", list);
		return "/joblog/logs :: joblogs-main-content";
	}
	
	/**
	 *根据日志ID，返回日志信息，用于图表动态显示数据
	 * @param logId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getLogInfoById")
	public JobLogEntity getLogInfoById(@RequestParam("logId") String logId){
		return jobLogService.getJobLogById(logId);
	}

	/**
	 * 设置list中setJobStatus的状态
	 * 
	 * @param list
	 */
	private void setActureStatus(List<JobLogEntity> list) {
		Map<String, Job> jobMap = KettleServiceImpl.getJobMap();// (Map<String, Job>) CacheUtils.get(KettleServiceImpl.KETTLE_CACHE, KettleServiceImpl.KETTLE_JOB_MAP);// 从ehCache获取jobMap
		/**
		 * 如果jobMap为空，比如初始启动时，要设置所有jobEntity状态为stopped;
		 * <p>
		 * 如果有job启动，jobMap就不为空，要设置所有的jobEntity状态为jobMap中对应的job的状态，其他的为stopped
		 */
		String jobId = "";
		String jobLogStartTime = "";
		String jobStartTime = "";
		for (JobLogEntity jobLogEntity : list) {
			jobId = jobLogEntity.getJobId();// 获取jobLogEntity中的jobId，一遍与cache中的job对比
			/**
			 * 1.获取job启动时间
			 */
			KettleJobEntity jobEntity = kettleService.getJobInfoById(jobId);
			if (null != jobEntity) {
				jobStartTime = DateUtil.stringPatternConvert(jobEntity.getStartTime(), "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss");
			}

			/**
			 * 2.获取job启动写日志的启动时间
			 */
			jobLogStartTime = DateUtil.stringPatternConvert(jobLogEntity.getStartTime(), "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss");

			if (null != jobMap && jobMap.containsKey(jobId) && jobLogStartTime.equals(jobStartTime)) {
				jobLogEntity.setJobStatus(jobMap.get(jobId).getStatus());
			}else{
				jobLogEntity.setJobStatus(Trans.STRING_STOPPED);
			}

			jobLogService.update(jobLogEntity);// 更新jobLog状态字段
		}
	}

	/**
	 * 下载jobLog
	 * 
	 * @param logId
	 *            jobLogID
	 * @param response
	 * @return
	 */
	@RequestMapping("/downloadLogs")
	public String downloadLogs(@RequestParam("logId") String logId, HttpServletResponse response) {
		/**
		 * 根据logId拿到logEntity对象，然后取到jobName
		 */
		JobLogEntity jobLogEntity = jobLogService.getJobLogById(logId);
		String jobId = jobLogEntity.getJobId();
		String jobName = jobLogEntity.getJobName();

		/**
		 * 根据jobId拿到jobEntity
		 */
		KettleJobEntity jobEntity = kettleService.getJobInfoById(jobId);
		String jobDir = jobEntity.getJobFileDir();// 取jobDir
		jobDir = FileUtil.setPathEndWithSlash(jobDir);// 检查目录是否以slash结尾，不是则加上
		String jobStartTime = jobEntity.getStartTime();// 取job开始时间

		/**
		 * job开始时间格式转换
		 */
		String startTime4LogName = DateUtil.stringPatternConvert(jobStartTime, "yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss");

		/**
		 * 拼凑日志文件名
		 */
		String logFileName = jobDir + LOG_DIR + SEPERATOR + jobName + "_" + startTime4LogName + ".log";// 日志文件名
		File file = new File(logFileName);// 日志文件
		downLoad(file, response);// 发回页面
		return null;
	}

	/**
	 * 弹出下载
	 * 
	 * @param file
	 *            要下载的文件
	 * @param response
	 */
	private void downLoad(File file, HttpServletResponse response) {
		response.reset();
		response.setContentType("application/x-msdownload");
		response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			FileUtil.file2OutputSteam(file, os);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtil.closeQuietly(os);
		}
	}
}
