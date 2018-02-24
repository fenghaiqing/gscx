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
import cn.migu.income.dao.PriceConfigExamineMapper;
import cn.migu.income.dao.TMiguPriceConfigHistoryMapper;
import cn.migu.income.pojo.MiguUsers;
import cn.migu.income.pojo.PriceConfigExamine;
import cn.migu.income.pojo.TMiguIncomeCategories;
import cn.migu.income.pojo.TMiguPriceConfigHistory;
import cn.migu.income.pojo.TMiguProjectBase;
import cn.migu.income.service.IMiguProjectManageService;
import cn.migu.income.service.PriceConfigExamineService;
import cn.migu.income.util.StringUtil;



@Service
public class PriceConfigExamineServiceImpl implements PriceConfigExamineService
{
    final static Logger log = LoggerFactory.getLogger(PriceConfigExamineServiceImpl.class);
    
    @Autowired
    private PriceConfigExamineMapper priceConfigExamineMapper;
    
    @Autowired
    private TMiguPriceConfigHistoryMapper priceConfigHistoryMapper;
    
    
    @Override
	public int queryTotal(String q_projectId, String q_projectDeptId, String q_projectUserName,
			String q_priceConfigAuditResult, String userId) {
    	Map<String, Object> param = new HashMap<String, Object>();
    	if (!"".equals(q_projectId) && q_projectId != null) {
			param.put("q_projectId", q_projectId);
		}
		if (!"".equals(q_projectDeptId) && q_projectDeptId != null) {
			param.put("q_projectDeptId", q_projectDeptId);
		}
		if (!"".equals(q_projectUserName) && q_projectUserName != null) {
			param.put("q_projectUserName", q_projectUserName);
		}
		if (!"".equals(q_priceConfigAuditResult) && q_priceConfigAuditResult != null) {
			param.put("q_priceConfigAuditResult", q_priceConfigAuditResult);
		}
		if (!"".equals(userId) && userId != null) {
			param.put("userId", userId);
		}
		return priceConfigExamineMapper.queryTotal(param);
	}


	@Override
	public List<PriceConfigExamine> queryAllExamine(String q_projectId, String q_projectDeptId, String q_projectUserName,
			String q_priceConfigAuditResult, String userId, int page, int pageSize) {
		Map<String, Object> param = new HashMap<String, Object>();
    	if (!"".equals(q_projectId) && q_projectId != null) {
			param.put("q_projectId", q_projectId);
		}
		if (!"".equals(q_projectDeptId) && q_projectDeptId != null) {
			param.put("q_projectDeptId", q_projectDeptId);
		}
		if (!"".equals(q_projectUserName) && q_projectUserName != null) {
			param.put("q_projectUserName", q_projectUserName);
		}
		if (!"".equals(q_priceConfigAuditResult) && q_priceConfigAuditResult != null) {
			param.put("q_priceConfigAuditResult", q_priceConfigAuditResult);
		}
		if (!"".equals(userId) && userId != null) {
			param.put("userId", userId);
		}
		param.put("curPage", page);
		param.put("pageSize", pageSize);
		List<PriceConfigExamine> list = priceConfigExamineMapper.queryAllExamine(param);
		return list;
	}


	@Override
	public String submitExamine(String auditResult, String priceConfigId, String handlingSuggestion) throws Exception{
		int add_result = 0;
		int update_result = priceConfigExamineMapper.updatePriceConfig(auditResult,priceConfigId,handlingSuggestion,StringUtil.getCurrDateStrContainHMS());
		if(update_result==1){
			add_result = priceConfigExamineMapper.addPriceConfigHistory(priceConfigId);
		}else{
			return "-1";
		}
		return add_result==1?"0":"-1";
	}


	@Override
	public List<TMiguPriceConfigHistory> queryAllPriceConfigInfoHis(String priceConfigId) {
		return priceConfigHistoryMapper.queryAllPriceConfigInfoHis(priceConfigId);
	}
}
