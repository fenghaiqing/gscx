package cn.migu.income.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;

public class StringUtil
{
    
	
	public static int getCharacterPosition(String string, String substring, int count) {
		Matcher slashMatcher = Pattern.compile(substring).matcher(string);
		int mIdx = 0;
		while (slashMatcher.find()) {
			mIdx++;
			if (mIdx == count) {
				break;
			}
		}
		return slashMatcher.start();
	}
	
	//double去除小数点后面的位数
	public static String getStringWithoutDec(Double a){
		BigDecimal original = new BigDecimal(a.doubleValue());
		String result = original.toString() ;
		String[]  temp = result.split("\\.");
		return temp[0];
	}
	
	
    // 获得当前日期与本周一相差的天数
    public static int getMondayPlus()
    {
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期一是第二天......
        // 如果是星期日 返回1 如果是星期1 返回 2，按照中国人的习惯要减1 cd.get(Calendar.DAY_OF_WEEK)
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
        if (dayOfWeek == 0)
        {
            return -6;
        }
        else
        {
            return 1 - dayOfWeek;
        }
    }
    
    // 获得本周星期一的日期
    public static String getCurrMonday()
    {
        int mondayPlus = getMondayPlus();
        
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currMonday = sdf.format(monday);
        return currMonday;
    }
    
    // 获得本周星期日的日期
    public static String getCurrSunday()
    {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
        Date sunday = currentDate.getTime();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currSunday = sdf.format(sunday);
        return currSunday;
    }
    
    // 获得当天的日期
    public static String getCurrDate()
    {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currDate = sdf.format(date);
        return currDate;
    }
    
    /**
     * [简要描述]:获得当天的日期,格式yyyyMMdd<br/>
     * [详细描述]:<br/>
     * 
     * @return
     * @exception 
     */
	public static String getCurrDateStr() {
		Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String currDate = sdf.format(date);
        return currDate;
		
	}
    
    /**
     * [简要描述]:获得昨天的日期,格式yyyyMMdd<br/>
     * [详细描述]:<br/>
     * 
     * @return
     * @exception 
     */
    public static String getYestDateStr()
    {	
    	Date date = new Date();
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
		cal.add(Calendar.DATE, -1);
		String yesterday = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
		return yesterday;
    }
    
    
    /**
     * chentao
     * <获取系统当前时间包含时分秒>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getCurrDateStrContainHMS()
    {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currDate = sdf.format(date);
        return currDate;
    }
    
    // 获得上周星期一的日期
    public static String getPrevMonday()
    {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus - 7);
        Date monday = currentDate.getTime();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String prevMonday = sdf.format(monday);
        return prevMonday;
    }
    
    // 获得上周星期日的日期
    public static String getPrevSunday()
    {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus - 1);
        Date sunday = currentDate.getTime();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String prevSunday = sdf.format(sunday);
        return prevSunday;
    }
    
    // 获取当月第一天
    public static String getCurrMonthFirstDay()
    {
        
        Calendar firstDate = Calendar.getInstance();
        firstDate.set(Calendar.DATE, 1);// 设为当前月的1号
        Date first = firstDate.getTime();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currMonthFirstDay = sdf.format(first);
        return currMonthFirstDay;
    }
    
    // 获取当月最后一天
    public static String getCurrMonthLastDay()
    {
        
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
        lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
        lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
        Date last = lastDate.getTime();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currMonthLastDay = sdf.format(last);
        return currMonthLastDay;
    }
    
    // 获取上月第一天
    public static String getPrevMonthFirstDay()
    {
        
        Calendar firstDate = Calendar.getInstance();
        firstDate.set(Calendar.DATE, 1);// 设为当前月的1号
        firstDate.add(Calendar.MONTH, -1);// 减一个月，变为上月的1号
        Date first = firstDate.getTime();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String prevMonthFirstDay = sdf.format(first);
        return prevMonthFirstDay;
    }
    
    // 获得上月最后一天
    public static String getPrevMonthLastDay()
    {
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        lastDate.add(Calendar.DATE, -1);// 减去一天，变为上月最后一天
        Date last = lastDate.getTime();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String prevMonthLastDay = sdf.format(last);
        return prevMonthLastDay;
    }
    
    // 获得当前日期是星期几
    public static String getWeek()
    {
        
        String week = "";
        
        Calendar cd = Calendar.getInstance();
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        
        if (dayOfWeek == Calendar.SUNDAY)
        {
            week = "周日";
        }
        else if (dayOfWeek == Calendar.MONDAY)
        {
            week = "周一";
        }
        else if (dayOfWeek == Calendar.TUESDAY)
        {
            week = "周二";
        }
        else if (dayOfWeek == Calendar.WEDNESDAY)
        {
            week = "周三";
        }
        else if (dayOfWeek == Calendar.THURSDAY)
        {
            week = "周四";
        }
        else if (dayOfWeek == Calendar.FRIDAY)
        {
            week = "周五";
        }
        else if (dayOfWeek == Calendar.SATURDAY)
        {
            week = "周六";
        }
        return week;
    }
    
    /**
     * 空字符处理
     * 
     * @param value
     * @return
     */
    public static String null2Blank(String value)
    {
        return value == null ? "" : value;
    }
    
