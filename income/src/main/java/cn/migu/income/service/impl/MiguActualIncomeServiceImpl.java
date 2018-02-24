package cn.migu.income.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSON;

import cn.migu.income.dao.MiguActualIncomeMapper;
import cn.migu.income.pojo.MiguIncomeActual;
import cn.migu.income.pojo.MiguIncomeBill;
import cn.migu.income.pojo.MiguIncomeBillIncome;
import cn.migu.income.pojo.MiguIncomeDiff;
import cn.migu.income.pojo.MiguIncomeManagerHis;
import cn.migu.income.pojo.MiguUsers;
import cn.migu.income.pojo.TMiguDepartments;
import cn.migu.income.service.IMiguActualIncomeService;
import cn.migu.income.util.CreatOATaskUtil;
import cn.migu.income.util.GetFileSize;
import cn.migu.income.util.MiguConstants;
import cn.migu.income.util.PropValue;
import cn.migu.income.util.RetCode;
import cn.migu.income.util.StringUtil;
import oracle.jdbc.driver.OracleTypes;
import cn.migu.income.util.IDGenUtil;


@Service
public class MiguActualIncomeServiceImpl implements IMiguActualIncomeService {

	@Autowired
	private MiguActualIncomeMapper actualIncomeMapper;

	private final static String INCOME_OA_TITLE = "实际收款审核";
	final static Logger log = LoggerFactory.getLogger(MiguActualIncomeServiceImpl.class);

	/**
	 * 实际收款查询总数
	 */
	@Override
	public int queryTotal(String q_month, String q_projectId, String q_projectName, String q_incomeState,  String q_record_month , String q_incomeClassId ,String q_incomeSectionId,
	        String q_dept, String q_userName ,String q_bill_num , String q_bill_total,
	        String q_income, String income_date,
			MiguUsers user) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		if (!"".equals(q_projectId) && q_projectId != null) {
			param.put("q_projectId", q_projectId);
		}
		if (!"".equals(q_projectName) && q_projectName != null) {
			param.put("q_projectName", q_projectName);
		}
		if (!"".equals(q_month) && q_month != null) {
			param.put("q_month", q_month);
		}
		if (!"".equals(q_incomeState) && q_incomeState != null) {
			System.out.println(q_incomeState.length());
			param.put("q_incomeState", q_incomeState);
		}
		if ((user.getDeptId() != null && user.getDeptId().equals("1")) || user.getUserId().equals("0")) {
			param.put("queryModel", null);
		} else {
			param.put("q_userId", user.getUserId());
		}
		
		param.put("q_record_month", q_record_month);
		param.put("q_incomeClassId",q_incomeClassId);
		param.put("q_incomeSectionId", q_incomeSectionId);
		param.put("q_dept", q_dept);
		param.put("q_userName", q_userName);
		param.put("q_bill_num",q_bill_num);
		param.put("q_bill_total",q_bill_total);
		param.put("q_income",q_income);
		param.put("income_date", income_date);
		
