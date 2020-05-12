package com.mrxmgl;

import com.mrxmgl.filter.TokenFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * http://www.zuidaima.com/share/3816522894216192.htm
 * https://blog.csdn.net/JinXYan/article/details/90736561
 */
@SpringBootApplication
@EnableZuulProxy//支持网关路由，声明一个Zuul代理。注解能注册到 eureka 服务上，是因为该注解包含了 eureka 客户端的注解，该 EnableZuulProxy 是一个复合注解
public class SpringbootZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootZuulApplication.class, args);
		System.out.println("zuul：默认情况下：Zuul代理所有注册到EurekaServer的微服务，路由规则： http://ZUUL_HOST:ZUUL_PORT/微服务实例名(serverId)/** 转发至serviceId对应的微服务");
		System.out.println("测试：路由和过滤都起作用");
		System.out.println("zuul-A：路由：http://localhost:8086/zuul/admin/admin/sys/login");
		System.out.println("zuul-A：过滤：http://localhost:8086/zuul/admin/admin/sys/login?accessToken=ribbon");
		System.out.println("zuul-B：路由：http://localhost:8086/zuul/fygl/fygl/bgtz/browse");
		System.out.println("zuul-B：过滤：http://localhost:8086/zuul/fygl/fygl/bgtz/browse?accessToken=ribbon");
	}

	/**
	 * 将TokenFilter加入到请求拦截队列
	 * @return
	 */
	@Bean
	public TokenFilter tokenFilter(){
		return new TokenFilter();
	}

}