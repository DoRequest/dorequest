package com.dt.util;

public class Constants {
	public static final String BASE_API_URL = "https://api.weixin.qq.com/";
	public static final String AppID = "wx0a24d36ada1eae50";// 应用id
	public static final String AppSecret = "8197ffb933c374507b90320b0c740ef1";// 应用密钥
	public static final String TOKEN = "CDJB";
	public static final String TEXT = "text";//文本消息
	public static final String IMAGE = "image";//图片消息
	public static final String LINK = "link";//连接消息
	public static final String LOCATION = "location";//地理位置消息
	public static final String VOICE = "voice";//声音消息
	public static final String VIDEO = "video";//视频消息
	public static final String EVENT = "event";//事件消息
	/**
	 * 菜单相关事件
	 */
	public static final String SUBSCRIBE = "subscribe";// 订阅
	public static final String UNSUBSCRIBE = "unsubscribe";// 取消订阅
	public static final String CLICK = "CLICK";// 菜单点击事件
	public static final String VIEW = "VIEW";// 点击菜单跳转链接
	public static final String LOCATION_PALCE = "LOCATION";// 上报地理位置事件
	public static final String SCAN = "SCAN";//已经关注的用户再次关注
	/**
	 * 自定义菜单事件推送
	 */
	public static final String SCANCODEPUSH = "scancode_push";//扫码推事件的事件推送
	public static final String SCANCODEWAITMSG = "scancode_waitmsg";//扫码推事件且弹出“消息接收中”提示框的事件推送
	public static final String PICSYSPHOTO = "pic_sysphoto";//弹出系统拍照发图的事件推送
	public static final String PICPHOTOORALBUM = "pic_photo_or_album";//弹出拍照或者相册发图的事件推送
	public static final String PICWEIXIN = "pic_weixin";//弹出微信相册发图器的事件推送
	public static final String LOCATIONSELECT = "location_select";//弹出地理位置选择器的事件推送
	/**
	 * 请求方式
	 */
	public static final String POST="POST";
	public static final String GET="GET";
}
