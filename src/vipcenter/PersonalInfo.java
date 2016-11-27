/**
 * 
 */
package vipcenter;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class PersonalInfo extends Activity implements OnClickListener {

    private ImageView back;
    private ImageView pic;
    private EditText name;
    private EditText phone;
    private TextView sex;
    private Spinner year;
    private EditText intro;
    private TextView add;
    private ViewGroup container1;
    private ViewGroup container2;
    private ViewGroup container3;
    private TextView submit;
    private DisplayImageOptions options;

    private CustomProgressDialog pd;
    private String[] tag1, tag2, tag3;

    private TextView containerTv;
    private boolean isFristTime = true;
    /** 标签之间的间距 px */
    final int itemMargins = 5;

    /** 标签的行间距 px */
    final int lineMargins = 5;

    private String _sex, yrid;
    private HashMap<String, String> yrId;
    private ArrayList<String> yrList;

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.personal_info);
	Exit.getInstance().addActivity(this);
	prepareView();

	new AsyncTask<Void, Void, Void>() {

	    @Override
	    protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pd = CustomProgressDialog.createDialog(PersonalInfo.this);
		pd.show();
		yrList = new ArrayList<String>();
		yrId = new HashMap<String, String>();
	    }

	    @Override
	    protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		String url = Config.PERSONAL_INFO_URL
			+ new Tools().getUserId(PersonalInfo.this);
		Log.e("url", url);

		String data = new Tools().getURL(url);
		System.out.println(data);

		try {
		    JSONObject jObject = new JSONObject(data);
		    JSONObject jsonObject = jObject.getJSONObject("result");
		    String code = jsonObject.getString("code");
		    if (code.equals("1")) {
			JSONObject job = jObject.getJSONObject("data");
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("id", job.getString("id"));
			hashMap.put("pic", job.getString("pic"));
			hashMap.put("xm", job.getString("xm"));
			hashMap.put("xb", job.getString("xb"));
			hashMap.put("dh", job.getString("dh"));
			hashMap.put("grjj", job.getString("grjj"));

			JSONArray cynx = job.getJSONArray("cynx");
			for (int i = 0, j = cynx.length(); i < j; i++) {
			    JSONObject nx = cynx.optJSONObject(i);
			    yrList.add(nx.getString("classname"));
			    yrId.put(nx.getString("id"),
				    nx.getString("classname"));
			}
			yrid = yrId.get(yrList.get(0));

			Message msg = handler.obtainMessage();
			msg.what = 1;
			msg.obj = hashMap;
			handler.sendMessage(msg);

			JSONArray jArray1 = job.getJSONArray("xczx");
			JSONArray jArray2 = job.getJSONArray("hzpp");
			JSONArray jArray3 = job.getJSONArray("fwfw");
			tag1 = new String[jArray1.length()];
			tag2 = new String[jArray2.length()];
			tag3 = new String[jArray3.length()];
			for (int i = 0, j = jArray1.length(); i < j; i++) {
			    JSONObject job1 = jArray1.optJSONObject(i);
			    tag1[i] = job1.getString("classname");
			}
			for (int i = 0, j = jArray2.length(); i < j; i++) {
			    JSONObject job2 = jArray2.optJSONObject(i);
			    tag2[i] = job2.getString("classname");
			}
			for (int i = 0, j = jArray3.length(); i < j; i++) {
			    JSONObject job3 = jArray3.optJSONObject(i);
			    tag3[i] = job3.getString("classname");
			}
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
	    switch (msg.what) {
	    case 1:
		HashMap<String, String> hashMap = (HashMap<String, String>) msg.obj;
		ImageLoader.getInstance().displayImage(
			Config.URL + "/" + hashMap.get("pic"), pic, options);
		name.setText(hashMap.get("xm").toString());
		phone.setText(hashMap.get("dh").toString());
		Log.e("dh", hashMap.get("dh").toString());
		if (hashMap.get("xb").toString().equals("1")) {
		    sex.setText("男");
		} else {
		    sex.setText("女");
		}
		intro.setText(hashMap.get("grjj").toString());

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
			PersonalInfo.this, R.layout.area_spinner_tv, R.id.text,
			yrList);
		adapter.setDropDownViewResource(R.layout.area_spinner_dropdown);
		year.setAdapter(adapter);
		year.setOnItemSelectedListener(new OnItemSelectedListener() {

		    @Override
		    public void onItemSelected(AdapterView<?> arg0, View arg1,
			    int arg2, long arg3) {
			// TODO Auto-generated method stub
			yrid = yrId.get(yrList.get(arg2));
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		    }
		});
		break;

	    default:
		break;
	    }
	}

    };

    /**
     * 首页热门搜索， 自动换行linearlayout
     * */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
	super.onWindowFocusChanged(hasFocus);
	if (hasFocus && isFristTime) {
	    isFristTime = false;
	    final int containerWidth = container1.getMeasuredWidth()
		    - container1.getPaddingRight()
		    - container1.getPaddingLeft();

	    final LayoutInflater inflater = getLayoutInflater();

	    /** 用来测量字符的宽度 */
	    final Paint paint = new Paint();
	    final TextView textView = (TextView) inflater.inflate(
		    R.layout.container_textview, null);
	    final int itemPadding = textView.getCompoundPaddingLeft()
		    + textView.getCompoundPaddingRight();
	    final LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(
		    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	    tvParams.setMargins(0, 0, itemMargins, 0);

	    paint.setTextSize(textView.getTextSize());

	    LinearLayout layout1 = new LinearLayout(this);
	    layout1.setLayoutParams(new LinearLayout.LayoutParams(
		    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	    layout1.setOrientation(LinearLayout.HORIZONTAL);

	    LinearLayout layout2 = new LinearLayout(this);
	    layout2.setLayoutParams(new LinearLayout.LayoutParams(
		    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	    layout2.setOrientation(LinearLayout.HORIZONTAL);

	    LinearLayout layout3 = new LinearLayout(this);
	    layout3.setLayoutParams(new LinearLayout.LayoutParams(
		    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	    layout3.setOrientation(LinearLayout.HORIZONTAL);

	    container1.addView(layout1);
	    container2.addView(layout2);
	    container3.addView(layout3);

	    final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
		    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	    params.setMargins(0, lineMargins, 0, 0);

	    /** 一行剩下的空间 **/
	    int remainWidth = containerWidth;

	    if (tag1.length != 0) {
		for (int i = 0; i < tag1.length; ++i) {
		    final String text = tag1[i];
		    final float itemWidth = paint.measureText(text)
			    + itemPadding;
		    if (remainWidth > itemWidth) {
			addItemView(inflater, layout1, tvParams, text, i);
		    } else {
			resetTextViewMarginsRight(layout1);
			layout1 = new LinearLayout(this);
			layout1.setLayoutParams(params);
			layout1.setOrientation(LinearLayout.HORIZONTAL);

			/** 将前面那一个textview加入新的一行 */
			addItemView(inflater, layout1, tvParams, text, i);
			container1.addView(layout1);
			remainWidth = containerWidth;
		    }
		    remainWidth = (int) (remainWidth - itemWidth + 0.5f)
			    - itemMargins;
		}
		resetTextViewMarginsRight(layout1);
	    }

	    if (tag2.length != 0) {
		for (int i = 0; i < tag2.length; ++i) {
		    final String text = tag2[i];
		    final float itemWidth = paint.measureText(text)
			    + itemPadding;
		    if (remainWidth > itemWidth) {
			addItemView(inflater, layout2, tvParams, text, i);
		    } else {
			resetTextViewMarginsRight(layout2);
			layout2 = new LinearLayout(this);
			layout2.setLayoutParams(params);
			layout2.setOrientation(LinearLayout.HORIZONTAL);

			/** 将前面那一个textview加入新的一行 */
			addItemView(inflater, layout2, tvParams, text, i);
			container2.addView(layout2);
			remainWidth = containerWidth;
		    }
		    remainWidth = (int) (remainWidth - itemWidth + 0.5f)
			    - itemMargins;
		}
		resetTextViewMarginsRight(layout2);
	    }

	    if (tag3.length != 0) {
		for (int i = 0; i < tag3.length; ++i) {
		    final String text = tag3[i];
		    final float itemWidth = paint.measureText(text)
			    + itemPadding;
		    if (remainWidth > itemWidth) {
			addItemView(inflater, layout3, tvParams, text, i);
		    } else {
			resetTextViewMarginsRight(layout3);
			layout3 = new LinearLayout(this);
			layout3.setLayoutParams(params);
			layout3.setOrientation(LinearLayout.HORIZONTAL);

			/** 将前面那一个textview加入新的一行 */
			addItemView(inflater, layout3, tvParams, text, i);
			container3.addView(layout3);
			remainWidth = containerWidth;
		    }
		    remainWidth = (int) (remainWidth - itemWidth + 0.5f)
			    - itemMargins;
		}
		resetTextViewMarginsRight(layout3);
	    }

	}
    }

    /**
     * 将每行最后一个textview的MarginsRight去掉
     * */
    private void resetTextViewMarginsRight(ViewGroup viewGroup) {
	final TextView tempTextView = (TextView) viewGroup.getChildAt(viewGroup
		.getChildCount() - 1);
	tempTextView.setLayoutParams(new LinearLayout.LayoutParams(
		LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    }

    /**
     * 将view添加到viewgroup中
     * */
    private void addItemView(LayoutInflater inflater, ViewGroup viewGroup,
	    LayoutParams params, String text, int tag) {
	containerTv = (TextView) inflater.inflate(R.layout.container_textview,
		null);
	containerTv.setTag(tag);
	containerTv.setText(text);
	containerTv.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		TextView tv = (TextView) v;
		// Toast.makeText(PersonalInfo.this, tv.getText().toString(),
		// Toast.LENGTH_LONG).show();
		// Intent intent1 = new Intent(MainActivity.this,
		// SearchActivity.class);
		// intent1.putExtra("tags", tags);
		// startActivity(intent1);
	    }
	});
	viewGroup.addView(containerTv, params);
    }

    private void prepareView() {
	back = (ImageView) findViewById(R.id.title_back);
	pic = (ImageView) findViewById(R.id.personal_info_pic);
	name = (EditText) findViewById(R.id.personal_info_name_et);
	phone = (EditText) findViewById(R.id.personal_info_phone_et);
	sex = (TextView) findViewById(R.id.personal_info_sex);
	year = (Spinner) findViewById(R.id.personal_info_year);
	intro = (EditText) findViewById(R.id.personal_info_intro);
	add = (TextView) findViewById(R.id.personal_info_add);
	container1 = (ViewGroup) findViewById(R.id.personal_info_container1);
	container2 = (ViewGroup) findViewById(R.id.personal_info_container2);
	container3 = (ViewGroup) findViewById(R.id.personal_info_container3);
	submit = (TextView) findViewById(R.id.personal_info_submit);

	back.setOnClickListener(this);
	sex.setOnClickListener(this);
	add.setOnClickListener(this);
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
	case R.id.personal_info_sex:
	    sexDialog();
	    break;
	case R.id.personal_info_add:
	    startActivity(new Intent(this, BrandAreaSelect.class));
	    break;
	case R.id.personal_info_submit:
	    if (validatePhone()) {
		new AsyncTask<Void, Void, String>() {
		    @Override
		    protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = CustomProgressDialog.createDialog(
				PersonalInfo.this).setMessage("正在提交...");
			pd.show();
		    }

		    @Override
		    protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String url = Config.UPDATE_INFO_URL + getInfo();
			Log.e("url", url);

			String data = new Tools().getURL(url);
			System.out.println(data);

			String code = "";
			try {
			    JSONObject job = new JSONObject(data);
			    code = job.getString("code");
			} catch (JSONException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}

			return code;
		    }

		    @Override
		    protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();
			if (result.equals("1")) {
			    Toast.makeText(PersonalInfo.this, "修改个人信息成功！",
				    Toast.LENGTH_SHORT).show();
			} else {
			    Toast.makeText(PersonalInfo.this, "修改信息失败，请重试！",
				    Toast.LENGTH_SHORT).show();
			}
		    }

		}.execute();
	    }
	    break;
	default:
	    break;
	}
    }

    private void sexDialog() {
	final String[] sex = { "男", "女" };
	AlertDialog builder = new AlertDialog.Builder(this)
		.setSingleChoiceItems(sex, 0,
			new DialogInterface.OnClickListener() {

			    @Override
			    public void onClick(DialogInterface dialog,
				    int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				Log.e("which", sex[which]);
				_sex = sex[which];
			    }
			}).show();
	this.sex.setText(_sex);
    }

    private Boolean validatePhone() {
	if (phone.getText().toString().length() == 0) {
	    Toast.makeText(this, "请填写电话号码！", Toast.LENGTH_SHORT).show();
	    return false;
	} else if (phone.getText().toString().length() != 11) {
	    Toast.makeText(this, "请填写正确的11位电话号码！", Toast.LENGTH_SHORT).show();
	    return false;
	} else {
	    return true;
	}
    }

    private String getInfo() {
	String name_utf8 = "", intro_utf8 = "";
	try {
	    name_utf8 = URLEncoder.encode(name.getText().toString(), "utf-8");
	    intro_utf8 = URLEncoder.encode(intro.getText().toString(), "utf-8");
	} catch (UnsupportedEncodingException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}

	StringBuilder sb = new StringBuilder();
	// sb.append("act=zxs_xinxixiugai");
	// sb.append("&zid=");
	sb.append(new Tools().getUserId(this));
	sb.append("&xm=");
	sb.append(name_utf8);

	sb.append("&dh=");
	sb.append(phone.getText().toString());
	sb.append("&xb=");
	if (sex.getText().toString().equals("男")) {
	    sb.append("1");
	} else {
	    sb.append("2");
	}
	sb.append("&cynx=");
	sb.append(yrid);
	sb.append("&grjj=");
	sb.append(intro_utf8);

	System.out.println(sb.toString());
	return sb.toString();
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
