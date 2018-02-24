package cn.migu.income.dao;

import cn.migu.income.pojo.MiguIncomeDetailReport;
import cn.migu.income.pojo.MiguIncomeDetailReport2;
import cn.migu.income.pojo.MiguProjectProfitReport;
import cn.migu.income.pojo.MiguSectorIncomeReport;

import java.util.List;
import java.util.Map;

public interface MiguTwoNonReportMapper {
	//查询两非项目利润报表总数
	int queryProjectProfitTotal(Map<String, Object> param);
	
	//查询两非项目利润报表详情
	List<MiguProjectProfitReport> queryAllProjectProfit(Map<String, Object> param);
	
	//查询两非项目利润报表详情（不分页）
	List<MiguProjectProfitReport> queryAllProjectProfit2(Map<String, Object> param);
	
	//查询两非项目分部门收益报表总数
	int querySectorIncomeReportTotal(Map<String, Object> param);
	
	//查询两非项目分部门收益报表详情
	List<MiguSectorIncomeReport> querySectorIncomeReport(Map<String, Object> param);
	
	//查询两非项目分部门收益报表详情（不分页）
	List<MiguSectorIncomeReport> querySectorIncomeReport2(Map<String, Object> param);
	
	//查询两非收入明细收益报表总数
	int queryIncomeDetailReportTotal(Map<String, Object> param);
	
	//查询两非收入明细收益报表详情
	List<MiguIncomeDetailReport> queryIncomeDetailReport(Map<String, Object> param);
	
	//查询两非收入明细报表详情（不分页）
	List<MiguIncomeDetailReport> queryIncomeDetailReport2(Map<String, Object> param);

	//查询两非收入明细合并前数据详情
	List<MiguIncomeDetailReport2> queryIncomeDetailReport3(Map<String, Object> param);
}