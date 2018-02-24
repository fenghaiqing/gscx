package cn.migu.income.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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

import cn.migu.income.util.TxtFileOperation;
import cn.migu.income.util.PropValue;
import cn.migu.income.dao.MiguIncomeDetailMapper;
import cn.migu.income.dao.MiguUsersMapper;
import cn.migu.income.pojo.MiguIncomeActual;
import cn.migu.income.pojo.MiguIncomeBill;
import cn.migu.income.pojo.MiguIncomeBillIncome;
import cn.migu.income.pojo.MiguIncomeDiff;
import cn.migu.income.pojo.MiguIncomeManager;
import cn.migu.income.pojo.MiguIncomeManagerHis;
import cn.migu.income.pojo.MiguPrintDetail;
import cn.migu.income.pojo.MiguProjectDetail;
import cn.migu.income.pojo.MiguUsers;
import cn.migu.income.pojo.TMiguDepartments;
import cn.migu.income.service.IMiguActualIncomeService;
import cn.migu.income.service.MiguIncomeManagerService;
import cn.migu.income.util.MiguConstants;
import cn.migu.income.util.StringUtil;

@Controller
@RequestMapping(value="/actualIncome")
public class MiguActualIncomeManagerController {
	
	final static Logger log = LoggerFactory.getLogger(MiguActualIncomeManagerController.class);

    
    @Autowired
    private IMiguActualIncomeService incomeService;

	@Autowired
	private MiguIncomeDetailMapper incomeDetailMapper;
	
	@Autowired
	private MiguUsersMapper UsersMapper;

    @Autowired
    private MiguIncomeManagerService incomeManagerService;
    /**
     * 实际收款管理页面	
     * @param request
     * @return
     */
	@RequestMapping(value = "/actualIncomeManage")
	public ModelAndView actualIncomeManage(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("incomeManage");
		return mav;
	}

