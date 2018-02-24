 package cn.migu.income.controller;


import cn.migu.income.pojo.MiguIncomeBill;
import cn.migu.income.pojo.MiguIncomeDetail;
import cn.migu.income.pojo.MiguIncomeManager;
import cn.migu.income.pojo.MiguUsers;
import cn.migu.income.pojo.TMiguIncomeBilling;
import cn.migu.income.service.MiguIncomeManagerService;
import cn.migu.income.service.UpdateBillingStateService;
import cn.migu.income.util.MiguConstants;
import cn.migu.income.util.PropValue;
import cn.migu.income.util.StringUtil;
import cn.migu.income.webservices.GetTaxInvoiceInfo;
import cn.migu.income.webservices.GetTaxInvoiceInfoResponse;
import cn.migu.income.webservices.SynTaxInvoiceInfoSrv;
import cn.migu.income.webservices.SynTaxInvoiceInfoSrvStub;

import org.apache.axis2.AxisFault;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 收入管理
 *
 * @author chentao
 */
@Controller
@RequestMapping("/income")
public class DrawBillManageController {
    final static Logger log = LoggerFactory.getLogger(DrawBillManageController.class);

    @Autowired
    private MiguIncomeManagerService incomeManagerService;
    
    @Resource(name="UpdateBillingStateService")
    private UpdateBillingStateService billingStateService;
    /**
     * 开票管理页面
     *
     * @author chentao
     */
    @RequestMapping(value = "/drawBillManage")
    public ModelAndView estimateIncomeManage(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("drawBillManage");
        return mav;
    }

