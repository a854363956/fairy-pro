package com.fairy.config.interfaces;

import javax.servlet.ServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.fairy.models.dto.RequestDto;

import lombok.Data;

public interface FairyFilter {
	public @Data class Result{
		private boolean status;
		private String message;
		private String path;
	}
	/**
	 * 是否允许通过
	 * @param requestDto  请求的dto对象
	 * @param request     请求servlet,注意,这个只能读取数据,不能用于设置
	 * @return  如果返回为true表示允许,false表示禁止
	 */
	Result isAllow(RequestDto<JSONObject> requestDto ,ServletRequest request);
}
