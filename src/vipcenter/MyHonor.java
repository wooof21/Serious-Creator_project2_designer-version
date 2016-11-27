/**
 * 
 */
package vipcenter;

import java.io.IOException;
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

import adapter.MyHonorListAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class MyHonor extends Activity implements OnClickListener {

    private ImageView back;
    private TextView add;
    private ListView lv;

    private ArrayList<HashMap<String, String>> list;
    private CustomProgressDialog pd;
    private MyHonorListAdapter adapter;

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.vip_center_my_honor);
	Exit.getInstance().addActivity(this);
	prepareView();
	list = new ArrayList<HashMap<String, String>>();

	new AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>() {

	    @Override
	    protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pd = CustomProgressDialog.createDialog(MyHonor.this);
		pd.show();
	    }

	    @Override
	    protected ArrayList<HashMap<String, String>> doInBackground(
		    Void... params) {
		// TODO Auto-generated method stub
		String url = Config.MY_HONOR_URL
			+ new Tools().getUserId(MyHonor.this);
		Log.e("url", url);
		String data = new Tools().getURL(url);
		System.out.println(data);

		try {
		    JSONObject job = new JSONObject(data);
		    JSONObject result = job.getJSONObject("result");
		    String code = result.getString("code");
		    if (code.equals("1")) {
			JSONArray jArray = job.getJSONArray("data");
			for (int i = 0, j = jArray.length(); i < j; i++) {
			    JSONObject jObject = jArray.optJSONObject(i);
			    HashMap<String, String> hashMap = new HashMap<String, String>();
			    hashMap.put("id", jObject.getString("id"));
			    hashMap.put("pic", jObject.getString("pic"));
			    hashMap.put("hdsj", jObject.getString("hdsj"));
			    hashMap.put("rymc", jObject.getString("rymc"));

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
		adapter = new MyHonorListAdapter(result, MyHonor.this);
		lv.setAdapter(adapter);

		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

		    @Override
		    public boolean onItemLongClick(AdapterView<?> arg0,
			    View arg1, final int arg2, long arg3) {
			// TODO Auto-generated method stub

			new AlertDialog.Builder(MyHonor.this)
				.setTitle("提示")
				.setMessage(
					"确定删除荣誉 " + list.get(arg2).get("rymc")
						+ " ?")
				.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

					    @Override
					    public void onClick(
						    DialogInterface dialog,
						    int which) {
						// TODO Auto-generated method
						// stub
						dialog.dismiss();

						new AsyncTask<Void, Void, String>() {

						    @Override
						    protected void onPreExecute() {
							// TODO Auto-generated
							// method stub
							super.onPreExecute();
							pd = CustomProgressDialog
								.createDialog(MyHonor.this);
							pd.show();
						    }

						    @Override
						    protected String doInBackground(
							    Void... params) {
							// TODO Auto-generated
							// method stub
							String url = Config.MY_HONOR_DELETE_URL
								+ new Tools()
									.getUserId(MyHonor.this)
								+ "&id1="
								+ list.get(arg2)
									.get("id");
							Log.e("url", url);

							String data = new Tools()
								.getURL(url);
							System.out
								.println(data);
							String code = "";
							try {
							    JSONObject job = new JSONObject(
								    data);
							    JSONObject result = job
								    .getJSONObject("result");
							    code = result
								    .getString("code");
							    if (code.equals("1")) {
								list.remove(arg2);
								adapter.notifyDataSetChanged();
							    }
							} catch (JSONException e) {
							    // TODO
							    // Auto-generated
							    // catch block
							    e.printStackTrace();
							}
							return code;
						    }

						    @Override
						    protected void onPostExecute(
							    String result) {
							// TODO Auto-generated
							// method stub
							super.onPostExecute(result);
							pd.dismiss();
							if (result.equals("1")) {
							    Toast.makeText(
								    MyHonor.this,
								    "删除荣誉成功！",
								    Toast.LENGTH_SHORT)
								    .show();
							} else {
							    Toast.makeText(
								    MyHonor.this,
								    "删除荣誉失败，请重试！",
								    Toast.LENGTH_SHORT)
								    .show();
							}
						    }

						}.execute();
					    }
					})
				.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {

					    @Override
					    public void onClick(
						    DialogInterface dialog,
						    int which) {
						// TODO Auto-generated method
						// stub
						dialog.dismiss();
					    }
					}).show();
			return true;
		    }
		});
	    }

	}.execute();
    }

    private void prepareView() {
	back = (ImageView) findViewById(R.id.title_back);
	add = (TextView) findViewById(R.id.my_honor_add);
	lv = (ListView) findViewById(R.id.my_honor_lv);

	back.setOnClickListener(this);
	add.setOnClickListener(this);
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
	case R.id.my_honor_add:
	    startActivity(new Intent(MyHonor.this, MyHonorAddHonor.class));
	    break;
	default:
	    break;
	}
    }


}
