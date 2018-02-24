package cn.migu.income.dao;

import cn.migu.income.pojo.MiguIncomeManagerHis;
import java.util.List;

public interface MiguIncomeManagerHisMapper {
   
    int deleteByPrimaryKey(String incomeManagerHisId);

    int insert(MiguIncomeManagerHis record);

    int insertSelective(MiguIncomeManagerHis record);

    MiguIncomeManagerHis selectByPrimaryKey(String incomeManagerHisId);

    int updateByPrimaryKeySelective(MiguIncomeManagerHis record);
    
    int updateByPrimaryKey(MiguIncomeManagerHis record);
    
    List<MiguIncomeManagerHis> queryList(String incomeManagerId,String type);
}