package com.example.util;

import android.content.Context;
import android.widget.Toast;
/**
 * 提示各种请求结果工具类
 * @author Administrator
 *
 */
public class T {
	public static Context context;
	/**
	 * 显示提示消息
	 */
	public static void Show(String content) {
		Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
	}
	


}
