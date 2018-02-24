package cn.migu.income.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.migu.income.dao.IncomeAlertMapper;
import cn.migu.income.service.IncomeAlertService;

@Service
public class IncomeAlertServiceImpl implements IncomeAlertService{

	@Autowired
	private IncomeAlertMapper incomeAlertMapper;
	
	final static Logger log = LoggerFactory.getLogger(IncomeAlertServiceImpl.class);
	/**
	 * 实际收款告警信息入库定时任务
	 */
	@Override
	public void incomeAlert(){
		try {
	        Date d = new Date();    
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	        String dateNowStr = sdf.format(d);          
			incomeAlertMapper.incomeAlert();
			log.info("实际收款告警定时任务执行成功！时间："+dateNowStr);

		} catch (Exception e) {
			log.error("实际收款告警定时任务异常！");
		}
		
	}
}
