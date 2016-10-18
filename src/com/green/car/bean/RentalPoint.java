package com.green.car.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 车辆租点基本类
 * @author think
 *
 */
public class RentalPoint {

	private int id;

	private String createTime;

	private String updateTime;

	private boolean onDeleted;

	private String orderNo;

	private String remarks;
	/**名称*/
	private String name;

	private String positionX;

	private String positionY;

	private String positionName;

	private int carPortNum;

	private int pilesNum;

	private int trickleChargeNum;

	private int expressChargeNum;

	private String serviceTime;

	private String workBeginTime;

	private String wordEndTime;

	private String introduction;

	private String thirdName;

	private String siteType;

	private boolean third;

	private String latLng;
	
	private int pilesSum;
	
	private int carSum;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



	public String getCreateTime() {
		return createTime;
	}



	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}



	public String getUpdateTime() {
		return updateTime;
	}



	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}



	public boolean isOnDeleted() {
		return onDeleted;
	}



	public void setOnDeleted(boolean onDeleted) {
		this.onDeleted = onDeleted;
	}



	public String getOrderNo() {
		return orderNo;
	}



	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}



	public String getRemarks() {
		return remarks;
	}



	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getPositionX() {
		return positionX;
	}



	public void setPositionX(String positionX) {
		this.positionX = positionX;
	}



	public String getPositionY() {
		return positionY;
	}



	public void setPositionY(String positionY) {
		this.positionY = positionY;
	}



	public String getPositionName() {
		return positionName;
	}



	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}



	public int getCarPortNum() {
		return carPortNum;
	}



	public void setCarPortNum(int carPortNum) {
		this.carPortNum = carPortNum;
	}



	public int getPilesNum() {
		return pilesNum;
	}



	public void setPilesNum(int pilesNum) {
		this.pilesNum = pilesNum;
	}



	public int getTrickleChargeNum() {
		return trickleChargeNum;
	}



	public void setTrickleChargeNum(int trickleChargeNum) {
		this.trickleChargeNum = trickleChargeNum;
	}



	public int getExpressChargeNum() {
		return expressChargeNum;
	}



	public void setExpressChargeNum(int expressChargeNum) {
		this.expressChargeNum = expressChargeNum;
	}



	public String getServiceTime() {
		return serviceTime;
	}



	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}



	public String getWorkBeginTime() {
		return workBeginTime;
	}



	public void setWorkBeginTime(String workBeginTime) {
		this.workBeginTime = workBeginTime;
	}



	public String getWordEndTime() {
		return wordEndTime;
	}



	public void setWordEndTime(String wordEndTime) {
		this.wordEndTime = wordEndTime;
	}



	public String getIntroduction() {
		return introduction;
	}



	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}



	public String getThirdName() {
		return thirdName;
	}



	public void setThirdName(String thirdName) {
		this.thirdName = thirdName;
	}



	public String getSiteType() {
		return siteType;
	}



	public void setSiteType(String siteType) {
		this.siteType = siteType;
	}



	public boolean isThird() {
		return third;
	}



	public void setThird(boolean third) {
		this.third = third;
	}



	public String getLatLng() {
		return latLng;
	}



	public void setLatLng(String latLng) {
		this.latLng = latLng;
	}



	public int getPilesSum() {
		return pilesSum;
	}



	public void setPilesSum(int pilesSum) {
		this.pilesSum = pilesSum;
	}



	public int getCarSum() {
		return carSum;
	}



	public void setCarSum(int carSum) {
		this.carSum = carSum;
	}

	/**
	 * 条件搜索网点
	 * @param rentalPoints
	 * @param searchName
	 * @return
	 */
	public static List<RentalPoint> getRentalPoints(List<RentalPoint> rentalPoints, String searchName){
		List<RentalPoint> searchPoints = new ArrayList<RentalPoint>();
		for (int i = 0; i < rentalPoints.size(); i++) {
			if (rentalPoints.get(i).getName().contains(searchName)) {
				searchPoints.add(rentalPoints.get(i));
			}
		}
		return searchPoints;
	}
}
