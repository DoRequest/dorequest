package com.dt.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.dt.enums.OauthScope;
import com.dt.exception.WeixinException;


public class GetRequestUtil {
	public static String getJson(String url,String type,String json) {
		URL u = null;
		HttpURLConnection conn = null;
		InputStream in = null;
		StringBuffer sb = new StringBuffer();
		InputStreamReader isreader = null;
		BufferedReader bufferedReader = null;
		try {
			u = new URL(url);
			conn = (HttpURLConnection) u.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod(type);
			conn.connect();
			if(StringUtil.isNotEmpty(json)){
				OutputStream  out = conn.getOutputStream();
                out.write(json.getBytes("UTF-8"));
                out.flush();
                out.close();
			}
			in = conn.getInputStream();
			isreader = new InputStreamReader(in, "utf-8");
			bufferedReader = new BufferedReader(isreader);
			String temp = null;
			while ((temp = bufferedReader.readLine()) != null) {
				sb.append(temp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
				try {
					if(null!=bufferedReader) bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					if(null!=isreader) isreader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return sb.toString();
	}
	 /**
     * 生成回调url，这个结果要求用户在微信中打开，即可获得token，并指向redirectUrl
     *
     * @param redirectUrl 用户自己设置的回调地址
     * @param scope       授权作用域
     * @param state       用户自带参数
     * @return 回调url，用户在微信中打开即可开始授权
     */
    public static String getOauthPageUrl(String redirectUrl, OauthScope scope, String state) {
        String userState = StringUtil.isEmpty(state) ? "STATE" : state;
        String url = null;
        try {
            url = URLEncoder.encode(redirectUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new WeixinException("朋友，链接处错咯！！！");
        }
        StringBuilder stringBuilder = new StringBuilder("https://open.weixin.qq.com/connect/oauth2/authorize?");
        stringBuilder.append("appid=").append(Constants.AppID)
                .append("&redirect_uri=").append(url)
                .append("&response_type=code&scope=").append(scope.toString())
                .append("&state=")
                .append(userState)
                .append("#wechat_redirect");
        return stringBuilder.toString();
    }
}
