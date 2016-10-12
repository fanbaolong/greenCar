package com.green.car.http;

import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @ClassName: SharedPreferenceUtil
 * @Description:(首选项工具类)
 * @date 2015-4-9 下午1:13:16
 */
public class SharedPreferenceUtil {
	private SharedPreferences preferences;
	private Editor editor;

	@SuppressLint("CommitPrefEdits")
	public void initSharedPreference(Context context, String name) {
		preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		editor = preferences.edit();
	}

	/**
	 * 保存数据
	 * 
	 * @param key
	 * @param value
	 */
	public void saveData(String key, Object value) {
		if (value instanceof Boolean) {
			editor.putBoolean(key, (Boolean) value);
		} else if (value instanceof Integer) {
			editor.putInt(key, (Integer) value);
		} else if (value instanceof Float) {
			editor.putFloat(key, (Float) value);
		} else if (value instanceof Long) {
			editor.putFloat(key, (Long) value);
		} else {
			editor.putString(key, (String) value);
		}
		editor.commit();
	}

	/**
	 * 获取数据
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public Object getData(String key, Object defaultValue) {
		if (defaultValue instanceof Boolean) {
			return preferences.getBoolean(key, (Boolean) defaultValue);
		} else if (defaultValue instanceof Integer) {
			return preferences.getInt(key, (Integer) defaultValue);
		} else if (defaultValue instanceof Float) {
			return preferences.getFloat(key, (Float) defaultValue);
		} else if (defaultValue instanceof Long) {
			return preferences.getLong(key, (Long) defaultValue);
		} else {
			return preferences.getString(key, (String) defaultValue);
		}
	}

	public Map<String, ?> getAllData() {
		return preferences.getAll();
	}

	public void removeDataByKey(String key) {
		editor.remove(key).commit();
	}

}
