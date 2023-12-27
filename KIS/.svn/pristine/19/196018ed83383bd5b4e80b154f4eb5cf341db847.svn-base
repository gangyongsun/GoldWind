package cn.com.goldwind.kis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import cn.com.goldwind.kis.bo.SearchLogCountSum;
import cn.com.goldwind.kis.entity.SearchLog;
import cn.com.goldwind.kis.mybatis.BaseMybatisDao;
import cn.com.goldwind.kis.mybatis.page.TableSplitResult;
import cn.com.goldwind.kis.repository.SearchLogRepository;
import cn.com.goldwind.kis.service.SearchLogService;

@Service
public class SearchLogServiceImpl extends BaseMybatisDao<SearchLogRepository> implements SearchLogService {
	
	@Autowired
	private SearchLogRepository searchLogRepository;

	@Override
	public TableSplitResult<SearchLog> findPage(ModelMap map, Integer pageNumber, Integer pageSize) {
		return super.findPage(map, pageNumber, pageSize);
	}

	@Override
	public int insert(SearchLog searchLog) {
		return searchLogRepository.insertSelective(searchLog);
	}

	@Override
	public int update(SearchLog searchLog) {
		return searchLogRepository.updateByPrimaryKeySelective(searchLog);
	}

	@Override
	public SearchLog findByEmpNoAndFindContent(SearchLog searchLog) {
		return searchLogRepository.selectOne(searchLog);
	}

	@Override
	public int getTotalSearchCount() {
		return searchLogRepository.searchTotalSearchCount();
	}

	@Override
	public SearchLogCountSum getSearchCountByAccurate() {
		return searchLogRepository.getSearchCountByAccurate();
	}

}
