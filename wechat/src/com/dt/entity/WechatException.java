package com.dt.entity;

import com.dt.enums.ResultType;
import com.dt.util.StringUtil;
/**
 * 
 * 类名称：WechatException   
 * 类描述：   异常类
 * 创建人：luoj  
 * 创建时间：2015年7月15日 上午11:24:26
 */
public class WechatException {
	private String errcode;
	private String errmsg;
	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		String result = this.errmsg;
		//将接口返回的错误信息转换成中文，方便提示用户出错原因
		if (StringUtil.isNotEmpty(this.errcode) && !ResultType.SUCCESS.getCode().toString().equals(this.errcode)) {
			ResultType resultType = ResultType.get(this.errcode);
			if(null!=resultType) {
				result = resultType.getDescription();
			}
		}
		return result;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
}
