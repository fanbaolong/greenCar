package com.green.car.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.green.car.R;


public class CommonUtils {
	
	public static String getEmply(String s){
		if (null == s) {
			s = "";
		}
		return s;
		
	}
	
	public static String getTime(String time){
		String timeStr = "";
		if (!"".equals(CommonUtils.getEmply(time))) {
			time=time.trim();
			String str2="";
			for(int i=0;i<time.length();i++){
				if(time.charAt(i)>=48 && time.charAt(i)<=57){
					str2+=time.charAt(i);
				}
			}
			long timel = Long.valueOf(str2);
			try {
				timeStr = TimeUtil.getStrTime(timel);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return timeStr;
		
	}

	public static int transDip(int dip) {
		float scale = Resources.getSystem().getDisplayMetrics().density;
		return (int) (dip * scale + 0.5f);
	}
	
	
	
	/**打电话**/
	public static void tell(String s,Activity activity){
		if (!"".equals(getEmply(s))) {
			Uri uri = Uri.parse("tel:"+s); 
			Intent it = new Intent(Intent.ACTION_DIAL, uri);   
			activity.startActivity(it);
		}
	}
	

	public static String getVersion(Context context) {
		String version = "";
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			version = "" + info.versionCode;
		} catch (NameNotFoundException e) {
			version = "1";
			e.printStackTrace();
		}

		return version;
	}

	public static String getVersionName(Context context) {
		String versionName = "";
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			versionName = "" + info.versionName;
		} catch (NameNotFoundException e) {
			versionName = "1.0";
			e.printStackTrace();
		}
		return versionName;
	}

