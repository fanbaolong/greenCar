package com.green.car.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 地图上查询车的基本信息
 * @author think
 *
 */
public class MapCar {

	private String id;//车的id
	private String rentalPointId;//租车点id
	private String name;//车辆名称
	private List<String> img = new ArrayList<String>();//车辆图片列表
	private String total;//车辆总数
	private String surplis;//剩余车辆
	private String deposit;//押金
	private String appointmentTime;//预约时间
	private String rentDay;//每日租金
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRentalPointId() {
		return rentalPointId;
	}
	public void setRentalPointId(String rentalPointId) {
		this.rentalPointId = rentalPointId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getImg() {
		return img;
	}
	public void setImg(List<String> img) {
		this.img = img;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getSurplis() {
		return surplis;
	}
	public void setSurplis(String surplis) {
		this.surplis = surplis;
	}
	public String getDeposit() {
		return deposit;
	}
	public void setDeposit(String deposit) {
		this.deposit = deposit;
	}
	public String getAppointmentTime() {
		return appointmentTime;
	}
	public void setAppointmentTime(String appointmentTime) {
		this.appointmentTime = appointmentTime;
	}
	public String getRentDay() {
		return rentDay;
	}
	public void setRentDay(String rentDay) {
		this.rentDay = rentDay;
	}
	
}
