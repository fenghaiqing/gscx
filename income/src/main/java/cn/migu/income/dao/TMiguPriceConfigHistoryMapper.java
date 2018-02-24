package cn.migu.income.dao;

import cn.migu.income.pojo.TMiguPriceConfigHistory;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TMiguPriceConfigHistoryMapper {

	List<TMiguPriceConfigHistory> queryAllPriceConfigInfoHis(String priceConfigId);
}