package cn.migu.income.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
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
import cn.migu.income.dao.MiguIncomeManagerMapper;
import cn.migu.income.pojo.MiguIncomeDetail;
import cn.migu.income.pojo.MiguIncomeManager;
import cn.migu.income.pojo.MiguIncomeManagerHis;
import cn.migu.income.pojo.MiguPrintDetail;
import cn.migu.income.pojo.MiguProjectDetail;
import cn.migu.income.pojo.MiguUsers;
import cn.migu.income.service.MiguIncomeManagerService;
import cn.migu.income.util.MiguConstants;
import cn.migu.income.util.StringUtil;
import cn.migu.income.util.TxtFileOperation;

@Controller
@RequestMapping(value = "/incomeManager")
public class MiguIncomeManagerController {

	@Autowired
	private MiguIncomeManagerService incomeManagerService;
	@Autowired
	private MiguIncomeManagerMapper IncomeManagerMapper;

	@Autowired
	private MiguIncomeDetailMapper incomeDetailMapper;
	
	final static Logger log = LoggerFactory.getLogger(MiguIncomeManagerController.class);

	/**
	 * 新增预估收入
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/addEstimateIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addEstimateIncome(HttpServletRequest request, HttpServletResponse resonse) {
		return incomeManagerService.addEstimateIncome(request, resonse);
	}

	@RequestMapping(value = "/checkUnique", method = RequestMethod.POST)
	@ResponseBody
	public MiguIncomeManager checkUnique(String cycle, String projectId) {
		MiguIncomeManager incomeManager = IncomeManagerMapper.selectByUnique(StringUtil.formatDateToyyyyMM(cycle),
				projectId);
		return incomeManager;
	}

	@RequestMapping(value = "/selectRealIncomeByUnique", method = RequestMethod.POST)
	@ResponseBody
	public MiguIncomeManager selectRealIncomeByUnique(String cycle, String projectId) {
		MiguIncomeManager incomeManager = IncomeManagerMapper
				.selectRealIncomeByUnique(StringUtil.formatDateToyyyyMM(cycle), projectId);
		return incomeManager;
	}

	/**
	 * 修改预估收入
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/editEstimateIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> editEstimateIncome(HttpServletRequest request, HttpServletResponse resonse) {
		return incomeManagerService.saveEditEstimateIncome(request, resonse);
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
	 * 删除预估收入明细信息
	 * 
	 * @param incomeDetailId
	 * @param request
	 * @param resonse
	 * @return
	 */
	@RequestMapping(value = "/delEstimateIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delEstimateIncome(String incomeDetailId, String type, HttpServletRequest request,
			HttpServletResponse resonse) {
		return incomeManagerService.delEstimateIncome(incomeDetailId, type);
	}

	/**
	 * 新增实际收入
	 * 
	 * @param request
	 * @param resonse
	 * @return
	 */
	@RequestMapping(value = "/addRealIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addRealIncome(HttpServletRequest request, HttpServletResponse resonse) {
		return incomeManagerService.addRealIncome(request, resonse);
	}