    /**
     * 查询该登录用户所有的开票信息
     *
     * @param request
     * @param session queryAllDrawBillIncome
     * @param q_projectId
     * @param q_projectName
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/queryAllBilling", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> queryAllBilling(HttpServletRequest request, HttpSession session,
                                        String q_month_begin, String q_month_end, String q_projectId, String q_projectName, String q_billState,
                                        String q_month_bill,String q_incomeClassId,String q_incomeSectionId,String q_dept,
                                        String q_userName,String q_bill_num,String q_bill_total, String page, String rows) {
        MiguUsers user = (MiguUsers) (session.getAttribute(MiguConstants.USER_KEY));
       //更新开票状态
        billingStateService.updateState();
        Map<String, Object> map = new HashMap<String, Object>();
        int count = 0;
        List<TMiguIncomeBilling> list = new ArrayList<TMiguIncomeBilling>();
        try {
            if (StringUtils.isEmpty(page)) {
                page = "1";
            }
            if (StringUtils.isEmpty(rows)) {
                rows = "10";
            }
            if (StringUtils.isEmpty(q_month_begin)) {
                q_month_begin = null;
            } else {
                q_month_begin = q_month_begin.split("-")[0] + (q_month_begin.split("-")[1].length() == 1 ? 0 + q_month_begin.split("-")[1] : q_month_begin.split("-")[1]);
            }
            if (StringUtils.isEmpty(q_month_end)) {
                q_month_end = null;
            } else {
                q_month_end = q_month_end.split("-")[0] + (q_month_end.split("-")[1].length() == 1 ? 0 + q_month_end.split("-")[1] : q_month_end.split("-")[1]);
            }
            if (StringUtils.isEmpty(q_projectId)) {
                q_projectId = null;
            }
            if (StringUtils.isEmpty(q_projectName)) {
                q_projectName = null;
            }
            if (StringUtils.isEmpty(q_billState)) {
                q_billState = null;
            }
            
            if (StringUtils.isEmpty(q_month_bill)) {
            	q_month_bill = null;
            }else {
            	q_month_bill = q_month_bill.split("-")[0] + (q_month_bill.split("-")[1].length() == 1 ? 0 + q_month_bill.split("-")[1] : q_month_bill.split("-")[1]);
            }
            if (StringUtils.isEmpty(q_incomeClassId)) {
            	q_incomeClassId = null;
            }
            if (StringUtils.isEmpty(q_incomeSectionId)) {
            	q_incomeSectionId = null;
            }
            if (StringUtils.isEmpty(q_dept)) {
            	q_dept = null;
            }
            if (StringUtils.isEmpty(q_userName)) {
            	q_userName = null;
            }
            if (StringUtils.isEmpty(q_bill_num)) {
            	q_bill_num = null;
            }
            if (StringUtils.isEmpty(q_bill_total)) {
            	q_bill_total = null;
            }
           
            
            
            
//            if (user.getDeptId().equals("1")) {
//                user.setDeptId(""); //如果用户部门是财务部，查询所有的数据
//            }
            map.put("dept", user.getDeptId());
            count = incomeManagerService.queryDrawBillTotal(q_month_begin, q_month_end, q_projectId, q_projectName, q_billState,
            		   q_month_bill, q_incomeClassId, q_incomeSectionId, q_dept,
                       q_userName, q_bill_num, q_bill_total,
            		user);
            list = incomeManagerService.queryAllDrawBillIncome(q_month_begin, q_month_end, q_projectId, q_projectName,
                    q_billState,  q_month_bill, q_incomeClassId, q_incomeSectionId, q_dept,
                    q_userName, q_bill_num, q_bill_total, Integer.parseInt(page), Integer.parseInt(rows), user);
            log.info("开票列表查询：总数=" + count + ",查询条件=>月份 =" + q_month_begin + "-" + q_month_end + ";项目编号 =" + q_projectId + ";项目名称 =" + q_projectName + ";开票状态 =" + q_billState);
        } catch (Exception e) {
            log.info("开票查询异常:", e);
            map.put("Exception", e);
            return map;

        }
        map.put("total", count);
        map.put("rows", list);
        return map;
    }


    /**
     * 查询当前登录人员所有的开票审核信息
     *
     * @param request
     * @param session     queryAllDrawBillIncome
     * @param projectId
     * @param projectName
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/queryAllDrawBillIncome", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> queryAllDrawBillIncome(String projectId, String projectName, HttpServletRequest request, HttpSession session, String page, String rows) {
        MiguUsers user = (MiguUsers) (session.getAttribute(MiguConstants.USER_KEY));
        //更新开票状态
        Map<String, Object> map = new HashMap<String, Object>();
        int count = 0;
        List<MiguIncomeManager> list = new ArrayList<MiguIncomeManager>();
        try {
            if (StringUtils.isEmpty(page)) {
                page = "1";
            }
            if (StringUtils.isEmpty(rows)) {
                rows = "20";
            }
            if (StringUtils.isEmpty(projectId)) {
                projectId = null;
            }
            if (StringUtils.isEmpty(projectName)) {
                projectName = null;
            }
            count = incomeManagerService.queryDrawBillTotal_1(projectId, projectName, user);
            if (count != 0) {
                list = incomeManagerService.queryAllDrawBillIncome_1(projectId, projectName,
                        Integer.parseInt(page), Integer.parseInt(rows), user);
            }
            log.info("开票页面查询审核通过的实际收入项目：总数=" + count + ",查询条件 ;项目编号 =" + projectId + ";项目名称 =" + projectName );
        } catch (Exception e) {
            log.info("开票页面查询审核通过的实际收入项目:", e);
            map.put("Exception", e);
            return map;

        }
        map.put("total", count);
        map.put("rows", list);
        return map;
    }

    /**
     * 查询当前登录人员所有的开票审核信息
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/putBillKey", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> putBillKey(HttpServletRequest request, HttpSession session,
                                   String projectId, String incomeManagerId, String drawType) {
        MiguUsers user = (MiguUsers) (session.getAttribute(MiguConstants.USER_KEY));
        Map<String, Object> map = new HashMap<String, Object>();
        int count = 0;
        try {
            String key = "";
            key = drawType + projectId + incomeManagerId + Long.valueOf(System.currentTimeMillis()).toString();
            map.put("code", 100);
            map.put("message", key);

        } catch (Exception e) {
            log.info(" 申请key异常:", e);
            map.put("code", -1);
            map.put("message", "申请key异常!");
        }
        return map;
    }

    /**
     * 查询当前登录人员所有的开票审核信息
     *
     * @param request
     * @param session
     * @return
     */
    @RequestMapping(value = "/putBillingRecord", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> putBillingRecord(HttpServletRequest request, HttpSession session,
                                         String projectId, String incomeManagerId, String drawType, String seq) {
        MiguUsers user = (MiguUsers) (session.getAttribute(MiguConstants.USER_KEY));
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            MiguIncomeManager miguIncomeManager = incomeManagerService.queryByProjectIdAndIncomeManagerId(projectId, incomeManagerId);
            TMiguIncomeBilling tMiguIncomeBilling = new TMiguIncomeBilling();
            tMiguIncomeBilling.setBillingKey(seq);
            tMiguIncomeBilling.setCycle(miguIncomeManager.getCycle());
            tMiguIncomeBilling.setBillType(drawType + "");
            tMiguIncomeBilling.setProjectId(miguIncomeManager.getProjectId());
            tMiguIncomeBilling.setProjectName(miguIncomeManager.getProjectName());
            tMiguIncomeBilling.setUserId(user.getUserId());
            tMiguIncomeBilling.setBzCycle(StringUtil.getTimeStamp("yyyyMM"));

            if (incomeManagerService.insertBilling(tMiguIncomeBilling) != 1) {
                map.put("code", -1);
                map.put("message", "插入记录失败！");
            }
        } catch (Exception e) {
            log.info(" 插入开票申请记录失败:", e);
            e.printStackTrace();
            map.put("code", -1);
            map.put("message", "插入开票申请记录异常!");
        }
        map.put("code", 100);
        map.put("message", "插入记录成功！");
        return map;
    }

