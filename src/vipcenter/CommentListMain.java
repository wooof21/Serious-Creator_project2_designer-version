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


import adapter.CommentListAdapter;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
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
public class CommentListMain extends Activity implements OnClickListener{

    private ImageView back;
    private LinearLayout allLL;
    private TextView all;
    private TextView underline1;
    private LinearLayout goodLL;
    private TextView good;
    private TextView underline2;
    private LinearLayout mediumLL;
    private TextView medium;
    private TextView underline3;
    private LinearLayout badLL;
    private TextView bad;
    private TextView underline4;
    private ListView lv;

    private ArrayList<HashMap<String, String>> aList, gList, mList, bList;
    protected CommentListAdapter adapter;
    
    
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_list_main);
	Exit.getInstance().addActivity(this);
        prepareView();
        
        
	new AsyncTask<String, Void, ArrayList<HashMap<String, String>>>() {
	    private CustomProgressDialog pd;
	    /* (non-Javadoc)
	     * @see android.os.AsyncTask#onPreExecute()
	     */
	    @Override
	    protected void onPreExecute() {
	        // TODO Auto-generated method stub
	        super.onPreExecute();
	        pd = CustomProgressDialog.createDialog(CommentListMain.this);
	        pd.show();
	        aList = new ArrayList<HashMap<String,String>>();
	        gList = new ArrayList<HashMap<String,String>>();
	        mList = new ArrayList<HashMap<String,String>>();
	        bList = new ArrayList<HashMap<String,String>>();
	    }
	    @Override
	    protected ArrayList<HashMap<String, String>> doInBackground(
		    String... params) {
		// TODO Auto-generated method stub
		String url = Config.COMMENT_LIST_URL + params[0];
		Log.e("url", url);
		String data = new Tools().getURL(url);
		System.out.println(data);
		
		try {
		    JSONObject jObject = new JSONObject(data);
		    JSONObject result = jObject.getJSONObject("result");
		    String code = result.getString("code");
		    if(code.equals("1")){
			JSONObject _data = jObject.getJSONObject("data");
			
			
			
			JSONArray zp = _data.getJSONArray("zp");
			for(int i=0,j=zp.length();i<j;i++){
			    JSONObject job1 = zp.optJSONObject(i);
			    HashMap<String, String> hashMap1 = new HashMap<String, String>();
			    hashMap1.put("tx", job1.getString("tx"));
			    hashMap1.put("xm", job1.getString("xm"));
			    hashMap1.put("sj", job1.getString("sj"));
			    hashMap1.put("pic", job1.getString("pic"));
			    hashMap1.put("nr", job1.getString("nr"));
			    
			    mList.add(hashMap1);
			}
			aList.addAll(mList);
			
			JSONArray cp = _data.getJSONArray("cp");
			for(int i=0,j=cp.length();i<j;i++){
			    JSONObject job2 = cp.optJSONObject(i);
			    HashMap<String, String> hashMap2 = new HashMap<String, String>();
			    hashMap2.put("tx", job2.getString("tx"));
			    hashMap2.put("xm", job2.getString("xm"));
			    hashMap2.put("sj", job2.getString("sj"));
			    hashMap2.put("pic", job2.getString("pic"));
			    hashMap2.put("nr", job2.getString("nr"));
			    
			    bList.add(hashMap2);
			}
			aList.addAll(bList);
			
			JSONArray hp = _data.getJSONArray("hp");
			for(int i=0,j=hp.length();i<j;i++){
			    JSONObject job3 = hp.optJSONObject(i);
			    HashMap<String, String> hashMap3 = new HashMap<String, String>();
			    hashMap3.put("tx", job3.getString("tx"));
			    hashMap3.put("xm", job3.getString("xm"));
			    hashMap3.put("sj", job3.getString("sj"));
			    hashMap3.put("pic", job3.getString("pic"));
			    hashMap3.put("nr", job3.getString("nr"));
			    
			    gList.add(hashMap3);
			}
			aList.addAll(gList);
			
			
			Message msg = handler.obtainMessage();
			msg.what = 1;
			msg.sendToTarget();
			
			
		    }else{
			Message msg404 = handler.obtainMessage();
			msg404.what = 404;
			msg404.sendToTarget();
		    }
		} catch (JSONException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		return aList;
	    }
	    /* (non-Javadoc)
	     * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	     */
	    @Override
	    protected void onPostExecute(
	            ArrayList<HashMap<String, String>> result) {
	        // TODO Auto-generated method stub
	        super.onPostExecute(result);
	        pd.dismiss();
	        if(result.size() == 0){
	            Toast.makeText(CommentListMain.this, "暂无评价！", Toast.LENGTH_SHORT).show();
	        }else{
	            adapter = new CommentListAdapter(result, CommentListMain.this);
	            lv.setAdapter(adapter);
	        }
	    }
	}.execute(new Tools().getUserId(CommentListMain.this));
    }
    
    
    private Handler handler = new Handler(){

	@Override
	public void handleMessage(Message msg) {
	    // TODO Auto-generated method stub
	    super.handleMessage(msg);
	    switch (msg.what) {
	    case 1:
		all.setText("(" + aList.size() + ")");
		good.setText("(" + gList.size() + ")");
		medium.setText("(" + mList.size() + ")");
		bad.setText("(" + bList.size() + ")");
		break;

	    case 404:
		Toast.makeText(CommentListMain.this, "获取评价列表失败，请重试！", Toast.LENGTH_SHORT).show();
		CommentListMain.this.finish();
		break;
	    default:
		break;
	    }
	}
	
    };
    
    
    
    
    private void prepareView() {
	back = (ImageView) findViewById(R.id.title_back);
	allLL = (LinearLayout) findViewById(R.id.comment_list_all_ll);
	all = (TextView) findViewById(R.id.comment_list_all);
	underline1 = (TextView) findViewById(R.id.comment_list_underline1);
	goodLL = (LinearLayout) findViewById(R.id.comment_list_good_ll);
	good = (TextView) findViewById(R.id.comment_list_good);
	underline2 = (TextView) findViewById(R.id.comment_list_underline2);
	mediumLL = (LinearLayout) findViewById(R.id.comment_list_medium_ll);
	medium = (TextView) findViewById(R.id.comment_list_medium);
	underline3 = (TextView) findViewById(R.id.comment_list_underline3);
	badLL = (LinearLayout) findViewById(R.id.comment_list_bad_ll);
	bad = (TextView) findViewById(R.id.comment_list_bad);
	underline4 = (TextView) findViewById(R.id.comment_list_underline4);
	lv = (ListView) findViewById(R.id.comment_list_lv);

	back.setOnClickListener(this);
	allLL.setOnClickListener(this);
	goodLL.setOnClickListener(this);
	mediumLL.setOnClickListener(this);
	badLL.setOnClickListener(this);
    }




    /* (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v) {
	// TODO Auto-generated method stub
	switch (v.getId()) {
	case R.id.title_back:
	    finish();
	    break;
	case R.id.comment_list_all_ll:
	    adapter = new CommentListAdapter(aList, CommentListMain.this);
            lv.setAdapter(adapter);
            setUnderLine(R.id.comment_list_all_ll);
	    break;
	case R.id.comment_list_good_ll:
	    adapter = new CommentListAdapter(gList, CommentListMain.this);
            lv.setAdapter(adapter);
            setUnderLine(R.id.comment_list_good_ll);
	    break;
	case R.id.comment_list_medium_ll:
	    adapter = new CommentListAdapter(mList, CommentListMain.this);
            lv.setAdapter(adapter);
            setUnderLine(R.id.comment_list_medium_ll);
	    break;
	case R.id.comment_list_bad_ll:
	    adapter = new CommentListAdapter(bList, CommentListMain.this);
            lv.setAdapter(adapter);
            setUnderLine(R.id.comment_list_bad_ll);
	    break;
	default:
	    break;
	}
    }
    
    private void setUnderLine(int id){
	switch (id) {
	case R.id.comment_list_all_ll:
	    underline1.setVisibility(View.VISIBLE);
	    underline2.setVisibility(View.INVISIBLE);
	    underline3.setVisibility(View.INVISIBLE);
	    underline4.setVisibility(View.INVISIBLE);
	    break;
	case R.id.comment_list_good_ll:
	    underline1.setVisibility(View.INVISIBLE);
	    underline2.setVisibility(View.VISIBLE);
	    underline3.setVisibility(View.INVISIBLE);
	    underline4.setVisibility(View.INVISIBLE);
	    break;
	case R.id.comment_list_medium_ll:
	    underline1.setVisibility(View.INVISIBLE);
	    underline2.setVisibility(View.INVISIBLE);
	    underline3.setVisibility(View.VISIBLE);
	    underline4.setVisibility(View.INVISIBLE);
	    break;
	case R.id.comment_list_bad_ll:
	    underline1.setVisibility(View.INVISIBLE);
	    underline2.setVisibility(View.INVISIBLE);
	    underline3.setVisibility(View.INVISIBLE);
	    underline4.setVisibility(View.VISIBLE);
	    break;
	default:
	    break;
	}
    }
}
