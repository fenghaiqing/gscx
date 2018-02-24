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

import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

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
public class SessionFilterMvc implements HandlerInterceptor
{
   
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

			String path =request.getRequestURI();
			//  String path = ((HttpServletRequest)request).getServletPath();
	        Object user = ((HttpServletRequest)request).getSession().getAttribute(MiguConstants.USER_KEY);
	        
	        if (path.indexOf("Login") > 0 || path.indexOf("login") > 0 || path.indexOf("pwdModify") > 0
	            || path.indexOf("modUserPassword") > 0|| path.indexOf("init") > 0 || path.indexOf("incomeOATask") > 0)
	        {
	            return true;
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
	            return false;
	        }
	        else
	        {
	        	
	        	return true;
	        }
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
    
}
