package com.green.car.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 车辆租点基本类
 * @author think
 *
 */
public class RentalPoint {

	private String id;//租点id
	private String name;//租点名称
	private String address;//租点地址
	private String tel;//租点联系电话
	private double lat;//租点纬度
	private double lont;//租点经度
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLont() {
		return lont;
	}
	public void setLont(double lont) {
		this.lont = lont;
	}
	
	/**
	 * 获取列表数据
	 * @param name
	 * @return
	 */
	public static List<RentalPoint> getRentalPoints(String name){
		List<RentalPoint> rentalPoints = new ArrayList<RentalPoint>();
		RentalPoint rentalPoint;
		rentalPoint = new RentalPoint();
		rentalPoint.setName("神州租车");
		rentalPoint.setAddress("上海虹口区虹口足球场店");
		rentalPoint.setTel("18896572307");
		rentalPoint.setLat(31.270531);
		rentalPoint.setLont(121.481189);
		if (rentalPoint.getName().contains(name)) {
			rentalPoints.add(rentalPoint);
		}
		
		rentalPoint = new RentalPoint();
		rentalPoint.setName("一嗨租车");
		rentalPoint.setAddress("上海虹口区曲阳路嘿客送车点");
		rentalPoint.setTel("18896572307");
		rentalPoint.setLat(31.272126);
		rentalPoint.setLont(121.492229);
		if (rentalPoint.getName().contains(name)) {
			rentalPoints.add(rentalPoint);
		}
		return rentalPoints;
	}
}
