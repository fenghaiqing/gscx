package cn.migu.income.service;

import java.util.List;

import cn.migu.income.pojo.MiguUsers;

public interface IMiguUsersService
{
    /**
     * 登录方法
     * @author gq
     * @param user
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    MiguUsers getUniqueUser(MiguUsers user) throws Exception;
    
    /**
     * 系统管理员：查询用户列表
     * @author gq
     * @param userName
     * @param page
     * @param pageSize
     * @return
     */
    
    List<MiguUsers> queryAllUserList(String userName,int page,String userId,int pageSize) throws Exception;
    /**
     * 新增
     * @author gq
     * @param userName
     * @param userId
     * @param msisdn
     * @param email
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    public String addUser(String userName, String userId, String msisdn,String email,String deptId,String startDate,String endDate,String pricingCommittee) throws Exception;
    
    /**
     * 删除用户
     * @author gq
     * @param userIds
     * @return
     */
    public String deleteUser(String userIds)throws Exception;
    
    /**
     * 修改
     * @author gq
     * @param userId
     * @param userName
     * @param msisdn
     * @param email
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    public String updateUser(String userId,String userName, String msisdn,String email,String deptId,String startDate,String endDate,String pricingCommittee) throws Exception;
    
    /**
     * 重置密码
     * @author gq
     * @param userId
     * @return
     */
    public String resetPwd(String userId) throws Exception;
    /**修改密码
     * @author gq
     * @param password
     * @param old_pwd
     * @param userId
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    public String modUserPassword(String password,String old_pwd,String userId) throws Exception;
    
    /**查询总数
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    public int countByExample(String userName) throws Exception;
    
    /**
     * 查询部门人员
     * @param deptCode
     * @return
     * @throws Exception
     */
    List<MiguUsers> queryDeptUserList(String deptCode) throws Exception;
    
    List<MiguUsers> queryPriceCommitteeUser()throws Exception ;
}
