package cn.migu.income.dao;

import java.util.List;

import cn.migu.income.pojo.MiguIncomeBill;

public interface MiguIncomeBillMapper {

  
    int insertSelective(MiguIncomeBill record);
    
    MiguIncomeBill selectOneByMarnagerId(String managerId);
    
    int updateIncomeBill(MiguIncomeBill record);

	int deleteIncomeBill(String billingKey);

	List<MiguIncomeBill> viewBillings(String billingKey);
}