		return actualIncomeMapper.queryTotal(param);
	}

	/**
	 * 实际收款查询明细
	 */
	@Override
	public List<MiguIncomeActual> queryAllActualIncome(String q_month, String q_projectId, String q_projectName,
			String q_incomeState,  String q_record_month , String q_incomeClassId ,String q_incomeSectionId,
	        String q_dept, String q_userName ,String q_bill_num , String q_bill_total,
	        String q_income, String income_date,int page, int pageSize, MiguUsers user) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		if (!"".equals(q_projectId) && q_projectId != null) {
			param.put("q_projectId", q_projectId);
		}
		if (!"".equals(q_projectName) && q_projectName != null) {
			param.put("q_projectName", q_projectName);
		}
		if (!"".equals(q_month) && q_month != null) {
			param.put("q_month", q_month);
		}
		if (!"".equals(q_incomeState) && q_incomeState != null) {
			param.put("q_incomeState", q_incomeState);
		}
		if ((user.getDeptId() != null && user.getDeptId().equals("1")) || user.getUserId().equals("0")) {
			param.put("queryModel", null);
		} else {
			param.put("q_userId", user.getUserId());
		}
		param.put("q_record_month", q_record_month);
		param.put("q_incomeClassId",q_incomeClassId);
		param.put("q_incomeSectionId", q_incomeSectionId);
		param.put("q_dept", q_dept);
		param.put("q_userName", q_userName);
		param.put("q_bill_num",q_bill_num);
		param.put("q_bill_total",q_bill_total);
		param.put("q_income",q_income);
		param.put("income_date", income_date);
		param.put("curPage", page);
		param.put("pageSize", pageSize);
		List<MiguIncomeActual> list = actualIncomeMapper.queryAllActualIncome(param);
		return list;
	}
	
	/**
	 * 查询审核历史
	 * @param billingKey
	 * @param type
	 * @return
	 */
	@Override
	public List<MiguIncomeManagerHis> queryList(String billingKey, String type) {
		List<MiguIncomeManagerHis> list = actualIncomeMapper.queryList(billingKey, type);
		return list;
    }
	
	/**
	 *查询单次开票实际收款明细 
	 */
	@Override
	public MiguIncomeActual querySglActualIncome(String billingKey) throws Exception{
		MiguIncomeActual income = new MiguIncomeActual();
		if(StringUtil.isNotEmpty(billingKey)){
			income = actualIncomeMapper.querySglActualIncome(billingKey);
		}
		return income;
	}
	
	/**
	 *查询实际收款表是否存在此次开票
	 */
	@Override
	public Integer queryExistActualIncome(String billingKey) throws Exception{
			return actualIncomeMapper.queryExistActualIncome(billingKey);
	}
	
	/**
	 * 查询实际收款对应项目的开票金额 
	 */
	@Override
	public String querySglBillTotal(String billingKey) throws Exception{
		String billTotal = actualIncomeMapper.querySglBillTotal(billingKey);
		return billTotal;
	};
	/**
	 * 查询所有部门
	 */
    @Override
    public List<TMiguDepartments> queryAllDep() throws Exception
    {
        List<TMiguDepartments> list = actualIncomeMapper.queryAllDep();
        return list;
    }
    
    /**
     * 查询所属部门的所有人员
     */
    @Override
    public List<MiguUsers> querydepPerson(String deptId) throws Exception
    {
    	List<MiguUsers> list =new ArrayList<MiguUsers>();
		if(StringUtil.isNotEmpty(deptId)){
        list = actualIncomeMapper.querydepPerson(deptId);
		}
        return list;
    }
    
    /**
     * 更新实际收款（包括开票、不开票）
     */
    @Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public Map<String, Object> updateIncome(HttpServletRequest request, HttpServletResponse resonse){
		Map<String, String> map = new HashMap<String, String>();
		HttpSession session = request.getSession();
		MiguUsers user = (MiguUsers)(session.getAttribute(MiguConstants.USER_KEY));
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		FileItem fileItem = null;
		try {
			List<?> items = upload.parseRequest(request);
			Iterator<?> itr = items.iterator();
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				if (!item.isFormField()) {
					if (item.getName() != null && !"".equals(item.getName())) {
						fileItem = item;
					}
				} else {
					map.put(item.getFieldName(), item.getString("utf-8"));
				}
			}
			String billingKey = null;
			//获取实际收款明细
			billingKey =map.get("billingKey");
			MiguIncomeActual income = actualIncomeMapper.querySglActualIncome(billingKey);
			income.setIncome(map.get("income"));
			income.setIncomeStatus("1");//提交审核
			income.setIncomeDate(map.get("q_actualMonth"));
			income.setAuditDept(map.get("Audit_dept"));
			income.setAuditPerson(map.get("Audit_person"));
			income.setBzCycle(StringUtil.getTimeStamp("yyyyMM"));
			//保存文件
			this.saveFile(fileItem, income);
			
			int incomeSign = 0;
			//查询是否存在收款表是否存在记录
			if(actualIncomeMapper.queryExistActualIncome(billingKey) > 0){
				incomeSign = actualIncomeMapper.updateIncome(income);
			}else{
				income.setIsNeedBill("1");
				incomeSign = actualIncomeMapper.insertIncome(income);
			}
			//更新实际收款
			if(incomeSign > 0){
				MiguIncomeManagerHis auditHis = new MiguIncomeManagerHis();
				auditHis.setIncomeManagerHisId(IDGenUtil.UUID());
				auditHis.setBillingKey(billingKey);
				auditHis.setAuditDept(user.getDeptId());
				auditHis.setAuditPerson(user.getUserId());
				auditHis.setCreateDate(StringUtil.getTimeStamp("yyyy-MM-dd HH:mm:ss"));
				auditHis.setDealResult("0");//提交审核
				auditHis.setDealOptions("");
				auditHis.setType("A");//实际收款审核
				//提交审核历史
				if(actualIncomeMapper.insertHisAudit(auditHis) > 0){
					//新增OA流程
					Map<String, String> params = new HashMap<String, String>();
					params.put("userId", user.getUserId());
					params.put("code", IDGenUtil.UUID());
					params.put("userName", user.getUserName());

					params.put("auditUser",map.get("Audit_person"));
					params.put("optType", "1");//新建
					params.put("title", INCOME_OA_TITLE);
					params.put("status", "0");//待办
					params.put("clazz", INCOME_OA_TITLE);
					params.put("url",PropValue.getPropValue("EXAMINE_INCOME_URL") + "?billingKey="+String.valueOf(billingKey)+"&auditUser="+map.get("Audit_person")+"&code="+params.get("code"));
					//创建OA代办
					String resp = CreatOATaskUtil.createOATask(params);
					@SuppressWarnings("unchecked")
					Map<String, String> result =(Map<String, String>)JSON.parse(resp);
					if(StringUtil.isNotEmpty(resp)){
						if("1".equals(result.get("rsp"))){
							return RetCode.success();
						}
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						log.error("创建OA代办异常：" + result.get("errDesc"));
						return RetCode.serverError("创建OA代办失败！");
					}else{
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						log.error("创建OA代办异常：" + result.get("errDesc"));
						return RetCode.serverError("创建OA代办失败！");
					}
				}else{
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return RetCode.serverError("提交审核历史失败！");
				}
			}else{
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return RetCode.serverError("更新实际收款失败！");
			}
			
		} catch (Exception e) {
			log.error("更新实际收款,提交审核异常：" + e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return RetCode.serverError("更新实际收款提交审核失败！");
		}
    }
    
    /**
     * 新增实际收款（不开票）
     */
    @Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public Map<String, Object> addIncome(HttpServletRequest request, HttpServletResponse resonse){
		Map<String, String> map = new HashMap<String, String>();
		HttpSession session = request.getSession();
		MiguUsers user = (MiguUsers)(session.getAttribute(MiguConstants.USER_KEY));
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		FileItem fileItem = null;
		try {
			List<?> items = upload.parseRequest(request);
			Iterator<?> itr = items.iterator();
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				if (!item.isFormField()) {
					if (item.getName() != null && !"".equals(item.getName())) {
						fileItem = item;
					}
				} else {
					map.put(item.getFieldName(), item.getString("utf-8"));
				}
			}
			String billingKey = null;
			billingKey = IDGenUtil.UUID();
			//获取新增实际收款明细
			MiguIncomeActual income = new MiguIncomeActual();
			income.setBillingKey(billingKey);
			income.setCycle(map.get("monthId"));
			income.setProjectId(map.get("projectId"));
			income.setProjectName(map.get("projectName"));
			income.setIncome(map.get("income_add"));
			income.setIncomeStatus("1");//提交审核
			income.setIncomeDate(map.get("q_actualMonth_add"));
			income.setAuditDept(map.get("Audit_dept_add"));
			income.setAuditPerson(map.get("Audit_person_add"));
			income.setIsNeedBill("0");
			income.setBzCycle(StringUtil.getTimeStamp("yyyyMM"));
			//保存文件
			this.saveFile(fileItem, income);
			//新增实际收款
			int incomeSign = 0;
			incomeSign = actualIncomeMapper.insertIncome(income);
			//插入审核历史，提交oa代办审核
			if(incomeSign > 0){
				MiguIncomeManagerHis auditHis = new MiguIncomeManagerHis();
				auditHis.setIncomeManagerHisId(IDGenUtil.UUID());
				auditHis.setBillingKey(billingKey);
				auditHis.setAuditDept(user.getDeptId());
				auditHis.setAuditPerson(user.getUserId());
				auditHis.setCreateDate(StringUtil.getTimeStamp("yyyy-MM-dd HH:mm:ss"));
				auditHis.setDealResult("0");//提交审核
				auditHis.setDealOptions("");
				auditHis.setType("A");//实际收款审核
				//提交审核历史
				if(actualIncomeMapper.insertHisAudit(auditHis) > 0){
					//新增OA流程
					Map<String, String> params = new HashMap<String, String>();
					params.put("userId", user.getUserId());
					params.put("code", IDGenUtil.UUID());
					params.put("userName", user.getUserName());

					params.put("auditUser",map.get("Audit_person_add"));
					params.put("optType", "1");//新建
					params.put("title", INCOME_OA_TITLE);
					params.put("status", "0");//待办
					params.put("clazz", INCOME_OA_TITLE);
					params.put("url",PropValue.getPropValue("EXAMINE_INCOME_URL") + "?billingKey="+String.valueOf(billingKey)+"&auditUser="+map.get("Audit_person_add")+"&code="+params.get("code"));
					//创建OA代办
					String resp = CreatOATaskUtil.createOATask(params);
					@SuppressWarnings("unchecked")
					Map<String, String> result =(Map<String, String>)JSON.parse(resp);
					if(StringUtil.isNotEmpty(resp)){
						if("1".equals(result.get("rsp"))){
							return RetCode.success();
						}
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						log.error("【新增实际收款】创建OA代办异常：" + result.get("errDesc"));
						return RetCode.serverError("创建OA代办失败！");
					}else{
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						log.error("【新增实际收款】创建OA代办异常：" + result.get("errDesc"));
						return RetCode.serverError("创建OA代办失败！");
					}
				}else{
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return RetCode.serverError("提交审核历史失败！");
				}
			}else{
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return RetCode.serverError("新增实际收款数据失败！");
			}

		} catch (Exception e) {
			log.error("【新增实际收款】提交审核异常：" + e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return RetCode.serverError("新增实际收款提交审核失败！");
		}
    }
    
    /**
     * 审核实际收款
     * @param map
     * @param title
     * @param url
     * @param type
     * @return
     * @throws Exception 
     */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public Map<String, Object> doExamine(Map<String, String> map,String title,String url,String type) throws Exception {
		try {
			String status = map.get("eastimateStatus");
			MiguIncomeActual  income = actualIncomeMapper.querySglActualIncome(map.get("billingKey"));
			String person=income.getAuditPerson();
			// 保存审核历史
			MiguIncomeManagerHis auditHis = new MiguIncomeManagerHis();
			auditHis.setIncomeManagerHisId(IDGenUtil.UUID());
			auditHis.setBillingKey(income.getBillingKey());
			auditHis.setAuditDept(income.getAuditDept());
			auditHis.setAuditPerson(income.getAuditPerson());
			auditHis.setDealResult(status);
			auditHis.setDealOptions(map.get("auditOption"));
			auditHis.setCreateDate(StringUtil.getTimeStamp("yyyy-MM-dd HH:mm:ss"));
			auditHis.setType(type);
			this.actualIncomeMapper.insertHisAudit(auditHis);

			income.setAuditDept(map.get("auditDept"));
			income.setAuditPerson(map.get("auditUser"));
			income.setIncomeStatus(status);

			actualIncomeMapper.updateIncome(income);
			
			// 流程结束 status==2 摊分实际收款
			if("2".equals(status)){
				Map<String, Object> param = new HashMap<>();
				param.put("V_CYCLE", income.getCycle());
				param.put("V_PROJECT_ID", income.getProjectId());
				param.put("V_BILLING_KEY", income.getBillingKey());
				param.put("V_RETURN", OracleTypes.VARCHAR);
				actualIncomeMapper.callPActualIncomeShare(param);
				if(param.get("V_RETURN").toString().equals("-1")){
					log.error("实际收款审核失败：摊分实际收款失败！" );
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return RetCode.serverError("实际收款审核失败，摊分实际收款失败！");
				}
							
			}
			
			// 将代办转为已办
			Map<String, String> params = new HashMap<>();
			params.put("userId",person);
			params.put("optType", "2");
			params.put("status", "1");
			params.put("title", title);
			params.put("clazz", title);
			params.put("auditUser",person);
			params.put("userName", map.get("userName"));
			params.put("code", map.get("code"));
			params.put("url", url + "?billingKey=" + map.get("billingKey") + "&auditUser="
					+ income.getAuditPerson() + "&code=" + map.get("code"));
			// 创建代办
			String result = CreatOATaskUtil.createOATask(params);
			Map<String, String> resp = (Map<String, String>) JSON.parse(result);
			if (!"1".equals(resp.get("rsp"))) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				log.error("审核实际收款异常 ：修改OA代办异常 返回信息"+result);
				return RetCode.serverError("审核失败！");
			}
			// 新增代办
			if (!"2".equals(status) && !"3".equals(status)) {
				params.put("userId", map.get("userId"));
				params.put("optType", "1");
				params.put("status", "0");
				params.put("title", title);
				params.put("clazz", title);
				params.put("auditUser",
						StringUtil.isEmpty(map.get("auditUser")) ? map.get("userId") : map.get("auditUser"));
				params.put("userName", map.get("userName"));
				params.put("code", IDGenUtil.UUID());
				params.put("url", url + "?billingKey=" +income.getBillingKey()
						+ "&auditUser=" + income.getAuditPerson()+ "&code=" + params.get("code"));
				result = CreatOATaskUtil.createOATask(params);
				resp = (Map<String, String>) JSON.parse(result);
				if (!"1".equals(resp.get("rsp"))) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					log.error("审核实际收款异常 ：创建OA代办异常 返回信息"+result);
					return RetCode.serverError("审核失败！");
				}
			}
			
			return RetCode.success();
		} catch (NumberFormatException e) {
			log.error("创建OA代办异常：" + e.getMessage());
			return RetCode.serverError("实际收款审核失败！");
		}

	}
    /**
     * 查询收入差额条目总数
     */
    @Override
	public int queryTotalDiff(String q_projectId, String q_projectName, String q_dept, String q_person, String q_month_start,
			String q_month_end){
			Map<String, Object> param = new HashMap<String, Object>();
			if (!"".equals(q_projectId) && q_projectId != null) {
				param.put("q_projectId", q_projectId);
			}
			if (!"".equals(q_projectName) && q_projectName != null) {
				param.put("q_projectName", q_projectName);
			}
			if (!"".equals(q_dept) && q_dept != null) {
				param.put("q_dept", q_dept);
			}
			if (!"".equals(q_person) && q_person != null) {
				param.put("q_person", q_person);
			}
			if (!"".equals(q_month_start) && q_month_start != null) {
				param.put("q_month_start", q_month_start);
			}
			if (!"".equals(q_month_end) && q_month_end != null) {
				param.put("q_month_end", q_month_end);
			}
			return actualIncomeMapper.queryTotalDiff(param);
    }

    /**
     *查询收入差额明细 
     */
    @Override
	public List<MiguIncomeDiff> queryAllIncomeDiff(String q_projectId, String q_projectName, String q_dept, String q_person,
			String q_month_start, String q_month_end, int page, int pageSize){
			Map<String, Object> param = new HashMap<String, Object>();
			if (!"".equals(q_projectId) && q_projectId != null) {
				param.put("q_projectId", q_projectId);
			}
			if (!"".equals(q_projectName) && q_projectName != null) {
				param.put("q_projectName", q_projectName);
			}
			if (!"".equals(q_dept) && q_dept != null) {
				param.put("q_dept", q_dept);
			}
			if (!"".equals(q_person) && q_person != null) {
				param.put("q_person", q_person);
			}
			if (!"".equals(q_month_start) && q_month_start != null) {
				param.put("q_month_start", q_month_start);
			}
			if (!"".equals(q_month_end) && q_month_end != null) {
				param.put("q_month_end", q_month_end);
			}
			param.put("curPage", page);
			param.put("pageSize", pageSize);
			List<MiguIncomeDiff> list = actualIncomeMapper.queryAllIncomeDiff(param);
			for(int i=0;i<list.size();i++){
				list.get(i).setDeferenceInIncome(this.doNull(list.get(i).getDeferenceInIncome()));
				list.get(i).setEstimateIncome(this.doNull(list.get(i).getEstimateIncome()));
				list.get(i).setRealIncome(this.doNull(list.get(i).getRealIncome()));
			}
            for(int i = 0; i<list.size();i++){
        	   //实际收入没有审核通过时，实际收入和差额置零
        	   if(list.get(i).getRealStatus() != null && list.get(i).getRealStatus().length()!= 0){
	        	   if(!list.get(i).getRealStatus().equals("2")){
	        		   list.get(i).setRealIncome(null);
	        		   list.get(i).setDeferenceInIncome(null);
	        	   }
        	   }else{
        		   list.get(i).setRealIncome(null);
        		   list.get(i).setDeferenceInIncome(null);
        	   }
            }
			return list;
    }
	
    /**
     *导出差额报表查询
     */
    @Override
	public List<MiguIncomeDiff> queryDiffNopage(String projectId, String projectName, String dept, String person,
			String monthStart, String monthEnd){
		Map<String, Object> param = new HashMap<String, Object>();
		if (!"".equals(projectId) && projectId != null) {
			param.put("projectId", projectId);
		}
		if (!"".equals(projectName) && projectName != null) {
			param.put("projectName", projectName);
		}
		if (!"".equals(dept) && dept != null) {
			param.put("dept", dept);
		}
		if (!"".equals(person) && person != null) {
			param.put("person", person);
		}
		if (!"".equals(monthStart) && monthStart != null) {
			param.put("monthStart", monthStart);
		}
		if (!"".equals(monthEnd) && monthEnd != null) {
			param.put("monthEnd", monthEnd);
		}
		List<MiguIncomeDiff> dataList = actualIncomeMapper.queryDiffNopage(param);
        for(int i = 0; i<dataList.size();i++){
     	   //实际收入没有审核通过时
//     	   System.out.println(">>>>>>>>>>>>>>>"+list.get(i).getRealStatus());
     	   if(dataList.get(i).getRealStatus() != null && dataList.get(i).getRealStatus().length()!= 0){
	        	   if(!dataList.get(i).getRealStatus().equals("2")){
	        		   dataList.get(i).setRealIncome(null);
	        		   dataList.get(i).setDeferenceInIncome(null);
	        	   }
     	   }else{
     		   dataList.get(i).setRealIncome(null);
     		   dataList.get(i).setDeferenceInIncome(null);
     	   }
     	   
     	  if(dataList.get(i).getOffSet() != null && dataList.get(i).getOffSet().length()!= 0){
	     	   if(dataList.get(i).getOffSet().equals("0")){
	     		  dataList.get(i).setOffSet("否");
	     	   }else if(dataList.get(i).getOffSet().equals("1")){
	     		  dataList.get(i).setOffSet("是");
	     	   }
     	  }else{
    		   dataList.get(i).setOffSet(null);
    		   }
        }
		return dataList;
	}
    
    
	//保存文件
	private MiguIncomeActual saveFile(FileItem fileItem,MiguIncomeActual incomeManager) throws Exception{
		File tempFile = null;
		File file = null;
		String fileName = null;
		if(fileItem!=null){
			File backupPath = new File(PropValue.getPropValue(MiguConstants.FILES_LOCATION) +"actualIncome/"+ incomeManager.getBillingKey());
			if (!backupPath.exists()) {
				backupPath.mkdirs();
			}
			if(!StringUtil.isEmpty(incomeManager.getIncomeOptionsUrl())){
				File oldfile = new File(incomeManager.getIncomeOptionsUrl());
				if (oldfile.exists() && oldfile.isFile()) {
					oldfile.delete();
				}	
			}
			
			tempFile = new File(fileItem.getName());
			file = new File(backupPath, tempFile.getName());
			fileName = tempFile.getName();
			incomeManager.setIncomeOptionsUrl(PropValue.getPropValue(MiguConstants.FILES_LOCATION) +"actualIncome/"+ incomeManager.getBillingKey() + "/" + fileName);
			if (GetFileSize.checkFileSize(fileItem.getInputStream())) {
				log.info("操作失败，" + fileItem.getName() + "文件过大，上传限制为20M！");
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				throw new Exception("操作失败，" + fileItem.getName() + "文件过大，上传限制为20M！");
			} else {
				fileItem.write(file);
			}
		}
		return incomeManager;
	}

    //金额处理
    public String doNull(String str){
    	if("".equals(str)||str == null||str ==""){
    		return "";
    	}else{
    		String s1 = String.format("%.2f", Double.valueOf(str));
    		return s1;
    	}
    }
    
    /**
	 * 实际收款查询明细
	 */
	@Override
	public List<MiguIncomeBillIncome> queryAllIncomeActual(String q_month, String q_projectId, String q_projectName,
			String q_incomeState, String billingKeys,  String q_record_month , String q_incomeClassId ,String q_incomeSectionId,
	        String q_dept, String q_userName ,String q_bill_num , String q_bill_total,
	        String q_income, String income_date, MiguUsers user) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		if (!"".equals(q_projectId) && q_projectId != null) {
			param.put("q_projectId", q_projectId);
		}
		if (!"".equals(q_projectName) && q_projectName != null) {
			param.put("q_projectName", q_projectName);
		}
		if (!"".equals(q_month) && q_month != null) {
			param.put("q_month", q_month);
		}
		if (!"".equals(q_incomeState) && q_incomeState != null) {
			param.put("q_incomeState", q_incomeState);
		}
		if (!"".equals(billingKeys) && billingKeys != null) {
			String [] billingKeyArr = billingKeys.split(",");
			param.put("billingKeys", billingKeyArr);
		}
		if ((user.getDeptId() != null && user.getDeptId().equals("1")) || user.getUserId().equals("0")) {
			param.put("queryModel", "all");
		} else {
			param.put("q_userId", user.getUserId());
		}
		param.put("q_record_month", q_record_month);
		param.put("q_incomeClassId",q_incomeClassId);
		param.put("q_incomeSectionId", q_incomeSectionId);
		param.put("q_dept", q_dept);
		param.put("q_userName", q_userName);
		param.put("q_bill_num",q_bill_num);
		param.put("q_bill_total",q_bill_total);
		param.put("q_income",q_income);
		param.put("income_date", income_date);
		
		List<MiguIncomeBillIncome> list = actualIncomeMapper.queryAllIncomeActual(param);
		for(int i = 0;i<list.size();i++){
			if(list.get(i).getIsNeedBill() != null && list.get(i).getIsNeedBill().length()!= 0){
				if(list.get(i).getIsNeedBill().equals("0")){
					list.get(i).setBillingKey("-");
				}
			}
		}
		return list;
	}
	/***
	 * 查询开票明细
	 */
	public List<MiguIncomeBill> showBilldetail(String billingKey) throws Exception{
		return actualIncomeMapper.showBilldetail(billingKey);
	};

}
