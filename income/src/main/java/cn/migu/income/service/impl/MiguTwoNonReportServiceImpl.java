package cn.migu.income.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.migu.income.dao.MiguTwoNonReportMapper;
import cn.migu.income.pojo.MiguIncomeDetailReport;
import cn.migu.income.pojo.MiguIncomeDetailReport2;
import cn.migu.income.pojo.MiguProjectProfitReport;
import cn.migu.income.pojo.MiguSectorIncomeReport;
import cn.migu.income.service.IMiguTwoNonReportService;

@Service
public class MiguTwoNonReportServiceImpl implements IMiguTwoNonReportService {

	@Autowired
	private MiguTwoNonReportMapper miguTwoNonReportMapper;

	final static Logger log = LoggerFactory.getLogger(MiguTwoNonReportServiceImpl.class);

    /**
     * 查询两非项目利润报表总数
     */
    @Override
	public int queryProjectProfitTotal(String q_projectId, String q_projectName, String q_dept, String q_person){
			Map<String, Object> param = new HashMap<String, Object>();
			if (!"".equals(q_projectId) && q_projectId != null) {
				param.put("q_projectId", q_projectId);
			}
			if (!"".equals(q_projectName) && q_projectName != null) {
				param.put("q_projectName", q_projectName);
			}
			if (!"".equals(q_dept) && q_dept != null) {
				param.put("dept", q_dept);
			}
			if (!"".equals(q_person) && q_person != null) {
				param.put("person", q_person);
			}
			return miguTwoNonReportMapper.queryProjectProfitTotal(param);
    }

    /**
     *查询两非项目利润报表明细 
     */
    @Override
	public List<MiguProjectProfitReport> queryAllProjectProfit(String q_projectId, String q_projectName, String q_dept, String q_person,
			int page, int pageSize){
			Map<String, Object> param = new HashMap<String, Object>();
			if (!"".equals(q_projectId) && q_projectId != null) {
				param.put("q_projectId", q_projectId);
			}
			if (!"".equals(q_projectName) && q_projectName != null) {
				param.put("q_projectName", q_projectName);
			}
			if (!"".equals(q_dept) && q_dept != null) {
				param.put("dept", q_dept);
			}
			if (!"".equals(q_person) && q_person != null) {
				param.put("person", q_person);
			}
			param.put("curPage", page);
			param.put("pageSize", pageSize);
			List<MiguProjectProfitReport> list = miguTwoNonReportMapper.queryAllProjectProfit(param);
			for(int i=0;i<list.size();i++){
				list.get(i).setActualIncome(this.doNull(list.get(i).getActualIncome()));
				list.get(i).setCostAmount(this.doNull(list.get(i).getCostAmount()));
				list.get(i).setProfitAmount(this.getProfit1(list.get(i).getActualIncome(), list.get(i).getCostAmount()));
				list.get(i).setProfitRatio(this.getProfit2(list.get(i).getActualIncome(), list.get(i).getCostAmount()));
				list.get(i).setProfitRatio2(this.getProfit3(list.get(i).getActualIncome(), list.get(i).getCostAmount()));

			}
			return list;
    }
	
    /**
     *导出两非项目利润报表查询
     */
    @Override
	public List<MiguProjectProfitReport> queryAllProjectProfit2(String projectId, String projectName, String dept, String person){
		Map<String, Object> param = new HashMap<String, Object>();
		if (!"".equals(projectId) && projectId != null) {
			param.put("q_projectId", projectId);
		}
		if (!"".equals(projectName) && projectName != null) {
			param.put("q_projectName", projectName);
		}
		if (!"".equals(dept) && dept != null) {
			param.put("dept", dept);
		}
		if (!"".equals(person) && person != null) {
			param.put("person", person);
		}
		List<MiguProjectProfitReport> list = miguTwoNonReportMapper.queryAllProjectProfit2(param);
		for(int i=0;i<list.size();i++){
			list.get(i).setActualIncome(this.doNull(list.get(i).getActualIncome()));
			list.get(i).setCostAmount(this.doNull(list.get(i).getCostAmount()));
			list.get(i).setProfitAmount(this.getProfit1(list.get(i).getActualIncome(), list.get(i).getCostAmount()));
			list.get(i).setProfitRatio(this.getProfit2(list.get(i).getActualIncome(), list.get(i).getCostAmount()));
			list.get(i).setProfitRatio2(this.getProfit3(list.get(i).getActualIncome(), list.get(i).getCostAmount()));
		}
		return list;
	}    
    