	/*
	 * MD5加密
	 */
	public static String md5(String str) {
		String value = "";
		if (str != null) {
			MessageDigest md5 = null;
			try {
				md5 = MessageDigest.getInstance("MD5");
				md5.update(str.getBytes());
				value = toHex(md5.digest());
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		return value.toLowerCase();
	}

	public static String toHex(final byte[] b) {
		final StringBuffer sb = new StringBuffer(b.length * 3);
		for (int i = 0; i < b.length; ++i) {
			sb.append(Character.forDigit((b[i] & 0xF0) >> 4, 16));
			sb.append(Character.forDigit(b[i] & 0xF, 16));
		}
		return ((sb != null) ? sb.toString().toUpperCase() : null);
	}

	/*
	 * MD5加密
	 */
	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		// 16位加密，从第9位到25位
		return md5StrBuff.substring(8, 24).toString().toUpperCase();
	}
	
	public static boolean is24H(Context context) {
		ContentResolver resolver = context.getContentResolver();
		String strTimeFormat = android.provider.Settings.System.getString(
				resolver, android.provider.Settings.System.TIME_12_24);
		if ("24".equals(strTimeFormat)) {
			return true;
		}
		return false;
	}
	
	public static String getTimeZone(Context context) {
		ContentResolver resolver = context.getContentResolver();
		String strTimeFormat = android.provider.Settings.System.getString(
				resolver, android.provider.Settings.Global.AUTO_TIME_ZONE);
		return strTimeFormat;
	}
	
	public static void saveValueToPublicSharedPreferences(Context context,
			String key, String Value) {
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = settings.edit();
		editor.putString(key, Value);
		editor.commit();
	}
	
	public static String getValueFromPublicSharedPreferences(Context context,
			String key) {
		String value = "";
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(context);
		value = settings.getString(key, "");
		return value;
	}
	
	public static Date parseDate(String string) {
		Date date = null;
		try {
			DateFormat XEP_0082_UTC_FORMAT_WITHOUT_MILLIS = new SimpleDateFormat(
					"yyyy-MM-dd'T'HH:mm:ss.SSSZ");
			XEP_0082_UTC_FORMAT_WITHOUT_MILLIS.setTimeZone(TimeZone
					.getTimeZone("UTC"));
			date = XEP_0082_UTC_FORMAT_WITHOUT_MILLIS.parse(string);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static String isNull(String str, String suffix) {
		String strTemp = "";
		if (null != str) {
			if (!"".equals(str) && !"null".equals(str)) {
				if (!"".equals(suffix)) {
					return str + suffix;
				} else {
					return str;
				}
			}
		}
		return strTemp;
	}
	
	public static String isNull(String str) {
		return isNull(str, "");
	}

	
	
	public static boolean hasSDCard() {
		String status = Environment.getExternalStorageState();
		if (!status.equals(Environment.MEDIA_MOUNTED)) {
			return false;
		}
		return true;
	}
	
	
	@SuppressWarnings("resource")
	public static double getFileSize(File file) throws Exception {// 取得文件大小
		double size = 0;
		if (file.exists()) {
			FileInputStream fis = null;
			fis = new FileInputStream(file);
			size = fis.available();
		} else {
			return 0;
		}
		size = size / 1024.00;
		size = Double.parseDouble(size + "");
		BigDecimal b = new BigDecimal(size);
		double y1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
		return Double.parseDouble(df.format(y1));
	}

	public static String getDate(int year, int monthIndex, int day, int hour) {
		return String.format("%d-%d-%dT%d:00:00", year, monthIndex, day, hour);
	}
	
	public static String getDate(int year, int monthIndex, int day, int hour,
			int minute) {
		return String.format("%d-%d-%dT%d:%d:00", year, monthIndex, day, hour,
				minute);
	}
	
	// ----------------------new-----------------

	/**
	 * 判断 list 是否为 null 或 size = 0
	 * @param list
	 * @return 若为 null 或 size =0 返回 true, 否则返回 false
	 */
	public static boolean isNullOrEmpty(List<?> list) {
		return null == list || list.isEmpty();
	}

	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			return isNetworkInfoAvailable(getConnectivityManager(context)
					.getActiveNetworkInfo());
		}
		return false;
	}


	private static ConnectivityManager getConnectivityManager(Context context) {
		return (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
	}

	private static boolean isNetworkInfoAvailable(NetworkInfo info) {
		if (info == null) {
			return false;
		}
		return info.isAvailable();
	}

	public static void hide(Context context, IBinder windowToken) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(windowToken,
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * 关闭键盘
	 * @param activity
	 */
	public static void hide(Activity activity) {
		InputMethodManager imm = (InputMethodManager) activity
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(
				activity.getCurrentFocus().getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * 对字符串左补0
	 * @author hjt
	 */
	public static String addLeftZeroForNum(String str, int strLength) {
		int strLen = str.length();
		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuffer sb = new StringBuffer();
				sb.append("0").append(str);// 左补0
				// sb.append(str).append("0");//右补0
				str = sb.toString();
				strLen = str.length();
			}
		}
		return str;
	}

	/**
	 * 获取long型时间
	 * @param s
	 * @return
	 */
	public static long getDateLong(String s) {
		long l = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			l = sdf.parse(s).getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return l;
	}

	public static String getDateString(long time) {
		String s = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			s = sdf.format(new Date(time));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * 获取短时间格式
	 * @param date
	 * @return
	 */
	public static String getShortDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		return sdf.format(date);
	}

	/**
	 * 计算date之后n天的日期
	 */
	public static Date getDateAfter(Date date, int n) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + n);
		return now.getTime();
	}


	private static long lastClickTime;

	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < 300) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

	private static long exitClickTime;
	/**
	 * 判断两次点击返回按钮时间的间隔
	 * @param s
	 * @return
	 */
	public static boolean isFastDoubleClick(int s) {
		long time = System.currentTimeMillis();
		long timeD = time - exitClickTime;
		if (0 < timeD && timeD < s) {
			return true;
		}
		exitClickTime = time;
		return false;
	}

