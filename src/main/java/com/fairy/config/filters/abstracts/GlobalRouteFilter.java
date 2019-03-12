package com.fairy.config.filters.abstracts;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fairy.config.filters.interfaces.FairyFilter;
import com.fairy.config.filters.interfaces.FairyFilter.Result;
import com.fairy.models.common.RequestWrapper;
import com.fairy.models.dto.RequestDto;
import com.fairy.models.dto.ResponseDto;
import com.fairy.models.exception.FairyException;
import com.google.common.base.Charsets;
/**
 * 	路由拦截器,基于Spring 实现的拦截,通过重写getApplicationContext,以及实现FairyFilter来注册拦截器
 */
public abstract class GlobalRouteFilter implements Filter {
	protected Logger logger = LoggerFactory.getLogger(this.getClass()); 
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		RequestWrapper requestWrapper = new RequestWrapper((HttpServletRequest)request);
		RequestDto<JSONObject> requestDto = JSON.parseObject(requestWrapper.toString(),new TypeReference<RequestDto<JSONObject>>() {});
		
		Map<String, FairyFilter> filters = getApplicationContext().getBeansOfType(FairyFilter.class);
		
		// 开始调用拦截器内容
		for(Map.Entry<String,FairyFilter> entry : filters.entrySet()) {
			try {
				Result result = entry.getValue().isAllow(requestDto, (HttpServletRequest)request);
				if(!result.isStatus()) {
					throw new FairyException(result.getMessage());
				}
			} catch (Exception e) {
				logger.error("GlobalRouteFilter", e);
				ResponseDto<String> resp = new ResponseDto<>();
				resp.setData(e.getMessage());
				resp.setMessage(e.getMessage());
				resp.setStatus(510);
				response.getOutputStream().write(JSON.toJSONString(resp).getBytes(Charsets.UTF_8));
				return ;
			}
			
		}
		// 结束
		
		// 放行到下一个拦截器
		chain.doFilter(requestWrapper, response);
	}
	
	
	/**
	 *  获取Spring的上下文
	 * @return
	 */
	protected abstract ApplicationContext getApplicationContext();
}
