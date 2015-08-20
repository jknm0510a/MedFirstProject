package tw.medfirst.com.project.baseunit;

import android.util.Log;

import tw.medfirst.com.project.manager.TagManager;

public class Logger{
	public static void v(String tag, String msg){
		if(TagManager.DEBUG)
			Log.v(tag, msg);
	}
	
	public static void v(String tag, String msg, Throwable e){
		if(TagManager.DEBUG)
			Log.v(tag, msg, e);
	}
	
	public static void d(String tag, String msg){
		if(TagManager.DEBUG)
			Log.d(tag, msg);
	}
	
	public static void d(String tag, String msg, Throwable e){
		if(TagManager.DEBUG)
			Log.d(tag, msg, e);
	}
	
	public static void i(String tag, String msg){
		if(TagManager.DEBUG)
			Log.i(tag, msg);
	}
	
	public static void i(String tag, String msg, Throwable e){
		if(TagManager.DEBUG)
			Log.i(tag, msg, e);
	}
	
	public static void w(String tag, String msg){
		if(TagManager.DEBUG)
			Log.w(tag, msg);
	}
	
	public static void w(String tag, Throwable e) {
		if(TagManager.DEBUG)
			Log.w(tag, e);
	}

	public static void w(String tag, String msg, Throwable e){
		if(TagManager.DEBUG)
			Log.w(tag, msg, e);
	}
	
	public static void e(String tag, String msg){
		if(TagManager.DEBUG)
			Log.e(tag, msg);
	}
	public static void e(String tag, int msg){
		if(TagManager.DEBUG)
			Log.e(tag, Integer.toString((msg)));
	}
	public static void e(String tag, Object msg){
		if(TagManager.DEBUG)
			Log.e(tag, msg.toString());
	}
	public static void e(String tag, float msg){
		if(TagManager.DEBUG)
			Log.e(tag, Float.toString((msg)));
	}
	public static void e(String tag, String msg, Throwable e){
		if(TagManager.DEBUG)
			Log.e(tag, msg, e);
	}
}