    /**
     * 空字符判断
     * 
     * @param value
     * @return
     */
    public static boolean isNullOrBlank(String value)
    {
        return (value == null || "".equals(value)) ? true : false;
    }
    
    /**
     * 产生验证码
     * 
     * @param length
     * @return
     */
    public static String retrieveRandomNumber(int length)
    {
        
        String result = "";
        try
        {
            SecureRandom r = SecureRandom.getInstance("SHA1PRNG");
            byte[] seed = r.generateSeed(20);
            r.setSeed(seed);
            byte[] bytes = new byte[length];
            r.nextBytes(bytes);
            for (int i = 0; i < bytes.length; i++)
            {
                byte value = bytes[i];
                result += String.valueOf(Math.abs(value) % 10);
            }
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return result;
    }
    
    /**
     * 获取一个六位随机数
     * 
     * @param length
     * @return
     */
    public static String getCharAndNumr(int length)
    {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++)
        {
            String charOrNum = random.nextInt(2) % 2 != 0 ? "num" : "char";
            if ("char".equalsIgnoreCase(charOrNum))
            {
                int choice = random.nextInt(2) % 2 != 0 ? 97 : 65;
                val = (new StringBuilder(String.valueOf(val))).append((char)(choice + random.nextInt(26))).toString();
            }
            else if ("num".equalsIgnoreCase(charOrNum))
                val = (new StringBuilder(String.valueOf(val))).append(String.valueOf(random.nextInt(10))).toString();
        }
        return val.toLowerCase();
    }
    
    /**
     * 获取时间戳
     * Jul 6, 2011
     * @return
     * 
     * String
     */
    public static String getTimeStamp(String format)
    {
        SimpleDateFormat dateFm = new SimpleDateFormat(format); //格式化当前系统日期
        return dateFm.format(new java.util.Date());
    }
    
    /**
     * 获取32位的字符类型的随机值
     * @return 32位的字符
     * Author : 邹建松
     * Date   : 2013-12-26
     */
    public static String getRandomStrId()
    {
        UUID uuid = UUID.randomUUID();
        String tempId = uuid.toString().replace("-", "");
        return tempId;
    }
    
    /**
     * 判断字符串是否为空
     * 
     * @param string 设置字符串
     * @return boolean 返回是否为空
     */
    public static boolean isEmpty(String string)
    {
        return string == null || string.length() == 0;
    }
    
    public static boolean isNotEmpty(String string)
    {
        return string != null && string.length() != 0;
    }
    
    //按字节大小截取字符串
    public static String subStr(String str, int len)
    {
        if (str != null && str.getBytes().length > len)
        {
            byte[] newValArr = new byte[len];
            System.arraycopy(str.getBytes(), 0, newValArr, 0, newValArr.length);
            str = new String(newValArr);
        }
        return str;
    }
    
    /**
     * 根据对象数组拼接对象数组字符串
     * @param  objArr         对象数组
     * @param  splitAfterStr  前拼接字符
     * @param  splitLastStr   后拼接字符
     * @return reStr          返回对象
     * @author 邹建松 2014-07-14
     */
    public static String concatObjArr(Object[] objArr, String splitAfterStr, String splitLastStr)
    {
        String reStr = "";
        if (splitAfterStr == null)
        {
            splitAfterStr = " ";
        }
        if (splitLastStr == null)
        {
            splitLastStr = " ";
        }
        if (objArr != null && objArr.length > 0)
        {
            for (int i = 0; i < objArr.length; i++)
            {
                reStr += splitAfterStr + objArr[i] + splitLastStr;
            }
        }
        return reStr;
    }
    
