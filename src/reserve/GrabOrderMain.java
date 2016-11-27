/**
 * 
 */
package reserve;

import java.io.File;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import reserve.ReserveActivity.StartEndAsync;
import reserve.ReserveActivity.endListener;
import tools.Config;
import tools.CustomProgressDialog;
import tools.Exit;
import tools.Tools;

import com.mkcomingd.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
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
public class GrabOrderMain extends Activity implements OnClickListener {

    private TextView grab;
    private TextView grabUnderline;
    private TextView finished;
    private TextView finishedUnderline;
    private TextView r_style;
    private TextView r_name;
    private ImageView r_pic;
    private TextView r_price;
    private TextView r_remark;
    private TextView r_orderId;
    private TextView r_phone;
    private ImageView r_phone_iv;
    private TextView r_address;
    private ImageView r_address_iv;
    private TextView r_time;
    private TextView r_time_title;
    private TextView r_start;
    private LinearLayout r_time_ll;
    private TextView r_hr;
    private TextView r_min;
    private TextView r_sec;
    private TextView r_tv;

    private String id, zt;

    private int hr;
    private int min;
    private int sec;
    private boolean isRun = true;

    private CustomProgressDialog pd;

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.grab_order_yes_main);
	Exit.getInstance().addActivity(this);
	prepareView();
	id = getIntent().getExtras().getString("id");
	zt = getIntent().getExtras().getString("zt");
	new InfoAsync().execute(id);
	if (zt.equals("1")) {
	    r_start.setText("等待用户付款");
	    r_start.setBackgroundColor(Color.rgb(38, 151, 231));
	} else if (zt.equals("2")) {
	    r_start.setText("开始服务");
	    r_start.setBackgroundColor(Color.rgb(255, 90, 132));
	    r_tv.setText("总部会统计所有造型师的造型时长");
	    r_start.setOnClickListener(new startListener());
	    
	} else {
	    r_start.setText("结束服务");
	    r_start.setBackgroundColor(Color.rgb(255, 90, 132));
	    r_tv.setText("总部会统计所有造型师的造型时长");
	    r_time.setVisibility(View.GONE);
	    r_time_ll.setVisibility(View.VISIBLE);
	    hr = 0;
	    min = 0;
	    sec = 0;
	    r_hr.setText("" + hr);
	    r_min.setText("" + min);
	    r_sec.setText("" + sec);
	    r_time_title.setText("服务时长：");
	    startRun();
	    r_start.setOnClickListener(new endListener());
	}
    }

    private void prepareView() {
	grab = (TextView) findViewById(R.id.grab_order_tv);
	grabUnderline = (TextView) findViewById(R.id.grab_order_underline);
	finished = (TextView) findViewById(R.id.grab_order_finished_tv);
	finishedUnderline = (TextView) findViewById(R.id.grab_order_wancheng_underline);

	r_style = (TextView) findViewById(R.id.grab_order_style);
	r_name = (TextView) findViewById(R.id.grab_order_name);
	r_pic = (ImageView) findViewById(R.id.grab_order_pic);
	r_price = (TextView) findViewById(R.id.grab_order_price);
	r_remark = (TextView) findViewById(R.id.grab_order_remark);
	r_orderId = (TextView) findViewById(R.id.grab_order_orderid);
	r_phone = (TextView) findViewById(R.id.grab_order_phone);
	r_phone_iv = (ImageView) findViewById(R.id.grab_order_phone_iv);
	r_address = (TextView) findViewById(R.id.grab_order_address);
	r_address_iv = (ImageView) findViewById(R.id.grab_order_address_iv);
	r_time = (TextView) findViewById(R.id.grab_order_time);
	r_time_title = (TextView) findViewById(R.id.grab_order_time_title);
	r_start = (TextView) findViewById(R.id.grab_order_start);
	r_time_ll = (LinearLayout) findViewById(R.id.grab_order_time_ll);
	r_hr = (TextView) findViewById(R.id.grab_order_hr);
	r_min = (TextView) findViewById(R.id.grab_order_min);
	r_sec = (TextView) findViewById(R.id.grab_order_sec);
	r_tv = (TextView) findViewById(R.id.grab_order_status_tv);

	
	preperImageLoader();
    }

    private Handler handler = new Handler() {

	@Override
	public void handleMessage(Message msg) {
	    // TODO Auto-generated method stub
	    super.handleMessage(msg);
	    switch (msg.what) {
	    case 1:
		HashMap<String, String> hashMap = (HashMap<String, String>) msg.obj;
		r_style.setText(hashMap.get("fgid") + "-" + hashMap.get("zrid"));
		ImageLoader.getInstance().displayImage(hashMap.get("tx"),
			r_pic, options);
		r_name.setText(hashMap.get("nc"));
		r_price.setText("￥" + hashMap.get("je"));
		r_remark.setText(hashMap.get("bz"));
		if (zt.equals("1")) {
		    r_phone.setText(hashMap.get("用户付款后可查看"));
		} else {
		    r_phone.setText(hashMap.get("dh"));
		    r_phone_iv.setOnClickListener(new OnClickListener() {
		        
		        @Override
		        public void onClick(View v) {
		    	// TODO Auto-generated method stub
		            Intent intent = new Intent();
			    intent.setAction("android.intent.action.DIAL");
			    intent.setData(Uri.parse("tel:" + phoneNum));
			    startActivity(intent);
		        }
		    });
		}
		r_address.setText(hashMap.get("dz"));
		r_time.setText(hashMap.get("jlfwsj"));

		break;

	    default:
		break;
	    }
	}

    };

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
		if (hr == 0 && min == 0 && sec == 0) {
		    r_time_ll.setVisibility(View.GONE);
		    r_time.setVisibility(View.VISIBLE);
		    r_time.setText("服务时间已用完！");
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
		Intent intent = new Intent(GrabOrderMain.this,
			StartServerPopUpWindow.class);
		intent.putExtra("type", "end_service");
		intent.putExtra("id", id);
		startActivity(intent);
		break;
	    case 102:// 开始失败
		Toast.makeText(GrabOrderMain.this, "服务开始失败，请重试！",
			Toast.LENGTH_SHORT).show();
		break;
	    case 103:// 结束失败
		Toast.makeText(GrabOrderMain.this, "服务结束失败，请重试！",
			Toast.LENGTH_SHORT).show();
		break;
	    default:
		break;
	    }
	}
    };
    private DisplayImageOptions options;
    public String phoneNum;

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
	    pd = CustomProgressDialog.createDialog(GrabOrderMain.this);
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
		    + new Tools().getUserId(GrabOrderMain.this) + "&oid="
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

    class startListener implements OnClickListener {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
	    // TODO Auto-generated method stub
	    Intent intent = new Intent(GrabOrderMain.this,
		    StartServerPopUpWindow.class);
	    intent.putExtra("type", "start_servive");
	    startActivityForResult(intent, 100);
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

    /************************************************************************/

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
	    if (resultCode == 1000) {
		new StartEndAsync().execute("start", id, "3");
	    } else {
		Toast.makeText(GrabOrderMain.this, "开始服务取消！",
			Toast.LENGTH_SHORT).show();
	    }
	    break;

	default:
	    break;
	}
    }

    class InfoAsync extends AsyncTask<String, Void, String> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
	    // TODO Auto-generated method stub
	    super.onPreExecute();
	    pd = CustomProgressDialog.createDialog(GrabOrderMain.this);
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
	    String url = Config.ORDER_RESERVE_CHECK_URL
		    + new Tools().getUserId(GrabOrderMain.this) + "&oid="
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
		    phoneNum = job.getString("dh");
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
	protected void onPostExecute(String result) {
	    // TODO Auto-generated method stub
	    super.onPostExecute(result);
	    pd.dismiss();
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
	case R.id.grab_order_phone_iv:
	    
	    break;

	default:
	    break;
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
