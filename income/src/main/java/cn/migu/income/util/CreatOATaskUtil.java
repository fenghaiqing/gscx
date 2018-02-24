package cn.migu.income.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import cn.migu.income.service.impl.MiguIncomeManagerServiceImpl;

public class CreatOATaskUtil {

	final static Logger log = LoggerFactory.getLogger(MiguIncomeManagerServiceImpl.class);
	
	public static String  createOATask(Map<String, String> map){

		Map<String, Object> params = new HashMap<>();
		String user=map.get("userId");
		HeadPojo head = new HeadPojo();
		head.setAppCode("miguoa");// 待办对应的应用系统编号
		head.setOptType(map.get("optType"));// 待办操作类型 1:add 2:modify 3:delete [Add：新增待	Modify：修改待办状态 Delete：删除待办]
		head.setUserID(user);// 登录名 当前应用系统用户
		head.setToken(MD5.md5("0000"));// md5(serviceId+serviceKey+time)
		params.put("head", head);
		PendingInfoPojo pendingInfo = new PendingInfoPojo();
		pendingInfo.setCode(map.get("code"));// 待办编码 待办编码,各应用系统待办的唯一标识
		pendingInfo.setTitle(map.get("title")); // 待办标题
		pendingInfo.setDate(new Date().getTime()); // 待办时间  时间戳12233333
		pendingInfo.setPerson(map.get("auditUser"));// 待办人标识 待办负责人标识,即用户登录名
		pendingInfo.setUrl(map.get("url"));// 待办信息URL-待办处理页面
		pendingInfo.setStatus(Integer.parseInt(map.get("status")));// 待办状态 0:待办 ，1: 已办，2: 待阅, 3: 已阅
		pendingInfo.setOraniger(user);// 待办发起人标识
		pendingInfo.setPrevious(map.get("userName"));// 上一待办人
		pendingInfo.setType(1);// 待办类别:（0:全部，1:公文，2:人事,3:报账，4:合同5：工单）
		pendingInfo.setClazz(map.get("clazz"));// 种类
		pendingInfo.setAppCodeMobile("0001");
		pendingInfo.setIsClient(2);
		params.put("pendingInfo", pendingInfo);
		try {
			return requestOATask(params);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("创建OA代办异常：" + e.getMessage());
			return "";
		}
	
	}
	
	public static String requestOATask(Map<String, Object> map) throws Exception {
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httppost
		HttpPost httppost = new HttpPost(PropValue.getPropValue(MiguConstants.WEBSERVICE_OA_TASK));
		// 设置请求头
		httppost.addHeader(HTTP.CONTENT_TYPE, MiguConstants.APPLICATION_JSON);
		String jsonstr = JSON.toJSONString(map);
		StringEntity se = new StringEntity(jsonstr, "UTF-8");
		String params = EntityUtils.toString(se, "UTF-8");
		log.info("创建OA代办传参信息："+params);
		se.setContentType(MiguConstants.CONTENT_TYPE_TEXT_JSON);
		se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, MiguConstants.APPLICATION_JSON));
		httppost.setEntity(se);
		CloseableHttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();
		String json = EntityUtils.toString(entity, "UTF-8");
		int code = response.getStatusLine().getStatusCode();
		log.info("创建OA代办返回信息："+json);
		if (code == 200 || code == 204) {
			return json;
		}
		return "";
	}

	public static String httpPost(Map<String, Object> map) throws Exception {
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httppost
		HttpPost httppost = new HttpPost(map.get("URL").toString());
		// 设置请求头
		httppost.addHeader(HTTP.CONTENT_TYPE, MiguConstants.APPLICATION_JSON);
		String jsonstr = JSON.toJSONString(map);
		StringEntity se = new StringEntity(jsonstr, "UTF-8");
		String params = EntityUtils.toString(se, "UTF-8");
		log.info("POST请求传参信息："+params);
		se.setContentType(MiguConstants.CONTENT_TYPE_TEXT_JSON);
		se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, MiguConstants.APPLICATION_JSON));
		httppost.setEntity(se);
		CloseableHttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();
		String json = EntityUtils.toString(entity, "UTF-8");
		int code = response.getStatusLine().getStatusCode();
		log.info("用户认证返回信息："+json);
		if (code == 200 || code == 204) {
			return json;
		}
		return "";
	}

}
