package com.green.car.ui;

import java.io.File;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.green.car.App;
import com.green.car.BaseActivity;
import com.green.car.ExitManager;
import com.green.car.R;
import com.green.car.injector.Injector;
import com.green.car.injector.Res;
import com.green.car.ui.fragment.MineFragment;
import com.green.car.ui.fragment.OrderFragment;
import com.green.car.ui.fragment.RentalCarFragment;
import com.green.car.ui.fragment.ServiceFragment;
import com.green.car.util.CommonUtils;
import com.green.car.util.NewToast;
import com.green.car.view.MyViewPager;
import com.green.car.view.PagerSlidingTabStrip;

public class MainActivity extends BaseActivity {

	@Res
	private PagerSlidingTabStrip tabs;

	@Res
	public MyViewPager pager;

	private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
	private MyPagerAdapter adapter;
	
	private AlertDialog dialog; 
	private int result = 0;
	private File file;
	private int fragment = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setView();
	}
	
	private String depid = "";
	
	public String getDepid() {
		return depid;
	}

	public void setDepid(String depid) {
		this.depid = depid;
	}
	
	private void setView(){
		Injector.getInstance().inject(context);

		fragments.add(new RentalCarFragment());
		fragments.add(new OrderFragment());
		fragments.add(new ServiceFragment());
		fragments.add(new MineFragment());

		tabs.setAllCaps(false);
		tabs.setTabWidth(App.getInstance().getScreenWidth() / 4);
		tabs.setDividerColorResource(android.R.color.transparent);
		tabs.setIndicatorColor(Color.TRANSPARENT);
		tabs.setTabPaddingLeftRight(0);
		tabs.setDividerPadding(0);
		pager.setOffscreenPageLimit(4);
		pager.setScanScroll(false);
		adapter = new MyPagerAdapter(getSupportFragmentManager(), tabs);
		pager.setAdapter(adapter);
		pager.setCurrentItem(0);
		fragment = 0;

		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
				.getDisplayMetrics());
		pager.setPageMargin(pageMargin);

		tabs.setViewPager(pager);
	}

	public class MyPagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider {

		private final String[] TITLES;
		private final TabView[] VIEWS;
		private final int[] imgIds = new int[]{R.drawable.wode_normal,
				R.drawable.wode_normal,R.drawable.wode_normal,  R.drawable.wode_normal};
		private final int[] imgPressedIds = new int[]{R.drawable.wode_pressed,
				R.drawable.wode_pressed,R.drawable.wode_pressed,
				R.drawable.wode_pressed};
		private final PagerSlidingTabStrip tab;

		public MyPagerAdapter(FragmentManager fm, PagerSlidingTabStrip tab) {
			super(fm);
			this.tab = tab;
			TITLES = getResources().getStringArray(R.array.bottom_navigation);
			VIEWS = new TabView[TITLES.length];
			tab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
				@Override
				public void onPageScrolled(int i, float v, int i2) {

				}

				@Override
				public void onPageSelected(int i) {
					setSelectView(i);
					fragment = i;
				}

				@Override
				public void onPageScrollStateChanged(int i) {

				}
			});
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}

		@Override
		public Fragment getItem(int position) {
			return fragments.get(position);
		}

		@Override
		public View getPageView(int i) {
			if (VIEWS[i] == null)
				VIEWS[i] = getTabItemView(i);
			return VIEWS[i];
		}

		private void setSelectView(int index) {
			for (int i = 0; i < VIEWS.length; i++) {
				if (i == index)
					VIEWS[i].setSelect(imgPressedIds[i]);
				else
					VIEWS[i].setUnSelect(imgIds[i]);
			}
		}

		/**
		 * 给Tab按钮设置图标和文字
		 */
		private TabView getTabItemView(int index) {
			return new TabView(context, index);
		}

		private class TabView extends LinearLayout {

			private final ImageView imageView;
			private final TextView textView;
			private final View layout;

			public TabView(Context context, int index) {
				super(context);
				LayoutInflater layoutInflater = LayoutInflater.from(context);
				layout = layoutInflater.inflate(R.layout.layout_sliding_tab_item, null);
				imageView = (ImageView) layout.findViewById(R.id.imageView);
				textView = (TextView) layout.findViewById(R.id.txt_cn);
				if (index == 0) {
					setSelect(imgPressedIds[index]);
				} else {
					setUnSelect(imgIds[index]);
				}

				TextView textView = (TextView) layout.findViewById(R.id.txt_cn);
				textView.setText(TITLES[index]);
//				textView.setTextSize(getResources().getDimension(R.dimen.system_textSize) / getResources().getDisplayMetrics().density);
				addView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
			}

			private void setSelect(int id) {
				imageView.setImageResource(id);
				textView.setTextColor(getResources().getColor(R.color.green_deep));
				//				layout.setBackgroundColor(getResources().getColor(R.color.base_title_alpth));
			}

			public void setUnSelect(int imgId) {
				imageView.setImageResource(imgId);
				textView.setTextColor(getResources().getColor(R.color.text_6));
				//				layout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
			}
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if (fragment != 0) {
				pager.setCurrentItem(0);
				fragment = 0;
				return true;
			}
			if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
				// if (curFragment instanceof IndexFragment) {
				if (!CommonUtils.isFastDoubleClick(3600)) {
					NewToast.makeText(context, R.string.re_exit);
				} else {
					ExitManager.getInstance().exit();
					onBackPressed();
				}
				return true;
			} else {
				getSupportFragmentManager().popBackStack();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void rightNavClick() {
		
	}

}
