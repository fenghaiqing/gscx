package cn.migu.income.controller;

import cn.migu.income.pojo.MiguFunc;
import cn.migu.income.pojo.MiguUsers;
import cn.migu.income.service.IMiguFuncService;
import cn.migu.income.service.IMiguUsersService;
import cn.migu.income.service.impl.MiguOptLogger;
import cn.migu.income.util.MiguConstants;
import cn.migu.income.util.PropValue;
import cn.migu.income.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static cn.migu.income.util.incomeSSO.HttpRequestUtils.doPostSSL;

@Controller
@RequestMapping("/Users")
public class MiguUsersController {
	final static Logger log = LoggerFactory.getLogger(MiguUsersController.class);

	@Autowired
	private IMiguUsersService usersService;

	@Autowired
	private IMiguFuncService funcService;

	@Autowired
	private MiguOptLogger dbLog;

	/**
     * 登录
     *
     * @author chentao
	 * @param req
	 * @param session
	 * @return
     * @see [类、类#方法、类#成员]
     */
	@RequestMapping("/login")
	public @ResponseBody Map<String, Object> selectOne(HttpServletRequest req, HttpSession session) {
        log.info("两非收入管理系统登录：用户名=" + req.getParameter("username"));
        Map<String, Object> map = new HashMap<String, Object>();
		try {
			MiguUsers users = new MiguUsers();
			String userId = (String) req.getParameter("username");
			String pass = (String) req.getParameter("password");
			session.setAttribute("password", pass);
			String nowCheckCodeValue = req.getParameter("checkCodeValue");
			String checkCodeValue = (String) req.getSession().getAttribute("checkCodeValue");
			long nowCode = (long) req.getSession().getAttribute("nowCode");
			long loseTime = nowCode + 1000 * 60 * 2;
			long currentTime = System.currentTimeMillis();
			if (loseTime < currentTime) {
				map.put("reCode", 102);
                map.put("reStr", "验证码超时!");
                log.info("验证码超时!");
                return map;
			}

			if (!nowCheckCodeValue.toUpperCase().equals(checkCodeValue)) {
				map.put("reCode", 101);
                map.put("reStr", "验证码错误!");
                log.info("验证码错误!");
                return map;
			}
			if ("0".equals(userId)) {
				String nowDate = StringUtil.getCurrDate();
				users.setUserId(userId);
				MiguUsers user = usersService.getUniqueUser(users);
				// String pwdDate = user.getPwdDate();
				String mdpass = StringUtil.md5(pass);
				if (user != null) {
					String startDate = user.getStartDate();
					String endDate = user.getEndDate();
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					Date now_date = format.parse(nowDate);
					Date start_date = format.parse(startDate.replace("/", "-"));
					Date end_date = format.parse(endDate.replace("/", "-"));
                    // 该用户不在有效期内
                    if (now_date.getTime() < start_date.getTime()) {
						map.put("reCode", 9);
                        map.put("reStr", "该用户不在有效期内");

                        log.info("该用户不在有效期内!");
                        return map;
					}
                    // 超过用户有效期
                    else if (now_date.getTime() > end_date.getTime()) {
						map.put("reCode", 9);
                        map.put("reStr", "用户过期");
                        log.info("用户过期!");
                        return map;
					}
					if (!mdpass.equals(user.getPassword())) {
						map.put("reCode", 1);
                        map.put("reStr", "密码错误!");
                        log.info("密码错误!");
                        return map;
					} else {
						session.setAttribute(MiguConstants.USER_KEY, user);
						dbLog.loginLog(req,userId, "用户登录成功", "登录用户ID：" + user.getUserId());
                        map.put("reCode", 100);
						map.put("reStr", "");
                        log.info("用户登录成功!");
                        return map;
					}
				}
			}
			Map<String, Object> param = new HashMap<>();
			param.put("username", userId);
			param.put("password", pass);
			param.put("URL", PropValue.getPropValue(MiguConstants.SSO_USER_AUTH));
			
			
			
			
            JSONObject json = new JSONObject();
            json.put("username", userId);
            json.put("password", pass);
            String result = doPostSSL(PropValue.getPropValue(MiguConstants.SSO_USER_AUTH), json);
            
            log.info("查看返回值"+result);
            
            param = JSON.parseObject(result);
            
            
            
            
            

			if (param != null && "000000".equals(param.get("code").toString())) {

				users.setUserId(userId);

				MiguUsers user = usersService.getUniqueUser(users);

				String nowDate = StringUtil.getCurrDate();
				if (user != null) {
					String startDate = user.getStartDate();
					String endDate = user.getEndDate();
					// String pwdDate = user.getPwdDate();
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					Date now_date = format.parse(nowDate);
					Date start_date = format.parse(startDate.replace("/", "-"));
					Date end_date = format.parse(endDate.replace("/", "-"));
					// Date pwd_Date = format.parse(pwdDate.replace("/", "-"));

					// if (!pass.equals(user.getPassword()))
					// {
					// map.put("reCode", 1);
                    // map.put("reStr", "密码错误!");
                    // log.info("密码错误!");
                    // }
					// else
					// {
                    // 该用户不在有效期内
                    if (now_date.getTime() < start_date.getTime()) {
						map.put("reCode", 9);
                        map.put("reStr", "该用户不在有效期内");
                        log.info("该用户不在有效期内!");
                        return map;
					}
                    // 超过用户有效期
                    else if (now_date.getTime() > end_date.getTime()) {
						map.put("reCode", 9);
                        map.put("reStr", "用户过期");
                        log.info("用户过期!");
                        return map;
					}
                    // // 用户输入密码等于上一次的密码，跳转到密码修改页面
                    // else if (user.getLastPwd().equals(user.getPassword()))
					// {
					// map.put("reCode", 11);
					// map.put("reStr", "");
                    // log.info("用户输入密码等于上一次的密码!");
                    // }
                    // // 用户密码重置或者新增用户，需要强制跳转密码修改页面
                    // else if (StringUtil.md5(userId).equals(pass))
					// {
					// map.put("reCode", 11);
					// map.put("reStr", "");
                    // log.info("用户密码重置或者新增用户!");
                    // }
                    // // 密码更新时间已经过期，跳转到密码修改页面
                    // else if (now_date.getTime() > pwd_Date.getTime())
					// {
					// map.put("reCode", 11);
					// map.put("reStr", "");
                    // log.info("密码更新时间已经过期!");
                    // }
					// else {
					session.setAttribute(MiguConstants.USER_KEY, user);
					dbLog.loginLog(req,userId, "用户登录成功", "登录用户ID：" + user.getUserId());
                    map.put("reCode", 100);
					map.put("reStr", "");
                    log.info("用户登录成功!");
                    return map;
					// }
				} else {
					map.put("reCode", 9);
                    map.put("reStr", "账号不存在!");
                    log.info("账号不存在，请联系管理员申请账号!");
                    return map;
				}
				 }else{
				 map.put("reCode", 9);
                map.put("reStr", "用户验证未通过!");
                log.info("用户不存在!");
            }
//			} else {
//				map.put("reCode", 9);
//				map.put("reStr", param.get("message").toString());
//				log.info("用户账号错误!");
//				return map;
//			}
		} catch (Exception e) {
			log.info("Exception:", e);
			e.printStackTrace();
			map.put("reCode", 101);
            map.put("reStr", "数据库连接错误!");
        }
		return map;
	}

