package com.example.common.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @ClassName: HttpContextUtils
 * @Description: Http 上下文
 * @Author: [咖啡]
 * @Version: 1.0
 **/
public class HttpContextUtils {

	/**
	 * 请求头数据
	 * @return
	 */
	public static HttpServletRequest getHttpServletRequest() {
		// return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
	}

	public static boolean isAjaxRequest(HttpServletRequest request){

		String accept = request.getHeader("accept");
		String xRequestedWith = request.getHeader("X-Requested-With");

		// 如果是异步请求或是手机端，则直接返回信息
		/*
		* 	String a="abcdefghijklmn";
			String b="defghi";
			String c="rst";
			System.out.println(a.contains(b));//如果包含，contains返回true
			System.out.println(a.indexOf(b));//如果包含，indexOf返回字符串匹配的首索引位置,3

			System.out.println(a.contains(c));//如果不包含，contains返回false
			System.out.println(a.indexOf(c));//如果不包含，indexOf返回-1
		* */
//		return ((accept != null && accept.indexOf("application/json") != -1
//				|| (xRequestedWith != null && xRequestedWith.indexOf("XMLHttpRequest") != -1)
//		));
		// 返回 "application/json"格式或其他
		return ((accept != null && accept.contains("application/json")
				|| (xRequestedWith != null && xRequestedWith.contains("XMLHttpRequest"))
		));
	}
}
