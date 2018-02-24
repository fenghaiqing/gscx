package cn.migu.income.dao;

import java.util.List;
import java.util.Map;

import cn.migu.income.pojo.MiguUsers;
import cn.migu.income.pojo.TMiguDepartments;

public interface MiguDepartmentsMapper {
	
	public List<TMiguDepartments> queryAllDepList(Map<String, Object> param) throws Exception;
	
	int selectCount(Map<String, Object> param) throws Exception;
	
	TMiguDepartments selectOne(String deptCode) throws Exception;
	
	int addDep(Map<String, Object> map) throws Exception;
	
	int deleteDep(String deptCode) throws Exception;
	
	int updateDep(TMiguDepartments record) throws Exception;
	
	public List<TMiguDepartments> queryAllDep() throws Exception;
	
}