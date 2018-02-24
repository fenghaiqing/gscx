package cn.migu.income.dao;

import cn.migu.income.pojo.MiguUserRole;

public interface MiguUserRoleMapper {
    
//    MiguUserRole selectByPrimaryKey(long userRoleId);

    int queryUsedUserRoleByRoleId(long roleId) throws Exception;
    
    int insertSelective(MiguUserRole record) throws Exception;
    
    /**
     * 删除用户已有角色
     * @param userId
     * @return
     */
    int deleteUserRole(String userId) throws Exception;
    
    int deleteRoleById(String[] userIds);
    
    String queryUserRoleDataByUserId(String userId) throws Exception;
}