package cn.migu.income.dao;

import cn.migu.income.pojo.TMiguPriceConfig;
import java.util.List;
import java.util.Map;

public interface TMiguPriceConfigMapper {
	
	

	int insertSelective(TMiguPriceConfig record);

	TMiguPriceConfig selectOneByPrimaryKey(Integer priceConfigId);
	
	void updateByPrimaryKey(TMiguPriceConfig miguPriceConfig);
	
	List<TMiguPriceConfig> selectPriceConfigForPage(Map<String, Object> map);
	
	Integer selectCount(String projectId);
}