    /**
     * 查询当前登录人员所有的实际收款(包含条件查询)
     * @param request
     * @param session
     * @param q_month
     * @param q_projectId
     * @param q_projectName
     * @param q_incomeState
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/queryAllActualIncome", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> queryAllActualIncome(HttpServletRequest request,HttpSession session, 
        String q_month,String q_projectId,String q_projectName,String q_incomeState,
        String q_record_month , String q_incomeClassId ,String q_incomeSectionId,
        String q_dept, String q_userName ,String q_bill_num , String q_bill_total,
        String q_income, String income_date, String page,String rows)
    {
    	MiguUsers user = (MiguUsers)(session.getAttribute(MiguConstants.USER_KEY));
        Map<String, Object> map = new HashMap<String, Object>();
        int count=0;
        List<MiguIncomeActual> list = new ArrayList<MiguIncomeActual>();
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
            if(StringUtils.isEmpty(q_incomeState))
            {
            	q_incomeState = null;
            }
            
            
            if(StringUtils.isEmpty(q_record_month))
            {
            	q_record_month = null;
            }else{
            	q_record_month = q_record_month.split("-")[0]+(q_record_month.split("-")[1].length()==1?0+q_record_month.split("-")[1]:q_record_month.split("-")[1]);
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
            if(StringUtils.isEmpty(q_bill_num))
            {
            	q_bill_num = null;
            }
            if(StringUtils.isEmpty(q_bill_total))
            {
            	q_bill_total = null;
            }
            if(StringUtils.isEmpty(q_income))
            {
            	q_income = null;
            }
            if(StringUtils.isEmpty(income_date))
            {
            	income_date = null;
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
           //查询总数
           count = incomeService.queryTotal(q_month, q_projectId, q_projectName,q_incomeState,
        		    q_record_month , q_incomeClassId , q_incomeSectionId, q_dept, q_userName ,
        			 q_bill_num ,q_bill_total, q_income,income_date, user);
           //查询列表详情
           list = incomeService.queryAllActualIncome(q_month, q_projectId, q_projectName,
        		   q_incomeState,q_record_month , q_incomeClassId , q_incomeSectionId, q_dept,
        			 q_userName ,q_bill_num ,q_bill_total, q_income, income_date,
        		   Integer.parseInt(page), Integer.parseInt(rows),user);
           log.info("实际收款列表查询：总数=" + count + ",月份 =" + q_month + ";项目编号 =" + q_projectId + ";项目名称 =" + q_projectName + ";实际收款状态 =" + q_incomeState);
        }
        catch (Exception e)
        {
        	log.error("实际收款查询异常:", e);
        	map.put("Exception", e);
            return map;
            
        }
        map.put("total", count);
        map.put("rows", list);
        return map;
    }
    
    /**
     * 查询所有通过审核且不需要开票的实际收入进行实际收款
     *
     * @param request
     * @param session     
     * @param projectId
     * @param projectName
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/queryAllNoBillIncome", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> queryAllNoBillIncome(String projectId, String projectName, HttpServletRequest request, HttpSession session, String page, String rows) {
        MiguUsers user = (MiguUsers) (session.getAttribute(MiguConstants.USER_KEY));
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
            count = incomeManagerService.queryNoBillTotal(projectId, projectName, user);
            if (count != 0) {
                list = incomeManagerService.queryAllNoBillIncome(projectId, projectName,
                        Integer.parseInt(page), Integer.parseInt(rows), user);
            }
            log.info("新增实际收款页面查询已审核通过且不需要开票的实际收入项目：总数=" + count + ",查询条件 ;项目编号 =" + projectId + ";项目名称 =" + projectName );
        } catch (Exception e) {
            log.info("新增实际收款页面查询已审核通过且不需要开票的实际收入项目:", e);
            map.put("Exception", e);
            return map;

        }
        map.put("total", count);
        map.put("rows", list);
        return map;
    }

    
    /**
     * 审核历史查询
     * @param request
     * @param session
     * @param incomeManagerId
     * @return
     */
    @RequestMapping(value = "/showHis", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> showHis(HttpServletRequest request,HttpSession session, 
        String billingKey)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        List<MiguIncomeManagerHis> list =new ArrayList<MiguIncomeManagerHis>();
        try
        {
            list = incomeService.queryList(billingKey, "A");
//            for(int i=0;i<list.size();i++){
//            	if(!"".equals(list.get(i).getCreateDate())&&list.get(i).getCreateDate()!=null){
//            		list.get(i).setCreateDate(list.get(i).getCreateDate().substring(0,8));
//            	}
//            }
            log.info("实际收款审核历史记录查询：实际收款对应开票ID=" + billingKey);
            log.info("实际收款审核历史记录查询查询结果：" + list);
        }
        catch (Exception e)
        { 
            log.error("Exception:", "实际收款查询审核历史异常:"+e);
        }
        map.put("rows", list);
        return map;
    }
    
    /**
     * 打印实际收款
     * @param billingKey
     * @return
     * @throws Exception 
     */
	@RequestMapping(value = "/printIncomePage", method = RequestMethod.GET)
	public ModelAndView printIncomePage(String billingKey) throws Exception {
		ModelAndView md = new ModelAndView();
		//查询审核历史详情
		List<MiguIncomeManagerHis> list = incomeService.queryList(billingKey, "A");
		md.addObject("hisList", list);
		
		//查询实际收款详情
		MiguIncomeActual income = null;
		try {
			income = incomeService.querySglActualIncome(billingKey);
		} catch (Exception e) {
            log.error("Exception:", "实际收款打印异常:"+e);
		}
		String url = income.getIncomeOptionsUrl();
		if (!StringUtil.isEmpty(url)) {
			String fileName = url.substring(url.lastIndexOf("/") + 1);
			income.setFileName(fileName);
		}
		md.addObject("income", income);
		
		//查询发票明细
        List<MiguIncomeBill> billingList =incomeService.showBilldetail(billingKey);
        md.addObject("billingList", billingList);
		
		//查询收款所属项目详情
		MiguProjectDetail miguProject = incomeDetailMapper.selectProjectDetail(income.getProjectId());
		md.addObject("miguProject", miguProject);
		
		MiguPrintDetail miguPrint = incomeDetailMapper.selectPrintDetail(income.getProjectId());
		md.addObject("miguPrint", miguPrint);
		md.setViewName("incomePrint");
		return md;
	}
	