    /**
     * 开票信息导出
     * @return
     * @throws UnsupportedEncodingException 
     */
    @RequestMapping(value = "/exportBillFile", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> exportBillFile(HttpServletRequest request, HttpSession session,
        String q_month_begin, String q_month_end,String q_projectId, String q_projectName, 
        String q_billState,String  billingKeys, String q_month_bill,String q_incomeClassId,String q_incomeSectionId,
        String q_dept,String q_userName,String q_bill_num,String q_bill_total) throws UnsupportedEncodingException
    {
    	MiguUsers user = (MiguUsers)(session.getAttribute(MiguConstants.USER_KEY));
        Map<String, Object> map = new HashMap<String, Object>();
        
        if (StringUtils.isEmpty(q_month_begin)) {
            q_month_begin = null;
        } else {
            q_month_begin = q_month_begin.split("-")[0] + (q_month_begin.split("-")[1].length() == 1 ? 0 + q_month_begin.split("-")[1] : q_month_begin.split("-")[1]);
        }
        
        if (StringUtils.isEmpty(q_month_end)) {
            q_month_end = null;
        } else {
            q_month_end = q_month_end.split("-")[0] + (q_month_end.split("-")[1].length() == 1 ? 0 + q_month_end.split("-")[1] : q_month_end.split("-")[1]);
        }
        if (StringUtils.isEmpty(q_month_bill)) {
        	q_month_bill = null;
        } else {
        	q_month_bill = q_month_bill.split("-")[0] + (q_month_bill.split("-")[1].length() == 1 ? 0 + q_month_bill.split("-")[1] : q_month_bill.split("-")[1]);
        }
        if (StringUtils.isEmpty(q_projectId)) {
            q_projectId = null;
        }
        if (StringUtils.isEmpty(q_projectName)) {
            q_projectName = null;
        }
        if (StringUtils.isEmpty(q_billState)) {
            q_billState = null;
        }
      /*  if (user.getDeptId().equals("1")) {
            user.setDeptId(""); //如果用户部门是财务部，查询所有的数据
        }*/
        
        if (StringUtils.isEmpty(q_incomeClassId)) {
        	q_incomeClassId = null;
        }
        if (StringUtils.isEmpty(q_incomeSectionId)) {
        	q_incomeSectionId = null;
        }
        if (StringUtils.isEmpty(q_dept)) {
        	q_dept = null;
        }
        if (StringUtils.isEmpty(q_userName)) {
        	q_userName = null;
        }
        if (StringUtils.isEmpty(q_bill_num)) {
        	q_bill_num = null;
        }
        if (StringUtils.isEmpty(q_bill_total)) {
        	q_bill_total = null;
        }
        if(StringUtils.isEmpty(billingKeys))
        {
        	billingKeys = null;
        }    
        
        List<TMiguIncomeBilling> dataList = new ArrayList<TMiguIncomeBilling>();
		try {
			dataList = incomeManagerService.queryAllBill(q_month_begin, q_month_end, q_projectId, q_projectName, 
			         q_billState, billingKeys, q_month_bill, q_incomeClassId, q_incomeSectionId,
			          q_dept, q_userName, q_bill_num, q_bill_total,
			         user);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        
        if (dataList.size() == 0)
        {
            log.info("该条件下无开票数据");
            map.put("reCode", -1);
            map.put("reStr", "该条件下无开票数据");
        }
        else
        {
        	
        	WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
            ServletContext servletContext = webApplicationContext.getServletContext();          
        	String tempFile= servletContext.getRealPath("/reportTemplate")+"/开票信息管理列表.xls";//模板文件的地址
        	File temp_file = new File(tempFile);
        	
        	
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
            
            
            HSSFWorkbook hssfworkbook = null;
			try {
				hssfworkbook = new HSSFWorkbook(new FileInputStream(reportFile));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
            HSSFSheet    hssfsheet    = hssfworkbook.getSheetAt(0);
            int i = hssfsheet.getPhysicalNumberOfRows();
            
            HSSFCellStyle style1 = hssfworkbook.createCellStyle();
            style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
            style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
            style1.setTopBorderColor(HSSFColor.BLACK.index);
            style1.setLeftBorderColor(HSSFColor.BLACK.index);
            style1.setRightBorderColor(HSSFColor.BLACK.index);
            style1.setBottomBorderColor(HSSFColor.BLACK.index);
            
            for (int j = 0; j < dataList.size(); j++) {
            	TMiguIncomeBilling tMiguIncomeBilling = (TMiguIncomeBilling) dataList.get(j);
            	//创建一行  
            	HSSFRow rowData = hssfsheet.createRow(i++);  
                createCell(style1, rowData, 0, StringUtil.null2Blank(tMiguIncomeBilling.getBillingKey())); 
                createCell(style1, rowData, 1, StringUtil.null2Blank(tMiguIncomeBilling.getBzCycle())); 
                createCell(style1, rowData, 2, StringUtil.null2Blank(tMiguIncomeBilling.getCycle())); 
                createCell(style1, rowData, 3, StringUtil.null2Blank(tMiguIncomeBilling.getProjectId())); 
                createCell(style1, rowData, 4, StringUtil.null2Blank(new String(tMiguIncomeBilling.getProjectName().getBytes("UTF-8"),"UTF-8")));
                createCell(style1, rowData, 5, StringUtil.null2Blank(tMiguIncomeBilling.getClassName())); 
                createCell(style1, rowData, 6, StringUtil.null2Blank(tMiguIncomeBilling.getSectionName())); 
                createCell(style1, rowData, 7, StringUtil.null2Blank(tMiguIncomeBilling.getDeptName())); 
                createCell(style1, rowData, 8, StringUtil.null2Blank(tMiguIncomeBilling.getUserName())); 
                String billType = "";
                if(tMiguIncomeBilling.getBillType().equals("0")){
                	billType = "已申请专票";
                }else if(tMiguIncomeBilling.getBillType().equals("1")){
                	billType = "已申请普票";
                }
                createCell(style1, rowData, 9, StringUtil.null2Blank(billType)); 
                  
                createCell(style1, rowData, 10, StringUtil.null2Blank(tMiguIncomeBilling.getInvoiceCode()==null?"":tMiguIncomeBilling.getInvoiceCode()));  
                createCell(style1, rowData, 11, StringUtil.null2Blank(tMiguIncomeBilling.getInvoiceNumber()==null?"":tMiguIncomeBilling.getInvoiceNumber()));  
                createCell(style1, rowData, 12, StringUtil.null2Blank(tMiguIncomeBilling.getTotal()==null?"":tMiguIncomeBilling.getTotal().toString()));
                createCell(style1, rowData, 13, StringUtil.null2Blank(tMiguIncomeBilling.getBillingTime()==null?"":tMiguIncomeBilling.getBillingTime()));  
                createCell(style1, rowData, 14, StringUtil.null2Blank(tMiguIncomeBilling.getBillingTotal()==null?"":tMiguIncomeBilling.getBillingTotal().toString()));
                String status = "";
                if(tMiguIncomeBilling.getBillingStatus()!=null){
                	if(tMiguIncomeBilling.getBillingStatus().equals("00")){
                    	status = "开具成功";
                    }else if(tMiguIncomeBilling.getBillingStatus().equals("02")){
                    	status = "空白作废票";
                    }else if(tMiguIncomeBilling.getBillingStatus().equals("03")){
                    	status = "已开作废票";
                    }else if(tMiguIncomeBilling.getBillingStatus().equals("11")){
                    	status = "待开发票";
                    }else if(tMiguIncomeBilling.getBillingStatus().equals("12")){
                    	status = "开具中发票";
                    }else if(tMiguIncomeBilling.getBillingStatus().equals("99")){
                    	status = "未开票";
                    }
                }
                createCell(style1, rowData, 15, status); 
			}
            
            OutputStream os = null;
			try {
				os = new FileOutputStream(reportFile);
				hssfworkbook.write(os);
				os.close();
				map.put("reCode", 100);
				map.put("fileName", reportFile.getName());
				log.info("开票明细文件生成成功");
			} catch (Exception e) {
				map.put("reCode", -1);
				map.put("reStr", "开票明细文件生成异常");
				e.printStackTrace();
			}
        }
        return map;
    }
    
  //poi表格行创建方法
    public static void createCell(HSSFCellStyle style, HSSFRow row, int column,  
            String value) {  
    	@SuppressWarnings("deprecation")
		HSSFCell cell = row.createCell((short)column);
        cell.setCellStyle(style);
        cell.setCellValue(value);
    }
    
    /**
     * 删除开票信息
     *
     * @param request
     * @param session
     * @return
     * @throws AxisFault 
     */
    @RequestMapping(value = "/deleteBilling", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> deleteBilling(HttpServletRequest request, HttpSession session,String seq,String projectId) throws AxisFault {
        Map<String, Object> map = new HashMap<String, Object>();
        String result = "";
        try
        {
            result = incomeManagerService.deleteBilling(seq,projectId);
        }
        catch (Exception e)
        {
            log.error("开票删除异常:", e);
        }
        
        if(result.equals("-100")){
        	map.put("reCode", -100);
        	map.put("reStr", "该笔开票数据已存在报账平台，无法删除！");
        }
        if(result.equals("100")){
        	map.put("reCode", 100);
        }
        if(result.equals("-1")){
        	map.put("reCode", -1);
        	map.put("reStr", "开票删除失败！");
        }
        return map;
    }
    
    /**
     * 查询发票明细
     * @param request
     * @param session
     * @param billingKey
     * @return
     */
    @RequestMapping(value = "/viewBillings", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> viewBillings(HttpServletRequest request,HttpSession session, 
        String billingKey)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        List<MiguIncomeBill> list =new ArrayList<MiguIncomeBill>();
        try
        {
            list = incomeManagerService.viewBillings(billingKey);
        }
        catch (Exception e)
        { 
            log.info("Exception:", e);
            e.printStackTrace();
        }
        map.put("rows", list);
        return map;
    }
    
}
