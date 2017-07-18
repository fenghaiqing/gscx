package com.gscx.ssm.dao;

import com.gscx.ssm.pojo.MiguIncomeDetail;
import com.gscx.ssm.pojo.MiguIncomeDetailExample;
import java.math.BigDecimal;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MiguIncomeDetailMapper {
   
    long countByExample(MiguIncomeDetailExample example);

 
    int deleteByExample(MiguIncomeDetailExample example);

   
    int deleteByPrimaryKey(BigDecimal incomeDetailId);

  
    int insert(MiguIncomeDetail record);

 
    int insertSelective(MiguIncomeDetail record);

  
    List<MiguIncomeDetail> selectByExample(MiguIncomeDetailExample example);

   
    MiguIncomeDetail selectByPrimaryKey(BigDecimal incomeDetailId);

  
    int updateByExampleSelective(@Param("record") MiguIncomeDetail record, @Param("example") MiguIncomeDetailExample example);

    int updateByExample(@Param("record") MiguIncomeDetail record, @Param("example") MiguIncomeDetailExample example);

   
    int updateByPrimaryKeySelective(MiguIncomeDetail record);

  
    int updateByPrimaryKey(MiguIncomeDetail record);
}