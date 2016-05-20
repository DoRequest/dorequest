package com.dt.util;

import com.dt.entity.AccessToken;

/**
 * 
 * 类名称：TokenThread 
 * 类描述： 定时获取微信access_token的线程
 * 创建人：luoj 
 * 创建时间：2015年7月15日
 * 下午10:22:18
 */
public class TokenThread implements Runnable {
	public static volatile AccessToken accessToken = null;

	@Override
	public void run() {
		while (true) {
			try {
				AccessToken token = new AccessToken(); // 从微信服务器刷新access_token
				token.setExpiresIn(7200);
				token.setToken("token");
				if (token != null) {
					accessToken=token;
				} else {
					System.err.println("----------------获取access_token失败咯，，亲！----------------");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (null != accessToken) {
					Thread.sleep((accessToken.getExpiresIn() - 200) * 1000); // 休眠7000秒
				} else {
					Thread.sleep(60 * 1000); // 如果access_token为null，60秒后再获取
				}
			} catch (InterruptedException e) {
				try {
					Thread.sleep(60 * 1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public static AccessToken getAccessToken() {
		return accessToken;
	}

	public static void setAccessToken(AccessToken accessToken) {
		TokenThread.accessToken = accessToken;
	}
	public static void main(String[] args) {
		try {
			Thread thread = new Thread(new TokenThread());
			thread.start();
			System.out.println(TokenThread.accessToken);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
