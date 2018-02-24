package cn.migu.income.dao;

import cn.migu.income.pojo.MiguProjectBudget;
import java.util.List;

public interface MiguProjectBudgetMapper {

    int insert(MiguProjectBudget record);
   
    int insertSelective(MiguProjectBudget record);
    
    void updateBySelective(MiguProjectBudget record);
    
    MiguProjectBudget selectOneById(String budgetId);
    
    List<MiguProjectBudget> selectOneByUnique(String projectId,String budgetId);
    
    void deleteBySelective(String budgetId);
    
    List<MiguProjectBudget> selectByEntity(MiguProjectBudget record);
    
    long selectCount(MiguProjectBudget record);
}