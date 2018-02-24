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
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import cn.migu.income.pojo.MiguUsers;
import cn.migu.income.pojo.TMiguContract;
import cn.migu.income.service.IMiguContractManageService;
import cn.migu.income.util.MiguConstants;
import cn.migu.income.util.PropValue;
import cn.migu.income.util.StringUtil;
import cn.migu.income.webservices.ContractSyncService;
import cn.migu.income.webservices.ContractSyncServiceStub;
import cn.migu.income.webservices.GetContractList;
import cn.migu.income.webservices.GetContractListResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 合同管理
 * @author chentao
 *
 */
@Controller
@RequestMapping("/contract")
public class ContractManageController
{
    final static Logger log = LoggerFactory.getLogger(ContractManageController.class);
    
    
    @Autowired
    private IMiguContractManageService contractManageService;
    
    /**
     * 合同管理页面
     * @author chentao
     */
    @RequestMapping(value = "/contractManage")
    public ModelAndView contractManage(HttpServletRequest request)
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("contractManage");
        return mav;
    }
    
    /**
     * 查询所有的合同
     * @param request
     * @param session
     * @param q_conNumber
     * @param q_conNo
     * @param q_conStartTime
     * @param q_conEndTime
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/queryAllContract", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> queryAllContract(HttpServletRequest request,HttpSession session, 
        String q_conNumber,String q_conNo,String q_conStartTime,String q_conEndTime,String page,String rows)
    {
    	MiguUsers user = (MiguUsers)(session.getAttribute(MiguConstants.USER_KEY));
        Map<String, Object> map = new HashMap<String, Object>();
        int count=0;
        List<TMiguContract> list = new ArrayList<TMiguContract>();
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
            if (StringUtils.isEmpty(q_conNumber))
            {
            	q_conNumber = null;
            }
            if (StringUtils.isEmpty(q_conNo))
            {
            	q_conNo = null;
            }
            if(StringUtils.isEmpty(q_conStartTime))
            {
            	q_conStartTime = null;
            }else{
            	q_conStartTime = StringUtil.formatStringTime(q_conStartTime)+" 00:00:00";
            }
            if(StringUtils.isEmpty(q_conEndTime))
            {
            	q_conEndTime = null;
            }else{
            	q_conEndTime = StringUtil.formatStringTime(q_conEndTime)+" 00:00:00";
            }
            if(StringUtil.isNotEmpty(q_conNumber)&&(q_conNumber.contains("_")||q_conNumber.contains("%")))
            {
            	q_conNumber=StringUtil.getSpecialParam(q_conNumber);
            }
            if(StringUtil.isNotEmpty(q_conNo)&&(q_conNo.contains("_")||q_conNo.contains("%")))
            {
            	q_conNo=StringUtil.getSpecialParam(q_conNo);
            }
            if(q_conStartTime!=null){
            	ContractSyncService contractSyncService = new ContractSyncServiceStub(
            			PropValue.getPropValue(MiguConstants.WEBSERVICE_LOCATION));

        		GetContractList getContractList = new GetContractList();
        		getContractList.setBeginDate(q_conStartTime);
        		getContractList.setEndDate(q_conEndTime);
        		getContractList.setCurrentPageP(page);
        		getContractList.setPageSizeP(rows);
        		// getContractList.setTotalPage("");
        		// getContractList.setTotalRecord("");
        		if(q_conNumber!=null){
        			getContractList.setContractNumber(q_conNumber);
        		}
        		if(q_conNo!=null){
        			getContractList.setContractCode(q_conNo);
        		}
        		GetContractListResponse GetContractListResponse = contractSyncService.getContractList(getContractList);
        		String json = GetContractListResponse.get_return();
        		JSONObject dataJson = JSONObject.fromObject(json);
        		JSONObject data = dataJson.getJSONObject("data");
        		JSONArray contractList = data.getJSONArray("contractList");
        		if(contractList.size()!=0){
        			count = data.getInt("totalRecord");
        			list = contractManageService.queryAllContract(contractList);
        		}
            }
            log.info("合同列表查询：总数=" + count + ",合同流水号 =" + q_conNumber + ";合同编号 =" + q_conNo + ";合同开始时间 =" + q_conStartTime
            		+ ",合同结束时间  =" + q_conEndTime);
        }
        catch (Exception e)
        {
        	log.info("合同查询异常:", e);
        	map.put("Exception", e);
            return map;
            
        }
        map.put("total", count);
        map.put("rows", list);
        return map;
    }
    
    /**
     * 关联合同
     * @param contracts
     * @return
     */
    @RequestMapping(value = "/doAssociatedContract", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> doAssociatedContract(@RequestBody  List<TMiguContract> contracts)
    {
    	log.info("合同"+contracts);
    	Map<String, Object> map = new HashMap<String, Object>();
    	String result = "";
    	try {
			result = contractManageService.doAssociatedContract(contracts);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	log.info("合同结果"+result);
    	map.put("reCode", 100);
        map.put("reStr", result);
    	return map;
    }
    
    /**
     * 查询已关联合同列表
     * @param request
     * @param session
     * @param projectId
     * @param q_conNumber
     * @param q_conNo
     * @param q_conStartTime
     * @param q_conEndTime
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/queryAllAssociatedContract", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> queryAllAssociatedContract(HttpServletRequest request,HttpSession session, 
        String projectId,String q_conNumber,String q_conNo,
        String page,String rows)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        int count=0;
        List<TMiguContract> list =null;
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
            if (StringUtils.isEmpty(q_conNumber))
            {
            	q_conNumber = null;
            }
            if (StringUtils.isEmpty(q_conNo))
            {
            	q_conNo = null;
            }
            if(StringUtil.isNotEmpty(q_conNumber)&&(q_conNumber.contains("_")||q_conNumber.contains("%")))
            {
            	q_conNumber=StringUtil.getSpecialParam(q_conNumber);
            }
            if(StringUtil.isNotEmpty(q_conNo)&&(q_conNo.contains("_")||q_conNo.contains("%")))
            {
            	q_conNo=StringUtil.getSpecialParam(q_conNo);
            }
            count = contractManageService.queryTotal(projectId, q_conNumber, q_conNo);
            list = contractManageService.queryAllAssociatedContract(projectId, q_conNumber, q_conNo,
            		Integer.parseInt(page), Integer.parseInt(rows));
            log.info("已关联合同列表查询：总数=" + count + ",合同流水号 =" + q_conNumber + ";合同编号 =" + q_conNo);
        }
        catch (Exception e)
        {
            log.info("项目查询异常:", e);
            e.printStackTrace();
        }
        map.put("total", count);
        map.put("rows", list);
        return map;
    }
    
    /**
     * 解除合同关联
     * @param request
     * @param session
     * @param conIds
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/cancelAssociatedContract", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> cancelAssociatedContract(HttpServletRequest request,HttpSession session,String conIds,String projectId)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        String result = "";
        String[] conIdArr = conIds.split(",");
        List<TMiguContract> list=new ArrayList<>();
        for (int i = 0; i < conIdArr.length; i++)
        {
        	TMiguContract miguContract = new TMiguContract();
        	miguContract.setConId(conIdArr[i].toString());
        	miguContract.setProjectId(projectId);
            list.add(miguContract);
        }
        try
        {
            log.info("解除合同关联：conIds=" + conIds);
            result = contractManageService.cancelAssociatedContract(list);
        }
        catch (Exception e)
        {
            log.info("Exception:", e);
            e.printStackTrace();
        }
        
        if ("0".equals(result))
        {
            map.put("reCode", 100);
            map.put("reStr", "解除合同关联成功！");
        }
        else
        {
            map.put("reCode", -1);
            map.put("reStr", "解除合同关联失败！");
        }
        return map;
    }
}
