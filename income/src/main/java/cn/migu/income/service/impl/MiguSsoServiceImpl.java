package cn.migu.income.service.impl;

import cn.migu.income.service.IMiguSsoService;
import cn.migu.income.util.incomeSSO.BillSSO;
import org.springframework.stereotype.Service;

/**
 * Created with IDEA
 * User:lushengpeng
 * Date:2017/8/3
 * Time:9:47
 */
@Service
public class MiguSsoServiceImpl implements IMiguSsoService {

    @Override
    public String getUrl(String url, String userName, String password) {
        BillSSO instance = new BillSSO();
        String ticket = instance.getStId(url, userName, password);
        if (ticket == null) {
            ticket = instance.getStId(url, userName, password);
        }
        return url + "%26ticket=" + ticket;
    }
}