    /**
     * 查询单个项目详情
     * @param request
     * @param incomeManagerId
     * @param response
     * @param session
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/editActualIncome", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> editActualIncome(HttpServletRequest request,String billingKey,HttpServletResponse response, HttpSession session) throws Exception{
    	Map<String, Object> map = new HashMap<String, Object>();
    	MiguIncomeActual income = incomeService.querySglActualIncome(billingKey);
    	map.put("income", income);
    	if(income.getIncomeStatus()!= null){
	    	if(income.getIncomeStatus().equals("1")){
	    		map.put("status", 1);//审核中
	    		map.put("str", "该项收款正在审核中，无法更新！");
	    	}else if(income.getIncomeStatus().equals("2")){
	    		map.put("status", 2);//审核通过
	    		map.put("str", "该项收款已通过审核，无法更新！");
	    	}
    	}
    	return map;
    }
    
	/**
	 * 查询所有部门
	 * @param request
	 * @param session
	 * @return
	 */
    @RequestMapping(value = "/queryAllDep", method = RequestMethod.POST)
    public @ResponseBody List<TMiguDepartments> queryAllDep(HttpServletRequest request,HttpSession session)
    {
        try
        {
            return incomeService.queryAllDep();
        }
        catch (Exception e)
        {
            log.error("部门查询异常:", e);
        }
        return null;
    }
    
	/**
	 * 查询部门对应人员
	 * @param request
	 * @param session
	 * @return
	 */
    @RequestMapping(value = "/queryDepPerson", method = RequestMethod.POST)
    public @ResponseBody List<MiguUsers> queryDepPerson(HttpServletRequest request,HttpSession session,String deptId)
    {
        try
        {
        	return incomeService.querydepPerson(deptId);
        }
        catch (Exception e)
        {
            log.error("部门人员查询异常:", e);
        }
        return null;
    }
    
    @RequestMapping(value = "/queryDepPersonByRole", method = RequestMethod.POST)
    public @ResponseBody List<MiguUsers> queryDepPersonByRole(HttpServletRequest request,HttpSession session,String deptId)
    {
        try
        { 
        	MiguUsers user = (MiguUsers) session.getAttribute(MiguConstants.USER_KEY);
        	 if(!"1".equals(user.getDeptId())&&"1".equals(deptId)){
				 return UsersMapper.queryUserByRole(deptId);
			 }
        	return incomeService.querydepPerson(deptId);
        }
        catch (Exception e)
        {
            log.error("部门人员查询异常:", e);
        }
        return null;
    }
   
    /**
     * 实际收款审核前校验开票金额
     * @param request
     * @param resonse
     * @return
     * @throws Exception 
     */
	@RequestMapping(value="/checkIncome",method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> checkIncome(HttpServletRequest request ,HttpServletResponse resonse,String billingKey) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		//查询是否需要开票
		String isNeedBill = incomeService.querySglActualIncome(billingKey).getIsNeedBill();
	   	if(isNeedBill!= null){
			if(isNeedBill.equals("1")){
	    		map.put("isNeedBill", 1);//需要开票
	    		map.put("billMoney", incomeService.querySglBillTotal(billingKey));//查询开票总金额
			}else{
	    		map.put("isNeedBill", 0);//不需要开票
	    		}
	   	}else{
    		map.put("isNeedBill", 1);//需要开票
    		map.put("billMoney", incomeService.querySglBillTotal(billingKey));//查询开票金额
	   	}
		return map;
	}
    
