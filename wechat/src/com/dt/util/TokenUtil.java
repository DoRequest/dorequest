package com.dt.util;
import com.alibaba.fastjson.JSON;
import com.dt.entity.AccessToken;
import com.dt.entity.JsTicket;
/**
 * 
 * 类名称：TokenUtil   
 * 类描述：   公众平台通用接口工具类
 * 创建人：luoj  
 * 创建时间：2015年7月15日 下午9:51:33
 */
public class TokenUtil {
	public static AccessToken accessToken = null;
	public static long time;
	public static JsTicket jsTicket = null;
	private static TokenUtil tokenUtil;
	private TokenUtil(){}
	public static synchronized TokenUtil getInstance(){
		if (tokenUtil == null) {  
			tokenUtil = new TokenUtil();  
		}  
//		accessToken = new AccessToken();
//		accessToken.setExpiresIn(7200);
//		accessToken.setToken("albq9nT6EBNa9FeX-omSMj5KlvhPCSD44bJd8Z4KIxF1575Ki530ONc33eQhKIVdXhgTglwwbBmAyisnpiacYMSdMsGmY71cTEpdkUp2kmg");
		tokenUtil.getToken();//先得到access_token
		return tokenUtil;  
	} 
	/**
	 * 第一次加载或者超时判断获取access_token
	 */
	private void getToken(){
		long now = System.currentTimeMillis();
		if(0==time||(now - time > 7100000)){//首次加载或者刷新/刷新官方给出7200秒过期时间 ，所以我们用7100秒来判断
			accessToken = getUrl();//先得到access_token
			jsTicket = getTiket(accessToken.getToken());//再得到jsSDK ticket
			time = System.currentTimeMillis();
		}
	}
	
	private AccessToken getUrl(){
		String url = Constants.BASE_API_URL+"cgi-bin/token?grant_type=client_credential&appid="
			+Constants.AppID+"&secret="+Constants.AppSecret;
		String json = GetRequestUtil.getJson(url,Constants.GET,null);
		AccessToken accessToken = JSON.parseObject(json, AccessToken.class);
		return accessToken;
	}
	public JsTicket getTiket(String accessToken){
		String url = Constants.BASE_API_URL+"cgi-bin/ticket/getticket?access_token="+accessToken+"&type=jsapi";
		String json = GetRequestUtil.getJson(url,Constants.GET,null);
		JsTicket js = JSON.parseObject(json, JsTicket.class);
		return js;
	}
}