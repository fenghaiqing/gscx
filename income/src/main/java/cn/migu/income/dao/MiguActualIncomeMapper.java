package cn.migu.income.dao;

import cn.migu.income.pojo.MiguIncomeActual;
import cn.migu.income.pojo.MiguIncomeBill;
import cn.migu.income.pojo.MiguIncomeBillIncome;
import cn.migu.income.pojo.MiguIncomeDiff;
import cn.migu.income.pojo.MiguIncomeManagerHis;
import cn.migu.income.pojo.MiguUsers;
import cn.migu.income.pojo.TMiguDepartments;

import java.util.List;
import java.util.Map;

public interface MiguActualIncomeMapper {
	//查询实际收款项目总数
	int queryTotal(Map<String, Object> param)throws Exception;
	//查询实际收款项目详情
	List<MiguIncomeActual> queryAllActualIncome(Map<String, Object> param)throws Exception;
	//查询单次开票实际收款明细 
	MiguIncomeActual querySglActualIncome(String billingKey) throws Exception;
	//查询实际收款表是否存在此次开票
	Integer queryExistActualIncome(String billingKey) throws Exception;
	//查询实际收款对应项目的开票金额 
	String querySglBillTotal(String billingKey) throws Exception;
	//查询部门列表
	List<TMiguDepartments> queryAllDep() throws Exception;
	//查询部门人员
	List<MiguUsers> querydepPerson(String deptId) throws Exception;
	//新增实际收款表记录 
	int insertIncome(MiguIncomeActual income);
	//更新实际收款表记录
	int updateIncome(MiguIncomeActual income);
	//查询收入差额项目总数
	int queryTotalDiff(Map<String, Object> param);
	//查询收入差额项目详情
	List<MiguIncomeDiff> queryAllIncomeDiff(Map<String, Object> param);
	//导出收入差额查询
	List<MiguIncomeDiff> queryDiffNopage(Map<String, Object> param);
	//新增审核历史
	int insertHisAudit(MiguIncomeManagerHis auditHis);
	//查询审核历史
	List<MiguIncomeManagerHis> queryList(String billingKey, String type);
	
	List<MiguIncomeBillIncome> queryAllIncomeActual(Map<String, Object> param);
	//查询开票明细
	List<MiguIncomeBill> showBilldetail(String billingKey)throws Exception;
	
	//摊分实际收款
	void callPActualIncomeShare(Map<String, Object> map);
}