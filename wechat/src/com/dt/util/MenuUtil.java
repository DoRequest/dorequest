package com.dt.util;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.dt.entity.Menu;
import com.dt.entity.MenuButton;
import com.dt.entity.WechatException;
import com.dt.enums.MenuType;
import com.dt.enums.OauthScope;
/**
 * 类名称：MenuUtil   
 * 类描述：   菜单工具类
 * 创建人：luoj  
 * 创建时间：2015年7月16日 下午3:08:30
 */
public class MenuUtil {
	/**
	 * 创建菜单
	 * @param menu
	 * @return
	 */
	public static WechatException createMenu(Menu menu){
		String token = TokenUtil.getInstance().accessToken.getToken();
		System.out.println(token);
		String url = Constants.BASE_API_URL+"cgi-bin/menu/create?access_token="+token;
		String menuJson = JSONUtil.toJson(menu);
		String backInfo = GetRequestUtil.getJson(url, Constants.POST, menuJson);
		return JSON.parseObject(backInfo, WechatException.class);
	}
	/**
	 * 删除菜单
	 * @return
	 */
	public static WechatException deleteMenu(){
		String token = TokenUtil.getInstance().accessToken.getToken();
		String url = Constants.BASE_API_URL+"cgi-bin/menu/delete?access_token="+token;
		String backInfo = GetRequestUtil.getJson(url, Constants.GET, null);
		return JSON.parseObject(backInfo, WechatException.class);
	}
	public static void main(String[] args) {
		Menu menu = new Menu();
		/**
		 * 
		 * --------------注意一级菜单最长四个汉字，二级菜单最长7个字不能定---------
		 * -------------一级菜单最多是三个，二级菜单最多5个，只有一级二级菜单-------------
		 * --------------菜单都不能定位到某个位置改，只能全部删除再创建----------------
		 * -----------创建自定义菜单后，由于微信客户端缓存，需要24小时微信客户端才会展现出来。
		 *           测试时可以尝试取消关注公众账号后再次关注，则可以看到创建后的效果。---------
		 */
		//一级菜单
		List<MenuButton> button = new ArrayList<MenuButton>();
		MenuButton menuButton1 = new MenuButton();
		menuButton1.setType(MenuType.VIEW);
		menuButton1.setName("官网");
		menuButton1.setKey("main1");
		String creaditFindUrl = "http://www.dingtian.com.cn";
//		menuButton1.setUrl(getUrl(creaditFindUrl));
		menuButton1.setUrl(creaditFindUrl);
		//主菜单1相关
		button.add(menuButton1);
		MenuButton menuButton2 = new MenuButton();
		menuButton2.setType(MenuType.CLICK);
		menuButton2.setName("便捷查询");
		menuButton2.setKey("main2");
		//menuButton2.setUrl("");
		//子菜单
		List<MenuButton> viewButton2 = new ArrayList<MenuButton>();
		MenuButton menuButtonView4 = new MenuButton();
		menuButtonView4.setType(MenuType.VIEW);
		menuButtonView4.setName("天气查询");
		String creditNew = "http://m.weather.com.cn/mweather/101270101.shtml";
//		menuButtonView4.setUrl(getUrl(creditNew));
		menuButtonView4.setUrl(creditNew);
		menuButtonView4.setKey("main2_son1");
		viewButton2.add(menuButtonView4);
		MenuButton menuButtonView5 = new MenuButton();
		menuButtonView5.setType(MenuType.VIEW);
		String creditRedName = "http://m.kuaidi100.com/uc/index.html";
		menuButtonView5.setUrl(creditRedName);
		menuButtonView5.setName("快递查询");
		menuButtonView5.setKey("main2_son2");
		viewButton2.add(menuButtonView5);
		MenuButton menuButtonView6 = new MenuButton();
		menuButtonView6.setType(MenuType.VIEW);
		String creditWarn = "http://m.keyunzhan.com";
//		menuButtonView6.setUrl(getUrl(creditWarn));
		menuButtonView6.setUrl(creditWarn);
		menuButtonView6.setName("车票查询");
		menuButtonView6.setKey("main2_son3");
		viewButton2.add(menuButtonView6);
		
		MenuButton menuButtonView7 = new MenuButton();
		menuButtonView7.setType(MenuType.VIEW);
		String find = "http://m.weizhangwang.com";
		menuButtonView7.setUrl(find);
		menuButtonView7.setName("违章查询");
		menuButtonView7.setKey("main2_son4");
		viewButton2.add(menuButtonView7);
		menuButton2.setSubButton(viewButton2);
		//主菜单2相关
		button.add(menuButton2);

		MenuButton menuButton3 = new MenuButton();
		menuButton3.setType(MenuType.CLICK);
		menuButton3.setName("个人中心");
		menuButton3.setKey("main3");

		List<MenuButton> viewButton3 = new ArrayList<MenuButton>();
		MenuButton menuButtonView8 = new MenuButton();
		menuButtonView8.setType(MenuType.VIEW);
		String attentionUrl = "http://dtsoft.tunnel.mobi/wechat/my.do";
		menuButtonView8.setUrl(GetRequestUtil.getOauthPageUrl(attentionUrl, OauthScope.SNSAPI_BASE, null));
		menuButtonView8.setName("WHO I AM?");
		menuButtonView8.setKey("main3_son1");
		viewButton3.add(menuButtonView8);
		
		MenuButton menuButtonView9 = new MenuButton();
		menuButtonView9.setType(MenuType.VIEW);
		String jsUrl = "http://dtsoft.tunnel.mobi/wechat/js.do";
		menuButtonView9.setUrl(GetRequestUtil.getOauthPageUrl(jsUrl, OauthScope.SNSAPI_BASE, null));
		menuButtonView9.setName("JS-SDK");
		menuButtonView9.setKey("main3_son2");
		viewButton3.add(menuButtonView9);
		
		MenuButton menuButtonView10 = new MenuButton();
		menuButtonView10.setType(MenuType.VIEW);
		String socket = "http://dtsoft.tunnel.mobi/SpringMvc-websocket/init";
		menuButtonView10.setUrl(GetRequestUtil.getOauthPageUrl(socket, OauthScope.SNSAPI_BASE, null));
		menuButtonView10.setName("chat-room");
		menuButtonView10.setKey("main3_son2");
		viewButton3.add(menuButtonView10);
		menuButton3.setSubButton(viewButton3);
		
		//主菜单2相关
		button.add(menuButton3);
		menu.setButton(button);
//		System.out.println(apiConfig.getAccessToken());
//		System.out.println(JSONUtil.toJson(menu));
		WechatException wechatException = MenuUtil.createMenu(menu);
//		String jsonString = JSON.toJSONString(menu,true);
		System.out.println(JSON.toJSON(wechatException));
//		String info = JSON.toJSONString(menu, SerializerFeature.QuoteFieldNames);
//		WechatException wechatException = MenuUtil.deleteMenu();
//		System.out.println(JSON.toJSON(wechatException));
	}

}
