package cn.migu.income.dao;

import java.util.List;
import java.util.Map;

import cn.migu.income.pojo.TMiguProjectBase;

public interface MiguProjectManageMapper {

	int saveProject(TMiguProjectBase tMiguProjectBase);

	int queryTotal(Map<String, Object> param);

	List<TMiguProjectBase> queryAllProject(Map<String, Object> param);
	
	List<TMiguProjectBase> queryAllProjectByUser(Map<String, Object> param);
	
	Long getCount(Map<String, Object> param);

	int doUpdateProject(TMiguProjectBase tMiguProjectBase);

	int selectManager(String projectId);

	void deleteConfig_history(String projectId);

	void deleteConfig_info(String projectId);

	void deleteConfig(String projectId);

	void deleteBudget(String projectId);

	void deleteContract(String projectId);

	void deleteProject(String projectId);
	
	List<Map<String, Object>> searchProject(Map<String, Object> map);
	
	int searchProjectCount(Map<String, Object> map);

	TMiguProjectBase selectOneById(String projectId);
}