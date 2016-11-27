/**
 * 
 */
package adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.mkcoming.CommentActivity;
import com.mkcoming.GrabSubmitPopUp;
import com.mkcomingd.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class OrderReserveFinishedListViewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<HashMap<String, String>> list;
    private String type;
    private Handler handler;
    private LayoutInflater lInflater;
    private DisplayImageOptions options;

    public OrderReserveFinishedListViewAdapter(Context context,
	    ArrayList<HashMap<String, String>> list, String type,
	    Handler handler) {
	super();
	this.context = context;
	this.list = list;
	this.type = type;
	this.handler = handler;
	this.lInflater = LayoutInflater.from(context);
	preperImageLoader();
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
	    convertView = lInflater.inflate(R.layout.mian_listview_item, null);
	}
	vHolder.type = (TextView) convertView
		.findViewById(R.id.main_lv_item_type);
	vHolder.distance = (TextView) convertView
		.findViewById(R.id.main_lv_item_distance);
	vHolder.pic = (ImageView) convertView
		.findViewById(R.id.main_lv_item_pic);
	vHolder.name = (TextView) convertView
		.findViewById(R.id.main_lv_item_name);
	vHolder.price = (TextView) convertView
		.findViewById(R.id.main_lv_item_price);
	vHolder.phone = (TextView) convertView
		.findViewById(R.id.main_lv_item_phone);
	vHolder.dt = (TextView) convertView.findViewById(R.id.main_lv_item_dt);
	vHolder.address = (TextView) convertView
		.findViewById(R.id.main_lv_item_address);
	vHolder.changedTitle = (TextView) convertView
		.findViewById(R.id.main_lv_item_changed_title);
	vHolder.changedText = (TextView) convertView
		.findViewById(R.id.main_lv_item_changed_text);
	vHolder.hideRl = (RelativeLayout) convertView
		.findViewById(R.id.main_lv_item_hide_rl);
	vHolder.hideLl = (LinearLayout) convertView
		.findViewById(R.id.main_lv_item_hide_ll);
	vHolder.hideTime = (TextView) convertView
		.findViewById(R.id.main_lv_item_hide_time);
	vHolder.grab = (TextView) convertView
		.findViewById(R.id.main_lv_item_grab);
	vHolder.dt_ll = (LinearLayout) convertView
		.findViewById(R.id.main_lv_item_dt_ll);
	vHolder.dis = (TextView) convertView
		.findViewById(R.id.main_lv_item_dis);
	final HashMap<String, String> hashMap = list.get(position);

	if (type.equals("order_done")) {
	    vHolder.hideLl.setVisibility(View.GONE);
	    vHolder.type.setText(hashMap.get("fgid") + "-"
		    + hashMap.get("zrid"));
	    vHolder.distance.setVisibility(View.GONE);
	    ImageLoader.getInstance().displayImage(hashMap.get("tx"),
		    vHolder.pic, options);
	    vHolder.name.setText(hashMap.get("nc"));
	    vHolder.price.setText("￥" + hashMap.get("je"));
	    vHolder.phone.setText(hashMap.get("dh"));
	    vHolder.dt.setText(hashMap.get("yysj") + "点");
	    vHolder.address.setText(hashMap.get("dz"));
	    vHolder.changedText.setText(hashMap.get("bz"));
	    if (hashMap.get("pjzt").equals("1")) {
		vHolder.grab.setText("完结");
		vHolder.grab.setBackgroundColor(Color.rgb(39, 152, 232));
		vHolder.grab.setClickable(false);
	    } else {
		vHolder.grab.setText("评价");
		vHolder.grab.setBackgroundColor(Color.rgb(255, 153, 0));
		vHolder.grab.setOnClickListener(new OnClickListener() {

		    @Override
		    public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(context,
				CommentActivity.class);
			intent.putExtra("id", hashMap.get("id"));
			context.startActivity(intent);

		    }
		});
	    }

	} else if (type.equals("reserve_done")) {
	    vHolder.hideLl.setVisibility(View.GONE);
	    vHolder.type.setText(hashMap.get("fgid") + "-"
		    + hashMap.get("zrid"));
	    vHolder.distance.setVisibility(View.GONE);
	    ImageLoader.getInstance().displayImage(hashMap.get("tx"),
		    vHolder.pic, options);
	    vHolder.name.setText(hashMap.get("nc"));
	    vHolder.price.setText("￥" + hashMap.get("je"));
	    vHolder.phone.setText(hashMap.get("dh"));
	    vHolder.dt_ll.setVisibility(View.VISIBLE);
	    vHolder.dt.setText(hashMap.get("yysj") + "点");
	    vHolder.address.setText(hashMap.get("dz"));
	    vHolder.changedText.setText(hashMap.get("bz"));
	    if (hashMap.get("pjzt").equals("1")) {
		vHolder.grab.setText("完结");
		vHolder.grab.setBackgroundColor(Color.rgb(39, 152, 232));
		vHolder.grab.setClickable(false);
	    } else {
		vHolder.grab.setText("评价");
		vHolder.grab.setBackgroundColor(Color.rgb(255, 153, 0));
		vHolder.grab.setOnClickListener(new OnClickListener() {

		    @Override
		    public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(context,
				CommentActivity.class);
			intent.putExtra("id", hashMap.get("id"));
			context.startActivity(intent);
		    }
		});
	    }
	} else if (type.equals("order_grab")) {
	    vHolder.type.setText(hashMap.get("fgid") + "-"
		    + hashMap.get("zrid"));
	    vHolder.distance.setVisibility(View.GONE);
	    ImageLoader.getInstance().displayImage(hashMap.get("tx"),
		    vHolder.pic, options);
	    vHolder.name.setText(hashMap.get("nc"));
	    vHolder.price.setText("￥" + hashMap.get("je"));
	    vHolder.address.setText(hashMap.get("dz"));
	    vHolder.changedText.setText(hashMap.get("bz"));
	    vHolder.grab.setText("抢单");
	    vHolder.grab.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		    // TODO Auto-generated method stub
		    Message msg = handler.obtainMessage();
		    msg.what = 400;
		    msg.obj = hashMap;
		    msg.sendToTarget();
		}
	    });

	} else {
	    vHolder.hideLl.setVisibility(View.VISIBLE);
	    vHolder.dis.setVisibility(View.VISIBLE);
	    vHolder.type.setText(hashMap.get("fgid") + "-"
		    + hashMap.get("zrid"));
	    vHolder.distance.setVisibility(View.GONE);
	    vHolder.phone.setText(hashMap.get("dh"));
	    ImageLoader.getInstance().displayImage(hashMap.get("tx"),
		    vHolder.pic, options);
	    vHolder.name.setText(hashMap.get("nc"));
	    vHolder.price.setText("￥" + hashMap.get("je"));
	    vHolder.address.setText(hashMap.get("dz"));
	    vHolder.dt_ll.setVisibility(View.VISIBLE);
	    vHolder.dt.setText(hashMap.get("yysj") + "点");
	    vHolder.changedText.setText(hashMap.get("bz"));
	    vHolder.hideTime.setText(hashMap.get("fwsc") + "分钟");
	    vHolder.grab.setText("查看");

	    vHolder.grab.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		    // TODO Auto-generated method stub
		    Message msg = handler.obtainMessage();
		    msg.what = 401;
		    msg.obj = hashMap.get("id");
		    msg.sendToTarget();
		}
	    });

	    vHolder.dis.setVisibility(View.VISIBLE);
	    vHolder.dis.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		    // TODO Auto-generated method stub
		    Message msg = handler.obtainMessage();
		    msg.what = 1000;
		    msg.obj = hashMap.get("id");
		    msg.sendToTarget();
		}
	    });

	}

	return convertView;
    }

    class ViewHolder {
	TextView type;
	TextView distance;
	ImageView pic;
	TextView name;
	TextView price;
	TextView phone;
	TextView dt;
	TextView address;
	TextView changedTitle;
	TextView changedText;
	RelativeLayout hideRl;
	LinearLayout hideLl;
	TextView hideTime;
	TextView grab;
	LinearLayout dt_ll;
	TextView dis;
    }

    private void preperImageLoader() {

	/******************* 配置ImageLoder ***********************************************/
	File cacheDir = StorageUtils.getOwnCacheDirectory(context,
		"imageloader/Cache");

	ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
		context).denyCacheImageMultipleSizesInMemory()
		.discCache(new UnlimitedDiscCache(cacheDir))// 自定义缓存路径
		.build();// 开始构建

	options = new DisplayImageOptions.Builder().cacheInMemory()
		.cacheOnDisc().imageScaleType(ImageScaleType.IN_SAMPLE_INT)
		.showImageForEmptyUri(com.mkcomingd.R.drawable.question)
		.showImageOnFail(com.mkcomingd.R.drawable.question).build();

	ImageLoader.getInstance().init(config);// 全局初始化此配置
	/*********************************************************************************/
    }
}
