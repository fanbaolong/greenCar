package com.green.car;
/**
 * 链接API
 * @author think
 *
 */
public class API {
	

	/** 域名 */
	// http://192.168.18.31:8080/ECar/
	//	public static final String API = "http://112.80.51.93:8080";// 外部网
	public static final String API = "http://www.lvnengzuche.com";// 正式
	//	 public static final String API = "http://172.22.207.4:8002/ECar";// 内部网
	public static final String SERVICE = "/Service";// /ECar
	/** 测试版本下载地址（fir） */
	public static final String DOWNLOAD_URL_ON_FIR = "http://fir.im/lvneng";
	/** 测试版本检查地址（fir） */
	public static final String UPDATE_URL_ON_FIR = "http://fir.im/api/v2/app/version/56d65fa9f2fc420eba000038?token=0c05245f5c6c008009bc2bee44fcb842";

	public static final String partnerId = "1327382101";

	public class prefre
	{
		/*  */
		public static final String PREF_FRIST_STRING = "first";
		public static final String SessionId = "sessionid";
		public static final String mUserId = "userid";
		public static final String mUserName = "username";
		public static final String mUserpass = "userpass";
		public static final String mTelphone = "telPhone";
		public static final String mBadge = "mbadge";

	}

	/* 检查用户名是否已经存在 */
	public static final String CheckUserName = API + SERVICE + "/CheckUserName";
	/* 检查手机号码是否已经存在 */
	public static final String CheckMobile = API + SERVICE + "/CheckMobile";
	/* 注册 */
	public static final String Register = API + SERVICE + "/Regist";
	/* 登录 */
	public static final String Login = API + SERVICE + "/Login";
	/* 获取网点类型 打点 */
	public static final String getSites = API + SERVICE + "/getSites";
	/* 获取某网点下的车型 */
	public static final String getPilesCategoryBySite = API + SERVICE + "/getPilesCategoryBySite";
	/* 预约充电桩 */
	public static final String reServePiles = API + SERVICE + "/reServePiles";
	/* 我的订单 */
	public static final String orderList = API + SERVICE + "/orderList";
	/* 个人信息 */
	public static final String getMember = API + SERVICE + "/getMember";
	/* 发送验证码 */
	public static final String sendVerification = API + SERVICE + "/sendVerification";
	/* 获取某个网点的车辆类别 */
	public static final String getCarCategoryBySite = API + SERVICE + "/getCarCategoryBySite";
	/* 预约车辆 */
	public static final String reServeCar = API + SERVICE + "/reServeCar";
	/* 删除订单 */
	public static final String deleteOrder = API + SERVICE + "/deleteOrder";
	/* 取消订单 */
	public static final String cancelOrder = API + SERVICE + "/cancelOrder";
	/* 获取充电桩图表 */
	public static final String getChart = API + SERVICE + "/getChart";
	/* 开启车辆 */
	public static final String startCar = API + SERVICE + "/startCar";
	/* 关闭车辆 */
	public static final String closeCar = API + SERVICE + "/closeCar";
	/* 租赁车辆 */
	public static final String lease = API + SERVICE + "/lease";
	/* 归还车辆 */
	public static final String returnCar = API + SERVICE + "/returnCar";
	/* 修改密码 */
	public static final String changePsd = API + SERVICE + "/changePsd";
	/* 获取某网点详情 */
	public static final String getSiteById = API + SERVICE + "/getSiteById";
	/* 获取充电桩详情 */
	public static final String getPilesById = API + SERVICE + "/getPilesById";
	/* 获取车辆详情 */
	public static final String getCarById = API + SERVICE + "/getCarById";
	/* 找回密码 */
	public static final String findPsd = API + SERVICE + "/findPsd";
	/* 开始充电 */
	public static final String startPiles = API + SERVICE + "/startPiles";
	/* 关闭充电 */
	public static final String closePiles = API + SERVICE + "/closePiles";
	/* 订单详情 */
	public static final String getOrderById = API + SERVICE + "/getOrderById";
	/* 扫码后的接口 */
	public static final String scanCode = API + SERVICE + "/scanCode";
	/* 消息接口 */
	public static final String getMessages = API + SERVICE + "/getMessages";
	/* 设置消息为已读 */
	public static final String mesRead = API + SERVICE + "/mesRead";
	/* 删除消息 */
	public static final String deleteMes = API + SERVICE + "/deleteMes";
	/* 删除消息(全部) */
	public static final String deleteMesAll = API + SERVICE + "/deleteMesAll";
	/* 充值订单创建 */
	public static final String createAccountDetail = API + SERVICE + "/createAccountDetail";
	/* 获取账户金额明细列表 */
	public static final String getAccountDetails = API + SERVICE + "/getAccountDetails";
	/* 扫码后的下单接口 */
	public static final String scanCodeOrder = API + SERVICE + "/scanCodeOrder";
	/* 上传图片 */
	public static final String upLoadFile = API + SERVICE + "/upLoadFile";
	/* 提交身份证审核 */
	public static final String saveLicense = API + SERVICE + "/saveLicense";
	/* 提交驾照审核 */
	public static final String saveDriverLicense = API + SERVICE + "/saveDriverLicense";
	/* 编辑保存个人信息 */
	public static final String saveInfo = API + SERVICE + "/saveInfo";


	
	

}
