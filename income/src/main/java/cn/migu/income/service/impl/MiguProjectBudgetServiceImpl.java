package cn.migu.income.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import  org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import cn.migu.income.dao.MiguProjectBudgetMapper;
import cn.migu.income.pojo.MiguProjectBudget;
import cn.migu.income.service.MiguProjectBudgetService;
import cn.migu.income.util.RetCode;
import cn.migu.income.util.StringUtil;

@Service
public class MiguProjectBudgetServiceImpl implements MiguProjectBudgetService {

	@Autowired
	private MiguProjectBudgetMapper projectBudgetMapper;

	private static final Logger log =LoggerFactory.getLogger(MiguProjectBudgetServiceImpl.class);
	@Override
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED)
	public Map<String, Object> associatedBudget(List<MiguProjectBudget> miguProjectBudget) {
		// 判断是否为空
		if (miguProjectBudget != null && miguProjectBudget.size() > 0) {
			
			MiguProjectBudget projectBudget = null;
			
			StringBuffer asFail = new StringBuffer("");
			
			StringBuffer assuccess = new StringBuffer("");
			
			Map<String, Object> result=new HashMap<>();
			
			try {
				for (int i = 0; i < miguProjectBudget.size(); i++) {
					
					projectBudget = miguProjectBudget.get(i);
					
					// 判断报账系统项目预算是否已与项目绑定
					List<MiguProjectBudget> plist =projectBudgetMapper.selectOneByUnique(projectBudget.getProjectId(), projectBudget.getBudgetResultId());
					
					if(plist!=null&&plist.size()>0){
						asFail.append(projectBudget.getBudgetProjectNumber()+",");
						continue;
					}
					
					projectBudgetMapper.insertSelective(projectBudget);
					
					assuccess.append(projectBudget.getBudgetProjectNumber()+",");
				}
				result.put("successful", assuccess.toString());
				
				result.put("fail", asFail.toString());
				
				result.put("status", "100");
				return result;
			} catch (Exception e) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				log.error("关联预算异常："+e.getMessage());
				return RetCode.serverError();
			}
			
		} else {
			return RetCode.serverError("项目预算不能为空！");
		}
	}
	@Override
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED)
	public Map<String, Object> cancelAssociation(List<MiguProjectBudget> miguProjectBudget) {
		StringBuffer sb = new StringBuffer();
		if(miguProjectBudget!=null&&miguProjectBudget.size()>0){
			
			MiguProjectBudget projectBudget = null;
			
			try {
				for (int i = 0; i < miguProjectBudget.size(); i++) {
					
					projectBudget = miguProjectBudget.get(i);
					
					projectBudgetMapper.deleteBySelective(projectBudget.getBudgetResultId());
					sb.append(projectBudget.getBudgetProjectNumber()+",");
				}
				return RetCode.success(sb.toString());
			} catch (Exception e) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				log.error("关联预算解绑异常："+e.getMessage());
				return RetCode.serverError();
			}
			
		}else{
			return RetCode.serverError("请选择取消关联的项目！");
		}
	}
	
	
	@Override
	public Map<String, Object> viewAssociation(MiguProjectBudget miguProjectBudget) {
		if(StringUtil.isEmpty(miguProjectBudget.getPage())){
			miguProjectBudget.setPage("1");
		}
		if(StringUtil.isEmpty(miguProjectBudget.getRows())){
			miguProjectBudget.setRows("15");
		}
		if(StringUtil.isEmpty(miguProjectBudget.getProjectId())){
			return null;
		}

		List<MiguProjectBudget> list = projectBudgetMapper.selectByEntity(miguProjectBudget);
		long total = projectBudgetMapper.selectCount(miguProjectBudget);
		Map<String, Object> map = new HashMap<>();
		map.put("rows", list);
		map.put("total", total);
		
		return map;
	}

}