	@RequestMapping(value = "/refreshIncome", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> refreshIncome(HttpServletRequest request, HttpServletResponse resonse) {
		return incomeManagerService.refreshIncome(request, resonse);
	}

	/**
	 * 审核历史查询
	 * 
	 * @param request
	 * @param session
	 * @param incomeManagerId
	 * @return
	 */
	@RequestMapping(value = "/showHis", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> showHis(HttpServletRequest request, HttpSession session,
			String incomeManagerId, String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<MiguIncomeManagerHis> list = new ArrayList<MiguIncomeManagerHis>();
		try {
			list = incomeManagerService.queryList(incomeManagerId, type);
			log.info("审核历史记录查询：收入管理表ID=" + incomeManagerId);
			log.info("审核历史记录查询查询结果：" + list);
		} catch (Exception e) {
			log.info("Exception:", e);
			e.printStackTrace();
		}
		map.put("rows", list);
		return map;
	}

	@RequestMapping(value = "/printEstPage", method = RequestMethod.GET)
	public ModelAndView printEstimateIncomePage(String incomeManagerId) {
		ModelAndView md = new ModelAndView();
		List<MiguIncomeManagerHis> list = incomeManagerService.queryList(incomeManagerId, "E");
		for (MiguIncomeManagerHis miguIncomeManagerHis : list) {
			if(null!=miguIncomeManagerHis.getDealResult()){
				if("0".equals(miguIncomeManagerHis.getDealResult())){
					miguIncomeManagerHis.setDealResult("提交审核");
				}
				if("1".equals(miguIncomeManagerHis.getDealResult())){
					miguIncomeManagerHis.setDealResult("审核通过");
				}
				if("2".equals(miguIncomeManagerHis.getDealResult())){
					miguIncomeManagerHis.setDealResult("审核通过，流程关闭");
				}
				if("3".equals(miguIncomeManagerHis.getDealResult())){
					miguIncomeManagerHis.setDealResult("驳回");
				}
			}
			
		}
		md.addObject("hisList", list);
		
		
		
		MiguIncomeManager incomeManager = IncomeManagerMapper.selectByPrimaryKey(Long.parseLong(incomeManagerId));
		String url = incomeManager.getEstimateOptionsUrl();
		if (!StringUtil.isEmpty(url)) {
			String fileName = url.substring(url.lastIndexOf("/") + 1);	
			incomeManager.setFileName(fileName);
		}
		md.addObject("incomeManager", incomeManager);
		
		
		DecimalFormat decimalFormat = new DecimalFormat("0.00");//格式化设置  
		String estimateIncome = incomeManager.getEstimateIncome()==null?"":decimalFormat.format(incomeManager.getEstimateIncome());
		String estimateAmount = incomeManager.getEstimateAmount()==null?"":decimalFormat.format(incomeManager.getEstimateAmount());
		String estimateExclusiveTax = incomeManager.getEstimateExclusiveTax()==null?"":decimalFormat.format(incomeManager.getEstimateExclusiveTax());
		md.addObject("estimateIncome", estimateIncome);
		md.addObject("estimateAmount", estimateAmount);
		md.addObject("estimateExclusiveTax", estimateExclusiveTax);
		
		
		List<MiguIncomeDetail> deatailList = incomeManagerService.viewIncomeDetails(incomeManagerId);
		md.addObject("deatailList", deatailList);
		
		MiguProjectDetail miguProject = incomeDetailMapper.selectProjectDetail(incomeManager.getProjectId());
		md.addObject("miguProject", miguProject);
		
		MiguPrintDetail miguPrint = incomeDetailMapper.selectPrintDetail(incomeManager.getProjectId());
		md.addObject("miguPrint", miguPrint);
		
		md.setViewName("estimatePrint");
		return md;
	}

	@RequestMapping(value = "/printRealPage", method = RequestMethod.GET)
	public ModelAndView printRealIncomePage(String incomeManagerId) {
		ModelAndView md = new ModelAndView();
		List<MiguIncomeManagerHis> list = incomeManagerService.queryList(incomeManagerId, "R");
		for (MiguIncomeManagerHis miguIncomeManagerHis : list) {
			if(null!=miguIncomeManagerHis.getDealResult()){
				if("0".equals(miguIncomeManagerHis.getDealResult())){
					miguIncomeManagerHis.setDealResult("提交审核");
				}
				if("1".equals(miguIncomeManagerHis.getDealResult())){
					miguIncomeManagerHis.setDealResult("审核通过");
				}
				if("2".equals(miguIncomeManagerHis.getDealResult())){
					miguIncomeManagerHis.setDealResult("审核通过，流程关闭");
				}
				if("3".equals(miguIncomeManagerHis.getDealResult())){
					miguIncomeManagerHis.setDealResult("驳回");
				}
			}
			
		}
		md.addObject("hisList", list);
		MiguIncomeManager incomeManager = IncomeManagerMapper.selectByPrimaryKey(Long.parseLong(incomeManagerId));
		String url = incomeManager.getRealOptionsUrl();
		if (!StringUtil.isEmpty(url)) {
			String fileName = url.substring(url.lastIndexOf("/") + 1);
				incomeManager.setFileName(fileName);
		}
		String isNeedBill ="";
		if (!StringUtil.isEmpty(incomeManager.getIsNeedBill())) {
			if(incomeManager.getIsNeedBill().equals("1")){
				isNeedBill="是";
			}else if(incomeManager.getIsNeedBill().equals("0")){
				isNeedBill="否";
			}
		}else{
			isNeedBill="暂未选择";
		}
		incomeManager.setIsNeedBill(isNeedBill);
		md.addObject("incomeManager", incomeManager);
		
		
		DecimalFormat decimalFormat = new DecimalFormat("0.00");//格式化设置  
		String realIncome = incomeManager.getRealIncome()==null?"":decimalFormat.format(incomeManager.getRealIncome());
		String realAmount = incomeManager.getRealAmount()==null?"":decimalFormat.format(incomeManager.getRealAmount());
		String realExclusiveTax = incomeManager.getRealExclusiveTax()==null?"":decimalFormat.format(incomeManager.getRealExclusiveTax());
		md.addObject("realIncome", realIncome);
		md.addObject("realAmount", realAmount);
		md.addObject("realExclusiveTax", realExclusiveTax);
		
		List<MiguIncomeDetail> deatailList = incomeDetailMapper.viewRealIncomeDetails(incomeManagerId);
		md.addObject("deatailList", deatailList);
		
		MiguProjectDetail miguProject = incomeDetailMapper.selectProjectDetail(incomeManager.getProjectId());
		md.addObject("miguProject", miguProject);
		
		MiguPrintDetail miguPrint = incomeDetailMapper.selectPrintDetail(incomeManager.getProjectId());
		md.addObject("miguPrint", miguPrint);
		
		md.setViewName("realIncomePrint");
		return md;
	}

}
