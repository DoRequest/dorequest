package com.dt.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 类名称：TulingUtil   
 * 类描述：   调用图灵机器人api接口，获取智能回复内容
 * 创建人：luoj  
 * 创建时间：2015年7月17日 上午9:26:54
 */
public class TulingUtil {
	/**
	 * 调用图灵机器人api接口，获取智能回复内容，解析获取自己所需结果
	 * @param content
	 * @return
	 */
	public static String getTulingResult(String content){
		/** 此处为图灵api接口，参数key需要自己去注册申请 */
		String apiUrl = "http://www.tuling123.com/openapi/api?key=995335878634920de471b4dd95d62d5d&info=";
		String param = "";
		try {
			param = apiUrl+URLEncoder.encode(content,"utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} //将参数转为url编码
		/** 发送httpget请求 */
		String result = "";
		result = GetRequestUtil.getJson(param, Constants.GET, null);
		/** 请求失败处理 */
		if(StringUtil.isEmpty(result)){
			return "亲，你说的话真是太高深了……";
		}
		try {
			JSONObject json = JSON.parseObject(result);
			//以code=100000为例，参考图灵机器人api文档
//			if(200000==json.getIntValue("code")){
				result = json.getString("text");
//			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	public static void main(String[] args) {
		String result = TulingUtil.getTulingResult("who i am");
		System.out.println(result);
	}
}
