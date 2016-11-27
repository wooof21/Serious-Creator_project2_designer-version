/**
 * 
 */
package vipcenter;

import java.nio.charset.CoderMalfunctionError;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mkcomingd.R;

import tools.Config;
import tools.CustomProgressDialog;
import tools.Exit;
import tools.Tools;
import vipcenter.MySchedule.ScheduleAsync;
import adapter.ScheduleGVAdapter;
import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
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
public class DefaultSchedule extends Activity implements OnClickListener {

    private ImageView back;
    private LinearLayout title;
    private LinearLayout dateLL;
    private TextView _date1, _date2, _date3, _date4;
    private GridView gv;
    private TextView set;
    private TextView submit;

    private CustomProgressDialog pd;
    private ArrayList<HashMap<String, String>> list;
    public ScheduleGVAdapter adapter;

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
	new ScheduleAsync().execute();
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
	submit.setOnClickListener(this);

	title.setVisibility(View.VISIBLE);
	dateLL.setVisibility(View.GONE);
	submit.setText("提交");
	set.setText("设置好默认忙碌时间，以后每天的这个时间我都在忙！");
	set.setTextColor(Color.rgb(118, 118, 118));
    }

    class ScheduleAsync extends
	    AsyncTask<Void, Void, ArrayList<HashMap<String, String>>> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
	    // TODO Auto-generated method stub
	    super.onPreExecute();
	    pd = CustomProgressDialog.createDialog(DefaultSchedule.this);
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
		Void... params) {
	    // TODO Auto-generated method stub
	    String url = Config.DEFAULT_SCHEDULE_URL
		    + new Tools().getUserId(DefaultSchedule.this);
	    Log.e("url", url);

	    String data = new Tools().getURL(url);
	    System.out.println(data);

	    try {
		JSONObject jObject = new JSONObject(data);
		JSONObject result = jObject.getJSONObject("result");
		String code = result.getString("code");
		if (code.equals("1")) {
		    JSONArray jArray = jObject.getJSONArray("data");
		    for (int i = 0, j = jArray.length(); i < j; i++) {
			JSONObject job = jArray.optJSONObject(i);
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("dian", job.getString("dian"));
			hashMap.put("zt", job.getString("zt"));

			list.add(hashMap);
		    }
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
	    adapter = new ScheduleGVAdapter(result, DefaultSchedule.this,
		    "default");
	    gv.setAdapter(adapter);

	    gv.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1,
			int arg2, long arg3) {
		    // TODO Auto-generated method stub
		    System.out.println(list.get(arg2).get("zt"));
		    if (list.get(arg2).get("zt").equals("1")) {
			list.get(arg2).put("zt", "0");
		    } else {
			list.get(arg2).put("zt", "1");
		    }
		    adapter.notifyDataSetChanged();
		}
	    });
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
	    pd = CustomProgressDialog.createDialog(DefaultSchedule.this);
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
	    String url = Config.DEFAULT_SCHEDULE_SET_URL
		    + new Tools().getUserId(DefaultSchedule.this) + "&mlsj="
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
		Toast.makeText(DefaultSchedule.this, "提交成功！", Toast.LENGTH_SHORT).show();
		finish();
	    }else{
		Toast.makeText(DefaultSchedule.this, "提交失败，请重试！", Toast.LENGTH_SHORT).show();
	    }
	}

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
	case R.id.my_time_setting_submit:
	    new PostAsync().execute(postData());
	    break;
	default:
	    break;
	}
    }

    private String postData() {
	StringBuilder sb = new StringBuilder();
	for (HashMap<String, String> map : list) {
	    if (map.get("zt").equals("1")) {
		sb.append(map.get("dian").substring(0,
			map.get("dian").length() - 1));
		sb.append(",");
	    }
	}
	return sb.toString().substring(0, sb.length() - 1);
    }
}
