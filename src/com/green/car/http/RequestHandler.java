package com.green.car.http;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

/**
 * @ClassName: RequestHandler
 * @Description:(请求处理类)
 * @author chengbo
 * @date 2015-4-13 上午11:24:01
 */
public class RequestHandler extends Handler implements IRequestHandler {

	@Override
	public void handleMessage(Message msg) {
		int code = msg.what;
		Bundle bundle = msg.getData();
		String tag = null;
		if (bundle != null) {
			tag = bundle.getString("arg0");
		}
		if (code == ConstantUtil.REQUEST_FAILTURECODE.NETWORK_CONNT_FAIL) {// 网络连接失败
			Object object = msg.obj;
			if (object != null) {
				handleData(object, tag);
			} else {
				checkFailure("网络失败,请检查网络", tag);
			}
		} else if (code == ConstantUtil.REQUEST_FAILTURECODE.NETWORK_CONNT_TIMEOUT) {// 网络连接超时
			checkFailure("网络连接超时,请重试", tag);
		} else {// 成功
			Object object = msg.obj;
			if (object != null && (object instanceof byte[])) {
				onSuccess(object, tag);
			} else if (TextUtils.isEmpty((String) object)) {
				checkFailure("操作失败,请重试", tag);
			} else {
				if (object instanceof String)
					handleData(object, tag);
			}
		}
	}

	/**
	 * 数据处理
	 * 
	 * @param object
	 * @param tag
	 */
	private void handleData(Object object, String tag) {
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(object.toString());
			checkSuccess(jsonObject.toString(), "", tag);
		} catch (JSONException e) {
			e.printStackTrace();
			checkFailure("数据解析出错", tag);
		}
	}

	@Override
	public void onSuccess(Object result, String value) {
	}

	@Override
	public void onFailure(Object result) {

	}

	@Override
	public void onSuccess(Object result, String value, String tag) {

	}

	@Override
	public void onFailure(Object result, String tag) {

	}

	public void checkFailure(Object result, String tag) {
		onFailure(result);
		onFailure(result, tag);
		onFailure(result, tag, "-10000");
	}

	public void checkSuccess(Object result, String value, String tag) {
		onSuccess(result, value);
		onSuccess(result, value, tag);
	}

	@Override
	public void onFailure(Object result, String tag, String error) {
		// TODO Auto-generated method stub

	}

}
