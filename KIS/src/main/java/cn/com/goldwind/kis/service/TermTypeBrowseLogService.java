package cn.com.goldwind.kis.service;

import org.springframework.ui.ModelMap;

import cn.com.goldwind.kis.entity.TermTypeBrowseLog;
import cn.com.goldwind.kis.mybatis.page.TableSplitResult;

public interface TermTypeBrowseLogService {

	public TableSplitResult<TermTypeBrowseLog> findPage(ModelMap map, Integer pageNumber, Integer pageSize);

	public int insert(TermTypeBrowseLog termTypeBrowseLog);

	public int update(TermTypeBrowseLog termTypeBrowseLog);

	public TermTypeBrowseLog findByEmpNoAndTermType(TermTypeBrowseLog termTypeBrowseLog);

}
