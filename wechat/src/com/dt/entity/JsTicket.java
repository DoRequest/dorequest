package com.dt.entity;

import com.alibaba.fastjson.annotation.JSONField;
/**
 * 
 * 类名称：JsTicket   
 * 类描述：js票据权限   
 * 创建人：luoj  
 * 创建时间：2015年7月20日 下午4:29:35
 */
public class JsTicket extends WechatException{
	private String ticket;
	@JSONField(name="expires_in")
	private String expiresIn;
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public String getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}
	
}