	/**
     * 跳转到主页面
     *
     * @author chentao
	 * @param req
	 * @param session
	 * @return
     * @see [类、类#方法、类#成员]
     */

	@RequestMapping("/main")
	public ModelAndView main(HttpServletRequest req, HttpSession session) {
        log.info("登录成功，跳转到主页面");
        try {
			ModelAndView mav = new ModelAndView();
			MiguUsers user = (MiguUsers) session.getAttribute(MiguConstants.USER_KEY);
			List<MiguFunc> funcList = funcService.getFuncListByUserId(user.getUserId());
			String funcs = "";
			if (funcList != null && funcList.size() > 0) {
				ObjectMapper mapper = new ObjectMapper();
				try {
					funcs = mapper.writeValueAsString(funcList);
				} catch (JsonProcessingException e) {
					log.info("Exception:", e);
					e.printStackTrace();
				}
			}
			if (funcs.length() <= 0) {
				funcs = "null";
			}
			// if(StringUtil.isNotEmpty(tablesName)){
			// mav.addObject("tabsName", tablesName);
			// }else{
			// mav.addObject("tabsName", null);
			// }
			session.setAttribute("funcList", funcList);
			mav.addObject("funcList", funcs);
			mav.setViewName("main");
			return mav;
		} catch (Exception e) {
			log.info("Exception:", e);
			e.printStackTrace();
			return null;
		}
	}

