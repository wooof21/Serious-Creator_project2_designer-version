/**
 * 
 */
package adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.mkcomingd.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import tools.Config;
import view.AutoChangeLineLL;

import android.app.Activity;
import android.content.Context;
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
public class CommentListAdapter extends BaseAdapter {

    private ArrayList<HashMap<String, String>> list;
    private Context context;
    private LayoutInflater lInflater;
    private DisplayImageOptions options;

    public CommentListAdapter(ArrayList<HashMap<String, String>> list,
	    Context context) {
	super();
	this.list = list;
	this.context = context;
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
	    convertView = lInflater.inflate(R.layout.comment_list_item, null);
	}

	vHolder.pic = (ImageView) convertView
		.findViewById(R.id.comment_list_item_pic);
	vHolder.name = (TextView) convertView
		.findViewById(R.id.comment_list_item_name);
	vHolder.dt = (TextView) convertView
		.findViewById(R.id.comment_list_item_dt);
	vHolder.comments = (TextView) convertView
		.findViewById(R.id.comment_list_item_comments);
	vHolder.container = (AutoChangeLineLL) convertView
		.findViewById(R.id.comment_list_item_container);

	vHolder.container.setmCellHeight(80);
	vHolder.container.setmCellWidth(80);

	if (list.get(position).get("tx").startsWith("http")) {
	    ImageLoader.getInstance().displayImage(
		    list.get(position).get("tx"), vHolder.pic, options);
	} else {
	    ImageLoader.getInstance().displayImage(
		    Config.URL + list.get(position).get("tx"), vHolder.pic,
		    options);
	}
	vHolder.name.setText(list.get(position).get("xm"));
	vHolder.dt.setText(list.get(position).get("sj"));
	vHolder.comments.setText(list.get(position).get("nr"));
	if (list.get(position).get("pic") != null) {
	    String[] pics = list.get(position).get("pic").split(",");
	    for (String pic : pics) {
		System.out.println(" + " + pic);
		ImageView iv = new ImageView(context);
		ImageLoader.getInstance().displayImage(Config.URL + pic, iv);
		vHolder.container.addView(iv);
	    }
	}

	return convertView;
    }

    class ViewHolder {
	ImageView pic;
	TextView name;
	TextView dt;
	TextView comments;
	AutoChangeLineLL container;
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
		.showImageForEmptyUri(R.drawable.question)
		.showImageOnFail(R.drawable.question).build();

	ImageLoader.getInstance().init(config);// 全局初始化此配置
	/*********************************************************************************/
    }
}
