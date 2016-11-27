/**
 * 
 */
package adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.mkcomingd.R;

import android.content.Context;
import android.graphics.Color;
import android.provider.Contacts.ContactMethods;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class ScheduleGVAdapter extends BaseAdapter {

    private ArrayList<HashMap<String, String>> list;
    private Context context;
    private LayoutInflater lInflater;
    private String type;

    public ScheduleGVAdapter(ArrayList<HashMap<String, String>> list,
	    Context context, String type) {
	super();
	this.list = list;
	this.context = context;
	this.type = type;
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
	    convertView = lInflater.inflate(R.layout.my_time_gv_item, null);
	}
	vHolder.bg = (LinearLayout) convertView
		.findViewById(R.id.my_time_gv_item_bg);
	vHolder.time = (TextView) convertView
		.findViewById(R.id.my_time_item_time);
	vHolder.status = (TextView) convertView
		.findViewById(R.id.my_time_item_status);

	vHolder.time.setText(list.get(position).get("dian"));
	if (type.equals("set")) {
	    vHolder.status.setText(list.get(position).get("ml"));
	} else {
	    if (list.get(position).get("zt").equals("0")) {
		vHolder.status.setText("¿ÕÏÐ");
	    } else {
		vHolder.status.setText("Ã¦Âµ");
	    }
	}
	if (list.get(position).get("zt").equals("0")) {
	    vHolder.bg.setBackgroundColor(Color.rgb(255, 91, 133));
	} else if (list.get(position).get("zt").equals("1")) {
	    vHolder.bg.setBackgroundColor(Color.rgb(255, 153, 0));
	} else {
	    vHolder.bg.setBackgroundColor(Color.rgb(221, 221, 221));
	}
	return convertView;
    }

    class ViewHolder {
	LinearLayout bg;
	TextView time;
	TextView status;
    }
}
