package cn.migu.income.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.migu.income.dao.MiguUserRoleMapper;
import cn.migu.income.dao.MiguUsersMapper;
import cn.migu.income.pojo.MiguUsers;
import cn.migu.income.service.IMiguUsersService;
import cn.migu.income.util.StringUtil;

/**
 * 
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service
public class MiguUsersServiceImpl implements IMiguUsersService
{
    final static Logger log = LoggerFactory.getLogger(MiguUsersServiceImpl.class);
    @Autowired
    private MiguUsersMapper usersMapper;
    
    @Autowired
    private MiguUserRoleMapper userRoleMapper;    
    @Override
    public MiguUsers getUniqueUser(MiguUsers user) throws Exception
    {
        return usersMapper.selectOne(user);
    }    
        
    /**
     * 增加用户
     */
    @Override
    public String addUser(String userName, String userId, String msisdn, String email, String deptId, String startDate, String endDate,String pricingCommittee) throws Exception
    {
        String result = "";
        Map<String, String> paramMap = new HashMap<String, String>();
        Map<String, Object> map = new HashMap<String, Object>();
        paramMap.put("msisdn", msisdn);
        List<MiguUsers> userList = usersMapper.queryUserByDn(paramMap);
        MiguUsers users = new MiguUsers();
        users.setUserId(userId);
        MiguUsers user = new MiguUsers();
        user = usersMapper.selectOne(users);
        if (userList != null && userList.size() > 0)
        {
            result = "-1";
            return result;
        }
        if (user != null)
        {
            result = "9";
            return result;
        }
        map.put("userName", userName);
        map.put("userId", userId);
        map.put("email", email);
        map.put("msisdn", msisdn);
        map.put("deptId", deptId);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("password", StringUtil.md5(userId));
        map.put("lastPwd", StringUtil.md5(userId));
        map.put("pricingCommittee", pricingCommittee);
        int addCount = usersMapper.addUser(map);
        if (addCount <= 0)
        {
            result = "增加用户失败！";
        }
        else
        {
            result = "用户登录名为：" + userId + ", 默认密码为OA系统登录密码" ;
        }
        return result;
    }
    
    /**
     * 删除用户
     */
    @Transactional
    @Override
    public String deleteUser(String userIds) throws Exception
    {
        String result = "0";
        String[] userIdsArray = userIds.split(",");
        userRoleMapper.deleteRoleById(userIdsArray);
        int deleteCount = usersMapper.deleteUserById(userIdsArray);
        if (deleteCount <= 0)
        {
            return "-2";
        }
        return result;
    }
    
    @Override
    public List<MiguUsers> queryAllUserList(String userName, int page,String userId, int pageSize) throws Exception
    {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userName", userName);
        param.put("curPage", page);
        param.put("pageSize", pageSize);
        List<MiguUsers> list = usersMapper.queryAllUserList(param);
        for (int i = 0; i < list.size(); i++)
        {
            if(userId.equals(list.get(i).getUserId())){
                list.add(0, list.get(i));
                list.remove(i+1);                
            }
        }
        return list;
    }
    
    /**
     * 人员修改
     */
    
    @Override
    public String updateUser(String userId, String userName, String msisdn, String email, String deptId,String startDate,
        String endDate,String pricingCommittee) throws Exception
    {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("msisdn", msisdn);
        MiguUsers users = usersMapper.selectByPrimaryKey(userId);
        if (!msisdn.equals(users.getMsisdn()))
        {//修改了手机号码
            List<MiguUsers> userList = usersMapper.queryUserByDn(paramMap);
            if (userList != null && userList.size() > 0)
            {
                return "-1";
            }
        }
        MiguUsers user = new MiguUsers();
        user.setEmail(email);
        user.setUserName(userName);
        user.setMsisdn(msisdn);
        user.setUserId(userId);
        user.setDeptId(deptId);
        user.setStartDate(startDate);
        user.setEndDate(endDate);
        user.setPricingCommittee(pricingCommittee);
        int count = usersMapper.updateByPrimaryKeySelective(user);
        if (count <= 0)
        {
            return "-2";
        }
        return "修改成功！";
    }
    
    /**
     * 重置密码
     */
    @Override
    public String resetPwd(String userId) throws Exception
    {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        MiguUsers user = new MiguUsers();
        user = usersMapper.selectOldPwdByUser(userId);
        paramMap.put("userId", userId);
        paramMap.put("password", StringUtil.md5(String.valueOf(userId)));
        paramMap.put("lastPwd", StringUtil.md5(user.getLastPwd()));
        int result = usersMapper.resetPwd(paramMap);
        if (result <= 0)
        {
            return "重置密码失败";
        }
        
        return "重置之后的密码为：" + userId;
    }
    
    @Override
    public String modUserPassword(String password, String old_pwd, String userId) throws Exception
    {
        // TODO Auto-generated method stub
        String res = "";
        MiguUsers user = new MiguUsers();
        user.setLastPwd(StringUtil.md5(old_pwd));
        user.setPassword(StringUtil.md5(password));
        user.setUserId(userId);
        MiguUsers uu = new MiguUsers();
        uu = usersMapper.selectOldPwdByUser(userId);
        if(uu==null){
            return "9";
        }
        if (!StringUtil.md5(old_pwd).equals(uu.getPassword()))
        {
            res = "9";
        }
        else
        {
            int result = usersMapper.modUserPassword(user);
            if (result < 0)
            {
                res = "0";
            }
            else
            {
                res = "100";
            }
        }
        return res;
    }
    
    @Override
    public int countByExample(String userName) throws Exception
    {
        // TODO Auto-generated method stub
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userName", userName);
        return usersMapper.selectCount(param);
    }
    
    @Override
    public List<MiguUsers> queryDeptUserList(String deptCode) throws Exception
    {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("deptId", deptCode);
        List<MiguUsers> list = usersMapper.queryDeptUserList(param);
        return list;
    }

	@Override
	public List<MiguUsers> queryPriceCommitteeUser() throws Exception {
		
	        List<MiguUsers> list = usersMapper.queryPriceCommitteeUser();
	        return list;
	}
    
}
