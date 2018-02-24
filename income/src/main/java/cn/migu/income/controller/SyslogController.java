package cn.migu.income.controller;


import java.util.HashMap;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.migu.income.pojo.MiguSystemOptLog;
import cn.migu.income.pojo.MiguUsers;
import cn.migu.income.service.impl.MiguOptLogger;
import cn.migu.income.util.MiguConstants;
import cn.migu.income.util.StringUtil;

/**
 * 
 * 系统操作日志Controller
 * @author  @zhuxiaoling
 * @version  [版本号, 2016年6月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */

@Controller
@RequestMapping(value = "/syslog")
public class SyslogController
{
    
    final static Logger log = LoggerFactory.getLogger(SyslogController.class);    
   
   
    @Autowired
    private MiguOptLogger dbLog;
    /**
     * 管理页面操作日志
     * @author 
     * @param request
     * @return
     */
    @RequestMapping(value = "/syslogManage")
    public ModelAndView spSumManage(HttpServletRequest request)
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("syslog");
        return mav;
    }
     
    
    /**
     * 
     * 分页模糊查询操作日志
     * @author zhuxiaoling
     * @param session
     * @param chName
     * @param page
     * @param rows
     * @return
     * @throws Exception 
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/queryAllSyslog", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> queryAllSpSum(HttpSession session, String userName,String startDate,String endDate,String operation,
        String page, String rows, String chaxun) throws Exception
    {
        String userId = ((MiguUsers)(session.getAttribute(MiguConstants.USER_KEY))).getUserId();
      
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isEmpty(page))
        {
            page = MiguConstants.PAGE_INDEX;
        }
        if (StringUtils.isEmpty(rows))
        {
            rows = MiguConstants.DEFAULT_ROWNUM;
        }
        if (StringUtils.isEmpty(userName))
        {
            userName = null;
        }
        if(StringUtil.isNotEmpty(userName)&&(userName.contains("_")||userName.contains("%")))
        {
            userName=StringUtil.getSpecialParam(userName);
        }
        if (StringUtils.isEmpty(startDate))
        {
            startDate = null;
        }
        if (StringUtils.isEmpty(endDate))
        {
            endDate = null;
        }
        if (StringUtils.isEmpty(operation))
        {
            operation = null;
        }
        if(StringUtil.isNotEmpty(operation)&&(operation.contains("_")||operation.contains("%")))
        {
            operation=StringUtil.getSpecialParam(operation);
        }
        
        Map<String, String> param = new HashMap<String, String>();
        param.put("userName", userName);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("operation", operation);
        param.put("curPage", page);
        param.put("pageSize", rows);
        
        try
        {
            int total = dbLog.querySyslogTotal(param);
            
            log.info(
                "查询操作日志：total=" + total + ", page=" + page + ", rows=" + rows + ", userName=" + userName+",startDate=" +startDate+";endDate="+endDate+",operation="+operation);
            //List<MiguRepSpSum> list = iMiguSpSumService.querySpSumList(param);miguSpSumMapper
            List<MiguSystemOptLog> list = dbLog.queryAllSyslog(param);
          
            log.info("操作日志：" + list);
            //dbLog.logDb(userId, "查询操作日志", "查询结果：" + StringUtil.subStringTo300(list));
            if(!StringUtils.isEmpty(chaxun)){
                String canshu="";
                if(StringUtils.isEmpty(userName)&&StringUtils.isEmpty(startDate)&&StringUtils.isEmpty(endDate)&&StringUtils.isEmpty(operation)){
                    canshu="无";
                }else{
                    canshu="userName=" + userName+",startDate=" +startDate+";endDate="+endDate+",operation="+operation;
                }
            }
            map.put("total", total);
            map.put("rows", list);
        }
        catch (Exception e)
        {
            log.error("Exception:", e);
        }
        return map;
    }
    
  
}
