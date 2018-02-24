package cn.migu.income.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import cn.migu.income.dao.TMiguPriceConfigInfoMapper;
import cn.migu.income.dao.TMiguPriceConfigMapper;
import cn.migu.income.pojo.MiguIncomeDetail;
import cn.migu.income.pojo.MiguIncomeManager;
import cn.migu.income.pojo.MiguProjectProfitReport;
import cn.migu.income.pojo.MiguUsers;
import cn.migu.income.pojo.TMiguPriceConfigInfo;
import cn.migu.income.service.MiguIncomeManagerService;
import cn.migu.income.util.MiguConstants;
import cn.migu.income.util.PropValue;
import cn.migu.income.util.StringUtil;
import cn.migu.income.util.TxtFileOperation;



/**
 * 收入管理
 * @author chentao
 *
 */
@Controller
@RequestMapping("/income")
public class IncomeManageController
{
    final static Logger log = LoggerFactory.getLogger(IncomeManageController.class);
    
    @Autowired
    private MiguIncomeManagerService incomeManagerService;

    /**
     * 收入管理页面
     * @author chentao
     */
    @RequestMapping(value = "/estimateIncomeManage")
    public ModelAndView estimateIncomeManage(HttpServletRequest request)
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("estimateIncomeManage");
        return mav;
    }
    
    /**
     * 收入管理页面
     * @author chentao
     */
    @RequestMapping(value = "/realIncomeManage")
    public ModelAndView realIncomeManage(HttpServletRequest request)
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("realIncomeManage");
        return mav;
    }
    /**
     * 查询当前登录人员所有的收入
     * @param request
     * @param session
     * @param q_month
     * @param q_projectId
     * @param q_projectName
     * @param q_estimateState
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/queryAllRealIncome", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> queryAllRealIncome(HttpServletRequest request,HttpSession session, 
        String q_month,String q_projectId,String q_projectName,String q_estimateState,String realIncomeStatus,
        String q_incomeClassId, String q_incomeSectionId, String q_dept, String q_userName,
        String est_income, String est_exclusive_tax, String q_cx,String q_bill,
        String q_merge,String bzCycleReal,String page,String rows)
    {
    	MiguUsers user = (MiguUsers)(session.getAttribute(MiguConstants.USER_KEY));
        Map<String, Object> map = new HashMap<String, Object>();
        int count=0;
        List<MiguIncomeManager> list = new ArrayList<MiguIncomeManager>();
        try
        {
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
            
            if(StringUtils.isEmpty(q_incomeClassId))
            {
            	q_incomeClassId = null;
            }
            if(StringUtils.isEmpty(q_incomeSectionId))
            {
            	q_incomeSectionId = null;
            }
            if(StringUtils.isEmpty(q_dept))
            {
            	q_dept = null;
            }
            if(StringUtils.isEmpty(q_userName))
            {
            	q_userName = null;
            }
            if(StringUtils.isEmpty(est_income))
            {
            	est_income = null;
            }
            if(StringUtils.isEmpty(est_exclusive_tax))
            {
            	est_exclusive_tax = null;
            }
            
            if(StringUtils.isEmpty(q_cx))
            {
            	q_cx = null;
            }
            if(StringUtils.isEmpty(q_bill))
            {
            	q_bill = null;
            }
            if(StringUtils.isEmpty(q_merge))
            {
            	q_merge = null;
            }
            if(StringUtils.isEmpty(bzCycleReal))
            {
            	bzCycleReal = null;
            }else{
            	bzCycleReal = bzCycleReal.split("-")[0]+(bzCycleReal.split("-")[1].length()==1?0+bzCycleReal.split("-")[1]:bzCycleReal.split("-")[1]);
            }
            
            if(StringUtil.isNotEmpty(q_userName)&&(q_userName.contains("_")||q_userName.contains("%")))
            {
            	q_userName=StringUtil.getSpecialParam(q_userName);
            }
            
            if(StringUtils.isEmpty(q_estimateState) || "K".equals(q_estimateState))
            {
            	q_estimateState = null;
            }
            if(StringUtils.isEmpty(realIncomeStatus)  || "K".equals(realIncomeStatus)){
            	realIncomeStatus=null;
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
           count = incomeManagerService.queryTotal(q_month, q_projectId, q_projectName,q_estimateState,realIncomeStatus,
        		    q_incomeClassId, q_incomeSectionId,  q_dept,  q_userName,  est_income,
        	         est_exclusive_tax, q_cx, q_bill,q_merge,bzCycleReal, user);
           list = incomeManagerService.queryAllRealIncome(q_month, q_projectId, q_projectName,
        		   q_estimateState,realIncomeStatus,
        		    q_incomeClassId, q_incomeSectionId, q_dept, q_userName,
        	         est_income, est_exclusive_tax,q_cx,q_bill,q_merge,bzCycleReal,
        		   Integer.parseInt(page), Integer.parseInt(rows),user);
           log.info("实际收入列表查询：总数=" + count + ",月份 =" + q_month + ";项目编号 =" + q_projectId + ";项目名称 =" + q_projectName + ";预估收入状态 =" + q_estimateState);
        }
        catch (Exception e)
        {
        	log.info("实际收入查询异常:", e);
        	map.put("Exception", e);
            return map;
            
        }
        map.put("total", count);
        map.put("rows", list);
        return map;
    }
    
    /**
     * 查询预估收入
     * @param request
     * @param session
     * @param q_month
     * @param q_projectId
     * @param q_projectName
     * @param q_estimateState
     * @param realIncomeStatus
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/selectEstimateIncome", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> selectEstimateIncome(HttpServletRequest request,HttpSession session, 
        String q_month,String q_projectId,String q_projectName,String q_estimateState,
        String realIncomeStatus,String q_incomeClassId,String q_incomeSectionId,
        String q_dept ,String q_userName, String est_income,String est_exclusive_tax,String bzCycle, String page,String rows)
    {
    	MiguUsers user = (MiguUsers)(session.getAttribute(MiguConstants.USER_KEY));
        Map<String, Object> map = new HashMap<String, Object>();
        int count=0;
        List<Map<String, Object>> list = new ArrayList<>();
        try
        {
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
            
            if(StringUtils.isEmpty(q_incomeClassId))
            {
            	q_incomeClassId = null;
            }
            if(StringUtils.isEmpty(q_incomeSectionId))
            {
            	q_incomeSectionId = null;
            }
            if(StringUtils.isEmpty(q_dept))
            {
            	q_dept = null;
            }
            if(StringUtils.isEmpty(q_userName))
            {
            	q_userName = null;
            }
            if(StringUtils.isEmpty(est_income))
            {
            	est_income = null;
            }
            if(StringUtils.isEmpty(est_exclusive_tax))
            {
            	est_exclusive_tax = null;
            }
            if(StringUtils.isEmpty(bzCycle))
            {
            	bzCycle = null;
            }else{
            	bzCycle = bzCycle.split("-")[0]+(bzCycle.split("-")[1].length()==1?0+bzCycle.split("-")[1]:bzCycle.split("-")[1]);
            }
        
            if(StringUtil.isNotEmpty(q_userName)&&(q_userName.contains("_")||q_userName.contains("%")))
            {
            	q_userName=StringUtil.getSpecialParam(q_userName);
            }
            
            if(StringUtils.isEmpty(q_estimateState) || "K".equals(q_estimateState))
            {
            	q_estimateState = null;
            }
            if(StringUtils.isEmpty(realIncomeStatus)  || "K".equals(realIncomeStatus)){
            	realIncomeStatus=null;
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
            
            
           count = incomeManagerService.queryRecodes(q_month, q_projectId, q_projectName,q_estimateState,realIncomeStatus, q_incomeClassId, q_incomeSectionId,
        	         q_dept , q_userName,  est_income, est_exclusive_tax,bzCycle,user);
           list = incomeManagerService.selectEstimateIncome(q_month, q_projectId, q_projectName,
        		   q_estimateState,realIncomeStatus, q_incomeClassId, q_incomeSectionId,
        	         q_dept , q_userName,  est_income, est_exclusive_tax,bzCycle,Integer.parseInt(page), Integer.parseInt(rows),user);
           log.info("预估收入列表查询：总数=" + count + ",月份 =" + q_month + ";项目编号 =" + q_projectId + ";项目名称 =" + q_projectName + ";预估收入状态 =" + q_estimateState);
        }
        catch (Exception e)
        {
        	log.info("预估收入查询异常:", e);
        	map.put("Exception", e);
            return map;
            
        }
        map.put("total", count);
        map.put("rows", list);
        return map;
    }
    
    
    /**
     * 查询与收入明细
     * @param request
     * @param session
     * @param incomeManagerId
     * @return
     */
    @RequestMapping(value = "/viewIncomeDetails", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> viewIncomeDetails(HttpServletRequest request,HttpSession session, 
        String incomeManagerId)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        List<MiguIncomeDetail> list =new ArrayList<MiguIncomeDetail>();
        try
        {
            list = incomeManagerService.viewIncomeDetails(incomeManagerId);
            log.info("预估收入明细查询：预估收入ID=" + incomeManagerId);
            log.info("预估收入明细查询结果：" + list);
        }
        catch (Exception e)
        { 
            log.info("Exception:", e);
            e.printStackTrace();
        }
        map.put("rows", list);
        return map;
    }
    
    /**
     *	导入产品信息
     * @param request
     * @param session
     * @param incomeManagerId
     * @return
     */
    @RequestMapping(value = "/importIncomeDetails", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> importIncomeDetails(HttpServletRequest request,HttpServletResponse response,HttpSession session)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
        	map=incomeManagerService.importProduct(request, response);
            log.info("导入产品信息：" + JSON.toJSONString(map.get("list")));
        }
        catch (Exception e)
        { 
            log.info("Exception:", e);
            e.printStackTrace();
        }
        return map;
    }
    
    /**
     * 生成含产品信息的模板
     * @param request
     * @param response
     * @param projectId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/downLoadExcel", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> downLoadExcel(HttpServletRequest request, HttpServletResponse response,@RequestBody Map<String, Object>
	param)
    		   throws Exception {

        	return incomeManagerService.downLoadExcel(request, response, param);
    }

       
        /**
         * 下载已上传附件
         * @param request
         * @param session
         * @param response
         * @param fileName
         * @throws FileNotFoundException
         */
        @RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
        public @ResponseBody int downloadFile(HttpServletRequest request, HttpSession session, HttpServletResponse response,String fileName)
            throws FileNotFoundException
        {        
            String filePath = PropValue.getPropValue("report_income_diff"); //文件路径
            File downloadFile = new File(filePath + fileName);
            try{
    	        if (downloadFile.exists())
    	        {
    	            TxtFileOperation.downloadLocal(response, downloadFile);
    	        }
    	        return 1;//成功
            }catch(Exception e){
            	log.error("下载已上传附件异常:", e);
            	return 0;//失败
            }
        }
        
        /**
         * 下载空白模板
         * @param request
         * @param session
         * @param response
         * @param fileName
         * @return
         * @throws FileNotFoundException
         */
        @RequestMapping(value = "/downLoad", method = RequestMethod.POST)
        public @ResponseBody int downLoad(HttpServletRequest request, HttpSession session, HttpServletResponse response)
            throws FileNotFoundException
        {        
        	 //获取模板文件
        	WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
            ServletContext servletContext = webApplicationContext.getServletContext();          
        	String tempFile= servletContext.getRealPath("/reportTemplate")+"/产品信息模板.xls";//模板文件的地址
        	File temp_file = new File(tempFile);
            try{
    	        if (temp_file.exists())
    	        {
    	            TxtFileOperation.downloadLocal(response, temp_file);
    	        }
    	        return 1;//成功
            }catch(Exception e){
            	log.error("下载已上传附件异常:", e);
            	return 0;//失败
            }
        }
     
    @RequestMapping(value = "/viewRealIncomeDetails", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> viewRealIncomeDetails(HttpServletRequest request,HttpSession session, 
        String incomeManagerId){
    	return incomeManagerService.viewRealIncomeDetails(incomeManagerId);
    }
    
    /**
     * 删除预估收入
     * @author chentao
     * @param request
     * @param incomeManagerId
     * @return
     */
    @RequestMapping(value = "/dellIncomeManager", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> dellIncomeManager(HttpServletRequest request,HttpSession session, 
    		String incomeManagerId, String projectName, String cycle,String projectId)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        MiguUsers user = ((MiguUsers)(session.getAttribute(MiguConstants.USER_KEY)));
        Map<String, Object> result = new HashMap<String, Object>();
        try
        {
            result = incomeManagerService.dellIncomeManager(incomeManagerId);
        }
        catch (Exception e)
        {
            log.error("Exception:", e);
        }
        
        if ("0".equals(result.get("code")))
        {
            map.put("reCode", 100);
            map.put("reStr", "删除成功！");
            log.info("预估收入删除成功，收入编号:"+incomeManagerId+",项目编号:"+projectId+",项目名称:"+projectName+",月份:"+cycle+",操作人："+user.getUserName()+",操作时间"+StringUtil.getCurrDateStrContainHMS());
        }
        else
        {
            map.put("reCode", -1);
            map.put("reStr", result.get("reason"));
            log.info("预估收入删除失败，收入编号:"+incomeManagerId+",项目编号:"+projectId+",项目名称:"+projectName+",月份:"+cycle+",操作人："+user.getUserName()+",操作时间"+StringUtil.getCurrDateStrContainHMS());
        }
        return map;
    }
    
    /**
     * 删除实际收入
     * @author chentao
     * @param request
     * @param incomeManagerId
     * @return
     */
    @RequestMapping(value = "/dellRealManager", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> dellRealManager(HttpServletRequest request,HttpSession session, 
    		String incomeManagerId, String projectName, String cycle,String projectId)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        MiguUsers user = ((MiguUsers)(session.getAttribute(MiguConstants.USER_KEY)));
        Map<String, Object> result = new HashMap<String, Object>();
        try
        {
            result = incomeManagerService.dellIncomeManager(incomeManagerId);
        }
        catch (Exception e)
        {
            log.error("Exception:", e);
        }
        
        if ("0".equals(result.get("code")))
        {
            map.put("reCode", 100);
            map.put("reStr", "删除成功！");
            log.info("实际收入删除成功，收入编号:"+incomeManagerId+",项目编号:"+projectId+",项目名称:"+projectName+",月份:"+cycle+",操作人："+user.getUserName()+",操作时间"+StringUtil.getCurrDateStrContainHMS());
        }
        else
        {
            map.put("reCode", -1);
            map.put("reStr", result.get("reason"));
            log.info("实际收入删除失败，收入编号:"+incomeManagerId+",项目编号:"+projectId+",项目名称:"+projectName+",月份:"+cycle+",操作人："+user.getUserName()+",操作时间"+StringUtil.getCurrDateStrContainHMS());
        }
        return map;
    }
    
    /**
     * 预估收入导出
     * @param request
     * @param session
     * @param provCode
     * @param chCode
     * @param activeDate
     * @return
     * @throws UnsupportedEncodingException 
     */
    @RequestMapping(value = "/exportFile", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> exportFile(HttpServletRequest request, HttpSession session,
        String q_month, String q_projectId, String q_projectName, String q_estimateState,
        String q_incomeClassId,String q_incomeSectionId,
        String q_dept ,String q_userName, String est_income,String est_exclusive_tax,String bzCycle,
        String incomeManagerIds) throws UnsupportedEncodingException
    {
    	MiguUsers user = (MiguUsers)(session.getAttribute(MiguConstants.USER_KEY));
        Map<String, Object> map = new HashMap<String, Object>();
        
        if (StringUtils.isEmpty(q_month))
        {
        	q_month = null;
        }else{
        	q_month = q_month.split("-")[0]+(q_month.split("-")[1].length()==1?0+q_month.split("-")[1]:q_month.split("-")[1]);
        }
        
        if (StringUtils.isEmpty(bzCycle))
        {
        	bzCycle = null;
        }else{
        	bzCycle = bzCycle.split("-")[0]+(bzCycle.split("-")[1].length()==1?0+bzCycle.split("-")[1]:bzCycle.split("-")[1]);
        }
        
        if (StringUtils.isEmpty(q_projectId))
        {
        	q_projectId = null;
        }
        if(StringUtils.isEmpty(q_projectName))
        {
        	q_projectName = null;
        }
        
        if(StringUtils.isEmpty(q_incomeClassId))
        {
        	q_incomeClassId = null;
        }
        if(StringUtils.isEmpty(q_incomeSectionId))
        {
        	q_incomeSectionId = null;
        }
        if(StringUtils.isEmpty(q_dept))
        {
        	q_dept = null;
        }
        if(StringUtils.isEmpty(q_userName))
        {
        	q_userName = null;
        }
        if(StringUtils.isEmpty(est_income))
        {
        	est_income = null;
        }
        if(StringUtils.isEmpty(est_exclusive_tax))
        {
        	est_exclusive_tax = null;
        }
        
        if(StringUtil.isNotEmpty(q_userName)&&(q_userName.contains("_")||q_userName.contains("%")))
        {
        	q_userName=StringUtil.getSpecialParam(q_userName);
        }
        if(StringUtils.isEmpty(q_estimateState) || "K".equals(q_estimateState))
        {
        	q_estimateState = null;
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
        if(StringUtils.isEmpty(incomeManagerIds))
        {
        	incomeManagerIds = null;
        }
        
        List<MiguIncomeManager> dataList = new ArrayList<MiguIncomeManager>();
		try {
			dataList = incomeManagerService.queryAllIncomeManager(q_month, q_projectId, q_projectName, q_estimateState,q_incomeClassId,q_incomeSectionId,q_dept, 
					q_userName,  est_income, est_exclusive_tax,bzCycle, incomeManagerIds ,user);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        
        if (dataList.size() == 0)
        {
            log.info("该条件下无预估收入数据");
            map.put("reCode", -1);
            map.put("reStr", "该条件下无预估收入数据");
        }
        else
        {
        	
        	WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
            ServletContext servletContext = webApplicationContext.getServletContext();          
        	String tempFile= servletContext.getRealPath("/reportTemplate")+"/预估收入管理列表.xls";//模板文件的地址
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
            
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");//格式化设置  
            
            for (int j = 0; j < dataList.size(); j++) {
            	MiguIncomeManager miguIncomeManager = (MiguIncomeManager) dataList.get(j);
            	//创建一行  
            	HSSFRow rowData = hssfsheet.createRow(i++); 
            	 createCell(style1, rowData, 0, StringUtil.null2Blank(miguIncomeManager.getBzCycle()==null?"":miguIncomeManager.getBzCycle()));  
                createCell(style1, rowData, 1, StringUtil.null2Blank(miguIncomeManager.getCycle()));  
                createCell(style1, rowData, 2, StringUtil.null2Blank(miguIncomeManager.getProjectId())); 
                createCell(style1, rowData, 3, StringUtil.null2Blank(new String(miguIncomeManager.getProjectName().getBytes("UTF-8"),"UTF-8")));  
                createCell(style1, rowData, 4, StringUtil.null2Blank(miguIncomeManager.getClassName()==null?"":miguIncomeManager.getClassName()));  
                createCell(style1, rowData, 5, StringUtil.null2Blank(miguIncomeManager.getSectionName()==null?"":miguIncomeManager.getSectionName()));  
                createCell(style1, rowData, 6, StringUtil.null2Blank(miguIncomeManager.getEstimateIncome()==null?"":decimalFormat.format(miguIncomeManager.getEstimateIncome())));  
                createCell(style1, rowData, 7, StringUtil.null2Blank(miguIncomeManager.getEstimateIncomeTax()==null?"":decimalFormat.format(miguIncomeManager.getEstimateIncomeTax())));  
                createCell(style1, rowData, 8, StringUtil.null2Blank(miguIncomeManager.getEstimateAmount()==null?"":decimalFormat.format(miguIncomeManager.getEstimateAmount())));  
                createCell(style1, rowData, 9, StringUtil.null2Blank(miguIncomeManager.getEstimateExclusiveTax()==null?"":decimalFormat.format(miguIncomeManager.getEstimateExclusiveTax())));  
                createCell(style1, rowData, 10, StringUtil.null2Blank(miguIncomeManager.getDeptName()==null?"":miguIncomeManager.getDeptName()));  
                createCell(style1, rowData, 11, StringUtil.null2Blank(miguIncomeManager.getUserName()==null?"":miguIncomeManager.getUserName()));  
                
                if(miguIncomeManager.getEstimateExplain()==null){
                	 createCell(style1, rowData, 12, "");
                }else{
                	createCell(style1, rowData, 12, StringUtil.null2Blank(new String(miguIncomeManager.getEstimateExplain().getBytes("UTF-8"),"UTF-8")));
                }
                String status = "";
                if(!StringUtil.isEmpty(miguIncomeManager.getEastimateStatus())){
                	if(miguIncomeManager.getEastimateStatus().equals("0")){
                    	status = "草稿";
                    }else if(miguIncomeManager.getEastimateStatus().equals("1")){
                    	status = "待审核("+miguIncomeManager.getAuditPerson()+")";
                    }else if(miguIncomeManager.getEastimateStatus().equals("2")){
                    	status = "审核通过";
                    }else{
                    	status = "驳回";
                    }
                }
                createCell(style1, rowData, 13, status);  
			}
            
            OutputStream os = null;
			try {
				os = new FileOutputStream(reportFile);
				hssfworkbook.write(os);
				os.close();
				map.put("reCode", 100);
				map.put("fileName", reportFile.getName());
				log.info("预估收入文件生成成功");
			} catch (Exception e) {
				map.put("reCode", -1);
				map.put("reStr", "预估收入文件生成异常");
				e.printStackTrace();
			}
        }
        return map;
    }
    
    /**
     * 实际收入导出
     * @param request
     * @param session
     * @param provCode
     * @param chCode
     * @param activeDate
     * @return
     * @throws UnsupportedEncodingException 
     */
    @RequestMapping(value = "/realIncomeExportFile", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> realIncomeExportFile(HttpServletRequest request, HttpSession session,
        String q_month, String q_projectId, String q_projectName, String realIncomeStatus, String incomeManagerIds,
        String q_incomeClassId,String q_incomeSectionId, String q_dept,String q_userName, String est_income,
        String est_exclusive_tax, String q_cx, String q_bill,String q_merge,String bzCycleReal
    		) throws UnsupportedEncodingException
    {
    	MiguUsers user = (MiguUsers)(session.getAttribute(MiguConstants.USER_KEY));
        Map<String, Object> map = new HashMap<String, Object>();
        
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
        
        if(StringUtils.isEmpty(q_incomeClassId))
        {
        	q_incomeClassId = null;
        }
        if(StringUtils.isEmpty(q_incomeSectionId))
        {
        	q_incomeSectionId = null;
        }
        if(StringUtils.isEmpty(q_dept))
        {
        	q_dept = null;
        }
        if(StringUtils.isEmpty(q_userName))
        {
        	q_userName = null;
        }
        if(StringUtils.isEmpty(est_income))
        {
        	est_income = null;
        }
        if(StringUtils.isEmpty(est_exclusive_tax))
        {
        	est_exclusive_tax = null;
        }
        
        if(StringUtils.isEmpty(q_cx))
        {
        	q_cx = null;
        }
        if(StringUtils.isEmpty(q_bill))
        {
        	q_bill = null;
        }
        if(StringUtils.isEmpty(q_merge))
        {
        	q_merge = null;
        }
        if(StringUtils.isEmpty(bzCycleReal))
        {
        	bzCycleReal = null;
        }else{
        	bzCycleReal = bzCycleReal.split("-")[0]+(bzCycleReal.split("-")[1].length()==1?0+bzCycleReal.split("-")[1]:bzCycleReal.split("-")[1]);
        }
        
        if(StringUtil.isNotEmpty(q_userName)&&(q_userName.contains("_")||q_userName.contains("%")))
        {
        	q_userName=StringUtil.getSpecialParam(q_userName);
        }
        
        if(StringUtils.isEmpty(realIncomeStatus) || "K".equals(realIncomeStatus))
        {
        	realIncomeStatus = null;
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
        if(StringUtils.isEmpty(incomeManagerIds))
        {
        	incomeManagerIds = null;
        }
        
        List<MiguIncomeManager> dataList = new ArrayList<MiguIncomeManager>();
		try {
			dataList = incomeManagerService.queryAllRealIncomeManager(q_month, q_projectId, q_projectName, realIncomeStatus, incomeManagerIds,
					    q_incomeClassId,  q_incomeSectionId, q_dept, q_userName,
				         est_income, est_exclusive_tax,q_cx, q_bill, q_merge,bzCycleReal,user);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        
        if (dataList.size() == 0)
        {
            log.info("该条件下无实际收入数据");
            map.put("reCode", -1);
            map.put("reStr", "该条件下无实际收入数据");
        }
        else
        {
        	
        	WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
            ServletContext servletContext = webApplicationContext.getServletContext();          
        	String tempFile= servletContext.getRealPath("/reportTemplate")+"/实际收入管理列表.xls";//模板文件的地址
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
            
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");//格式化设置  
            
            for (int j = 0; j < dataList.size(); j++) {
            	MiguIncomeManager miguIncomeManager = (MiguIncomeManager) dataList.get(j);
            	//创建一行  
            	HSSFRow rowData = hssfsheet.createRow(i++);  
                createCell(style1, rowData, 0, StringUtil.null2Blank(miguIncomeManager.getBzCycleReal()==null?"":miguIncomeManager.getBzCycleReal()));  
                createCell(style1, rowData, 1, StringUtil.null2Blank(miguIncomeManager.getCycle()));  
                createCell(style1, rowData, 2, StringUtil.null2Blank(miguIncomeManager.getProjectId())); 
                createCell(style1, rowData, 3, StringUtil.null2Blank(new String(miguIncomeManager.getProjectName().getBytes("UTF-8"),"UTF-8")));  
                createCell(style1, rowData, 4, StringUtil.null2Blank(miguIncomeManager.getClassName()==null?"":miguIncomeManager.getClassName()));  
                createCell(style1, rowData, 5, StringUtil.null2Blank(miguIncomeManager.getSectionName()==null?"":miguIncomeManager.getSectionName()));  
                createCell(style1, rowData, 6, StringUtil.null2Blank(miguIncomeManager.getRealIncome()==null?"":decimalFormat.format(miguIncomeManager.getRealIncome())));
                createCell(style1, rowData, 7, StringUtil.null2Blank(miguIncomeManager.getRealIncomeTax()==null?"":decimalFormat.format(miguIncomeManager.getRealIncomeTax())));  
                createCell(style1, rowData, 8, StringUtil.null2Blank(miguIncomeManager.getRealAmount()==null?"":decimalFormat.format(miguIncomeManager.getRealAmount())));  
                createCell(style1, rowData, 9, StringUtil.null2Blank(miguIncomeManager.getRealExclusiveTax()==null?"":decimalFormat.format(miguIncomeManager.getRealExclusiveTax())));
                String status = "";
                if(!StringUtil.isEmpty(miguIncomeManager.getRealStatus())){
                		if( miguIncomeManager.getRealStatus().equals("0")){
		                	status = "草稿";
		                }else if(miguIncomeManager.getRealStatus().equals("1")){
		                	status = "待审核("+miguIncomeManager.getAuditPerson()+")";
		                }else if(miguIncomeManager.getRealStatus().equals("2")){
		                	status = "审核通过";
		                }else{
		                	status = "驳回";
		                }
                }
                createCell(style1, rowData, 10, status); 
                String offset = "";
                if(!StringUtil.isEmpty(miguIncomeManager.getOffset())){
	                if(miguIncomeManager.getOffset().equals("1")){
	                	offset = "是";
	                }else{
	                	offset = "否";
	                }
                }
                createCell(style1, rowData, 11, offset);
                
                String isNeedbill = "";
                if(!StringUtil.isNullOrBlank(miguIncomeManager.getIsNeedBill())){
	                if(miguIncomeManager.getIsNeedBill().equals("1")){
	                	isNeedbill = "是";
	                }else if(miguIncomeManager.getIsNeedBill().equals("0")){
	                	isNeedbill = "否";
	                }
                }
                createCell(style1, rowData, 12, isNeedbill);  
                
                createCell(style1, rowData, 13, StringUtil.null2Blank(miguIncomeManager.getDeptName()==null?"":miguIncomeManager.getDeptName()));  
                createCell(style1, rowData, 14, StringUtil.null2Blank(miguIncomeManager.getUserName()==null?"":miguIncomeManager.getUserName()));  
             
                if(miguIncomeManager.getRealExplain()==null){
                	 createCell(style1, rowData, 15, "");
                }else{
                	createCell(style1, rowData, 15, StringUtil.null2Blank(new String(miguIncomeManager.getRealExplain().getBytes("UTF-8"),"UTF-8")));
                }
                if(StringUtil.isEmpty(miguIncomeManager.getMergeStatus())){
               	 createCell(style1, rowData, 16, "");
               }else if("0".equals(miguIncomeManager.getMergeStatus())){
               	createCell(style1, rowData, 16, StringUtil.null2Blank(new String("未合并".getBytes("UTF-8"),"UTF-8")));
               }else if("1".equals(miguIncomeManager.getMergeStatus())){
            	   createCell(style1, rowData, 16, StringUtil.null2Blank(new String("已合并".getBytes("UTF-8"),"UTF-8")));
               }
			}
            
            OutputStream os = null;
			try {
				os = new FileOutputStream(reportFile);
				hssfworkbook.write(os);
				os.close();
				map.put("reCode", 100);
				map.put("fileName", reportFile.getName());
				log.info("实际收入文件生成成功");
			} catch (Exception e) {
				map.put("reCode", -1);
				map.put("reStr", "实际收入文件生成异常");
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
}
