package mybatis.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 日期工具类
 * 
 * @author xyb
 *
 */
public class DateUtils {
	
	/**
	 * 获取默认格式的当前时间字符串
	 * @param pattern
	 * @return
	 */
	public static String getNowTime() {
		return getNowTime("yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取指定格式的当前时间字符串
	 * @param pattern
	 * @return
	 */
	public static String getNowTime(String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return formatter.format(LocalDateTime.now());
	}
  
}
