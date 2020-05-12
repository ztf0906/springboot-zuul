package com.mrxmgl.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Filter是Zuul的核心，用来实现对外服务的控制
 * @author Administrator
 * @date 2019/12/24 0024 10:19
 */
public class TokenFilter extends ZuulFilter{

	private static Logger log = LoggerFactory.getLogger(TokenFilter.class);

	@Override
	public String filterType(){//过滤器的类型，它决定过滤器在请求的哪个生命周期中执行。这里定义为pre,代表会在请求被路由之前执行。有前置、后置和路由过滤器。pre：可以在请求被路由之前调用。routing： 路由请求时被调用。post：在routing和error过滤器之后被调用。error：处理请求时发生错误时被调用
		return "pre";
	}

	@Override
	public int filterOrder(){
		return 0;//filter执行顺序，通过数字指定，优先级为0
	}

	@Override
	public boolean shouldFilter() {
		return true;//是否执行该过滤器，返回了true,因此该过滤器对所有请求都会生效。实际运用中我们可以利用该函数来指定过滤器的有效范围。
	}

	@Override
	public Object run() {//过滤器的具体逻辑
		//获取上下文
		RequestContext ctx = RequestContext.getCurrentContext();
		//获取Request
		HttpServletRequest request = ctx.getRequest();
		//获取请求参数accessToken
		String accessToken = request.getParameter("accessToken");
		//使用String工具类
		if (StringUtils.isBlank(accessToken)) {
			log.warn("accessToken is empty");
			ctx.setSendZuulResponse(false);  //进行拦截
			ctx.setResponseStatusCode(401);
			try {
				ctx.getResponse().getWriter().write("accessToken is empty");
			} catch (Exception e) {
			}
			return null;
		}
		log.info("access is ok");
		return null;
	}
}