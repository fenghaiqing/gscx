package cn.migu.income.dao;

import java.util.List;

import cn.migu.income.pojo.MiguFunc;

public interface MiguFuncMapper {
    /**
     * 根据用户id查询用户菜单权限
     * @author gyz
     * @param userId
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<MiguFunc> getFuncListByUserId(String userId) throws Exception;

    /**
     * 
     * 根据用户id查询用户菜单权限
     * @author gyz
     * @param roleId
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    List<MiguFunc> queryAllFunc(long roleId) throws Exception;

    /**
     * 
     * 根据权限id查询对应的父级菜单权限
     * @author gyz
     * @param funcId
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    MiguFunc queryFuncByIdForTree(long funcId) throws Exception;
    
    /**
     * 查询子菜单
     * <一句话功能简述>
     * <功能详细描述>
     * @param pid
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<MiguFunc> querySubmenu(String pid,String userId);
    
    /**
     * 查询子菜单权限
     * <一句话功能简述>
     * <功能详细描述>
     * @param pid
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<MiguFunc> querySubFun(String pid,String roleId);
    
    /**
     * 
     * 根据用户id查询用户菜单权限
     * @author gyz
     * @param roleId
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    List<MiguFunc> queryAllFuncByMenu(long roleId) throws Exception;
}