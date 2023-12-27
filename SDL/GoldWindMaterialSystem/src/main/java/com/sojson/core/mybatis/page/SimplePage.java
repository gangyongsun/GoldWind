package com.sojson.core.mybatis.page;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SimplePage implements Paginable {
	private static final long serialVersionUID = 1L;
	public static final int DEF_COUNT = 20;

	protected int totalCount = 0;
	protected int pageSize = 20;
	protected int pageNo = 1;
	protected int filterNo;

	public SimplePage(int pageNo, int pageSize, int totalCount) {
		this.totalCount = totalCount <= 0 ? 0 : totalCount;
		this.pageSize = pageSize <= 0 ? DEF_COUNT : pageSize;
		this.pageNo = pageNo <= 0 ? 1 : pageNo;
		if ((this.pageNo - 1) * this.pageSize >= totalCount) {
			this.pageNo = totalCount / pageSize == 0 ? 1 : totalCount / pageSize;
		}
	}

	/**
	 * 调整分页参数，使合理化
	 */
	public void adjustPage() {
		if (totalCount <= 0) {
			totalCount = 0;
		}
		if (pageSize <= 0) {
			pageSize = DEF_COUNT;
		}
		if (pageNo <= 0) {
			pageNo = 1;
		}
		if ((pageNo - 1) * pageSize >= totalCount) {
			pageNo = totalCount / pageSize;
		}
	}

	public int getTotalPage() {
		int totalPage = totalCount / pageSize;
		if (totalCount % pageSize != 0 || totalPage == 0) {
			totalPage++;
		}
		return totalPage;
	}

	public boolean isFirstPage() {
		return pageNo <= 1;
	}

	public boolean isLastPage() {
		return pageNo >= getTotalPage();
	}

	public int getNextPage() {
		if (isLastPage()) {
			return pageNo;
		} else {
			return pageNo + 1;
		}
	}

	public int getPrePage() {
		if (isFirstPage()) {
			return pageNo;
		} else {
			return pageNo - 1;
		}
	}

}
