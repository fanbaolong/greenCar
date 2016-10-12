package com.green.car;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * ÈÄÇÈÖçÂô®‰∏≠Áö? ViewHolder Á±?
 * 
 * @author hjt
 * @link ÂèÇË?ÉÔºöhttp://www.piwai.info/android-adapter-good-practices/#Update
 * 
 */
public class ViewHolder {

	@SuppressWarnings("unchecked")
	public static <T extends View> T getView(View convertView, int id) {

		SparseArray<View> holder = (SparseArray<View>) convertView.getTag();
		if (holder == null) {
			holder = new SparseArray<View>();
			convertView.setTag(holder);
		}

		View view = holder.get(id);
		if (view == null) {
			view = convertView.findViewById(id);
			holder.put(id, view);
		}
		return (T) view;
	}

	@SuppressWarnings("unchecked")
	public static <T extends View> T getView(View convertView, int id, int width, int height) {

		SparseArray<View> holder = (SparseArray<View>) convertView.getTag();
		if (holder == null) {
			holder = new SparseArray<View>();
			convertView.setTag(holder);
		}

		View view = holder.get(id);
		if (view == null) {
			view = convertView.findViewById(id);
			ViewGroup.LayoutParams lp = view.getLayoutParams();
			if (lp != null) {
				lp.width = width;
				lp.height = height;
			}
			holder.put(id, view);
		}
		return (T) view;
	}
}
