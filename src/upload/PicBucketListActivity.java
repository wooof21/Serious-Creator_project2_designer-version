/**
 * 
 */
package upload;

import java.io.Serializable;
import java.util.List;

import tools.Exit;

import com.mkcomingd.R;

import model.ImageBucket;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class PicBucketListActivity extends Activity {

    List<ImageBucket> dataList;
    GridView gridView;
    ImageBucketAdapter adapter;// 自定义的适配器
    AlbumHelper helper;
    public static final String EXTRA_IMAGE_LIST = "imagelist";
    public static Bitmap bimap;

    private String wORh;
    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_image_bucket);
	Exit.getInstance().addActivity(this);

	helper = AlbumHelper.getHelper();
	helper.init(getApplicationContext());

	wORh = getIntent().getExtras().getString("wORh");
	initData();
	initView();
    }

    /**
     * 初始化数据
     */
    private void initData() {
	dataList = helper.getImagesBucketList(false);
	bimap = BitmapFactory.decodeResource(getResources(),
		R.drawable.icon_addpic_focused);
    }

    /**
     * 初始化view视图
     */
    private void initView() {
	gridView = (GridView) findViewById(R.id.gridview);
	adapter = new ImageBucketAdapter(PicBucketListActivity.this, dataList);
	gridView.setAdapter(adapter);

	gridView.setOnItemClickListener(new OnItemClickListener() {

	    @Override
	    public void onItemClick(AdapterView<?> parent, View view,
		    int position, long id) {
		/**
		 * 根据position参数，可以获得跟GridView的子View相绑定的实体类，然后根据它的isSelected状态，
		 * 来判断是否显示选中效果。 至于选中效果的规则，下面适配器的代码中会有说明
		 */
		// if(dataList.get(position).isSelected()){
		// dataList.get(position).setSelected(false);
		// }else{
		// dataList.get(position).setSelected(true);
		// }
		/**
		 * 通知适配器，绑定的数据发生了改变，应当刷新视图
		 */
		// adapter.notifyDataSetChanged();
		Intent intent = new Intent(PicBucketListActivity.this,
			ImageGridActivity.class);
		intent.putExtra(PicBucketListActivity.EXTRA_IMAGE_LIST,
			(Serializable) dataList.get(position).imageList);
		intent.putExtra("wORh", wORh);
		startActivity(intent);
		finish();
	    }

	});
    }
}