    /**
     * 根据对象列表拼接对象数组字符串
     * @param  li          对象列表
     * @param  replaceStr  替换字符
     * @param  splitStr    分割字符
     * @return reStr       返回对象
     * @author 邹建松 2014-10-14
     */
    public static String replaceObjArrToStr(List<Object> li, String replaceStr, String splitStr)
    {
        String reStr = "";
        for (int i = 0; i < li.size(); i++)
        {
            reStr += (replaceStr + splitStr);
        }
        if (StringUtil.isNotEmpty(reStr))
        {
            reStr = reStr.substring(0, reStr.length() - splitStr.length());
        }
        return reStr;
    }
    
    /**
     * 根据对象拼接对象数组字符串
     * @param  obj            对象
     * @param  splitAfterStr  前拼接字符
     * @param  splitLastStr   后拼接字符
     * @return reStr          返回对象
     * @author 邹建松 2014-07-14
     */
    public static String beanToObjArrStr(BeanPropertySqlParameterSource beanObj, String splitAfterStr,
        String splitLastStr)
    {
        String reStr = "";
        if (splitAfterStr == null)
        {
            splitAfterStr = ":";
        }
        if (splitLastStr == null)
        {
            splitLastStr = ";";
        }
        if (beanObj != null)
        {
            String[] strArr = beanObj.getReadablePropertyNames();
            for (int i = 0; i < strArr.length; i++)
            {
                reStr += strArr[i] + splitAfterStr + beanObj.getValue(strArr[i]) + splitLastStr;
            }
        }
        return reStr;
    }
    
    /**
     * 通用方法-解决字符串回车换行问题
     * @param  str    处理的字符串对象
     * @return String 返回对象
     * @author 邹建松 2014-07-14
     */
    public static String jointString(String str)
    {
        StringBuffer buf = new StringBuffer();
        for (StringTokenizer st = new StringTokenizer(str != null ? str : "", "\n", false); st.hasMoreTokens(); buf
            .append(st.nextToken().trim()))
            ;
        return buf.toString();
    }
    
    /**
     * 递归将列表生成树节点对象
     * @param  tempList    	节点列表
     * @param  topObj      	根节点对象
     * @param  idName      	id对象名称
     * @param  parentIdName parentId对象名称
     * @param  childrenName children对象名称
     * @return Map<String,Object>   返回对象
     * @author 邹建松 2014-08-09
     */
    @SuppressWarnings("unchecked")
    private static Map<String, Object> treeWork(List<Map<String, Object>> tempList, Map<String, Object> topObj,
        String idName, String parentIdName, String childrenName)
    {
        Map<String, Object> mapObj = null;
        List<Map<String, Object>> objList = null;
        String parentId = topObj.get(idName).toString();
        List<Map<String, Object>> temp2List = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> temp3List = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < tempList.size(); i++)
        {
            mapObj = tempList.get(i);
            if (mapObj.get(parentIdName).equals(parentId))
            {
                if (topObj.containsKey(childrenName))
                {
                    ((List<Map<String, Object>>)topObj.get(childrenName)).add(mapObj);
                }
                else
                {
                    objList = new ArrayList<Map<String, Object>>();
                    objList.add(mapObj);
                    topObj.put(childrenName, objList);
                }
                temp2List.add(mapObj);
                topObj.put("leaf", false);
            }
            else
            {
                temp3List.add(mapObj);
            }
        }
        
