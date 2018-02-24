package cn.migu.income.test.gyz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.migu.income.dao.MiguRolesMapper;
import cn.migu.income.pojo.MiguRoles;

public class TestDao
{
    
    @SuppressWarnings("resource")
    public static void main(String[] args)
    {
        try
        {
           /* ApplicationContext ac =
                new ClassPathXmlApplicationContext(new String[] {"spring-mvc.xml", "spring-mybatis.xml"});
            MiguRolesMapper sp = (MiguRolesMapper)ac.getBean("miguRolesMapper");
            Map<String, String> map = new HashMap<String, String>();
            map.put("roleName", null);
            List<MiguRoles> list = sp.queryAllRoles(map);
            for (MiguRoles s : list)
            {
                System.out.println(s.getRoleName() + "\t" + s.getCrtateDate()+ "\t" + s.getIsAdmin());
            }*/
        	System.out.println(450%100);
        }
        catch (BeansException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
  /*  public static void main(String[] args) {
	
	}*/
}
