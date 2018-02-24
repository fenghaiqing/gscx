package cn.migu.income.service;

import java.util.List;
import java.util.Map;

import cn.migu.income.pojo.MiguFunc;

public interface IMiguFuncService
{
    /**
     * 
     * 根据用户id查询用户权限列表
     * @author gyz
     * @param userId
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<MiguFunc> getFuncListByUserId(String userId) throws Exception;

    /**
     * 
     * 根据角色id查询角色对应的权限列表
     * @author gyz
     * @param parseLong
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<Map<String, Object>> queryAllFunc(long roleId,String userId) throws Exception;

    /**
     * 
     * 保存角色的权限配置
     * @author gyz
     * @param roleId
     * @param funcIds
     * @return
     * @see [类、类#方法、类#成员]
     */
    String saveRoleFunc(long roleId, String funcIds) throws Exception;
    
    /**
     * 查询子菜单
     * <一句话功能简述>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<Map<String, Object>> querySubmenu(String pid,String userId,List<MiguFunc> funcList);

}