	/**
	 * 得到一个可用的缓存目录(如果外部可用使用外部,否则内部)。
	 *
	 * @param context
	 *            上下文信息
	 * @param uniqueName
	 *            目录名字
	 * @return 返回目录名字
	 */
	public static File getDiskCacheDir(Context context, String uniqueName) {
		// 检查是否安装或存储媒体是内置的,如果是这样,试着使用
		// 外部缓存 目录
		// 否则使用内部缓存 目录
		final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState()) ? getExternalCacheDir(context)
				.getPath() : context.getCacheDir().getPath();
		File appCacheDir = new File(cachePath + File.separator + uniqueName);
		if (!appCacheDir.exists()) {
			if (!appCacheDir.mkdirs()) {
				return null;
			}
			try {
				new File(appCacheDir, ".nomedia").createNewFile();
			} catch (IOException e) {
			}
		}
		return appCacheDir;
	}

	/**
	 * 获得外部应用程序缓存目录
	 * @param context
	 *            上下文信息
	 * @return 外部缓存目录
	 */
	public static File getExternalCacheDir(Context context) {
		if (hasFroyo()) {
			return context.getExternalCacheDir();
		}
		final String cacheDir = "/Android/data/" + context.getPackageName()
				+ "/cache/";
        File appCacheDir = null;
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            appCacheDir = new File(Environment.getExternalStorageDirectory()
                    .getPath() + cacheDir);
            if (!appCacheDir.exists()) {
                if (!appCacheDir.mkdirs()) {
                    return null;
                }
                try {
                    new File(appCacheDir, ".nomedia").createNewFile();
                } catch (IOException e) {
                }
            }
        }
		return appCacheDir;
	}

	/**
	 * 当前Android系统版本是否在（ Froyo） Android 2.2或 Android 2.2以上
	 * @return
	 */
	public static boolean hasFroyo() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
	}

	public static boolean isMobile(String strMobile) {
		String reg = "^1[0-9]{10}$"; // 验证手机号码
		// String reg = "^(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$";
		// // 验证手机号码
		return strMobile.matches(reg);
	}

	public static boolean isMail(String strMobile) {
		String reg = "^([a-z0-9A-Z]+[_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$"; // 验证邮箱
		return strMobile.matches(reg);
	}

	/**
	 * 将图片修剪为正圆形
	 * @param bitmap
	 * @return
	 */
	public static Bitmap getCircleBitmap(Bitmap bitmap) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		int size = (bitmap.getWidth() > bitmap.getHeight()) ? bitmap
				.getHeight() : bitmap.getWidth();
		final Rect rect = new Rect(0, 0, size, size);
		final RectF rectF = new RectF(rect);
		final float roundPx = bitmap.getWidth() / 2;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	public static String changeTelToPsw(String tel, String rstr) {
		String s1 = "";
		String s2 = "";
		int n = 4;
		try {
			s1 = tel.substring(0, n - 1);
			s2 = tel.substring(n + 3, tel.length());
		} catch (Exception ex) {
			// throw new Throwable("替换的位数大于字符串的位数");
		}
		return s1 + rstr + s2;
	}

	/**直接打电话 */
	public static void CallPhone1(Context context, String phone) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (tm != null && tm.getDeviceId() != null) {
			Intent intent = new Intent(Intent.ACTION_DIAL,
					Uri.parse("tel:" + phone));
			context.startActivity(intent);
		} else {
			NewToast.makeText(context, context.getString(R.string.action_settings),
					Toast.LENGTH_SHORT);
		}
	}
	
	/**跳转到电话输入界面 */
	public static void CallPhone(Context context, String phone) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (tm != null && tm.getDeviceId() != null) {
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setData(Uri.parse("tel:" + phone));
			context.startActivity(intent);
		} else {
			Toast.makeText(context, "T_T!您的设备不支持拨号功能哦！",
					Toast.LENGTH_SHORT).show();
		}
	}

	public static void saveLog(String s) {
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long time = System.currentTimeMillis();
			String now = sf.format(new Date(time));

			File file = new File("mnt/sdcard/time_log.txt");
			byte[] contentByte = getBytesFromFile(file);
			if (contentByte != null) {
				String content = new String(contentByte);
				s = "----------------------------------------\r\n" + now
						+ " : " + s + "\r\n" + content;
			} else {
				s = now + " : " + s + "\r\n";
			}

			byte[] data = s.getBytes();
			ByteArrayInputStream is;
			is = new ByteArrayInputStream(data, 0, data.length);
			if (is != null) {
				String url = "mnt/sdcard/time_log.txt";
				try {
					writeSDFromInput(is, url);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static byte[] getBytesFromFile(File f) {
		if (f == null) {
			return null;
		}
		try {
			FileInputStream stream = new FileInputStream(f);
			ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = stream.read(b)) != -1)
				out.write(b, 0, n);
			byte[] datas = out.toByteArray();
			stream.close();
			out.close();
			return datas;
		} catch (IOException e) {
		}
		return null;
	}

	/**
	 * 将一个InputStream里面的数据写入SD卡中
	 */
	public static void writeSDFromInput(ByteArrayInputStream input, String _file)
			throws IOException {
		OutputStream output = null;
		try {
			File file = new File(_file);
			// String _filePath_file.replace(File.separatorChar +
			// file.getName(), "");
			int end = _file.lastIndexOf(File.separator);
			String _filePath = _file.substring(0, end);
			File filePath = new File(_filePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			file.createNewFile();
			output = new FileOutputStream(file);
			byte buffer[] = new byte[4 * 1024];
			int i;
			while (-1 != (i = input.read(buffer))) {
				output.write(buffer, 0, i);
			}
			output.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (output != null) {
					output.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static long diff = 0;
	
	/**
	 * 存储服务器时间和本地时间的差值
	 */
	public static void saveDiffTime(Context context, String systime, long diff) {
		long diffTime = CommonUtils.getDateLong(systime) + diff
				- System.currentTimeMillis();
		PreferenceManager.getDefaultSharedPreferences(context).edit()
				.putLong("diff", diffTime).commit();
		diff = 0;
	}
	
	/**
	 * 获取服务器时间和本地时间的差值
	 */
	public static long readDiffTime(Context context) {
		if (diff == 0)
			diff = PreferenceManager.getDefaultSharedPreferences(context)
					.getLong("diff", 0);
		return diff;
	}
	
	/**
	 * 获取时间
	 * @return
	 */
	public static long getSystemTime(Context context) {
		return System.currentTimeMillis() + CommonUtils.readDiffTime(context);
	}
	
	



    /**
     * 判断连接是否有效
     * @param url
     * @return
     */
    public static boolean checkURL(String url) {
        boolean value = false;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url)
                    .openConnection();
            int code = conn.getResponseCode();
            if (code != 200) {
                value = false;
            } else {
                value = true;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }
    
    public static String isnull(String comment){
    	String comments = "";
    	if (comment == null) {
			return comments;
		} else {
			return comment;
		}
    }
    
    public static String getText(TextView tv){
    	return isNull(tv.getText().toString().trim());
    }
    
    public static String getEdit(EditText et){
    	return isNull(et.getText().toString().trim());
    }
    
    /**得到任务内容*/
	public static String getTaskContent(int type_id){
		String content = "";
		if (type_id == 0) {
			content = "营业厅值班";
		}else if (type_id == 1) {
			content = "有线电视收视费工作";
		}else if (type_id == 2) {
			content = "有线电视宽带工作";
		}else if (type_id == 3) {
			content = "整理信息";//已经废除
		}else if (type_id == 4) {
			content = "集团公户工作";
		}else if (type_id == 5) {
			content = "社区现场工作";
		}else if (type_id == 6) {
			content = "营业厅开会";
		}else if (type_id == 7) {
			content = "其他";
		}
		return isNull(content);
	}
	/**得到维修类型*/
	public static String getWeiXiuContent(int type_id){
		String content = "";
		if (type_id == 0) {
			content = "安装模拟";
		}else if (type_id == 1) {
			content = "安装数字";
		}else if (type_id == 2) {
			content = "安装宽带";
		}else if (type_id == 3) {
			content = "维修模拟";
		}else if (type_id == 4) {
			content = "维修数字";
		}else if (type_id == 5) {
			content = "维修宽带";
		}
		return isNull(content);
	}
	/**得到维修结果*/
	public static String getWeiXiuResult(int type_id){
		String content = "";
		if (type_id == 0) {
			content = "安装成功";
		}else if (type_id == 1) {
			content = "安装失败";
		}else if (type_id == 2) {
			content = "自动恢复";
		}else if (type_id == 3) {
			content = "解释调试";
		}else if (type_id == 4) {
			content = "室内网络问题";
		}else if (type_id == 5) {
			content = "室外网络问题-网络调试";
		}else if (type_id == 6) {
			content = "室外网络问题-更换器件";
		}else if (type_id == 7) {
			content = "终端设备故障";
		}else if (type_id == 8) {
			content = "故障无法修复";
		}
		return isNull(content);
	}
	/**得到用户*/
	public static String getUserRole(int type_id){
		String content = "";
		if (type_id == 0) {
			content = "主管领导";
		}else if (type_id == 1) {
			content = "营业厅主任";
		}else if (type_id == 2) {
			content = "维护主管";
		}else if (type_id == 3) {
			content = "社区经理";
		}else if (type_id == 4) {
			content = "客户经理";
		}
		return isNull(content);
	}
	/**工单当前状态*/
	public static String getTaskState(int state){
		String content = "";
		if (state == 0) {
			content = "待执行";
		}else if (state == 1) {
			content = "已完成";
		}else if (state == 2) {
			content = "待派";
		}else if (state == 3) {
			content = "转派中";
		}
		return isNull(content);
	}
}
