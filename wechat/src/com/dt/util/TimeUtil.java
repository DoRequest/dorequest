package com.dt.util;

import java.sql.Date;
import java.text.SimpleDateFormat;
/**
 * 
 * 类名称：TimeUtil   
 * 类描述：   时间工具
 * 创建人：luoj  
 * 创建时间：2015年7月16日 下午9:54:02
 */
public class TimeUtil {
	/**
	 * 时间戳转换
	 * @param timestamp
	 * @return 
	 */
	public static String convert(String timestamp){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long timestamps = Long.valueOf(timestamp);
		String date = sdf.format(new Date(timestamps*1000L));
		return date;
	}
}
