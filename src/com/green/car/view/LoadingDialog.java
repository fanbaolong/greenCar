package com.green.car.view;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.green.car.R;

/**
 * 自定义Dialog
 * 
 * 2015/5/22
 * 
 * @author Yuan.Wu
 * 
 */
public class LoadingDialog extends Dialog {
	
	private static int default_width = 120; //默认宽度
	private static int default_height = 120;//默认高度
	
	private TextView tvMessage;
	
	public LoadingDialog(Context context) {
		this(context, R.layout.layout_dialog_progressbar, R.style.MyDialog_pro);
	}

	public LoadingDialog(Context context, int layout, int style) {
		this(context, default_width, default_height, layout, style);
	}

	public LoadingDialog(Context context,
			int width, int height, 
			int layout, int style) {
		super(context, style);
		
		View view = LayoutInflater.from(context).inflate(layout, null);
		tvMessage = (TextView) view.findViewById(R.id.tv_dialog_msg);
		
		setContentView(view);
		// set window params
		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		// set width,height by density and gravity
		float density = getDensity(context);
		params.width = (int) (width * density);
		params.height = (int) (height * density);
		params.gravity = Gravity.CENTER;
		window.setAttributes(params);
	}
	
	public void setMessage(String msg) {
		tvMessage.setText(msg);
	}

	private float getDensity(Context context) {
		Resources resources = context.getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		return dm.density;
	}

}
