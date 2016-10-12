package com.green.car.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.green.car.R;

/**
 * toast 复写类，多次点击不会重复长时间显示
 * @author chenming.ma
 */
public class NewToast {
	
	protected static Toast toast = null;
	
	public static void makeText(Context context, String s, int duration) {
		try {
			if (null == context || null == s) {
				return;
			}
			View v = LayoutInflater.from(context).inflate(R.layout.transient_notification, null);
			TextView txt = (TextView) v.findViewById(R.id.notifactiontxt);
			txt.setText(s);
			
			if (toast == null) {
				toast = Toast.makeText(context, s, duration);
			}
			toast.setView(v);
			toast.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void makeText(Context context, int resId) {
		makeText(context, context.getString(resId), Toast.LENGTH_LONG);
	}

	public static void makeText(Context context, String str) {
		makeText(context, str, Toast.LENGTH_LONG);
	}
}
