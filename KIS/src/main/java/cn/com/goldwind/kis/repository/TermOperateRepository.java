package cn.com.goldwind.kis.repository;

import cn.com.goldwind.kis.entity.TermOperate;
import tk.mybatis.mapper.common.Mapper;

public interface TermOperateRepository extends Mapper<TermOperate> {

	TermOperate searchByTermIdAndEmpNO(Integer termId, String empNO);

}
