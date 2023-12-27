package cn.com.goldwind.kis.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.goldwind.kis.entity.AccessSummary;
import cn.com.goldwind.kis.entity.KeyInfo;
import tk.mybatis.mapper.common.Mapper;

public interface KeyInfoRepository extends Mapper<KeyInfo> {

	/**
	 * 根据关键词的中or英文名进行查询
	 * 
	 * @param findContent
	 * @return
	 */
	public List<KeyInfo> findByTermName(@Param("findContent") String findContent);

	/**
	 * 搜索二级分类
	 * 
	 * @return
	 */
	public List<String> findTermTypes();

	/**
	 * 按二级类型搜索
	 * 
	 * @param termType
	 * @return
	 */
	public List<KeyInfo> findByTermType(String termType);

	/**
	 * 搜索二级分类术语的数量
	 * 
	 * @param termType
	 * @return
	 */
	public int getNumByType(String termType);

	/**
	 * 搜索热词
	 * 
	 * @param num
	 * @return
	 */
	public List<String> findHotTerms(Integer num);

	/**
	 * 不同类型术语访问量统计
	 * 
	 * @param num
	 * @return
	 */
	public List<AccessSummary> findAccessSummary();

	/**
	 * 不同类型术语的数量
	 * 
	 * @return
	 */
	public List<AccessSummary> findQuantatySummary();

	/**
	 * 不同类型术语访问量统计(三级)
	 * 
	 * @param num
	 * @return
	 */
	public List<AccessSummary> findAccessSummary4backGround();

	/**
	 * 查询术语英文字段列表
	 * 
	 * @return
	 */
	public List<String> findTermList_en();

	/**
	 * 查询术语中文字段列表
	 * 
	 * @return
	 */
	public List<String> findTermList_cn();

	/**
	 * 查询术语英文简称字段列表
	 * 
	 * @return
	 */

	public List<String> findTermList_abbreviationEN();

	/**
	 * 查询术语中文简称字段列表
	 * 
	 * @return
	 */
	public List<String> findTermList_abbreviationCN();

}
