package cn.migu.income.dao;

import java.util.List;
import java.util.Map;

import cn.migu.income.pojo.TMiguPriceConfigInfo;

public interface TMiguPriceConfigInfoMapper {
	
    int insertSelective(TMiguPriceConfigInfo record);
    
    List<TMiguPriceConfigInfo> selectByprojectId(String projectId);
    
    void deleteByPrimaryKey(Integer priceConfigInfoId);
    
    void updateByPrimaryKey(TMiguPriceConfigInfo miguPriceConfigInfo);
    
    List<TMiguPriceConfigInfo> selectByExample(Map<String, Object> map);

	int queryTotal(Map<String, Object> param);

	List<TMiguPriceConfigInfo> selectMiguPriceConfigByProject(Map<String, Object> param);
 	
	List<TMiguPriceConfigInfo> selectProduct(Map<String, Object> map);
    
    TMiguPriceConfigInfo selectByPrimaryKey(String priceConfigInfoId);

   List<TMiguPriceConfigInfo> selectByCase(String projectId,String month,String productId,String productName);

   List<TMiguPriceConfigInfo> queryAllpriceConfig(Map<String, Object> param);
   
   int  deleteByProjectId(String projectId);
}