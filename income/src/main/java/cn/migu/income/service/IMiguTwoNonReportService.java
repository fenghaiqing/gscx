package cn.migu.income.service;

import java.util.List;

import cn.migu.income.pojo.MiguIncomeDetailReport;
import cn.migu.income.pojo.MiguIncomeDetailReport2;
import cn.migu.income.pojo.MiguProjectProfitReport;
import cn.migu.income.pojo.MiguSectorIncomeReport;



public interface IMiguTwoNonReportService {
	int queryProjectProfitTotal(String projectId, String projectName, String dept, String person);

	List<MiguProjectProfitReport> queryAllProjectProfit(String projectId, String projectName, String dept, String person,
			 int parseInt, int parseInt2);

	List<MiguProjectProfitReport> queryAllProjectProfit2(String projectId, String projectName, String dept, String person);

	int querySectorIncomeReportTotal(String dept);
	
	List<MiguSectorIncomeReport> querySectorIncomeReport(String dept,int parseInt, int parseInt2);
	
	List<MiguSectorIncomeReport> querySectorIncomeReport2(String dept);
	
	int queryIncomeDetailReportTotal(String q_month_begin,String q_month_end,String q_projectId,String q_projectName);
	
	List<MiguIncomeDetailReport> queryIncomeDetailReport(String q_month_begin,String q_month_end,String q_projectId,String q_projectName,int parseInt, int parseInt2);
	
	List<MiguIncomeDetailReport> queryIncomeDetailReport2(String q_month_begin,String q_month_end,String q_projectId,String q_projectName);

	List<MiguIncomeDetailReport2> queryIncomeDetailReport3(String q_month_begin,String q_month_end,String q_projectId,String q_projectName);

}
