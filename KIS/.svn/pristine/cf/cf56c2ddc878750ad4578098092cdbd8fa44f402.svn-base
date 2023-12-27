package cn.com.goldwind.kis.mybatis.page;

import java.io.Serializable;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * bootstrap table 分页对象
 * 
 * @author alvin
 *
 * @param <T> 数据集类型
 */
@Data
@RequiredArgsConstructor
public class TableSplitResult<T> implements Serializable {
	private static final long serialVersionUID = -457883346644738554L;

	/**
	 * total代表的是总条数，变量名必须为"total" bootstrap table才能正确显示总条数
	 */
	private Integer total;

	/**
	 * 页码
	 */
	private Integer pageNo;

	/**
	 * 每页条数
	 */
	private Integer pageSize;

	/**
	 * rows是所有的数据(结果集)，这是在后台进行设置好返回到前台的，变量名必须为“rows” bootstrap table才能正确显示数据
	 */
	private T rows;

}