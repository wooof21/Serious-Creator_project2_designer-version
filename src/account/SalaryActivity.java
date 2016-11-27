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

import com.igexin.getuiext.a.l;
import com.mkcomingd.R;

import adapter.SalaryListAdapter;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
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
public class SalaryActivity extends Activity {

    private ImageView back;
    private ListView lv;
    private TextView total;

    private ArrayList<HashMap<String, String>> list;
    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.salary_detail);
	Exit.getInstance().addActivity(this);
	prepareView();

	new AsyncTask<Void, Void, String>() {
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
		pd = CustomProgressDialog.createDialog(SalaryActivity.this);
		pd.show();
		list = new ArrayList<HashMap<String,String>>();
	    }

	    @Override
	    protected String doInBackground(Void... params) {
		// TODO Auto-generated method stub
		String url = Config.SALARY_DETAIL_URL
			+ new Tools().getUserId(SalaryActivity.this);
		Log.e("url", url);
		String data = new Tools().getURL(url);
		System.out.println(data);
		String code = "";
		try {
		    JSONObject job = new JSONObject(data);
		    JSONObject result = job.getJSONObject("result");
		    code = result.getString("code");
		    if(code.equals("1")){
			JSONObject _data = job.getJSONObject("data");
			String total = _data.getString("zye");
			
			Message msg = handler.obtainMessage();
			msg.obj = total;
			msg.sendToTarget();
			
			JSONArray jArray = _data.getJSONArray("xzmxlist");
			for(int i=0,j=jArray.length();i<j;i++){
			    JSONObject mx = jArray.optJSONObject(i);
			    HashMap<String, String> hashMap = new HashMap<String, String>();
			    hashMap.put("sj", mx.getString("sj"));
			    hashMap.put("je", mx.getString("je"));
			    hashMap.put("txfs", mx.getString("txfs"));
			    
			    list.add(hashMap);
			    
			}
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
		if(result.equals("1")){
		    SalaryListAdapter adapter = new SalaryListAdapter(list, SalaryActivity.this);
		    lv.setAdapter(adapter);
		}else{
		    Toast.makeText(SalaryActivity.this, "未收到工资！", Toast.LENGTH_SHORT).show();
		    //SalaryActivity.this.finish();
		}
	    }
	}.execute();
    }
    
    private Handler handler = new Handler(){

	@Override
	public void handleMessage(Message msg) {
	    // TODO Auto-generated method stub
	    super.handleMessage(msg);
	    String total = (String) msg.obj;
	    SalaryActivity.this.total.setText(total);
	}
	
    };

    private void prepareView() {
	back = (ImageView) findViewById(R.id.back);
	lv = (ListView) findViewById(R.id.listView1);
	total = (TextView) findViewById(R.id.salary_detail_total);

	back.setOnClickListener(new OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		finish();
	    }
	});

    }
}
