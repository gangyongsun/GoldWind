package cn.com.goldwind.kis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import cn.com.goldwind.kis.entity.WebResponse;
import cn.com.goldwind.kis.mybatis.BaseMybatisDao;
import cn.com.goldwind.kis.mybatis.page.TableSplitResult;
import cn.com.goldwind.kis.repository.WebResponseRepository;
import cn.com.goldwind.kis.service.WebResponseService;

@Service
public class WebResponseServiceImpl extends BaseMybatisDao<WebResponseRepository> implements WebResponseService {

	@Autowired
	private WebResponseRepository webResponseRepository;

	@Override
	public TableSplitResult<WebResponse> findWebResponseListPage(ModelMap map, Integer pageNumber, Integer pageSize) {
		return super.findPage(map, pageNumber, pageSize);
	}

	@Override
	public int saveWebresponseLog(WebResponse webResponse) {
		return webResponseRepository.saveWebresponseLog(webResponse);
	}

}
