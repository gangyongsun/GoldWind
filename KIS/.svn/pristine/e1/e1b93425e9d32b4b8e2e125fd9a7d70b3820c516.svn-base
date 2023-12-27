package cn.com.goldwind.kis.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import cn.com.goldwind.kis.bo.DeptNameBo;
import cn.com.goldwind.kis.mybatis.BaseMybatisDao;
import cn.com.goldwind.kis.mybatis.page.TableSplitResult;
import cn.com.goldwind.kis.repository.DepNameDescListRepository;
import cn.com.goldwind.kis.service.DepNameDescListService;

@Service
public class DepNameDescListServiceImpl extends BaseMybatisDao<DepNameDescListRepository> implements DepNameDescListService {

	@Override
	public TableSplitResult<DeptNameBo> findDepNameDescListPage(ModelMap map, Integer pageNumber, Integer pageSize) {
		return super.findPage(map, pageNumber, pageSize);
	}

}
