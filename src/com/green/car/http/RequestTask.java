package com.green.car.http;

import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.green.car.view.LoadingDialog;
 

/**
 * 网络请求任务
 * 
 * @author chengbo
 */
@SuppressLint("NewApi")
public class RequestTask extends AsyncTask<Map<String, Object>, Void, Object> {
	private Context context;
	private boolean isShowProgressBar;
	private String url;
	private LoadingDialog processingDialog;
	private int requestType;
	private Handler mHandler;
	private String tag;
	private long timeStamp;
	private boolean isNeedCache;

	public RequestTask(Context context, boolean isShowProgressBar, String tag, String url, int requestType, Handler handler) {
		this(context, isShowProgressBar, tag, url, requestType, handler, 0l);
	}

	public RequestTask(Context context, boolean isShowProgressBar, String tag, String url, int requestType, Handler handler, long timeStamp) {
		this.context = context;
		this.isShowProgressBar = isShowProgressBar;
		this.tag = tag;
		this.url = url;
		this.requestType = requestType;
		mHandler = handler;
		if (timeStamp != 0) {
			isNeedCache = true;
			this.timeStamp = timeStamp;
		}
	}

	@Override
	protected void onPreExecute() {
		// 用来现实ProgressBar
		try {
			if (isShowProgressBar) {
				if (processingDialog != null && processingDialog.isShowing()) {
					processingDialog.cancel();
					processingDialog = null;
				}
				if (context != null) {
					processingDialog = new LoadingDialog(context);
					processingDialog.setOnCancelListener(new OnCancelListener() {
						@Override
						public void onCancel(DialogInterface arg0) {
							if (processingDialog != null && processingDialog.isShowing()) {
								processingDialog.cancel();
								processingDialog = null;
							}
						}
					});
					processingDialog.show();
				}
			}
		} catch (Exception e) {
			Log.e("RequestTask", e.toString());
		}
	}

	@Override
	protected Object doInBackground(Map<String, Object>... arg0) {
		Map<String, Object> params = null;
		if (arg0 != null && arg0.length > 0) {
			params = arg0[0];
		}
		 if ("image".equals(tag)) {
	            return NetManager.getInstant(context).sendJsonRequestFromOkHttpClient(url);
	        }
		String result = NetManager.getInstant(context)
				.sendJsonRequestFromOkHttpClient(context, requestType, url, params, mHandler, tag, isNeedCache, timeStamp);
		return result;
	}

	@Override
	protected void onPostExecute(Object result) {
		Message message = null; 
		if (!CommonFunction.isEmpty(result)) {
			if (("" + ConstantUtil.REQUEST_FAILTURECODE.NETWORK_CONNT_TIMEOUT).equals(result)) {
				message = CommonFunction.getMessage(ConstantUtil.REQUEST_FAILTURECODE.NETWORK_CONNT_TIMEOUT, result, tag);
			} else if (("" + ConstantUtil.REQUEST_FAILTURECODE.CONNECTED_REFRUSH).equals(result)) {
				message = CommonFunction.getMessage(ConstantUtil.REQUEST_FAILTURECODE.CONNECTED_REFRUSH, result, tag);
			} else {
				message = CommonFunction.getMessage(ConstantUtil.SUCCESS, result, tag);
			}
			if (mHandler != null) {
				mHandler.sendMessage(message);
			}
		} else {
			if (mHandler != null) {
				message = CommonFunction.getMessage(ConstantUtil.REQUEST_FAILTURECODE.DATA_ERROR, result, tag);
				mHandler.sendMessage(message);
			}
		}
		if (context != null && processingDialog != null && processingDialog.isShowing() && !((Activity) context).isFinishing()) {
			processingDialog.dismiss();
			processingDialog = null;
		}
	}

}
