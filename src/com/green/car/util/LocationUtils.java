package com.green.car.util;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.green.car.App;

/**
 * 定位公共类
 * @author think
 *
 */
public class LocationUtils implements AMapLocationListener {

	private static final int LOOP_LOCATION_INTERVAL = 5 * 60 * 1000;
	private AMapLocationClient locationClient = null;
	private AMapLocationClientOption locationOption = null;
	private static LocationUtils instance;
	private OnLocationListener onLocationListener;

	public interface OnLocationListener {
		void onLocation(AMapLocation location, String error);
	}

	public static synchronized LocationUtils getInstance() {
		if (instance == null) {
			instance = new LocationUtils();
		}
		return LocationUtils.instance;
	}

	public void startLocation(OnLocationListener listener) {
		LogUtils.error("定位信息", "开始定位");
		locationClient = new AMapLocationClient(App.getInstance().getApplicationContext());
		locationOption = new AMapLocationClientOption();
		// 设置定位模式为高精度模式
		locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
		// 设置定位监听
		locationClient.setLocationListener(this);
		locationOption.setOnceLocation(true);// 设置为单次定位
		// locationOption.setInterval(1000);//60秒一定位
		// 设置定位参数
		locationClient.setLocationOption(locationOption);
		// 启动定位
		locationClient.startLocation();
 		onLocationListener = listener;
	}

	public void releaseResource() {
		if (null != locationClient) {
			/**
			 * 如果AMapLocationClient是在当前Activity实例化的，
			 * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
			 */
			locationClient.onDestroy();
			locationClient = null;
			locationOption = null;
		}
	}

	@Override
	public void onLocationChanged(AMapLocation aMapLocation) {
		int errorCode = aMapLocation.getErrorCode();
		if (errorCode > 0) {
			onLocationListener.onLocation(aMapLocation, aMapLocation.getErrorInfo());
		} else {
			onLocationListener.onLocation(aMapLocation, null);
		}
	}

	public void stop() {
		if (locationClient != null) {
			locationClient.stopLocation();
			releaseResource();
		}
	}
}
