package cn.migu.income.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.migu.income.pojo.MiguRoles;
import cn.migu.income.pojo.MiguUsers;
import cn.migu.income.pojo.TMiguDepartments;
import cn.migu.income.service.IMiguDepartmentsService;
import cn.migu.income.service.IMiguFuncService;
import cn.migu.income.service.IMiguRolesService;
import cn.migu.income.service.IMiguUsersService;
import cn.migu.income.service.impl.MiguOptLogger;
import cn.migu.income.util.MiguConstants;
import cn.migu.income.util.StringUtil;

/**
 * 
 * 系统管理Controller
 * @author  chentao
 * @version  [版本号, 2016年1月21日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Controller
@RequestMapping(value = "/manage")
public class ManageSystemController
{
    final static Logger log = LoggerFactory.getLogger(ManageSystemController.class);    
    @Autowired
    private IMiguRolesService rolesService;    
    @Autowired
    private IMiguFuncService funcService;    
    @Autowired
    private IMiguUsersService usersService;
    @Autowired
    private IMiguDepartmentsService departmentsService;
    @Autowired
    private MiguOptLogger dbLog;
    
    @RequestMapping(value = "/toLogin")
    public ModelAndView toLogin(HttpServletRequest request)
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }
    
    /**
     * 转到角色管理页面
     * @author chentao
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/roleManage")
    public ModelAndView roleManage(HttpServletRequest request)
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("roleManage");
        return mav;
    }
    
    /**
     * 
     * 根据roleName进行模糊查询，获取所有角色信息
     * @author chentao
     * @param request
     * @param roleName
     * @param page
     * @param rows
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/queryAllRoles", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> queryAllRoles(HttpServletRequest request, HttpSession session, String roleName,String chaxun, String page,
        String rows)
    {
        String userId = ((MiguUsers)(session.getAttribute(MiguConstants.USER_KEY))).getUserId();
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isEmpty(page))
        {
            page = "1";
        }
        if (StringUtils.isEmpty(rows))
        {
            rows = "10";
        }
        if (StringUtils.isEmpty(roleName))
        {
            roleName = null;
        }
        try
        {
            if(StringUtil.isNotEmpty(roleName)&&(roleName.contains("_")||roleName.contains("%")))
            {
                roleName=StringUtil.getSpecialParam(roleName);
            }
            int total = rolesService.queryRolesTotal(roleName);
            log.info("查询所有角色数量：total=" + total + ", page=" + page + ", rows=" + rows + "roleName=" + roleName);
            List<MiguRoles> list = rolesService.queryAllRoles(roleName, page, rows);
            map.put("total", total);
            map.put("rows", list);
            if(!StringUtils.isEmpty(chaxun)){
                String canshu="";
                if(StringUtils.isEmpty(roleName)){
                    canshu="无";
                }else{
                    canshu="roleName=" + roleName;
                }
            }
        }
        catch (Exception e)
        {
            log.error("Exception:", e);
        }
        return map;
    }
    
    /**
     * 添加角色
     * @author chentao
     * @param request
     * @param roleName
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> addRole(HttpServletRequest request, HttpSession session,String roleName)
    {
    	String userId = ((MiguUsers)(session.getAttribute(MiguConstants.USER_KEY))).getUserId();
        log.info("增加角色：roleName=" + roleName);
        Map<String, Object> map = new HashMap<String, Object>();
        String result = "-1";
        try
        {
            result = rolesService.addRole(roleName);
        }
        catch (Exception e)
        {
            log.error("Exception:", e);
        }
        if ("-9".equals(result))
        {
            map.put("reCode", -9);
            map.put("reStr", "角色创建失败，该角色名已经存在!");
            log.info("角色创建失败，该角色名已经存在!!");
            //dbLog.logDb(userId, "角色管理新增", "结果：失败；roleName：" + StringUtil.subStringTo300(roleName));
            return map;
        }
        if ("-1".equals(result))
        {
            map.put("reCode", -1);
            map.put("reStr", "新增失败，请重试");
            log.info("角色创建失败!");
            //dbLog.logDb(userId, "角色管理新增", "结果：失败；roleName：" + StringUtil.subStringTo300(roleName));
            return map;
        }
        map.put("reCode", 100);
        map.put("reStr", "角色创建成功");
        log.info("角色创建成功!");
        return map;
    }
    
    /**
     * 
     * 修改角色
     * @author chentao
     * @param request
     * @param roleId
     * @param roleName
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/updateRole", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> updateRole(HttpServletRequest request, HttpSession session,String roleId, String roleName)
    {
    	String userId = ((MiguUsers)(session.getAttribute(MiguConstants.USER_KEY))).getUserId();
        log.info("修改角色：roleId=" + roleId + ", roleName=" + roleName);
        Map<String, Object> map = new HashMap<String, Object>();
        String result = "-1";
        try
        {
            result = rolesService.editRole(Long.parseLong(roleId), roleName);
        }
        catch (Exception e)
        {
            log.error("Exception:", e);
        }
        if ("-9".equals(result))
        {
            map.put("reCode", -9);
            map.put("reStr", "角色创建失败，该角色名已经存在!");
            log.info("角色创建失败，该角色名已经存在!!");
            //dbLog.logDb(userId, "角色管理修改", "结果：失败；roleId：" + StringUtil.subStringTo300(roleId));
            return map;
        }
        if ("-1".equals(result))
        {
            map.put("reCode", -1);
            map.put("reStr", "修改角色失败");
            log.info("修改角色失败！");
            //dbLog.logDb(userId, "角色管理修改", "结果：失败；roleId：" + StringUtil.subStringTo300(roleId));
            return map;
        }
        map.put("reCode", 100);
        map.put("reStr", "修改角色成功");
        log.info("修改角色成功！");
        return map;
    }
    
    /**
     * 删除角色
     * @author chentao
     * @param request
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/deleteRole", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> deleteRole(HttpServletRequest request, HttpSession session,String roleId)
    {
    	String userId = ((MiguUsers)(session.getAttribute(MiguConstants.USER_KEY))).getUserId();
        log.info("删除角色：roleId=" + roleId);
        Map<String, Object> map = new HashMap<String, Object>();
        String result = "";
        try
        {
            result = rolesService.deleteRole(Long.parseLong(roleId));
        }
        catch (Exception e)
        {
            log.error("Exception:", e);
        }
        if ("-1".equals(result))
        {
            map.put("reCode", -1);
            map.put("reStr", "角色正在使用，不能删除");
            log.info("角色正在使用，不能删除！");
            //dbLog.logDb(userId, "角色管理删除", "结果：失败；roleId：" + StringUtil.subStringTo300(roleId));
            return map;
        }
        if ("-2".equals(result))
        {
            map.put("reCode", -2);
            map.put("reStr", "删除失败");
            log.info("角色删除失败！");
            //dbLog.logDb(userId, "角色管理删除", "结果：失败；roleId：" + StringUtil.subStringTo300(roleId));
            return map;
        }
        map.put("reCode", 100);
        map.put("reStr", "删除成功");
        log.info("角色删除成功！");
        return map;
    }
    
    /**
     * 查询所有菜单
     * @author chentao
     * @param request
     * @param roleId
     * @param response
     * @return
     */
    @RequestMapping(value = "/queryAllFunc", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> queryAllFunc(HttpServletRequest request, String roleId,
        HttpServletResponse response,HttpSession session)
    {
    	String userId = ((MiguUsers)(session.getAttribute(MiguConstants.USER_KEY))).getUserId();
        log.info("根据角色查询对应的所有权限：roleId=" + roleId);
        // 定义返回值
        Map<String, Object> map = new HashMap<String, Object>();
        String funcs = "";
        try
        {
            // 如果有该功能的权限，就到到service层去操作
            List<Map<String, Object>> funcMap = funcService.queryAllFunc(Long.parseLong(roleId),userId);
            ObjectMapper mapper = new ObjectMapper();
            funcs = mapper.writeValueAsString(funcMap);
        }
        catch (Exception e)
        {
            log.error("Exception:", e);
        }
        // 记录用户信息
        map.put("reCode", 100);
        map.put("reStr", funcs);
        log.info("查询菜单：" + funcs);
        return map;
    }
    
    /**
     * 为角色配置权限，保存角色-权限信息
     * @author chentao
     * @param request
     * @param roleId
     * @param funcIds
     * @param response
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/saveRoleFunc", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> saveRoleFunc(HttpSession session,HttpServletRequest request, String roleId, String funcIds,
        HttpServletResponse response)
    {
        log.info("保存角色-权限关系信息：roleId=" + roleId + ", funcIds=" + funcIds);
        String userId = ((MiguUsers)(session.getAttribute(MiguConstants.USER_KEY))).getUserId();
        // 定义返回值
        Map<String, Object> map = new HashMap<String, Object>();
        String result = "-1";
        try
        {
            result = funcService.saveRoleFunc(Long.parseLong(roleId), funcIds);
        }
        catch (Exception e)
        {
            log.error("Exception:", e);
        }
        if ("-1".equals(result))
        {
            map.put("reCode", -1);
            map.put("reStr", "保存失败");
            log.info("保存角色的权限配置失败！");
        }
        // 记录用户信息
        map.put("reCode", 100);
        map.put("reStr", "保存成功");
        log.info("保存角色的权限配置成功！");
        return map;
    }
    
    /**
     * 跳转到人员管理页面
     * @author chentao
     * @param request
     * @return
     */
    @RequestMapping(value = "/userManage", method = RequestMethod.GET)
    public ModelAndView userManage(HttpServletRequest request)
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("userManage");
        return mav;
    }
    
    /**
     * 系统管理员：查询所有用户
     * @author chentao
     * @param request
     * @param userName
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/queryAllUserList", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> queryAllUserList(HttpServletRequest request, String userName,String chaxun, String page,HttpSession session,
        String rows)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        String userId = ((MiguUsers)(session.getAttribute(MiguConstants.USER_KEY))).getUserId();
        int count=0;
        List<MiguUsers> list =null;
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
            
            if (StringUtils.isEmpty(userName))
            {
                userName = null;
            }
            if(StringUtil.isNotEmpty(userName)&&(userName.contains("_")||userName.contains("%")))
            {
                userName=StringUtil.getSpecialParam(userName);
            }
           
            count = usersService.countByExample(userName);
            log.info("查询用户：totle=" + count + "userName=" + userName + ";page=" + page);
            list = usersService.queryAllUserList(userName, Integer.parseInt(page),((MiguUsers)(session.getAttribute(MiguConstants.USER_KEY))).getUserId(), Integer.parseInt(rows));
            
            if(!StringUtils.isEmpty(chaxun)){
                String canshu="";
                if(StringUtils.isEmpty(userName)){
                    canshu="无";
                }else{
                    canshu="userName=" + userName;
                }
            }
           
        }
        catch (Exception e)
        {
            log.error("Exception:", e);
        }
        map.put("total", count);
        map.put("rows", list);
        return map;
    }
    
    /** 增加用户
     * @author chentao
     * @param request
     * @param userName
     * @param userId
     * @param msisdn
     * @param email
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> addUser(HttpServletRequest request, HttpSession session, String userName, String userId,
        String msisdn, String email,String deptId, String startDate, String endDate,String pricingCommittee)
    {
        log.info("增加用户：email=" + email + ";msisdn=" + msisdn + ";userName=" + userName + ";userId=" + userId);
        Map<String, Object> map = new HashMap<String, Object>();
        String user = ((MiguUsers)(session.getAttribute(MiguConstants.USER_KEY))).getUserId();
        String result = null;
        try
        {
            result = usersService.addUser(userName, userId, msisdn, email, deptId, startDate, endDate,pricingCommittee);
        }
        catch (Exception e)
        {
            log.error("Exception:", e);
        }
       
        if ("-1".equals(result))
        {
            map.put("reCode", -1);
            map.put("reStr", "手机号已存在，请重新输入");
            log.info("手机号已存在，请重新输入");
            //dbLog.logDb(user, "人员管理新增", "结果：失败；userId：" + StringUtil.subStringTo300(userId));
            return map;
        }
        if ("9".equals(result))
        {
            map.put("reCode", 9);
            map.put("reStr", "用户编号已存在，请重新输入");
            log.info("用户编号已存在，请重新输入");
            //dbLog.logDb(user, "人员管理新增", "结果：失败；userId：" + StringUtil.subStringTo300(userId));
            return map;
        }
        map.put("reCode", 100);
        map.put("reStr", result);
        return map;
    }
    
    /**
     * 删除用户
     * @author chentao
     * @param request
     * @param userIds
     * @return
     */
    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> deleteUser(HttpServletRequest request,HttpSession session, String userIds)
    {
        log.info("删除用户：userIds=" + userIds);
        Map<String, Object> map = new HashMap<String, Object>();
        String user = ((MiguUsers)(session.getAttribute(MiguConstants.USER_KEY))).getUserId();
        String result = null;
        try
        {
            result = usersService.deleteUser(userIds);
            //dbLog.logDb(user, "人员管理删除", "参数：" + StringUtil.subStringTo300(userIds));
        }
        catch (Exception e)
        {
            log.error("Exception:", e);
        }
        
        if ("0".equals(result))
        {
            map.put("reCode", 100);
            map.put("reStr", "删除成功！");
            log.info("删除成功！");
        }
        else
        {
            map.put("reCode", -1);
            map.put("reStr", "删除失败！");
            log.info("删除失败！");
           // dbLog.logDb(user, "人员管理删除", "结果：失败；userIds：" + StringUtil.subStringTo300(userIds));
        }
        return map;
    }    
    
    /**
     * 修改人员
     * @author chentao
     * @param request
     * @param userId
     * @param userName
     * @param msisdn
     * @param email
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> updateUser(HttpServletRequest request,HttpSession session, String userId, String userName,
        String msisdn, String email, String deptId,String startDate, String endDate,String pricingCommittee)
    {
        log.info("修改用户：email=" + email + ";msisdn=" + msisdn + ";userName=" + userName + ";userId=" + userId);
        Map<String, Object> map = new HashMap<String, Object>();
        String user = ((MiguUsers)(session.getAttribute(MiguConstants.USER_KEY))).getUserId();
        String result = null;
        try
        {
            result = usersService.updateUser(userId, userName, msisdn, email,deptId, startDate, endDate,pricingCommittee);
        }
        catch (Exception e)
        {
            log.error("Exception:", e);
        }
        
        if ("-1".equals(result))
        {
            map.put("reCode", -1);
            map.put("reStr", "手机号已存在，修改失败");
            log.info("手机号已存在，修改失败");
            return map;
        }
        if ("-2".equals(result))
        {
            map.put("reCode", -2);
            map.put("reStr", "不存在该条数据，修改失败");
            log.info("不存在该条数据，修改失败");
            return map;
        }
        map.put("reCode", 100);
        map.put("reStr", result);
        return map;
    }
    
    
    /**
     * 查询所有部门
     * @author chentao
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryAllDep", method = RequestMethod.POST)
    public @ResponseBody List<TMiguDepartments> queryAllDep(HttpServletRequest request,HttpSession session)
    {
        try
        {
            return departmentsService.queryAllDep();
        }
        catch (Exception e)
        {
            log.error("Exception:", e);
        }
        return null;
    }
    
    
    /**
     * 跳转到部门管理页面
     * @author chentao
     * @param request
     * @return
     */
    @RequestMapping(value = "/depManage", method = RequestMethod.GET)
    public ModelAndView depManage(HttpServletRequest request)
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("depManage");
        return mav;
    }
    
    /**
     * 查询所有部门
     * @author chentao
     * @param request
     * @param deptName
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/queryAllDepList", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> queryAllDepList(HttpServletRequest request, String deptName, String page, HttpSession session, String rows)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        String userId = ((MiguUsers)(session.getAttribute(MiguConstants.USER_KEY))).getUserId();
        int count=0;
        List<TMiguDepartments> list =null;
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
            
            if (StringUtils.isEmpty(deptName))
            {
            	deptName = null;
            }
            if(StringUtil.isNotEmpty(deptName)&&(deptName.contains("_")||deptName.contains("%")))
            {
            	deptName=StringUtil.getSpecialParam(deptName);
            }
           
            count = departmentsService.countByExample(deptName);
            log.info("查询部门：totle=" + count + ";deptName=" + deptName + ";page=" + page);
            list = departmentsService.queryAllDepList(deptName, Integer.parseInt(page),((MiguUsers)(session.getAttribute(MiguConstants.USER_KEY))).getUserId(), Integer.parseInt(rows));
        }
        catch (Exception e)
        {
            log.error("Exception:", e);
        }
        map.put("total", count);
        map.put("rows", list);
        return map;
    }
    
    
    /** 增加部门
     * @author chentao
     * @param request
     * @param deptCode
     * @param deptName
     * @param deptDescribe
     * @return
     */
    @RequestMapping(value = "/addDep", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> addDep(HttpServletRequest request, HttpSession session, String deptCode, String deptName,
        String deptDescribe)
    {
        log.info("增加部门：deptCode=" + deptCode + ";deptName=" + deptName + ";deptDescribe=" + deptDescribe);
        Map<String, Object> map = new HashMap<String, Object>();
        String userId = ((MiguUsers)(session.getAttribute(MiguConstants.USER_KEY))).getUserId();
        String result = null;
        try
        {
            result = departmentsService.addDep(deptCode, deptName, deptDescribe);
        }
        catch (Exception e)
        {
            log.error("Exception:", e);
        }
        if ("9".equals(result))
        {
            map.put("reCode", 9);
            map.put("reStr", "部门编号已存在，请重新输入!");
            log.info("部门编号已存在，请重新输入!");
            return map;
        }
        map.put("reCode", 100);
        map.put("reStr", result);
        return map;
    }
    
    /**
     * 删除部门
     * @author chentao
     * @param request
     * @return
     */
    @RequestMapping(value = "/deleteDep", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> deleteDep(HttpServletRequest request,HttpSession session, String deptCode)
    {
        log.info("删除部门：deptCode=" + deptCode);
        Map<String, Object> map = new HashMap<String, Object>();
        String user = ((MiguUsers)(session.getAttribute(MiguConstants.USER_KEY))).getUserId();
        List<MiguUsers> list =null;
        String result = null;
        try
        {
        	list = usersService.queryDeptUserList(deptCode);
        	if(list.size()==0){
        		result = departmentsService.deleteDep(deptCode);
        	}else{
        		result = "-1";
        	}
            
        }
        catch (Exception e)
        {
            log.error("Exception:", e);
        }
        
        if ("0".equals(result))
        {
            map.put("reCode", 100);
            map.put("reStr", "删除成功!");
            log.info("删除成功!");
        }
        else if("-1".equals(result))
        {
        	map.put("reCode", -1);
            map.put("reStr", "删除失败,请先删除该部门下用户!");
            log.info("删除失败,请先删除该部门下用户!");
        }
        else
        {
            map.put("reCode", -1);
            map.put("reStr", "删除失败!");
            log.info("删除失败!");
        }
        return map;
    }
    
    /**
     * 修改部门
     * @author chentao
     * @param request
     * @param deptCode
     * @param deptName
     * @param deptDescribe
     * @return
     */
    @RequestMapping(value = "/updateDep", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> updateDep(HttpServletRequest request,HttpSession session, String deptCode, String deptName,
        String deptDescribe)
    {
        log.info("修改部门：deptCode=" + deptCode + ";deptName=" + deptName + ";deptDescribe=" + deptDescribe);
        Map<String, Object> map = new HashMap<String, Object>();
        String user = ((MiguUsers)(session.getAttribute(MiguConstants.USER_KEY))).getUserId();
        String result = null;
        try
        {
            result = departmentsService.updateDep(deptCode, deptName, deptDescribe);
        }
        catch (Exception e)
        {
            log.error("Exception:", e);
        }
        
        if ("-2".equals(result))
        {
            map.put("reCode", -2);
            map.put("reStr", "不存在该条数据，修改失败!");
            log.info("不存在该条数据，修改失败!");
            return map;
        }
        map.put("reCode", 100);
        map.put("reStr", result);
        return map;
    }
    
    
    /**
     * 重置密码
     * @author chentao
     * @param request
     * @param userId
     * @return
     */
    @RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> resetPwd(HttpServletRequest request,HttpSession session, String userId)
    {
        log.info("重置密码：userId=" + userId);
        Map<String, Object> map = new HashMap<String, Object>();
        String user = ((MiguUsers)(session.getAttribute(MiguConstants.USER_KEY))).getUserId();
        String result = null;
        try
        {
            result = usersService.resetPwd(userId);
        }
        catch (Exception e)
        {
            log.error("Exception:", e);
        }
        map.put("reCode", 100);
        map.put("reStr", result);
        return map;
    }
    
    /**
     * 修改密码
     * @author chentao
     * @param request
     * @param password
     * @param old_pwd
     * @param userId
     * @param response
     * @param session
     * @return
     * @see [类、类#方法、类#成员]
     */
//    @RequestMapping(value = "/modUserPassword", method = RequestMethod.POST)
//    public @ResponseBody Map<String, Object> modUserPassword(HttpServletRequest request, String password,
//        String old_pwd,String userId, HttpServletResponse response, HttpSession session)
//    {
//        Map<String, Object> map = new HashMap<String, Object>();
//        MiguUsers user = (MiguUsers)session.getAttribute(MiguConstants.USER_KEY);
//        if(StringUtil.isEmpty(userId))
//        {
//            userId = user.getUserId();
//        }
//        log.info("修改密码：password=" + password + ";old_pwd=" + old_pwd + ";userId=" + userId);
//        String result = null;
//        try
//        {
//           // result = this.usersService.modUserPassword(password, old_pwd, user==null?userId:user.getUserId());
//            result = this.usersService.modUserPassword(password, old_pwd, userId);
//            //dbLog.logDb(Long.parseLong(userId), "密码修改", "参数：" + StringUtil.subStringTo300(userId));
//        }
//        catch (Exception e)
//        {
//            log.error("Exception:", e);
//        }
//        if ("0".equals(result))
//        {
//            map.put("reCode", -1);
//            map.put("reStr", "修改失败");
//            log.info("修改密码失败！");
//            //dbLog.logDb(Long.parseLong(userId), "密码修改", "结果：失败；userId：" + StringUtil.subStringTo300(userId));
//        }
//        else if ("9".equals(result))
//        {
//            map.put("reCode", 9);
//            map.put("reStr", "原密码输入错误，修改失败");
//            log.info("原密码输入错误，修改失败！");
//            //dbLog.logDb(Long.parseLong(userId), "密码修改", "结果：失败；userId：" + StringUtil.subStringTo300(userId));
//        }
//        else
//        {
//            map.put("reCode", 100);
//            map.put("reStr", "修改成功");
//            log.info("修改密码成功！");
//            dbLog.logDb(userId, "密码修改", "结果：成功；主键：userId=" + StringUtil.subStringTo300(userId));
//        }
//        return map;
//    }
//    
    /**
     * 查询用户已有角色
     * @param request
     * @param userId
     * @param response
     * @return
     */
    @RequestMapping(value = "/queryRoleUser", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> queryRoleUser(HttpServletRequest request, String userId,
        HttpServletResponse response,HttpSession session)
    {
        log.info("用户已有角色：userId=" + userId);
        // 定义返回值
        Map<String, Object> map = new HashMap<String, Object>();
        String uId = ((MiguUsers)(session.getAttribute(MiguConstants.USER_KEY))).getUserId();
        // 判断参数
        if (StringUtils.isEmpty(userId))
        {
            map.put("reCode", 1);
            map.put("reStr", "你操作失败，参数错误");
            return map;
        }
        MiguRoles role = null;
        try
        {
            role = this.rolesService.queryUserRoleByUserId(userId);
            //dbLog.logDb(uId, "查询用户已有角色", "参数：" + StringUtil.subStringTo300(userId));
        }
        catch (Exception e)
        {
            log.error("Exception:", e);
        }
        if (role != null)
        {
            // 记录用户信息
            map.put("reCode", 100);
            map.put("reStr", role.getRoleName());
            map.put("reRoleId", role.getRoleId());
            return map;
        }
        else
        {
            map.put("reCode", 2);
            map.put("reStr", "没有查询到对应的数据");
            log.info("没有查询到对应的数据！");
            return map;
        }
    }
    
    /**
     * 根据用户类型查询角色
     * @author chentao
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryRoleByUserType", method = RequestMethod.POST)
    public @ResponseBody List<MiguRoles> queryRoleByUserType(HttpServletRequest request,HttpSession session)
    {
        log.info("类型查询角色：");
        String uId = ((MiguUsers)(session.getAttribute(MiguConstants.USER_KEY))).getUserId();
        try
        {
            //dbLog.logDb(uId, "查询用户已有角色", "参数：" + StringUtil.subStringTo300(rolesService.queryRoleByUserType()));
            return rolesService.queryRoleByUserType();
        }
        catch (Exception e)
        {
            log.error("Exception:", e);
        }
        return null;
    }
    
    /**
     * 
     * 保存用户角色
     * @author chentao
     * @param request
     * @param userId
     * @param type
     * @param roleId
     * @param response
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/saveUserRole", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> saveUserRole(HttpServletRequest request, String userId, String type,
        String roleId, HttpServletResponse response,HttpSession session)
    {
        log.info("保存用户角色：roleId=" + roleId + ";userId=" + userId);
        Map<String, Object> map = new HashMap<String, Object>();
        String uId = ((MiguUsers)(session.getAttribute(MiguConstants.USER_KEY))).getUserId();
        // 对参数进行判断
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(roleId))
        {
            map.put("reCode", 1);
            map.put("reStr", "操作失败，请重试");
            return map;
        }
        String result = "-1";
        try
        {
            //dbLog.logDb(uId, "保存用户角色", "参数：" + StringUtil.subStringTo300(roleId));
            result = rolesService.saveUserRole(userId, Long.parseLong(roleId), type);
        }
        catch (Exception e)
        {
            log.error("Exception:", e);
        }
        if (!"0".equals(result))
        {
            map.put("reCode", -1);
            map.put("reStr", "角色赋值失败");
            log.info("角色赋值失败！");
            //dbLog.logDb(uId, "用户角色分配", "结果：失败；userId：" + StringUtil.subStringTo300(userId));
            return map;
        }
        map.put("reCode", 100);
        map.put("reStr", "角色赋值成功");
        log.info("角色赋值成功！");
        return map;
    }
    
    /**
     * 跳转到修改密码jsp
     * @author chentao
     * @param request
     * @param response
     * @param userId
     * @return
     * @see [类、类#方法、类#成员]
     */
//    @RequestMapping(value = "/pwdModify")
//    public ModelAndView pwdModify(HttpServletRequest request, HttpServletResponse response,String userId,HttpSession session)
//    {
//        ModelAndView mav = new ModelAndView();
//        Map<String, Object> map=new HashMap<String, Object>();        
//        //dbLog.logDb(Long.parseLong(userId==null?((MiguUsers)(session.getAttribute(MiguConstants.USER_KEY))).getUserId():userId), "重置密码", "参数：" + StringUtil.subStringTo300(userId==null?((MiguUsers)(session.getAttribute(MiguConstants.USER_KEY))).getUserId():userId));
//        map.put("UserId", userId);
//        mav.setViewName("personalSetting");
//        mav.addObject("personalSetting", userId);
//        return mav;
//    } 
    
    /**
     * 查询该部门下所有人员
     * @author chentao
     * @param request
     * @return
     */
    @RequestMapping(value = "/queryDepPerson", method = RequestMethod.POST)
    public @ResponseBody List<MiguUsers> queryDepPerson(HttpServletRequest request,HttpSession session,String projectDeptId)
    {
        try
        {
            return usersService.queryDeptUserList(projectDeptId);
        }
        catch (Exception e)
        {
            log.error("Exception:", e);
        }
        return null;
    }
}
