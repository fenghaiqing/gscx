package cn.migu.income.service.impl;

import cn.migu.income.dao.*;
import cn.migu.income.pojo.*;
import cn.migu.income.service.MiguIncomeManagerService;
import cn.migu.income.util.*;
import cn.migu.income.webservices.GetTaxInvoiceInfo;
import cn.migu.income.webservices.GetTaxInvoiceInfoResponse;
import cn.migu.income.webservices.SynTaxInvoiceInfoSrv;
import cn.migu.income.webservices.SynTaxInvoiceInfoSrvStub;

import com.alibaba.fastjson.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class MiguIncomeManagerServiceImpl implements MiguIncomeManagerService {

	@Autowired
	private MiguIncomeManagerMapper incomeManagerMapper;
	@Autowired
	private MiguIncomeDetailMapper incomeDetailMapper;
	@Autowired
	private TMiguIncomeBillingMapper miguIncomeBillingMapper;
	@Autowired
	private TMiguPriceConfigInfoMapper configInfoMapper;
	@Autowired
	private MiguIncomeManagerHisMapper miguIncomeManagerHisMapper;
	@Autowired
	private MiguProjectManageMapper projectManageMapper;
	@Autowired
	private MiguIncomeBillMapper incomeBillMapper;
	
	final static Logger log = LoggerFactory.getLogger(MiguIncomeManagerServiceImpl.class);

	private final static String EST_OA_TITLE = "预估收入审核";

	private final static String REAL_OA_TITLE = "实际收入审核";

	@Override
	public int queryTotal(String q_month, String q_projectId, String q_projectName, String q_estimateState,
			String realIncomeStatus, String q_incomeClassId,String q_incomeSectionId, String q_dept,
	 	        String q_userName, String est_income, String est_exclusive_tax, String q_cx,  String q_bill,
	 	        String q_merge,String bzCycleReal,MiguUsers user) {
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
		if (!"".equals(q_estimateState) && q_estimateState != null) {
			param.put("q_estimateState", q_estimateState);
		}
		if (!"".equals(realIncomeStatus) && realIncomeStatus != null) {
			param.put("realIncomeStatus", realIncomeStatus);
		}
		if ((user.getDeptId() != null && user.getDeptId().equals("1")) || user.getUserId().equals("0")) {
			param.put("q_userId", null);
		} else {
			param.put("q_userId", user.getUserId());
		}
		param.put("q_incomeClassId",q_incomeClassId);
		param.put("q_incomeSectionId", q_incomeSectionId);
		param.put("q_dept",q_dept);
		param.put("q_userName",q_userName);
		param.put("est_income", est_income);
		param.put("est_exclusive_tax",est_exclusive_tax);
		param.put("q_cx", q_cx);
		param.put("q_bill", q_bill);
		param.put("q_merge", q_merge);
		param.put("bzCycleReal", bzCycleReal);
		return incomeManagerMapper.queryTotal(param);
	}

	@Override
	public int queryDrawBillTotal(String q_month_begin, String q_month_end, String q_projectId, String q_projectName,
			String q_billState,String q_month_bill,String q_incomeClassId,String q_incomeSectionId,String q_dept,
    		String q_userName,String q_bill_num,String q_bill_total,MiguUsers user) {
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
		if (!"".equals(q_billState) && q_billState != null) {
			param.put("q_billState", q_billState);
		}
		if ((user.getDeptId() != null && user.getDeptId().equals("1")) || user.getUserId().equals("0")) {
			param.put("q_userId",null);
		} else {
			param.put("q_userId", user.getUserId());
		}
		param.put("q_month_bill",q_month_bill);
		param.put("q_incomeClassId", q_incomeClassId);
		param.put("q_incomeSectionId", q_incomeSectionId);
		param.put("q_dept", q_dept);
		param.put("q_userName", q_userName);
		param.put("q_bill_num",q_bill_num);
		param.put("q_bill_total",q_bill_total);
		
		Integer count = miguIncomeBillingMapper.getIncomeBillingCount(param);
		if (count == null)
			return 0;
		else
			return count.intValue();
	}

	@Override
	public int queryDrawBillTotal_1(String q_projectId, String q_projectName, MiguUsers user) {
		Map<String, Object> param = new HashMap<String, Object>();
		if (!"".equals(q_projectId) && q_projectId != null) {
			param.put("q_projectId", q_projectId);
		}
		if (!"".equals(q_projectName) && q_projectName != null) {
			param.put("q_projectName", q_projectName);
		}
		if ((user.getDeptId() != null && user.getDeptId().equals("1")) || user.getUserId().equals("0")) {
			param.put("q_userId", null);
		} else {
			param.put("q_userId", user.getUserId());
		}
		return incomeManagerMapper.queryDrawBillTotal(param);
	}

	@Override
	public int queryNoBillTotal(String q_projectId, String q_projectName, MiguUsers user) {
		Map<String, Object> param = new HashMap<String, Object>();
		if (!"".equals(q_projectId) && q_projectId != null) {
			param.put("q_projectId", q_projectId);
		}
		if (!"".equals(q_projectName) && q_projectName != null) {
			param.put("q_projectName", q_projectName);
		}
		if ((user.getDeptId() != null && user.getDeptId().equals("1")) || user.getUserId().equals("0")) {
			param.put("q_userId", null);
		} else {
			param.put("q_userId", user.getUserId());
		}
		return incomeManagerMapper.queryNoBillTotal(param);
	}
	
	@Override
	public List<MiguIncomeManager> queryAllRealIncome(String q_month, String q_projectId, String q_projectName,
			String q_estimateState, String realIncomeStatus,
			 String q_incomeClassId,
 	        String q_incomeSectionId,
 	        String q_dept,
 	        String q_userName,
 	        String est_income,
 	        String est_exclusive_tax,
 	        String q_cx,
 	        String q_bill,
 	        String q_merge,String bzCycleReal,
			int page, int pageSize, MiguUsers user) {
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
		if (!"".equals(q_estimateState) && q_estimateState != null) {
			param.put("q_estimateState", q_estimateState);
		}
		if (!"".equals(realIncomeStatus) && realIncomeStatus != null) {
			param.put("realIncomeStatus", realIncomeStatus);
		}
		if ((user.getDeptId() != null && user.getDeptId().equals("1")) || user.getUserId().equals("0")) {
			param.put("q_userId", null);
		} else {
			param.put("q_userId", user.getUserId());
		}
		
		param.put("q_incomeClassId",q_incomeClassId);
		param.put("q_incomeSectionId", q_incomeSectionId);
		param.put("q_dept",q_dept);
		param.put("q_userName",q_userName);
		param.put("est_income", est_income);
		param.put("est_exclusive_tax",est_exclusive_tax);
		param.put("q_cx", q_cx);
		param.put("q_bill", q_bill);
		param.put("q_merge", q_merge);
		param.put("bzCycleReal", bzCycleReal);
		param.put("curPage", page);
		param.put("pageSize", pageSize);
		List<MiguIncomeManager> list = incomeManagerMapper.queryAllRealIncome(param);
		return list;
	}

	@Override
	public List<TMiguIncomeBilling> queryAllDrawBillIncome(String q_month_begin, String q_month_end, String q_projectId,
			String q_projectName, String q_billState,String  q_month_bill,String q_incomeClassId,String q_incomeSectionId,String q_dept,
            String q_userName,String q_bill_num,String q_bill_total, int page, int pageSize, MiguUsers user) {
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
		if (!"".equals(q_billState) && q_billState != null) {
			param.put("q_billState", q_billState);
		}
		if ((user.getDeptId() != null && user.getDeptId().equals("1")) || user.getUserId().equals("0")) {
			param.put("q_userId", null);
		} else {
			param.put("q_userId", user.getUserId());
		}
		
		param.put("q_month_bill",q_month_bill);
		param.put("q_incomeClassId", q_incomeClassId);
		param.put("q_incomeSectionId", q_incomeSectionId);
		param.put("q_dept", q_dept);
		param.put("q_userName", q_userName);
		param.put("q_bill_num",q_bill_num);
		param.put("q_bill_total",q_bill_total);
		
		param.put("page", page);
		param.put("rows", pageSize);
		List<TMiguIncomeBilling> list = miguIncomeBillingMapper.getIncomeBillingList(param);
		return list;
	}

	@Override
	public List<MiguIncomeManager> queryAllDrawBillIncome_1(String q_projectId, String q_projectName, int page,
			int pageSize, MiguUsers user) {
		Map<String, Object> param = new HashMap<String, Object>();
		if (!"".equals(q_projectId) && q_projectId != null) {
			param.put("q_projectId", q_projectId);
		}
		if (!"".equals(q_projectName) && q_projectName != null) {
			param.put("q_projectName", q_projectName);
		}
		if ((user.getDeptId() != null && user.getDeptId().equals("1")) || user.getUserId().equals("0")) {
			param.put("q_userId", null);
		} else {
			param.put("q_userId", user.getUserId());
		}
		param.put("page", page);
		param.put("rows", pageSize);
		List<MiguIncomeManager> list = incomeManagerMapper.queryAllDrawBillIncome(param);
		return list;
	}

	@Override
	public List<MiguIncomeManager> queryAllNoBillIncome(String q_projectId, String q_projectName, int page,
			int pageSize, MiguUsers user) {
		Map<String, Object> param = new HashMap<String, Object>();
		if (!"".equals(q_projectId) && q_projectId != null) {
			param.put("q_projectId", q_projectId);
		}
		if (!"".equals(q_projectName) && q_projectName != null) {
			param.put("q_projectName", q_projectName);
		}
		if ((user.getDeptId() != null && user.getDeptId().equals("1")) || user.getUserId().equals("0")) {
			param.put("q_userId", null);
		} else {
			param.put("q_userId", user.getUserId());
		}
		param.put("page", page);
		param.put("rows", pageSize);
		List<MiguIncomeManager> list = incomeManagerMapper.queryAllNoBillIncome(param);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryIncomeManagerList(MiguIncomeManager incomeManager, Integer page, Integer rows) {
		Map<String, Object> map = new HashMap<>();
		map = (Map<String, Object>) incomeManager;
		if (page == null) {
			page = 1;
		}
		if (rows == null) {
			rows = 20;
		}
		map.put("page", page);
		map.put("rows", rows);
		List<MiguIncomeManager> list = incomeManagerMapper.selectByExample(map);
		Integer total = incomeManagerMapper.selectCountByExample(map);
		Map<String, Object> result = new HashMap<>();
		result.put("rows", list);
		result.put("total", total);
		result.put("page", page);
		result.put("size", rows);
		return result;
	}

	@Override
	public List<MiguIncomeDetail> viewIncomeDetails(String incomeManagerId) {
		return incomeDetailMapper.viewIncomeDetails(incomeManagerId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public Map<String, Object> addEstimateIncome(HttpServletRequest request, HttpServletResponse resonse) {
		Map<String, String> map = new HashMap<String, String>();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		FileItem fileItem = null;
		long incomeManagerId = incomeManagerMapper.getIncomeManagerId();
		try {
			List<?> items = upload.parseRequest(request);
			fileItem = this.parseForm(items, map);
			JSONArray json = JSONArray.fromObject(map.get("list"));
			log.info(map.get("list"));

			@SuppressWarnings("unchecked")
			List<MiguIncomeDetail> list = JSONArray.toList(json, new MiguIncomeDetail(), new JsonConfig());

			MiguIncomeManager incomeManager = new MiguIncomeManager();
			incomeManager.setIncomeManagerId(incomeManagerId);
			incomeManager.setLasteDate(StringUtil.getTimeStamp("yyyy-MM-dd"));
			incomeManager.setBzCycle(StringUtil.getTimeStamp("yyyyMM"));
			incomeManager.setCycle(StringUtil.formatDateToyyyyMM(map.get("cycle")));
			incomeManager.setEstimateIncome(Double.valueOf(map.get("estimateIncome")));
			
			incomeManager.setEstimateIncomeTax(Double.valueOf(map.get("estimateIncomeTax")));
			incomeManager.setEstimateExclusiveTax(Double.valueOf(map.get("estimateExclusiveTax")));
			incomeManager.setEstimateAmount(Double.valueOf(map.get("estimateAmount")));
			
			incomeManager.setProjectId(map.get("projectId"));
			if (StringUtil.getStringBytesLength(map.get("estimateExplain")) > 2000) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return RetCode.serverError(RetCode.INCOME_MANAGER_ERROR_B);
			}
			incomeManager.setEstimateExplain(map.get("estimateExplain"));
			incomeManager.setEastimateStatus(map.get("status"));
			incomeManager.setAuditDept(map.get("auditDepartment") == null ? "" : map.get("auditDepartment"));
			incomeManager.setAuditPerson(map.get("auditPerson") == null ? "" : map.get("auditPerson"));
			incomeManager.setProjectName(map.get("projectName"));

			// 唯一约束验证
			MiguIncomeManager miguIncomeManager = incomeManagerMapper.selectByUnique(incomeManager.getCycle(),
					incomeManager.getProjectId());

			if (miguIncomeManager != null) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return RetCode.serverError(RetCode.INCOME_MANAGER_ERROR_A);
			}
			for (MiguIncomeDetail miguIncomeDetail : list) {
				// 查询定价配置明细
				TMiguPriceConfigInfo coonfigInfo = configInfoMapper
						.selectByPrimaryKey(miguIncomeDetail.getPriceConfigInfoId());
				// 判断 预估最低售价是否大于 售价
				if (coonfigInfo.getMinSellPrice().doubleValue() > miguIncomeDetail.getSellingPrice().doubleValue()) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return RetCode.serverError("售价不能低于定价配置的最低售价！");
				}
				miguIncomeDetail.setPriceConfigInfoId(miguIncomeDetail.getPriceConfigInfoId());
				miguIncomeDetail.setIncomeManagerId(String.valueOf(incomeManagerId));
				incomeDetailMapper.insertSelective(miguIncomeDetail);
			}
			this.saveFile(fileItem, incomeManager, MiguConstants.OPRATION_TYPE_ADD);
			incomeManagerMapper.insertSelective(incomeManager);
			if ("1".equals(map.get("status"))) {
				// 创建oa代办
				return this.createTask(incomeManagerId, map.get("auditPerson"), EST_OA_TITLE, "E",
						PropValue.getPropValue("EXAMINE_URL"), request);
			}

			return RetCode.success(incomeManagerId);
		} catch (Exception e) {
			log.error("新增预估收入异常：" + e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return RetCode.serverError("新增预估收入失败！");
		}

	}

	private FileItem parseForm(List<?> items, Map<String, String> map) throws Exception {
		FileItem fileItem = null;
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
		return fileItem;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public Map<String, Object> saveEditEstimateIncome(HttpServletRequest request, HttpServletResponse resonse) {
		Map<String, String> map = new HashMap<String, String>();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		Long incomeManagerId = null;

		FileItem fileItem = null;
		try {
			List<?> items = upload.parseRequest(request);
			fileItem = parseForm(items, map);
			// 获取预估收入明细
			JSONArray json = JSONArray.fromObject(map.get("list"));
			log.info(map.get("list"));
			@SuppressWarnings("unchecked")
			List<MiguIncomeDetail> list = JSONArray.toList(json, new MiguIncomeDetail(), new JsonConfig());

			incomeManagerId = Long.valueOf(map.get("incomeManagerId"));
			MiguIncomeManager incomeManager = incomeManagerMapper.selectByPrimaryKey(incomeManagerId);
			incomeManager.setLasteDate(StringUtil.getTimeStamp("yyyy-MM-dd"));
			incomeManager.setEstimateIncome(Double.valueOf(map.get("estimateIncome")));
			
			incomeManager.setEstimateIncomeTax(Double.valueOf(map.get("estimateIncomeTax")));
			incomeManager.setEstimateExclusiveTax(Double.valueOf(map.get("estimateExclusiveTax")));
			incomeManager.setEstimateAmount(Double.valueOf(map.get("estimateAmount")));
			
			if (StringUtil.getStringBytesLength(map.get("estimateExplain")) > 2000) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return RetCode.serverError(RetCode.INCOME_MANAGER_ERROR_B);
			}
			incomeManager.setEstimateExplain(map.get("estimateExplain"));
			incomeManager.setEastimateStatus(map.get("status"));
			incomeManager.setAuditDept(map.get("auditDepartment") == null ? "" : map.get("auditDepartment"));
			incomeManager.setAuditPerson(map.get("auditPerson") == null ? "" : map.get("auditPerson"));

			for (MiguIncomeDetail miguIncomeDetail : list) {
				// 查询定价配置明细
				TMiguPriceConfigInfo coonfigInfo = configInfoMapper
						.selectByPrimaryKey(miguIncomeDetail.getPriceConfigInfoId());

				// 判断 预估最低售价是否大于 售价
				if (coonfigInfo.getMinSellPrice().doubleValue() > miguIncomeDetail.getSellingPrice().doubleValue()) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return RetCode.serverError("售价不能低于定价配置的最低售价！");
				}
				miguIncomeDetail.setIncomeManagerId(String.valueOf(incomeManagerId));
				miguIncomeDetail.setPriceConfigInfoId(miguIncomeDetail.getPriceConfigInfoId());
				// 判断是否为新增
				if (miguIncomeDetail.getIncomeDetailId() == null) {
					incomeDetailMapper.insertSelective(miguIncomeDetail);
				} else {
					incomeDetailMapper.updateByPrimaryKey(miguIncomeDetail);
				}

			}
			// 保存文件
			this.saveFile(fileItem, incomeManager, MiguConstants.OPRATION_TYPE_ADD);
			incomeManagerMapper.updateByPrimaryKey(incomeManager);
			if ("1".equals(map.get("status"))) {
				// 创建oa代办
				return this.createTask(incomeManagerId, map.get("auditPerson"), EST_OA_TITLE, "E",
						PropValue.getPropValue("EXAMINE_URL"), request);
			}
			return RetCode.success();
		} catch (Exception e) {
			log.error("修改预估收入异常：" + e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return RetCode.serverError("修改预估收入失败！");
		}

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public Map<String, Object> delEstimateIncome(String incomeDetailId, String type) {
		try {
			MiguIncomeDetail incomeDetail = incomeDetailMapper.selectByPrimaryKey(incomeDetailId);

			MiguIncomeManager incomeManager = incomeManagerMapper
					.selectByPrimaryKey(Long.valueOf(incomeDetail.getIncomeManagerId()));
			if (MiguConstants.OPRATION_TYPE_EDIT.equals(type)) {
				incomeManager.setEstimateIncome(incomeManager.getEstimateIncome()
						- (incomeDetail.getSellingPrice() * incomeDetail.getPorductNumber()));
			} else {
				incomeManager.setRealIncome(incomeManager.getRealIncome()
						- (incomeDetail.getSellingPrice() * incomeDetail.getPorductNumber()));
				incomeManager.setDeferenceInIncome(incomeManager.getRealIncome()
						- (incomeManager.getEstimateIncome()==null?0:incomeManager.getEstimateIncome()));
			}

			incomeManager.setLasteDate(StringUtil.getTimeStamp("yyyy-MM-dd"));
			incomeManagerMapper.updateByPrimaryKey(incomeManager);
			incomeDetailMapper.deleteByPrimaryKey(incomeDetailId);
			return RetCode.success();
		} catch (Exception e) {
			log.error("删除预估收入明细异常：" + e.getMessage());
			return RetCode.serverError("删除失败！");
		}
	}

	// 保存文件
	private MiguIncomeManager saveFile(FileItem fileItem, MiguIncomeManager incomeManager, String type)
			throws Exception {
		File tempFile = null;
		File file = null;
		String fileName = null;
		if (fileItem != null) {
			File backupPath = null;
			if (MiguConstants.OPRATION_TYPE_REFRESH.equals(type)) {
				backupPath = new File(PropValue.getPropValue(MiguConstants.FILES_LOCATION) + "refreshRealIncome/"
						+ incomeManager.getIncomeManagerId());
				
				if (!StringUtil.isEmpty(incomeManager.getRealOptionsUrl())) {
					File oldfile = new File(incomeManager.getRealOptionsUrl());
					if (oldfile.exists() && oldfile.isFile()) {
						oldfile.delete();
					}
				}
				
			} else {
				backupPath = new File(PropValue.getPropValue(MiguConstants.FILES_LOCATION) + "estimateIncome/"
						+ incomeManager.getIncomeManagerId());
				
				if (!StringUtil.isEmpty(incomeManager.getEstimateOptionsUrl())) {
					File oldfile = new File(incomeManager.getEstimateOptionsUrl());
					if (oldfile.exists() && oldfile.isFile()) {
						oldfile.delete();
					}
				}
			}
			if (!backupPath.exists()) {
				backupPath.mkdirs();
			}
			

			tempFile = new File(fileItem.getName());
			file = new File(backupPath, tempFile.getName());
			fileName = tempFile.getName();
			if (MiguConstants.OPRATION_TYPE_REFRESH.equals(type)) {

				incomeManager.setRealOptionsUrl(PropValue.getPropValue(MiguConstants.FILES_LOCATION)
						+ "/refreshRealIncome/" + incomeManager.getIncomeManagerId() + "/" + fileName);

				if (StringUtil.getStringBytesLength(incomeManager.getRealOptionsUrl()) > 200) {
					throw new Exception(RetCode.INCOME_MANAGER_ERROR_C);
				}

			} else {

				incomeManager.setEstimateOptionsUrl(PropValue.getPropValue(MiguConstants.FILES_LOCATION)
						+ "estimateIncome/" + +incomeManager.getIncomeManagerId() + "/" + fileName);

				if (StringUtil.getStringBytesLength(incomeManager.getEstimateOptionsUrl()) > 200) {
					throw new Exception(RetCode.INCOME_MANAGER_ERROR_C);
				}
			}

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

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public Map<String, Object> refreshIncome(HttpServletRequest request, HttpServletResponse resonse) {
		Map<String, String> map = new HashMap<String, String>();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		Long incomeManagerId = null;
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
			// 获取预估收入明细
			JSONArray json = JSONArray.fromObject(map.get("list"));
			log.info(map.get("list"));
			@SuppressWarnings("unchecked")
			List<MiguIncomeDetail> list = JSONArray.toList(json, new MiguIncomeDetail(), new JsonConfig());

			incomeManagerId = Long.valueOf(map.get("incomeManagerId"));
			MiguIncomeManager incomeManager = incomeManagerMapper.selectByPrimaryKey(incomeManagerId);
			if(incomeManager.getBzCycleReal()==null||"".equals(incomeManager.getBzCycleReal())){
				incomeManager.setBzCycleReal(StringUtil.getTimeStamp("yyyyMM"));
			}
			incomeManager.setOffset(map.get("offset"));
			incomeManager.setLasteDate(StringUtil.getTimeStamp("yyyy-MM-dd"));
			incomeManager.setRealIncome(Double.valueOf(map.get("realIncome")));
			
			incomeManager.setRealIncomeTax(Double.valueOf(map.get("realIncomeTax")));
			incomeManager.setRealExclusiveTax(Double.valueOf(map.get("realExclusiveTax")));
			incomeManager.setRealAmount(Double.valueOf(map.get("realAmount")));
			
			if (StringUtil.getStringBytesLength(map.get("realExplain")) > 2000) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return RetCode.serverError(RetCode.INCOME_MANAGER_ERROR_B);
			}
			incomeManager.setIsNeedBill(map.get("billFlag2"));
			incomeManager.setRealExplain(map.get("realExplain"));
			incomeManager.setRealStatus(map.get("status"));
			incomeManager.setAuditDept(map.get("Audit_dept") == null ? "" : map.get("Audit_dept"));
			incomeManager.setAuditPerson(map.get("Audit_person") == null ? "" : map.get("Audit_person"));
			incomeManager.setDeferenceInIncome(Double.valueOf(map.get("realIncome"))
					- (incomeManager.getEstimateIncome() == null ? 0.00 : incomeManager.getEstimateIncome()));
			for (MiguIncomeDetail miguIncomeDetail : list) {
				// 查询定价配置明细
				TMiguPriceConfigInfo coonfigInfo = configInfoMapper
						.selectByPrimaryKey(miguIncomeDetail.getPriceConfigInfoId());

				// 判断 预估最低售价是否大于 售价
				if (coonfigInfo.getMinSellPrice().doubleValue() > miguIncomeDetail.getSellingPrice().doubleValue()) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return RetCode.serverError("实际收入不能低于定价配置的最低售价！");
				}
				miguIncomeDetail.setIncomeManagerId(String.valueOf(incomeManagerId));
				miguIncomeDetail.setPriceConfigInfoId(miguIncomeDetail.getPriceConfigInfoId());
				// 判断是否为新增
				if (miguIncomeDetail.getIncomeDetailId() == null) {
					incomeDetailMapper.insertSelective(miguIncomeDetail);
				} else {
					incomeDetailMapper.updateByPrimaryKey(miguIncomeDetail);
				}
			}
			// 保存文件
			this.saveFile(fileItem, incomeManager, MiguConstants.OPRATION_TYPE_REFRESH);
			incomeManager.setMergeStatus("0");
			incomeManagerMapper.updateByPrimaryKey(incomeManager);
			if ("1".equals(map.get("status"))) {
				return createTask(incomeManagerId, map.get("Audit_person"), REAL_OA_TITLE, "R",
						PropValue.getPropValue("EXAMINE_REAL_URL"), request);
			}
			return RetCode.success();
		} catch (Exception e) {
			log.error("刷新实际收入异常：" + e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return RetCode.serverError("刷新实际收入失败！");
		}

	}

	private Map<String, Object> createTask(Long incomeManagerId, String auditPerson, String title, String type,
			String url, HttpServletRequest request) {
		// 增加历史记录
		HttpSession session = request.getSession();
		MiguUsers user = (MiguUsers) (session.getAttribute(MiguConstants.USER_KEY));
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", user.getUserId());
		params.put("code", IDGenUtil.UUID());
		params.put("userName", user.getUserName());
		params.put("incomeManageId", String.valueOf(incomeManagerId));
		params.put("auditUser", auditPerson);
		params.put("optType", "1");
		params.put("title", title);
		params.put("status", "0");
		params.put("clazz", title);
		params.put("url", url + "?incomeManageId=" + String.valueOf(incomeManagerId) + "&auditUser=" + auditPerson
				+ "&code=" + params.get("code"));
		// 审核记录
		this.saveMiguIncomeManagerHis(String.valueOf(incomeManagerId), user.getDeptId(), user.getUserId(), "0", "",
				type);
		// 创建OA代办
		String resp = CreatOATaskUtil.createOATask(params);
		if (StringUtil.isNotEmpty(resp)) {
			@SuppressWarnings("unchecked")
			Map<String, String> result = (Map<String, String>) JSON.parse(resp);
			if ("1".equals(result.get("rsp"))) {
				return RetCode.success();
			}
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return RetCode.serverError("提交" + title + "失败！");
		} else {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return RetCode.serverError("创建OA代办失败！");
		}
	}

	@Override
	public Map<String, Object> viewRealIncomeDetails(String incomeManagerId) {
		Map<String, Object> map = new HashMap<>();
		List<MiguIncomeDetail> list = incomeDetailMapper.viewRealIncomeDetails(incomeManagerId);
		map.put("rows", list);
		return map;
	}

	@Override
	public MiguIncomeManager selectExamineObj(String incomeManageId, String auditUser) {

		if (StringUtil.isEmpty(incomeManageId)) {
			incomeManageId = "";
		}
		if (StringUtil.isEmpty(auditUser)) {
			auditUser = "";
		}
		return incomeManagerMapper.selectExamineObj(incomeManageId, auditUser);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public Map<String, Object> doExamine(Map<String, String> map, String title, String url, String type) {
		try {
			String status = map.get("eastimateStatus");
			MiguIncomeManager miguim = incomeManagerMapper.selectByPrimaryKey(Long.valueOf(map.get("incomManagerId")));
			String person = miguim.getAuditPerson();
			// 保存审核历史
			this.saveMiguIncomeManagerHis(String.valueOf(miguim.getIncomeManagerId()), miguim.getAuditDept(),
					miguim.getAuditPerson(), status, map.get("auditOption"), type);
			miguim.setAuditDept(map.get("auditDept"));
			miguim.setAuditPerson(map.get("auditUser"));
			if ("E".equals(type)) {
				miguim.setEastimateStatus(status);
			}
			if ("R".equals(type)) {
				miguim.setRealStatus(status);
			}
			if ("A".equals(type)) {
				miguim.setIncomeStatus(status);
			}
			incomeManagerMapper.updateByPrimaryKey(miguim);
			// 将代办转为已办
			Map<String, String> params = new HashMap<>();
			params.put("userId", person);
			params.put("optType", "2");
			params.put("status", "1");
			params.put("title", title);
			params.put("clazz", title);
			params.put("auditUser", person);
			params.put("userName", map.get("userName"));
			params.put("code", map.get("code"));
			params.put("url", url + "?incomeManageId=" + map.get("incomeManageId") + "&auditUser="
					+ miguim.getAuditPerson() + "&code=" + map.get("code"));
			// 创建代办
			String result = CreatOATaskUtil.createOATask(params);
			Map<String, String> resp = (Map<String, String>) JSON.parse(result);
			if (!"1".equals(resp.get("rsp"))) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				log.error("审核预估收入异常 ：修改OA代办异常 返回信息" + result);
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
				params.put("url", url + "?incomeManageId=" + miguim.getIncomeManagerId() + "&auditUser="
						+ miguim.getAuditPerson() + "&code=" + params.get("code"));
				result = CreatOATaskUtil.createOATask(params);
				resp = (Map<String, String>) JSON.parse(result);
				if (!"1".equals(resp.get("rsp"))) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					log.error("审核预估收入异常 ：创建OA代办异常 返回信息" + result);
					return RetCode.serverError("审核失败！");
				}
			}
			return RetCode.success();
		} catch (NumberFormatException e) {
			log.error("创建OA代办异常：" + e.getMessage());
			return RetCode.serverError("审核失败！");
		}

	}

	@Override
	public List<MiguIncomeManagerHis> queryList(String incomeManagerId, String type) {
		List<MiguIncomeManagerHis> list = miguIncomeManagerHisMapper.queryList(incomeManagerId, type);
		return list;
	}

	@Override
	@Transactional
	public void updateBillKey(String projectId, String incomeManagerId, String key) throws Exception {
		int i = incomeManagerMapper.updateBillKey(projectId, incomeManagerId, key);

		// 更新不成功则抛出异常
		if (i != 1) {
			throw new SQLException();
		}
	}

	@Override
	public MiguIncomeManager queryByProjectIdAndIncomeManagerId(String projectId, String incomeManagerId) {
		return incomeManagerMapper.selectByPrimaryKey(Long.valueOf(incomeManagerId));
	}

	@Override
	public int insertBilling(TMiguIncomeBilling tMiguIncomeBilling) {
		return miguIncomeBillingMapper.insertBilling(tMiguIncomeBilling);
	}

	private void saveMiguIncomeManagerHis(String incomeManagerId, String department, String auditPerson,
			String dealResult, String dealOptions, String type) {
		MiguIncomeManagerHis his = new MiguIncomeManagerHis();
		his.setIncomeManagerHisId(IDGenUtil.UUID());
		his.setMiguIncomeManagerId(incomeManagerId);
		his.setAuditDept(department);
		his.setAuditPerson(auditPerson);
		his.setDealResult(dealResult);
		his.setDealOptions(dealOptions);
		his.setCreateDate(StringUtil.getTimeStamp("yyyy-MM-dd HH:mm:ss"));
		his.setType(type);
		miguIncomeManagerHisMapper.insertSelective(his);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public Map<String, Object> addRealIncome(HttpServletRequest request, HttpServletResponse resonse) {
		Map<String, String> map = new HashMap<String, String>();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		Long incomeManagerId = incomeManagerMapper.getIncomeManagerId();
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
			// 获取实际收入明细
			JSONArray json = JSONArray.fromObject(map.get("list"));
			log.info(map.get("list"));
			@SuppressWarnings("unchecked")
			List<MiguIncomeDetail> list = JSONArray.toList(json, new MiguIncomeDetail(), new JsonConfig());

			MiguIncomeManager incomeManager = new MiguIncomeManager();
			incomeManager.setIncomeManagerId(incomeManagerId);
			incomeManager.setLasteDate(StringUtil.getTimeStamp("yyyy-MM-dd"));
			incomeManager.setBzCycleReal(StringUtil.getTimeStamp("yyyyMM"));
			incomeManager.setRealIncome(Double.valueOf(map.get("addRealIncome")));
			
			incomeManager.setRealIncomeTax(Double.valueOf(map.get("realIncomeTax")));
			incomeManager.setRealExclusiveTax(Double.valueOf(map.get("realExclusiveTax")));
			incomeManager.setRealAmount(Double.valueOf(map.get("realAmount")));
			
			if (StringUtil.getStringBytesLength(map.get("realIncomeExplain")) > 2000) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return RetCode.serverError(RetCode.INCOME_MANAGER_ERROR_B);
			}
			incomeManager.setProjectId(map.get("projectId"));
			incomeManager.setProjectName(map.get("projectName"));
			incomeManager.setCycle(StringUtil.formatDateToyyyyMM(map.get("cycle")));
			incomeManager.setRealExplain(map.get("realIncomeExplain"));
			incomeManager.setRealStatus(map.get("status"));
			incomeManager.setAuditDept(map.get("auditDept") == null ? "" : map.get("auditDept"));
			incomeManager.setAuditPerson(map.get("auditPerson") == null ? "" : map.get("auditPerson"));
			incomeManager.setOffset(map.get("offset"));
			incomeManager.setDeferenceInIncome(Double.valueOf(map.get("addRealIncome")));
			incomeManager.setIsNeedBill(map.get("billFlag"));
			for (MiguIncomeDetail miguIncomeDetail : list) {
				// 查询定价配置明细
				TMiguPriceConfigInfo coonfigInfo = configInfoMapper
						.selectByPrimaryKey(miguIncomeDetail.getPriceConfigInfoId());

				// 判断 预估最低售价是否大于 售价
				if (coonfigInfo.getMinSellPrice().doubleValue() > miguIncomeDetail.getSellingPrice().doubleValue()) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return RetCode.serverError("实际收入不能低于定价配置的最低售价！");
				}
				miguIncomeDetail.setIncomeManagerId(String.valueOf(incomeManagerId));
				miguIncomeDetail.setPriceConfigInfoId(miguIncomeDetail.getPriceConfigInfoId());
				miguIncomeDetail.setType("R");
				// 判断是否为新增
				if (miguIncomeDetail.getIncomeDetailId() == null) {
					incomeDetailMapper.insertSelective(miguIncomeDetail);
				} else {
					incomeDetailMapper.updateByPrimaryKey(miguIncomeDetail);
				}
			}
			// 保存文件
			this.saveFile(fileItem, incomeManager, MiguConstants.OPRATION_TYPE_REFRESH);
			incomeManager.setMergeStatus("0");
			incomeManagerMapper.insertSelective(incomeManager);
			if ("1".equals(map.get("status"))) {
				return createTask(incomeManagerId, map.get("auditPerson"), REAL_OA_TITLE, "R",
						PropValue.getPropValue("EXAMINE_REAL_URL"), request);
			}
			return RetCode.success();
		} catch (Exception e) {
			log.error("新增实际收入异常：" + e.getMessage());
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return RetCode.serverError("新增实际收入失败！");
		}

	}

	/**
	 * 导入产品信息
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public Map<String, Object> importProduct(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InputStream in = null;
		Map<String, String> map = new HashMap<String, String>();
		Workbook work = null;
		Sheet sheet = null;
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<?> items = upload.parseRequest(request);
		Iterator<?> itr = items.iterator();
		while (itr.hasNext()) {
			FileItem item = (FileItem) itr.next();
			if (!item.isFormField()) {
				in = item.getInputStream();
				try {
					work = WorkbookFactory.create(in);
				} catch (Exception e) {
					log.error("操作时间：【"+new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())+"】,操作内容：【导入产品信息】，异常信息：【"+e.getMessage()+"】");
					return RetCode.serverError("文件格式有误，请导入模板文件！");
				} 
			} else {
				map.put(item.getFieldName(), item.getString("utf-8"));
			}
		}

		if (null == work) {
			return RetCode.serverError("创建Excel工作薄为空！");
		}
		List<MiguIncomeDetail> list = null;
		String projectId = map.get("projectId");
		String month = StringUtil.formatDateToyyyyMM(map.get("month"));
		String oldData = map.get("oldData");

		if (StringUtil.isNotEmpty(oldData)) {
			list = JSON.parseArray(oldData, MiguIncomeDetail.class);
		}

		// 获取Excel中所有的sheet
		sheet = work.getSheetAt(0);
		if (sheet == null) {
			return RetCode.serverError("Excel工作薄为空！");
		}
		try {
			// 解析excel返回定价配置信息集合
			Map<String, Object> result = getIncomeDetailList(sheet, projectId, month, list);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return RetCode.serverError(e.getMessage());
		}

	}
	
	private Map<String, Object> getIncomeDetailList(Sheet sheet, String projectId, String month,
			List<MiguIncomeDetail> list) {
		Row row = null;
		Cell cell = null;
		Map<String, Object> map = new HashMap<>();
		MiguIncomeDetail incomeDetail = null;
		StringBuffer fail = new StringBuffer();
		// 遍历当前sheet中的所有行
		boolean flag = false;
		int line = 0;
		if(list==null){
			list= new ArrayList<>();
		}
		if (sheet.getLastRowNum() < 1) {
			map.put("error", "excel校验失败，数据无法导入，请修正后重新选择附件提交！");
			return map;
		}
		// 遍历所有的行
		for (int j = 1; j <= sheet.getLastRowNum(); j++) {
			line = j + 1;
			row = sheet.getRow(j);
			if (row == null) {
				flag = true;
				break;
			}

			incomeDetail = new MiguIncomeDetail();

			if (row.getCell(1) != null && StringUtil.isNotEmpty(getCellVale(row.getCell(1)))) {
				incomeDetail.setProductName((getCellVale(row.getCell(1)).trim()));
			} else {
				flag = true;
				break;
			}
			if (row.getCell(2) != null && StringUtil.isNotEmpty(getCellVale(row.getCell(2)))) {
				if(StringUtil.isNumeric(getCellVale(row.getCell(2)).trim())){
					row.getCell(2).setCellType(1);
					incomeDetail.setSellingPrice(Double.parseDouble(getCellVale(row.getCell(2))));
				}else{
					map.put("error", "第" + line + "行校验失败，数据无法导入，售价必须为数字！");
					return map;
				}
				
			} else {
				flag = true;
				break;
			}

			if (row.getCell(3) != null && StringUtil.isNotEmpty(getCellVale(row.getCell(3)))) {
				if(StringUtil.isNumeric(getCellVale(row.getCell(3)).trim())){
				incomeDetail.setPorductNumber(Double.parseDouble(getCellVale(row.getCell(3)).trim()));
				}else{
					map.put("error", "第" + line + "行校验失败，数据无法导入，数量必须为数字！");
					return map;
				}
			} else {
				flag = true;
				break;
			}

			if (row.getCell(0) != null && StringUtil.isNotEmpty(getCellVale(row.getCell(0)))) {
				incomeDetail.setProductId(getCellVale(row.getCell(0)).trim());
			} else {
				flag = true;
				break;
			}

			List<TMiguPriceConfigInfo> result = configInfoMapper.selectByCase(projectId, month,
					incomeDetail.getProductId(), incomeDetail.getProductName());

			if (result != null && result.size() > 0) {
				incomeDetail.setPriceConfigInfoId(String.valueOf(result.get(0).getPriceConfigInfoId()));
				list.add(incomeDetail);
			} else {
				fail.append(incomeDetail.getProductId() + ",");
			}
		}
		if (flag) {
			map.put("error", "第" + line + "行校验失败，数据无法导入，请修正后重新选择附件提交！");
			return map;
		}
		map.put("failData", fail.toString());
		map.put("list", list);
		return map;
	}

	private String getCellVale(Cell cell) {
		return ImportExcelUtil.getCellValue(cell).toString();
	}

	@Override
	public Map<String, Object> downLoadExcel(HttpServletRequest request, HttpServletResponse response,Map<String, Object>
			param) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

        List<TMiguPriceConfigInfo> dataList = new ArrayList<TMiguPriceConfigInfo>();
		try {
			String month = StringUtil.formatDateToyyyyMM(param.get("today").toString());
			param.put("today",month);
			dataList = configInfoMapper.selectProduct(param);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        
        if (dataList.size() == 0)
        {
            log.info("该项目下无产品定价信息！");
            map.put("reCode", -1);
            map.put("reStr", "该项目下无产品定价信息！");
        }else{
        	//获取模板文件
        	WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
            ServletContext servletContext = webApplicationContext.getServletContext();          
        	String tempFile= servletContext.getRealPath("/reportTemplate")+"/产品信息模板.xls";//模板文件的地址
        	File temp_file = new File(tempFile);
        	//将模板文件生成到指定文件夹
        	String tempFolder = PropValue.getPropValue("report_income_diff");
            File folderTemp = new File(tempFolder);
            if(!folderTemp.isDirectory()&&!folderTemp.exists()){
                folderTemp.mkdirs();
            }
            File reportFile = new File(tempFolder + temp_file.getName());//创建传送文件
            if (reportFile.exists())
            {
            	reportFile.delete();
            }
            try
            {
            	reportFile.createNewFile();
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return null;
            }
            try {
				FileUtils.copyFile(temp_file, reportFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
            FileInputStream is = new FileInputStream(reportFile);
          //HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is); 
          Workbook hssfWorkbook = null; 
          try { 
              hssfWorkbook = new HSSFWorkbook(is); 
          } catch (Exception ex) {
              // 解决read error异常
              is = new FileInputStream(reportFile);
              hssfWorkbook = new XSSFWorkbook(is); 
          } 
            //将数据写入到模板文件
          Sheet hssfsheet = hssfWorkbook.getSheetAt(0);
            int i = hssfsheet.getPhysicalNumberOfRows();
            CellStyle style = createStyle(hssfWorkbook);
            for (int j = 0; j < dataList.size(); j++) {
            	TMiguPriceConfigInfo tMiguDataStatistics = (TMiguPriceConfigInfo) dataList.get(j);
            	//创建一行  
            	Row rowData = hssfsheet.createRow(i++);  
                createCell(rowData, 0,tMiguDataStatistics.getProductId(),style  );  
                createCell(rowData, 1,doNull(tMiguDataStatistics.getProductName()),style);  
                createCell(rowData, 2, String.valueOf(tMiguDataStatistics.getMinSellPrice()),style);  
                createCell(rowData, 3, "1",style);  
			}
            
            OutputStream os = null;
			try {
				os = new FileOutputStream(reportFile);
				hssfWorkbook.write(os);
				os.close();
				map.put("reCode", 100);
				map.put("fileName", reportFile.getName());
				log.info("产品信息模板生成成功！");
			} catch (Exception e) {
				map.put("reCode", -1);
				map.put("reStr", "产品信息模板生成异常！");
				log.error("产品信息模板生成异常:", e);
				e.printStackTrace();
			}
        	
        }
    	return map;
	}
	
	 //poi表格行创建方法
    public static void createCell(Row row, int column,  
            String value,CellStyle style) {  
    	@SuppressWarnings("deprecation")
		Cell cell = row.createCell((short)column);
    	cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    private static CellStyle createStyle(Workbook wb){
    	 CellStyle style1 = wb.createCellStyle();
         style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
         style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
         style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
         style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
         style1.setTopBorderColor(HSSFColor.BLACK.index);
         style1.setLeftBorderColor(HSSFColor.BLACK.index);
         style1.setRightBorderColor(HSSFColor.BLACK.index);
         style1.setBottomBorderColor(HSSFColor.BLACK.index);
         return style1;
    }
    
  //判空处理
    public String doNull(Object object){
    	if(object == null){
    		return "";
    	}else{
    		return object.toString();
    	}
    }

	@Override
	public List<Map<String, Object>> selectEstimateIncome(String q_month, String q_projectId, String q_projectName,
			String q_estimateState, String realIncomeStatus,String q_incomeClassId,String q_incomeSectionId,
	        String q_dept ,String q_userName, String est_income,String est_exclusive_tax,String bzCycle, int page, int pageSize, MiguUsers user) {
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
		if (!"".equals(q_estimateState) && q_estimateState != null) {
			param.put("q_estimateState", q_estimateState);
		}
		if (!"".equals(realIncomeStatus) && realIncomeStatus != null) {
			param.put("realIncomeStatus", realIncomeStatus);
		}
		if ((user.getDeptId() != null && user.getDeptId().equals("1")) || user.getUserId().equals("0")) {
			param.put("q_userId", null);
		} else {
			param.put("q_userId", user.getUserId());
		}
		param.put("q_incomeClassId", q_incomeClassId);
		param.put("q_incomeSectionId", q_incomeSectionId);
		param.put("q_dept", q_dept);
		param.put("q_userName", q_userName);
		param.put("est_income", est_income);
		param.put("est_exclusive_tax", est_exclusive_tax);
		param.put("bzCycle", bzCycle);
		param.put("curPage", page);
		param.put("pageSize", pageSize);
		List<Map<String, Object>> list = incomeManagerMapper.selectEstimateIncome(param);
		return list;
	}

	@Override
	public int queryRecodes(String q_month, String q_projectId, String q_projectName, String q_estimateState,
			String realIncomeStatus,String q_incomeClassId,String q_incomeSectionId,
	        String q_dept ,String q_userName, String est_income,String est_exclusive_tax,String bzCycle, MiguUsers user) {
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
		if (!"".equals(q_estimateState) && q_estimateState != null) {
			System.out.println(q_estimateState.length());
			param.put("q_estimateState", q_estimateState);
		}
		if (!"".equals(realIncomeStatus) && realIncomeStatus != null) {
			param.put("realIncomeStatus", realIncomeStatus);
		}
		if ((user.getDeptId() != null && user.getDeptId().equals("1")) || user.getUserId().equals("0")) {
			param.put("q_userId", null);
		} else {
			param.put("q_userId", user.getUserId());
		}
		param.put("q_incomeClassId", q_incomeClassId);
		param.put("q_incomeSectionId", q_incomeSectionId);
		param.put("q_dept", q_dept);
		param.put("q_userName", q_userName);
		param.put("est_income", est_income);
		param.put("est_exclusive_tax", est_exclusive_tax);
		param.put("bzCycle", bzCycle);
		return incomeManagerMapper.queryRecodes(param);
	}
	
	@Override
	public Map<String, Object> dellIncomeManager(String incomeManagerId) {
		Map<String, Object> result = new HashMap<String, Object>();
		incomeManagerMapper.deleteIncome_manager_his(incomeManagerId);
		incomeManagerMapper.deleteIncome_detail(incomeManagerId);
		incomeManagerMapper.deleteIncome_manager(incomeManagerId);
		result.put("code", "0");
		result.put("reason", "");
		return result;
	}
	
	@Override
	public List<MiguIncomeManager> queryAllIncomeManager(String q_month, String q_projectId, String q_projectName,
			String q_estimateState,String q_incomeClassId,String q_incomeSectionId,
	        String q_dept ,String q_userName, String est_income,String est_exclusive_tax,String bzCycle, String incomeManagerIds,MiguUsers user) {
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
		if (!"".equals(q_estimateState) && q_estimateState != null) {
			param.put("q_estimateState", q_estimateState);
		}
		if (!"".equals(incomeManagerIds) && incomeManagerIds != null) {
			String [] incomeManagerIdArr = incomeManagerIds.split(",");
			param.put("incomeManagerIds", incomeManagerIdArr);
		}
		if ((user.getDeptId() != null && user.getDeptId().equals("1")) || user.getUserId().equals("0")) {
			param.put("q_userId", null);
		} else {
			param.put("q_userId", user.getUserId());
		}
		param.put("q_incomeClassId", q_incomeClassId);
		param.put("q_incomeSectionId", q_incomeSectionId);
		param.put("q_dept", q_dept);
		param.put("q_userName", q_userName);
		param.put("est_income", est_income);
		param.put("est_exclusive_tax", est_exclusive_tax);
		param.put("bzCycle", bzCycle);
		List<MiguIncomeManager> list = incomeManagerMapper.queryAllIncomeManager(param);
		return list;
	}
	
	@Override
	public List<MiguIncomeManager> queryAllRealIncomeManager(String q_month, String q_projectId, String q_projectName,
			String realIncomeStatus, String incomeManagerIds, String q_incomeClassId, String q_incomeSectionId,
		        String q_dept,String q_userName, String est_income, String est_exclusive_tax,
		        String q_cx,  String q_bill, String q_merge,String bzCycleReal,
			MiguUsers user) {
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
		if (!"".equals(realIncomeStatus) && realIncomeStatus != null) {
			param.put("realIncomeStatus", realIncomeStatus);
		}
		if (!"".equals(incomeManagerIds) && incomeManagerIds != null) {
			String [] incomeManagerIdArr = incomeManagerIds.split(",");
			param.put("incomeManagerIds", incomeManagerIdArr);
		}
		if ((user.getDeptId() != null && user.getDeptId().equals("1")) || user.getUserId().equals("0")) {
			param.put("q_userId", null);
		} else {
			param.put("q_userId", user.getUserId());
		}
		
		param.put("q_incomeClassId",q_incomeClassId);
		param.put("q_incomeSectionId", q_incomeSectionId);
		param.put("q_dept",q_dept);
		param.put("q_userName",q_userName);
		param.put("est_income", est_income);
		param.put("est_exclusive_tax",est_exclusive_tax);
		param.put("q_cx", q_cx);
		param.put("q_bill", q_bill);
		param.put("q_merge", q_merge);
		param.put("bzCycleReal", bzCycleReal);
		List<MiguIncomeManager> list = incomeManagerMapper.queryAllRealIncomeManager(param);
		return list;
	}
	
	@Override
	public List<TMiguIncomeBilling> queryAllBill(String q_month_begin, String q_month_end, String q_projectId,
			String q_projectName, String q_billState,String billingKeys, String q_month_bill,String q_incomeClassId,String q_incomeSectionId,
	        String q_dept,String q_userName,String q_bill_num,String q_bill_total,MiguUsers user) {
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
		if (!"".equals(q_billState) && q_billState != null) {
			param.put("q_billState", q_billState);
		}
		if (!"".equals(billingKeys) && billingKeys != null) {
			String [] billingKeyArr = billingKeys.split(",");
			param.put("billingKeys", billingKeyArr);
		}
		if ((user.getDeptId() != null && user.getDeptId().equals("1")) || user.getUserId().equals("0")) {
			param.put("q_userId", null);
		} else {
			param.put("q_userId", user.getUserId());
		}
		param.put("q_month_bill", q_month_bill);
		param.put("q_incomeClassId", q_incomeClassId);
		param.put("q_incomeSectionId", q_incomeSectionId);
		param.put("q_dept", q_dept);
		param.put("q_userName", q_userName);
		param.put("q_bill_num", q_bill_num);
		param.put("q_bill_total", q_bill_total);
		
		
		List<TMiguIncomeBilling> list = miguIncomeBillingMapper.queryAllBill(param);
		return list;
	}
	
	@Override
    public String deleteBilling(String seq,String projectId) throws Exception
    {
		SynTaxInvoiceInfoSrv synTaxInvoiceInfoSrv = new SynTaxInvoiceInfoSrvStub(PropValue.getPropValue(MiguConstants.BILL_WEBSERVICE_LOCATION));
		GetTaxInvoiceInfo getTaxInvoiceInfo = new GetTaxInvoiceInfo();
        try {
        	
        	String json = "{\"systemSource\":\"income\",\"priKey\":" + seq + "}";

			getTaxInvoiceInfo.setArgs0(json);
			GetTaxInvoiceInfoResponse getTaxInvoiceInfoResponse = synTaxInvoiceInfoSrv
					.getTaxInvoiceInfo(getTaxInvoiceInfo);
			String result = getTaxInvoiceInfoResponse.get_return();
			log.info("删除操作---priKey:"+seq+",项目号:"+projectId+",发票申请查询返回信息："+result);
			
			// 解析返回值，获取开票时间，开票状态
			Map<String, Object> resultMap = (Map<String, Object>) JSON.parse(result);
			
			Object status = resultMap.get("returnFlag");
			
			if (status != null && "Y".equals(status.toString())) {
				return "-100";
			}else{
				int i = miguIncomeBillingMapper.deleteBilling(seq);
				if(i!=1){
					return "-1";
				}
			}
        } catch (Exception e) {
            log.info("开票信息异常:"+e);
        }
        return "100";
    }

	@Override
	public int queryTotal(String q_month, String q_projectId, String q_projectName,String bzCycleReal,String q_incomeClassId,String q_incomeSectionId,String q_dept,String q_userName,
			String rl_income,String rl_exclusive_tax,String q_cx) {
		if(StringUtil.isEmpty(q_month)){
			q_month=null;
		}else{
         	q_month = q_month.split("-")[0]+(q_month.split("-")[1].length()==1?0+q_month.split("-")[1]:q_month.split("-")[1]);
         }
		if(StringUtil.isEmpty(q_projectId)){
			q_projectId=null;
		}
		if(StringUtil.isEmpty(q_projectName)){
			q_projectName=null;
		}
		if(StringUtil.isEmpty(bzCycleReal)){
			bzCycleReal=null;
		}else{
			bzCycleReal = bzCycleReal.split("-")[0]+(bzCycleReal.split("-")[1].length()==1?0+bzCycleReal.split("-")[1]:bzCycleReal.split("-")[1]);
         }
		if(StringUtil.isEmpty(q_incomeClassId)){
			q_incomeClassId=null;
		}
		if(StringUtil.isEmpty(q_incomeSectionId)){
			q_incomeSectionId=null;
		}
		if(StringUtil.isEmpty(q_dept)){
			q_dept=null;
		}
		if(StringUtil.isEmpty(q_userName)){
			q_userName=null;
		}
		if(StringUtil.isEmpty(rl_income)){
			rl_income=null;
		}
		if(StringUtil.isEmpty(rl_exclusive_tax)){
			rl_exclusive_tax=null;
		}
		if(StringUtil.isEmpty(q_cx)){
			q_cx=null;
		}
		Map<String , Object> map=new HashMap<>();
	     map.put("q_month", q_month);
         map.put("q_projectId", q_projectId);
         map.put("q_projectName", q_projectName);
         map.put("bzCycleReal", bzCycleReal);
         map.put("q_incomeClassId", q_incomeClassId);
         map.put("q_incomeSectionId", q_incomeSectionId);
         map.put("q_dept", q_dept);
         map.put("q_userName", q_userName);
         map.put("rl_income", rl_income);
         map.put("rl_exclusive_tax", rl_exclusive_tax);
         map.put("offset", q_cx);
		return incomeManagerMapper.getMergeIncomeTotal(map);
	}

	@Override
	public List<MiguIncomeManager> queryMergeIncome(String q_month, String q_projectId, String q_projectName,
			String bzCycleReal,String q_incomeClassId,String q_incomeSectionId,String q_dept,String q_userName,
			String rl_income,String rl_exclusive_tax,String q_cx,String page, String rows) {
		 
		Map<String, Object> map = new HashMap<>();
		if (StringUtils.isEmpty(page))
         {
             page = "1";
         }
         if (StringUtils.isEmpty(rows))
         {
             rows = "10";
         }            
         if (StringUtils.isEmpty(q_month))
         {
         	q_month = null;
         }else{
         	q_month = q_month.split("-")[0]+(q_month.split("-")[1].length()==1?0+q_month.split("-")[1]:q_month.split("-")[1]);
         }
         if (StringUtils.isEmpty(q_projectId))
         {
         	q_projectId = null;
         }
         if(StringUtils.isEmpty(q_projectName))
         {
         	q_projectName = null;
         }
         if(StringUtil.isEmpty(bzCycleReal)){
 			bzCycleReal=null;
 		}else{
			bzCycleReal = bzCycleReal.split("-")[0]+(bzCycleReal.split("-")[1].length()==1?0+bzCycleReal.split("-")[1]:bzCycleReal.split("-")[1]);
        }
 		if(StringUtil.isEmpty(q_incomeClassId)){
 			q_incomeClassId=null;
 		}
 		if(StringUtil.isEmpty(q_incomeSectionId)){
 			q_incomeSectionId=null;
 		}
 		if(StringUtil.isEmpty(q_dept)){
 			q_dept=null;
 		}
 		if(StringUtil.isEmpty(q_userName)){
 			q_userName=null;
 		}else if(q_userName.contains("_")||q_userName.contains("%")){
 			q_userName=StringUtil.getSpecialParam(q_userName);
 		}
 		if(StringUtil.isEmpty(rl_income)){
 			rl_income=null;
 		}
 		if(StringUtil.isEmpty(rl_exclusive_tax)){
 			rl_exclusive_tax=null;
 		}
 		if(StringUtil.isEmpty(q_cx)){
 			q_cx=null;
 		}
         if(StringUtil.isNotEmpty(q_month)&&(q_month.contains("_")||q_month.contains("%")))
         {
         	q_month=StringUtil.getSpecialParam(q_month);
         }
         if(StringUtil.isNotEmpty(q_projectId)&&(q_projectId.contains("_")||q_projectId.contains("%")))
         {
         	q_projectId=StringUtil.getSpecialParam(q_projectId);
         }
         if(StringUtil.isNotEmpty(q_projectName)&&(q_projectName.contains("_")||q_projectName.contains("%")))
         {
         	q_projectName=StringUtil.getSpecialParam(q_projectName);
         }
         
         map.put("page", page);
         map.put("q_month", q_month);
         map.put("q_projectId", q_projectId);
         map.put("q_projectName", q_projectName);
         map.put("bzCycleReal", bzCycleReal);
         map.put("q_incomeClassId", q_incomeClassId);
         map.put("q_incomeSectionId", q_incomeSectionId);
         map.put("q_dept", q_dept);
         map.put("q_userName", q_userName);
         map.put("rl_income", rl_income);
         map.put("rl_exclusive_tax", rl_exclusive_tax);
         map.put("offset", q_cx);
         map.put("rows", rows);
         
		return incomeManagerMapper.queryMergeIncome(map);
	}

	@Override
	public Map<String, Object> queryMergeRealIncome(String dept_id, String startDate,String endDate,String page, String rows) {
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		if (StringUtils.isEmpty(page))
         {
             page = "1";
         }
         if (StringUtils.isEmpty(rows))
         {
             rows = "10";
         }   
         if (StringUtils.isEmpty(dept_id))
         {
        	 map.put("dept_id", null);
         }else{
        	 String[] dept =dept_id.split(",");
        	 if(dept!=null && dept.length>0){
        		 map.put("dept_id", dept);
        	 }else{
        		 map.put("dept_id", null);
        	 }
        	
         }   
         if (StringUtils.isEmpty(startDate))
         {
        	 map.put("startDate", null);
         }else{
        	 map.put("startDate", StringUtil.formatDateToyyyyMM(startDate));
         }
         if (StringUtils.isEmpty(endDate))
         {
        	 map.put("endDate", null);
         }else{
        	 map.put("endDate", StringUtil.formatDateToyyyyMM(endDate));
         }
         map.put("page", page);
         map.put("rows", rows);
        
         
		List<Map<String, Object>> list = incomeManagerMapper.queryMergeRealIncome(map);
		int count = incomeManagerMapper.getMergeRealIncomeCount(map);
		
		result.put("total", count);
		result.put("rows", list);
		
		return result;
	}

	@Override
	public Map<String, Object> searchProject(String projectid, String projectName,String userId, String page, String rows) {
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		if (StringUtils.isEmpty(page))
         {
             page = "1";
         }
         if (StringUtils.isEmpty(rows))
         {
             rows = "10";
         }   
         if (StringUtils.isEmpty(projectid))
         {
        	 map.put("projectId", null);
         }else{
        	 map.put("projectId", projectid);
         }
         if (StringUtils.isEmpty(projectName))
         {
        	 map.put("projectName", null);
         }else{
        	 map.put("projectName", projectName);
         }
         map.put("userId", userId);
         map.put("page", page);
         map.put("rows", rows);
        
		List<Map<String, Object>> list = projectManageMapper.searchProject(map);
		int count = projectManageMapper.searchProjectCount(map);
		
		result.put("total", count);
		result.put("rows", list);
		
		return result;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.READ_COMMITTED)
	public Map<String, Object> mergeRealIncome(String ids, String projectId) {
		Date date = new Date();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMM");
		Map<String, Object> params = new HashMap<>();
		List<String> months = new ArrayList<>();
		MiguIncomeManager manager=null;
		try {
			String[] items =ids.split(",");
			params.put("items", items);
			TMiguProjectBase project = projectManageMapper.selectOneById(projectId);
			List<MiguIncomeManager> list = incomeManagerMapper.getMergeRealIncome(params);
			BigDecimal realIncome = new BigDecimal(0.00);
			BigDecimal realAmount = new BigDecimal(0.00);
			BigDecimal realExclusiveTax = new BigDecimal(0.00);
			MiguIncomeManager incomeManager= new MiguIncomeManager();
			if(list!=null&&list.size()>0){
				MiguIncomeManager income=list.get(0);
				Long mergeId = incomeManagerMapper.getIncomeManagerId();
				for (MiguIncomeManager miguIncomeManager : list) {
					 BigDecimal b1 = new BigDecimal(miguIncomeManager.getRealIncome());  
					   BigDecimal b2 = new BigDecimal(miguIncomeManager.getRealAmount()); 
					   BigDecimal b3 = new BigDecimal(miguIncomeManager.getRealExclusiveTax());  
					   realIncome=realIncome.add(b1);
					   realAmount=realAmount.add(b2);
					   realExclusiveTax=realExclusiveTax.add(b3);
					miguIncomeManager.setMergeStatus("1");
					miguIncomeManager.setMergeId(String.valueOf(mergeId));
					months.add(miguIncomeManager.getCycle());
					incomeManagerMapper.updateByPrimaryKeySelective(miguIncomeManager);
				}
				Collections.sort(months);
				incomeManager.setRealIncome(new Double(realIncome.doubleValue()));
				incomeManager.setRealAmount(new Double(realAmount.doubleValue()));
				incomeManager.setRealExclusiveTax(new Double(realExclusiveTax.doubleValue()));
				incomeManager.setRealStatus("2");
				incomeManager.setBillingStatus("99");
				incomeManager.setRealIncomeTax(list.get(0).getRealIncomeTax());
				incomeManager.setMergeStatus("2");
				incomeManager.setProjectId(project.getProjectId());
				incomeManager.setProjectName(project.getProjectName());
				incomeManager.setCycle(months.get(months.size()-1));
				fmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				incomeManager.setLasteDate(fmt.format(date));
				incomeManager.setIsNeedBill("1");
				incomeManager.setIncomeManagerId(mergeId);
				incomeManager.setOffset(income.getOffset());
				fmt = new SimpleDateFormat("yyyyMM");
				incomeManager.setBzCycleReal(fmt.format(date));
				manager=incomeManagerMapper.selectByUnique(incomeManager.getCycle(), projectId);
				if(manager!=null){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return 	 RetCode.serverError("同一项目，同一业务账期只能存在一条实际收入！");
				}
				incomeManagerMapper.insertSelective(incomeManager);
				return RetCode.success();
		}else{
			return RetCode.serverError("合并记录不存在！");
		}
			
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
			return RetCode.serverError();
		}
		
	}

	@Override
	public Map<String, Object> viewMergeDetail(String mergeId) {
		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> list = incomeManagerMapper.viewMergeDetail( mergeId);
		map.put("rows", list);
		return map;
	}

	@Override
	public Map<String, Object> revokeMerge(List<MiguIncomeManager> list) {
		
		for (MiguIncomeManager incomManager : list) {
			int num=miguIncomeBillingMapper.selectUnBilling(incomManager.getProjectId(),incomManager.getCycle());
			if(num>0){
				return RetCode.serverError("该条实际收入已开票，不能取消合并！");
			}else{
				incomeManagerMapper.deleteByPrimaryKey(incomManager.getIncomeManagerId());
				incomeManagerMapper.revokeMerge(incomManager.getIncomeManagerId().toString());
			}
		}
	 	
		return RetCode.success();
	}

	@Override
	public List<MiguIncomeManager> viewMergeInfo(String id) {
		
		List<MiguIncomeManager> list=incomeManagerMapper.selectById(id);
	
		return list;
	}
	
	@Override
	public List<MiguIncomeBill> viewBillings(String billingKey) {
		
		List<MiguIncomeBill> list=incomeBillMapper.viewBillings(billingKey);
	
		return list;
	}
		
}
