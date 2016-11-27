/**
 * 
 */
package account;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import tools.Config;
import tools.CustomProgressDialog;
import tools.Exit;
import tools.Tools;

import com.mkcomingd.R;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class AccountActivity extends Activity implements OnClickListener {

    private TextView balance;
    private TextView bank;
    private TextView name;
    private TextView cardNo;
    private FrameLayout income;
    private FrameLayout salary;
    
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
	setContentView(R.layout.account_info_main);
	Exit.getInstance().addActivity(this);
	prepareView();
	
	new AsyncTask<Void, Void, Void>() {

	    @Override
	    protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pd = CustomProgressDialog.createDialog(AccountActivity.this);
		pd.show();
	    }

	    @Override
	    protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		String url = Config.ACCOUNT_MAIN_URL + new Tools().getUserId(AccountActivity.this);
		Log.e("url", url);
		String data = new Tools().getURL(url);
		System.out.println(data);
		
		try {
		    JSONObject jObject = new JSONObject(data);
		    JSONObject result = jObject.getJSONObject("result");
		    String code = result.getString("code");
		    if(code.equals("1")){
			JSONObject job = jObject.getJSONObject("data");
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("ye", job.getString("ye"));
			hashMap.put("zfb", job.getString("zfb"));
			hashMap.put("zfbxm", job.getString("zfbxm"));
			
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

    private Handler handler = new Handler(){

	@Override
	public void handleMessage(Message msg) {
	    // TODO Auto-generated method stub
	    super.handleMessage(msg);
	    HashMap<String, String> hashMap = (HashMap<String, String>) msg.obj;
	    bank.setText(hashMap.get("zfb"));
	    name.setText(hashMap.get("zfbxm"));
	    balance.setText("гд"+hashMap.get("ye"));
	}
	
    };
    private void prepareView() {
	balance = (TextView) findViewById(R.id.account_info_balance);
	bank = (TextView) findViewById(R.id.account_info_bank);
	name = (TextView) findViewById(R.id.account_info_name);
	cardNo = (TextView) findViewById(R.id.account_info_card_no);
	income = (FrameLayout) findViewById(R.id.account_info_income);
	salary = (FrameLayout) findViewById(R.id.account_info_salary);

	income.setOnClickListener(this);
	salary.setOnClickListener(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v) {
	// TODO Auto-generated method stub
	Intent intent = null;
	switch (v.getId()) {
	case R.id.account_info_income:
	    intent = new Intent(this, InComeActivity.class);
	    break;
	case R.id.account_info_salary:
	    intent = new Intent(this, SalaryActivity.class);
	    break;
	default:
	    break;
	}
	startActivity(intent);
    }

}
