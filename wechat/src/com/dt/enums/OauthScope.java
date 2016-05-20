package com.dt.enums;
/**
 * 
 * 类名称：OauthScope   
 * 类描述：   Oauth授权作用域枚举
 * 创建人：luoj  
 * 创建时间：2015年7月16日 下午4:02:32
 */
public enum OauthScope {

    /**
     * 仅仅获取用户openid
     */
    SNSAPI_BASE("snsapi_base"),

    /**
     * 获取用户完整信息
     */
    SNSAPI_USERINFO("snsapi_userinfo");

    String value;

    OauthScope(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
