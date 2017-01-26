package com.testdial.myapplication.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {

	// Toast统一管理类
	private ToastUtil() {
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	public static Toast mToast;

	public static void showToast(Context context, String msg) {
		showToast(context, msg, Toast.LENGTH_LONG);
	}

	public static void showToast(Context mContext, String msg, int duration) {
		if (mToast == null) {
			mToast = Toast.makeText(mContext, "", duration);
		}
		mToast.setText(msg);
		mToast.show();
	}
}
