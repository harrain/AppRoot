package code_base.util;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * Created by yue on 15/10/29.
 * 防止重复创建的Toast工厂,测试使用的吐司
 * debug显示，release不显示
 */
public class ToastDebug {
	private ToastDebug() {
	}

	private static Context mContext = null;
	private static Toast toast = null;
	private static boolean isToast = true;

	public static void init(Context context){ mContext = context;}

	public static void init(Context context,boolean debug){
		mContext = context;
		isToast = debug;
	}

	public static Toast getToast(Context context, String text) {
		if (ToastDebug.mContext == context) {
			toast.setText(text);
			toast.setDuration(Toast.LENGTH_SHORT);
		} else {
			ToastDebug.mContext = context;
			toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		}
		return toast;
	}

	public static Toast getToast(Context context, int text) {
		if (ToastDebug.mContext == context) {
			toast.setText(text);
			toast.setDuration(Toast.LENGTH_SHORT);
		} else {
			ToastDebug.mContext = context;
			toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		}
		return toast;
	}

	public static Toast getLongToast(Context context, String text) {
		if (ToastDebug.mContext == context) {
			toast.setText(text);
			toast.setDuration(Toast.LENGTH_LONG);
		} else {
			ToastDebug.mContext = context;
			toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
		}
		return toast;
	}

	public static Toast getLongToast(Context context, int text) {
		if (ToastDebug.mContext == context) {
			toast.setText(text);
			toast.setDuration(Toast.LENGTH_LONG);
		} else {
			ToastDebug.mContext = context;
			toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
		}
		return toast;
	}

	public static void showLongToast(Context context, int text) {
		getLongToast(context, text).show();
	}

	public static void showLongToast(Context context, String text) {
		getLongToast(context, text).show();
	}

	public static void showToast(Context context, int text) {
		getToast(context, text).show();
	}

	public static void showShortToast(String text){
		if (!isToast) return;
		Toast.makeText(mContext,text,Toast.LENGTH_SHORT).show();
	}

	public static void showLongToast(String text){
		if (!isToast) return;
		Toast.makeText(mContext,text,Toast.LENGTH_LONG).show();
	}

	public static void showShortToast(@StringRes int strId){
		if (!isToast) return;
		Toast.makeText(mContext,strId,Toast.LENGTH_SHORT).show();
	}

	public static void showLongToast(@StringRes int strId){
		if (!isToast) return;
		Toast.makeText(mContext,strId,Toast.LENGTH_LONG).show();
	}

	public static void showToast(Context context, String text) {
		getToast(context, text).show();
	}

	public static void cancelToast() {
		if (toast != null) {
			toast.cancel();
		}
	}

	public static void setIsToast(boolean isToast) {
		ToastDebug.isToast = isToast;
	}
}
