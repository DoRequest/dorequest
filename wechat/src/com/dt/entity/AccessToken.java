package com.dt.entity;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * 类名称：AccessToken   
 * 类描述：    接口访问凭证
 * 创建人：luoj  
 * 创建时间：2015年7月15日 下午9:49:21
 */
public class AccessToken {
	// 获取到的凭证
	@JSONField(name="access_token")
	private String token;
	// 凭证有效时间，单位：秒
	@JSONField(name="expires_in")
	private long expiresIn;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String toString(){
		return "access_token="+token+",expires_in="+expiresIn;
	}
}