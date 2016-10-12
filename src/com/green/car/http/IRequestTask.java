package com.green.car.http;

import java.util.HashMap;
import java.util.Map;


public interface IRequestTask {
	public Map<String, RequestTask> requestTasks = new HashMap<String, RequestTask>();
	
	/**
	 * 根据Tag来取消请求
	 * @param tag
	 */
	public abstract void cancelRequest(String tag);
	
	/**
	 * 取消所有的网络请求
	 */
	public abstract void cancelAllRequest();
}
