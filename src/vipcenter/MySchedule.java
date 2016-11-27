/**
 * 
 */
package vipcenter;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tools.Config;
import tools.CustomProgressDialog;
import tools.Exit;
import tools.Tools;

import com.mkcomingd.R;

import adapter.ScheduleGVAdapter;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class MySchedule extends Activity implements OnClickListener {

    private ImageView back;
    private LinearLayout title;
    private LinearLayout dateLL;
    private TextView _date1, _date2, _date3, _date4;
    private GridView gv;
    private TextView set;
    private TextView submit;

    private CustomProgressDialog pd;
    private ArrayList<HashMap<String, String>> list;
    
    private String setData;
    public ScheduleGVAdapter adapter;
    
    private String d1, d2, d3, d4;

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.my_time_setting);
	Exit.getInstance().addActivity(this);
	prepareView();
	new ScheduleAsync().execute("0");
	
//	String date1 = new Tools().Today();
//	String date2 = new Tools().nextDay1(date1);
//	String date3 = new Tools().nextDay2(date1);
//	String date4 = new Tools().nextDay3(date1);
//	
//	String day1 = new Tools().getWeek();
//	String day2 = null, day3 = null, day4 = null;
//	if(day1.equals("一")){
//	    day2 = "二";
//	    day3 = "三";
//	    day4 = "四";
//	}else if(day1.equals("二")){
//	    day2 = "三";
//	    day3 = "四";
//	    day4 = "五";
//	}else if(day1.equals("三")){
//	    day2 = "四";
//	    day3 = "五";
//	    day4 = "六";
//	}else if(day1.equals("四")){
//	    day2 = "五";
//	    day3 = "六";
//	    day4 = "日";
//	}else if(day1.equals("五")){
//	    day2 = "六";
//	    day3 = "日";
//	    day4 = "一";
//	}else if(day1.equals("六")){
//	    day2 = "日";
//	    day3 = "一";
//	    day4 = "二";
//	}else if(day1.equals("日")){
//	    day2 = "一";
//	    day3 = "二";
//	    day4 = "三";
//	}
//	
//	
//	_date1.setText(date1.split("-")[1] + "." + date1.split("-")[2] + "(" + day1 + ")");
//	_date2.setText(date2.split("-")[1] + "." + date2.split("-")[2] + "(" + day2 + ")");
//	_date3.setText(date3.split("-")[1] + "." + date3.split("-")[2] + "(" + day3 + ")");
//	_date4.setText(date4.split("-")[1] + "." + date4.split("-")[2] + "(" + day4 + ")");

    }

    private void prepareView() {
	back = (ImageView) findViewById(R.id.title_back);
	title = (LinearLayout) findViewById(R.id.my_time_title_ll);
	dateLL = (LinearLayout) findViewById(R.id.my_time_date_ll);
	_date1 = (TextView) findViewById(R.id.my_time_date1);
	_date2 = (TextView) findViewById(R.id.my_time_date2);
	_date3 = (TextView) findViewById(R.id.my_time_date3);
	_date4 = (TextView) findViewById(R.id.my_time_date4);
	gv = (GridView) findViewById(R.id.my_time_gv);
	set = (TextView) findViewById(R.id.ttvvvvvvvvvvvvvvvvvvvvvvvvv);
	submit = (TextView) findViewById(R.id.my_time_setting_submit);

	back.setOnClickListener(this);
	_date1.setOnClickListener(this);
	_date2.setOnClickListener(this);
	_date3.setOnClickListener(this);
	_date4.setOnClickListener(this);
	set.setOnClickListener(this);
	submit.setOnClickListener(this);

	title.setVisibility(View.GONE);
	_date1.setBackgroundColor(Color.rgb(221, 221, 221));
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v) {
	// TODO Auto-generated method stub
	switch (v.getId()) {
	case R.id.title_back:
	    finish();
	    break;
	case R.id.my_time_date1:
	    new ScheduleAsync().execute("0");
	    _date1.setBackgroundColor(Color.rgb(221, 221, 221));
	    _date2.setBackgroundColor(Color.rgb(255, 255, 255));
	    _date3.setBackgroundColor(Color.rgb(255, 255, 255));
	    _date4.setBackgroundColor(Color.rgb(255, 255, 255));
	    setData = d1;
	    break;
	case R.id.my_time_date2:
	    new ScheduleAsync().execute("1");
	    _date2.setBackgroundColor(Color.rgb(221, 221, 221));
	    _date1.setBackgroundColor(Color.rgb(255, 255, 255));
	    _date3.setBackgroundColor(Color.rgb(255, 255, 255));
	    _date4.setBackgroundColor(Color.rgb(255, 255, 255));
	    setData = d2;
	    break;
	case R.id.my_time_date3:
	    new ScheduleAsync().execute("2");
	    _date3.setBackgroundColor(Color.rgb(221, 221, 221));
	    _date2.setBackgroundColor(Color.rgb(255, 255, 255));
	    _date1.setBackgroundColor(Color.rgb(255, 255, 255));
	    _date4.setBackgroundColor(Color.rgb(255, 255, 255));
	    setData = d3;
	    break;
	case R.id.my_time_date4:
	    new ScheduleAsync().execute("3");
	    _date4.setBackgroundColor(Color.rgb(221, 221, 221));
	    _date2.setBackgroundColor(Color.rgb(255, 255, 255));
	    _date3.setBackgroundColor(Color.rgb(255, 255, 255));
	    _date1.setBackgroundColor(Color.rgb(255, 255, 255));
	    setData = d4;
	    break;
	case R.id.ttvvvvvvvvvvvvvvvvvvvvvvvvv:
	    startActivity(new Intent(this, DefaultSchedule.class));
	    break;
	case R.id.my_time_setting_submit:
	    new PostAsync().execute(postData());
	    break;
	default:
	    break;
	}
    }
    
    class PostAsync extends AsyncTask<String, Void, String> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
	    // TODO Auto-generated method stub
	    super.onPreExecute();
	    pd = CustomProgressDialog.createDialog(MySchedule.this);
	    pd.show();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected String doInBackground(String... params) {
	    // TODO Auto-generated method stub
	    String url = Config.MY_SCHEDULE_SET_URL
		    + new Tools().getUserId(MySchedule.this) + "&dtsj=" + setData + "&szsj="
		    + params[0];
	    Log.e("url", url);
	    String data = new Tools().getURL(url);
	    System.out.println(data);
	    String code = "";
	    
	    try {
		JSONObject job = new JSONObject(data);
		JSONObject result = job.getJSONObject("result");
		code = result.getString("code");
	    } catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    return code;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(String result) {
	    // TODO Auto-generated method stub
	    super.onPostExecute(result);
	    pd.dismiss();
	    if(result.equals("1")){
		Toast.makeText(MySchedule.this, "提交成功！", Toast.LENGTH_SHORT).show();
		finish();
	    }else{
		Toast.makeText(MySchedule.this, "提交失败，请重试！", Toast.LENGTH_SHORT).show();
	    }
	}

    }
    
    private String postData(){
	StringBuilder sb = new StringBuilder();
	for(HashMap<String, String> map : list){
	    if(!map.get("zt").equals("2")){
		sb.append(map.get("dian").substring(0,
			map.get("dian").length() - 1));
		sb.append(",");
		sb.append(map.get("zt"));
		sb.append("|");
	    }
	}
	return sb.toString().substring(0, sb.length()-1);
    }
    
    private Handler handler = new Handler(){

	@Override
	public void handleMessage(Message msg) {
	    // TODO Auto-generated method stub
	    super.handleMessage(msg);
	    Bundle b = msg.getData();
	    _date1.setText(b.getString("date1"));
	    _date2.setText(b.getString("date2"));
	    _date3.setText(b.getString("date3"));
	    _date4.setText(b.getString("date4"));
	    d1 = new Tools().formatDate(b.getString("date1"));
	    d2 = new Tools().formatDate(b.getString("date2"));
	    d3 = new Tools().formatDate(b.getString("date3"));
	    d4 = new Tools().formatDate(b.getString("date4"));
	}
	
    };

    class ScheduleAsync extends
	    AsyncTask<String, Void, ArrayList<HashMap<String, String>>> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
	    // TODO Auto-generated method stub
	    super.onPreExecute();
	    pd = CustomProgressDialog.createDialog(MySchedule.this);
	    pd.show();
	    list = new ArrayList<HashMap<String, String>>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected ArrayList<HashMap<String, String>> doInBackground(
		String... params) {
	    // TODO Auto-generated method stub
	    String url = Config.MY_SCHEDULE_URL
		    + new Tools().getUserId(MySchedule.this) + "&day="
		    + params[0];
	    Log.e("url", url);

	    String data = new Tools().getURL(url);
	    System.out.println(data);

	    try {
		JSONObject jObject = new JSONObject(data);
		JSONObject result = jObject.getJSONObject("result");
		String code = result.getString("code");
		if (code.equals("1")) {
		    JSONObject _data = jObject.getJSONObject("data");
		    JSONArray jArray = _data.getJSONArray("shijian");
		    for (int i = 0, j = jArray.length(); i < j; i++) {
			JSONObject job = jArray.optJSONObject(i);
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("dian", job.getString("dian"));
			hashMap.put("ml", job.getString("ml"));
			hashMap.put("zt", job.getString("zt"));

			list.add(hashMap);
		    }
		    String date1 = _data.getString("rq");
		    String date2 = _data.getString("rq1");
		    String date3 = _data.getString("rq2");
		    String date4 = _data.getString("rq3");
		    Bundle b = new Bundle();
		    b.putString("date1", date1);
		    b.putString("date2", date2);
		    b.putString("date3", date3);
		    b.putString("date4", date4);
		    Message msg = handler.obtainMessage();
		    msg.setData(b);
		    msg.sendToTarget();
		    
		}
	    } catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(ArrayList<HashMap<String, String>> result) {
	    // TODO Auto-generated method stub
	    super.onPostExecute(result);
	    pd.dismiss();
	    adapter = new ScheduleGVAdapter(result,
		    MySchedule.this, "set");
	    gv.setAdapter(adapter);
	    
	    gv.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1,
			int arg2, long arg3) {
		    // TODO Auto-generated method stub
		    System.out.println(list.get(arg2).get("zt"));
		    if (list.get(arg2).get("zt").equals("1")) {
			list.get(arg2).put("zt", "0");
			list.get(arg2).put("ml", "空闲");
		    } else {
			list.get(arg2).put("zt", "1");
			list.get(arg2).put("ml", "忙碌");
		    }
		    adapter.notifyDataSetChanged();
		}
	    });
	}

    }

}
