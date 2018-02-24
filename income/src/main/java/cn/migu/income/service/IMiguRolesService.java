package cn.migu.income.service;

import java.util.List;

import cn.migu.income.pojo.MiguRoles;

public interface IMiguRolesService
{
    /**
     * 查询所有角色
     * @param row 
     * @param page 
     * @return
     */
    public List<MiguRoles> queryAllRoles(String roleName, String page, String row) throws Exception;

    /**
     * 
     * 新增角色
     * @param roleName
     * @return
     * @see [类、类#方法、类#成员]
     */
    public String addRole(String roleName) throws Exception;
    
    /**
     * 
     * 根据用户id查询用户的角色
     * @author gyz
     * @param userId
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    MiguRoles queryUserRoleByUserId(String userId) throws Exception;

    /**
     * 
     * 删除角色
     * @param parseLong
     * @return
     * @see [类、类#方法、类#成员]
     */
    public String deleteRole(long roleId) throws Exception;

    /**
     * 修改角色
     * @param roleId
     * @param roleName
     * @return
     */
    public String editRole(long roleId, String roleName) throws Exception;

    /**
     * 
     * 根据用户类型查询用户对应的角色
     * @author gyz
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    List<MiguRoles> queryRoleByUserType() throws Exception;
    
    /**
     * 保存用户角色
     * @param userId
     * @param roleId
     * @param createUserId
     * @return
     */
    public String saveUserRole(String userId,long roleId,String type) throws Exception;
    
    
    /**
     * 删除用户已有角色
     * @param userId
     * @param roleId
     * @return
     */
    public String deleteUserRole(String userId) throws Exception;

    /**
     * 
     * 删除角色总数
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    public int queryRolesTotal(String roleName) throws Exception;

}
