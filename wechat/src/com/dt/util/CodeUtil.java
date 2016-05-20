package com.dt.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.dt.exception.WeixinException;
/**
 * 
 * 类名称：CodeUtil
 * 类描述： 获取微信Oauth授权code 
 * 创建人：luoj
 * 创建时间：2015年7月16日 下午3:13:34
 */
public class CodeUtil {
	/**
	 * 此方法只能使用一次，再次使用将过期，获取用户在所属公众号中的唯一标识openId
	 * 
	 * @param code
	 *            微信返回code
	 * @return
	 */
	public static String getOpenId(String code) {
		String url = Constants.BASE_API_URL + "sns/oauth2/access_token?appid=" + Constants.AppID
				+ "&secret=" + Constants.AppSecret + "&code=" + code
				+ "&grant_type=authorization_code";
		StringBuffer sb = new StringBuffer();
		InputStream ips = getInputStream(url);
		InputStreamReader isreader = null;
		try {
			isreader = new InputStreamReader(ips, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		BufferedReader bufferedReader = new BufferedReader(isreader);
		String temp = null;
		try {
			while ((temp = bufferedReader.readLine()) != null) {
				sb.append(temp);
			}
			bufferedReader.close();
			isreader.close();
			ips.close();
			ips = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Map<String, Object> map = getJsonMap(sb.toString());
			if(map.containsKey("errmsg")){
				throw new WeixinException("code无效或已经请求过！");
			}
			if(map.containsKey("openid")){//用户在公众号中的唯一标识
				return map.get("openid").toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private static Map<String, Object> getJsonMap(String json) {
		Map<String, Object> mapJson = (Map<String, Object>) JSON.parse(json);
		return mapJson;
	}
	/**
	 * 从请求的URL中获取返回的流数据
	 * 
	 * @param requestUrl
	 * @return InputStream
	 */
	private static InputStream getInputStream(String requestUrl) {
		URL url = null;
		HttpURLConnection conn = null;
		InputStream in = null;
		try {
			url = new URL(requestUrl);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			conn.connect();
			in = conn.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return in;
	}
}
