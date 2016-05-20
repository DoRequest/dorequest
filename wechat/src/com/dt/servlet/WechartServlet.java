package com.dt.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dt.util.ComfirmUtil;
import com.dt.util.XmlParser;

@SuppressWarnings("serial")
public class WechartServlet extends HttpServlet {
	/**
	 * 微信验证请求方式为get
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = null;
		try {
			// 微信加密签名
			String signature = request.getParameter("signature");
			// 时间戮
			String timestamp = request.getParameter("timestamp");
			// 随机数
			String nonce = request.getParameter("nonce");
			// 随机字符串
			String echostr = request.getParameter("echostr"); 
			out = response.getWriter();
			if(ComfirmUtil.checkSignature(signature, timestamp, nonce)){
				out.print(echostr);
				System.out.println("---------------微信接入恭喜验证通过了------------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null!=out) out.close();
		}
	}
	/**
	 * 微信发送的文本或者其他请求地址
	 */
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PrintWriter writer = null;
		resp.setCharacterEncoding("utf-8");
		try {
			String  xml = XmlParser.processRequest(req);
			System.out.println(xml);
			writer = resp.getWriter();
			writer.write(xml);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null!=writer) writer.close();
		}
	}

}
