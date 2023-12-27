package cn.com.goldwind.kis.service;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;

import cn.com.goldwind.kis.entity.KeyInfo;

public interface KeyInfoService {

	/**
	 * 查询所有关键词信息
	 * 
	 * @return
	 */
	public List<KeyInfo> findAll();

	/**
	 * 根据给出的中文或英文模糊查询相关的关键词信息
	 * 
	 * @param keyInfo
	 * @return
	 */
	public List<KeyInfo> findByKeyInfo(String keyInfo);

	public Page<KeyInfo> findAll(Pageable pageable);

	public Page<KeyInfo> findByKeyInfo(String keyInfo, Pageable pageable);

}
