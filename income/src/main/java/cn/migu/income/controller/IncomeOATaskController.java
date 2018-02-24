package cn.migu.income.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.migu.income.dao.MiguIncomeDetailMapper;
import cn.migu.income.dao.MiguUsersMapper;
import cn.migu.income.pojo.MiguIncomeActual;
import cn.migu.income.pojo.MiguIncomeBill;
import cn.migu.income.pojo.MiguIncomeDetail;
import cn.migu.income.pojo.MiguIncomeManager;
import cn.migu.income.pojo.MiguIncomeManagerHis;
import cn.migu.income.pojo.MiguProjectDetail;
import cn.migu.income.pojo.MiguUsers;
import cn.migu.income.pojo.TMiguDepartments;
import cn.migu.income.service.IMiguActualIncomeService;
import cn.migu.income.service.MiguIncomeManagerService;
import cn.migu.income.util.MiguConstants;
import cn.migu.income.util.PropValue;
import cn.migu.income.util.StringUtil;
import cn.migu.income.util.TxtFileOperation;

@Controller
@RequestMapping(value = "/incomeOATask")
public class IncomeOATaskController {

	@Autowired
	private MiguIncomeManagerService incomeManagerService;

	@Autowired
	private MiguUsersMapper UsersMapper;

	@Autowired
	private MiguIncomeDetailMapper incomeDetailMapper;

	@Autowired
	private IMiguActualIncomeService incomeService;

	final static Logger log = LoggerFactory.getLogger(IncomeOATaskController.class);

	private final static String EST_OA_TITLE = "预估收入审核";

	private final static String REAL_OA_TITLE = "实际收入审核";

	private final static String INCOME_OA_TITLE = "实际收款审核";

	@RequestMapping(value = "/examineEstimateIncome", method = RequestMethod.GET)
	public ModelAndView examineEstimateIncome(String incomeManageId, String auditUser, String code) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		MiguIncomeManager incomeManager = incomeManagerService.selectExamineObj(incomeManageId, auditUser);
		if (incomeManager != null) {
			String url = incomeManager.getEstimateOptionsUrl();
			String fileName = url.substring(url.lastIndexOf("/") + 1);
			if (StringUtil.isNotEmpty(fileName) && fileName.length() > 10) {
				incomeManager.setFileName(fileName.substring(0, 10) + "...");
			} else {
				incomeManager.setFileName(fileName);
			}
			String explain = incomeManager.getEstimateExplain();
			if (StringUtil.isNotEmpty(explain) && explain.length() > 10) {
				incomeManager.setRemark(explain.substring(0, 10) + "...");
			} else {
				incomeManager.setRemark(explain);
			}
		}
		List<MiguIncomeDetail> list = incomeManagerService.viewIncomeDetails(incomeManageId);
		List<MiguIncomeManagerHis> his = incomeManagerService.queryList(incomeManageId, "E");
		MiguIncomeManagerHis incomeManagerHis = null;
		for (int i = 0; i < his.size(); i++) {
			String options = "";
			incomeManagerHis = his.get(i);
			if (StringUtil.isNotEmpty(incomeManagerHis.getDealOptions())
					&& incomeManagerHis.getDealOptions().length() > 10) {
				options=incomeManagerHis.getDealOptions().substring(0,10);
				incomeManagerHis.setOptions(options+"...");
			}else{
				incomeManagerHis.setOptions(incomeManagerHis.getDealOptions());
			}
		}
		
		MiguProjectDetail miguProject = incomeDetailMapper.selectProjectDetail(incomeManager.getProjectId());
		
