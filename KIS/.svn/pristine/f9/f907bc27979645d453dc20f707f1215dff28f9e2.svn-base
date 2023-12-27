package cn.com.goldwind.kis.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.com.goldwind.kis.bo.DailyAccessBo;
import cn.com.goldwind.kis.entity.WebAccessLog;
import tk.mybatis.mapper.common.Mapper;

public interface WebAccessLogRepository extends Mapper<WebAccessLog> {

	int searchTotalAccessCount();

	int searchTotalAccessEmp();

	List<DailyAccessBo> getRecent30Tendency(@Param("timePeriod") int i);

	List<DailyAccessBo> getCurrentSeasionTendency();

	List<DailyAccessBo> getLastSeasionTendency();

	List<DailyAccessBo> getCurrentYearTendency();

	List<DailyAccessBo> getLastYearTendency();

	List<DailyAccessBo> getTendency();
}
