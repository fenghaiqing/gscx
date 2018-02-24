package cn.migu.income.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.migu.income.dao.IncomeCategoriesMapper;
import cn.migu.income.dao.MiguProjectManageMapper;
import cn.migu.income.pojo.MiguUsers;
import cn.migu.income.pojo.TMiguIncomeCategories;
import cn.migu.income.pojo.TMiguProjectBase;
import cn.migu.income.service.IMiguProjectManageService;
import cn.migu.income.util.MiguConstants;
import cn.migu.income.util.StringUtil;



@Service
public class MiguProjectManageServiceImpl implements IMiguProjectManageService
{
    final static Logger log = LoggerFactory.getLogger(IMiguProjectManageService.class);
    
    @Autowired
    private IncomeCategoriesMapper incomeCategoriesMapper;
    
    @Autowired
    private MiguProjectManageMapper miguProjectManageMapper;
    
    @Override
    public List<TMiguIncomeCategories> queryIncomeClassId() throws Exception
    {
        List<TMiguIncomeCategories> attrList = incomeCategoriesMapper.queryIncomeClassId();
        return attrList;
    }
    
    @Override
    public List<TMiguIncomeCategories> queryIncomeSectionId(String incomeParentId) throws Exception
    {
        List<TMiguIncomeCategories> attrList = incomeCategoriesMapper.queryIncomeSectionId(incomeParentId);
        return attrList;
    }

	@Override
	public String getProjectId() {
		return incomeCategoriesMapper.getProjectId();
	}

	@Override
	public String saveProject(TMiguProjectBase tMiguProjectBase) {
		int result = miguProjectManageMapper.saveProject(tMiguProjectBase);
		return result==1?"100":"9";
	}

	@Override
	public int queryTotal(String q_projectId, String q_projectName, String q_incomeClassId, String q_incomeSectionId,
			String q_projectUserName, String q_projectDeptId,MiguUsers user) {
		Map<String, Object> param = new HashMap<String, Object>();
		if (!"".equals(q_projectId) && q_projectId != null) {
			param.put("q_projectId", q_projectId);
		}
		if (!"".equals(q_projectName) && q_projectName != null) {
			param.put("q_projectName", q_projectName);
		}
		if (!"".equals(q_incomeClassId) && q_incomeClassId != null) {
			param.put("q_incomeClassId", q_incomeClassId);
		}
		if (!"".equals(q_incomeSectionId) && q_incomeSectionId != null) {
			param.put("q_incomeSectionId", q_incomeSectionId);
		}
		if (!"".equals(q_projectUserName) && q_projectUserName != null) {
			param.put("q_projectUserName", q_projectUserName);
		}
		if (!"".equals(q_projectDeptId) && q_projectDeptId != null) {
			param.put("q_projectDeptId", q_projectDeptId);
		}
		if((user.getDeptId()!=null&&user.getDeptId().equals("1"))||user.getUserId().equals("0")){
			param.put("queryModel", "all");
		}else{
			param.put("q_projectDeptId", user.getDeptId());
		}
		return miguProjectManageMapper.queryTotal(param);
	}

	@Override
	public List<TMiguProjectBase> queryAllProject(String q_projectId, String q_projectName, String q_incomeClassId,
			String q_incomeSectionId, String q_projectUserName, String q_projectDeptId, int page, int pageSize,
			MiguUsers user) {
		Map<String, Object> param = new HashMap<String, Object>();
		if (!"".equals(q_projectId) && q_projectId != null) {
			param.put("q_projectId", q_projectId);
		}
		if (!"".equals(q_projectName) && q_projectName != null) {
			param.put("q_projectName", q_projectName);
		}
		if (!"".equals(q_incomeClassId) && q_incomeClassId != null) {
			param.put("q_incomeClassId", q_incomeClassId);
		}
		if (!"".equals(q_incomeSectionId) && q_incomeSectionId != null) {
			param.put("q_incomeSectionId", q_incomeSectionId);
		}
		if (!"".equals(q_projectUserName) && q_projectUserName != null) {
			param.put("q_projectUserName", q_projectUserName);
		}
		if (!"".equals(q_projectDeptId) && q_projectDeptId != null) {
			param.put("q_projectDeptId", q_projectDeptId);
		}
		if((user.getDeptId()!=null&&user.getDeptId().equals("1"))||user.getUserId().equals("0")){
			param.put("queryModel", "all");
		}else{
			param.put("q_projectDeptId", user.getDeptId());
		}
		
		param.put("curPage", page);
		param.put("pageSize", pageSize);
		List<TMiguProjectBase> list = miguProjectManageMapper.queryAllProject(param);
		return list;
	}

	@Override
	public Map<String, Object> queryAllProjectByUser(String projectId,String projectName,String userId, String page, String rows) {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> result = new HashMap<String, Object>();
		param.put("userId", userId);
		if(StringUtil.isEmpty(page)){
			page=MiguConstants.PAGE_INDEX;
		}
		if(StringUtil.isEmpty(rows)){
			page=MiguConstants.DEFAULT_ROWNUM;
		}
		if(StringUtil.isEmpty(projectId)){
			projectId=null;
		}
		if(StringUtil.isEmpty(projectName)){
			projectName=null;
		}
		param.put("projectId", projectId);
		param.put("projectName", projectName);
		param.put("curPage", page);
		param.put("pageSize", rows);
		List<TMiguProjectBase> list = miguProjectManageMapper.queryAllProjectByUser(param);
		Long total =miguProjectManageMapper.getCount(param);
		result.put("total", total);
		result.put("rows", list);
		return result;
	}
	
	@Override
	public String doUpdateProject(TMiguProjectBase tMiguProjectBase) {
		int result = miguProjectManageMapper.doUpdateProject(tMiguProjectBase);
		return result==1?"100":"9";
	}

	@Override
	public Map<String, Object> dellProject(String projectId) {
		Map<String, Object> result = new HashMap<String, Object>();
		int count = miguProjectManageMapper.selectManager(projectId);
		if(count>0){
			result.put("code", "-1");
			result.put("reason", "该项目存在预估、实际收入,无法删除,请先删除预估、实际收入！");
			return result;
		}
		miguProjectManageMapper.deleteConfig_history(projectId);
		miguProjectManageMapper.deleteConfig_info(projectId);
		miguProjectManageMapper.deleteConfig(projectId);
		miguProjectManageMapper.deleteBudget(projectId);
		miguProjectManageMapper.deleteContract(projectId);
		miguProjectManageMapper.deleteProject(projectId);
		result.put("code", "0");
		result.put("reason", "");
		return result;
	}
}
