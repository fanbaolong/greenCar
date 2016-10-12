package com.green.car;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;

/**
 * @author fbl
 *  activity集合类，
 *  在每个activity启动时添加到maneger,退出时循环退出
 */
public class ExitManager {
	private List<Activity> activityList = new LinkedList<Activity>();
	private static ExitManager instance;

	private ExitManager() {
	}

	public static ExitManager getInstance() {
		if (instance == null) {
			instance = new ExitManager();
		}
		return instance;
	}

	/**
	 * add activity
	 * @param activity
	 */
	public void addActivity(Activity activity) {
		activityList.add(activity);
	}
	
	public List<Activity> getActivities(){
		return activityList;
	}

	/**
	 *  退出程序时调用	
	 */
	public void exit() {
		
		for (Activity activity : activityList) {
			if (!activity.isFinishing()) {
				activity.finish();
			}
		}
		
		System.exit(0);
		int id = android.os.Process.myPid();
		
		if (id != 0) {
			android.os.Process.killProcess(id);
		}
	}
}