    /**
     * 更新实际收款提交审核（包括开票、不开票）
     * @param request
     * @param resonse
     * @return
     */
	@RequestMapping(value="/updateIncome",method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateIncome(HttpServletRequest request ,HttpServletResponse resonse){
		//更新实际收款记录
		return incomeService.updateIncome(request,resonse);
	}
	
    /**
     * 新增实际收款提交审核（不开票）
     * @param request
     * @param resonse
     * @return
     */
	@RequestMapping(value="/addIncome",method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> addIncome(HttpServletRequest request ,HttpServletResponse resonse){
		//新增实际收款记录
		return incomeService.addIncome(request,resonse);
	}
	/**
	 * 下载模版文件
	 * @param request
	 * @param session
	 * @param response
	 * @return
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/downLoad", method = RequestMethod.POST)
	public @ResponseBody String downLoad(HttpServletRequest request, HttpSession session, HttpServletResponse response)
			throws FileNotFoundException, UnsupportedEncodingException {
		String rootpath = URLDecoder.decode(request.getParameter("fileURL"), "UTF-8");
		File file = new File(rootpath );
		if (file.exists()) {
			TxtFileOperation.downloadLocal(response, file);
		} else {
			log.error("项目附件下载功能异常，文件不存在，请检查！");
			try {
				return new String("项目附件下载功能异常，文件不存在，请检查！".getBytes(), "ISO-8859-1");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 预估与实际收入差额展示页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/incomeDiffShow")
	public ModelAndView incomeDiffShow(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("incomeDiffShow");
		return mav;
	}
	
    /**
     * 查询当前登录人员所有差额报表(包含条件查询)
     * @param request
     * @param session
     * @param q_projectId
     * @param q_projectName
     * @param q_dept
     * @param q_person
     * @param q_month_start
     * @param q_month_end
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/queryAllIncomeDiff", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> queryAllIncomeDiff(HttpServletRequest request,HttpSession session, 
        String q_projectId,String q_projectName,String q_dept,String q_person,String q_month_start,String q_month_end,String page,String rows)
    {
        Map<String, Object> map = new HashMap<String, Object>();

        int count=0;
        List<MiguIncomeDiff> list = new ArrayList<MiguIncomeDiff>();
        
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
        if (StringUtils.isEmpty(q_month_start))
        {
        	q_month_start = null;
        }else{
        	q_month_start = q_month_start.split("-")[0]+(q_month_start.split("-")[1].length()==1?0+q_month_start.split("-")[1]:q_month_start.split("-")[1]);
        }
        if (StringUtils.isEmpty(q_month_end))
        {
        	q_month_end = null;
        }else{
        	q_month_end = q_month_end.split("-")[0]+(q_month_end.split("-")[1].length()==1?0+q_month_end.split("-")[1]:q_month_end.split("-")[1]);
        }
        if(StringUtil.isNotEmpty(q_projectId)&&(q_projectId.contains("_")||q_projectId.contains("%")))
        {
        	q_projectId=StringUtil.getSpecialParam(q_projectId);
        }
        if(StringUtil.isNotEmpty(q_projectName)&&(q_projectName.contains("_")||q_projectName.contains("%")))
        {
        	q_projectName=StringUtil.getSpecialParam(q_projectName);
        }
        if(StringUtil.isNotEmpty(q_month_start)&&(q_month_start.contains("_")||q_month_start.contains("%")))
        {
        	q_month_start=StringUtil.getSpecialParam(q_month_start);
        }
        if(StringUtil.isNotEmpty(q_month_end)&&(q_month_end.contains("_")||q_month_end.contains("%")))
        {
        	q_month_end=StringUtil.getSpecialParam(q_month_end);
        }
        
        try
        {
           //查询总数
           count = incomeService.queryTotalDiff(q_projectId, q_projectName,q_dept,q_person,q_month_start,q_month_end);
           //查询列表详情
           list = incomeService.queryAllIncomeDiff(q_projectId, q_projectName,q_dept,q_person,q_month_start,q_month_end,Integer.parseInt(page), Integer.parseInt(rows));
           log.info("预估与实际收入差额列表查询：总数=" + count + ",开始月份 =" + q_month_start+ ",结束月份 =" + q_month_end 
        		   + ";项目编号 =" + q_projectId + ";项目名称 =" + q_projectName + ";责任部门 =" + q_dept + ";责任人 =" + q_person);
        }
        catch (Exception e)
        {
        	log.error("预估与实际收入差额列表查询异常:", e);
        	map.put("Exception", e);
            return map;
            
        }
        map.put("total", count);
        map.put("rows", list);
        return map;
    }
    
    /**
     * 导出差额报表
     * @param request
     * @param session
     * @param projectId
     * @param projectName
     * @param dept
     * @param person
     * @param monthStart
     * @param monthEnd
     * @return
     */
    @RequestMapping(value = "/exportExc", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> exportExc(HttpServletRequest request, HttpSession session,
        String projectId, String projectName,String dept,String person,String monthStart,String monthEnd)
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
        if(StringUtils.isEmpty(monthStart))
        {
        	monthStart = null;
        }else{
        	monthStart = monthStart.split("-")[0]+(monthStart.split("-")[1].length()==1?0+monthStart.split("-")[1]:monthStart.split("-")[1]);
        }
        if (StringUtils.isEmpty(monthEnd))
        {
        	monthEnd = null;
        }else{
        	monthEnd = monthEnd.split("-")[0]+(monthEnd.split("-")[1].length()==1?0+monthEnd.split("-")[1]:monthEnd.split("-")[1]);
        }
        if(StringUtil.isNotEmpty(projectId)&&(projectId.contains("_")||projectId.contains("%")))
        {
        	projectId=StringUtil.getSpecialParam(projectId);
        }
        if(StringUtil.isNotEmpty(projectName)&&(projectName.contains("_")||projectName.contains("%")))
        {
        	projectName=StringUtil.getSpecialParam(projectName);
        }
        if(StringUtil.isNotEmpty(monthStart)&&(monthStart.contains("_")||monthStart.contains("%")))
        {
        	monthStart=StringUtil.getSpecialParam(monthStart);
        }
        if(StringUtil.isNotEmpty(monthEnd)&&(monthEnd.contains("_")||monthEnd.contains("%")))
        {
        	monthEnd=StringUtil.getSpecialParam(monthEnd);
        }
        
        List<MiguIncomeDiff> dataList = new ArrayList<MiguIncomeDiff>();
		try {
			dataList = incomeService.queryDiffNopage(projectId, projectName, dept, person, monthStart, monthEnd);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        
        if (dataList.size() == 0)
        {
            log.info("该条件下无预估与实际收入差额数据！");
            map.put("reCode", -1);
            map.put("reStr", "该条件下无预估与实际收入差额数据！");
        }else{
        	//获取模板文件
        	WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
            ServletContext servletContext = webApplicationContext.getServletContext();          
        	String tempFile= servletContext.getRealPath("/reportTemplate")+"/incomeDiffShow.xls";//模板文件的地址
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
            	MiguIncomeDiff tMiguDataStatistics = (MiguIncomeDiff) dataList.get(j);
            	//创建一行  
            	HSSFRow rowData = hssfsheet.createRow(i++);  
                createCell(style1, rowData, 0, Integer.toString(j+1));  
                createCell(style1, rowData, 1, doNull(tMiguDataStatistics.getProjectId()));  
                createCell(style1, rowData, 2, doNull(tMiguDataStatistics.getProjectName()));  
                createCell(style1, rowData, 3, doNull(tMiguDataStatistics.getDeptName()));  
                createCell(style1, rowData, 4, doNull(tMiguDataStatistics.getPersonName()));  
                createCell(style1, rowData, 5, doNull(tMiguDataStatistics.getCycle()));  
                createCell(style1, rowData, 6, doNull2(tMiguDataStatistics.getEstimateIncome()));  
                createCell(style1, rowData, 7, doNull2(tMiguDataStatistics.getRealIncome()));  
                createCell(style1, rowData, 8, doNull2(tMiguDataStatistics.getDeferenceInIncome()));
                createCell(style1, rowData, 9, doNull(tMiguDataStatistics.getOffSet()));
			}
            
            OutputStream os = null;
			try {
				os = new FileOutputStream(reportFile);
				hssfworkbook.write(os);
				os.close();
				map.put("reCode", 100);
				map.put("fileName", reportFile.getName());
				log.info("预估与实际收入差额报表生成成功！");
			} catch (Exception e) {
				map.put("reCode", -1);
				map.put("reStr", "预估与实际收入差额报表生成异常！");
				log.error("预估与实际收入差额报表生成异常:", e);
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
    
    /**
     * 下载已上传附件2
     * @param request
     * @param session
     * @param response
     * @param url
     * @throws FileNotFoundException
     */
    @RequestMapping(value = "/downloadAttach", method = RequestMethod.POST)
    public @ResponseBody String downloadAttach(HttpServletRequest request, HttpSession session, HttpServletResponse response,String url)
        throws FileNotFoundException
    {        
        String path = url; //文件路径
        File file = new File(path);
        if (file.exists())
        {
            TxtFileOperation.downloadLocal(response, file);
        }else{
        	log.error("项目附件下载功能异常，文件不存在，请检查！");
            try {
				return new String("项目附件下载功能异常，文件不存在，请检查！".getBytes(), "ISO-8859-1");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
        }
		return null;
    }
    
    
    /**
     * 实际收款导出
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
        String q_month, String q_projectId, String q_projectName, String q_incomeState, String billingKeys,
        String q_record_month , String q_incomeClassId ,String q_incomeSectionId,
        String q_dept, String q_userName ,String q_bill_num , String q_bill_total,
        String q_income, String income_date
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
        if(StringUtils.isEmpty(q_incomeState))
        {
        	q_incomeState = null;
        }
        if(StringUtils.isEmpty(q_record_month))
        {
        	q_record_month = null;
        }else{
        	q_record_month = q_record_month.split("-")[0]+(q_record_month.split("-")[1].length()==1?0+q_record_month.split("-")[1]:q_record_month.split("-")[1]);
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
        if(StringUtils.isEmpty(q_bill_num))
        {
        	q_bill_num = null;
        }
        if(StringUtils.isEmpty(q_bill_total))
        {
        	q_bill_total = null;
        }
        if(StringUtils.isEmpty(q_income))
        {
        	q_income = null;
        }
        if(StringUtils.isEmpty(income_date))
        {
        	income_date = null;
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
        if(StringUtils.isEmpty(billingKeys))
        {
        	billingKeys = null;
        }    
        
        List<MiguIncomeBillIncome> dataList = new ArrayList<MiguIncomeBillIncome>();
		try {
			dataList = incomeService.queryAllIncomeActual(q_month, q_projectId, q_projectName, q_incomeState, billingKeys,
					   q_record_month ,  q_incomeClassId , q_incomeSectionId,q_dept,  q_userName , q_bill_num ,  q_bill_total,q_income,  income_date,user);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        
        if (dataList.size() == 0)
        {
            log.info("该条件下无实际收款数据");
            map.put("reCode", -1);
            map.put("reStr", "该条件下无实际收款数据");
        }
        else
        {
        	
        	WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
            ServletContext servletContext = webApplicationContext.getServletContext();          
        	String tempFile= servletContext.getRealPath("/reportTemplate")+"/实际收款管理列表.xls";//模板文件的地址
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
            	MiguIncomeBillIncome miguIncomeActual = (MiguIncomeBillIncome) dataList.get(j);
            	//创建一行  
            	HSSFRow rowData = hssfsheet.createRow(i++);  
                createCell(style1, rowData, 0, StringUtil.null2Blank(miguIncomeActual.getCycle()));  
                createCell(style1, rowData, 1, StringUtil.null2Blank(miguIncomeActual.getBzCycle()));  
                createCell(style1, rowData, 2, StringUtil.null2Blank(miguIncomeActual.getProjectId())); 
                createCell(style1, rowData, 3, StringUtil.null2Blank(new String(miguIncomeActual.getProjectName().getBytes("UTF-8"),"UTF-8")));  
                createCell(style1, rowData, 4, StringUtil.null2Blank(miguIncomeActual.getClassName()));  
                createCell(style1, rowData, 5, StringUtil.null2Blank(miguIncomeActual.getSectionName()));  
                createCell(style1, rowData, 6, StringUtil.null2Blank(miguIncomeActual.getBillingKey()));  
                createCell(style1, rowData, 7, StringUtil.null2Blank(miguIncomeActual.getInvoiceCode()==null?"":miguIncomeActual.getInvoiceCode()));  
                createCell(style1, rowData, 8, StringUtil.null2Blank(miguIncomeActual.getInvoiceNumber()==null?"":miguIncomeActual.getInvoiceNumber()));  
                createCell(style1, rowData, 9, StringUtil.null2Blank(miguIncomeActual.getBillingDate()==null?"":miguIncomeActual.getBillingDate()));
                createCell(style1, rowData, 10, StringUtil.null2Blank(miguIncomeActual.getBillingIncome()==null?"":miguIncomeActual.getBillingIncome()));
                createCell(style1, rowData, 11, StringUtil.null2Blank(miguIncomeActual.getBillingTotal()==null?"":miguIncomeActual.getBillingTotal()));
                createCell(style1, rowData, 12, StringUtil.null2Blank(miguIncomeActual.getIncome()==null?"":miguIncomeActual.getIncome()));
                createCell(style1, rowData, 13, StringUtil.null2Blank(miguIncomeActual.getIncomeDate()==null?"":miguIncomeActual.getIncomeDate()));
                String status = "";
                if(miguIncomeActual.getIncomeStatus()!=null){
                	if(miguIncomeActual.getIncomeStatus().equals("1")){
                    	status = "待审核("+miguIncomeActual.getAuditPerson()+")";
                    }else if(miguIncomeActual.getIncomeStatus().equals("2")){
                    	status = "审核通过";
                    }else if(miguIncomeActual.getIncomeStatus().equals("3")){
                    	status = "驳回";
                    }
                }
                createCell(style1, rowData, 14, status); 
                createCell(style1, rowData, 15, StringUtil.null2Blank(miguIncomeActual.getDeptName()));  
                createCell(style1, rowData, 16, StringUtil.null2Blank(miguIncomeActual.getUserName()));  
                
			}
            
            OutputStream os = null;
			try {
				os = new FileOutputStream(reportFile);
				hssfworkbook.write(os);
				os.close();
				map.put("reCode", 100);
				map.put("fileName", reportFile.getName());
				log.info("实际收款文件生成成功");
			} catch (Exception e) {
				map.put("reCode", -1);
				map.put("reStr", "实际收款文件生成异常");
				e.printStackTrace();
			}
        }
        return map;
    }
    
    
    /**
     * 发开票明细查询
     * @param billingKey
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/showBilldetail", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> showBilldetail(String billingKey) throws Exception
    {
        Map<String, Object> map = new HashMap<String, Object>();
        List<MiguIncomeBill> list =incomeService.showBilldetail(billingKey);
        map.put("rows", list);
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
    
    //判空处理
    public String doNull(Object object){
    	if(object == null){
    		return "";
    	}else{
    		return object.toString();
    	}
    }
    
    //判空处理2
    public String doNull2(String object){
    	if(object == null){
    		return "";
    	}else{
    		String s1 = String.format("%.2f", Double.valueOf(object));
    		return s1;
    	}
    }
    
}
