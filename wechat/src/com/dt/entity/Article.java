package com.dt.entity;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("item")
public class Article implements Serializable{
	private static final long serialVersionUID = 3209089262833626767L;
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
	@XStreamAlias("Title")
	private String title;
	@XStreamAlias("Description")
	private String description;
	@XStreamAlias("PicUrl")
	private String picUrl;
	@XStreamAlias("Url")
	private String url;

	public Article() {

	}

	public Article(String title) {
		this.title = title;
	}

	public Article(String title, String url) {
		this.title = title;
		this.url = url;
	}

	public Article(String title, String description, String picUrl, String url) {
		this.title = title;
		this.description = description;
		this.picUrl = picUrl;
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
