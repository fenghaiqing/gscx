package cn.migu.income.dao;

import java.util.List;
import java.util.Map;

import cn.migu.income.pojo.MiguRoles;

public interface MiguRolesMapper
{
    /**
     * 
     * 查詢所有角色信息
     * @author gyz
     * @param param
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    List<MiguRoles> queryAllRoles(Map<String, String> param) throws Exception;

    /**
     * 
     * 保存角色
     * @author gyz
     * @param miguRole
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    int addRole(MiguRoles miguRole) throws Exception;

    /**
     * 
     * 刪除角色
     * @author gyz
     * @param roleId
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    int deleteRoles(long roleId) throws Exception;

    /**
     * 
     * 修改角色
     * @author gyz
     * @param role
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    int updateByPrimaryKeySelective(MiguRoles role) throws Exception;
    
    /**
     * 根据用户ID查询用户所拥有的角色
     * @param userId
     * @return
     */
    List<MiguRoles> queryUserRoleByUserId(String userId) throws Exception;
    
    /**
     * 根据用户类型查询角色
     * @param userType
     * @return
     */
    List<MiguRoles> queryRoleByUserType() throws Exception;

    /**
     * 
     * 查看角色的总数
     * @author gyz
     * @return
     * @see [类、类#方法、类#成员]
     */
    int queryRolesTotal(Map<String, String> map) throws Exception;
    
    /**
     * 查询角色是否已经存在
     * <一句话功能简述>
     * <功能详细描述>
     * @param role
     * @return
     * @see [类、类#方法、类#成员]
     */
    MiguRoles selectOne(MiguRoles role);
    
}