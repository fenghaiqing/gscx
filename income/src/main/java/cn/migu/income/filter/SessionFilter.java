package cn.migu.income.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.migu.income.util.MiguConstants;

/**
 * 
 * session过滤器，验证用户session是否超时
 * 
 * @author  guanyuzhuang
 * @version  [版本号, 2016年1月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SessionFilter implements Filter
{
    
    @Override
    public void destroy()
    {
    
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException
    {
        String path = ((HttpServletRequest)request).getServletPath();
        String tablsName = ((HttpServletRequest)request).getParameter("tabsName");
        Object user = ((HttpServletRequest)request).getSession().getAttribute(MiguConstants.USER_KEY);
        
        if (path.indexOf("Login") > 0 || path.indexOf("login") > 0 || path.indexOf("pwdModify") > 0
            || path.indexOf("modUserPassword") > 0|| path.indexOf("init") > 0)
        {
            chain.doFilter(request, response);
        }
        else if (path.endsWith(".do") && user == null)
        {
            //如果是ajax请求响应头会有，x-requested-with  
            if (((HttpServletRequest)request).getHeader("x-requested-with") != null
                && ((HttpServletRequest)request).getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest"))
            {
                //在响应头设置session状态  
                ((HttpServletResponse)response).setHeader("sessionstatus", "timeout");
            }
            else
            {
                //跳转到登陆页面
                String contextPath = ((HttpServletRequest)request).getContextPath();
                ((HttpServletResponse)response).sendRedirect(contextPath + "/manage/toLogin.do");
            }
        }
        else
        {
        	if(path.contains("main.do")){
        		request.setAttribute("tabsName", tablsName);
        	}
            chain.doFilter(request, response);
        }
        
    }
    
    @Override
    public void init(FilterConfig arg0)
        throws ServletException
    {
    
    }
    
}
