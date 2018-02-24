package cn.migu.income.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
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
import cn.migu.income.pojo.TMiguIncomeCategories;
import cn.migu.income.pojo.TMiguProjectBase;
import cn.migu.income.service.IMiguProjectManageService;
import cn.migu.income.service.impl.MiguOptLogger;
import cn.migu.income.util.GetFileSize;
import cn.migu.income.util.MiguConstants;
import cn.migu.income.util.PropValue;
import cn.migu.income.util.StringUtil;
import cn.migu.income.util.TxtFileOperation;

/**
 * 项目管理
 * @author chentao
 *
 */
@Controller
@RequestMapping("/project")
public class ProjectManageController
{
    final static Logger log = LoggerFactory.getLogger(ProjectManageController.class);
    
    @Autowired
    private MiguOptLogger dbLog;
    
    @Autowired
    private IMiguProjectManageService projectManageService;
    
    /**
     * 项目管理页面
     * @author chentao
     */
    @RequestMapping(value = "/projectManage")
    public ModelAndView projectManage(HttpServletRequest request)
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("projectManage");
        return mav;
    }
    
    /**
     * 
     * 获取收入小类下拉框
     * @author chentao
     * @param session
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/queryIncomeSectionId", method = RequestMethod.POST)
    public @ResponseBody List<TMiguIncomeCategories> queryIncomeSectionId(HttpSession session,String incomeParentId)
    {
        List<TMiguIncomeCategories> list = new ArrayList<TMiguIncomeCategories>();
        try
        {
            list = projectManageService.queryIncomeSectionId(incomeParentId);
        }
        catch (Exception e)
        {
            log.error("Exception:", e);
        }
        return list;
    }
    
    /**
     * 
     * 获取收入大类下拉框
     * @author chentao
     * @param session
     * @return
     * @see [类、类#方法、类#成员]
     */
    @RequestMapping(value = "/queryIncomeClassId", method = RequestMethod.POST)
    public @ResponseBody List<TMiguIncomeCategories> queryIncomeClassId(HttpSession session)
    {
        List<TMiguIncomeCategories> list = new ArrayList<TMiguIncomeCategories>();
        try
        {
            list = projectManageService.queryIncomeClassId();
        }
        catch (Exception e)
        {
            log.error("Exception:", e);
        }
        return list;
    }
    
    /**
     * 项目查询 chentao
     * @param request
     * @param session
     * @param q_projectId
     * @param q_projectName
     * @param q_incomeClassId
     * @param q_incomeSectionId
     * @param q_projectUserName
     * @param q_projectDeptId
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/queryAllProject", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> queryAllProject(HttpServletRequest request,HttpSession session, 
        String q_projectId,String q_projectName,String q_incomeClassId,String q_incomeSectionId,String q_projectUserName,
        String q_projectDeptId,String page,String rows)
    {
    	MiguUsers user = (MiguUsers)(session.getAttribute(MiguConstants.USER_KEY));
        Map<String, Object> map = new HashMap<String, Object>();
        int count=0;
        List<TMiguProjectBase> list =null;
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
            if (StringUtils.isEmpty(q_projectName))
            {
            	q_projectName = null;
            }
            if (StringUtils.isEmpty(q_incomeClassId))
            {
            	q_incomeClassId = null;
            }
            if (StringUtils.isEmpty(q_incomeSectionId))
            {
            	q_incomeSectionId = null;
            }
            if (StringUtils.isEmpty(q_projectUserName))
            {
            	q_projectUserName = null;
            }
            if (StringUtils.isEmpty(q_projectDeptId))
            {
            	q_projectDeptId = null;
            }
            if(StringUtil.isNotEmpty(q_projectId)&&(q_projectId.contains("_")||q_projectId.contains("%")))
            {
            	q_projectId=StringUtil.getSpecialParam(q_projectId);
            }
            if(StringUtil.isNotEmpty(q_projectName)&&(q_projectName.contains("_")||q_projectName.contains("%")))
            {
            	q_projectName=StringUtil.getSpecialParam(q_projectName);
            }
            if(StringUtil.isNotEmpty(q_projectUserName)&&(q_projectUserName.contains("_")||q_projectUserName.contains("%")))
            {
            	q_projectUserName=StringUtil.getSpecialParam(q_projectUserName);
            }
            count = projectManageService.queryTotal(q_projectId, q_projectName, q_incomeClassId,
            		 q_incomeSectionId, q_projectUserName, q_projectDeptId,user);
            list = projectManageService.queryAllProject(q_projectId, q_projectName, q_incomeClassId,
           		 q_incomeSectionId, q_projectUserName, q_projectDeptId,Integer.parseInt(page), Integer.parseInt(rows),user);
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
     * 提交项目
     * @param request
     * @param session
     * @param response
     * @return
     */
    @RequestMapping(value = "/submitProject", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> submitProject(HttpServletRequest request,HttpSession session,HttpServletResponse response)
    {
    	MiguUsers user = (MiguUsers)(session.getAttribute(MiguConstants.USER_KEY));
        String result="";
        Map<String, Object> map = new HashMap<String, Object>();
        TMiguProjectBase tMiguProjectBase = new TMiguProjectBase();
        String projectId = projectManageService.getProjectId();//获取项目号 SEQ_MIGU_PROJECT
        tMiguProjectBase.setProjectId(projectId);
        tMiguProjectBase.setProjectUserId(user.getUserId());
        tMiguProjectBase.setProjectDeptId(user.getDeptId());
        tMiguProjectBase.setProjectApplyUserId(user.getUserId());
        try
        {
        	request.setCharacterEncoding("UTF-8");
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<?> items = upload.parseRequest(request);
            Iterator<?> itr = items.iterator();
            File backupPath = new File(PropValue.getPropValue(MiguConstants.FILES_LOCATION)+projectId);
            tMiguProjectBase.setRequisitionFilepath(backupPath.getAbsolutePath());
            tMiguProjectBase.setOaScreenFilepath(backupPath.getAbsolutePath());
            tMiguProjectBase.setProFileFilepath(backupPath.getAbsolutePath());
            tMiguProjectBase.setUpdateDate(StringUtil.getCurrDateStrContainHMS());
            tMiguProjectBase.setCreateDate(StringUtil.getCurrDateStrContainHMS());
            if(!backupPath.exists()){
        		backupPath.mkdirs();
        	}
            while (itr.hasNext())
            {
                FileItem item = (FileItem)itr.next();
                if (!item.isFormField())                
                {
                    if (item.getName() != null && !"".equals(item.getName()))
                    {
                    	File tempFile = new File(item.getName());
                        File file = new File(backupPath, tempFile.getName());
                        if(GetFileSize.checkFileSize(item.getInputStream())){
                        	map.put("reCode", 9);
                            map.put("reStr", "操作失败，"+item.getName()+"文件过大，上传限制为20M！");
                            log.info("操作失败，"+item.getName()+"文件过大，上传限制为20M！");
                            return map;
                        }else{
                        	item.write(file);
                        }
                        String setMethodName =
                                "set" + item.getFieldName().split("_")[1].substring(0, 1).toUpperCase() + item.getFieldName().split("_")[1].substring(1)+"name";
                            Class<? extends TMiguProjectBase> tCls = tMiguProjectBase.getClass();
                            Method setMethod = tCls.getMethod(setMethodName,String.class);
                            setMethod.invoke(tMiguProjectBase, file.getName());
                    }
                }else{
                	String setMethodName =
                            "set" + item.getFieldName().split("_")[1].substring(0, 1).toUpperCase() + item.getFieldName().split("_")[1].substring(1);
                        Class<? extends TMiguProjectBase> tCls = tMiguProjectBase.getClass();
                        Method setMethod = tCls.getMethod(setMethodName,String.class);
                        setMethod.invoke(tMiguProjectBase, item.getString("utf-8"));
                }
            }
            result = projectManageService.saveProject(tMiguProjectBase);
            map.put("reCode", result);
            if(result.equals("9")){
            	map.put("reStr", "操作失败,数据库新增失败！");
            	log.info("操作失败,数据库新增失败！");
            }
        }
        catch (Exception e)
        {
            log.info("提交项目异常:", e);
            e.printStackTrace();
        }
        return map;
    }
    
    /**
     * 更新项目
     * @param request
     * @param session
     * @param response
     * @return
     */
    @RequestMapping(value = "/doUpdateProject", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> doUpdateProject(HttpServletRequest request,HttpSession session,HttpServletResponse response)
    {
        String result="";
        Map<String, Object> map = new HashMap<String, Object>();
        TMiguProjectBase tMiguProjectBase = new TMiguProjectBase();
        try
        {
        	request.setCharacterEncoding("UTF-8");
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<?> items = upload.parseRequest(request);
            String projectId = this.parseForm(items);
            File backupPath = new File(PropValue.getPropValue(MiguConstants.FILES_LOCATION)+projectId);
            if(!backupPath.exists()){
        		backupPath.mkdirs();
        	}
            Iterator<?> itr = items.iterator();
            tMiguProjectBase.setUpdateDate(StringUtil.getCurrDateStrContainHMS());
            while (itr.hasNext())
            {
                FileItem item = (FileItem)itr.next();
                if (!item.isFormField())                
                {
                    if (item.getName() != null && !"".equals(item.getName()))
                    {
                    	File tempFile = new File(item.getName());
                        File file = new File(backupPath, tempFile.getName());
                        if(GetFileSize.checkFileSize(item.getInputStream())){
                        	map.put("reCode", 9);
                            map.put("reStr", "操作失败，"+item.getName()+"文件过大，上传限制为20M！");
                            log.info("操作失败，"+item.getName()+"文件过大，上传限制为20M！");
                            return map;
                        }else{
                        	item.write(file);
                        }
                        String setMethodName =
                                "set" + item.getFieldName().split("_")[1].substring(0, 1).toUpperCase() + item.getFieldName().split("_")[1].substring(1)+"name";
                            Class<? extends TMiguProjectBase> tCls = tMiguProjectBase.getClass();
                            Method setMethod = tCls.getMethod(setMethodName,String.class);
                            setMethod.invoke(tMiguProjectBase, file.getName());
                    }
                }else{
                	System.out.println(item.getString("utf-8"));
                	String setMethodName =
                            "set" + item.getFieldName().split("_")[1].substring(0, 1).toUpperCase() + item.getFieldName().split("_")[1].substring(1);
                        Class<? extends TMiguProjectBase> tCls = tMiguProjectBase.getClass();
                        Method setMethod = tCls.getMethod(setMethodName,String.class);
                        setMethod.invoke(tMiguProjectBase, item.getString("utf-8"));
                }
            }
            result = projectManageService.doUpdateProject(tMiguProjectBase);
            map.put("reCode", result);
            if(result.equals("9")){
            	map.put("reStr", "操作失败,数据库修改失败！");
            	log.info("操作失败,数据库修改失败！");
            }
        }
        catch (Exception e)
        {
            log.info("提交项目异常:", e);
            e.printStackTrace();
        }
        return map;
    }
    
    /**
     * 删除项目
     * @author chentao
     * @param request
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/dellProject", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> dellProject(HttpServletRequest request,HttpSession session, String projectId, String projectName)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        MiguUsers user = ((MiguUsers)(session.getAttribute(MiguConstants.USER_KEY)));
        Map<String, Object> result = new HashMap<String, Object>();
        try
        {
            result = projectManageService.dellProject(projectId);
        }
        catch (Exception e)
        {
            log.error("Exception:", e);
        }
        
        if ("0".equals(result.get("code")))
        {
            map.put("reCode", 100);
            map.put("reStr", "删除成功！");
            dbLog.logDb(user.getUserId(), "项目删除", "结果：成功；操作人：" + user.getUserName());
            log.info("项目删除成功，项目编号:"+projectId+",项目名称:"+projectName+",操作人："+user.getUserName()+",操作时间"+StringUtil.getCurrDateStrContainHMS());
        }
        else
        {
            map.put("reCode", -1);
            map.put("reStr", result.get("reason"));
            log.info("项目删除失败，项目编号:"+projectId+",项目名称:"+projectName+",操作人："+user.getUserName()+",操作时间"+StringUtil.getCurrDateStrContainHMS());
        }
        return map;
    }
    
    /**
     * 下载项目附件
     * @param request
     * @param response
     * @param test
     * @return
     * @throws UnsupportedEncodingException
     */
    
    @RequestMapping(value = "/downLoad", method = RequestMethod.POST)
    public @ResponseBody String downLoad(HttpServletRequest request, HttpSession session, HttpServletResponse response,String filename,String projectId)
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
    
    @RequestMapping(value = "/queryAllProjectByUser", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> queryAllProjectByUser(String projectId,String projectName,HttpServletRequest request,HttpSession session,String page,String rows)
    {
    	  MiguUsers user = (MiguUsers)(session.getAttribute(MiguConstants.USER_KEY));
    	  return projectManageService.queryAllProjectByUser(projectId,projectName,user.getUserId(), page, rows);
    }
    
    private String parseForm(List<?> items) throws Exception {
    	String projectId = "";
		Iterator<?> itr = items.iterator();
		while (itr.hasNext()) {
			FileItem item = (FileItem) itr.next();
			if (item.isFormField()) {
				if((item.getFieldName().equals("update_projectId"))){
					projectId = item.getString("utf-8");
				}
			} 
		}
		return projectId;
	}
}
