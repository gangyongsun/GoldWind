package cn.com.goldwind.kis.service.impl;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import cn.com.goldwind.kis.entity.KeyInfo;
import cn.com.goldwind.kis.repository.KeyInfoRepository;
import cn.com.goldwind.kis.service.KeyInfoService;

@Service
public class KeyInfoServiceImpl implements KeyInfoService {

	@Autowired
	private KeyInfoRepository keyInforRepository;
	
	@Override
	public List<KeyInfo> findAll() {
		return keyInforRepository.findAll();
	}

	@Override
	public List<KeyInfo> findByKeyInfo(String keyInfo) {
		return keyInforRepository.findByKeyInfo(keyInfo);
	}

	@Override
	public Page<KeyInfo> findAll(Pageable pageable) {
		return keyInforRepository.findAll(pageable);
	}

	@Override
	public Page<KeyInfo> findByKeyInfo(String keyInfo, Pageable pageable) {
		return keyInforRepository.findByKeyInfo(keyInfo, pageable);
	}

}
