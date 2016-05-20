package com.dt.entity;

import java.io.Serializable;
import java.util.List;

import com.dt.exception.WeixinException;
/**
 * 类名称：Menu   
 * 类描述：   主菜单
 * 创建人：luoj  
 * 创建时间：2015年7月16日 下午2:49:52
 */
public class Menu implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8316770571051512182L;
	/**
     * 一级菜单列表，最多3个
     */
    private List<MenuButton> button;

    public List<MenuButton> getButton() {
        return button;
    }

    public void setButton(List<MenuButton> button) {
        if (null == button || button.size() > 3) {
            throw new WeixinException("主菜单最多3个");
        }
        this.button = button;
    }
}
