package cn.com.goldwind.kis.repository;

import java.awt.print.Pageable;
import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cn.com.goldwind.kis.entity.KeyInfo;

public interface KeyInfoRepository extends JpaRepository<KeyInfo, String>, Serializable {

	/**
	 * 根据关键词的中or英文名进行查询
	 * 
	 * @param keyInfo
	 * @return
	 */
	@Query(value = "select * from t_key_info where chinese like CONCAT('%',:keyInfo,'%') or english like CONCAT('%',:keyInfo,'%')", nativeQuery = true)
	public List<KeyInfo> findByKeyInfo(@Param("keyInfo") String keyInfo);

	public Page<KeyInfo> findAll(Pageable pageable);

	public Page<KeyInfo> findByKeyInfo(String keyInfo, Pageable pageable);

}
