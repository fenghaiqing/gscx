package cn.migu.income.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropValue
{
    
    private static String fileNamePath = PropValue.class.getClassLoader().getResource("filesLocation.properties").getPath() ;
    String loc="";
    public static String getPropValue(String key){  
        Properties props = new Properties();  
        InputStream in = null;
        String value = "";
        try {  
            in = new FileInputStream(fileNamePath);
            props.load(in);
            value = props.getProperty(key);
            // 乱码时要进行重新编码  
            // new String(props.getProperty("name").getBytes("ISO-8859-1"), "GBK");  
            return value;
        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
        } finally {
            if (null != in)
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
        }
        return value;
    }
}
