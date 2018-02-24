package cn.migu.income.dao;

import java.util.List;
import java.util.Map;

import cn.migu.income.pojo.MiguSystemOptLog;

public interface MiguSystemOptLogMapper
{
    
    int insert(MiguSystemOptLog record);
    /**
     * 
     * 获取数据的条数
     * @author zhuxiaoling
     * @param map
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    int querySyslogTotal(Map<String, String> map) throws Exception;
    
    /**
     * 
     * 获取报账审核的数据
     * @author zhuxiaoling
     * @param map
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    List<MiguSystemOptLog> queryAllSyslog(Map<String, String> map) throws Exception;
    
}