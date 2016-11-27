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
    ImageBucketAdapter adapter;// �Զ����������
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
     * ��ʼ������
     */
    private void initData() {
	dataList = helper.getImagesBucketList(false);
	bimap = BitmapFactory.decodeResource(getResources(),
		R.drawable.icon_addpic_focused);
    }

    /**
     * ��ʼ��view��ͼ
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
		 * ����position���������Ի�ø�GridView����View��󶨵�ʵ���࣬Ȼ���������isSelected״̬��
		 * ���ж��Ƿ���ʾѡ��Ч���� ����ѡ��Ч���Ĺ��������������Ĵ����л���˵��
		 */
		// if(dataList.get(position).isSelected()){
		// dataList.get(position).setSelected(false);
		// }else{
		// dataList.get(position).setSelected(true);
		// }
		/**
		 * ֪ͨ���������󶨵����ݷ����˸ı䣬Ӧ��ˢ����ͼ
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
