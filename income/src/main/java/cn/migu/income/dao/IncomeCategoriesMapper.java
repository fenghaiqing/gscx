package cn.migu.income.dao;

import java.util.List;

import cn.migu.income.pojo.TMiguIncomeCategories;


public interface IncomeCategoriesMapper {
    
	List<TMiguIncomeCategories> queryIncomeClassId() throws Exception;
	
	List<TMiguIncomeCategories> queryIncomeSectionId(String incomeParentId) throws Exception;

	String getProjectId();
}