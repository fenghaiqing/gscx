package cn.migu.income.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.migu.income.dao.MiguSystemOptLogMapper;
import cn.migu.income.pojo.MiguSystemOptLog;

@Service
public class MiguOptLogger
{
    @Autowired
    private MiguSystemOptLogMapper optLogMapper;
    
    public void logDb(String userId, String opt, String msg) {
		MiguSystemOptLog optLog = new MiguSystemOptLog();
		optLog.setUserId(String.valueOf(userId));
		optLog.setOperation(opt);
		optLog.setComments(msg);
		optLog.setUserLoginIp("");
		optLogMapper.insert(optLog);
	}
	
	public void loginLog(HttpServletRequest request, String userId, String opt, String msg) {
		MiguSystemOptLog optLog = new MiguSystemOptLog();
		optLog.setUserId(userId);
		optLog.setOperation(opt);
		optLog.setComments(msg);
		optLog.setUserLoginIp(getIpAddr(request));
		optLogMapper.insert(optLog);
	}
    
    
    /**
     * 
     * 获取数据的条数
     * @author zhuxiaoling
     * @param map
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    public int querySyslogTotal(Map<String, String> map) throws Exception {
        return optLogMapper.querySyslogTotal(map);
    }
    
    /**
     * 
     * 获取报账审核的数据
     * @author zhuxiaoling
     * @param map
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
   public List<MiguSystemOptLog> queryAllSyslog(Map<String, String> map) throws Exception{
       return optLogMapper.queryAllSyslog(map);
   }
   
   public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip.equals("0:0:0:0:0:0:0:1")) {
			ip = "本地";
		}
		return ip;
	}
}
