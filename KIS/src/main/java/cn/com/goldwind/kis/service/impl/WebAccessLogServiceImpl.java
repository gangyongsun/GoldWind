package cn.com.goldwind.kis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import cn.com.goldwind.kis.bo.DailyAccessBo;
import cn.com.goldwind.kis.entity.WebAccessLog;
import cn.com.goldwind.kis.mybatis.BaseMybatisDao;
import cn.com.goldwind.kis.mybatis.page.TableSplitResult;
import cn.com.goldwind.kis.repository.WebAccessLogRepository;
import cn.com.goldwind.kis.service.WebAccessLogService;

@Service
public class WebAccessLogServiceImpl extends BaseMybatisDao<WebAccessLogRepository> implements WebAccessLogService {

	@Autowired
	private WebAccessLogRepository webAccessLogRepository;
	
	@Override
	public TableSplitResult<WebAccessLog> findPage(ModelMap map, Integer pageNumber, Integer pageSize) {
		return super.findPage(map, pageNumber, pageSize);
	}

	@Override
	public int insert(WebAccessLog webAccessLog) {
		return webAccessLogRepository.insert(webAccessLog);
	}

	@Override
	public int getTotalAccessCount() {
		return webAccessLogRepository.searchTotalAccessCount();
	}

	@Override
	public int getTotalAccessEmp() {
		return webAccessLogRepository.searchTotalAccessEmp();
	}

	@Override
	public List<DailyAccessBo> getRecent30Tendency(int i) {
		return webAccessLogRepository.getRecent30Tendency(i);
	}

	@Override
	public List<DailyAccessBo> getCurrentSeasionTendency() {
		return webAccessLogRepository.getCurrentSeasionTendency();
	}

	@Override
	public List<DailyAccessBo> getLastSeasionTendency() {
		return webAccessLogRepository.getLastSeasionTendency();
	}

	@Override
	public List<DailyAccessBo> getCurrentYearTendency() {
		return webAccessLogRepository.getCurrentYearTendency();
	}

	@Override
	public List<DailyAccessBo> getLastYearTendency() {
		return webAccessLogRepository.getLastYearTendency();
	}

	@Override
	public List<DailyAccessBo> getTendency() {
		return webAccessLogRepository.getTendency();
	}

}
