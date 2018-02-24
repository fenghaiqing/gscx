package cn.migu.income.util.incomeSSO;

import cn.migu.income.util.PropValue;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static cn.migu.income.util.incomeSSO.HttpRequestUtils.doPostSSL;

/**
 * 单点登录到SSO
 * Created with IDEA
 * User:lushengpeng
 * Date:2017/8/1
 * Time:13:54
 */
public class BillSSO {

    final static Logger log = LoggerFactory.getLogger(BillSSO.class);

//    private static BillSSO instance;

    private String ssoUrl_TGST = null;

    private String ssoUrl_STS = null;

    public BillSSO() {
        //初始化的时候获取ssoUrl
        ssoUrl_TGST = PropValue.getPropValue("SSO_URL_TGTS");
        ssoUrl_STS = PropValue.getPropValue("SSO_URL_STS");
    }

   /* public static synchronized BillSSO getInstance() {
        if (instance == null) {
            instance = new BillSSO();
        }
        return instance;
    }*/

    public String getStId(String url, String userName, String password) {
        String tgts = getTgts(userName, password);
        JSONObject json = new JSONObject();
        String sUrl = null;
        if (url.contains("?")) sUrl = url.substring(0, url.indexOf("?"));
        json.put("service", sUrl);
        json.put("tgtId", tgts);
        String result = doPostSSL(ssoUrl_STS, json);
        JSONObject jsonResult = JSONObject.parseObject(result);

        if (jsonResult.get("code").equals("000000")) {
            return JSONObject.parseObject(jsonResult.get("body").toString()).get("stId").toString();
        } else {
            tgts = getTgts(userName, password);
            return null;
        }
    }

    private String getTgts(String userName, String password) {
        if (ssoUrl_TGST == null) {
            log.error("ssoUrl_TGST地址为空，检查配置文件是否配置了URL！");
        }
        JSONObject json = new JSONObject();
        json.put("username", userName);
        json.put("password", password);
        String result = doPostSSL(ssoUrl_TGST, json);
        JSONObject jsonResult = JSONObject.parseObject(result);
        if (jsonResult.get("code").equals("000000")) {
            String r = JSONObject.parseObject(jsonResult.get("body").toString()).get("tgtId").toString();
            return r;
        } else {
            log.error("获取tgts失败");
            return null;
        }
    }
}
