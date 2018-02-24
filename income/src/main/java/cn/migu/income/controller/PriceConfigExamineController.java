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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import cn.migu.income.pojo.MiguUsers;
import cn.migu.income.pojo.PriceConfigExamine;
import cn.migu.income.pojo.TMiguContract;
import cn.migu.income.pojo.TMiguPriceConfigHistory;
import cn.migu.income.pojo.TMiguProjectBase;
import cn.migu.income.service.PriceConfigExamineService;
import cn.migu.income.util.MiguConstants;
import cn.migu.income.util.StringUtil;

/**
 * 定价配置审核
 * @author chentao
 *
 */
@Controller
@RequestMapping("/examine")
public class PriceConfigExamineController
{
    final static Logger log = LoggerFactory.getLogger(PriceConfigExamineController.class);
    
    @Autowired
    private PriceConfigExamineService priceConfigExamineService;
    
    /**
     * 定价配置审核页面
     * @author chentao
     */
    @RequestMapping(value = "/priceConfigExamine")
    public ModelAndView priceConfigExamine(HttpServletRequest request)
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("priceConfigExamine");
        return mav;
    }
    
    
    /**
     * 定价配置审核查询
     * @param request
     * @param session
     * @param currentUserId
     * @param q_projectId
     * @param q_projectDeptId
     * @param q_projectUserName
     * @param q_priceConfigAuditResult
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/queryAllExamine", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> queryAllExamine(HttpServletRequest request,HttpSession session, 
        String q_projectId,String q_projectDeptId,String q_projectUserName,
        String q_priceConfigAuditResult,String page,String rows)
    {
    	MiguUsers user = (MiguUsers)(session.getAttribute(MiguConstants.USER_KEY));
        Map<String, Object> map = new HashMap<String, Object>();
        int count=0;
        List<PriceConfigExamine> list =null;
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
            if (StringUtils.isEmpty(q_projectId))
            {
            	q_projectId = null;
            }
            if (StringUtils.isEmpty(q_projectDeptId))
            {
            	q_projectDeptId = null;
            }
            if (StringUtils.isEmpty(q_projectUserName))
            {
            	q_projectUserName = null;
            }
            if (StringUtils.isEmpty(q_priceConfigAuditResult))
            {
            	q_priceConfigAuditResult = "1";
            }
            if(StringUtil.isNotEmpty(q_projectId)&&(q_projectId.contains("_")||q_projectId.contains("%")))
            {
            	q_projectId=StringUtil.getSpecialParam(q_projectId);
            }
            if(StringUtil.isNotEmpty(q_projectUserName)&&(q_projectUserName.contains("_")||q_projectUserName.contains("%")))
            {
            	q_projectUserName=StringUtil.getSpecialParam(q_projectUserName);
            }
            count = priceConfigExamineService.queryTotal(q_projectId, q_projectDeptId, q_projectUserName,
            		q_priceConfigAuditResult, user.getUserId());
            list = priceConfigExamineService.queryAllExamine(q_projectId, q_projectDeptId, q_projectUserName,
            		q_priceConfigAuditResult, user.getUserId(),Integer.parseInt(page), Integer.parseInt(rows));
            log.info("定价配置审核查询：总数=" + count + ",项目编号 =" + q_projectId + ";责任部门 =" + q_projectDeptId + ";责任人=" + q_projectUserName
            		+ ",审核结果 =" + q_priceConfigAuditResult );
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
     * 提交审核
     * @param request
     * @param session
     * @param auditResult
     * @param priceConfigId
     * @param handlingSuggestion
     * @return
     */
    @RequestMapping(value = "/submitExamine", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> submitExamine(HttpServletRequest request,HttpSession session,String auditResult,
    		String priceConfigId,String handlingSuggestion)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        String result = "";
        try
        {
            result = priceConfigExamineService.submitExamine(auditResult,priceConfigId,handlingSuggestion);
        }
        catch (Exception e)
        {
            log.info("Exception:", e);
            e.printStackTrace();
        }
        
        if ("0".equals(result))
        {
            map.put("reCode", 100);
            map.put("reStr", "定价配置审核成功！");
        }
        else
        {
            map.put("reCode", -1);
            map.put("reStr", "定价配置审核失败！");
        }
        return map;
    }
    
    
    @RequestMapping(value = "/queryAllPriceConfigInfoHis", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> queryAllPriceConfigInfoHis(HttpServletRequest request,HttpSession session, 
        String priceConfigId)
    {
    	MiguUsers user = (MiguUsers)(session.getAttribute(MiguConstants.USER_KEY));
        Map<String, Object> map = new HashMap<String, Object>();
        int count=0;
        List<TMiguPriceConfigHistory> list =null;
        try
        {
            list = priceConfigExamineService.queryAllPriceConfigInfoHis(priceConfigId);
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
    
}
