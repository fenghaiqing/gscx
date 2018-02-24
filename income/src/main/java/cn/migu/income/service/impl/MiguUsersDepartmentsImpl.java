package cn.migu.income.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.migu.income.dao.MiguDepartmentsMapper;
import cn.migu.income.dao.MiguUserRoleMapper;
import cn.migu.income.dao.MiguUsersMapper;
import cn.migu.income.pojo.MiguUsers;
import cn.migu.income.pojo.TMiguDepartments;
import cn.migu.income.service.IMiguDepartmentsService;
import cn.migu.income.service.IMiguUsersService;
import cn.migu.income.util.StringUtil;

/**
 * 
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Service
public class MiguUsersDepartmentsImpl implements IMiguDepartmentsService
{
    final static Logger log = LoggerFactory.getLogger(MiguUsersDepartmentsImpl.class);
    @Autowired
    private MiguDepartmentsMapper miguDepartmentsMapper;
    
    
    @Override
    public List<TMiguDepartments> queryAllDepList(String deptName, int page,String userId, int pageSize) throws Exception
    {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("deptName", deptName);
        param.put("curPage", page);
        param.put("pageSize", pageSize);
        List<TMiguDepartments> list = miguDepartmentsMapper.queryAllDepList(param);
        return list;
    }
    
    @Override
    public int countByExample(String deptName) throws Exception
    {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("deptName", deptName);
        return miguDepartmentsMapper.selectCount(param);
    }
    
    @Override
    public String addDep(String deptCode, String deptName, String deptDescribe) throws Exception
    {
        String result = "";
        Map<String, Object> map = new HashMap<String, Object>();
        TMiguDepartments miguDepartments = new TMiguDepartments();
        miguDepartments = miguDepartmentsMapper.selectOne(deptCode);
        if (miguDepartments != null)
        {
            result = "9";
            return result;
        }
        map.put("deptCode", deptCode);
        map.put("deptName", deptName);
        map.put("deptDescribe", deptDescribe);
        int addCount = miguDepartmentsMapper.addDep(map);
        if (addCount <= 0)
        {
            result = "新增部门失败！";
        }
        else
        {
            result = "新增部门成功";
        }
        return result;
    }
    
    /**
     * 删除部门
     */
    @Transactional
    @Override
    public String deleteDep(String deptCode) throws Exception
    {
        String result = "0";
        int deleteCount = miguDepartmentsMapper.deleteDep(deptCode);
        if (deleteCount <= 0)
        {
            return "-2";
        }
        return result;
    }
    
    @Override
    public String updateDep(String deptCode, String deptName, String deptDescribe) throws Exception
    {
        TMiguDepartments uMiguDepartments = new TMiguDepartments();
        uMiguDepartments.setDeptCode(deptCode);
        uMiguDepartments.setDeptName(deptName);
        uMiguDepartments.setDeptDescribe(deptDescribe);
        int count = miguDepartmentsMapper.updateDep(uMiguDepartments);
        if (count <= 0)
        {
            return "-2";
        }
        return "修改成功！";
    }
    
    @Override
    public List<TMiguDepartments> queryAllDep() throws Exception
    {
        List<TMiguDepartments> list = miguDepartmentsMapper.queryAllDep();
        return list;
    }
    
}
