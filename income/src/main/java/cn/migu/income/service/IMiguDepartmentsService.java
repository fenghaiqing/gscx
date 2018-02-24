package cn.migu.income.service;

import java.util.List;

import cn.migu.income.pojo.MiguUsers;
import cn.migu.income.pojo.TMiguDepartments;

public interface IMiguDepartmentsService
{
	/**
     * 查询部门列表
     * @author 陈涛
     * @param deptName
     * @param page
     * @param pageSize
     * @return
     */
    
    List<TMiguDepartments> queryAllDepList(String deptName,int page,String userId,int pageSize) throws Exception;
   
    /**查询总数
     * @return
     * @throws Exception
     */
    public int countByExample(String deptName) throws Exception;
    
    /**
     * 新增
     * @author chentao
     * @param deptCode
     * @param deptName
     * @param deptDescribe
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    public String addDep(String deptCode, String deptName, String deptDescribe) throws Exception;
    
    /**
     * 删除部门
     * @author chentao
     * @param deptCode
     * @return
     */
    public String deleteDep(String deptCode)throws Exception;
    
    /**
     * 修改
     * @author chentao
     * @param deptCode
     * @param deptName
     * @param deptDescribe
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    public String updateDep(String deptCode, String deptName, String deptDescribe) throws Exception;
    
    /**
     * 查询部门列表
     * @author 陈涛
     * @return
     */
    
    List<TMiguDepartments> queryAllDep() throws Exception;
    
}
