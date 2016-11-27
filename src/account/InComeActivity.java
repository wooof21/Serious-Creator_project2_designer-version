/**
 * 
 */
package account;

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

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class InComeActivity extends Activity {

    private ImageView back;
    private ListView lv;
    private TextView total;
    protected CustomProgressDialog pd;

    private int oldPostion = -1;
    private ArrayList<HashMap<String, String>> list;

    private InComeListAdapter adapter;

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.income_detail);
	Exit.getInstance().addActivity(this);
	prepareView();

	new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

	    @Override
	    protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pd = CustomProgressDialog.createDialog(InComeActivity.this);
		pd.show();
		list = new ArrayList<HashMap<String,String>>();
	    }

	    @Override
	    protected ArrayList<HashMap<String, String>> doInBackground(
		    Void... params) {
		// TODO Auto-generated method stub
		String url = Config.ACCOUNT_INCOME_URL
			+ new Tools().getUserId(InComeActivity.this);
		Log.e("url", url);
		String data = new Tools().getURL(url);
		System.out.println(data);

		try {
		    JSONObject jObject = new JSONObject(data);
		    JSONObject result = jObject.getJSONObject("result");
		    String code = result.getString("code");
		    if (code.equals("1")) {
			JSONArray jArray = jObject.getJSONArray("data");
			list = new ArrayList<HashMap<String, String>>();
			for (int i = 0, j = jArray.length(); i < j; i++) {
			    JSONObject job = jArray.optJSONObject(i);
			    HashMap<String, String> hashMap = new HashMap<String, String>();
			    hashMap.put("id", job.getString("id"));
			    hashMap.put("je", job.getString("je"));
			    hashMap.put("num", job.getString("num"));
			    hashMap.put("sj", job.getString("sj"));
			    hashMap.put("uid", job.getString("uid"));
			    hashMap.put("zrid", job.getString("zrid"));
			    hashMap.put("up_down", "up");
			    hashMap.put("expand", "false");

			    list.add(hashMap);
			}
		    }
		} catch (JSONException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		return list;
	    }

	    @Override
	    protected void onPostExecute(
		    ArrayList<HashMap<String, String>> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		pd.dismiss();
		if (list.size() != 0) {
		    adapter = new InComeListAdapter(result, InComeActivity.this);
		    lv.setAdapter(adapter);
		    lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
			    // TODO Auto-generated method stub
			    if (list.get(arg2).get("up_down").toString()
				    .equals("up")) {
				list.get(arg2).put("up_down", "down");
			    } else {
				list.get(arg2).put("up_down", "up");
			    }
			    if (list.get(arg2).get("expand").toString()
				    .equals("true")) {
				list.get(arg2).put("expand", "false");
			    } else {
				list.get(arg2).put("expand", "true");
			    }
			    // if (oldPostion == arg2) {
			    // if (_expand) {
			    // oldPostion = -1;
			    // }
			    // _expand = !_expand;
			    // } else {
			    // oldPostion = arg2;
			    // _expand = true;
			    // }

			    int totalHeight = 0;
			    for (int i = 0; i < adapter.getCount(); i++) {
				View viewItem = adapter.getView(i, null, lv);// 这个很重要，那个展开的item的measureHeight比其他的大
				viewItem.measure(0, 0);
				totalHeight += viewItem.getMeasuredHeight();
			    }

			    ViewGroup.LayoutParams params = lv
				    .getLayoutParams();
			    params.height = totalHeight
				    + (lv.getDividerHeight() * (lv.getCount() - 1));
			    lv.setLayoutParams(params);
			    adapter.notifyDataSetChanged();
			}

		    });
		}else{
		    Toast.makeText(InComeActivity.this, "无收入！", Toast.LENGTH_SHORT).show();
		    //finish();
		}

	    }
	}.execute();
    }

    class InComeListAdapter extends BaseAdapter {

	private ArrayList<HashMap<String, String>> listData;
	private Context context;
	private LayoutInflater lInflater;

	public InComeListAdapter(ArrayList<HashMap<String, String>> list,
		Context context) {
	    super();
	    this.listData = list;
	    this.context = context;
	    this.lInflater = LayoutInflater.from(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
	    // TODO Auto-generated method stub
	    return listData.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
	    // TODO Auto-generated method stub
	    return listData.get(position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
	    // TODO Auto-generated method stub
	    return position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    // TODO Auto-generated method stub
	    ViewHolder vHolder = new ViewHolder();
	    if (convertView == null) {
		convertView = lInflater.inflate(
			R.layout.income_detail_listview_item, null);
	    }
	    vHolder.time = (TextView) convertView
		    .findViewById(R.id.income_item_time);
	    vHolder.name = (TextView) convertView
		    .findViewById(R.id.income_item_name);
	    vHolder.amount = (TextView) convertView
		    .findViewById(R.id.income_item_amount);
	    vHolder.iv = (ImageView) convertView
		    .findViewById(R.id.income_item_iv);
	    vHolder.expand = (LinearLayout) convertView
		    .findViewById(R.id.item2);
	    vHolder.id = (TextView) convertView
		    .findViewById(R.id.income_item_order_id);
	    vHolder.type = (TextView) convertView
		    .findViewById(R.id.income_item_type);

	    vHolder.time.setText(listData.get(position).get("sj"));
	    vHolder.name.setText(listData.get(position).get("uid"));
	    vHolder.amount.setText("￥" + listData.get(position).get("je"));
	    vHolder.id.setText(listData.get(position).get("num"));
	    vHolder.type.setText(listData.get(position).get("zrid"));

	    if (listData.get(position).get("up_down").toString().equals("up")) {
		vHolder.iv.setImageResource(R.drawable.up_dark);
	    } else {
		vHolder.iv.setImageResource(R.drawable.down_dark);
	    }

	    if (listData.get(position).get("expand").equals("true")) {
		vHolder.expand.setVisibility(View.VISIBLE);
	    } else {
		vHolder.expand.setVisibility(View.GONE);
	    }
	    return convertView;
	}

	class ViewHolder {
	    TextView time;
	    TextView name;
	    TextView amount;
	    ImageView iv;
	    LinearLayout expand;
	    TextView id;
	    TextView type;
	}
    }

    private void prepareView() {
	back = (ImageView) findViewById(R.id.income_back);
	lv = (ListView) findViewById(R.id.income_lv);
	total = (TextView) findViewById(R.id.income_detail_total);

	back.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		finish();
	    }
	});
    }
}
