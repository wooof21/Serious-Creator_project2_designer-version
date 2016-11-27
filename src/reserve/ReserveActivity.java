/**
 * 
 */
package reserve;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tools.Config;
import tools.CustomProgressDialog;
import tools.Exit;
import tools.Tools;

import com.mkcoming.MainActivity;
import com.mkcomingd.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import adapter.OrderReserveFinishedListViewAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class ReserveActivity extends Activity implements OnClickListener {

    private TextView reserve;
    private TextView reserveUnderline;
    private TextView finished;
    private TextView finishedUnderline;
    private LinearLayout replace;
    private ListView lv;

    private View replaceView;

    private ArrayList<HashMap<String, String>> list;

    private TextView r_style;
    private TextView r_name;
    private ImageView r_pic;
    private TextView r_price;
    private TextView r_dt;
    private TextView r_remark;
    private TextView r_orderId;
    private TextView r_phone;
    private ImageView r_phone_iv;
    private TextView r_address;
    private ImageView r_address_iv;
    private TextView r_time, r_time_title;
    private TextView r_start;
    private LinearLayout r_time_ll;
    private TextView r_hr, r_min, r_sec;
    protected String id;
    private String serviceTime;
    private RelativeLayout parent;
    
    private DisplayImageOptions options;
    public HashMap<String, String> orderIdMap;
    private int hr;
    private int min;
    private int sec;
    private boolean isRun = true;
    
    

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.reserve_main);
	Exit.getInstance().addActivity(this);

	prepareView();
	prepareReplaceView();
	preperImageLoader();

	new ReserveAsync().execute();
    }

    private void prepareView() {
	reserve = (TextView) findViewById(R.id.main_reserve_tv);
	reserveUnderline = (TextView) findViewById(R.id.main_reserve_underline);
	finished = (TextView) findViewById(R.id.main_reserve_finished_tv);
	finishedUnderline = (TextView) findViewById(R.id.main_reserve_wancheng_underline);
	replace = (LinearLayout) findViewById(R.id.main_reserve_replace_ll);
	lv = (ListView) findViewById(R.id.main_reserve_check_lv);
	parent = (RelativeLayout) findViewById(R.id.reserve_parent);
	reserve.setOnClickListener(this);
	finished.setOnClickListener(this);

	replaceView = LayoutInflater.from(ReserveActivity.this).inflate(
		R.layout.qiangdan_listview_replacement, null);
    }

    private void prepareReplaceView() {
	r_style = (TextView) replaceView.findViewById(R.id.replace_style);
	r_name = (TextView) replaceView.findViewById(R.id.replace_name);
	r_pic = (ImageView) replaceView.findViewById(R.id.replace_pic);
	r_price = (TextView) replaceView.findViewById(R.id.replace_price);
	r_dt = (TextView) replaceView.findViewById(R.id.replace_dt);
	r_remark = (TextView) replaceView.findViewById(R.id.replace_remark);
	r_orderId = (TextView) replaceView.findViewById(R.id.replace_orderid);
	r_phone = (TextView) replaceView.findViewById(R.id.replace_phone);
	r_phone_iv = (ImageView) replaceView
		.findViewById(R.id.replace_phone_iv);
	r_address = (TextView) replaceView.findViewById(R.id.replace_address);
	r_address_iv = (ImageView) replaceView
		.findViewById(R.id.replace_address_iv);
	r_time = (TextView) replaceView.findViewById(R.id.replace_time);
	r_time_title = (TextView) replaceView
		.findViewById(R.id.replace_time_title);
	r_start = (TextView) replaceView.findViewById(R.id.replace_start);
	r_time_ll = (LinearLayout) replaceView
		.findViewById(R.id.replace_time_ll);
	r_hr = (TextView) replaceView.findViewById(R.id.replace_hr);
	r_min = (TextView) replaceView.findViewById(R.id.replace_min);
	r_sec = (TextView) replaceView.findViewById(R.id.replace_sec);

	r_phone_iv.setOnClickListener(this);
	r_start.setOnClickListener(new startListener());
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
		replace.removeView(lv);
		replace.addView(replaceView);
		break;
	    case 401:// 预约
		replace.removeView(lv);
		replace.addView(replaceView);
		id = (String) msg.obj;
		new CheckAsync().execute(id);
		break;
	    case 1:
		HashMap<String, String> hashMap = (HashMap<String, String>) msg.obj;
		r_style.setText(hashMap.get("fgid") + "-" + hashMap.get("zrid"));
		ImageLoader.getInstance().displayImage(hashMap.get("tx"),
			r_pic, options);
		r_name.setText(hashMap.get("nc"));
		r_price.setText("￥" + hashMap.get("je"));
		r_dt.setText(hashMap.get("fwsj"));
		r_remark.setText(hashMap.get("bz"));
		r_orderId.setText(orderIdMap.get(id));
		r_phone.setText(hashMap.get("dh"));
		r_address.setText(hashMap.get("dz"));
		r_time.setText(hashMap.get("jlfwsj"));
		serviceTime = hashMap.get("jlfwsj").substring(0,
			hashMap.get("jlfwsj").toString().length() - 2);
		break;

	    case 1000:
		String wyId = (String) msg.obj;
		new PopupWindows(ReserveActivity.this, parent, wyId);
		break;
	    default:
		break;
	    }
	}

    };

    class PopupWindows extends PopupWindow {

	public PopupWindows(Context mContext, View parent, final String id) {

	    View view = View.inflate(mContext, R.layout.wy_submit, null);
	    view.startAnimation(AnimationUtils.loadAnimation(mContext,
		    R.anim.push_bottom_in_2));
	    ImageView back = (ImageView) view.findViewById(R.id.title_back);
	    TextView submit = (TextView) view.findViewById(R.id.wyb_ok);
	    
	    setWidth(LayoutParams.FILL_PARENT);
	    setHeight(LayoutParams.FILL_PARENT);
	    // setBackgroundDrawable(new BitmapDrawable());
	    setFocusable(true);
	    setOutsideTouchable(true);
	    setContentView(view);
	    showAtLocation(parent, Gravity.CENTER, 0, 0);
	    update();

	    back.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		    // TODO Auto-generated method stub
		    dismiss();
		}
	    });

	    submit.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		    // TODO Auto-generated method stub
		    new WYAsync().execute(id);
		    dismiss();
		}
	    });

	}

    }

    class WYAsync extends AsyncTask<String, Void, String> {
	private CustomProgressDialog pd;
	private String msg = "";

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
	    // TODO Auto-generated method stub
	    super.onPreExecute();
	    pd = CustomProgressDialog.createDialog(ReserveActivity.this);
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
	    String url = Config.WY_URL + params[0] + "&zid="
		    + new Tools().getUserId(ReserveActivity.this);
	    Log.e("url", url);
	    String data = new Tools().getURL(url);
	    System.out.println(data);
	    String code = "";
	    
	    try {
		JSONObject job = new JSONObject(data);
		JSONObject result = job.getJSONObject("result");
		code = result.getString("code");
		msg = result.getString("msg");
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
		Toast.makeText(ReserveActivity.this, "违约完成！", Toast.LENGTH_SHORT).show();
		new ReserveAsync().execute();
	    }else{
		Toast.makeText(ReserveActivity.this, msg, Toast.LENGTH_SHORT).show();
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
	case R.id.main_reserve_tv:
	    reserveUnderline.setVisibility(View.VISIBLE);
	    finishedUnderline.setVisibility(View.INVISIBLE);
	    new ReserveAsync().execute();
	    break;
	case R.id.main_reserve_finished_tv:
	    reserveUnderline.setVisibility(View.INVISIBLE);
	    finishedUnderline.setVisibility(View.VISIBLE);

	    new DoneAsync().execute();
	    break;
	case R.id.replace_phone_iv:
	    Intent intent = new Intent();
	    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    ; // 关键之处
	    intent.setAction("android.intent.action.DIAL");
	    intent.setData(Uri.parse("tel:" + r_phone.getText().toString()));
	    startActivity(intent);
	    break;
	default:
	    break;
	}
    }

    class startListener implements OnClickListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
	    // TODO Auto-generated method stub
	    Intent intent1 = new Intent(ReserveActivity.this,
		    StartServerPopUpWindow.class);
	    intent1.putExtra("type", "start_servive");
	    startActivityForResult(intent1, 100);
	}

    }

    class endListener implements OnClickListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
	    // TODO Auto-generated method stub
	    new StartEndAsync().execute("end", id, "4");
	    isRun = false;
	}

    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onActivityResult(int, int,
     * android.content.Intent)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	// TODO Auto-generated method stub
	super.onActivityResult(requestCode, resultCode, data);
	switch (requestCode) {
	case 100:
	    if (resultCode == 4444) {
		Toast.makeText(this, "服务未开始！", Toast.LENGTH_SHORT).show();
	    } else {
		new StartEndAsync().execute("start", id, "3");
	    }
	    break;
	default:
	    break;
	}
    }

    /************************************************************************/

    /**
     * 计时计算
     */
    private void computeTime() {
	sec++;
	if (sec > 59) {
	    min++;
	    sec = 0;
	    if (min > 59) {
		min = 0;
		hr++;
		if (hr > 23) {
		    // 倒计时结束
		    hr = 0;
		}
	    }
	}
    }

    /**
     * 开启计时
     */
    private void startRun() {
	new Thread(new Runnable() {

	    @Override
	    public void run() {
		// TODO Auto-generated method stub
		while (isRun) {
		    try {
			Thread.sleep(1000); // sleep 1000ms
			Message message = Message.obtain();
			message.what = 1;
			timeHandler.sendMessage(message);
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		}
	    }
	}).start();
    }

    private Handler timeHandler = new Handler() {

	@Override
	public void handleMessage(Message msg) {
	    super.handleMessage(msg);
	    switch (msg.what) {
	    case 1:
		computeTime();
		r_hr.setText(hr + "");
		r_min.setText(min + "");
		r_sec.setText(sec + "");
		if (hr == 23 && min == 59 && sec == 59) {
		    r_time_ll.setVisibility(View.GONE);
		    r_time.setVisibility(View.VISIBLE);
		    r_time.setText("服务超时！");
		}
		break;

	    case 100:// 开始服务
		r_time.setVisibility(View.GONE);
		r_time_ll.setVisibility(View.VISIBLE);
		hr = 0;
		min = 0;
		sec = 0;
		r_hr.setText("" + hr);
		r_min.setText("" + min);
		r_sec.setText("" + sec);
		r_time_title.setText("服务时长：");
		r_start.setText("结束服务");
		r_start.setOnClickListener(new endListener());
		startRun();
		break;
	    case 101:// 结束服务
		Intent intent = new Intent(ReserveActivity.this,
			StartServerPopUpWindow.class);
		intent.putExtra("type", "end_service");
		intent.putExtra("id", id);
		startActivity(intent);
		break;
	    case 102:// 开始失败
		Toast.makeText(ReserveActivity.this, "服务开始失败，请重试！",
			Toast.LENGTH_SHORT).show();
		break;
	    case 103:// 结束失败
		Toast.makeText(ReserveActivity.this, "服务结束失败，请重试！",
			Toast.LENGTH_SHORT).show();
		break;
	    default:
		break;
	    }
	}
    };

    class StartEndAsync extends AsyncTask<String, Void, Void> {

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
	    pd = CustomProgressDialog.createDialog(ReserveActivity.this);
	    pd.show();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected Void doInBackground(String... params) {
	    // TODO Auto-generated method stub
	    String url = Config.START_END_SERVICE_URL
		    + new Tools().getUserId(ReserveActivity.this) + "&oid="
		    + params[1] + "&zt=" + params[2];
	    Log.e("url", url);
	    String data = new Tools().getURL(url);
	    System.out.println(data);
	    try {
		JSONObject job = new JSONObject(data);
		JSONObject result = job.getJSONObject("result");
		String code = result.getString("code");
		Message msg = timeHandler.obtainMessage();
		if (code.equals("1")) {
		    if (params[0].equals("start")) {
			msg.what = 100;
		    } else {
			msg.what = 101;
		    }
		} else {
		    if (params[0].equals("start")) {
			msg.what = 102;
		    } else {
			msg.what = 103;
		    }
		}
		msg.sendToTarget();
	    } catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(Void result) {
	    // TODO Auto-generated method stub
	    super.onPostExecute(result);
	    pd.dismiss();
	}
    }

    /************************************************************************/

    class CheckAsync extends AsyncTask<String, Void, Void> {
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
	    pd = CustomProgressDialog.createDialog(ReserveActivity.this);
	    pd.show();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected Void doInBackground(String... params) {
	    // TODO Auto-generated method stub
	    String url = Config.ORDER_RESERVE_CHECK_URL
		    + new Tools().getUserId(ReserveActivity.this) + "&oid="
		    + params[0];
	    Log.e("url", url);
	    String data = new Tools().getURL(url);
	    System.out.println(data);

	    try {
		JSONObject jObject = new JSONObject(data);
		JSONObject result = jObject.getJSONObject("result");
		String code = result.getString("code");
		if (code.equals("1")) {
		    JSONObject job = jObject.getJSONObject("data");
		    HashMap<String, String> hashMap = new HashMap<String, String>();
		    hashMap.put("tx", job.getString("tx"));
		    hashMap.put("nc", job.getString("nc"));
		    hashMap.put("fgid", job.getString("fgid"));
		    hashMap.put("zrid", job.getString("zrid"));
		    hashMap.put("je", job.getString("je"));
		    hashMap.put("jlfwsj", job.getString("jlfwsj"));
		    hashMap.put("fwsj", job.getString("fwsj"));
		    hashMap.put("bz", job.getString("bz"));
		    hashMap.put("dh", job.getString("dh"));
		    hashMap.put("dz", job.getString("dz"));

		    Message msg = handler.obtainMessage();
		    msg.what = 1;
		    msg.obj = hashMap;
		    msg.sendToTarget();
		}
	    } catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }

	    return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(Void result) {
	    // TODO Auto-generated method stub
	    super.onPostExecute(result);
	    pd.dismiss();
	}
    }

    class ReserveAsync extends
	    AsyncTask<Void, Void, ArrayList<HashMap<String, String>>> {

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
	    pd = CustomProgressDialog.createDialog(ReserveActivity.this);
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
	    String url = Config.ORDER_RESERVE_URL
		    + new Tools().getUserId(ReserveActivity.this);
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

			orderIdMap = new HashMap<String, String>();
			orderIdMap.put(job.getString("id"),
				job.getString("num"));
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
	    OrderReserveFinishedListViewAdapter adapter = new OrderReserveFinishedListViewAdapter(
		    ReserveActivity.this, result, "order_reserve", handler);
	    lv.setAdapter(adapter);

	}
    }

    class DoneAsync extends
	    AsyncTask<Void, Void, ArrayList<HashMap<String, String>>> {

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
	    pd = CustomProgressDialog.createDialog(ReserveActivity.this);
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
	    String url = Config.ORDER_DONE_URL
		    + new Tools().getUserId(ReserveActivity.this) + "&lei=2";
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
	    OrderReserveFinishedListViewAdapter adapter = new OrderReserveFinishedListViewAdapter(
		    ReserveActivity.this, result, "reserve_done", handler);
	    lv.setAdapter(adapter);

	}

    }

    private void preperImageLoader() {

	/******************* 配置ImageLoder ***********************************************/
	File cacheDir = StorageUtils.getOwnCacheDirectory(this,
		"imageloader/Cache");

	ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
		this).denyCacheImageMultipleSizesInMemory()
		.discCache(new UnlimitedDiscCache(cacheDir))// 自定义缓存路径
		.build();// 开始构建

	options = new DisplayImageOptions.Builder().cacheInMemory()
		.cacheOnDisc().imageScaleType(ImageScaleType.IN_SAMPLE_INT)
		.showImageForEmptyUri(com.mkcomingd.R.drawable.question)
		.showImageOnFail(com.mkcomingd.R.drawable.question).build();

	ImageLoader.getInstance().init(config);// 全局初始化此配置
	/*********************************************************************************/
    }
}
