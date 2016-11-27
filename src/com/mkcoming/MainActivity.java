package com.mkcoming;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import login.LoginActivity;
import reserve.GrabOrderMain;
import tools.Config;
import tools.CustomProgressDialog;
import tools.Exit;
import tools.Tools;
import com.mkcomingd.R;

import adapter.OrderReserveFinishedListViewAdapter;
import adapter.ViewPagerAdapter;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

    private TextView order;
    private TextView orderUnderline;
    private TextView finished;
    private TextView finishedUnderline;
    private LinearLayout replace;
    private ListView lv;

    private View replaceView;

    private ArrayList<HashMap<String, String>> list1, list2;

    private CustomProgressDialog pd;

    private String id, zt;

    // 定义ViewPager对象
    private ViewPager vp;

    // 定义ViewPager适配器
    private ViewPagerAdapter vpAdapter;

    // 定义一个ArrayList来存放View
    private ArrayList<View> views;//
    private boolean isExit = false;

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onBackPressed()
     */
    @Override
    public void onBackPressed() {
	// TODO Auto-generated method stub
	return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	Exit.getInstance().addActivity(this);
	prepareView();

	replaceView = LayoutInflater.from(MainActivity.this).inflate(
		R.layout.qiangdan_listview_replacement, null);
	new UpdateAsync().execute();
	if (new Tools().isFristRun(this)) {
	    SharedPreferences sharedPre = MainActivity.this
		    .getSharedPreferences("frist", Context.MODE_PRIVATE);
	    Editor editor = sharedPre.edit();
	    editor.putString("frist", "0");
	    editor.commit();
	    Log.e("指导页启动----", "");
	    // initView();
	    // initData();
	    /**********************************************************************************/

	    vp.setOnPageChangeListener(new MyOnPageChangeListener());

	    // 将要分页显示的View装入数组中
	    LayoutInflater mLi = LayoutInflater.from(this);
	    View view1 = mLi.inflate(R.layout.view1, null);
	    View view2 = mLi.inflate(R.layout.view2, null);
	    View view3 = mLi.inflate(R.layout.view3, null);
	    View view4 = mLi.inflate(R.layout.view4, null);

	    // 每个页面的view数据
	    final ArrayList<View> views = new ArrayList<View>();
	    views.add(view1);
	    views.add(view2);
	    views.add(view3);
	    views.add(view4);

	    // 填充ViewPager的数据适配器
	    PagerAdapter pagerAdapter = new PagerAdapter() {

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
		    return arg0 == arg1;
		}

		@Override
		public int getCount() {
		    return views.size();
		}

		@Override
		public void destroyItem(View container, int position,
			Object object) {
		    ((ViewPager) container).removeView(views.get(position));
		}

		@Override
		public Object instantiateItem(View container, int position) {
		    ((ViewPager) container).addView(views.get(position));
		    return views.get(position);
		}

	    };
	    vp.setAdapter(pagerAdapter);
	    /**********************************************************************************/
	} else {
	    vp.setVisibility(View.GONE);

	    init();
	}

    }

    private void init() {
	if (!new Tools().isUserLogin(this)) {
	    startActivity(new Intent(this, LoginActivity.class));
	} else {
	    // new GrabAsync().execute();
	    new GrabCheckAsync().execute();
	}
    }

    /********************************** D27 *****************************************/
    public class MyOnPageChangeListener implements OnPageChangeListener {

	@Override
	public void onPageScrollStateChanged(int arg0) {
	    // TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	    // TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
	    // TODO Auto-generated method stub

	}

    }

    public void start(View v) {
	vp.setVisibility(View.GONE);
	init();
    }

    private void prepareView() {
	order = (TextView) findViewById(R.id.main_qiangdan_tv);
	orderUnderline = (TextView) findViewById(R.id.main_qiangdang_underline);
	finished = (TextView) findViewById(R.id.main_finished_tv);
	finishedUnderline = (TextView) findViewById(R.id.main_wancheng_underline);
	replace = (LinearLayout) findViewById(R.id.main_replace_ll);
	lv = (ListView) findViewById(R.id.main_qiangdan_lv);
	vp = (ViewPager) findViewById(R.id.viewpager);

	order.setOnClickListener(this);//
	finished.setOnClickListener(this);

    }

    class UpdateAsync extends AsyncTask<Void, Void, Void> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected Void doInBackground(Void... params) {
	    // TODO Auto-generated method stub
	    String url = Config.UPDATE_CID_URL
		    + new Tools().getUserId(MainActivity.this) + "&cid="
		    + new Tools().getCID(MainActivity.this);
	    Log.e("url111", url);
	    String data = new Tools().getURL(url);
	    System.out.println(data);
	    return null;
	}

    }

    class GrabCheckAsync extends AsyncTask<Void, Void, String> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
	    // TODO Auto-generated method stub
	    super.onPreExecute();
	    pd = CustomProgressDialog.createDialog(MainActivity.this);
	    pd.show();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected String doInBackground(Void... params) {
	    // TODO Auto-generated method stub
	    String url = Config.GRAB_ORDER_CHECK_URL
		    + new Tools().getUserId(MainActivity.this);
	    Log.e("url", url);
	    String data = new Tools().getURL(url);
	    System.out.println(data);
	    String code = "";
	    try {
		JSONObject job = new JSONObject(data);
		JSONObject result = job.getJSONObject("result");
		code = result.getString("code");
		if (code.equals("1")) {
		    JSONObject jObject = job.getJSONObject("data");
		    id = jObject.getString("id");
		    zt = jObject.getString("zt");
		}
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
	    if (result.equals("0")) {
		new GrabAsync().execute();
	    } else {
		Intent intent = new Intent(MainActivity.this,
			GrabOrderMain.class);
		intent.putExtra("id", id);
		intent.putExtra("zt", zt);
		startActivity(intent);
		MainActivity.this.finish();
	    }
	}

    }

    private Handler handler = new Handler() {

	@Override
	public void handleMessage(Message msg) {
	    // TODO Auto-generated method stub
	    super.handleMessage(msg);
	    switch (msg.what) {
	    case 404:// 返回

		break;
	    case 400:// 抢单
		     // order.setText("已抢");
		     // replace.removeView(lv);
		     // replace.addView(replaceView);
		Intent intent = new Intent(MainActivity.this,
			GrabSubmitPopUp.class);
		intent.putExtra("map", (HashMap<String, String>) msg.obj);
		startActivityForResult(intent, 400);
		break;


	    default:
		break;
	    }
	}

    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	// TODO Auto-generated method stub
	super.onActivityResult(requestCode, resultCode, data);
	switch (requestCode) {
	case 400:
	    if (resultCode == 404) {
		Toast.makeText(MainActivity.this, "抢单失败，请重试！",
			Toast.LENGTH_SHORT).show();
	    }
	    break;

	default:
	    break;
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
	case R.id.main_qiangdan_tv:
	    orderUnderline.setVisibility(View.VISIBLE);
	    finishedUnderline.setVisibility(View.INVISIBLE);
	    new GrabCheckAsync().execute();
	    break;
	case R.id.main_finished_tv:
	    orderUnderline.setVisibility(View.INVISIBLE);
	    finishedUnderline.setVisibility(View.VISIBLE);
	    new DoneAsync().execute();
	    break;
	default:
	    break;
	}
    }

    /*
     * 按俩次back键退出程序
     */

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
	// TODO Auto-generated method stub
	if (keyCode == KeyEvent.KEYCODE_BACK) {
	    if (!isExit) {
		isExit = true;
		Toast.makeText(getApplicationContext(), "再按一次退出程序",
			Toast.LENGTH_SHORT).show();
		new Handler().postDelayed(new Runnable() {
		    public void run() {
			isExit = false;
		    }
		}, 2000);

		return false;
	    }
	    Exit.getInstance().exit();
	}

	return super.onKeyDown(keyCode, event);
	// if(keyCode == KeyEvent.KEYCODE_MENU){
	// super.openOptionsMenu(); // 调用这个，就可以弹出菜单
	// }

    }

    class GrabAsync extends
	    AsyncTask<String, Void, ArrayList<HashMap<String, String>>> {

	private CustomProgressDialog pd;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
	    // TODO Auto-generated method stub
	    super.onPreExecute();
	    pd = CustomProgressDialog.createDialog(MainActivity.this);
	    pd.show();
	    list1 = new ArrayList<HashMap<String, String>>();
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
	    String url = Config.ORDER_GRAB_LIST_URL
		    + new Tools().getUserId(MainActivity.this);
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
			hashMap.put("id", job.getString("id"));
			hashMap.put("tx", job.getString("tx"));
			hashMap.put("nc", job.getString("nc"));
			hashMap.put("fgid", job.getString("fgid"));
			hashMap.put("zrid", job.getString("zrid"));
			hashMap.put("je", job.getString("je"));
			hashMap.put("yysj", job.getString("yysj"));
			hashMap.put("bz", job.getString("bz"));
			hashMap.put("dz", job.getString("dz"));
			hashMap.put("fwsc", job.getString("fwsc"));
			hashMap.put("dh", job.getString("dh"));
			hashMap.put("num", job.getString("num"));

			list1.add(hashMap);
		    }

		}
	    } catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    return list1;
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
	    OrderReserveFinishedListViewAdapter adapter1 = new OrderReserveFinishedListViewAdapter(
		    MainActivity.this, list1, "order_grab", handler);
	    lv.setAdapter(adapter1);
	    // if (result.size() != 0) {
	    //
	    // }
	}
    }

    class DoneAsync extends
	    AsyncTask<String, Void, ArrayList<HashMap<String, String>>> {

	private CustomProgressDialog pd;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
	    // TODO Auto-generated method stub
	    super.onPreExecute();
	    pd = CustomProgressDialog.createDialog(MainActivity.this);
	    pd.show();
	    list2 = new ArrayList<HashMap<String, String>>();
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
	    String url = Config.ORDER_DONE_URL
		    + new Tools().getUserId(MainActivity.this) + "&lei=1";
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
			hashMap.put("id", job.getString("id"));
			hashMap.put("tx", job.getString("tx"));
			hashMap.put("nc", job.getString("nc"));
			hashMap.put("fgid", job.getString("fgid"));
			hashMap.put("zrid", job.getString("zrid"));
			hashMap.put("je", job.getString("je"));
			hashMap.put("yysj", job.getString("yysj"));
			hashMap.put("bz", job.getString("bz"));
			hashMap.put("dz", job.getString("dz"));
			hashMap.put("fwsc", job.getString("fwsc"));
			hashMap.put("dh", job.getString("dh"));
			hashMap.put("num", job.getString("num"));
			hashMap.put("pjzt", job.getString("pjzt"));

			list2.add(hashMap);
		    }

		    
		}
	    } catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    return list2;
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
	    OrderReserveFinishedListViewAdapter adapter2 = new OrderReserveFinishedListViewAdapter(
			MainActivity.this, list2, "order_done", handler);
		lv.setAdapter(adapter2);
	    // if (result.size() != 0) {
	    //
	    // }
	}

    }

}
