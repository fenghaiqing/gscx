package cn.migu.income.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.migu.income.dao.MiguFuncMapper;
import cn.migu.income.dao.MiguFuncRoleMapper;
import cn.migu.income.dao.MiguUserRoleMapper;
import cn.migu.income.pojo.MiguFunc;
import cn.migu.income.pojo.MiguFunc.ComparatorMiguFunc;
import cn.migu.income.service.IMiguFuncService;
import cn.migu.income.util.StringUtil;

/**
 * 
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  guanyuzhuang
 * @version  [版本号, 2015年12月30日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service
public class MiguFuncServiceImpl implements IMiguFuncService
{
    @Autowired
    private MiguFuncMapper funcMapper;
    
    @Autowired
    private MiguUserRoleMapper userRoleMapper;
    
    @Autowired
    private MiguFuncRoleMapper funcRoleMapper;
    
    @Override
    public List<MiguFunc> getFuncListByUserId(String userId)
        throws Exception
    {
        // 检索userId对应的roleId,如果roleId为【0】则检索所有的菜单显示，否则按照用户分配的角色检索菜单
        String roleId = userRoleMapper.queryUserRoleDataByUserId(userId);
        List<MiguFunc> funcList = null;
        if (!StringUtil.isEmpty(roleId) && "0".equals(roleId))
        {
            funcList = funcMapper.queryAllFuncByMenu(Long.valueOf(roleId));
        }
        else
        {
            funcList = funcMapper.getFuncListByUserId(userId);
        }
        List<Long> funcIdList = new ArrayList<Long>();
        Set<Long> funcPidSet = new HashSet<Long>();
        for (MiguFunc mf : funcList)
        {
            funcIdList.add(mf.getFuncId());
            funcPidSet.add(mf.getFuncPid());
        }
        for (long pid : funcPidSet)
        {
            if (pid != 0 && !funcIdList.contains(pid))
            {
                funcList.add(funcMapper.queryFuncByIdForTree(pid));
            }
        }
        for (MiguFunc mf : funcList)
        {
            funcIdList.add(mf.getFuncId());
            funcPidSet.add(mf.getFuncPid());
        }
        for (long pid : funcPidSet)
        {
            if (pid != 0 && !funcIdList.contains(pid))
            {
                funcList.add(funcMapper.queryFuncByIdForTree(pid));
            }
        }
        ComparatorMiguFunc comparator = new ComparatorMiguFunc();
        Collections.sort(funcList, comparator);
        return funcList;
    }
    
    @Override
    public List<Map<String, Object>> queryAllFunc(long roleId,String userId)
        throws Exception
    {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<MiguFunc> funcList = funcMapper.queryAllFunc(roleId);//查询一级菜单
        for (MiguFunc manageFunc : funcList)
        {
        	Map<String, Object> map = new HashMap<String, Object>();
            long funcId = manageFunc.getFuncId();
            // 对参数进行赋值
            map.put("id", funcId);
            map.put("text", manageFunc.getFuncName());
            map.put("state", "closed");
            if (manageFunc.getFuncIsIframe() == null || manageFunc.getFuncIsIframe() != 1)
            {
                map.put("checked", false);
            }
            else
            {
                map.put("checked", true);
            }
            
            // 获取二级菜单
            List<Map<String, Object>> subList = this.querySubFun(String.valueOf(funcId), String.valueOf(roleId));
            
            // 判断二级菜单是否存在
            if (subList.size() > 0)
            {
                map.put("children", subList);
            }
            // 保存到list里面
            list.add(map);
        }
        return list;
    }
    
    @Transactional
    @Override
    public String saveRoleFunc(long roleId, String funcList)
        throws Exception
    {
        //首先删除角色已有功能
        funcRoleMapper.deleteRoleFuncByRoleId(roleId);
        //再保存角色和功能关系
        String[] funcIds = funcList.split(",");
        if (funcIds.length == 0)
        {
            return "0";
        }
        List<Map<String, Long>> list = new ArrayList<Map<String, Long>>();
        for (String funcId : funcIds)
        {
            Map<String, Long> map = new HashMap<String, Long>();
            map.put("roleId", roleId);
            map.put("funcId", Long.valueOf(funcId));
            list.add(map);
            
        }
        int isSave = funcRoleMapper.saveRoleFunc(list);
        
        if (isSave <= 0)
        {
            return "-1";
        }
        return "0";
    }
    
    @Override
    public List<Map<String, Object>> querySubmenu(String pid,String userId,List<MiguFunc> funcList)
    {
    	String roleId = "";
    	try {
			roleId = userRoleMapper.queryUserRoleDataByUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
        List<Map<String,Object>> partreeList  =new ArrayList<Map<String,Object>>();
        List<MiguFunc> list = new ArrayList<MiguFunc>();//子菜单
        
        for (MiguFunc mf : funcList)
        {
        	if(String.valueOf(mf.getFuncPid()).equals(pid)){
        		list.add(mf);
        	}
        }
        
        for (int i = 0; i < list.size(); i++) {
        	List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>(); 
            Map<String, Object> map = null;  
            MiguFunc miguFunc = (MiguFunc) list.get(i);  
            
            map = new HashMap<String, Object>();  
            map.put("id", miguFunc.getFuncId());
            map.put("text",miguFunc.getFuncName());
            map.put("bossId", miguFunc.getFuncId());
            map.put("iconCls", miguFunc.getFuncIconClass());
            map.put("url", miguFunc.getFuncPageSrc());
            map.put("funcIframe", true);
            
            if(roleId.equals("0"))
            {
            	for (MiguFunc mf : funcList)
                {
            		
                	if(String.valueOf(mf.getFuncPid()).equals(String.valueOf(miguFunc.getFuncId()))){
                		Map<String, Object> childmap =new HashMap<String, Object>();  
                		childmap.put("id", mf.getFuncId());
                		childmap.put("text",mf.getFuncName());
                		childmap.put("bossId", mf.getFuncId());
                		childmap.put("iconCls", mf.getFuncIconClass());
                		childmap.put("url", mf.getFuncPageSrc());
                		childmap.put("funcIframe", true);
                        if (map != null)  
                        	childList.add(childmap);  
                	}
                }
            	if(childList!=null&&childList.size()>0){
                	map.put("state", "closed");
                    map.put("funcIframe", false);
                    map.put("children", childList);
                }
            }
            else
            {
            	List<Map<String,Object>> childtreeList = queryTreeChildren(String.valueOf(miguFunc.getFuncId()),userId);
                if(childtreeList!=null&&childtreeList.size()>0){
                	map.put("state", "closed");
                    map.put("funcIframe", false);
                    map.put("children", childtreeList);
                }
            }
            
            
            
            if (map != null)  
                partreeList.add(map);  
        }
        return partreeList;
    }
    
    private  List<Map<String, Object>> queryTreeChildren(String id,String userId) 
    {
        List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();  
        List<MiguFunc> list = funcMapper.querySubmenu(id,userId);//三级菜单
        for (int j = 0; j < list.size(); j++) {  
            Map<String, Object> map = null;  
            MiguFunc miguFunc = (MiguFunc) list.get(j);  
            map = new HashMap<String, Object>();  
            map.put("id", miguFunc.getFuncId());
            map.put("text",miguFunc.getFuncName());
            map.put("bossId", miguFunc.getFuncId());
            map.put("iconCls", miguFunc.getFuncIconClass());
            map.put("url", miguFunc.getFuncPageSrc());
            map.put("funcIframe", true);
            if (map != null)  
                childList.add(map);  
        }  
        return childList;  
    }

    public List<Map<String, Object>> querySubFun(String pid,String roleId)
    {
        List<Map<String,Object>> partreeList  =new ArrayList<Map<String,Object>>();
        List<MiguFunc> list = funcMapper.querySubFun(pid,roleId);//子菜单
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = null;  
            MiguFunc miguFunc = (MiguFunc) list.get(i);  
            
            map = new HashMap<String, Object>();  
            map.put("id", miguFunc.getFuncId());
            map.put("text", miguFunc.getFuncName());
            if (miguFunc.getFuncIsIframe() == null || miguFunc.getFuncIsIframe() != 1)
            {
                map.put("checked", false);
            }
            else
            {
                map.put("checked", true);
            }
            
            
            List<Map<String,Object>> childtreeList = queryTreeFunChildren(String.valueOf(miguFunc.getFuncId()),roleId);
            if(childtreeList!=null&&childtreeList.size()>0){
                map.put("state", "closed");
                map.put("children", childtreeList);
                map.put("state", "closed");
            }
            
            if (map != null)  
                partreeList.add(map);  
        }
        return partreeList;
    }
    
    private  List<Map<String, Object>> queryTreeFunChildren(String id,String roleId) 
    {
        List<Map<String, Object>> childList = new ArrayList<Map<String, Object>>();  
        List<MiguFunc> list = funcMapper.querySubFun(id,roleId);//三级菜单
        for (int j = 0; j < list.size(); j++) {  
            Map<String, Object> map = null;  
            MiguFunc miguFunc = (MiguFunc) list.get(j);  
            map = new HashMap<String, Object>();  
            map.put("id", miguFunc.getFuncId());
            map.put("text", miguFunc.getFuncName());
            if (miguFunc.getFuncIsIframe() == null || miguFunc.getFuncIsIframe() != 1)
            {
                map.put("checked", false);
            }
            else
            {
                map.put("checked", true);
            }
            if (map != null)  
                childList.add(map);  
        }  
        return childList;  
    }
    
}
