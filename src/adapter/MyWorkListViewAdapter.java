/**
 * 
 */
package adapter;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import tools.Config;
import tools.CustomProgressDialog;
import tools.ScrollListView;
import tools.Tools;
import view.MGridView;
import vipcenter.MyHonor;
import vipcenter.MyWorkAddWork;

import com.mkcomingd.R;

import model.MyWorkListModel;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class MyWorkListViewAdapter extends BaseAdapter {

    private ArrayList<MyWorkListModel> list;
    private Context context;
    private LayoutInflater lInflater;
    private MyWorkGridViewAdapter adapter;
    private CustomProgressDialog pd;
    private HashMap<String, String> picId;
    private Handler handler;

    public MyWorkListViewAdapter(ArrayList<MyWorkListModel> list,
	    Context context, HashMap<String, String> picId, Handler handler) {
	super();
	this.list = list;
	this.context = context;
	this.picId = picId;
	this.lInflater = LayoutInflater.from(context);
	this.handler = handler;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public int getCount() {
	// TODO Auto-generated method stub
	return list.size();
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public Object getItem(int position) {
	// TODO Auto-generated method stub
	return list.get(position);
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
	    convertView = lInflater.inflate(R.layout.my_work_lv_item, null);
	}

	vHolder.title = (TextView) convertView
		.findViewById(R.id.my_work_lv_item_name);
	vHolder.gv = (MGridView) convertView
		.findViewById(R.id.my_work_lv_item_gv);
	final MyWorkListModel model = list.get(position);
	vHolder.title.setText(model.getTitle());

	System.out.println("size " + model.getPicAddr().size());
	adapter = new MyWorkGridViewAdapter(model.getPicAddr(), context);
	vHolder.gv.setAdapter(adapter);
	vHolder.gv.setOnItemLongClickListener(new OnItemLongClickListener() {

	    @Override
	    public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
		    final int arg2, long arg3) {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(context)
			.setTitle("提示")
			.setMessage("确定删除作品？ ")
			.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {

				    @Override
				    public void onClick(DialogInterface dialog,
					    int which) {
					// TODO Auto-generated method
					// stub
					dialog.dismiss();
					new AsyncTask<String, Void, String>() {

					    @Override
					    protected void onPreExecute() {
						// TODO Auto-generated method
						// stub
						super.onPreExecute();
						pd = CustomProgressDialog
							.createDialog(context);
						pd.show();
					    }

					    @Override
					    protected String doInBackground(
						    String... params) {
						// TODO Auto-generated method
						// stub
						String url = Config.MY_WORK_DELETE_URL
							+ new Tools()
								.getUserId(context)
							+ "&id1="
							+ picId.get(params[0]);
						Log.e("url", url);
						String data = new Tools()
							.getURL(url);
						System.out.println(data);
						String code = "";
						try {
						    JSONObject job = new JSONObject(
							    data);
						    JSONObject result = job
							    .getJSONObject("result");
						    code = result
							    .getString("code");
						} catch (JSONException e) {
						    // TODO Auto-generated catch
						    // block
						    e.printStackTrace();
						}
						return code;
					    }

					    @Override
					    protected void onPostExecute(
						    String result) {
						// TODO Auto-generated method
						// stub
						super.onPostExecute(result);
						pd.dismiss();
						if (result.equals("1")) {
						    Toast.makeText(context,
							    "删除成功！",
							    Toast.LENGTH_SHORT)
							    .show();
						    Message msg = handler.obtainMessage();
						    handler.sendMessage(msg);
						}else{

						    Toast.makeText(context,
							    "删除失败，请重试！",
							    Toast.LENGTH_SHORT)
							    .show();
						}
					    }

					}.execute(model.getPicAddr().get(
						arg2));

				    }
				})
			.setNegativeButton("取消",
				new DialogInterface.OnClickListener() {

				    @Override
				    public void onClick(DialogInterface dialog,
					    int which) {
					// TODO Auto-generated method
					// stub
					dialog.dismiss();
				    }
				}).show();
		return true;
	    }
	});
	return convertView;
    }

    class ViewHolder {
	TextView title;
	MGridView gv;
    }
}
