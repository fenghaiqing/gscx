package cn.migu.income.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class MiguConstants
{
    /**
     * session中的用户信息的key值
     */
    public final static String USER_KEY = "user";
    
    /**
     * 默认第一页
     */
    public final static String PAGE_INDEX = "1";
    
    /**
     * 默认每页20条数据
     */
    public final static String DEFAULT_ROWNUM = "20";
    /**
     *  文件异常存放的地址
     */
    public final static String ERROR_LOCATION = "er_location";
    
    /**
     * 存放临时文件
     */
    public final static String FILES_LOCATION = "files_location";
    public final static String TP_LOCATION = "tp_location";
    public final static String TEP_LOCATION = "tep_location";
    /**
     * 返回代码_数据库操作正常
     */
    public final static String OPER_CODE_NORMAL = "0";
    
    /**
     * 返回代码_数据库操作无返回数据
     */
    public final static String OPER_CODE_NODATA = "-1";
    
    /**
     * 返回代码_数据库存在关联数据
     */
    public final static String OPER_CODE_RELADATA = "-2";
    
    /**
     * 返回代码_数据库存在该条记录
     */
    public final static String OPER_CODE_ISEXIST = "-3";
    
    /**
     * 返回代码_数据库不存在该条记录
     */
    public final static String OPER_CODE_ISNOTEXIST = "-4";
    
    /**
     * 返回代码_数据库存在多条记录
     */
    public final static String OPER_CODE_ISEXISTMULTI = "-5";
    
    /**
     * 返回代码_数据库存在该优先级记录
     */
    public final static String PRIORITY_ISEXIST = "-6";
    
    /**
     * 返回代码_操作其他异常
     */
    public final static String OPER_CODE_OTHER = "-100";
    
    /**
     * 返回代码_操作成功
     */
    public final static String OPER_CODE_SUCCESS = "100";
    
    public final static String WEBSERVICE_LOCATION = "webService_location";
    
    public final static String BILL_WEBSERVICE_LOCATION = "bill_webService_location";
    
    public final static String WEBSERVICE_OA_TASK="OA_TASK_URL";
    
    public final static String SSO_USER_AUTH="SSO_USER_AUTH";
    
    /**
     * 操作类型
     */
    public final static String OPRATION_TYPE_EDIT="update";
    
    public final static String OPRATION_TYPE_ADD="add";
    
    public final static String OPRATION_TYPE_REFRESH="refresh";
    
    public static final String APPLICATION_JSON="application/json;charset=UTF-8";
	
    public static final String CONTENT_TYPE_TEXT_JSON="text/json";
    
}
