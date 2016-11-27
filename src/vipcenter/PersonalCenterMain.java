/**
 * 
 */
package vipcenter;

import java.io.File;
import java.util.HashMap;

import login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import tools.Config;
import tools.CustomProgressDialog;
import tools.Exit;
import tools.Tools;
import vip.PersonalCenterMain;

import com.mkcomingd.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class PersonalCenterMain extends Activity implements OnClickListener {

    private LinearLayout ll;
    private ImageView pic;
    private TextView name;
    private TextView level;
    private TextView jobDone;
    private TextView rate;
    private TextView schedule;
    private TextView work;
    private TextView brand;
    private TextView honor;
    private TextView comment;
    private TextView quit;
    
    private TextView wyb;
    private LinearLayout wybLL;

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
	setContentView(R.layout.vip_center);
	Exit.getInstance().addActivity(this);
	prepareView();

	new AsyncTask<Void, Void, Void>() {

	    @Override
	    protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pd = CustomProgressDialog.createDialog(PersonalCenterMain.this);
		pd.show();
	    }

	    @Override
	    protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		String url = Config.PERSONAL_CENTER_MAIN_URL
			+ new Tools().getUserId(PersonalCenterMain.this);
		Log.e("url", url);

		String data = new Tools().getURL(url);
		System.out.println(data);

		try {
		    JSONObject jsonObject = new JSONObject(data);
		    JSONObject job = jsonObject.getJSONObject("result");
		    String code = job.getString("code");
		    if (code.equals("1")) {
			JSONObject jObject = jsonObject.getJSONObject("data");
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("id", jObject.getString("id"));
			hashMap.put("pic", jObject.getString("pic"));
			hashMap.put("xm", jObject.getString("xm"));
			hashMap.put("dj", jObject.getString("dj"));
			hashMap.put("jdcs", jObject.getString("jdcs"));
			hashMap.put("hpl", jObject.getString("hpl"));
			hashMap.put("wyb", jObject.getString("wyb"));

			Message msg = handler.obtainMessage();
			msg.obj = hashMap;
			handler.sendMessage(msg);
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

	}.execute();
    }

    private Handler handler = new Handler() {

	@Override
	public void handleMessage(Message msg) {
	    // TODO Auto-generated method stub
	    super.handleMessage(msg);
	    HashMap<String, String> hashMap = (HashMap<String, String>) msg.obj;
	    ImageLoader.getInstance().displayImage(hashMap.get("pic"), pic,
		    options);
	    name.setText(hashMap.get("xm").toString());
	    level.setText(hashMap.get("dj").toString());
	    jobDone.setText("已接单" + hashMap.get("jdcs").toString() + "次");
	    rate.setText("服务评价" + hashMap.get("hpl").toString() + "次");
	    wyb.setText(hashMap.get("wyb"));
	}

    };
    private DisplayImageOptions options;

    private void prepareView() {
	ll = (LinearLayout) findViewById(R.id.vip_center_ll);
	pic = (ImageView) findViewById(R.id.vip_center_pic);
	name = (TextView) findViewById(R.id.vip_center_name);
	level = (TextView) findViewById(R.id.vip_center_level);
	jobDone = (TextView) findViewById(R.id.vip_center_job_done);
	rate = (TextView) findViewById(R.id.vip_center_comment_rate);
	schedule = (TextView) findViewById(R.id.vip_center_my_schedule);
	work = (TextView) findViewById(R.id.vip_center_my_work);
	brand = (TextView) findViewById(R.id.vip_center_brand);
	honor = (TextView) findViewById(R.id.vip_center_honor);
	wyb = (TextView) findViewById(R.id.vip_center_wyb);
	wybLL = (LinearLayout) findViewById(R.id.vip_center_wyb_ll);
	comment = (TextView) findViewById(R.id.vip_center_comment);
	quit = (TextView) findViewById(R.id.vip_center_quit);
	
	quit.setOnClickListener(this);
	ll.setOnClickListener(this);
	schedule.setOnClickListener(this);
	work.setOnClickListener(this);
	brand.setOnClickListener(this);
	honor.setOnClickListener(this);
	wybLL.setOnClickListener(this);
	comment.setOnClickListener(this);
	
	
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
	case R.id.vip_center_ll:
	    startActivity(new Intent(this, PersonalInfo.class));
	    break;
	case R.id.vip_center_my_schedule:
	    startActivity(new Intent(this, MySchedule.class));
	    break;
	case R.id.vip_center_my_work:
	    startActivity(new Intent(this, MyWork.class));
	    break;
	case R.id.vip_center_honor:
	    startActivity(new Intent(this, MyHonor.class));
	    break;
	case R.id.vip_center_wyb_ll:
	    startActivity(new Intent(this, WYHint.class));
	    break;
	case R.id.vip_center_comment:
	    startActivity(new Intent(this, CommentListMain.class));
	    break;
	case R.id.vip_center_quit:
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);

	    builder.setMessage("是否确定退出账号");
	    builder.setPositiveButton("确定",
		    new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,
				int whichButton) {

			    SharedPreferences sharedPre = PersonalCenterMain.this
				    .getSharedPreferences("config",
					    Context.MODE_PRIVATE);
			    SharedPreferences.Editor editor = sharedPre.edit();

			    editor.remove("username");
			    editor.remove("password");
			    editor.remove("id");
			    editor.clear();
			    editor.commit();

			    // Intent intent = getIntent();
			    // setResult(ResultCode.SUCCESS, intent);
			    startActivity(new Intent(PersonalCenterMain.this,
				    LoginActivity.class));
			    finish();
			}
		    });
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
