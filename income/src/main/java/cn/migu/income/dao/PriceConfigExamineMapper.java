package cn.migu.income.dao;

import java.util.List;
import java.util.Map;

import cn.migu.income.pojo.PriceConfigExamine;

public interface PriceConfigExamineMapper {

	int queryTotal(Map<String, Object> param);

	List<PriceConfigExamine> queryAllExamine(Map<String, Object> param);

	int updatePriceConfig(String auditResult, String priceConfigId, String handlingSuggestion, String performAuditTime);

	int addPriceConfigHistory(String priceConfigId);
	
	

}