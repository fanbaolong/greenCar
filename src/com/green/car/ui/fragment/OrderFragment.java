package com.green.car.ui.fragment;

import java.util.HashMap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.green.car.BaseFragment;
import com.green.car.R;
import com.green.car.injector.Injector;
import com.green.car.injector.Res;

public class OrderFragment extends BaseFragment{
	
	@Res
	private TextView tv_test;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_order, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		Injector.getInstance().inject(activity, this, view);
		getData();
	}

	private void getData() {
		String url = "http://115.159.109.124:99/lbs/getAroundShops.json";
		HashMap<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("t", "728FA4218BAF756E23CA8330AB539C6A");
		reqMap.put("distance", "1500");
		reqMap.put("catId", "11");
		reqMap.put("zoom", "16.0");
		reqMap.put("lat", "31.194664519683197");
		reqMap.put("lng", "121.43629028795513");
		requestPost(true, url, reqMap);
	}
	
	@Override
	public void responseSuccess(Object result, String value, String tag) {
		super.responseSuccess(result, value, tag);
		tv_test.setText(result.toString());
	}
}
