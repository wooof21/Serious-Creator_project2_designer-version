/**
 * 
 */
package adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import tools.Config;

import com.mkcomingd.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class MyWorkGridViewAdapter extends BaseAdapter {

    private ArrayList<String> list;
    private Context context;
    private LayoutInflater lInflater;
    
    
    public MyWorkGridViewAdapter(ArrayList<String> list, Context context) {
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
//	if (convertView == null) {
//	    convertView = lInflater.inflate(R.layout.my_work_gv_item, null);
//	}
	convertView = lInflater.inflate(R.layout.my_work_gv_item, null);
	ImageView iv = (ImageView) convertView
		.findViewById(R.id.my_work_gv_item_iv);
	System.out.println(list.get(position));
	ImageLoader.getInstance().displayImage(Config.URL + list.get(position), iv);
	return convertView;
    }

    private void preperImageLoader() {

	/******************* 配置ImageLoder ***********************************************/
	File cacheDir = StorageUtils.getOwnCacheDirectory(context,
		"imageloader/Cache");

	ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
		context).denyCacheImageMultipleSizesInMemory()
		.discCache(new UnlimitedDiscCache(cacheDir))// 自定义缓存路径
		.build();// 开始构建

	// DisplayImageOptions options =
	// new DisplayImageOptions.Builder()
	// .cacheInMemory()
	// .cacheOnDisc()
	// .imageScaleType(
	// ImageScaleType.IN_SAMPLE_INT)
	// .showImageForEmptyUri(
	// com.mkcomingd.R.drawable.default_pic)
	// .showImageOnFail(com.mkcomingd.R.drawable.default_pic)
	// .build();

	ImageLoader.getInstance().init(config);// 全局初始化此配置
	/*********************************************************************************/
    }
}
