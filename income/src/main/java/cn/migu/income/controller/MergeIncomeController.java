package cn.migu.income.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.migu.income.pojo.MiguIncomeManager;
import cn.migu.income.pojo.MiguUsers;
import cn.migu.income.pojo.TMiguDepartments;
import cn.migu.income.service.IMiguActualIncomeService;
import cn.migu.income.service.MiguIncomeManagerService;
import cn.migu.income.util.MiguConstants;
import cn.migu.income.util.RetCode;

@RestController
@RequestMapping(value = "/mergeIncome")
public class MergeIncomeController {

	final static Logger log = LoggerFactory.getLogger(MergeIncomeController.class);

	@Autowired
	private MiguIncomeManagerService incomeManagerService;

	@Autowired
	private IMiguActualIncomeService incomeService;

	@RequestMapping(value = "/viewMergeIncome", method = RequestMethod.GET)
	public ModelAndView viewMergeIncome() {

		ModelAndView md = new ModelAndView();

		md.setViewName("mergeIncomeManage");
		return md;
	}

	/**
	 *	查询合并收入
	 * 
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
	@RequestMapping(value = "/queryMergeIncome", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> queryMergeIncome(HttpServletRequest request, HttpSession session,
			String q_month, String q_projectId, String q_projectName, 
			String bzCycleReal,String q_incomeClassId,String q_incomeSectionId,String q_dept,String q_userName,
			String rl_income,String rl_exclusive_tax,String q_cx,String page, String rows) {

		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		List<MiguIncomeManager> list = new ArrayList<MiguIncomeManager>();
		try {
			count = incomeManagerService.queryTotal(q_month, q_projectId, q_projectName,bzCycleReal,q_incomeClassId,q_incomeSectionId
					,q_dept,q_userName,rl_income,rl_exclusive_tax,q_cx);
			list = incomeManagerService.queryMergeIncome(q_month, q_projectId, q_projectName,bzCycleReal,q_incomeClassId,q_incomeSectionId
					,q_dept,q_userName,rl_income,rl_exclusive_tax,q_cx, page, rows);
			log.info(
					"合并实际收入列表查询：总数=" + count + ",月份 =" + q_month + ";项目编号 =" + q_projectId + ";项目名称 =" + q_projectName);
		} catch (Exception e) {
			log.info("合并实际收入查询异常:", e);
			map.put("Exception", e);
			return map;

		}
		map.put("total", count);
		map.put("rows", list);
		return map;
	}

	/**
	 * 查询可以合并的实际收入
	 * 
	 * @param dept_id
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/queryMergeRealIncome", method = RequestMethod.POST)
	public Map<String, Object> queryMergeRealIncome(String dept_id,String startDate,String endDate, String page, String rows) {
		Map<String, Object> result;
		try {
			result = incomeManagerService.queryMergeRealIncome(dept_id,startDate,endDate, page, rows);
			log.info("可合并实际收入列表查询：总数=" + result.get("total") + ",部门 =" + dept_id+"开始月份="+startDate+"，结束月份="+endDate);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			log.info("可合并实际收入列表查询异常：" + e.getMessage());
			return RetCode.serverError();
		}
	}

	@RequestMapping(value = "/getDept", method = RequestMethod.POST)
	public List<TMiguDepartments> getDept() {
		try {
			return incomeService.queryAllDep();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 查询项目
	 * @param projectId
	 * @param projectName
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/searchProject", method = RequestMethod.POST)
	public Map<String, Object> searchProject(HttpServletRequest request,HttpSession session,String projectId, String projectName, String page, String rows) {
		Map<String, Object> result;
		MiguUsers user = (MiguUsers) session.getAttribute(MiguConstants.USER_KEY);
		try {
			result = incomeManagerService.searchProject(projectId, projectName,user.getUserId(), page, rows);
			log.info("合并实际收入项目列表查询：总数=" + result.get("total") + ",项目号=" + projectId + ",项目名=" + projectName);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			log.info("合并实际收入项目列表查询异常：" + e.getMessage());
			return RetCode.serverError();
		}
	}

	/**
	 * 合并
	 * @param ids
	 * @param projectId
	 * @return
	 */
	@RequestMapping(value = "/mergeRealIncome", method = RequestMethod.POST)
	public Map<String, Object> mergeRealIncome(String ids, String projectId) {
		Map<String, Object> result = incomeManagerService.mergeRealIncome(ids, projectId);
		log.info("合并实际收入：合并项实际收入id=" + ids + ",合并项目号=" + projectId);
		return result;
	}
	/**
	 * 查看明细
	 * @param mergeId
	 * @return
	 */
	@RequestMapping(value = "/viewMergeDetail", method = RequestMethod.POST)
	public Map<String, Object> viewMergeDetail(String mergeId) {
		Map<String, Object> result = incomeManagerService.viewMergeDetail(mergeId);
		return result;
	}
	/**
	 * 取消合并
	 * @param mergeId
	 * @return
	 */
	@RequestMapping(value = "/revokeMerge", method = RequestMethod.POST)
	public Map<String, Object> revokeMerge(@RequestBody List<MiguIncomeManager> list) {
		Map<String, Object> result = incomeManagerService.revokeMerge(list);
		return result;
	}
	
	@RequestMapping(value = "/viewMergeInfo", method = RequestMethod.POST)
	public List<MiguIncomeManager> viewMergeInfo(String id) {
		List<MiguIncomeManager> result = incomeManagerService.viewMergeInfo(id);
		return result;
	}
}
