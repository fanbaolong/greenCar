package com.green.car;


import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.green.car.util.ScreenSizeUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * 
 * @author wangfeng
 *
 */
public class App extends Application {
	
    private final static String TAG = "com.flycent.crm";
    public final static boolean DEBUG = true;
    public final static double imgrote = 0.40;
    public boolean isReceiveMSG = false;
    public static Context applicationContext;
    private static App mInstance = null;
    public String address = "";
    public double lat = 0.0;
    public double lon = 0.0;
    public String time = "";
    public boolean update = false;
    
    private ScreenSizeUtil sizeUtil;
	
    public ScreenSizeUtil getSizeUtil() {
        return sizeUtil;
    }
    
    public void setSizeUtil(ScreenSizeUtil sizeUtil) {
        this.sizeUtil = sizeUtil;
    }
    
    public int getScreenWidth() {
        return sizeUtil.getScreenWidth();
    }
    
    public int getScreenHeight(){
    	return sizeUtil.getScreenHeight();
    }
	    
    @Override
    public void onCreate() {
    	super.onCreate();
    	mInstance = this;
    	sizeUtil = new ScreenSizeUtil(this);
    	//创建默认的ImageLoader配置参数  
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration  
                .createDefault(this);  
          
        //Initialize ImageLoader with configuration.  
        ImageLoader.getInstance().init(configuration);
    }
    
    /**
	 * Get display options for UniversalImageLoader
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public DisplayImageOptions getDisplayOption() {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.cacheInMemory(true).cacheOnDisc(true)
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
				.build();
		return options;
	}

    
    
    public static App getInstance() {
    	if(mInstance == null ){
			mInstance = new App();
		}
		return mInstance;
    }

    public static void showToast(Object msg) {
        Toast.makeText(getInstance(), msg + "", Toast.LENGTH_SHORT).show();
    }
    
    public static void showLog(Object msg) {
        Log.i(TAG, msg + "");
    }

    public static void SystemOut(String str) {
        System.out.println(str);
    }
    
    
    
}
