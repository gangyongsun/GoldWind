package com.mdip.web.biz.kettlemanage.service.impl;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.logging.KettleLogStore;
import org.pentaho.di.core.logging.LogLevel;
import org.pentaho.di.core.logging.LoggingBuffer;
import org.pentaho.di.core.logging.LoggingRegistry;
import org.pentaho.di.core.plugins.PluginFolder;
import org.pentaho.di.core.plugins.StepPluginType;
import org.pentaho.di.job.Job;
import org.pentaho.di.job.JobMeta;
import org.pentaho.di.repository.StringObjectId;
import org.pentaho.di.trans.Trans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mdip.common.util.DateUtil;
import com.mdip.common.util.FileUtil;
import com.mdip.common.util.MyStringUtil;
import com.mdip.common.util.PropertiesUtil;
import com.mdip.common.util.ThreadUtil;
import com.mdip.kettle.KettleEnvCheck;
import com.mdip.kettle.KettleLogEventListener;
import com.mdip.timedTask.listeners.DataFileStatusListener;
import com.mdip.timedTask.monitor.DataFileStatusMonitor;
import com.mdip.web.biz.joblog.entity.JobLogEntity;
import com.mdip.web.biz.kettlemanage.entity.KettleJobEntity;
import com.mdip.web.biz.kettlemanage.service.IKettleService;
import com.mdip.web.framework.base.cache.CacheUtils;
import com.mdip.web.framework.base.dao.IDao;
import com.mdip.web.framework.base.entity.PageEntity;
import com.mdip.web.framework.base.exception.ServiceException;
import com.mdip.web.framework.base.service.BaseService;
import com.mdip.web.framework.base.service.IPageService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("kettleService")
public class KettleServiceImpl extends BaseService implements IKettleService {
//	public static String KETTLE_CACHE = "KETTLE";// KETTLE_CACHE作为ehcache
//	public static String KETTLE_JOB_MAP = "KETTLE_JOB_MAP";// 将jobMap放到ehCach缓存，通过KETTLE_JOB_MAP作为key来获取jobMap
//	public static String KETTLE_FILE_MONITOR_MAP = "KETTLE_FILE_MONITOR_MAP";// kettle作业相关的数据文件监控map
//	public static String KETTLE_LOG_LISTENER_MAP = "KETTLE_LOG_LISTENER_MAP";// KettleLogEventListener的map，用于停止作业时，关闭流
	private static Map<String, Job> jobMap = new Hashtable<String,Job>();
	private static Map<String, KettleLogEventListener> kettleLogEventListenerMap = new Hashtable<String,KettleLogEventListener>();
	private static Map<String, DataFileStatusMonitor> dataFileStatusMonitorMap = new Hashtable<String,DataFileStatusMonitor>();
	public static final String FILE_SEPERATOR = "/";// 停止失败

	private static final String JOB_PROPERTIES = "kettle.properties";// 各kettle作业中的配置文件名字，规定为这个
	private static final String ConfigPropertyFile = "kettle.properties";// mdip-web项目中的Kettle配置文件名字
	private static final String JOBS_DIR = PropertiesUtil.getValueByKey("job.dir.jobs", ConfigPropertyFile);
	private static final String LOG_DIR = PropertiesUtil.getValueByKey("job.dir.log", ConfigPropertyFile);
	private static final String LOG_SUFFIX = PropertiesUtil.getValueByKey("log.file.suffix", ConfigPropertyFile);
	private static final String LOG_LEVEL = PropertiesUtil.getValueByKey("log.file.level", ConfigPropertyFile);

	// job tracker的最大数量
	private static final String LOG_JOB_TRACKER = PropertiesUtil.getValueByKey("log.job.tracker.size", ConfigPropertyFile);

	private static final String and = " and ";

	@Autowired
	protected IPageService pageService;

	@Autowired
	protected IDao kettleJobDao;

	@Autowired
	protected IDao jobLogDao;

