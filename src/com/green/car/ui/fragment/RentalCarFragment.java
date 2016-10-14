package com.green.car.ui.fragment;

import java.util.List;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ZoomControls;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.OnMapClickListener;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.green.car.BaseFragment;
import com.green.car.R;
import com.green.car.bean.RentalPoint;
import com.green.car.injector.Injector;
import com.green.car.injector.Res;
import com.green.car.util.CommonUtils;
import com.green.car.util.LocationUtils;
import com.green.car.util.LocationUtils.OnLocationListener;

public class RentalCarFragment extends BaseFragment implements OnClickListener,
OnMapClickListener, OnMarkerClickListener {
	@Res
	private MapView mapview;
	@Res
	private TextView btn_search, tv_location, poi_name, poi_address;
	@Res
	private EditText input_edittext;
	@Res
	private View poi_detail;

	private AMap mAMap;
	private LatLonPoint lp;//
	private Marker mlastMarker;// 选择的点
	private UiSettings mUiSettings;// 定义一个UiSettings对象
	private List<RentalPoint> rentalPoints;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_rental_car, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Injector.getInstance().inject(activity, this, view);
		mapview = (MapView) view.findViewById(R.id.mapView);
		mapview.onCreate(savedInstanceState);
		location();
	}

	private void location() {
		LocationUtils.getInstance().startLocation(new OnLocationListener() {

			@Override
			public void onLocation(AMapLocation location, String error) {
				lp = new LatLonPoint(location.getLatitude(), location
						.getLongitude());
				init();
			}
		});
	}

	/**
	 * 初始化AMap对象
	 */
	private void init() {
		if (mAMap == null) {
			mAMap = mapview.getMap();
			mUiSettings = mAMap.getUiSettings();// 实例化UiSettings类
			mUiSettings.setZoomControlsEnabled(false);

			mAMap.setOnMapClickListener(this);
			mAMap.setOnMarkerClickListener(this);
			btn_search.setOnClickListener(this);
			tv_location.setOnClickListener(this);
			
		}
		rentalPoints = RentalPoint.getRentalPoints("");
		mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
				new LatLng(lp.getLatitude(), lp.getLongitude()), 14));
		addMarker();
	}

	/** 添加店铺点 */
	private void addMarker() {
		mAMap.clear();
		mlastMarker = null;
		for (int i = 0; i < rentalPoints.size(); i++) {
			addItem(rentalPoints.get(i), i);
		}
		View child = mapview.getChildAt(1);
		if (child != null
				&& (child instanceof ImageView || child instanceof ZoomControls)) {
			child.setVisibility(View.INVISIBLE);
		}
		
		mlastMarker = mAMap.addMarker(new MarkerOptions()
		.anchor(0.5f, 0.5f)
		.icon(BitmapDescriptorFactory
				.fromBitmap(BitmapFactory.decodeResource(getResources(),
						R.drawable.point)))
						.position(new LatLng(lp.getLatitude(), lp.getLongitude())));
		mlastMarker.showInfoWindow();
	}

	Marker addItem(RentalPoint result, int i) {
		MarkerOptions options = new MarkerOptions();
		options.position(
				new LatLng(rentalPoints.get(i).getLat(), rentalPoints.get(i)
						.getLont()))
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.poi_marker_pressed))
								.draggable(true);
		Marker marker = mAMap.addMarker(options);
		marker.setTitle(i + "");
		return marker;
	}

	/**
	 * 气泡点击监听
	 */
	@Override
	public boolean onMarkerClick(Marker marker) {
		int index = Integer.valueOf(marker.getTitle());
		whetherToShowDetailInfo(true, index);
		return true;
	}

	/**
	 * 返回地图 监听
	 */
	@Override
	public void onMapClick(LatLng arg0) {
		whetherToShowDetailInfo(false, 0);
		if (mlastMarker != null) {
			resetlastmarker();
		}
	}

	// 将之前被点击的marker置为原来的状态
	private void resetlastmarker() {
		mlastMarker
		.setIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
				.decodeResource(getResources(),
						R.drawable.poi_marker_pressed)));
		mlastMarker = null;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_search:
			doSearchQuery();
			break;
		case R.id.tv_location:
			mAMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(lp.getLatitude(), lp.getLongitude())));
			break;

		default:
			break;
		}
	}

	/**
	 * 开始进行poi搜索
	 */
	protected void doSearchQuery() {
		rentalPoints = RentalPoint.getRentalPoints(CommonUtils
				.getEdit(input_edittext));
		addMarker();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	public void onResume() {
		super.onResume();
		mapview.onResume();
		whetherToShowDetailInfo(false, 0);
	}

	/**
	 * 点击气泡后弹出的框
	 * 
	 * @param isToShow
	 *            是否弹框
	 */
	private void whetherToShowDetailInfo(boolean isToShow, int index) {
		if (isToShow) {
			poi_detail.setVisibility(View.VISIBLE);
			poi_name.setText(CommonUtils.isNull(rentalPoints.get(index).getName()));
			poi_address.setText(CommonUtils.isNull(rentalPoints.get(index).getAddress()));
		}else {
			poi_detail.setVisibility(View.GONE);
		}
	}

	/**
	 * 方法必须重写
	 */
	@Override
	public void onPause() {
		super.onPause();
		mapview.onPause();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapview.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		mapview.onDestroy();
	}
}
