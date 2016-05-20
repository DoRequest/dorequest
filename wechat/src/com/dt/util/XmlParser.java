package com.dt.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alibaba.fastjson.JSON;
import com.dt.entity.Article;
import com.dt.entity.BaseInfo;
import com.dt.entity.PicAndInfoXml;
import com.dt.entity.ReceiveXmlEntity;
import com.dt.entity.Subscribe;
import com.dt.enums.ResultType;
import com.dt.exception.WeixinException;
import com.sun.mail.handlers.text_html;

public abstract class XmlParser {
	public static TokenUtil tokenUtil;
	static{
		tokenUtil = TokenUtil.getInstance();
	}
	/**
	 * 解析微信xml消息
	 * 
	 * @param strXml
	 * @return
	 */
	public static ReceiveXmlEntity getMsgEntity(HttpServletRequest request) {
		ReceiveXmlEntity msg = null;
		String strXml = "";
		try {
			strXml = getXML(request);
			if (strXml.length() <= 0 || strXml == null)
				return null;
			// 将字符串转化为XML文档对象
			Document document = DocumentHelper.parseText(strXml);
			// 获得文档的根节点
			Element root = document.getRootElement();
			// 遍历根节点下所有子节点
			Iterator<?> iter = root.elementIterator();
			// 遍历所有结点
			msg = new ReceiveXmlEntity();
			// 利用反射机制，调用set方法
			// 获取该实体的元类型
			Class<?> c = Class.forName("com.dt.entity.ReceiveXmlEntity");
			msg = (ReceiveXmlEntity) c.newInstance();// 创建这个实体的对象
			while (iter.hasNext()) {
				Element ele = (Element) iter.next();
				// 获取set方法中的参数字段（实体类的属性）
				Field field = c.getDeclaredField(ele.getName());
				// 获取set方法，field.getType())获取它的参数数据类型
				Method method = c.getDeclaredMethod("set" + ele.getName(),
						field.getType());
				// 调用set方法
				method.invoke(msg, ele.getText());
			}
		} catch (Exception e) {
			System.out.println("xml 格式异常: " + strXml);
			e.printStackTrace();
		}
		return msg;
	}

