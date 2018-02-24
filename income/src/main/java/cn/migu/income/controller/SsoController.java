package cn.migu.income.controller;

import cn.migu.income.pojo.MiguUsers;
import cn.migu.income.service.IMiguSsoService;
import cn.migu.income.util.MiguConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IDEA
 * User:lushengpeng
 * Date:2017/8/3
 * Time:9:38
 */
@Controller
@RequestMapping(value = "/sso")
public class SsoController {

    final static Logger log = LoggerFactory.getLogger(SsoController.class);

    @Autowired
    private IMiguSsoService iMiguSsoService;

    @RequestMapping(value = "/getSsoUrl", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getSsoUrl(HttpSession session, String url) {
        MiguUsers user = (MiguUsers) session.getAttribute(MiguConstants.USER_KEY);
        String userName = user.getUserId();
        String password = (String) session.getAttribute("password");
        String newUrl = iMiguSsoService.getUrl(url, userName, password);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", 100);
        map.put("message", newUrl);
        return map;
    }


}
