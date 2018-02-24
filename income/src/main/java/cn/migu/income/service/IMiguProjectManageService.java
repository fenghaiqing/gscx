package cn.migu.income.service;

import java.util.List;
import java.util.Map;

import cn.migu.income.pojo.MiguUsers;
import cn.migu.income.pojo.TMiguIncomeCategories;
import cn.migu.income.pojo.TMiguProjectBase;

public interface IMiguProjectManageService
{
	/**
     * 获取收入大类下拉框
     */
    List<TMiguIncomeCategories> queryIncomeClassId() throws Exception;
    
    /**
     * 获取收入小类下拉框
     */
    List<TMiguIncomeCategories> queryIncomeSectionId(String incomeParentId) throws Exception;

    /**
     * 获取项目号
     * @return
     */
	String getProjectId();
	
	/**
	 * 新增项目
	 * @param tMiguProjectBase
	 * @return
	 */
	String saveProject(TMiguProjectBase tMiguProjectBase);
	
	/**
	 * 查询项目总数
	 * @param q_projectId
	 * @param q_projectName
	 * @param q_incomeClassId
	 * @param q_incomeSectionId
	 * @param q_projectUserName
	 * @param q_projectDeptId
	 * @return
	 */
	int queryTotal(String q_projectId, String q_projectName, String q_incomeClassId, String q_incomeSectionId,
			String q_projectUserName, String q_projectDeptId,MiguUsers user);
	/**
	 * 查询项目列表
	 * @param q_projectId
	 * @param q_projectName
	 * @param q_incomeClassId
	 * @param q_incomeSectionId
	 * @param q_projectUserName
	 * @param q_projectDeptId
	 * @param parseInt
	 * @param parseInt2
	 * @param user
	 * @return
	 */
	List<TMiguProjectBase> queryAllProject(String q_projectId, String q_projectName, String q_incomeClassId,
			String q_incomeSectionId, String q_projectUserName, String q_projectDeptId, int page, int pageSize,
			MiguUsers user);

	Map<String, Object> queryAllProjectByUser(String projectId,String projectName,String userId, String page, String rows);
	
	/**
	 * 修改项目
	 * @param tMiguProjectBase
	 * @return
	 */
	String doUpdateProject(TMiguProjectBase tMiguProjectBase);
	
	/**
	 * 删除项目
	 * @param projectId
	 * @return
	 */
	Map<String, Object> dellProject(String projectId);
   
}
