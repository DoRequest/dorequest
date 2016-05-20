package com.dt.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.dt.entity.Subscribe;
import com.dt.util.CodeUtil;
import com.dt.util.StringUtil;
import com.dt.util.XmlParser;

@SuppressWarnings("serial")
public class MyServlet extends HttpServlet{

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			String code = req.getParameter("code");//菜单点击进来
			System.err.println(code);
			String openId = check(req);
			if(StringUtil.isEmpty(openId)){//新添加
				openId = CodeUtil.getOpenId(code);
				Cookie cookie = new Cookie("openId", openId);
				resp.addCookie(cookie);
			}
			Subscribe subscribe = XmlParser.getUserInfo(openId);//关注者信息
//			Subscribe subscribe = new Subscribe();
//			subscribe.setSex(1);
//			subscribe.setNickname("昵称");
//			subscribe.setSubscribeTime(1437055846165L);
			System.out.println(JSON.toJSON(subscribe));
			req.setAttribute("sub", subscribe);
			req.getRequestDispatcher("./my.jsp").forward(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doPost(req, resp);
	}
	/**
	 * 检查是否有这个cookie
	 * @param request
	 * @return
	 */
	private String check(HttpServletRequest request){
		Cookie[] cookies = request.getCookies();
		if(null==cookies || cookies.length<=0){
			return null;
		}
		for(Cookie cookie : cookies){
			if(cookie.getName().equals("openId")
					&&StringUtil.isNotEmpty(cookie.getValue())){
				return cookie.getValue();
			}
		}
		return null;
	}
}
