package cn.migu.income.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.migu.income.pojo.MiguIncomeActual;
import cn.migu.income.pojo.MiguIncomeBill;
import cn.migu.income.pojo.MiguIncomeBillIncome;
import cn.migu.income.pojo.MiguIncomeDiff;
import cn.migu.income.pojo.MiguIncomeManagerHis;
import cn.migu.income.pojo.MiguUsers;
import cn.migu.income.pojo.TMiguDepartments;

public interface IMiguActualIncomeService {

	int queryTotal(String q_month, String q_projectId, String q_projectName,String q_incomeState,  String q_record_month , String q_incomeClassId ,String q_incomeSectionId,
	        String q_dept, String q_userName ,String q_bill_num , String q_bill_total,
	        String q_income, String income_date, MiguUsers user) throws Exception;

	List<MiguIncomeActual> queryAllActualIncome(String q_month, String q_projectId, String q_projectName,
			String q_incomeState,  String q_record_month , String q_incomeClassId ,String q_incomeSectionId,
	        String q_dept, String q_userName ,String q_bill_num , String q_bill_total,
	        String q_income, String income_date,
			int parseInt, int parseInt2, MiguUsers user) throws Exception;

	MiguIncomeActual querySglActualIncome(String billingKey) throws Exception;
	
	Integer queryExistActualIncome(String billingKey) throws Exception;
	
	String querySglBillTotal(String billingKey) throws Exception;
	
    List<TMiguDepartments> queryAllDep() throws Exception;

	List<MiguUsers> querydepPerson(String deptId) throws Exception;

	Map<String, Object> addIncome(HttpServletRequest request, HttpServletResponse resonse);

	Map<String, Object> updateIncome(HttpServletRequest request, HttpServletResponse resonse);

	int queryTotalDiff(String q_projectId, String q_projectName, String q_dept, String q_person, String q_month_start,
			String q_month_end);

	List<MiguIncomeDiff> queryAllIncomeDiff(String q_projectId, String q_projectName, String q_dept, String q_person,
			String q_month_start, String q_month_end, int parseInt, int parseInt2);

	List<MiguIncomeDiff> queryDiffNopage(String projectId, String projectName, String dept, String person,
			String monthStart, String monthEnd);

	List<MiguIncomeManagerHis> queryList(String billingKey, String type);

	Map<String, Object> doExamine(Map<String, String> map, String title, String url, String type) throws Exception;

	List<MiguIncomeBillIncome> queryAllIncomeActual(String q_month, String q_projectId, String q_projectName,
			String q_incomeState, String billingKeys,  String q_record_month , String q_incomeClassId ,String q_incomeSectionId,
	        String q_dept, String q_userName ,String q_bill_num , String q_bill_total,
	        String q_income, String income_date, MiguUsers user) throws Exception;

	List<MiguIncomeBill> showBilldetail(String billingKey) throws Exception;

}
