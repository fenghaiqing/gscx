package cn.springmvc.test;


import cn.migu.income.service.MiguIncomeManagerService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.migu.income.pojo.TMiguProjectBase;
import cn.migu.income.service.IMiguProjectManageService;
import cn.migu.income.service.impl.MiguProjectManageServiceImpl;
import cn.migu.income.service.impl.TMiguPriceConfigServiceImpl;
import cn.migu.income.util.PriceConfigPojo;


public class ProjectTest {
	private TMiguPriceConfigServiceImpl priceConfigService;
  


	@Before
    public void before(){                                                                    
     
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:/spring-mvc.xml"
                ,"classpath:/spring-mybatis.xml"});
        priceConfigService = (TMiguPriceConfigServiceImpl) context.getBean(TMiguPriceConfigServiceImpl.class);
    }
     
    @Test
    public void addMiGupriceConfig(){
    	PriceConfigPojo projectBase = new PriceConfigPojo();
    	projectBase.setProjectId("123123123");
    	projectBase.setAduitUser("yanzheng");
    	projectBase.setStatus("1");
    	//priceConfigService.addMiGupriceConfig(projectBase);
         
    }

  

}
