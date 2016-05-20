package com.dt.entity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dt.exception.WeixinException;
import com.dt.util.XmlUtil;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 
 * 类名称：PicAndInfoXml   
 * 类描述：   图文消息测试类
 * 创建人：luoj  
 * 创建时间：2015年7月18日 下午11:59:23
 */
@XStreamAlias("xml")
public class PicAndInfoXml extends BaseInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7731073440209509245L;
	@XStreamAlias("ArticleCount")
	private int articleCount;
	private List<Article> Articles;
	public List<Article> getArticles() {
		return Articles;
	}
	public void setList(List<Article> Articles) {
		this.Articles = Articles;
		int size = this.Articles.size();
		if(size>10){
			throw new WeixinException("图文消息个数不能超过10个");
		}else {
			this.setArticleCount(size);
		}
	}
	public int getArticleCount() {
		return articleCount;
	}
	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}
	public static void main(String[] args) {
		PicAndInfoXml xml = new PicAndInfoXml();
		xml.setCreateTime(1000L);
		xml.setFromUserName("发送者");
		xml.setMsgType("消息类型");
		xml.setToUserName("接收者");
		List<Article> list = new ArrayList<Article>();
		for (int i = 0; i < 10; i++) {
			Article article1= new Article();
			article1.setTitle("标题"+i);
			article1.setPicUrl(null);
			article1.setUrl(null);
			article1.setDescription("描述");
			list.add(article1);
		}
		xml.setList(list);
		String xmlString = XmlUtil.toXml(xml);
		System.out.println(xmlString);
	}	
}
