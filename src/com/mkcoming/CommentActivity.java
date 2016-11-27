/**
 * 
 */
package com.mkcoming;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import reserve.ReserveActivity;
import tools.Config;
import tools.CustomProgressDialog;
import tools.Exit;
import tools.Tools;
import view.MarqueeTV;

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
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class CommentActivity extends Activity implements OnClickListener {

    private ImageView back;
    private ImageView pic;
    private TextView name;
    private MarqueeTV style;
    private TextView price;
    private TextView level;
    private TextView timeCount;
    private LinearLayout good_ll;
    private TextView good_tv;
    private ImageView good_iv;
    private LinearLayout medium_ll;
    private TextView medium_tv;
    private ImageView medium_iv;
    private LinearLayout bad_ll;
    private TextView bad_tv;
    private ImageView bad_iv;
    private RatingBar rb;
    private EditText comment;
    private TextView submit;

    private CustomProgressDialog pd;
    private String rate;

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.comment_main);
	Exit.getInstance().addActivity(this);
	prepareView();

	rate = "1";
	new AsyncTask<String, Void, Void>() {

	    @Override
	    protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pd = CustomProgressDialog.createDialog(CommentActivity.this);
		pd.show();
	    }

	    @Override
	    protected Void doInBackground(String... params) {
		// TODO Auto-generated method stub
		String url = Config.COMMENT_MAIN_URL
			+ new Tools().getUserId(CommentActivity.this) + "&oid="
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
			hashMap.put("dj", job.getString("dj"));
			hashMap.put("fwsc", job.getString("fwsc"));

			Message msg = handler.obtainMessage();
			msg.obj = hashMap;
			msg.sendToTarget();
		    }
		} catch (JSONException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		return null;
	    }

	    @Override
	    protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		pd.dismiss();
	    }
	}.execute(getIntent().getExtras().getString("id"));
    }

    private Handler handler = new Handler() {

	@Override
	public void handleMessage(Message msg) {
	    // TODO Auto-generated method stub
	    super.handleMessage(msg);
	    HashMap<String, String> hashMap = (HashMap<String, String>) msg.obj;
	    ImageLoader.getInstance().displayImage(hashMap.get("tx"), pic,
		    options);
	    name.setText(hashMap.get("nc"));
	    style.setText(hashMap.get("fgid") + "-" + hashMap.get("zrid"));
	    price.setText("￥" + hashMap.get("je"));
	    level.setText("(" + hashMap.get("dj") + ")");
	    timeCount.setText(hashMap.get("fwsc") + "分钟");
	}

    };
    private DisplayImageOptions options;

    private void prepareView() {
	back = (ImageView) findViewById(R.id.title_back);
	pic = (ImageView) findViewById(R.id.comment_main_pic);
	name = (TextView) findViewById(R.id.comment_main_name);
	style = (MarqueeTV) findViewById(R.id.comment_main_style);
	price = (TextView) findViewById(R.id.comment_main_price);
	level = (TextView) findViewById(R.id.comment_main_level);
	timeCount = (TextView) findViewById(R.id.comment_main_time_count);
	good_ll = (LinearLayout) findViewById(R.id.comment_main_good_ll);
	good_tv = (TextView) findViewById(R.id.comment_main_good_tv);
	good_iv = (ImageView) findViewById(R.id.comment_main_good_iv);
	medium_ll = (LinearLayout) findViewById(R.id.comment_main_medium_ll);
	medium_tv = (TextView) findViewById(R.id.comment_main_medium_tv);
	medium_iv = (ImageView) findViewById(R.id.comment_main_medium_iv);
	bad_ll = (LinearLayout) findViewById(R.id.comment_main_bad_ll);
	bad_tv = (TextView) findViewById(R.id.comment_main_bad_tv);
	bad_iv = (ImageView) findViewById(R.id.comment_main_bad_iv);
	rb = (RatingBar) findViewById(R.id.comment_main_rb);
	comment = (EditText) findViewById(R.id.comment_main_et);
	submit = (TextView) findViewById(R.id.comment_main_submit);

	back.setOnClickListener(this);
	good_ll.setOnClickListener(this);
	medium_ll.setOnClickListener(this);
	bad_ll.setOnClickListener(this);
	submit.setOnClickListener(this);
	preperImageLoader();
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
	case R.id.comment_main_good_ll:
	    setBg("good");
	    rate = "1";
	    break;
	case R.id.comment_main_medium_ll:
	    setBg("medium");
	    rate = "2";
	    break;
	case R.id.comment_main_bad_ll:
	    setBg("bad");
	    rate = "3";
	    break;
	case R.id.comment_main_submit:
	    String rating = "" + rb.getRating();
	    System.out.println(rating);
	    if (comment.getText().toString().length() == 0) {
		Toast.makeText(this, "说点什么吧！", Toast.LENGTH_SHORT).show();
	    } else {
		String comment_utf8 = "";
		try {
		    comment_utf8 = URLEncoder.encode(comment
		    	.getText().toString(), "utf-8");
		} catch (UnsupportedEncodingException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		new PostAsync().execute(
			getIntent().getExtras().getString("id"), comment_utf8, rate,
			"" + rb.getRating());
	    }
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
	    pd = CustomProgressDialog.createDialog(CommentActivity.this);
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
	    String url = Config.COMMENT_POST_URL
		    + new Tools().getUserId(CommentActivity.this) + "&oid="
		    + params[0] + "&nr=" + params[1] + "&pj=" + params[2]
		    + "&gt=" + params[3];
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
		Toast.makeText(CommentActivity.this, "评价成功！", Toast.LENGTH_SHORT).show();
		startActivity(new Intent(CommentActivity.this, ReserveActivity.class));
	    }else{
		Toast.makeText(CommentActivity.this, "评价失败，请重试！", Toast.LENGTH_SHORT).show();
	    }
	}
    }

    private void setBg(String rate) {
	if (rate.equals("good")) {
	    good_ll.setBackgroundColor(Color.rgb(255, 87, 133));
	    medium_ll.setBackgroundColor(Color.rgb(255, 255, 255));
	    bad_ll.setBackgroundColor(Color.rgb(255, 255, 255));
	    good_tv.setTextColor(Color.rgb(255, 255, 255));
	    medium_tv.setTextColor(Color.rgb(10, 11, 19));
	    bad_tv.setTextColor(Color.rgb(10, 11, 19));
	    good_iv.setImageResource(R.drawable.comment_good_select);
	    medium_iv.setImageResource(R.drawable.comment_medium_unselect);
	    bad_iv.setImageResource(R.drawable.comment_bad_unselect);
	} else if (rate.equals("medium")) {
	    good_ll.setBackgroundColor(Color.rgb(255, 255, 255));
	    medium_ll.setBackgroundColor(Color.rgb(255, 87, 133));
	    bad_ll.setBackgroundColor(Color.rgb(255, 255, 255));
	    good_tv.setTextColor(Color.rgb(10, 11, 19));
	    medium_tv.setTextColor(Color.rgb(255, 255, 255));
	    bad_tv.setTextColor(Color.rgb(10, 11, 19));
	    good_iv.setImageResource(R.drawable.comment_good_unselect);
	    medium_iv.setImageResource(R.drawable.comment_medium_select);
	    bad_iv.setImageResource(R.drawable.comment_bad_unselect);
	} else {
	    good_ll.setBackgroundColor(Color.rgb(255, 255, 255));
	    medium_ll.setBackgroundColor(Color.rgb(255, 255, 255));
	    bad_ll.setBackgroundColor(Color.rgb(255, 87, 133));
	    good_tv.setTextColor(Color.rgb(10, 11, 19));
	    medium_tv.setTextColor(Color.rgb(10, 11, 19));
	    bad_tv.setTextColor(Color.rgb(255, 255, 255));
	    good_iv.setImageResource(R.drawable.comment_good_unselect);
	    medium_iv.setImageResource(R.drawable.comment_medium_unselect);
	    bad_iv.setImageResource(R.drawable.comment_bad_select);
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
