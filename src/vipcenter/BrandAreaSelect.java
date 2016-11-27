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

import adapter.BrandAreaListAdapter;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class BrandAreaSelect extends Activity implements OnClickListener {

    private ImageView back;
    private ListView bListView1;
    private ListView bListView2;
    private TextView submit;

    private CustomProgressDialog pd;
    private ArrayList<HashMap<String, String>> list1, list2;
    private ArrayList<String> id;

    private BrandAreaListAdapter adapter1, adapter2;

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.brand_area_select_main);
	Exit.getInstance().addActivity(this);
	prepareView();
	id = new ArrayList<String>();
	new AsyncTask<Void, Void, Void>() {

	    @Override
	    protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pd = CustomProgressDialog.createDialog(BrandAreaSelect.this);
		pd.show();
	    }

	    @Override
	    protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		String url = Config.BRAND_LIST_URL
			+ new Tools().getUserId(BrandAreaSelect.this);
		Log.e("url", url);
		String data = new Tools().getURL(url);
		System.out.println(data);
		list1 = new ArrayList<HashMap<String, String>>();
		list2 = new ArrayList<HashMap<String, String>>();
		try {
		    JSONObject jObject = new JSONObject(data);
		    JSONObject result = jObject.getJSONObject("result");
		    String code = result.getString("code");
		    if (code.equals("1")) {
			JSONArray jArray = jObject.getJSONArray("data");
			for (int i = 0, j = jArray.length(); i < j; i++) {
			    JSONObject job1 = jArray.optJSONObject(i);
			    HashMap<String, String> hashMap = new HashMap<String, String>();
			    hashMap.put("id", job1.getString("id"));
			    hashMap.put("classname",
				    job1.getString("classname"));
			    hashMap.put("iv_status", "1");

			    if (i < jArray.length() / 2) {
				list1.add(hashMap);
			    } else {
				list2.add(hashMap);
			    }
			}
			Message msg1 = handler.obtainMessage();
			msg1.what = 1;
			handler.sendMessage(msg1);

			Message msg2 = handler.obtainMessage();
			msg2.what = 2;
			handler.sendMessage(msg2);
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
		adapter1 = new BrandAreaListAdapter(list1, BrandAreaSelect.this);
		bListView1.setAdapter(adapter1);
		bListView1.setOnItemClickListener(new OnItemClickListener() {

		    @Override
		    public void onItemClick(AdapterView<?> arg0, View arg1,
			    int arg2, long arg3) {
			// TODO Auto-generated method stub
			if (id.size() == 0) {
			    id.add(list1.get(arg2).get("id"));
			    list1.get(arg2).put("iv_status", "2");
			    adapter1.notifyDataSetChanged();
			} else {
			    if (id.contains(list1.get(arg2).get("id"))) {
				list1.get(arg2).put("iv_status", "1");
				id.remove(list1.get(arg2).get("id"));
				adapter1.notifyDataSetChanged();
			    } else {
				id.add(list1.get(arg2).get("id"));
				list1.get(arg2).put("iv_status", "2");
				adapter1.notifyDataSetChanged();
			    }
			}
		    }
		});
		break;
	    case 2:
		adapter2 = new BrandAreaListAdapter(list2, BrandAreaSelect.this);
		bListView2.setAdapter(adapter2);
		bListView2.setOnItemClickListener(new OnItemClickListener() {

		    @Override
		    public void onItemClick(AdapterView<?> arg0, View arg1,
			    int arg2, long arg3) {
			// TODO Auto-generated method stub
			if (id.size() == 0) {
			    id.add(list2.get(arg2).get("id"));
			    list2.get(arg2).put("iv_status", "2");
			    adapter2.notifyDataSetChanged();
			} else {
			    if (id.contains(list2.get(arg2).get("id"))) {
				list2.get(arg2).put("iv_status", "1");
				id.remove(list2.get(arg2).get("id"));
				adapter2.notifyDataSetChanged();
			    } else {
				id.add(list2.get(arg2).get("id"));
				list2.get(arg2).put("iv_status", "2");
				adapter2.notifyDataSetChanged();
			    }
			}
		    }
		});
		break;
	    default:
		break;
	    }
	}

    };

    private void prepareView() {
	back = (ImageView) findViewById(R.id.title_back);
	bListView1 = (ListView) findViewById(R.id.brand_area_select_lv1);
	bListView2 = (ListView) findViewById(R.id.brand_area_select_lv2);
	submit = (TextView) findViewById(R.id.brand_area_select_submit);

	back.setOnClickListener(this);
	submit.setOnClickListener(this);
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
	case R.id.brand_area_select_submit:
	    String ids = "";
	    for (int i = 0, j = id.size() - 1; i < j; i++) {
		System.out.println("id --> " + id.get(i));
		ids += id.get(i) + ",";
	    }
	    ids += id.get(id.size() - 1);
	    System.out.println(ids);
	    new AsyncTask<String, Void, String>() {

		@Override
		protected void onPreExecute() {
		    // TODO Auto-generated method stub
		    super.onPreExecute();
		    pd = CustomProgressDialog
			    .createDialog(BrandAreaSelect.this);
		    pd.show();
		}

		@Override
		protected String doInBackground(String... params) {
		    // TODO Auto-generated method stub
		    String url = Config.BRAND_POST_URL
			    + new Tools().getUserId(BrandAreaSelect.this)
			    + "&id1=" + params[0];
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

		@Override
		protected void onPostExecute(String result) {
		    // TODO Auto-generated method stub
		    super.onPostExecute(result);
		    pd.dismiss();
		    if(result.equals("1")){
			Toast.makeText(BrandAreaSelect.this, "提交成功！", Toast.LENGTH_SHORT).show();
			finish();
		    }else{
			Toast.makeText(BrandAreaSelect.this, "提交失败，请重试！", Toast.LENGTH_SHORT).show();
		    }
		}
	    }.execute(ids);
	    break;
	default:
	    break;
	}
    }
}
