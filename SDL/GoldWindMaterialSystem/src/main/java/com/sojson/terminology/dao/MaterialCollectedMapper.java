package com.sojson.terminology.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.sojson.terminology.bo.MaterialCollected;

/**
 * 物资信息接口
 * 
 * @author alvin
 *
 */
public interface MaterialCollectedMapper {

	int deleteByPrimaryKey(Long id);

	Map<String, Object> deleteMaterialById(String ids);

	int insert(MaterialCollected record);

	int insertSelective(MaterialCollected record);

	MaterialCollected selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(MaterialCollected record);

	int updateByPrimaryKey(MaterialCollected record);

	MaterialCollected searchByNameAndUid(@Param("materialName") String materialName, @Param("uid") Long uid);

	MaterialCollected searchByMidAndUid(@Param("materialId") String materialId, @Param("uid") Long uid);

	int deleteCollect(MaterialCollected materialCollected);
}
