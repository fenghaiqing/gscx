
package cn.migu.income.controller;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.axis2.AxisFault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

import cn.migu.income.pojo.MiguProjectBudget;
import cn.migu.income.service.MiguProjectBudgetService;
import cn.migu.income.util.StringUtil;
import cn.migu.income.webservices.FindContractBudget;
import cn.migu.income.webservices.FindContractBudgetResponse;
import cn.migu.income.webservices.RequestParam;
import cn.migu.income.webservices.SyncContractBudgetStub;

/**
 * 关联预算
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/estimate")
public class AssociatedBudgetController {
	
	@Autowired
	private MiguProjectBudgetService projectBudgetService;
	
	/**
	 * 关联预算页面
	 */
	@RequestMapping(value = "/estimateManager")
	public ModelAndView contractManage(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("associatedEstimate");
		return mav;
	}

	/**
	 * 关联预算
	 * @param miguProjectBudget
	 * @return
	 */
	@RequestMapping(value="/associatedBudget",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> associatedBudget(@RequestBody List<MiguProjectBudget> miguProjectBudget){
		
		return projectBudgetService.associatedBudget(miguProjectBudget);
	}
	
	/**
	 * 取消关联
	 * @param miguProjectBudget
	 * @return
	 */
	@RequestMapping(value="/cancelAssociation",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> cancelAssociation(@RequestBody List<MiguProjectBudget> miguProjectBudget){
		
		return projectBudgetService.cancelAssociation(miguProjectBudget);
	}
	
	/**
	 * 查看关联预算
	 * @param miguProjectBudget
	 * @return
	 */
	@RequestMapping(value="/viewAssociation",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> viewAssociation(String projectId,String budgetProjectNumber,String budgetProjectName,Integer page,Integer rows){
		MiguProjectBudget miguProjectBudget = new MiguProjectBudget();
		miguProjectBudget.setProjectId(projectId);
		miguProjectBudget.setBudgetProjectName(StringUtil.isEmpty(budgetProjectName)?null:budgetProjectName);
		miguProjectBudget.setBudgetProjectNumber(StringUtil.isEmpty(budgetProjectNumber)?null:budgetProjectNumber);
		miguProjectBudget.setPage(page==null?"":String.valueOf(page));
		miguProjectBudget.setRows(rows==null?"":String.valueOf(rows));
		return projectBudgetService.viewAssociation(miguProjectBudget);
	}
	
	

	/**
	 * 查询关联预算
	 * @param budgetYear
	 * @param budgetDeptCode
	 * @param budgetResultId
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws RemoteException
	 */
	@RequestMapping(value = "/queryContractBudget", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryContractBudget(String budgetYear, String budgetDeptCode,String budgetResultId, Integer page,
			Integer rows) throws RemoteException {
		Map<String, Object> resultMap = new HashMap<>();
		SyncContractBudgetStub stub = new SyncContractBudgetStub();
		FindContractBudget findContractBudget = new FindContractBudget();
		Integer currentPage=page;
		Integer pageSize=rows;
		RequestParam param = new RequestParam();
		if (StringUtils.isEmpty(budgetResultId)) {
			budgetResultId = null;
			if (StringUtils.isEmpty(budgetYear)) {
				return null;
			}
			if (StringUtils.isEmpty(budgetDeptCode)) {
				return null;
			}
		}
		if (StringUtil.isNotEmpty(budgetYear) && (budgetYear.contains("_") || budgetYear.contains("%"))) {
			budgetYear = StringUtil.getSpecialParam(budgetYear);
		}
		if (StringUtil.isNotEmpty(budgetDeptCode) && (budgetDeptCode.contains("_") || budgetDeptCode.contains("%"))) {
			budgetDeptCode = StringUtil.getSpecialParam(budgetDeptCode);
		}

		param.setBudgetYear(budgetYear);
		param.setBudgetDeptCode(budgetDeptCode);
		param.setBudgetResultId(budgetResultId);
		if (currentPage == null) {
			param.setCurrentPage(1);
		} else {
			param.setCurrentPage(currentPage);
		}
		if (pageSize == null) {
			param.setPageSize(20);
		} else {
			param.setPageSize(pageSize);
		}
		param.setSystemSource("income");
		findContractBudget.setRequest(param);
		FindContractBudgetResponse respone = stub.findContractBudget(findContractBudget);
		String result = respone.get_return();
		Map<String, Object> map = new HashMap<>();
		map = (Map<String, Object>) JSON.parse(result);
		resultMap.put("rows", ((Map<String, Object>) map.get("budgetList")).get("budgetItem"));
		resultMap.put("total", map.get("totalRecord"));
		return resultMap;
	}
}
