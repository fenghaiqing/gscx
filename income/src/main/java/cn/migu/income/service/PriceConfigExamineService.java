package cn.migu.income.service;

import java.util.List;

import cn.migu.income.pojo.MiguUsers;
import cn.migu.income.pojo.PriceConfigExamine;
import cn.migu.income.pojo.TMiguIncomeCategories;
import cn.migu.income.pojo.TMiguPriceConfigHistory;
import cn.migu.income.pojo.TMiguProjectBase;

public interface PriceConfigExamineService
{
	
	/**
	 * 定价配置审核查询总数
	 * @param q_projectId
	 * @param q_projectDeptId
	 * @param q_projectUserName
	 * @param q_priceConfigAuditResult
	 * @param userId
	 * @return
	 */
	int queryTotal(String q_projectId, String q_projectDeptId, String q_projectUserName,
			String q_priceConfigAuditResult, String userId);
	
	/**
	 * 定价配置审核查询明细
	 * @param q_projectId
	 * @param q_projectDeptId
	 * @param q_projectUserName
	 * @param q_priceConfigAuditResult
	 * @param userId
	 * @param parseInt
	 * @param parseInt2
	 * @return
	 */
	List<PriceConfigExamine> queryAllExamine(String q_projectId, String q_projectDeptId, String q_projectUserName,
			String q_priceConfigAuditResult, String userId, int page, int rows);

	/**
	 * 定价配置审核
	 * @param auditResult
	 * @param priceConfigId
	 * @param handlingSuggestion
	 * @return
	 */
	String submitExamine(String auditResult, String priceConfigId, String handlingSuggestion) throws Exception;
	
	/**
	 * 审核历史查询
	 * @param priceConfigId
	 * @return
	 */
	List<TMiguPriceConfigHistory> queryAllPriceConfigInfoHis(String priceConfigId);
	
	
}
