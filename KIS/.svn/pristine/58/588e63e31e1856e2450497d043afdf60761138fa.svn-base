package cn.com.goldwind.kis.service;

import org.springframework.ui.ModelMap;

import cn.com.goldwind.kis.entity.ClickLog;
import cn.com.goldwind.kis.mybatis.page.TableSplitResult;

public interface ClickLogService {

	public TableSplitResult<ClickLog> findPage(ModelMap map, Integer pageNumber, Integer pageSize);

	public int insert(ClickLog clickLog);

	public int update(ClickLog clickLog);

	public ClickLog findEmpNoAndTermId(ClickLog clickLog);
}
