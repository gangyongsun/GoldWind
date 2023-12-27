package com.mdip.web.biz.main.controller;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

import org.pentaho.di.trans.Trans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mdip.OSutil.ComputerMonitorUtil;
import com.mdip.common.util.FileUtil;
import com.mdip.common.util.PropertiesUtil;
import com.mdip.web.biz.joblog.service.IJobLogService;
import com.mdip.web.biz.kettlemanage.entity.KettleJobEntity;
import com.mdip.web.biz.kettlemanage.service.IKettleService;
import com.mdip.web.framework.base.controller.BaseController;

@Controller
@RequestMapping("/main")
public class MainController extends BaseController {
	private static final String ConfigPropertyFile = "kettle.properties";// mdip-web项目中的Kettle配置文件名字
	private static final String LOG_DIR = PropertiesUtil.getValueByKey("job.dir.log", ConfigPropertyFile);

	@Autowired
	@Qualifier("kettleService")
	IKettleService kettleService;

	@Autowired
	IJobLogService jobLogService;

	/**
	 * 跳转到index页面
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return "/index";
	}

	/**
	 * 跳转到dashboard页面
	 * 
	 * @return
	 */
	@RequestMapping("/dashboard")
	public String dashboard(Model model) {
		int totalNum = kettleService.getJobCount();
		int runningNum = kettleService.getJobCountByStatus(Trans.STRING_RUNNING);
		int stoppedNum = kettleService.getJobCountByStatus(Trans.STRING_STOPPED);
		int finishedNum = kettleService.getJobCountByStatus(Trans.STRING_FINISHED);
		int othersNum = totalNum - runningNum - stoppedNum - finishedNum;

		model.addAttribute("runningNum", runningNum);
		model.addAttribute("stoppedNum", stoppedNum);
		model.addAttribute("finishedNum", finishedNum);
		model.addAttribute("othersNum", othersNum);

		return "/dashboard/dashboard :: dashboard-main-content";
	}

	/**
	 * 返回jobEntityList显示柱状图
	 * 
	 * @return jobEntityList
	 */
	@ResponseBody
	@RequestMapping("/echartsFetch")
	public List<KettleJobEntity> echartsFetch() {
		List<KettleJobEntity> jobEntityList = kettleService.getAllJobInfo();
		if (null != jobEntityList) {
			for (KettleJobEntity jobEntity : jobEntityList) {
				String jobDir = FileUtil.setPathEndWithSlash(jobEntity.getJobFileDir());// 检查目录是否以slash结尾，不是则加上
				File file = new File(jobDir + LOG_DIR);// 日志目录
				String logFolderSize = new DecimalFormat("#0.00").format(FileUtil.getDirSize(file));// 获取日志目录大小
				jobEntity.setLogFolderSize(logFolderSize);
			}
		}
		return jobEntityList;
	}

	/**
	 * 获取磁盘使用
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getDiskUsage")
	public double getDiskUsage() {
		return ComputerMonitorUtil.getDiskUsage();
	}

	/**
	 * 获取CPU使用
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getCPUUsage")
	public double getCPUUsage() {
		return ComputerMonitorUtil.getCpuUsage();
	}

	/**
	 * 获取CPU使用
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getMemUsage")
	public double getMemUsage() {
		return ComputerMonitorUtil.getMemUsage();
	}

}
