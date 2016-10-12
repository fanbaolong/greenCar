package com.green.car;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.green.car.http.MyRequestHandler;
import com.green.car.http.RequestTaskManager;
import com.umeng.analytics.MobclickAgent;

/**
 * Fragment基类，实现了触摸事件拦截
 */
@SuppressLint("NewApi")
public class BaseFragment extends Fragment implements OnTouchListener {
	
	public static String TAG = null;
	public BaseActivity activity;
	private Dialog mDialog;

	public BaseFragment() {
		TAG = ((Object) this).getClass().getName();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.activity = (BaseActivity) getActivity();
	}

	protected void back() {
		getFragmentManager().popBackStack();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return false;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mDialog != null)
			mDialog.dismiss();
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart(this.getClass().getName()); // 统计页面
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd(this.getClass().getName());
	}
	
	public void requestPost(boolean isShow, String url, HashMap<String, Object> reqMap){
		new RequestTaskManager().requestDataByPost(getActivity(), isShow, url, url, reqMap, mHandler);
	}
	
	MyRequestHandler mHandler = new MyRequestHandler(){
		public void onSuccess(Object result, String value, String tag) {
			responseSuccess(result, value, tag);
		};
		
		public void onFailure(Object result) {
			
		};
	};
	
	public void responseSuccess(Object result, String value, String tag){
		
	}
	
}