    /**
     * 查询两非项目分部门收益报表总数
     */
    @Override
	public int querySectorIncomeReportTotal(String q_dept){
		Map<String, Object> param = new HashMap<String, Object>();
		if (!"".equals(q_dept) && q_dept != null) {
			param.put("dept", q_dept);
		}
		return miguTwoNonReportMapper.querySectorIncomeReportTotal(param);
    }
	
    /**
     * 查询两非项目分部门收益报表明细
     */
    @Override
    public List<MiguSectorIncomeReport> querySectorIncomeReport(String q_dept,int page, int pageSize){
		Map<String, Object> param = new HashMap<String, Object>();
		if (!"".equals(q_dept) && q_dept != null) {
			param.put("dept", q_dept);
		}
		param.put("curPage", page);
		param.put("pageSize", pageSize);
		List<MiguSectorIncomeReport> list = miguTwoNonReportMapper.querySectorIncomeReport(param);
		for(int i=0;i<list.size();i++){
			list.get(i).setEstimateTax(this.doNull(list.get(i).getEstimateTax()));
			list.get(i).setRealTax(this.doNull(list.get(i).getRealTax()));
			list.get(i).setActualIncome(this.doNull(list.get(i).getActualIncome()));
//			list.get(i).setCostAmountTotal(this.doNull(list.get(i).getCostAmountTotal()));
//			list.get(i).setProfitAmount(this.getProfit1(list.get(i).getRealTax(), list.get(i).getCostAmountTotal()));
//			list.get(i).setProfitRatio(this.getProfit2(list.get(i).getRealTax(), list.get(i).getCostAmountTotal()));
//			list.get(i).setProfitRatio2(this.getProfit3(list.get(i).getRealTax(), list.get(i).getCostAmountTotal()));

		}
		return list;
    }
	
    /**
     * 导出两非项目分部门收益报表查询
     */
    @Override
    public List<MiguSectorIncomeReport> querySectorIncomeReport2(String q_dept){
		Map<String, Object> param = new HashMap<String, Object>();
		if (!"".equals(q_dept) && q_dept != null) {
			param.put("dept", q_dept);
		}
		List<MiguSectorIncomeReport> list = miguTwoNonReportMapper.querySectorIncomeReport2(param);
		for(int i=0;i<list.size();i++){
			list.get(i).setEstimateTax(this.doNull(list.get(i).getEstimateTax()));
			list.get(i).setRealTax(this.doNull(list.get(i).getRealTax()));
			list.get(i).setActualIncome(this.doNull(list.get(i).getActualIncome()));
//			list.get(i).setProfitAmount(this.getProfit1(list.get(i).getRealTax(), list.get(i).getCostAmountTotal()));
//			list.get(i).setProfitRatio(this.getProfit2(list.get(i).getRealTax(), list.get(i).getCostAmountTotal()));
//			list.get(i).setProfitRatio2(this.getProfit3(list.get(i).getRealTax(), list.get(i).getCostAmountTotal()));
		}
		return list;
    }
    
