package com.gscx.ssm.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * 跨域过滤器
 * 
 * @author jiangyonghua
 * @date 2016年6月18日 上午11:02:49
 */
public class CorsFilter implements Filter {

	
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		// 允许的来源，如果预检请求成功，该来源将与请求中的来源标头匹配
		httpResponse.setHeader("Access-Control-Allow-Origin", "*");
		// 指示是否可使用凭据发出实际请求。 此标头始终设置为 true
		httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
		// 如果预检请求成功，此标头将设置为针对请求标头 Access-Control-Request-Method 指定的值
		httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, HEAD, OPTIONS, DELETE, PUT");
		// 指定允许用户代理缓存预检请求以用于将来的请求的时间长度
		httpResponse.setHeader("Access-Control-Max-Age", "3600");
		// 如果预检请求成功，此标头将设置为针对请求标头 Access-Control-Request-Headers 指定的值
		httpResponse.setHeader("Access-Control-Allow-Headers",
				"Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");

		chain.doFilter(request, response);
	}

	
	public void destroy() {
		// TODO Auto-generated method stub

	}
}
