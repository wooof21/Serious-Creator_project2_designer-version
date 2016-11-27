/**
 * 
 */
package com.mkcoming;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import reserve.StartServerPopUpWindow;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class GrabSubmitPopUp extends Activity implements OnClickListener {

    private TextView submit;
    private TextView remark;
    private TextView address;
    private TextView dt;
    private TextView phone;
    private TextView price;
    private TextView type;
    private ImageView back;

    private String id;
    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.qiangdan_confirm_dialog_view);
	Exit.getInstance().addActivity(this);
	prepareView();
	
	
	HashMap<String, String> hashMap = (HashMap<String, String>) getIntent()
		.getExtras().get("map");
	type.setText(hashMap.get("fgid") + "-" + hashMap.get("zrid"));
	price.setText("£§" + hashMap.get("je"));
	phone.setText(hashMap.get("dh"));
	dt.setText(hashMap.get("yysj"));
	address.setText(hashMap.get("dz"));
	remark.setText(hashMap.get("bz"));
	
	id = hashMap.get("id");
	
    }

    private void prepareView() {
	back = (ImageView) findViewById(R.id.qiangdan_confirm_dialog_back);
	type = (TextView) findViewById(R.id.qiangdan_confirm_dialog_type);
	price = (TextView) findViewById(R.id.qiangdan_confirm_dialog_price);
	phone = (TextView) findViewById(R.id.qiangdan_confirm_dialog_phone);
	dt = (TextView) findViewById(R.id.qiangdan_confirm_dialog_dt);
	address = (TextView) findViewById(R.id.qiangdan_confirm_dialog_address);
	remark = (TextView) findViewById(R.id.qiangdan_confirm_dialog_remark);
	submit = (TextView) findViewById(R.id.qiangdan_confirm_dialog_submit);

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
	case R.id.qiangdan_confirm_dialog_back:
	    setResult(404);
	    finish();
	    break;
	case R.id.qiangdan_confirm_dialog_submit:
	    new GrabAsync().execute(id);
	    break;
	default:
	    break;
	}
    }

    class GrabAsync extends AsyncTask<String, Void, String> {

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
	    pd = CustomProgressDialog.createDialog(GrabSubmitPopUp.this);
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
	    String url = Config.ORDER_GRAB_URL
		    + new Tools().getUserId(GrabSubmitPopUp.this) + "&oid="
		    + params[0];
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
	    Message msg = handler.obtainMessage();
	    if(result.equals("1")){
		msg.what = 1;
	    }else{
		msg.what = 2;
	    }
	    msg.sendToTarget();
	}
    }
    
    private Handler handler = new Handler(){

	@Override
	public void handleMessage(Message msg) {
	    // TODO Auto-generated method stub
	    super.handleMessage(msg);
	    switch (msg.what) {
	    case 1:
		Intent intent = new Intent(GrabSubmitPopUp.this, StartServerPopUpWindow.class);
		intent.putExtra("type", "grab_order");
		startActivity(intent);
		finish();
		break;
	    case 2:
		Toast.makeText(GrabSubmitPopUp.this, "«¿µ• ß∞‹£¨«Î÷ÿ ‘£°", Toast.LENGTH_SHORT).show();
		GrabSubmitPopUp.this.finish();
		break;
	    default:
		break;
	    }
	}
	
    };
}