	private static String getXML(HttpServletRequest request) {
		StringBuffer buffer = new StringBuffer();
		try {
			InputStream is = request.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String s = "";
			while ((s = br.readLine()) != null) {
				buffer.append(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(buffer);
		return buffer.toString();
	}
	/**
	 * 普通文本
	 * @param content
	 * @return
	 */
	public abstract String text();
	/**
	 * 列表文本
	 * @param content
	 * @return
	 */
	public abstract List<Article> listText();
	/**
	 * 处理关注者
	 * @param sub
	 * @return
	 */
	public abstract void handleSubscribe(Subscribe sub);
	/**
	 * 取消关注者
	 * @param sub
	 * @return
	 */
	public abstract void cancelSubscribe(String opneId);
	/**
	 * 关注消息提示
	 * @param sub
	 */
	public abstract String subscribeMessage();
	/**
	 * 处理微信服务器发来的请求方法
	 * 
	 * @param request
	 *            http请求对象
	 * @return 处理消息的结果，已经是接口要求的xml报文了
	 */
	public String processRequest(HttpServletRequest request) {
		// Map<String, Object> reqMap = MessageUtil.parseXml(request,
		// getToken(), getAppId(), getAESKey());
		// fromUserName = (String) reqMap.get("FromUserName");
		// toUserName = (String) reqMap.get("ToUserName");
		// String msgType = (String) reqMap.get("MsgType");
		// LOG.debug("收到消息,消息类型:{}", msgType);

		// BaseMsg msg = null;
		ReceiveXmlEntity entity = getMsgEntity(request);
		if (null == entity) {
			return "";
		}
		String result="";
		if (entity.getMsgType().equals(Constants.EVENT)) {// 事件类型
			String eventType = entity.getEvent();
			String ticket = entity.getTicket();
			// QrCodeEvent qrCodeEvent = null;
			if (StringUtil.isNotEmpty(ticket)) {
				String eventKey = entity.getEventKey();
			}
			if (eventType.equals(Constants.SUBSCRIBE)) {// 关注事件
//				System.out.println("-------------关注人员openId"
//						+ entity.getFromUserName() + "-----------------");
				Subscribe subscribe = getUserInfo(entity.getFromUserName());
				handleSubscribe(subscribe);//处理关注者信息
//				System.out.println("关注人员信息："+JSON.toJSON(subscribe));
				String message = subscribeMessage();
				if(StringUtil.isNotEmpty(message)){
					result = answerText(message, entity.getFromUserName(), entity.getToUserName());
				}else {
					result = answerText("感谢关注！！！", entity.getFromUserName(), entity.getToUserName());
				}
				
			} else if (eventType.equals(Constants.UNSUBSCRIBE)) {// 取消关注事件
				/**
				 * 此时无法根据openID获取-人员信息，因为openId是关注者在该公众号中的唯一标识，取消关注后改标识就无效了
				 * 注意openId不会随着取消关注而更改，下次关注的人员openId不会变化
				 */
//				System.out.println("------------取消关注人员openId" + entity.getFromUserName() + "-----------------");
				cancelSubscribe(entity.getFromUserName());
			} else if (eventType.equals(Constants.CLICK)) {
//				
			} else if (eventType.equals(Constants.VIEW)) {
//				
			} else if (eventType.equals(Constants.LOCATION)) {
				
			} else if (Constants.SCANCODEPUSH.equals(eventType)
					|| Constants.SCANCODEWAITMSG.equals(eventType)) {
				
			} else if (Constants.PICPHOTOORALBUM.equals(eventType)
					|| Constants.PICSYSPHOTO.equals(eventType)
					|| Constants.PICWEIXIN.equals(eventType)) {
				
			}
		} else {
			if (entity.getMsgType().equals(Constants.TEXT)) {
//				String url = request.getRequestURL().toString();
//				url = url.substring(0, url.lastIndexOf("/"));
//				//拼接回复消息xml
//				result = answerText("你也好", entity.getFromUserName(), entity.getToUserName());
//				//工具生成xml
//				PicAndInfoXml xml = new PicAndInfoXml();
//				xml.setFromUserName(entity.getToUserName());
//				xml.setToUserName(entity.getFromUserName());
//				List<Article> list = new ArrayList<Article>();
				/**
				 * 此处如果回复的消息只有一条的话那么描述就有用，如果多条的话图片描述是不会展示出来的
				 */
//				for (int i = 0; i < 1; i++) {
//					Article article1= new Article();
//					article1.setTitle("【这是个标题】 可以很长很长很长～～～～"+i);
//					article1.setDescription("这个就是图片描述了，哈哈哈哈哈哈哈哈哈哈～～～～～");
//					article1.setPicUrl(url+"/images/2.JPG");
				//	article1.setUrl(url+"/images/2.JPG");
//					list.add(article1);
//				}
//				xml.setList(list);
				//result = XmlUtil.toXml(xml);
				String message = text();
				if(StringUtil.isNotEmpty(message)){
					result =  answerText(message, entity.getFromUserName(), entity.getToUserName());
				}
				if(StringUtil.isEmpty(message)){
					List<Article> lists = listText();
					if(null!=lists && lists.size()>0){
						PicAndInfoXml xml = new PicAndInfoXml();
						xml.setFromUserName(entity.getToUserName());
						xml.setToUserName(entity.getFromUserName());
						List<Article> list = new ArrayList<Article>();
						/**
						 * 此处如果回复的消息只有一条的话那么描述就有用，如果多条的话图片描述是不会展示出来的
						 */
						for (Article article1:lists) {
							list.add(article1);
						}
						xml.setList(list);
						result = XmlUtil.toXml(xml);
					}
				}
			} else if (entity.getMsgType().equals(Constants.IMAGE)) {//图片
				
			} else if (entity.getMsgType().equals(Constants.VOICE)) {
				System.out.println("声音"+entity.getRecognition());
				result = answerText(entity.getRecognition(), entity.getFromUserName(), entity.getToUserName());
			} else if (entity.getMsgType().equals(Constants.VIDEO)) {//视频
//				
				
			} else if (entity.getMsgType().equals(Constants.LOCATION)) {//位置服务
				
			} else if (entity.getMsgType().equals(Constants.LINK)) {//连接
//				
			}
		}
//		String result = "";
//		if (nonNull(msg)) {
//			msg.setFromUserName(toUserName);
//			msg.setToUserName(fromUserName);
//			result = msg.toXml();
//			if (StrUtil.isNotBlank(getAESKey())) {
//				try {
//					WXBizMsgCrypt pc = new WXBizMsgCrypt(getToken(),
//							getAESKey(), getAppId());
//					result = pc.encryptMsg(result,
//							request.getParameter("timestamp"),
//							request.getParameter("nonce"));
//				} catch (AesException e) {
//					LOG.error("加密异常", e);
//				}
//			}
//		}
		return result;
	}

	/**
	 * 获取关注者信息
	 * 
	 * @param openid
	 *            关注者ID
	 * @return 关注者信息对象
	 */
	public static Subscribe getUserInfo(String openid) {
		if (StringUtil.isEmpty(openid)) {
			throw new WeixinException("警告：--------->"+"--------openid不能是空-------");
		}
		Subscribe subscribe = null;
		String accssToken = tokenUtil.accessToken.getToken();
		String url = Constants.BASE_API_URL
				+ "cgi-bin/user/info?access_token="+accssToken+"&lang=zh_CN&openid="
				+ openid;
		String result = GetRequestUtil.getJson(url,Constants.GET,null);
		try {
			subscribe = JSON.parseObject(result, Subscribe.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subscribe;
	}
	private boolean isSuccess(String errCode) {
		return ResultType.SUCCESS.getCode().toString().equals(errCode);
	}
	private static String answerText(String content,String openId,String fromUserName){
		Long returnTime = Calendar.getInstance().getTimeInMillis();
//		String answer = TulingUtil.getTulingResult(content);
		String xml = "<xml><ToUserName><![CDATA["+openId+"]]></ToUserName>"+
		  "<FromUserName><![CDATA["+fromUserName+"]]></FromUserName>"
		  +"<CreateTime>"+returnTime+"</CreateTime>"
		  +"<MsgType><![CDATA[text]]></MsgType>"
		  +"<Content><![CDATA["+content+"]]></Content>"
		  +"</xml>";
		return xml;
	}
	private static String answerVoice(String content,String openId,String fromUserName,String mediaId){
		Long returnTime = Calendar.getInstance().getTimeInMillis();
		String xml = "<xml><ToUserName><![CDATA["+openId+"]]></ToUserName>"+""
				+ "<FromUserName><![CDATA["+fromUserName+"]]></FromUserName>"
				+"<CreateTime>"+returnTime+"</CreateTime>"
				+"<MsgType><![CDATA[voice]]></MsgType>"
				+"<Voice>"
				+"<MediaId><![CDATA["+mediaId+"]]></MediaId>"
				+"</Voice>"
				+"</xml>";
		return xml;
	}
}