		MiguUsers user = UsersMapper.selectByPrimaryKey(auditUser);
		modelAndView.addObject("incomeManager", incomeManager);
		modelAndView.addObject("code", code);
		modelAndView.addObject("his", his);
		modelAndView.addObject("user", user);
		modelAndView.addObject("list", list);
		modelAndView.addObject("miguProject", miguProject);
		modelAndView.setViewName("examineEstimateIncome");
		return modelAndView;
	}

	/**
	 * 审核预估收入
	 * 
	 * @param map
	 * @param request
	 * @param resonse
	 * @return
	 */
	@RequestMapping(value = "/doExamine", method = RequestMethod.POST)
	@ResponseBody
	public Object doExamine(@RequestBody Map<String, String> map, HttpServletRequest request,
			HttpServletResponse resonse) {
		return incomeManagerService.doExamine(map, EST_OA_TITLE,PropValue.getPropValue("EXAMINE_URL") , "E");
	}

	@RequestMapping(value = "/examineRealIncome", method = RequestMethod.GET)
	public ModelAndView examineRealIncome(String incomeManageId, String auditUser, String code) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		MiguIncomeManager incomeManager = incomeManagerService.selectExamineObj(incomeManageId, auditUser);
		if (incomeManager != null) {
			String url = incomeManager.getRealOptionsUrl();
			String fileName = url.substring(url.lastIndexOf("/") + 1);
			if (StringUtil.isNotEmpty(fileName) && fileName.length() > 10) {
				incomeManager.setFileName(fileName.substring(0, 10) + "...");
			} else {
				incomeManager.setFileName(fileName);
			}
			String explain = incomeManager.getRealExplain();
			if (StringUtil.isNotEmpty(explain) && explain.length() > 10) {
				incomeManager.setRemark(explain.substring(0, 10) + "...");
			} else {
				incomeManager.setRemark(explain);
			}
		}
		List<MiguIncomeDetail> list = incomeDetailMapper.viewRealIncomeDetails(incomeManageId);
		List<MiguIncomeManagerHis> his = incomeManagerService.queryList(incomeManageId, "R");

		MiguIncomeManagerHis incomeManagerHis = null;
		for (int i = 0; i < his.size(); i++) {
			String options = "";
			incomeManagerHis = his.get(i);
			if (StringUtil.isNotEmpty(incomeManagerHis.getDealOptions())
					&& incomeManagerHis.getDealOptions().length() > 10) {
				options=incomeManagerHis.getDealOptions().substring(0,10);
				incomeManagerHis.setOptions(options+"...");
			}else{
				incomeManagerHis.setOptions(incomeManagerHis.getDealOptions());
			}
		}
		MiguProjectDetail miguProject = incomeDetailMapper.selectProjectDetail(incomeManager.getProjectId());
		
		MiguUsers user = UsersMapper.selectByPrimaryKey(auditUser);
		modelAndView.addObject("incomeManager", incomeManager);
		modelAndView.addObject("code", code);
		modelAndView.addObject("his", his);
		modelAndView.addObject("user", user);
		modelAndView.addObject("list", list);
		modelAndView.addObject("miguProject", miguProject);
		modelAndView.setViewName("examineRealIncome");
		return modelAndView;
	}

	/**
	 * 审核实际收入
	 * 
	 * @param map
	 * @param request
	 * @param resonse
	 * @return
	 */
	@RequestMapping(value = "/doRealExamine", method = RequestMethod.POST)
	@ResponseBody
	public Object doRealExamine(@RequestBody Map<String, String> map, HttpServletRequest request,
			HttpServletResponse resonse) {
		return incomeManagerService.doExamine(map, REAL_OA_TITLE, PropValue.getPropValue("EXAMINE_REAL_URL"), "R");
	}

	/**
	 * 查询审核历史记录
	 * 
	 * @param incomeManagerId
	 * @return
	 */
	@RequestMapping(value = "/queryList", method = RequestMethod.POST)
	@ResponseBody
	public List<MiguIncomeManagerHis> queryList(String incomeManagerId) {

		return incomeManagerService.queryList(incomeManagerId, "E");
	}

	/**
	 * 查询与收入明细
	 * 
	 * @param request
	 * @param session
	 * @param incomeManagerId
	 * @return
	 */
	@RequestMapping(value = "/viewIncomeDetails", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> viewIncomeDetails(HttpServletRequest request, HttpSession session,
			String incomeManagerId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<MiguIncomeDetail> list = new ArrayList<MiguIncomeDetail>();
		try {
			list = incomeManagerService.viewIncomeDetails(incomeManagerId);
			log.info("预估收入明细查询：预估收入ID=" + incomeManagerId);
			log.info("预估收入明细查询结果：" + list);
		} catch (Exception e) {
			log.info("Exception:", e);
			e.printStackTrace();
		}
		map.put("rows", list);
		return map;
	}

	/**
	 * 下载模版文件
	 * 
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
		File file = new File(rootpath);
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
	 * 查询所有部门
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/queryAllDep", method = RequestMethod.POST)
	public @ResponseBody List<TMiguDepartments> queryAllDep(HttpServletRequest request, HttpSession session) {
		try {
			return incomeService.queryAllDep();
		} catch (Exception e) {
			log.error("部门查询异常:", e);
		}
		return null;
	}

	/**
	 * 查询部门对应人员
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/queryDepPerson", method = RequestMethod.POST)
	public @ResponseBody List<MiguUsers> queryDepPerson(HttpServletRequest request, HttpSession session,
			String deptId,String userId) {
		try {
			 MiguUsers user= UsersMapper.selectByPrimaryKey(userId);
			 if(!"1".equals(user.getDeptId())&&"1".equals(deptId)){
				 return UsersMapper.queryUserByRole(deptId);
			 }
			 
			return incomeService.querydepPerson(deptId);
		} catch (Exception e) {
			log.error("部门人员查询异常:", e);
		}
		return null;
	}

	/**
	 * 实际收款审核页面
	 * 
	 * @param incomeManageId
	 * @param auditUser
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/examineIncome", method = RequestMethod.GET)
	public ModelAndView examineIncome(String billingKey, String auditUser, String code) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		// 项目详情查询
		MiguIncomeActual incomeManager = incomeService.querySglActualIncome(billingKey);
		if (incomeManager != null) {
			String url = incomeManager.getIncomeOptionsUrl();
			String fileName = url.substring(url.lastIndexOf("/") + 1);
			if (StringUtil.isNotEmpty(fileName) && fileName.length() > 10) {
				incomeManager.setFileName(fileName.substring(0, 10) + "...");
			} else {
				incomeManager.setFileName(fileName);
			}
			String billingIncome = incomeManager.getBillingIncome();
			if(!billingIncome.equals("-")){
			       DecimalFormat format = new DecimalFormat("0.00");
			       incomeManager.setBillingIncome(format.format(new BigDecimal(billingIncome)));
			}
		}
		// 项目历史查询
		List<MiguIncomeManagerHis> his = incomeService.queryList(billingKey, "A");
		
		MiguIncomeManagerHis incomeManagerHis = null;
		for (int i = 0; i < his.size(); i++) {
			String options = "";
			incomeManagerHis = his.get(i);
			if (StringUtil.isNotEmpty(incomeManagerHis.getDealOptions())
					&& incomeManagerHis.getDealOptions().length() > 10) {
				options=incomeManagerHis.getDealOptions().substring(0,10);
				incomeManagerHis.setOptions(options+"...");
			}else{
				incomeManagerHis.setOptions(incomeManagerHis.getDealOptions());
			}
		}
		MiguProjectDetail miguProject = incomeDetailMapper.selectProjectDetail(incomeManager.getProjectId());
		
		//查询发票明细
        List<MiguIncomeBill> billingList =incomeService.showBilldetail(billingKey);
		
		// 审核人查询
		MiguUsers user = UsersMapper.selectByPrimaryKey(auditUser);
		modelAndView.addObject("incomeManager", incomeManager);
		modelAndView.addObject("code", code);
		modelAndView.addObject("his", his);
		modelAndView.addObject("user", user);
		modelAndView.addObject("miguProject", miguProject);
		modelAndView.addObject("billingList", billingList);
		modelAndView.setViewName("examineIncome");
		return modelAndView;
	}

	/**
	 * 审核实际收款
	 * 
	 * @param map
	 * @param request
	 * @param resonse
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/doIncomeExamine", method = RequestMethod.POST)
	@ResponseBody
	public Object doIncomeExamine(@RequestBody Map<String, String> map, HttpServletRequest request,
			HttpServletResponse resonse) throws Exception {
		return incomeService.doExamine(map, INCOME_OA_TITLE, PropValue.getPropValue("EXAMINE_INCOME_URL"), "A");
	}
	
    /**
     * 下载项目附件
     * @param request
     * @param response
     * @param test
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/downLoadProjectFile", method = RequestMethod.POST)
    public @ResponseBody String downLoadProjectFile(HttpServletRequest request, HttpSession session, HttpServletResponse response,String filename,String projectId)
        throws FileNotFoundException
    {
    	String path =PropValue.getPropValue(MiguConstants.FILES_LOCATION)+projectId+"/"+filename; //文件路径
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
}
