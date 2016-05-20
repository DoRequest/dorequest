package com.dt.entity;

import java.io.Serializable;
import java.util.Calendar;

import com.thoughtworks.xstream.annotations.XStreamAlias;
public class BaseInfo implements Serializable{
	private static final long serialVersionUID = -894164985314882391L;
	/**
	 * @XStreamAlias("message") 别名注解  
		作用目标: 类,字段  
		@XStreamImplicit 隐式集合  
		@XStreamImplicit(itemFieldName="part")  
		作用目标: 集合字段  
		@XStreamConverter(SingleValueCalendarConverter.class) 注入转换器  
		作用目标: 对象  
		@XStreamAsAttribute 转换成属性  
		作用目标: 字段  
		@XStreamOmitField 忽略字段  
		作用目标: 字段  
	 */
	@XStreamAlias("ToUserName")
	private String toUserName;
	@XStreamAlias("FromUserName")
	private String fromUserName;
	@XStreamAlias("CreateTime")
	private Long createTime=Calendar.getInstance().getTimeInMillis();
	@XStreamAlias("MsgType")
	private String msgType="news";
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
}
