package cn.migu.income.service;

import java.util.List;
import java.util.Map;

import cn.migu.income.pojo.MiguProjectBudget;

public interface MiguProjectBudgetService {

	/**
	 * 关联预算
	 * @param miguProjectBudget
	 * @return
	 */
	Map<String, Object> associatedBudget(List<MiguProjectBudget> miguProjectBudget);

	/**
	 * 取消关联
	 * @param miguProjectBudget
	 * @return
	 */
	Map<String, Object> cancelAssociation(List<MiguProjectBudget> miguProjectBudget);

	/**
	 * 查看绑定的预算信息
	 * @param miguProjectBudget
	 * @return
	 */
	Map<String, Object> viewAssociation(MiguProjectBudget miguProjectBudget);

}
