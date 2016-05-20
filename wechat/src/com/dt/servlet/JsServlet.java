package com.dt.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.dt.entity.JsConfig;
import com.dt.util.Sign;
import com.dt.util.TokenUtil;

public class JsServlet extends HttpServlet{

	private static final long serialVersionUID = -7089394283434828469L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		request.setCharacterEncoding("utf-8");
//		String url = request.getParameter("url");
//		String ticket = TokenUtil.getInstance().jsTicket.getTicket();
//		Map<String, String> map = Sign.sign(ticket, url);
//		String  config = JSON.toJSONString(map);
//		JsConfig jsConfig = JSON.parseObject(config, JsConfig.class);
//		request.setAttribute("appId", Constants.AppID);
//		request.setAttribute("timestamp", map.get("timestamp"));
//		request.setAttribute("nonceStr", map.get("nonceStr"));
//		request.setAttribute("signature", map.get("signature"));
//		response.getWriter().print(jsConfig);
		request.getRequestDispatcher("./js.jsp").forward(request, response);
		//System.out.println(url);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		try {
			String url = request.getParameter("url");
			String ticket = TokenUtil.getInstance().jsTicket.getTicket();
			Map<String, String> map = Sign.sign(ticket, url);
			String  configString = JSON.toJSONString(map);
			JsConfig config = JSON.parseObject(configString, JsConfig.class);
	//		request.setAttribute("appId", Constants.AppID);
	//		request.setAttribute("timestamp", map.get("timestamp"));
	//		request.setAttribute("nonceStr", map.get("nonceStr"));
	//		request.setAttribute("signature", map.get("signature"));
			response.getWriter().print(JSON.toJSONString(config));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