    /**
     *查询两非收入明细报表总数 
     */
	public int queryIncomeDetailReportTotal(String q_month_begin,String q_month_end,String q_projectId,String q_projectName){
		Map<String, Object> param = new HashMap<String, Object>();
		if (!"".equals(q_projectId) && q_projectId != null) {
			param.put("q_projectId", q_projectId);
		}
		if (!"".equals(q_projectName) && q_projectName != null) {
			param.put("q_projectName", q_projectName);
		}
		if (!"".equals(q_month_begin) && q_month_begin != null) {
			param.put("q_month_begin", q_month_begin);
		}
		if (!"".equals(q_month_end) && q_month_end != null) {
			param.put("q_month_end", q_month_end);
		}
		return miguTwoNonReportMapper.queryIncomeDetailReportTotal(param);
    }
    /**
     *查询两非收入明细报表
     */
	public List<MiguIncomeDetailReport> queryIncomeDetailReport(String q_month_begin,String q_month_end,String q_projectId,String q_projectName,int page, int pageSize){
		Map<String, Object> param = new HashMap<String, Object>();
		if (!"".equals(q_projectId) && q_projectId != null) {
			param.put("q_projectId", q_projectId);
		}
		if (!"".equals(q_projectName) && q_projectName != null) {
			param.put("q_projectName", q_projectName);
		}
		if (!"".equals(q_month_begin) && q_month_begin != null) {
			param.put("q_month_begin", q_month_begin);
		}
		if (!"".equals(q_month_end) && q_month_end != null) {
			param.put("q_month_end", q_month_end);
		}
		param.put("curPage", page);
		param.put("pageSize", pageSize);
		List<MiguIncomeDetailReport> list = miguTwoNonReportMapper.queryIncomeDetailReport(param);
		for(int i=0;i<list.size();i++){
			if(list.get(i).getIncomeOptionUrl()!= null){
			list.get(i).setIncomeOptionUrl(list.get(i).getIncomeOptionUrl().substring(list.get(i).getIncomeOptionUrl().lastIndexOf("/")+1));
			}
		}
		return list;
    }
    /**
     *导出两非收入明细报表
     */
	public List<MiguIncomeDetailReport> queryIncomeDetailReport2(String q_month_begin,String q_month_end,String q_projectId,String q_projectName){
		Map<String, Object> param = new HashMap<String, Object>();
		if (!"".equals(q_projectId) && q_projectId != null) {
			param.put("q_projectId", q_projectId);
		}
		if (!"".equals(q_projectName) && q_projectName != null) {
			param.put("q_projectName", q_projectName);
		}
		if (!"".equals(q_month_begin) && q_month_begin != null) {
			param.put("q_month_begin", q_month_begin);
		}
		if (!"".equals(q_month_end) && q_month_end != null) {
			param.put("q_month_end", q_month_end);
		}
		List<MiguIncomeDetailReport> list = miguTwoNonReportMapper.queryIncomeDetailReport2(param);
		for(int i=0;i<list.size();i++){
			if(list.get(i).getIncomeOptionUrl() != null){
			list.get(i).setIncomeOptionUrl(list.get(i).getIncomeOptionUrl().substring(list.get(i).getIncomeOptionUrl().lastIndexOf("/")+1));
			}		}
		return list;
    }
	
    /**
     *导出两非收入明细报表（合并前数据）
     */
	public List<MiguIncomeDetailReport2> queryIncomeDetailReport3(String q_month_begin,String q_month_end,String q_projectId,String q_projectName){
		Map<String, Object> param = new HashMap<String, Object>();
		if (!"".equals(q_projectId) && q_projectId != null) {
			param.put("q_projectId", q_projectId);
		}
		if (!"".equals(q_projectName) && q_projectName != null) {
			param.put("q_projectName", q_projectName);
		}
		if (!"".equals(q_month_begin) && q_month_begin != null) {
			param.put("q_month_begin", q_month_begin);
		}
		if (!"".equals(q_month_end) && q_month_end != null) {
			param.put("q_month_end", q_month_end);
		}
		List<MiguIncomeDetailReport2> list = miguTwoNonReportMapper.queryIncomeDetailReport3(param);
		return list;
    }
	
    //判空处理2
    public String doNull(String str){
    	if("".equals(str)||str == null||str ==""){
    		return "0.00";
    	}else{
    		String s1 = String.format("%.2f", Double.valueOf(str));
    		return s1;
    	}
    }
    
    //计算利润金额
    public String getProfit1(String actualIncome,String costAmount){
    	if("".equals(actualIncome)||actualIncome == null||actualIncome == ""){
    		return "0.00";
    	}
    	if("".equals(costAmount)||costAmount == null||costAmount == ""){
    		return "0.00";
    	}
    	String s1 = String.valueOf((Double.valueOf(actualIncome)-Double.valueOf(costAmount)));
    	return String.format("%.2f", Double.valueOf(s1));
    }
    
    //计算成本利润率
    public String getProfit2(String actualIncome,String costAmount){
    	String profit =this.getProfit1(actualIncome, costAmount);
    	if("".equals(costAmount)||costAmount == null||costAmount == ""||"0.00".equals(costAmount)){
    		return "N/A";
    	}
    	if("".equals(profit)||profit == null||profit ==""){
    		return "0.00%";
    	}

    	String s1 = String.valueOf((Double.valueOf(profit)/Double.valueOf(costAmount))*100);
    	return String.format("%.2f", Double.valueOf(s1))+"%";
    }
    
    //计算销售利润率
    public String getProfit3(String actualIncome,String costAmount){
    	String profit =this.getProfit1(actualIncome, costAmount);
    	if("".equals(actualIncome)||actualIncome == null||actualIncome == ""||"0.00".equals(actualIncome)){
    		return "N/A";
    	}
    	if("".equals(profit)||profit == null||profit ==""){
    		return "0.00%";
    	}

    	String s1 = String.valueOf((Double.valueOf(profit)/Double.valueOf(actualIncome))*100);
    	return String.format("%.2f", Double.valueOf(s1))+"%";
    }
}
