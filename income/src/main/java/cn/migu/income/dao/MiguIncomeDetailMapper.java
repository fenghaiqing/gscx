package cn.migu.income.dao;

import java.util.List;

import cn.migu.income.pojo.MiguIncomeDetail;
import cn.migu.income.pojo.MiguPrintDetail;
import cn.migu.income.pojo.MiguProjectDetail;

public interface MiguIncomeDetailMapper {
  
    int deleteByPrimaryKey(String incomeDetailId);
    
    int insert(MiguIncomeDetail record);

    int insertSelective(MiguIncomeDetail record);

    MiguIncomeDetail selectByPrimaryKey(String incomeDetailId);
   
    int updateByPrimaryKeySelective(MiguIncomeDetail record);
 
    int updateByPrimaryKey(MiguIncomeDetail record);
    
    List<MiguIncomeDetail> viewIncomeDetails(String incomeManagerId);

	List<MiguIncomeDetail> viewRealIncomeDetails(String incomeManagerId);
	
	List<MiguIncomeDetail> selectByPriceConfigId(String id);
	
    MiguProjectDetail selectProjectDetail(String projectId);

    MiguPrintDetail selectPrintDetail(String projectId);
}