package tools;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class Tools {

    public String getURL(String urlStr) {
	HttpURLConnection httpcon = null;
	InputStream in = null;
	String data = "";

	try {
	    URL url = new URL(urlStr);
	    httpcon = (HttpURLConnection) url.openConnection();
	    httpcon.setDoInput(true);
	    httpcon.setDoOutput(true);
	    httpcon.setUseCaches(false);
	    httpcon.setRequestMethod("GET");
	    in = httpcon.getInputStream();
	    InputStreamReader isr = new InputStreamReader(in);
	    BufferedReader bufferReader = new BufferedReader(isr);
	    String input = "";
	    while ((input = bufferReader.readLine()) != null) {
		data = input + data;
	    }

	} catch (MalformedURLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    if (in != null) {
		try {
		    in.close();
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    }
	    if (httpcon != null) {
		httpcon.disconnect();
	    }
	}

	return data;

    }

    public void scaleInAnimation(View v) {
	ScaleAnimation sa = new ScaleAnimation(1.0f, 0.95f, 1.0f, 0.95f,
		Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
		0.5f);
	sa.setRepeatCount(0);
	sa.setFillAfter(false);
	sa.setDuration(100);
	v.startAnimation(sa);
    }

    public void upDown(View v) {
	TranslateAnimation ta = new TranslateAnimation(0, 0, 0, 5);
	ta.setDuration(500);
	ta.setInterpolator(new CycleInterpolator(5));
	v.startAnimation(ta);
    }

    public String doPostData(String urlStr) {
	HttpURLConnection httpcon = null;
	InputStream in = null;
	String data = "";

	try {
	    URL url = new URL(urlStr);
	    httpcon = (HttpURLConnection) url.openConnection();
	    httpcon.setDoInput(true);

	    httpcon.setDoOutput(true);
	    httpcon.setUseCaches(false);
	    httpcon.setRequestMethod("POST");
	    in = httpcon.getInputStream();
	    InputStreamReader isr = new InputStreamReader(in);
	    BufferedReader bufferReader = new BufferedReader(isr);
	    String input = "";
	    while ((input = bufferReader.readLine()) != null) {
		data = input + data;
	    }

	} catch (MalformedURLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} finally {
	    if (in != null) {
		try {
		    in.close();
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	    }
	    if (httpcon != null) {
		httpcon.disconnect();
	    }
	}

	return data;

    }

    public String doPostData(String urlStr, String data) throws IOException {
	String result = "";
	byte[] xmlbyte = data.getBytes("UTF-8");
	Log.e("post接口上传 格式的内容---utf8---", data);

	URL url = new URL(urlStr);

	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	conn.setConnectTimeout(5000);
	conn.setDoOutput(true);// 允许输出
	conn.setDoInput(true);
	conn.setUseCaches(false);// 不使用缓存
	conn.setRequestMethod("POST");
	conn.getOutputStream().write(xmlbyte);
	conn.getOutputStream().flush();
	conn.getOutputStream().close();

	Log.e("conn.getResponseCode()----", "" + conn.getResponseCode());

	if (conn.getResponseCode() != 200)
	    throw new RuntimeException("请求url失败");
	int codeOrder = conn.getResponseCode();

	if (codeOrder == 200) {

	    InputStream inStream = conn.getInputStream();// 获取返回数据

	    // 使用输出流来输出字符(可选)
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    byte[] buf = new byte[1024];
	    int len;
	    while ((len = inStream.read(buf)) != -1) {
		out.write(buf, 0, len);
	    }
	    result = out.toString("UTF-8");
	    Log.e("post返回结果--------", "" + result);
	    out.close();

	} else {

	}

	return result;
    }

    public boolean getInternet(Context context) {
	ConnectivityManager con = (ConnectivityManager) context
		.getSystemService(Activity.CONNECTIVITY_SERVICE);
	boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
		.isConnectedOrConnecting();
	boolean internet = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
		.isConnectedOrConnecting();
	if (wifi || internet) {
	    return true;
	} else {
	    return false;
	}
    }

    public boolean isUserLogin(Context context) {
	SharedPreferences sharedPre = context.getSharedPreferences("config",
		Context.MODE_PRIVATE);

	String username = sharedPre.getString("username", "");
	String password = sharedPre.getString("password", "");
	Log.e("username", "A" + username);
	Log.e("password", "B" + password);
	if ("".equals(username) || "".equals(password)) {
	    return false;
	} else {
	    return true;
	}
    }

    public String getUserId(Context context) {
	SharedPreferences sharedPre = context.getSharedPreferences("config",
		Context.MODE_PRIVATE);
	String id = sharedPre.getString("id", "");
	System.out.println("id ---> " + id);
	return id;
    }
    
    public String getCID(Context context) {
	SharedPreferences sharedPre = context.getSharedPreferences("config",
		Context.MODE_PRIVATE);
	String id = sharedPre.getString("cid", "");
	System.out.println("cid ---> " + id);
	return id;
    }

    public String getDate() {
	Calendar c = Calendar.getInstance();
	c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
	return String.valueOf(c.get(Calendar.MONTH) + 1) + "."
		+ String.valueOf(c.get(Calendar.DAY_OF_MONTH));
    }

    public String getWeek() {
	Calendar c = Calendar.getInstance();
	c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
	String day = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
	if ("1".equals(day)) {
	    day = "日";
	} else if ("2".equals(day)) {
	    day = "一";
	} else if ("3".equals(day)) {
	    day = "二";
	} else if ("4".equals(day)) {
	    day = "三";
	} else if ("5".equals(day)) {
	    day = "四";
	} else if ("6".equals(day)) {
	    day = "五";
	} else if ("7".equals(day)) {
	    day = "六";
	}
	return day;
    }
    
    public String Today() {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
	Calendar c = Calendar.getInstance();
	String d = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1)
		+ "-" + c.get(Calendar.DAY_OF_MONTH);
	Date date = java.sql.Date.valueOf(d);

	Log.e("today", sdf.format(date));

	return sdf.format(date);
    }
    
    public String formatDate(String date){
	String _date = date.substring(0, date.length()-3);
	Log.e("_date", _date);
	String[] d = _date.split("\\.");
	Log.e("d1", d[0]);
	Log.e("d2", d[1]);
	Calendar c = Calendar.getInstance();
	String dd = c.get(Calendar.YEAR) + "-" + d[0] + "-" + d[1];
	Log.e("dd", dd);
	
	return dd;
    }
    
    public String nextDay1(String date) {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
	Date d = java.sql.Date.valueOf(Today());
	Calendar calendar1 = getCalendarDate(d);
	calendar1.setTime(d);
	calendar1.add(calendar1.DATE, 1);
	d = calendar1.getTime();
	sdf.format(d);
	Log.e("nextDay ---", sdf.format(d));
	return sdf.format(d);
    }
    public String nextDay2(String date) {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
	Date d = java.sql.Date.valueOf(Today());
	Calendar calendar1 = getCalendarDate(d);
	calendar1.setTime(d);
	calendar1.add(calendar1.DATE, 2);
	d = calendar1.getTime();
	sdf.format(d);
	Log.e("nextDay ---", sdf.format(d));
	return sdf.format(d);
    }
    public String nextDay3(String date) {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
	Date d = java.sql.Date.valueOf(Today());
	Calendar calendar1 = getCalendarDate(d);
	calendar1.setTime(d);
	calendar1.add(calendar1.DATE, 3);
	d = calendar1.getTime();
	sdf.format(d);
	Log.e("nextDay ---", sdf.format(d));
	return sdf.format(d);
    }
    
    public Calendar getCalendarDate(Date date) {

	Calendar calendar = Calendar.getInstance();
	calendar.setTime(date);
	calendar.set(Calendar.HOUR_OF_DAY, 0);
	calendar.set(Calendar.MINUTE, 0);
	calendar.set(Calendar.SECOND, 0);
	calendar.set(Calendar.MILLISECOND, 0);

	return calendar;
    }
    /**
     * 对double数据进行取精度.
     * <p>
     * For example: <br>
     * double value = 100.345678; <br>
     * double ret = round(value,4,BigDecimal.ROUND_HALF_UP); <br>
     * ret为100.3457 <br>
     * 
     * @param value
     *            double数据.
     * @param scale
     *            精度位数(保留的小数位数).
     * @param roundingMode
     *            精度取值方式.
     * @return 精度计算后的数据.
     */
    // public static double round(double value, int scale, int roundingMode) {
    // BigDecimal bd = new BigDecimal(value);
    // bd = bd.setScale(scale, roundingMode);
    // double d = bd.doubleValue();
    // bd = null;
    // return d;
    // }
    //
    // public double getDistance(double lat1, double myLat, double lon1, double
    // myLon){
    // if(myLat == 0 && myLat == 0){
    // return -1;
    // }else{
    // LatLng ll1 = new LatLng(lat1, lon1);
    // LatLng ll2 = new LatLng(myLat, myLon);
    // double distance = DistanceUtil.getDistance(ll1, ll2);
    // return distance;
    // }
    //
    // }
    
    public boolean isFristRun(Context context) {
	Log.e("进入指导页判断-------", "-------");
	SharedPreferences sharedPre = context.getSharedPreferences(
		"frist", Context.MODE_PRIVATE);
	String fristString = sharedPre.getString("frist", "1");
	Log.e("frist====", "" + fristString);
	if ("1".equals(fristString)) {
	    return true;
	} else {
	    return false;
	}

    }
}
