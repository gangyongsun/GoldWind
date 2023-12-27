package cn.com.goldwind.kis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import cn.com.goldwind.kis.entity.ClickLog;
import cn.com.goldwind.kis.mybatis.BaseMybatisDao;
import cn.com.goldwind.kis.mybatis.page.TableSplitResult;
import cn.com.goldwind.kis.repository.ClickLogRepository;
import cn.com.goldwind.kis.service.ClickLogService;

@Service
public class ClickLogServiceImpl extends BaseMybatisDao<ClickLogRepository> implements ClickLogService {

	@Autowired
	private ClickLogRepository clickLogRepository;

	@Override
	public TableSplitResult<ClickLog> findPage(ModelMap map, Integer pageNumber, Integer pageSize) {
		return super.findPage(map, pageNumber, pageSize);
	}

	@Override
	public int insert(ClickLog clickLog) {
		return clickLogRepository.insertSelective(clickLog);
	}

	@Override
	public int update(ClickLog clickLog) {
		return clickLogRepository.updateByPrimaryKeySelective(clickLog);
	}

	@Override
	public ClickLog findEmpNoAndTermId(ClickLog clickLog) {
		return clickLogRepository.selectOne(clickLog);
	}

}
