package cn.migu.income.dao;

import java.util.List;
import java.util.Map;

public interface MiguFuncRoleMapper
{
    /**
     * 
     * 根据用户角色id刪除角色-权限对应关系
     * @author gyz
     * @param roleId
     * @see [类、类#方法、类#成员]
     */
    void deleteRoleFuncByRoleId(long roleId) throws Exception;
    
    /**
     * 
     * 保存角色-权限对应关系
     * @author gyz
     * @param miguFuncRole
     * @return
     * @see [类、类#方法、类#成员]
     */
    int saveRoleFunc(List<Map<String, Long>> list) throws Exception;
    
}