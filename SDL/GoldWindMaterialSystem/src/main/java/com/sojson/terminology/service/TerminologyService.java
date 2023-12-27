
package com.sojson.terminology.service;

import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.sojson.core.mybatis.page.Pagination;
import com.sojson.terminology.bo.MaterialCollected;

/**
 * 关键词查询相关服务
 * 
 * @author alvin
 *
 */
public interface TerminologyService {
	/**
	 * 查询关键词
	 * 
	 * @param token
	 * @param terminologyWord
	 * @param languageId
	 * @return
	 */
	public JSONArray search(String token, String terminologyWord, int languageId);

	/**
	 * 查询收藏物资信息
	 * 
	 * @param map
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Pagination<MaterialCollected> findByPage(Map<String, Object> map, Integer pageNo, int pageSize);
	
	int deleteByPrimaryKey(Long id);
	
	/**
	 * 根据UID+Material Name，取消收藏物资或关键词
	 * 
	 * @return
	 */
	int deleteCollect(MaterialCollected materialCollected);
	
	/**
	 * 根据主键id 删除收藏的物资或关键词
	 * @param ids
	 * @return
	 */
	Map<String, Object> deleteMaterialByIds(String ids);
	
	int insert(MaterialCollected record);

	int insertSelective(MaterialCollected record);

	MaterialCollected selectByPrimaryKey(Long id);
	
	/**
	 * 根据收藏的关键词(物资)Name和用户ID查询
	 * 
	 * @param materialName
	 * @param uid
	 * @return
	 */
	MaterialCollected searchByNameAndUid(String materialName,Long uid);
	
	/**
	 * 根据收藏的关键词(物资)ID和用户ID查询
	 * 
	 * @param materialId
	 * @param uid
	 * @return
	 */
	MaterialCollected searchByMidAndUid(String materialId,Long uid);

	int updateByPrimaryKeySelective(MaterialCollected record);

	int updateByPrimaryKey(MaterialCollected record);

}
