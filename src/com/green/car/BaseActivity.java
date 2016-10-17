package com.green.car;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.green.car.http.MyRequestHandler;
import com.green.car.http.RequestTaskManager;
import com.green.car.injector.AutomaticViewHolderUtil;
import com.green.car.view.LoadingDialog;
import com.umeng.analytics.MobclickAgent;

/**
 * 基本Activity，管理所有Activity
 * @author think
 *
 */
@SuppressLint("InlinedApi")
public abstract class BaseActivity extends FragmentActivity {

	private LinearLayout ll_titleBar;
	public TextView tv_title;
	public TextView tv_left;
	private RelativeLayout rl_left;
	private LinearLayout rl_right;
	public ImageView iv_right_one, iv_right_two;
	public ImageView iv_left, iv_right;
	private LinearLayout llBg;
	private RelativeLayout title_bg;
	public TextView tv_right;
	private LinearLayout ly_content;
	public View v_line;
	// 提示栏区域
	public TextView netTitle;
	public ImageView netImg;
	public RelativeLayout netRela;
	public LinearLayout netReflesh;
	// 内容区域的布局
	private View contentView;

	private String mPageName = "";

	public static LoadingDialog dialog;
	public BaseActivity context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_base);
		context = this;
		initView();

		String subClassName = getClass().getName();
		mPageName = subClassName.substring(subClassName.lastIndexOf(".") + 1);

		dialog = new LoadingDialog(this, R.layout.layout_dialog_progressbar, R.style.Theme_dialog);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(true);

		//将activity添加到activity管理，方便退出
		ExitManager.getInstance().getActivities().add(this);
	}

	/**
	 * 全屏
	 */
	public void noTitleView() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
	}

	public void showDialog() {
		if (dialog != null) {
			dialog.show();
		} else {
			dialog = new LoadingDialog(this, R.layout.layout_dialog_progressbar, R.style.Theme_dialog);
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
		}
	}

	public void showDialog(String msg) {
		if (dialog != null) {
			dialog.setMessage(msg);
			dialog.show();
		} else {
			dialog = new LoadingDialog(this, R.layout.layout_dialog_progressbar, R.style.Theme_dialog);
			dialog.setCanceledOnTouchOutside(false);
			dialog.setMessage(msg);
			dialog.show();
		}
	}

	public void dismissDialog() {
		if (!context.isFinishing()&&dialog != null) {
			dialog.dismiss();
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	/**
	 * 初始化第三方集成参数
	 */
	/*
	 * private void initThirdParty() { // ========= umeng START ===========
	 * MobclickAgent.setDebugMode(true); // SDK在统计Fragment时，需要关闭Activity自带的页面统计，
	 * // 然后在每个页面中重新集成页面统计的代码(包括调用了 onResume 和 onPause 的Activity)。
	 * MobclickAgent.openActivityDurationTrack(false); //
	 * MobclickAgent.setAutoLocation(true); //
	 * MobclickAgent.setSessionContinueMillis(1000); 
	 */

	/**
	 * 设置是否对日志信息进行加密, 默认false(不加密)
	 */
	/*
	 * AnalyticsConfig.enableEncrypt(true);
	 * 
	 * MobclickAgent.updateOnlineConfig(this); // ========= umeng END
	 * ============= }
	 */
	private void initView() {
		v_line = findViewById(R.id.v_line);
		v_line.setVisibility(View.GONE);
		llBg = (LinearLayout) findViewById(R.id.ll_bg);
		ll_titleBar = (LinearLayout) findViewById(R.id.ll_titlebar);
		title_bg = (RelativeLayout) findViewById(R.id.title_bg);
		tv_title = (TextView) findViewById(R.id.tv_title);
		rl_left = (RelativeLayout) findViewById(R.id.btn_left);
		rl_right = findView(R.id.btn_right);
		tv_left = (TextView) findView(R.id.tv_left);
		iv_left = (ImageView) findViewById(R.id.iv_left);
		iv_right = (ImageView) findViewById(R.id.iv_right);
		iv_right_one = (ImageView) findViewById(R.id.ivFavorite);
		iv_right_two = (ImageView) findViewById(R.id.ivBook);
		tv_right = (TextView) findViewById(R.id.tv_right);
		ly_content = (LinearLayout) findViewById(R.id.ll_content);

		// 提示界面
		netImg = (ImageView) findView(R.id.net_img);
		netTitle = (TextView) findView(R.id.net_title);
		netRela = (RelativeLayout) findView(R.id.net_rela);
		netReflesh = (LinearLayout) findView(R.id.net_refresh);

		iv_left.setOnClickListener(leftNavClickListener);
		tv_left.setOnClickListener(leftNavClickListener);
		iv_right.setOnClickListener(rightNavClickListener);
		tv_right.setOnClickListener(rightNavClickListener);
	}

	/** 设置一级头部颜色（蓝色） */
	public void setFristTitleBackgroundColor() {
		title_bg.setBackgroundColor(getResources().getColor(R.color.color_title_bg));
		tv_title.setTextColor(getResources().getColor(R.color.white));
		iv_left.setImageResource(R.drawable.nav_back);
		v_line.setVisibility(View.GONE);
	}

	/** 隐藏头部下面横线 */
	public void hideLine() {
		v_line.setVisibility(View.GONE);
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart(mPageName);
		MobclickAgent.onResume(this);
		// JPushInterface.onResume(this);
		if (dialog == null) {
			dialog = new LoadingDialog(this, R.layout.layout_dialog_progressbar, R.style.Theme_dialog);
			dialog.setCanceledOnTouchOutside(false);
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd(mPageName);
		MobclickAgent.onPause(this);
	}

	/***
	 * 设置内容区域
	 * 
	 * @param resId
	 *            资源文件ID
	 */
	public void setContentLayout(int resId) {
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		contentView = inflater.inflate(resId, null);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.MATCH_PARENT);
		contentView.setLayoutParams(layoutParams);
		contentView.setBackgroundDrawable(null);

		if (null != ly_content) {
			ly_content.addView(contentView);

			AutomaticViewHolderUtil.findAllViews(this, ly_content);
		}
	}

	/***
	 * 设置内容区域
	 * 
	 * @param view
	 *            View对象
	 */
	public void setContentLayout(View view) {
		if (null != ly_content) {
			ly_content.addView(view);
		}
	}

	@Override
	protected void onDestroy() {
		if (dialog != null) {
			dialog.cancel();
			dialog = null;
		}
		ExitManager.getInstance().getActivities().remove(this);
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.dismissDialog();
		overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
	}

	/**
	 * 设置标题视图View
	 * 
	 * @param view
	 */
	public void setTitleView(View view) {
		if (ll_titleBar != null) {
			ll_titleBar.removeAllViews();
			ll_titleBar.addView(view);
		}
	}

	/**
	 * 得到内容的View
	 * 
	 * @return
	 */
	public View getLyContentView() {
		return contentView;
	}

	/**
	 * 得到左边的按钮
	 * 
	 * @return
	 */
	public ImageView getbtn_left() {
		return iv_left;
	}

	/**
	 * 得到右边的按钮
	 * 
	 * @return
	 */
	public ImageView getbtn_right() {
		return iv_right;
	}

	/**
	 * 设置标题
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		if (null != tv_title) {
			tv_title.setText(title);
		}
	}

	/**
	 * 得到右边文字的按钮
	 * 
	 * @return
	 */
	public TextView gettv_right() {
		return tv_right;
	}


	public void setTvRight(String right) {
		if (tv_right != null) {
			rl_right.setVisibility(View.VISIBLE);
			iv_right.setVisibility(View.GONE);
			tv_right.setVisibility(View.VISIBLE);
			tv_right.setText(right);
		}
	}

	/**
	 * 设置标题
	 * 
	 * @param resId
	 */
	public void setTitle(int resId) {
		tv_title.setText(getString(resId));
	}

	/**
	 * 设置页面背景（从资源中获取）
	 * 
	 * @param resId
	 */
	public void setBackgroundResource(int resId) {
		llBg.setBackgroundResource(resId);
	}

	public void setBackgroundColor(String color) {
		llBg.setBackgroundColor(Color.parseColor(color));
	}

	public void setBackgroundColor(int color) {
		llBg.setBackgroundColor(color);
	}

	/**
	 * 设置左边按钮的图片资源
	 * 
	 * @param resId
	 */
	public void setbtn_leftRes(int resId) {
		if (null != iv_left) {
			iv_left.setImageResource(resId);
		}
	}

	/**
	 * 设置左边按钮的图片资源
	 * 
	 * @param bm
	 */
	public void setbtn_leftRes(Drawable drawable) {
		if (null != iv_left) {
			iv_left.setImageDrawable(drawable);
		}

	}

	/**
	 * 设置右边按钮的图片资源
	 * 
	 * @param resId
	 */
	public void setbtn_rightRes(int resId) {
		rl_right.setVisibility(View.VISIBLE);
		if (null != iv_right) {
			iv_right.setVisibility(View.VISIBLE);
			iv_right.setImageResource(resId);
		}
	}

	/** 第一张右图 */
	public void setRightOne(int resId) {
		rl_right.setVisibility(View.VISIBLE);
		if (null != iv_right_one) {
			iv_right_one.setVisibility(View.VISIBLE);
			iv_right_one.setImageResource(resId);
		}
	}

	/** 第二张右图 */
	public void setRightTwo(int resId) {
		rl_right.setVisibility(View.VISIBLE);
		if (null != iv_right_two) {
			iv_right_two.setVisibility(View.VISIBLE);
			iv_right_two.setImageResource(resId);
		}
	}

	public void setShareVisible(View.OnClickListener onClickListener) {
		rl_right.setVisibility(View.VISIBLE);
		findView(R.id.iv_right).setVisibility(View.VISIBLE);
		findView(R.id.iv_right).setOnClickListener(onClickListener);
	}

	public void setBookVisible(View.OnClickListener onClickListener, boolean status) {
		rl_right.setVisibility(View.VISIBLE);
		ImageView ivBook = findView(R.id.ivBook);
		ivBook.setVisibility(View.VISIBLE);
		ivBook.setOnClickListener(onClickListener);
	}

	public void setFavoriteVisible(View.OnClickListener onClickListener, boolean status) {
		rl_right.setVisibility(View.VISIBLE);
		ImageView ivFavorite = findView(R.id.ivFavorite);
		ivFavorite.setVisibility(View.VISIBLE);
		ivFavorite.setOnClickListener(onClickListener);
	}

	/**
	 * 设置右边按钮的图片资源
	 * 
	 * @param drawable
	 */
	public void setbtn_rightRes(Drawable drawable) {
		if (null != iv_right) {
			iv_right.setImageDrawable(drawable);
		}
	}

	/**
	 * 隐藏上方的标题栏
	 */
	public void hideTitleView() {
		if (ll_titleBar != null) {
			ll_titleBar.setVisibility(View.GONE);
		}
	}

	/**
	 * 显示上方的标题栏
	 */
	public void showTitleView() {
		if (ll_titleBar != null) {
			ll_titleBar.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 隐藏左边的视图
	 */
	public void hidebtn_left() {
		if (null != rl_left) {
			rl_left.setVisibility(View.INVISIBLE);
		}
	}

	/***
	 * 隐藏右边的视图
	 */
	public void hidebtn_right() {
		if (null != rl_right) {
			rl_right.setVisibility(View.INVISIBLE);
		}
	}

	/***
	 * 显示左边,文字
	 */
	public void setTv_left(String left) {
		if (null != iv_left) {
			iv_left.setVisibility(View.INVISIBLE);
		}
		if (null != tv_left) {
			tv_left.setVisibility(View.VISIBLE);
			tv_left.setText(left);
		}
	}

	/***
	 * 显示右边,文字
	 */
	public void setTv_right(String right) {
		if (null != iv_right) {
			iv_right.setVisibility(View.INVISIBLE);
		}
		if (null != tv_right) {
			tv_right.setVisibility(View.VISIBLE);
			tv_right.setText(right);
		}
	}

	/**
	 * 设置导航左边部分
	 */
	public void setNavLeftView(View view) {
		if (rl_left != null) {
			rl_left.removeAllViews();
			rl_left.addView(view);
		}
	}

	/**
	 * 设置导航右边部分
	 */
	public void setNavRightView(View view) {
		if (rl_right != null) {
			rl_right.removeAllViews();
			rl_right.addView(view);
		}
	}

	public void loadFail(boolean isShow) {
		loadFail(isShow, "", 0, null);
	}

	/** 加载失败或空页面 */
	public void loadFail(boolean isShow, String title, int res, OnClickListener l) {
		if (netRela == null)
			return;
		if (isShow) {
			netRela.setVisibility(View.VISIBLE);
			if (l != null)
				netReflesh.setOnClickListener(l);
			netImg.setImageResource(res == 0 ? R.drawable.not_net : res);
			netTitle.setText(TextUtils.isEmpty(title) ? "卫星被UFO吸走了!" : title);
		} else {
			netRela.setVisibility(View.GONE);
		}

	}

	/**
	 * 检验字符串是否为空
	 * 
	 * @param string
	 * @return
	 */
	public static boolean isNull(String string) {
		boolean t1 = "".equals(string);
		boolean t2 = string == null;
		boolean t3 = "null".equals(string);
		if (t1 || t2 || t3) {
			return true;
		} else {
			return false;
		}
	}

	// public void onBackPressed() {
	// // AppManager.getInstance().finishActivity(1);
	// finish();
	// }

	public void leftNavClick() {
		onBackPressed();
		IBinder iBinder = getWindow().peekDecorView().getWindowToken();
		//		CommonUtil.hide(this, iBinder);
	}

	public View.OnClickListener leftNavClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			leftNavClick();
		}
	};

	public View.OnClickListener rightNavClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			rightNavClick();
		}
	};

	public abstract void rightNavClick();

	@SuppressWarnings("unchecked")
	protected <V extends View> V findView(int id) {
		return (V) findViewById(id);
	}

	@SuppressWarnings("unchecked")
	protected <V extends View> V findView(View parent, int id) {
		return (V) parent.findViewById(id);
	}

	public void startBaseActivity(Intent intent, boolean anim) {
		startActivity(intent);
		if (android.os.Build.VERSION.SDK_INT > 10) {
			if (anim) {
				overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
			} else {
				overridePendingTransition(R.anim.login_enter, R.anim.alpha);
			}
		}
	}

	public void requestPost(boolean isShow, String url, HashMap<String, Object> reqMap){
		new RequestTaskManager().requestDataByPost(this, isShow, url, url, reqMap, mHandler);
	}

	MyRequestHandler mHandler = new MyRequestHandler(){
		public void onSuccess(Object result, String value, String tag) {
			responseSuccess(result, value, tag);
		};

		public void onFailure(Object result) {

		};
	};

	public void responseSuccess(Object result, String value, String tag){

	}
}
