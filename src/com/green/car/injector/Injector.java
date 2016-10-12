package com.green.car.injector;

import java.lang.reflect.Field;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;


/**
 * Created by cheng on 13-10-27.
 */
public class Injector {
	private static Injector instance;

	private Injector() {
	}

	public static Injector getInstance() {
		if (instance == null) {
			instance = new Injector();
		}
		return instance;
	}

	public void inject(Activity activity) {
		Class<?> father = activity.getClass();
		while (father != null) {
			inject(father, activity, activity.getWindow().getDecorView());
			father = father.getSuperclass();
		}
	}

	private void inject(Class<?> clazz, Activity activity, View view) {
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			injectView(activity, field, view);
		}
	}

//	public void inject(Activity activity) {
//		inject(activity, activity.getWindow().getDecorView());
//	}

	public void inject(Activity activity, View view) {
		inject(activity.getClass(), activity, view);
	}

	private void injectView(Activity activity, Field field, View sourceView) {
		if (field.isAnnotationPresent(Res.class)) {
			Res viewInject = field.getAnnotation(Res.class);
			int viewId = viewInject.vid();
			if (viewId <= 0)
				viewId = activity.getResources().getIdentifier(field.getName(),
						"id", activity.getPackageName());
			try {
				field.setAccessible(true);
				field.set(
						activity,
						sourceView == null ? activity.findViewById(viewId) : sourceView
								.findViewById(viewId)
				);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 使用inject(Fragment fragment)
	 */
	@Deprecated
	public void inject(Activity activity, Fragment fragment, View view) {
		Field[] fields = ((Object) fragment).getClass().getDeclaredFields();
		for (Field field : fields) {
			injectView(activity, fragment, field, view);
		}
	}

	public void inject(Fragment fragment) {
		inject(fragment.getActivity(), fragment, fragment.getActivity().getWindow().getDecorView());
	}

	private void injectView(Activity activity, Fragment fragment, Field field,
	                        View sourceView) {
		if (field.isAnnotationPresent(Res.class)) {
			Res viewInject = field.getAnnotation(Res.class);
			int viewId = viewInject.vid();
			if (viewId <= 0)
				viewId = activity.getResources().getIdentifier(field.getName(),
						"id", activity.getPackageName());
			try {
				field.setAccessible(true);
				field.set(fragment, sourceView.findViewById(viewId));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
