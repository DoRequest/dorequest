package com.dt.exception;

public class WeixinException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	public WeixinException(String msg)  
	{  
		super("警告：------->"+msg);  
	}  
}
