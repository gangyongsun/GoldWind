package cn.com.goldwind.kis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import cn.com.goldwind.kis.entity.TermTypeBrowseLog;
import cn.com.goldwind.kis.mybatis.BaseMybatisDao;
import cn.com.goldwind.kis.mybatis.page.TableSplitResult;
import cn.com.goldwind.kis.repository.TermTypeBrowseLogRepository;
import cn.com.goldwind.kis.service.TermTypeBrowseLogService;

@Service
public class TermTypeBrowseLogServiceImpl extends BaseMybatisDao<TermTypeBrowseLog> implements TermTypeBrowseLogService {

	@Autowired
	private TermTypeBrowseLogRepository termTypeBrowseLogRepository;
	
	@Override
	public TableSplitResult<TermTypeBrowseLog> findPage(ModelMap map, Integer pageNumber, Integer pageSize) {
		return super.findPage(map, pageNumber, pageSize);
	}

	@Override
	public int insert(TermTypeBrowseLog termTypeBrowseLog) {
		return termTypeBrowseLogRepository.insertSelective(termTypeBrowseLog);
	}

	@Override
	public int update(TermTypeBrowseLog termTypeBrowseLog) {
		return termTypeBrowseLogRepository.updateByPrimaryKeySelective(termTypeBrowseLog);
	}

	@Override
	public TermTypeBrowseLog findByEmpNoAndTermType(TermTypeBrowseLog termTypeBrowseLog) {
		return termTypeBrowseLogRepository.selectOne(termTypeBrowseLog);
	}

}
