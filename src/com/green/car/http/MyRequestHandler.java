package com.green.car.http;

import org.json.JSONObject;

import com.green.car.util.CommonUtils;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;

public class MyRequestHandler extends RequestHandler {
	public static final int noNet = -10000;// 没有网络

	@Override
	public void handleMessage(Message msg) {
		int code = msg.what;
		Bundle bundle = msg.getData();
		String tag = null;
		if (bundle != null) {
			tag = bundle.getString("arg0");
		}
		if (code == ConstantUtil.REQUEST_FAILTURECODE.NETWORK_CONNT_FAIL) {// 网络连接失败
			checkFailure("网络不给力呀，检查一下网络再试试吧！", tag);
			Object object = msg.obj;
			if (object != null) {
				handleData(object, tag);
			}
		} else if (code == ConstantUtil.REQUEST_FAILTURECODE.NETWORK_CONNT_TIMEOUT) {// 网络连接超时
			checkFailure("刚才走神了，再试一次吧！", tag);
		} else {// 成功
			Object object = msg.obj;
			if (CommonFunction.isEmpty((String) object)) {
				checkFailure("数据收集中，稍后再试吧！", tag);
			} else {
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
			String resultType = CommonFunction.getValueByKey(jsonObject, "resultType").toString();
			String resultMes = CommonFunction.getValueByKey(jsonObject, "resultMes").toString();
			String objectResult = CommonFunction.getValueByKey(jsonObject, "objectResult").toString();
			if (CommonUtils.isNull(resultType).equals("OK")) {
				checkSuccess(objectResult, resultMes, tag);
			} else {
				checkFailure("暂无数据", tag, resultType, "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			checkFailure("暂无数据", tag, "-1", "");
		}
	}

	@Override
	public void onSuccess(Object result, String value) {
	}

	@Override
	public void onSuccess(Object result, String value, String tag) {

	}

	@Override
	public void onFailure(Object result, String tag) {

	}

	public void onFailure(Object result, String tag, String errorCode) {

	}

	public void onFailure(Object result, String tag, String errorCode, Object modle) {

	}

	@Override
	public void onFailure(Object result) {

	}

	public void checkFailure(Object result, String tag, String errorCode, Object modle) {
		onFailure(result);
		onFailure(result, tag);
		onFailure(result, tag, errorCode);
		onFailure(result, tag, errorCode, modle);
	}

	public void checkSuccess(Object result, String value, String tag) {
		onSuccess(result, value);
		onSuccess(result, value, tag);
	}

}