        for (int i = 0; i < temp2List.size(); i++)
        {
            mapObj = temp2List.get(i);
            treeWork(temp3List, mapObj, idName, parentIdName, childrenName);
        }
        return topObj;
    }
    
    /**
     * 递归将列表生成树节点对象
     * @param  tempList    	节点列表
     * @param  topObj      	根节点对象
     * @param  idName      	id对象名称
     * @param  parentIdName parentId对象名称
     * @param  childrenName children对象名称
     * @return List<Map<String,Object>>    返回对象
     * @author 邹建松 2014-08-09
     */
    public static List<Map<String, Object>> getJsonArrForList(List<Map<String, Object>> objList, String idName,
        String parentIdName, String childrenName)
    {
        Map<String, Object> obj = new HashMap<String, Object>();
        List<Map<String, Object>> topList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
        Map<String, Object> mapObj = null;
        List<Map<String, Object>> reObjList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < objList.size(); i++)
        {
            obj = objList.get(i);
            obj.put("leaf", true);
            if (obj.get(parentIdName) == null || obj.get(parentIdName).equals("null")
                || obj.get(parentIdName).equals(""))
            {
                topList.add(obj);
            }
            else
            {
                tempList.add(obj);
            }
        }
        
        for (int i = 0; i < topList.size(); i++)
        {
            mapObj = topList.get(i);
            treeWork(tempList, mapObj, idName, parentIdName, childrenName);
            reObjList.add(mapObj);
        }
        return reObjList;
    }
    
    /**
     * MD5的32位加密
     * @param  inStr     对象
     * @return String  	  返回对象
     * @author 邹建松 2014-08-09
     */
    public static String md5(String inStr)
    {
        MessageDigest md5 = null;
        try
        {
            md5 = MessageDigest.getInstance("MD5");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for (int i = 0; i < charArray.length; i++)
        {
            byteArray[i] = (byte)charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++)
        {
            int val = ((int)md5Bytes[i]) & 0xff;
            if (val < 16)
            {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
    
    /**
     * [简要描述]:处理时间字符串<br/>
     * [详细描述]:<br/>
     * 
     * @param time 2014-11-14 17:04:58.0
     * @return 2014-11-14 17:04:58
     * @exception 
     */
    public static String dealTimeStr(String time)
    {
        // 对参数进行判断
        if (isEmpty(time))
        {
            return time;
        }
        else
        {
            return time.substring(0, time.length() - 2);
        }
    }
    
    /**
     * [简要描述]:通过","隔开的字符串获取正则表达式对象<br/>
     * [详细描述]:<br/>
     * 
     * @param extStr ","隔开的字符串
     * @param caseInsensitive 是否忽略大小写
     * @return
     * @exception 
     */
    public static String getReg(String extStr, boolean caseInsensitive)
    {
        String[] arr = extStr.split(",");
        String reStr = "";
        for (int i = 0; i < arr.length; i++)
        {
            if (i == arr.length - 1)
            {
                reStr += "(.*\\" + arr[i] + ")";
            }
            else
            {
                reStr += "(.*\\" + arr[i] + ")|";
            }
        }
        if (caseInsensitive)
        {
            return "^(?i)" + reStr + "$";
        }
        return "^" + reStr + "$";
    }
    
    /**  
     * 手机号验证是否合法
     * @param  str  
     * @return 验证通过返回true  
     */
    public static boolean isMobile(String str)
    {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号   
        m = p.matcher(str);
        b = m.matches();
        return b;
    }
    
    /**
     * 检测邮箱地址是否合法
     * 
     * @param email
     * @return true合法 false不合法
     */
    public static boolean isEmail(String email)
    {
        if (null == email || "".equals(email))
            return false;
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");// 复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }
    
    /**
     * 检测日期串是否合法
     * 
     * @param dateStr
     * @return true合法 false不合法
     */
    public static boolean isDateValid(String dateStr)
    {
        String eL =
            "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
        Pattern p = Pattern.compile(eL);
        Matcher m = p.matcher(dateStr);
        boolean b = m.matches();
        if (b)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public static boolean isDateValidForFormat(String dateStr,String f){
    	 boolean convertSuccess=true;
         SimpleDateFormat format = new SimpleDateFormat(f);
           try {
              format.setLenient(false);
              format.parse(dateStr);
           } catch (ParseException e) {
              // e.printStackTrace();
    // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
               convertSuccess=false;
           } 
           return convertSuccess;
    }
    
    /**
     * 
     * 格式化日期，去掉字符'/'并调序
     * @author guanyuzhuang
     * @param date
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String formatStringDate(String date)
    {
        String[] arrDate = date.split("/");
        if (arrDate.length != 3)
        {
            return date;
        }
        else
        {
            return arrDate[2] + arrDate[0] + arrDate[1];
        }
    }
    
    
    /**
     * 
     * 格式化日期，去掉字符'/'并调序
     * @param date
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String formatDateString(String date)
    {
        String[] arrDate = date.split("/");
        if (arrDate.length != 3)
        {
            return date;
        }
        else
        {
        	return arrDate[2] +"-"+ arrDate[0] +"-"+ arrDate[1];
        }
    }
    
    /**
     * 
     * 格式化日期带时分秒，去掉字符'/'并调序
     * @author chentao
     * @param date
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String formatStringTime(String date)
    {
        String[] arrDate = date.split(" ")[0].split("/");
        if (arrDate.length != 3)
        {
            return date;
        }
        else
        {
            return arrDate[2] +"-"+ arrDate[0] +"-"+ arrDate[1]+" "+date.split(" ")[1];
        }
    }
    
    /**
     * 
     * 格式化日期，去掉字符'/'并调序
     * @author guanyuzhuang
     * @param date
     * @return 'yyyyMM'
     * @see [类、类#方法、类#成员]
     */
    public static String formatDateToString(String date)
    {
        String[] arrDate = date.split("/");
        if (arrDate.length != 3)
        {
            return date;
        }
        else
        {
            return arrDate[2] + arrDate[0];
        }
    }
    
    /**
     * 
     * 格式化日期，去掉字符'-'并调序
     * @author guanyuzhuang
     * @param date
     * @return 'yyyyMM'
     * @see [类、类#方法、类#成员]
     */
    public static String formatDateToyyyyMM(String date)
    {
        String[] arrDate = date.split("-");
        if (arrDate.length != 2)
        {
            return date;
        }
        else
        {
            return arrDate[0] + (arrDate[1].length()>1?arrDate[1]:"0"+arrDate[1]);
        }
    }
    
    public static String subStringTo300(Object obj)
    {
        if (obj.toString().length() >= 300)
        {
            return obj.toString().substring(0, 300) + "...";
        }
        else
        {
            return obj.toString();
        }
    }
    
    /**
     * 数组arr中是否包含targetValue项
     */
    public static boolean hasContains(String[] arr, String targetValue)
    {
        for (String s : arr)
        {
            if (s.equals(targetValue))
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 判断字符串是否为数字
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isNumeric(String str)
    {
        return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }
    
    /**
     * 数字格式，保留两位小数
     * @param value
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String numberFormat(String value)
    {
        if (null == value || !StringUtil.isNumeric(value))
        {
            return value;
        }
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(Double.valueOf(value));
    }
    
    /**
     * chentao
     * <判断字符串时间是否符合规定的日期格式>
     * <功能详细描述>
     * @param Date
     * @param format
     * @see [类、类#方法、类#成员]
     */
    public static boolean checkStringFormatDate(String Date,String format)
    {
        for(int i=0 ; i<Date.length() ; i++){ //循环遍历字符串   
            if(Character.isLetter(Date.charAt(i))){   //用char包装类中的判断字母的方法判断每一个字符
                return false;
            }
        }
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        simpleDateFormat.setLenient(false);
        try
        {
            simpleDateFormat.parse(Date);
        }
        catch (ParseException e)
        {
            return false;
        }
        return true;
    }
    
    /**
     * chentao
     * <获取字符串的字节长度>
     * <功能详细描述>
     * @param s
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static int getStringBytesLength(String s)  
    {  
        int length = 0;  
        for(int i = 0; i < s.length(); i++)  
        {  
            int ascii = Character.codePointAt(s, i);  
            if(ascii >= 0 && ascii <=255)  
                length++;  
            else  
                length += 2;  
                  
        }  
        return length;  
    } 
    
    /**
     * 判断时间是否在指定的时间区间内
     * <一句话功能简述>
     * <功能详细描述>
     * @return
     * @throws ParseException 
     * @see [类、类#方法、类#成员]
     */
    public static boolean checkDateBeforeAfter(String beforeDate, String afterDate,String data){
        SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd"); 
        try
        {
            Date dateBefor = df.parse(beforeDate);
            Date dateAfter=df.parse(afterDate);   
            Date time=df.parse(data);
            if(time.before(dateAfter) && time.after(dateBefor)){
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * 获取当前时间前一天
     * <一句话功能简述>
     * <功能详细描述>
     * @param date
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getNextDay(String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
        date = dateFormat.parse(time);
        } catch (ParseException e) {
        e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        date = calendar.getTime();
        String strDate=dateFormat.format(date);
        return strDate;
    }
    /**
     * 
     * 在oracle查询中_、%转化成\_、\%的处理
     * <功能详细描述>
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getSpecialParam(String str)
    {
        String un = "";
        
        for (int i = 0; i < str.length(); i++)
        {
            if (str.charAt(i) == '_')
            {
                un += "\\" + str.charAt(i);
            }
            else if(str.charAt(i) == '%')
            {
                un += "\\" + str.charAt(i);
            }
            else
            {
                un += str.charAt(i);
            }
            
        }
        
        return un;
    }
    
    /**
     * 判断时间大小
     * <一句话功能简述>
     * <功能详细描述>
     * @param date1
     * @param date2
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean compare_date(String date1,String date2){
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return true;
            } else if (dt1.getTime() < dt2.getTime()) {
                return false;
            } else if (dt1.getTime() == dt2.getTime()) {
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return true;
    }
    
    
    /**
     * 判断时间大小
     * <一句话功能简述>
     * <功能详细描述>
     * @param date1
     * @param date2
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean compare_date(String date1,String date2,String formatType){
        DateFormat df = new SimpleDateFormat(formatType);
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return true;
            } else if (dt1.getTime() < dt2.getTime()) {
                return false;
            } else if (dt1.getTime() == dt2.getTime()) {
                return true;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return true;
    }
    
    /**
     * 获取文件编码格式
     * <一句话功能简述>
     * <功能详细描述>
     * @param fileName
     * @return
     * @throws IOException
     * @see [类、类#方法、类#成员]
     */
    public static String getCharset(String fileName) {  
        
        FileInputStream fi=null;
        BufferedInputStream bin = null;
        String code = null;    
        try
        {
            fi = new FileInputStream(fileName);
            bin = new BufferedInputStream(fi);    
            int p = (bin.read() << 8) + bin.read();    
            
            
            switch (p) {    
                case 0xefbb:    
                    code = "UTF-8";    
                    break;    
                case 0xfffe:    
                    code = "Unicode";    
                    break;    
                case 0xfeff:    
                    code = "UTF-16BE";    
                    break;    
                default:    
                    code = "GBK";    
            }    
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            try
            {
                fi.close();
                bin.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            
        }
        return code;  
    } 
    
    /**
     * 获取当前时间的上个月
     * <一句话功能简述>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getLastDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -1);
        return sdf.format(cal.getTime());
    }
    
    
    /**
     * 获取某个月从date到月末的列表 比如20160926-20160930
     * @param date@zhuxl 20161014
     * @return
     */
    public static List<String> getDateFromSomeDay(Calendar date){
        List<String> list = new ArrayList<String>();
        SimpleDateFormat sdfy = new SimpleDateFormat("yyyyMMdd");//格式化输出年月日
        Calendar last = Calendar.getInstance();
        last.set(date.get(Calendar.YEAR),date.get(Calendar.MONTH) , date.getActualMaximum(Calendar.DAY_OF_MONTH));
        return getDateBetween(date, last);
    }
    
   
    
    /**
     * 获取本月所有日期列表比如20160901-20160930
     * @param date@zhuxl 20161014
     * @return
     */
    public static List<String> getDateWholeMonth(Calendar date){
        List<String> list = new ArrayList<String>();
        SimpleDateFormat sdfy = new SimpleDateFormat("yyyyMMdd");//格式化输出年月日
        Calendar first = Calendar.getInstance();
        Calendar last = Calendar.getInstance();
        first.set(date.get(Calendar.YEAR),date.get(Calendar.MONTH) , 1);
        last.set(date.get(Calendar.YEAR),date.get(Calendar.MONTH) , date.getActualMaximum(Calendar.DAY_OF_MONTH));
        return getDateBetween(first, last);
    }
    
    /**
     * 获取从某一天到某一天的列表
     * @param from
     * @param to
     * @return
     */
    public static List<String> getDateBetween(Calendar from,Calendar to){
        List<String> list = new ArrayList<String>();
        SimpleDateFormat sdfy = new SimpleDateFormat("yyyyMMdd");//格式化输出年月日
        for(;from.compareTo(to)<=0?true:false; from.add(Calendar.DAY_OF_MONTH,1)){
            list.add(sdfy.format(from.getTime()));
        }
        return list;
    }
    
    /**
     * 特殊返回值处理
     * 
     * @param value
     * @return
     */
    public static String nullToBlank(String value)
    {
        return value == null || value.equals("null")? "" : value;
    }
    
    
    
    public static void main(String[] args) {
    	System.out.println(StringUtil.md5("123456"));
	}
}
