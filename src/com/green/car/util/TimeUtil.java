package com.green.car.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class TimeUtil {

	public TimeUtil() {
		super();
	};

	public static String getTimestamp() {
		// 由于 SimpleDateFormat 不是线程安全的，故 每次使用时都应该 new 一个实例
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-HH-dd hh:mm:ss",
				Locale.getDefault());
		return sdf.format(new Date());
	}

	private String getCurrentDate() {
		String dateStr = "2013-10-01 12:12:00";
		SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		if (dFormat != null && date != null) {
			dateStr = dFormat.format(date);
		}
		return dateStr;
	}

	public static String getShortDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		return sdf.format(date);
	}

	/**
	 * 计算date之前n天的日期
	 */
	public static Date getDateBefore(Date date, int n) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - n);
		return now.getTime();
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


	public static String getStringDateByYuGao(String time) {
		SimpleDateFormat sf = new SimpleDateFormat("MM-dd HH:mm");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date purchaseTime = new Date();
		try {
			purchaseTime = sdf.parse(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sf.format(purchaseTime);
	}

	public static String getStringDateByYuGao1(String time) {
		SimpleDateFormat sf = new SimpleDateFormat("HH:mm");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date purchaseTime = new Date();
		try {
			purchaseTime = sdf.parse(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sf.format(purchaseTime);
	}

	public static String getStringDateByOrder(String time) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date purchaseTime = new Date();
		try {
			purchaseTime = sdf.parse(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sf.format(purchaseTime);
	}

	public static String getStringDateByIndexList(String time) {
		SimpleDateFormat sf = new SimpleDateFormat("HH:mm");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date purchaseTime = new Date();
		try {
			purchaseTime = sdf.parse(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sf.format(purchaseTime);
	}

	public static String getStringDateByTopicDetails(String time) {
		SimpleDateFormat sf = new SimpleDateFormat("MM/dd HH:mm");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date purchaseTime = new Date();
		try {
			purchaseTime = sdf.parse(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sf.format(purchaseTime);
	}


	public static String getStringDateByEssenceWeek(String time) {
		SimpleDateFormat sf = new SimpleDateFormat("yy年MM月");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date purchaseTime = new Date();
		try {
			purchaseTime = sdf.parse(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getStringWeek(purchaseTime.getDay()) + "<br>"
				+ sf.format(purchaseTime);
	}

	public static String getStringWeek(int i) {
		String[] str = new String[] { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
				"星期六" };
		return str[i];
	}

    /**
     * 将时间戳转换为显示时间
     * @param systime 服务器时间毫秒数
     * @param timestamp 毫秒数
     * @return
     */
    public static String convertTime(long systime,long timestamp){
        long timeGap = (systime-timestamp)/1000l;//与现在时间相差秒数
        String timeStr = null;
        /*if(timeGap>24*60*60){//1天以上
            //timeStr = timeGap/(24*60*60)+"天前";

            timeStr = getStandardTime(timestamp,1);

        }else if(timeGap>60*60){//1小时-24小时
            //timeStr = timeGap/(60*60)+"小时前";
        }else if(timeGap>60){//1分钟-59分钟
            //timeStr = timeGap/60+"分钟前";
        }else{//1秒钟-59秒钟
           // timeStr = "刚刚";
        }*/
        if(timeGap>24*60*60){//1天以上
            timeStr = getStandardTime(timestamp,1);
        }else{
            timeStr = getStandardTime(timestamp,2);
        }
        return timeStr;
    }
    
	public static String convertTime2(long systime,long timestamp){
		long timeGap = (systime-timestamp)/1000l;//与现在时间相差秒数
		String timeStr = null;
        if(timeGap > 24 * 60 * 60){//1天以上
            timeStr = getStandardTime2(timestamp,1);
        } else if(timeGap > 60*60){//1小时-24小时
            timeStr = timeGap/(60*60) + "小时前";
        } else if(timeGap > 60){//1分钟-59分钟
            timeStr = timeGap/60 + "分钟前";
        } else {//1秒钟-59秒钟
           timeStr = "刚刚";
        }
		return timeStr;
	}
	
    public static String getStandardTime(long timestamp,int type){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
        Date date = new Date(timestamp);
        String str = null;
        if(type == 1)
        {
            str=getStandardTime2(date);
//            str = sdf.format(date);
        } else if(type == 2)
        {
            //str = "今天 "+sdf2.format(date);
            str=getStandardTime2(date);
        }
        return str;
    }
    public static String getStandardTime2(long timestamp,int type){
    	Date date = new Date(timestamp);
    	String str = null;
    	if(type == 1)
    	{
    		str=getStandardTime3(date);
    	} else if(type == 2)
    	{
    		str=getStandardTime3(date);
    	}
    	return str;
    }

    public static String getStandardTime2(Date date){
        String str="";
        Calendar cTime=Calendar.getInstance();
        Calendar cNow=Calendar.getInstance();
        cTime.setTime(date);
        int yeara = cTime.get(Calendar.YEAR);
        int yearb = cNow.get(Calendar.YEAR);
        if(yeara != yearb){
            str = yeara+"年"+(cTime.get(Calendar.MONTH)+1)+"月"+cTime.get(Calendar.DATE)+"日 "+(cTime.get(Calendar.HOUR_OF_DAY)<10?"0"+cTime.get(Calendar.HOUR_OF_DAY):cTime.get(Calendar.HOUR_OF_DAY))+":"+(cTime.get(Calendar.MINUTE)<10?"0"+cTime.get(Calendar.MINUTE):cTime.get(Calendar.MINUTE));
        } else {
            str = (cTime.get(Calendar.MONTH)+1)+"月"+cTime.get(Calendar.DATE)+"日 "+(cTime.get(Calendar.HOUR_OF_DAY)<10?"0"+cTime.get(Calendar.HOUR_OF_DAY):cTime.get(Calendar.HOUR_OF_DAY))+":"+(cTime.get(Calendar.MINUTE)<10?"0"+cTime.get(Calendar.MINUTE):cTime.get(Calendar.MINUTE));
        }
        return str;
    }
    public static String getStandardTime3(Date date){
    	String str="";
    	Calendar cTime=Calendar.getInstance();
    	Calendar cNow=Calendar.getInstance();
    	cTime.setTime(date);
    	int yeara = cTime.get(Calendar.YEAR);
    	int yearb = cNow.get(Calendar.YEAR);
    	if(yeara != yearb){
    		str = yeara+"-"+(cTime.get(Calendar.MONTH)+1)+"-"+cTime.get(Calendar.DATE)+" "+(cTime.get(Calendar.HOUR_OF_DAY)<10?"0"+cTime.get(Calendar.HOUR_OF_DAY):cTime.get(Calendar.HOUR_OF_DAY))+":"+(cTime.get(Calendar.MINUTE)<10?"0"+cTime.get(Calendar.MINUTE):cTime.get(Calendar.MINUTE));
    	} else {
    		str = (cTime.get(Calendar.MONTH)+1)+"-"+cTime.get(Calendar.DATE)+" "+(cTime.get(Calendar.HOUR_OF_DAY)<10?"0"+cTime.get(Calendar.HOUR_OF_DAY):cTime.get(Calendar.HOUR_OF_DAY))+":"+(cTime.get(Calendar.MINUTE)<10?"0"+cTime.get(Calendar.MINUTE):cTime.get(Calendar.MINUTE));
    	}
    	return str;
    }
    
    /**
     * 时间戳转化成string/date
     * @param a
     * @return
     * @throws ParseException
     */
    public static String getStrTime(long a) throws ParseException{
		SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );

		Long time=new Long(a);

		String d = format.format(time);

		Date date=format.parse(d);
		return d;
    }
    
    /**
     * date/string转化成时间戳(String time="1970-01-06 11:45:55";)
     * @return
     * @throws ParseException
     */
    public static long  getLongTime(String time) throws ParseException{
    	SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );

    	

    	Date date = format.parse(time);

		return date.getTime();
    	
    }
    
    /**
     * 时间戳转化成string/date
     * @param a
     * @return
     * @throws ParseException
     */
    public static String getStrTime1(long a) {
    	SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
    	
    	Long time=new Long(a);
    	
    	String d = format.format(time);
    	
    	try {
			Date date=format.parse(d);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return d;
    }
}
