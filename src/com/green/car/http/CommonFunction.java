package com.green.car.http;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import com.green.car.util.NewToast;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by wangke on 16/5/11.
 */
public class CommonFunction {

	/**
	 * 初始化首选项
	 * 
	 * @param context
	 * @param name
	 * @return
	 */
	public static SharedPreferenceUtil initSharedPreferences(Context context, String name) {
		SharedPreferenceUtil util = new SharedPreferenceUtil();
		util.initSharedPreference(context, name);
		return util;
	}

	/**
	 * 从JSONObject中根据key获取值
	 * 
	 * @param result
	 * @return
	 */
	public static Object getValueByKey(JSONObject result, String key) {
		if (result.has(key)) {
			try {
				return result.get(key);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	/**
	 * 判断String类型是否为空
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(Object value) {
		return value == null || "".equals(value.toString()) || "".equals((value.toString()).trim()) || value == "null" || "[]".equals(value)
				|| "\"\"".equals(value);
	}

	/**
	 * 获取Message对象
	 * 
	 * @param code
	 * @param result
	 * @param bundleValues
	 * @return
	 */
	public static Message getMessage(int code, Object result, String... bundleValues) {
		Message message = new Message();
		message.what = code;
		message.obj = result;
		if (bundleValues != null) {
			int size = bundleValues.length;
			if (size > 0) {
				Bundle bundle = new Bundle();
				for (int i = 0; i < size; i++) {
					bundle.putString("arg" + i, bundleValues[i]);
				}
				message.setData(bundle);
			}
		}
		return message;
	}

	/**
	 * 判断有效的手机号码
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(147)|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 得到版本号
	 */
	public static String getVersionName(Context context) {
		// 获取packagemanager的实例
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = null;
		try {
			packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		return packInfo == null ? null : packInfo.versionName;
	}

	/**
	 * 内容校验，只限汉字和字母
	 * 
	 * @param str
	 * @return
	 */
	public static boolean ChckText(String str, Context context) {
		// 只能输入中文和字母
		String reg = "^[\u4e00-\u9fa5a-zA-Z]+$";
		Pattern pattern = Pattern.compile(reg);
		Matcher remarkMatcher = null;
		if (!CommonFunction.isEmpty(str)) {
			remarkMatcher = pattern.matcher(str.toString());
		}
		if (remarkMatcher != null && !remarkMatcher.matches()) {
			NewToast.makeText(context, "只能输入汉字和字母");
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 正则表达式验证内容是否包含字符串
	 * 
	 * @param content
	 * @return
	 */
	public static boolean ChekIsEmpty(String content) {
		Pattern p = Pattern.compile("^[^\\s]+$");
		Matcher m = p.matcher(content);
		return m.matches();
	}

	/** 转码，UTF-8daoshi使用 **/
	public static String unescape(String s) {
		StringBuffer sbuf = new StringBuffer();
		int l = s.length();
		int ch = -1;
		int b, sumb = 0;
		for (int i = 0, more = -1; i < l; i++) {
			/* Get next byte b from URL segment s */
			switch (ch = s.charAt(i)) {
			case '%':
				ch = s.charAt(++i);
				int hb = (Character.isDigit((char) ch) ? ch - '0' : 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;
				ch = s.charAt(++i);
				int lb = (Character.isDigit((char) ch) ? ch - '0' : 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;
				b = (hb << 4) | lb;
				break;
			case '+':
				b = ' ';
				break;
			default:
				b = ch;
			}
			/* Decode byte b as UTF-8, sumb collects incomplete chars */
			if ((b & 0xc0) == 0x80) { // 10xxxxxx (continuation byte)
				sumb = (sumb << 6) | (b & 0x3f); // Add 6 bits to sumb
				if (--more == 0)
					sbuf.append((char) sumb); // Add char to sbuf
			} else if ((b & 0x80) == 0x00) { // 0xxxxxxx (yields 7 bits)
				sbuf.append((char) b); // Store in sbuf
			} else if ((b & 0xe0) == 0xc0) { // 110xxxxx (yields 5 bits)
				sumb = b & 0x1f;
				more = 1; // Expect 1 more byte
			} else if ((b & 0xf0) == 0xe0) { // 1110xxxx (yields 4 bits)
				sumb = b & 0x0f;
				more = 2; // Expect 2 more bytes
			} else if ((b & 0xf8) == 0xf0) { // 11110xxx (yields 3 bits)
				sumb = b & 0x07;
				more = 3; // Expect 3 more bytes
			} else if ((b & 0xfc) == 0xf8) { // 111110xx (yields 2 bits)
				sumb = b & 0x03;
				more = 4; // Expect 4 more bytes
			} else /* if ((b & 0xfe) == 0xfc) */{ // 1111110x (yields 1 bit)
				sumb = b & 0x01;
				more = 5; // Expect 5 more bytes
			}
			/* We don't test if the UTF-8 encoding is well-formed */
		}
		return sbuf.toString();
	}

	public static String getInformation(Context context) {
		SharedPreferenceUtil util = CommonFunction.initSharedPreferences(context, "information");
		return util.getData("info", "").toString();
	}

	public static String getChannelName(Context activity) {
		if (activity == null) {
			return "android";
		}
		String channelName = null;
		try {
			PackageManager packageManager = activity.getPackageManager();
			if (packageManager != null) {
				// 注意此处为ApplicationInfo 而不是
				// ActivityInfo,因为友盟设置的meta-data是在application标签中，而不是某activity标签中，所以用ApplicationInfo
				ApplicationInfo applicationInfo = packageManager.getApplicationInfo(activity.getPackageName(), PackageManager.GET_META_DATA);
				if (applicationInfo != null) {
					if (applicationInfo.metaData != null) {
						channelName = applicationInfo.metaData.getString("UMENG_CHANNEL");
					}
				}
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return channelName;
	}

	/**
	 * 隐藏键盘
	 * 
	 * @param view
	 * @param context
	 */
	@SuppressLint("NewApi")
	public static void isHideKeyboard(View view, Context context) {
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	/**
	 * 验证身份证15位与18位
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isIdCardNo(String mobiles) {
		// Pattern p15 = Pattern
		// .compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");
		// Pattern p18 = Pattern
		// .compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4}$");
		// Matcher m15 = p15.matcher(mobiles);
		// Matcher m18 = p18.matcher(mobiles);
		//
		// boolean result = false;
		// if (m15.matches()) {
		// result = true;
		// }
		// if (m18.matches()) {
		// result = true;
		// }
		return isIDCardNO(mobiles);
	}

	/** 判断身份证 */
	public static boolean isIDCardNO(String text) {
		String regx = "[0-9]{17}x";
		String regX = "[0-9]{17}X";
		String reg17 = "[0-9]{17}";
		String reg1 = "[0-9]{15}";
		String regex = "[0-9]{18}";
		if (TextUtils.isEmpty(text) && text.length() < 14) {
			return false;
		}
		if (text.length() == 18) {
			String re = text.substring(0, 17);
			if (!re.matches(reg17)) {
				return false;
			}
		}
		return text.matches(regx) || text.matches(reg1) || text.matches(regex) || text.matches(regX);
	}

	public static boolean isInstallByread(final String packageName) {
		return new File("/data/data/" + packageName).exists();
	}

	public static boolean checkApkExist(Context context, String packageName) {
		if (TextUtils.isEmpty(packageName))
			return false;
		try {
			ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
			return true;
		} catch (NameNotFoundException e) {
			return false;
		}
	}

    /** 判断当前摄像头是否被使用，用于调取摄像头后一步 */
    public static boolean isCameraCanUse() {
        boolean canUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
        } catch (Exception e) {
            canUse = false;
        }
        if (canUse) {
            if (mCamera != null)
                mCamera.release();
            mCamera = null;
        }
        return canUse;
    }
    /**
     * 判断当前应用程序处于前台还是后台,true是在后台，flase不在后台
     */
    public static boolean isApplicationBroughtToBackground( Context context) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        if(am==null)
            return false;
        List<RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }
}
