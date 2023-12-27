package cn.com.goldwind.kis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.goldwind.kis.entity.TermOperate;
import cn.com.goldwind.kis.repository.TermOperateRepository;
import cn.com.goldwind.kis.service.TermOperateService;

@Service
public class TermOperateServiceImpl implements TermOperateService {

	@Autowired
	private TermOperateRepository termOperateRepository;

	@Override
	public TermOperate findByTermIdAndEmpNO(Integer termId, String empNO) {
		return termOperateRepository.searchByTermIdAndEmpNO(termId, empNO);
	}

	@Override
	public int updateTermOperate(TermOperate termOperate) {
		return termOperateRepository.updateByPrimaryKeySelective(termOperate);
	}

	@Override
	public int saveTermOperate(TermOperate newTermOperate) {
		return termOperateRepository.insertSelective(newTermOperate);
	}
}
