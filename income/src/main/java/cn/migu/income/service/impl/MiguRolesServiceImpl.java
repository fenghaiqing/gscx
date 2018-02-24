package cn.migu.income.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.migu.income.dao.MiguRolesMapper;
import cn.migu.income.dao.MiguUserRoleMapper;
import cn.migu.income.pojo.MiguRoles;
import cn.migu.income.pojo.MiguUserRole;
import cn.migu.income.service.IMiguRolesService;

@Service
public class MiguRolesServiceImpl implements IMiguRolesService
{
    final static Logger log = LoggerFactory.getLogger(MiguRolesServiceImpl.class);
    
    @Autowired
    private MiguRolesMapper rolesMapper;
    
    @Autowired
    private MiguUserRoleMapper userRoleMapper;
    
    @Override
    public List<MiguRoles> queryAllRoles(String roleName, String page, String row)
        throws Exception
    {
        Map<String, String> param = new HashMap<String, String>();
        param.put("roleName", roleName);
        param.put("curPage", page);
        param.put("pageSize", row);
        return rolesMapper.queryAllRoles(param);
    }
    
    @Override
    public String addRole(String roleName)
        throws Exception
    {
        MiguRoles role = new MiguRoles();
        role.setRoleName(roleName);
        role.setIsAdmin((short)0);
        MiguRoles miguRoles = new MiguRoles();
        miguRoles = rolesMapper.selectOne(role);
        if(miguRoles!=null)
        {
            return "-9";
        }
        int result = rolesMapper.addRole(role);
        return String.valueOf(result);
    }
    
    @Override
    public String deleteRole(long roleId)
        throws Exception
    {
        // 首先查询角色是否在使用
        int relUserRoleCount = userRoleMapper.queryUsedUserRoleByRoleId(roleId);
        if (relUserRoleCount > 0)
        {
            log.info("角色正在使用...");
            return "-1";
        }
        
        // 删除角色
        int delete = rolesMapper.deleteRoles(roleId);
        if (delete <= 0)
        {
            return "-2";
        }
        return "0";
    }
    
    @Override
    public String editRole(long roleId, String roleName)
        throws Exception
    {
        MiguRoles role = new MiguRoles();
        role.setRoleId(roleId);
        role.setRoleName(roleName);
        MiguRoles miguRoles = new MiguRoles();
        miguRoles = rolesMapper.selectOne(role);
        if(miguRoles!=null)
        {
            if(miguRoles.getRoleId()!=roleId){
                return "-9";
            }
            
        }
        int isUpdated = rolesMapper.updateByPrimaryKeySelective(role);
        if (isUpdated <= 0)
        {
            return "-1";
        }
        return "0";
    }
    
    @Override
    public MiguRoles queryUserRoleByUserId(String userId)
        throws Exception
    {
        List<MiguRoles> list = rolesMapper.queryUserRoleByUserId(userId);
        if (list != null && list.size() > 0)
        {
            return list.get(0);
        }
        return null;
    }
    
    /**
     * 根据用户类型查询角色
     */
    @Override
    public List<MiguRoles> queryRoleByUserType()
        throws Exception
    {
        return rolesMapper.queryRoleByUserType();
    }
    
    /**
     * 保存用户角色
     */
    @Override
    public String saveUserRole(String userId, long roleId, String type)
        throws Exception
    {
        if ("delete".equals(type))
        {//删除角色
            String isDelete = deleteUserRole(userId);
            if ("-1".equals(isDelete))
            {
                return "-1";
            }
        }
        else
        {//保存角色
         // 先删除用户原有的角色
            deleteUserRole(userId);
            
            MiguUserRole relUserRole = new MiguUserRole();
            relUserRole.setUserId(userId);
            relUserRole.setRoleId(roleId);
            //relUserRole.setCrtateTime(new Date());
            int isSave = userRoleMapper.insertSelective(relUserRole);
            if (isSave <= 0)
            {
                return "-2";
            }
        }
        return "0";
    }
    
    @Override
    public String deleteUserRole(String userId) throws Exception
    {
        String result = "0";
        int isDelete = userRoleMapper.deleteUserRole(userId);
        if (isDelete <= 0)
        {
            result = "-1";
        }
        return result;
    }
    
    @Override
    public int queryRolesTotal(String roleName)
        throws Exception
    {
        Map<String, String> map = new HashMap<String, String>();
        map.put("roleName", roleName);
        return rolesMapper.queryRolesTotal(map);
    }
    
}
