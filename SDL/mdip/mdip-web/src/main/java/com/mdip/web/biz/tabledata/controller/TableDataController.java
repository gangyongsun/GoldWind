package com.mdip.web.biz.tabledata.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mdip.web.biz.tabledata.service.ITableDataService;
import com.mdip.web.framework.base.controller.BaseController;
import com.mdip.web.framework.base.entity.PageEntity;
import com.mdip.web.framework.base.entity.ParamsEntity;

@Controller
@RequestMapping("/tableData")
public class TableDataController extends BaseController {

	@Autowired
	@Qualifier("tableDataService")
	ITableDataService tableDataService;

	/**
	 * 作业列表
	 * 
	 * @param model
	 *            页面返回model
	 * @return
	 */
	@RequestMapping("/getList")
	public String getListByWhere(Model model, PageEntity pageEntity) {
		String tableName = "zengkuang_product";
		String where = "";
		String orderb = " 1 ASC";

		pageEntity.setCurrentPage(1);
		pageEntity.setPageSize(2);

		tableDataService.getPage(tableName, where, orderb, pageEntity);

		List resultSetList = (List) pageEntity.getResult();
		model.addAttribute("resultSetList", resultSetList);
		return "/tabledata/tabledata";
	}

	@RequestMapping("/searchData")
	public @ResponseBody PageEntity getListByWhere(PageEntity pageEntity) {
		String tableName = "NRDF2Table";
		String where = "";
		String orderb = " 1 ASC";
		
		ParamsEntity paramsEntity = getParamsEntity();
		String curPage = paramsEntity.getString("curPage");
		pageEntity.setCurrentPage(curPage == null ? 1 : Integer.parseInt(curPage));
		pageEntity.setPageSize(15);

		tableDataService.getPage(tableName, where, orderb, pageEntity);
		return pageEntity;
	}

}
