package cn.migu.income.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
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
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.util.CellRangeAddress;
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

import cn.migu.income.util.PropValue;
import cn.migu.income.pojo.MiguIncomeDetailReport;
import cn.migu.income.pojo.MiguIncomeDetailReport2;
import cn.migu.income.pojo.MiguProjectProfitReport;
import cn.migu.income.pojo.MiguSectorIncomeReport;
import cn.migu.income.service.IMiguTwoNonReportService;
import cn.migu.income.util.StringUtil;
import cn.migu.income.util.TxtFileOperation;

@Controller
@RequestMapping(value="/twoNonReport")
public class MiguTwoNonReportController {
	
	final static Logger log = LoggerFactory.getLogger(MiguTwoNonReportController.class);

    
    @Autowired
    private IMiguTwoNonReportService twoNonReportService;
	
	/**
	 * 两非项目利润报表页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/projectProfitReport")
	public ModelAndView projectProfitReport(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("projectProfitReport");
		return mav;
	}
	
	/**
	 * 两非分部门收益报表页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sectorIncomeReport")
	public ModelAndView sectorIncomeReport(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("sectorIncomeReport");
		return mav;
	}
	
	/**
	 * 两非收入明细报表页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/incomeDetailReport")
	public ModelAndView incomeDetailReport(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("incomeDetailReport");
		return mav;
	}
    /**
     * 查询两非项目利润报表(包含条件查询)
     * @param request
     * @param session
     * @param q_projectId
     * @param q_projectName
     * @param q_dept
     * @param q_person
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/queryProjectProfit", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> queryProjectProfit(HttpServletRequest request,HttpSession session, 
        String q_projectId,String q_projectName,String q_dept,String q_person,String page,String rows)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        int count=0;
        List<MiguProjectProfitReport> list = new ArrayList<MiguProjectProfitReport>();
        
        //格式化输入参数
        if (StringUtils.isEmpty(page))
        {
            page = "1";
        }
        if (StringUtils.isEmpty(rows))
        {
            rows = "10";
        }   
        if (StringUtils.isEmpty(q_projectId))
        {
        	q_projectId = null;
        }
        if(StringUtils.isEmpty(q_projectName))
        {
        	q_projectName = null;
        }
        if(StringUtils.isEmpty(q_dept))
        {
        	q_dept = null;
        }
        if(StringUtils.isEmpty(q_person))
        {
        	q_person = null;
        }
        if(StringUtil.isNotEmpty(q_projectId)&&(q_projectId.contains("_")||q_projectId.contains("%")))
        {
        	q_projectId=StringUtil.getSpecialParam(q_projectId);
        }
        if(StringUtil.isNotEmpty(q_projectName)&&(q_projectName.contains("_")||q_projectName.contains("%")))
        {
        	q_projectName=StringUtil.getSpecialParam(q_projectName);
        }
        try
        {
           //查询总数
           count = twoNonReportService.queryProjectProfitTotal(q_projectId, q_projectName,q_dept,q_person);
           //查询列表详情
           list = twoNonReportService.queryAllProjectProfit(q_projectId, q_projectName,q_dept,q_person,Integer.parseInt(page), Integer.parseInt(rows));
           log.info("两非项目利润报表查询：总数=" + count + ";项目编号 =" + q_projectId + ";项目名称 =" + q_projectName + ";责任部门 =" + q_dept + ";责任人 =" + q_person);
        }
        catch (Exception e)
        {
        	log.error("两非项目利润报表查询异常:", e);
        	map.put("Exception", e);
            return map;
            
        }
        map.put("total", count);
        map.put("rows", list);
        return map;
    }
    
    /**
     * 导出两非项目利润报表
     * @param request
     * @param session
     * @param projectId
     * @param projectName
     * @param dept
     * @param person
     * @return
     */
    @RequestMapping(value = "/exportExc", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> exportExc(HttpServletRequest request, HttpSession session,
        String projectId, String projectName,String dept,String person)
    {
    	Map<String, Object> map = new HashMap<String, Object>();

        //格式化输入参数
        if (StringUtils.isEmpty(projectId))
        {
        	projectId = null;
        }            
        if (StringUtils.isEmpty(projectName))
        {
        	projectName = null;
        }
        if(StringUtils.isEmpty(dept))
        {
        	dept = null;
        }
        if(StringUtils.isEmpty(person))
        {
        	person = null;
        }
        if(StringUtil.isNotEmpty(projectId)&&(projectId.contains("_")||projectId.contains("%")))
        {
        	projectId=StringUtil.getSpecialParam(projectId);
        }
        if(StringUtil.isNotEmpty(projectName)&&(projectName.contains("_")||projectName.contains("%")))
        {
        	projectName=StringUtil.getSpecialParam(projectName);
        }
        
        List<MiguProjectProfitReport> dataList = new ArrayList<MiguProjectProfitReport>();
		try {
			dataList = twoNonReportService.queryAllProjectProfit2(projectId, projectName, dept, person);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        
        if (dataList.size() == 0)
        {
            log.info("该条件下无两非项目利润报表数据！");
            map.put("reCode", -1);
            map.put("reStr", "该条件下无两非项目利润报表数据！");
        }else{
        	//获取模板文件
        	WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
            ServletContext servletContext = webApplicationContext.getServletContext();          
        	String tempFile= servletContext.getRealPath("/reportTemplate")+"/projectProfitReport.xls";//模板文件的地址
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
            //将数据写入到模板文件
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
            	MiguProjectProfitReport tMiguDataStatistics = (MiguProjectProfitReport) dataList.get(j);
            	//创建一行  
            	HSSFRow rowData = hssfsheet.createRow(i++);  
                createCell(style1, rowData, 0, doNull(tMiguDataStatistics.getProjectId()));  
                createCell(style1, rowData, 1, doNull(tMiguDataStatistics.getProjectName()));  
                createCell(style1, rowData, 2, doNull(tMiguDataStatistics.getDeptName()));  
                createCell(style1, rowData, 3, doNull(tMiguDataStatistics.getPersonName()));  
                createCell(style1, rowData, 4, tMiguDataStatistics.getCostAmount());  
                createCell(style1, rowData, 5, tMiguDataStatistics.getActualIncome());  
                createCell(style1, rowData, 6, tMiguDataStatistics.getProfitAmount());  
                createCell(style1, rowData, 7, tMiguDataStatistics.getProfitRatio());
                createCell(style1, rowData, 8, tMiguDataStatistics.getProfitRatio2());
			}
            
            OutputStream os = null;
			try {
				os = new FileOutputStream(reportFile);
				hssfworkbook.write(os);
				os.close();
				map.put("reCode", 100);
				map.put("fileName", reportFile.getName());
				log.info("两非项目利润报表生成成功！");
			} catch (Exception e) {
				map.put("reCode", -1);
				map.put("reStr", "两非项目利润报表生成异常！");
				log.error("两非项目利润报表生成异常:", e);
				e.printStackTrace();
			}
        	
        }
    	return map;
    }
    
    
    /**
     * 查询两非项目分部门收益报表(包含条件查询)
     * @param request
     * @param session
     * @param q_dept
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/querySectorIncome", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> querySectorIncome(HttpServletRequest request,HttpSession session, String q_dept,String page,String rows)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        int count=0;
        List<MiguSectorIncomeReport> list = new ArrayList<MiguSectorIncomeReport>();
        
        //格式化输入参数
        if (StringUtils.isEmpty(page))
        {
            page = "1";
        }
        if (StringUtils.isEmpty(rows))
        {
            rows = "10";
        }   
        if(StringUtils.isEmpty(q_dept))
        {
        	q_dept = null;
        }
        try
        {
           //查询总数
           count = twoNonReportService.querySectorIncomeReportTotal(q_dept);
           //查询列表详情
           list = twoNonReportService.querySectorIncomeReport(q_dept,Integer.parseInt(page), Integer.parseInt(rows));
           log.info("两非项目分部门收益报表查询：总数=" + count +";责任部门 =" + q_dept);
        }
        catch (Exception e)
        {
        	log.error("两非项目分部门收益报表查询异常:", e);
        	map.put("Exception", e);
            return map;
            
        }
        map.put("total", count);
        map.put("rows", list);
        return map;
    }
 
    /**
     * 导出两非项目分部门收益报表
     * @param request
     * @param session
     * @param dept
     * @return
     */
    @RequestMapping(value = "/exportExc2", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> exportExc2(HttpServletRequest request, HttpSession session,String dept)
    {
    	Map<String, Object> map = new HashMap<String, Object>();

        //格式化输入参数
        if(StringUtils.isEmpty(dept))
        {
        	dept = null;
        }
        List<MiguSectorIncomeReport> dataList = new ArrayList<MiguSectorIncomeReport>();
		try {
			dataList = twoNonReportService.querySectorIncomeReport2(dept);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        
        if (dataList.size() == 0)
        {
            log.info("该条件下无两非项目分部门收益报表数据！");
            map.put("reCode", -1);
            map.put("reStr", "该条件下无两非项目分部门收益报表数据！");
        }else{
        	//获取模板文件
        	WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
            ServletContext servletContext = webApplicationContext.getServletContext();          
        	String tempFile= servletContext.getRealPath("/reportTemplate")+"/两非分部门收益报表.xls";//模板文件的地址
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
            //将数据写入到模板文件
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
            	MiguSectorIncomeReport tMiguDataStatistics = (MiguSectorIncomeReport) dataList.get(j);
            	//创建一行  
            	HSSFRow rowData = hssfsheet.createRow(i++);   
                createCell(style1, rowData, 0, doNull(tMiguDataStatistics.getMonthId()));  
                createCell(style1, rowData, 1, doNull(tMiguDataStatistics.getDeptName()));  
                createCell(style1, rowData, 2, doNull(tMiguDataStatistics.getClassName()));  
                createCell(style1, rowData, 3, doNull(tMiguDataStatistics.getSectionName()));  
                createCell(style1, rowData, 4, doNull(tMiguDataStatistics.getEstimateTax()));
                createCell(style1, rowData, 5, doNull(tMiguDataStatistics.getRealTax()));
                createCell(style1, rowData, 6, doNull(tMiguDataStatistics.getActualIncome()));
                createCell(style1, rowData, 7, "");
                createCell(style1, rowData, 8, "");
                createCell(style1, rowData, 9, "");
                createCell(style1, rowData, 10, "");
			}
            
            OutputStream os = null;
			try {
				os = new FileOutputStream(reportFile);
				hssfworkbook.write(os);
				os.close();
				map.put("reCode", 100);
				map.put("fileName", reportFile.getName());
				log.info("两非项目分部门收益报表生成成功！");
			} catch (Exception e) {
				map.put("reCode", -1);
				map.put("reStr", "两非项目分部门收益报表生成异常！");
				log.error("两非项目分部门收益报表生成异常:", e);
				e.printStackTrace();
			}
        	
        }
    	return map;
    }
    
    /**
     * 查询两非收入明细报表(包含条件查询)
     * @param request
     * @param session
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/queryIncomeDetail", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> queryIncomeDetail(HttpServletRequest request,HttpSession session, String q_month_begin,String q_month_end,String q_projectId,String q_projectName,String page,String rows)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        int count=0;
        List<MiguIncomeDetailReport> list = new ArrayList<MiguIncomeDetailReport>();
        
        //格式化输入参数
        if (StringUtils.isEmpty(page))
        {
            page = "1";
        }
        if (StringUtils.isEmpty(rows))
        {
            rows = "10";
        }   
        if (StringUtils.isEmpty(q_month_begin))
        {
        	q_month_begin = null;
        }else{
        	q_month_begin = q_month_begin.split("-")[0]+(q_month_begin.split("-")[1].length()==1?0+q_month_begin.split("-")[1]:q_month_begin.split("-")[1]);
        }
        if (StringUtils.isEmpty(q_month_end))
        {
        	q_month_end = null;
        }else{
        	q_month_end = q_month_end.split("-")[0]+(q_month_end.split("-")[1].length()==1?0+q_month_end.split("-")[1]:q_month_end.split("-")[1]);
        }
        if (StringUtils.isEmpty(q_projectId))
        {
        	q_projectId = null;
        }
        if(StringUtils.isEmpty(q_projectName))
        {
        	q_projectName = null;
        }
        try
        {
           //查询总数
           count = twoNonReportService.queryIncomeDetailReportTotal(q_month_begin,q_month_end,q_projectId,q_projectName);
           //查询列表详情
           list = twoNonReportService.queryIncomeDetailReport(q_month_begin,q_month_end,q_projectId,q_projectName,Integer.parseInt(page), Integer.parseInt(rows));
           log.info("两非收入明细报表查询：总数=" + count +";业务账期范围=" + q_month_begin+"-"+q_month_end+";项目号="+q_projectId+";项目名称="+q_projectName);
        }
        catch (Exception e)
        {
        	log.error("两非收入明细报表查询异常:", e);
        	map.put("Exception", e);
            return map;
            
        }
        map.put("total", count);
        map.put("rows", list);
        return map;
    }
    
    
    /**
     * 导出两非项目收入明细报表
     * @param request
     * @param session
     * @return
     */
	@RequestMapping(value = "/exportIncomeDetail", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> exportIncomeDetail(HttpServletRequest request, HttpSession session,String q_month_begin,String q_month_end,String q_projectId,String q_projectName)
    {
    	Map<String, Object> map = new HashMap<String, Object>();

        //格式化输入参数
    	if (StringUtils.isEmpty(q_month_begin))
        {
        	q_month_begin = null;
        }else{
        	q_month_begin = q_month_begin.split("-")[0]+(q_month_begin.split("-")[1].length()==1?0+q_month_begin.split("-")[1]:q_month_begin.split("-")[1]);
        }
        if (StringUtils.isEmpty(q_month_end))
        {
        	q_month_end = null;
        }else{
        	q_month_end = q_month_end.split("-")[0]+(q_month_end.split("-")[1].length()==1?0+q_month_end.split("-")[1]:q_month_end.split("-")[1]);
        }
        if (StringUtils.isEmpty(q_projectId))
        {
        	q_projectId = null;
        }
        if(StringUtils.isEmpty(q_projectName))
        {
        	q_projectName = null;
        }
        List<MiguIncomeDetailReport> dataList = new ArrayList<MiguIncomeDetailReport>();
        List<MiguIncomeDetailReport2> dataList2 = new ArrayList<MiguIncomeDetailReport2>();
		try {
			dataList = twoNonReportService.queryIncomeDetailReport2(q_month_begin,q_month_end,q_projectId,q_projectName);//查询收入明细报表明细（不包括合并前）
			dataList2 = twoNonReportService.queryIncomeDetailReport3(q_month_begin,q_month_end,q_projectId,q_projectName);//查询合并前明细
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        
        if (dataList.size() == 0)
        {
            log.info("【两非收入明细报表】该条件下无两非收入明细报表数据！");
            map.put("reCode", -1);
            map.put("reStr", "该条件下无两非项目收入明细报表数据！");
        }else{
        	//获取模板文件
        	WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
            ServletContext servletContext = webApplicationContext.getServletContext();          
        	String tempFile= servletContext.getRealPath("/reportTemplate")+"/两非收入明细报表.xls";//模板文件的地址
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
            //将数据写入到模板文件
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
            style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直    
            style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平 
            
            for (int j = 0; j < dataList.size(); j++) {
            	MiguIncomeDetailReport tMiguDataStatistics = (MiguIncomeDetailReport) dataList.get(j);
            	//创建一行  
            	HSSFRow rowData = hssfsheet.createRow(i++);   
                createCell(style1, rowData, 0, doNull(tMiguDataStatistics.getCycle()));  
                createCell(style1, rowData, 1, doNull(tMiguDataStatistics.getProjectId()));  
                createCell(style1, rowData, 2, doNull(tMiguDataStatistics.getProjectName()));  
                createCell(style1, rowData, 3, doNull(tMiguDataStatistics.getEstimateIncome()));  
                createCell(style1, rowData, 4, doNull(tMiguDataStatistics.getEstimateIncomeTax()));
                createCell(style1, rowData, 5, doNull(tMiguDataStatistics.getEstimateExclusiveTax()));
                createCell(style1, rowData, 6, doNull(tMiguDataStatistics.getRealIncome()));
                createCell(style1, rowData, 7, doNull(tMiguDataStatistics.getRealIncomeTax()));
                createCell(style1, rowData, 8, doNull(tMiguDataStatistics.getRealExclusiveTax()));
                createCell(style1, rowData, 9, doNull(tMiguDataStatistics.getOffset()));
                createCell(style1, rowData, 10, doNull(tMiguDataStatistics.getIsneedBill()));
                createCell(style1, rowData, 11, doNull(tMiguDataStatistics.getIsMerge()));
                createCell(style1, rowData, 12, doNull(tMiguDataStatistics.getBillingKey()));
                createCell(style1, rowData, 13, doNull(tMiguDataStatistics.getBillingType()));
                createCell(style1, rowData, 14, doNull(tMiguDataStatistics.getTotal()));
                createCell(style1, rowData, 15, doNull(tMiguDataStatistics.getInvoiceStatus()));
                createCell(style1, rowData, 16, doNull(tMiguDataStatistics.getIncome()));
                createCell(style1, rowData, 17, doNull(tMiguDataStatistics.getIncomeDate()));
                createCell(style1, rowData, 18, doNull(tMiguDataStatistics.getIncomeOptionUrl()));
                createCell(style1, rowData, 19, doNull(tMiguDataStatistics.getIncomeStatus()));
			}
            
            int index = 0;
            int lastIndex = 0;
            Long indexValue =null;
            for(int a=0;a<dataList.size();a++){
//                indexValue = sheet.getRow(index).getCell((short)1).getStringCellValue();
            	indexValue = dataList.get(index).getIncomeManagerId();
            	if(!indexValue.equals(dataList.get(a).getIncomeManagerId())){
            			lastIndex=a-1;
//            			hssfsheet.addMergedRegion(new Region(index+1,(short)0,lastIndex+1,(short)0));
//            			hssfsheet.addMergedRegion(new Region(index+1,(short)1,lastIndex+1,(short)1));
//            			hssfsheet.addMergedRegion(new Region(index+1,(short)2,lastIndex+1,(short)2));
//            			hssfsheet.addMergedRegion(new Region(index+1,(short)3,lastIndex+1,(short)3));
            			hssfsheet.addMergedRegion(new CellRangeAddress(index+1,lastIndex+1,0,0));  
            			hssfsheet.addMergedRegion(new CellRangeAddress(index+1,lastIndex+1,1,1));  
            			hssfsheet.addMergedRegion(new CellRangeAddress(index+1,lastIndex+1,2,2));  
            			hssfsheet.addMergedRegion(new CellRangeAddress(index+1,lastIndex+1,3,3));  
            			hssfsheet.addMergedRegion(new CellRangeAddress(index+1,lastIndex+1,4,4));  
            			hssfsheet.addMergedRegion(new CellRangeAddress(index+1,lastIndex+1,5,5));  
            			hssfsheet.addMergedRegion(new CellRangeAddress(index+1,lastIndex+1,6,6));  
            			hssfsheet.addMergedRegion(new CellRangeAddress(index+1,lastIndex+1,7,7));  
            			hssfsheet.addMergedRegion(new CellRangeAddress(index+1,lastIndex+1,8,8));  
            			hssfsheet.addMergedRegion(new CellRangeAddress(index+1,lastIndex+1,9,9));  
            			hssfsheet.addMergedRegion(new CellRangeAddress(index+1,lastIndex+1,10,10)); 
            			hssfsheet.addMergedRegion(new CellRangeAddress(index+1,lastIndex+1,11,11));  

            			index = a;	
            	}
            }
            //处理最后一次未进行的循环合并
			hssfsheet.addMergedRegion(new CellRangeAddress(index+1,dataList.size(),0,0));  
			hssfsheet.addMergedRegion(new CellRangeAddress(index+1,dataList.size(),1,1));  
			hssfsheet.addMergedRegion(new CellRangeAddress(index+1,dataList.size(),2,2));  
			hssfsheet.addMergedRegion(new CellRangeAddress(index+1,dataList.size(),3,3));  
			hssfsheet.addMergedRegion(new CellRangeAddress(index+1,dataList.size(),4,4));  
			hssfsheet.addMergedRegion(new CellRangeAddress(index+1,dataList.size(),5,5));  
			hssfsheet.addMergedRegion(new CellRangeAddress(index+1,dataList.size(),6,6));  
			hssfsheet.addMergedRegion(new CellRangeAddress(index+1,dataList.size(),7,7));  
			hssfsheet.addMergedRegion(new CellRangeAddress(index+1,dataList.size(),8,8));  
			hssfsheet.addMergedRegion(new CellRangeAddress(index+1,dataList.size(),9,9));  
			hssfsheet.addMergedRegion(new CellRangeAddress(index+1,dataList.size(),10,10));  
			hssfsheet.addMergedRegion(new CellRangeAddress(index+1,dataList.size(),11,11));  
			
			if(dataList2.size() == 0){
				log.info("【两非收入明细报表】当前报表无合并前报表数据！");
			}else{
	            HSSFSheet    hssfsheet2    = hssfworkbook.getSheetAt(1);
	            int a = hssfsheet2.getPhysicalNumberOfRows();
	            for (int k = 0; k < dataList2.size(); k++) {
	            	MiguIncomeDetailReport2 tMiguDataStatistics2 = (MiguIncomeDetailReport2) dataList2.get(k);
	            	//创建一行  
	            	HSSFRow rowData = hssfsheet2.createRow(a++);   
	                createCell(style1, rowData, 0, doNull(tMiguDataStatistics2.getCycle2()));  
	                createCell(style1, rowData, 1, doNull(tMiguDataStatistics2.getProjectId2()));  
	                createCell(style1, rowData, 2, doNull(tMiguDataStatistics2.getCycle()));  
	                createCell(style1, rowData, 3, doNull(tMiguDataStatistics2.getProjectId()));  
	                createCell(style1, rowData, 4, doNull(tMiguDataStatistics2.getProjectName()));  
	                createCell(style1, rowData, 5, doNull(tMiguDataStatistics2.getEstimateIncome()));  
	                createCell(style1, rowData, 6, doNull(tMiguDataStatistics2.getEstimateIncomeTax()));
	                createCell(style1, rowData, 7, doNull(tMiguDataStatistics2.getEstimateExclusiveTax()));
	                createCell(style1, rowData, 8, doNull(tMiguDataStatistics2.getRealIncome()));
	                createCell(style1, rowData, 9, doNull(tMiguDataStatistics2.getRealIncomeTax()));
	                createCell(style1, rowData, 10, doNull(tMiguDataStatistics2.getRealExclusiveTax()));
	                createCell(style1, rowData, 11, doNull(tMiguDataStatistics2.getOffset()));
	                createCell(style1, rowData, 12, doNull(tMiguDataStatistics2.getIsneedBill()));
				}
			}
			
            OutputStream os = null;
			try {
				os = new FileOutputStream(reportFile);
				hssfworkbook.write(os);
				os.close();
				map.put("reCode", 100);
				map.put("fileName", reportFile.getName());
				log.info("【两非收入明细报表】两非收入明细报表生成成功！");
			} catch (Exception e) {
				map.put("reCode", -1);
				map.put("reStr", "两非收入明细报表生成异常！");
				log.error("【两非收入明细报表】两非收入明细收益报表生成异常:", e);
				e.printStackTrace();
			}
        	
        }
    	return map;
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
    
    //poi表格行创建方法
    public static void createCell(HSSFCellStyle style, HSSFRow row, int column,  
            String value) {  
    	@SuppressWarnings("deprecation")
		HSSFCell cell = row.createCell((short)column);
        cell.setCellStyle(style);
        cell.setCellValue(value);
    }
    
    //判空处理
    public String doNull(Object object){
    	if(object == null){
    		return "";
    	}else{
    		return object.toString();
    	}
    }
}
