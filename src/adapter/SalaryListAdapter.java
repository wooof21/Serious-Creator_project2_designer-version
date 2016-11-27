/**
 * 
 */
package adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.mkcomingd.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author Liming Chu
 *
 * @param
 * @return
 */
public class SalaryListAdapter extends BaseAdapter{

    private ArrayList<HashMap<String, String>> list;
    private Context context;
    private LayoutInflater lInflater;
    
public SalaryListAdapter(ArrayList<HashMap<String, String>> list,
	    Context context) {
	super();
	this.list = list;
	this.context = context;
	this.lInflater = LayoutInflater.from(context);
    }
    
    
    /* (non-Javadoc)
     * @see android.widget.Adapter#getCount()
     */
    @Override
    public int getCount() {
	// TODO Auto-generated method stub
	return list.size();
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getItem(int)
     */
    @Override
    public Object getItem(int position) {
	// TODO Auto-generated method stub
	return list.get(position);
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getItemId(int)
     */
    @Override
    public long getItemId(int position) {
	// TODO Auto-generated method stub
	return position;
    }

    /* (non-Javadoc)
     * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	// TODO Auto-generated method stub
	if(convertView == null){
	    convertView = lInflater.inflate(R.layout.salary_detail_listview_item, null);
	}
	TextView time = (TextView) convertView.findViewById(R.id.sa_time);
	TextView way = (TextView) convertView.findViewById(R.id.sa_way);
	TextView amount = (TextView) convertView.findViewById(R.id.sa_amount);
	
	time.setText(list.get(position).get("sj"));
	way.setText(list.get(position).get("txfs"));
	amount.setText("гд"+list.get(position).get("je"));
	
	return convertView;
    }

}
