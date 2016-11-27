/**
 * 
 */
package adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.mkcomingd.R;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class BrandAreaListAdapter extends BaseAdapter {

    private ArrayList<HashMap<String, String>> list;
    private ArrayList<String> id;
    private Context context;
    private Handler handler;
    private LayoutInflater lInflater;

    public BrandAreaListAdapter(ArrayList<HashMap<String, String>> list,
	    Context context) {
	super();
	this.list = list;
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
	    convertView = lInflater.inflate(R.layout.brand_select_item, null);
	}
	vHolder.iv = (ImageView) convertView.findViewById(R.id.brand_select_iv);
	vHolder.tv = (TextView) convertView.findViewById(R.id.brand_select_tv);
	if(list.get(position).get("iv_status").equals("1")){
	    vHolder.iv.setImageResource(R.drawable.select_unselect);
	}else{
	    vHolder.iv.setImageResource(R.drawable.select_black);
	}
	vHolder.tv.setText(list.get(position).get("classname").toString());
	return convertView;
    }

    class ViewHolder {
	ImageView iv;
	TextView tv;
    }
}
