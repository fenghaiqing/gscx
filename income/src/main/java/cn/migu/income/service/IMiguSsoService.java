package cn.migu.income.service;

/**
 * Created with IDEA
 * User:lushengpeng
 * Date:2017/8/3
 * Time:9:44
 */
public interface IMiguSsoService {
    /**
     * 根据url获取ssoUrl
     *
     * @param url
     * @return
     */
    public String getUrl(String url, String userName, String password);
}
