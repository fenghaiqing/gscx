package cn.migu.income.dao;

import cn.migu.income.pojo.MiguIncomeManager;
import java.util.List;
import java.util.Map;


public interface MiguIncomeManagerMapper {
  
    int deleteByPrimaryKey(Long incomeManagerId);
  
    int insert(MiguIncomeManager record);

    int insertSelective(MiguIncomeManager record);
  
    MiguIncomeManager selectByPrimaryKey(Long incomeManagerId);

    int updateByPrimaryKeySelective(MiguIncomeManager record);

    int updateByPrimaryKey(MiguIncomeManager record);
    
    List<MiguIncomeManager> selectByExample(Map<String, Object> map);
    
    Integer selectCountByExample(Map<String, Object> map);
    
    MiguIncomeManager selectByUnique(String cycle,String projectId);

	int queryTotal(Map<String, Object> param);

    int queryDrawBillTotal(Map<String, Object> param);
    
    int queryNoBillTotal(Map<String, Object> param);

	List<MiguIncomeManager> queryAllRealIncome(Map<String, Object> param);

    List<MiguIncomeManager> queryAllDrawBillIncome(Map<String, Object> param);
    
    List<MiguIncomeManager> queryAllNoBillIncome(Map<String, Object> param);

    Long getIncomeManagerId();

	MiguIncomeManager selectExamineObj(String incomeManageId, String auditUser);

    int updateBillKey(String projectId, String incomeManagerId, String key);
    
    List<MiguIncomeManager> queryAllBill();
    
    MiguIncomeManager selectRealIncomeByUnique(String cycle,String projectId);
    
    List<Map<String, Object>> selectEstimateIncome(Map<String, Object> param);
    
    int queryRecodes(Map<String, Object> param);
    
    void deleteIncome_manager_his(String incomeManagerId);

	void deleteIncome_detail(String incomeManagerId);

	void deleteIncome_manager(String incomeManagerId);

	List<MiguIncomeManager> queryAllIncomeManager(Map<String, Object> param);

	List<MiguIncomeManager> queryAllRealIncomeManager(Map<String, Object> param);

	int getMergeIncomeTotal(Map<String , Object> map);

	List<MiguIncomeManager> queryMergeIncome(Map<String , Object> map);
	
	
	List<Map<String, Object>> queryMergeRealIncome(Map<String , Object> map);
	
	int getMergeRealIncomeCount(Map<String , Object> map);

	List<MiguIncomeManager> getMergeRealIncome(Map<String, Object> params);

	List<Map<String, Object>> viewMergeDetail(String mergeId);

	void revokeMerge(String mergeId);

	List<MiguIncomeManager> selectById(String id);
}