	/**
	 * 根据jobId启动job作业
	 */
	@Override
	public boolean startJobById(String jobId) {
		KettleJobEntity entity = getJobInfoById(jobId);// 根据jobId查询数据库获取jobEntity对象
		boolean flag = false;
		try {
			if (null != entity) {
				startJob(entity);// 启动作业
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 启动作业
	 * 
	 * @param entity
	 *            作业entity
	 * @return
	 * @throws Exception
	 */
	public boolean startJob(KettleJobEntity entity) throws Exception {
		boolean flag = false;
		String jobId = entity.getId();// 获取job id
		String jobName = entity.getJobFileName();// 获取job名称
		String jobDir = entity.getJobFileDir();// 获取job文件路径
		jobDir = FileUtil.setPathEndWithSlash(jobDir);// 检查目录是否以slash结尾，不是则加上

		// 检查作业环境
		if (KettleEnvCheck.initByJobName(jobDir)) {
			String jobFile = jobDir + JOBS_DIR + FILE_SEPERATOR + jobName;// 获取作业文件绝对路径

			String rootPath = Thread.currentThread().getContextClassLoader().getResource(FILE_SEPERATOR).getPath();
			StepPluginType.getInstance().getPluginFolders().add(new PluginFolder(rootPath + FILE_SEPERATOR + "plugins", false, true));

			/**
			 * 1.kettle环境初始化
			 */
			if (!KettleEnvironment.isInitialized()) {
				KettleEnvironment.init();
			}

			if (!KettleLogStore.isInitialized()) {
				KettleLogStore.init();
			}
			
			/**
			 * 2.创建一个job
			 */
			JobMeta jobMeta = new JobMeta(jobFile, null, null);
			jobMeta.setObjectId(new StringObjectId(jobId));
			Job job = new Job(null, jobMeta);

			job.init();

			// job tracker设置
			int trackerSize = LOG_JOB_TRACKER == null || "".equals(LOG_JOB_TRACKER) ? 1000 : Integer.parseInt(LOG_JOB_TRACKER);
			job.getJobTracker().setMaxChildren(trackerSize);

			/**
			 * 3.初始化KETTLE_CACHE，将jobMap放到ehCache里
			 */
			//Map<String, Job> jobMap = initEhcacheMap(KETTLE_CACHE, KETTLE_JOB_MAP);
			jobMap.put(jobId, job);// 2.把创建的job放到jobMap

			/**
			 * 4.写日志文件开启
			 */
			// if (!KettleClientEnvironment.isInitialized()) {
			// KettleClientEnvironment.init();
			// }
			job.setLogLevel(getLogLevel(LOG_LEVEL));// 日志级别
			// KettleClientEnvironment.getInstance().setClient(KettleClientEnvironment.ClientType.CARTE);
			LoggingBuffer loggingBuffer = KettleLogStore.getAppender();

			String logChannelId = job.getLogChannel().getLogChannelId();

			/**
			 * 5.在每个作业log目录下生成log文件，文件名为"作业Name_开始时间.log"
			 */
			Date date = new Date();
			String startTime = DateUtil.formatDate2String(date, "yyyy-MM-dd HH:mm:ss");
			String startTime4LogName = DateUtil.getCurrentDateTime("yyyyMMddHHmmss");
			String logName = jobDir + LOG_DIR + FILE_SEPERATOR + entity.getJobName() + "_" + startTime4LogName + LOG_SUFFIX;// 日志文件名

			// 初始化KETTLE_CACHE，将KettleLogEventListener放到ehCache里
			//Map<String, KettleLogEventListener> kettleLogEventListenerMap = initEhcacheMap(KETTLE_CACHE, KETTLE_LOG_LISTENER_MAP);
			KettleLogEventListener kettleLogEventListener = new KettleLogEventListener(logChannelId, logName, true);
			kettleLogEventListenerMap.put(jobId, kettleLogEventListener);

			loggingBuffer.addLoggingEventListener(kettleLogEventListener);

			/**
			 * 6.获取kettle配置文件所有参数,设置kettle作业属性
			 */
			Properties properties = PropertiesUtil.getAllParams(jobDir + JOBS_DIR, JOB_PROPERTIES);
			Set<Entry<Object, Object>> entrySet = properties.entrySet();
			for (Entry<Object, Object> entry : entrySet) {
				job.setVariable((String) entry.getKey(), (String) entry.getValue());
			}

			/**
			 * 7.向JobLogEntity对应的数据库插入jobLog记录
			 */
			JobLogEntity jobLogEntity = new JobLogEntity();
			jobLogEntity.setJobId(jobId);
			jobLogEntity.setJobName(entity.getJobName());
			jobLogEntity.setStartTime(startTime);
			jobLogEntity.setJobStatus(Trans.STRING_RUNNING);
			jobLogDao.insert(jobLogEntity);

			/**
			 * 8.启动作业数据文件监控线程，根据处理文件数即时更新KETTLE_JOB_LOG表
			 */
			//Map<String, DataFileStatusMonitor> dataFileStatusMonitorMap = initEhcacheMap(KETTLE_CACHE, KETTLE_FILE_MONITOR_MAP);// 初始化KETTLE_CACHE，将dataFileStatusMonitorMap放到ehCache里

			DataFileStatusMonitor monitor = new DataFileStatusMonitor(5000);
			monitor.monitor(jobDir, new DataFileStatusListener(jobLogEntity, new File(jobDir)));

			dataFileStatusMonitorMap.put(jobLogEntity.getId(), monitor);

			monitor.start();

			/**
			 * 9.启动作业
			 */
			job.start();// 启动作业
			ThreadUtil.sleep(1);// 睡眠1秒钟

			/**
			 * 10.更新job数据库
			 */
			entity.setStartTime(startTime);// 设置作业开始时间
			entity.setEndTime("");
			entity.setRunStatus(job.getStatus());// 设置entity的状态值
			kettleJobDao.update(entity);// 更新数据库

			/**
			 * 11.kettle环境关闭
			 */
			logCleanup(job, jobMeta);
			KettleEnvironment.shutdown();
			flag = true;
		}
		return flag;
	}

	/**
	 * NOTHING( 0, "Nothing" ), ERROR( 1, "Error" ), MINIMAL( 2, "Minimal" ),
	 * BASIC( 3, "Basic" ), DETAILED( 4, "Detailed" ), DEBUG( 5, "Debug" ),
	 * ROWLEVEL( 6, "Rowlevel" )
	 * 
	 * @param loglevel
	 * @return
	 */
	private LogLevel getLogLevel(String loglevel) {
		switch (loglevel) {
		case "0":
			return LogLevel.NOTHING;
		case "1":
			return LogLevel.ERROR;
		case "2":
			return LogLevel.MINIMAL;
		case "3":
			return LogLevel.BASIC;
		case "4":
			return LogLevel.DETAILED;
		case "5":
			return LogLevel.DEBUG;
		case "6":
			return LogLevel.ROWLEVEL;
		default:
			return LogLevel.BASIC;
		}
	}

	/**
	 * 初始化KETTLE_CACHE，将jobMap放到ehCache里
	 * <p>
	 * 从ehCache中获取jobMap对象，如果jobMap为null，就创建jobMap，并把jobMap放到ehCache
	 * 
	 * @param <T>
	 * @param <T>
	 * 
	 * @return jobMap
	 */
	public <T> Map<String, T> initEhcacheMap(String cacheName, String key) {
		Map<String, T> map = (Map<String, T>) CacheUtils.get(cacheName, key);
		if (null == map) {
			map = new Hashtable<String, T>();// 作业id,作业map
			CacheUtils.put(cacheName, key, map);
		}
		return map;
	}

	/**
	 * 根据jobId停止job信息
	 */
	@Override
	public boolean stopJobById(String jobId) {
		boolean flag = false;
		try {
			/**
			 * 1.停止作业
			 */
			boolean flagA = false;
			//Map<String, Job> jobMap = (Map<String, Job>) CacheUtils.get(KETTLE_CACHE, KETTLE_JOB_MAP);// 从ehCache获取jobMap
			Job job = (null == jobMap) ? null : jobMap.get(jobId);// 从jobMap中拿到创建过的job对象
			if (job != null) {
				flagA = stopJob(job);// 停止作业
			}

			ThreadUtil.sleep(1);// 睡眠1秒钟
			/**
			 * 2.更新数据库中jobEntity状态
			 */
			boolean flagB = false;
			String endTime = DateUtil.getCurrentDateTime("yyyy-MM-dd HH:mm:ss");
			KettleJobEntity jobEntity = getJobInfoById(jobId);
			if (null != jobEntity) {
				jobEntity.setRunStatus(job.getStatus());// 设置jobEntity运行状态
				jobEntity.setEndTime(endTime);// 设置作业结束时间
				flagB = kettleJobDao.update(jobEntity);// 更新到数据库
			}

			/**
			 * 3.更新log状态和结束时间
			 */
			String jobLogStartTime = jobEntity.getStartTime();// 作业启动时设置的joblog启动时间
			boolean flagC = false;
			JobLogEntity jobLogEntity = null;
			List<?> list = (List<?>) jobLogDao.sqlSelect("select id from KETTLE_JOB_LOG where jobid='" + jobId + "' and STARTDATE='" + jobLogStartTime + "'");
			if (null != list && list.size() != 0) {
				String logId = list.get(0).toString();
				jobLogEntity = jobLogDao.queryOneById(JobLogEntity.class, logId);
				if (null != jobLogEntity) {
					jobLogEntity.setEndTime(endTime);
					jobLogEntity.setJobStatus(job.getStatus());
					flagC = jobLogDao.update(jobLogEntity);
				}
			}

			/**
			 * 4.停止该作业的文件数据监控线程
			 */
			if (null != jobLogEntity) {
				String logId = jobLogEntity.getId();
				//Map<String, DataFileStatusMonitor> dataFileStatusMonitorMap = (Map<String, DataFileStatusMonitor>) CacheUtils.get(KETTLE_CACHE, KETTLE_FILE_MONITOR_MAP);// 从ehCache获取dataFileStatusMonitorMap
				DataFileStatusMonitor monitor = dataFileStatusMonitorMap.get(logId);
				monitor.stop();
				dataFileStatusMonitorMap.remove(logId);// 移除dataFileStatusMonitorMap对象
			}

			/**
			 * 5.关掉该作业日志的流
			 */
			if (null != jobLogEntity) {
				// 从ehCache获取KettleLogEventListener的map
				//Map<String, KettleLogEventListener> kettleLogEventListenerMap = (Map<String, KettleLogEventListener>) CacheUtils.get(KETTLE_CACHE, KETTLE_LOG_LISTENER_MAP);
				KettleLogEventListener kettleLogEventListener = kettleLogEventListenerMap.get(jobId);
				// 关闭该作业日志的流
				if (null != kettleLogEventListener) {
					kettleLogEventListener.close();
					kettleLogEventListener=null;
				}
				kettleLogEventListenerMap.remove(jobId);// 移除kettleLogEventListener对象
			}

			/**
			 * #1和#2都成功
			 */
			if (flagA && flagB) {
				jobMap.remove(jobId);// 移除job对象
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 停止作业
	 * 
	 * @param job
	 *            job对象
	 * @return 是否成功停止
	 */
	private boolean stopJob(Job job) {
		boolean flag = false;
		if (null != job && job.isActive()) {
			job.stopAll();
			flag = true;
		}
		return flag;
	}

	/**
	 * 根据jobId获取作业状态
	 */
	@Override
	public String getJobStatusById(String jobId) {
		//Map<String, Job> jobMap = (Map<String, Job>) CacheUtils.get(KETTLE_CACHE, KETTLE_JOB_MAP);// 从ehCache获取jobMap
		if (null != jobMap && jobMap.containsKey(jobId)) {
			return jobMap.get(jobId).getStatus();
		} else {
			return kettleJobDao.queryOneById(KettleJobEntity.class, jobId).getRunStatus();
		}
	}

	/**
	 * 根据jobId查询数据库获取jobEntity
	 */
	@Override
	public KettleJobEntity getJobInfoById(String jobId) {
		return kettleJobDao.queryOneById(KettleJobEntity.class, jobId);// 查询数据库获取jobEntity对象
	}

	/**
	 * 向数据库插入JobEntity
	 */
	@Override
	public boolean saveJobInfo(KettleJobEntity jobEntity) {
		return kettleJobDao.insert(jobEntity);
	}

	/**
	 * 查询所有JobEntity
	 */
	@Override
	public List<KettleJobEntity> getAllJobInfo() {
		return kettleJobDao.queryList("KettleJobEntity");
	}

	/**
	 * 根据runStatus查询jobEntity
	 */
	@Override
	public List<KettleJobEntity> getJobsByRunStatus(String runStatus) {
		StringBuffer where = new StringBuffer();
		if (!"".equals(runStatus)) {
			where.append("runStatus = '").append(runStatus).append("'");
		}
		return kettleJobDao.queryList("KettleJobEntity", where.toString());
	}

	/**
	 * 根据jobName查询jobEntity
	 */
	@Override
	public List<KettleJobEntity> getJobsByName(String jobName) {
		StringBuffer where = new StringBuffer();
		if (!"".equals(jobName)) {
			where.append("jobName like '%").append(jobName).append("%'");
		}
		return kettleJobDao.queryList("KettleJobEntity", where.toString());
	}

	/**
	 * 根据jobId和jobName查询jobEntity
	 */
	@Override
	public List<KettleJobEntity> getJobsByIdName(String jobId, String jobName) {
		StringBuffer where = new StringBuffer();
		if (!"".equals(jobId)) {
			where.append("id ='").append(jobId).append("'");
			where.append(!"".equals(jobName) ? and : "");
		}
		if (!"".equals(jobName)) {
			where.append("jobName like '%").append(jobName).append("%'");
		}
		String finalWhere = MyStringUtil.finalWhere(where.toString());// 判断where是否是以and结尾，如果是就要处理
		return kettleJobDao.queryList("KettleJobEntity", finalWhere);
	}

	/**
	 * 根据条件查询jobs
	 */
	@Override
	public List<KettleJobEntity> getJobsByConditions(String jobName, String jobType, String runStatus) {
		jobName = jobName.trim();
		jobType = jobType.trim();
		runStatus = runStatus.trim();
		StringBuffer where = new StringBuffer();
		if (!"".equals(jobName)) {
			where.append("jobName like '%").append(jobName).append("%'");
			where.append(!"".equals(jobName) ? and : "");
		}
		if (!"".equals(jobType)) {
			where.append("jobType ='").append(jobType).append("'");
			where.append(!"".equals(jobType) ? and : "");
		}
		if (!"".equals(runStatus)) {
			where.append("runStatus ='").append(runStatus).append("'");
		}
		String finalWhere = MyStringUtil.finalWhere(where.toString());// 判断where是否是以and结尾，如果是就要处理
		return kettleJobDao.queryList("KettleJobEntity", finalWhere);
	}

	/**
	 * 根据jobId从数据库中删除jobEntity
	 */
	@Override
	public boolean delteJobById(String jobId) {
		boolean flag = false;
		if (StringUtils.isNotBlank(jobId)) {
			flag = kettleJobDao.delete(jobId);
		}
		return flag;
	}

	/**
	 * 更新到数据库
	 */
	@Override
	public Boolean update(KettleJobEntity entity) {
		return kettleJobDao.update(entity);
	}

	/**
	 * 根据id查询对象
	 */
	@Override
	public KettleJobEntity queryById(String id) throws ServiceException {
		return kettleJobDao.queryOneById(KettleJobEntity.class, id);
	}

	/**
	 * 查询所有job数量
	 */
	@Override
	public int getJobCount() {
		return kettleJobDao.getCount().intValue();
	}

	/**
	 * 根据状态查询job数量
	 */
	@Override
	public int getJobCountByStatus(String runnStatus) {
		runnStatus = runnStatus.trim();
		StringBuffer where = new StringBuffer();
		if (!"".equals(runnStatus)) {
			where.append("RUN_STATUS ='").append(runnStatus).append("'");
		}
		return kettleJobDao.queryCountByWhere("JOB_INFO", where.toString());//
	}

	@Override
	public Boolean deleteById(Serializable id, String update_by) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteByIds(String ids, String update_by) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteByIdsRe(String ids, String update_by) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getPage(String hql, PageEntity paramPageEntity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getPage(String tableName, String where, String orderBy, PageEntity paramPageEntity) {
		// TODO Auto-generated method stub

	}

	@Override
	public Boolean save(KettleJobEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<KettleJobEntity> queryByWhere(KettleJobEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getPage(KettleJobEntity entity, PageEntity pageentity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void getPageRe(KettleJobEntity entity, PageEntity pageentity) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<KettleJobEntity> getAll(KettleJobEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	private void logCleanup(Job job, JobMeta jobMeta) {
		// Remove the logging records
		String logChannelId = job.getLogChannelId();
		if (null != logChannelId) {
			KettleLogStore.discardLines(logChannelId, true);
			// Remove the entries from the registry
			log.info("removeIncludingChildren " + logChannelId);
			LoggingRegistry.getInstance().removeIncludingChildren(logChannelId);
		}
		// Also remove the entries from other objects like TransMeta or
		// JobMeta...
		logChannelId = jobMeta.getLogChannelId();
		if (null != logChannelId)
			KettleLogStore.discardLines(logChannelId, true);
	}

	public static Map<String, Job> getJobMap() {
		return jobMap;
	}

	public static void setJobMap(Map<String, Job> jobMap) {
		KettleServiceImpl.jobMap = jobMap;
	}

}
