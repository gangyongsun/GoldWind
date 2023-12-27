package cn.com.goldwind.kis.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import cn.com.goldwind.kis.bo.DeptNameBo;
import cn.com.goldwind.kis.mybatis.BaseMybatisDao;
import cn.com.goldwind.kis.mybatis.page.TableSplitResult;
import cn.com.goldwind.kis.repository.DepNameDescListRepository;
import cn.com.goldwind.kis.service.DepNameDescListService;

@Service
public class DepNameDescListServiceImpl extends BaseMybatisDao<DepNameDescListRepository> implements DepNameDescListService {

	@Autowired
	private DepNameDescListRepository depNameDescListRepository;
	
	@Override
	public TableSplitResult<DeptNameBo> findPage(ModelMap map, Integer pageNumber, Integer pageSize) {
		return super.findPage(map, pageNumber, pageSize);
	}

	@Override
	public List<DeptNameBo> findDepNameDescList() {
		return depNameDescListRepository.findDepNameDescList();
	}

}