	/**
     * 退出页面，销毁session
     *
     * @param req
	 * @param session
	 * @return
     * @see [类、类#方法、类#成员]
     */
	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest req, HttpSession session) {
        log.info("退出页面，销毁session");
        try {
			ModelAndView mav = new ModelAndView();
			session.invalidate();
			mav.setViewName("login");
			return mav;
		} catch (Exception e) {
			log.info("Exception:", e);
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping("/init")
	/**
     * chentao <登录验证码> <功能详细描述>
     *
     * @param request
	 * @param response
	 * @return
	 * @throws IOException
     * @see [类、类#方法、类#成员]
     */
	public ModelAndView createCheckCode(HttpServletRequest request, HttpServletResponse response) throws IOException {

		long nowCode = new Date().getTime();
        request.getSession().setAttribute("nowCode", nowCode);// 验证码创建时间

        // 验证码图片的宽度。
        int width = 60;

        // 验证码图片的高度。
        int height = 20;

        // 验证码字符个数
        int codeCount = 4;

        // 随机字符左右距离
        int marginLeft = 12;

        // 字体高度
        int fontHeight = 15;

        // 验证码字符与图片上边距的距离
        int marginTop = 15;

        // 基础字符
        char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S',
				'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9' };

        // 初始化图像的Buffer对象
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // 创建图像画布
        Graphics2D g = bufferedImage.createGraphics();

        // 初始化一个随机数生成器类
        Random random = new Random();

        // 将图像填充为白色
        g.setColor(Color.WHITE);

        // 图像上实心白色矩形框
        g.fillRect(0, 0, width, height);

        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        StringBuffer randomCode = new StringBuffer();
		int red = 0;
		int green = 0;
		int blue = 0;

        // 创建字体，字体的大小应该根据图片的高度来定。
        Font fontCode = new Font("Microsoft YaHei", Font.BOLD, fontHeight);
        // 设置字体。
        g.setFont(fontCode);

        // 随机产生基础字符内的验证码。
        for (int i = 0; i < codeCount; i++) {
            // 在上面定义的基础字符内随机产生验证码
            String strRand = String.valueOf(codeSequence[random.nextInt(32)]);

            // RGB随机产生值，这样输出的每位数字的颜色值都不同
            red = random.nextInt(255);
			green = random.nextInt(255);
			blue = random.nextInt(255);

            // 将随机产生的颜色值配置在验证码中
            g.setColor(new Color(red, green, blue));

            // 旋转文本
            // g.rotate(random.nextInt(2) * Math.PI / 180);
            // 平移原点到图形环境的中心 ,这个方法的作用实际上就是将字符串移动到某一个位置
            // g.translate(1, 1);

			g.drawString(strRand, (i + 1) * marginLeft - 5, marginTop);

            // 组合产生的验证码
            randomCode.append(strRand);
		}

		// System.out.println(randomCode.toString());

        // 将产生的随机数存在session中，用作比对页面上输入的验证码值
        request.getSession().setAttribute("checkCodeValue", randomCode.toString());
        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

        // 设置图像返回客户端类型
        response.setContentType("image/jpeg");

        // 将图像以流的方式输出
        ServletOutputStream sos = response.getOutputStream();
		ImageIO.write(bufferedImage, "jpeg", sos);
		sos.close();
		return null;
	}

	/**
     * 查询子菜单 <一句话功能简述> <功能详细描述>
     *
     * @param session
	 * @param request
	 * @param response
	 * @return
     * @see [类、类#方法、类#成员]
     */
	@RequestMapping(value = "/querySubmenu", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> querySubmenu(HttpSession session, HttpServletRequest request,
			HttpServletResponse response, String pid) {
		Map<String, Object> map = new HashMap<String, Object>();
		MiguUsers user = (MiguUsers) session.getAttribute(MiguConstants.USER_KEY);
		List<MiguFunc> funcList = (List<MiguFunc>) session.getAttribute("funcList");
		String menus = "";
		try {
			List<Map<String, Object>> menuList = funcService.querySubmenu(pid, user.getUserId(), funcList);
			ObjectMapper mapper = new ObjectMapper();
			menus = mapper.writeValueAsString(menuList);
		} catch (Exception e) {
			log.error("Exception:", e);
		}
		map.put("reCode", 100);
		map.put("reStr", menus);
		return map;
	}

	@RequestMapping(value = "/queryPriceCommitteeUser", method = RequestMethod.POST)
	@ResponseBody
	public List<MiguUsers> queryPriceCommitteeUser() {
		try {
			return usersService.queryPriceCommitteeUser();
		} catch (Exception e) {
			log.error("Exception:", e);
			return null;
		}
	}
}
