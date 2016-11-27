/**
 * 
 */
package vipcenter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import model.MyWorkListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tools.Config;
import tools.CustomProgressDialog;
import tools.Exit;
import tools.ScrollListView;
import tools.Tools;
import upload.Bmp;
import upload.PicBucketListActivity;

import com.mkcoming.MainActivity;
import com.mkcomingd.R;

import adapter.MyWorkListViewAdapter;
import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class MyWork extends Activity implements OnClickListener {

    private ImageView back;
    private TextView upload;
    private ListView lv;

    private CustomProgressDialog pd;

    private HashMap<String, String> picId;

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.vip_center_my_work_main);
	Exit.getInstance().addActivity(this);
	prepareView();

	new ListAsync().execute();
    }

    class ListAsync extends AsyncTask<Void, Void, ArrayList<MyWorkListModel>> {

	@Override
	protected void onPreExecute() {
	    // TODO Auto-generated method stub
	    super.onPreExecute();
	    pd = CustomProgressDialog.createDialog(MyWork.this);
	    pd.show();
	}

	@Override
	protected ArrayList<MyWorkListModel> doInBackground(Void... params) {
	    // TODO Auto-generated method stub
	    String url = Config.MY_WORK_URL
		    + new Tools().getUserId(MyWork.this);
	    Log.e("url", url);

	    String data = new Tools().getURL(url);
	    System.out.println(data);

	    ArrayList<MyWorkListModel> list = new ArrayList<MyWorkListModel>();

	    try {
		JSONObject job = new JSONObject(data);
		JSONObject jsonObject = job.getJSONObject("result");
		String code = jsonObject.getString("code");
		picId = new HashMap<String, String>();
		if (code.equals("1")) {
		    JSONArray jArray = job.getJSONArray("data");
		    for (int i = 0, j = jArray.length(); i < j; i++) {
			JSONObject job1 = jArray.optJSONObject(i);
			MyWorkListModel model = new MyWorkListModel();
			model.setTitle(job1.getString("classname"));
			JSONArray jArray2 = job1.getJSONArray("piclist");
			ArrayList<String> picList = new ArrayList<String>();
			for (int m = 0, n = jArray2.length(); m < n; m++) {
			    JSONObject job3 = jArray2.optJSONObject(m);
			    picList.add(job3.getString("pic"));
			    picId.put(job3.getString("pic"),
				    job3.getString("id"));
			}
			model.setPicAddr(picList);

			list.add(model);
		    }

		}
	    } catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    return list;
	}

	@Override
	protected void onPostExecute(ArrayList<MyWorkListModel> result) {
	    // TODO Auto-generated method stub
	    super.onPostExecute(result);
	    pd.dismiss();
	    MyWorkListViewAdapter adapter = new MyWorkListViewAdapter(result,
		    MyWork.this, picId, handler);
	    lv.setAdapter(adapter);
	    ScrollListView.setListViewHeightBasedOnChildren(lv);
	}
    }

    private Handler handler = new Handler() {

	@Override
	public void handleMessage(Message msg) {
	    // TODO Auto-generated method stub
	    super.handleMessage(msg);
	    new ListAsync().execute();
	}

    };

    private void prepareView() {
	back = (ImageView) findViewById(R.id.title_back);
	upload = (TextView) findViewById(R.id.my_work_upload);
	lv = (ListView) findViewById(R.id.vip_center_my_work_lv);

	back.setOnClickListener(this);
	upload.setOnClickListener(this);
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
	case R.id.my_work_upload:
	    startActivity(new Intent(this, MyWorkAddWork.class));
	    break;
	default:
	    break;
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onBackPressed()
     */
    @Override
    public void onBackPressed() {
	// TODO Auto-generated method stub
	super.onBackPressed();
	finish();
    }
}
