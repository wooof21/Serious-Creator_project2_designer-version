package upload;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import tools.Exit;
import upload.ImageGridAdapter.TextCallback;
import vipcenter.MyHonorAddHonor;
import vipcenter.MyWork;
import vipcenter.MyWorkAddWork;

import com.mkcomingd.R;

import model.ImageItem;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class ImageGridActivity extends Activity {
    public static final String EXTRA_IMAGE_LIST = "imagelist";
    private String wORh;
    List<ImageItem> dataList;
    GridView gridView;
    ImageGridAdapter adapter;
    AlbumHelper helper;
    Button bt;

    Handler mHandler = new Handler() {
	@Override
	public void handleMessage(Message msg) {
	    switch (msg.what) {
	    case 0:
		Toast.makeText(ImageGridActivity.this, "最多选择1张图片", 400).show();
		break;

	    default:
		break;
	    }
	}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	setContentView(R.layout.activity_image_grid);
	Exit.getInstance().addActivity(this);

	helper = AlbumHelper.getHelper();
	helper.init(getApplicationContext());

	dataList = (List<ImageItem>) getIntent().getSerializableExtra(
		EXTRA_IMAGE_LIST);
	wORh = getIntent().getExtras().getString("wORh");

	initView();
	bt = (Button) findViewById(R.id.bt);
	bt.setOnClickListener(new OnClickListener() {

	    public void onClick(View v) {
		ArrayList<String> list = new ArrayList<String>();
		Collection<String> c = adapter.map.values();
		Iterator<String> it = c.iterator();
		for (; it.hasNext();) {
		    list.add(it.next());
		}
		for (int i = 0; i < list.size(); i++) {
		    if (Bmp.drr.size() < 1) {
			Bmp.drr.add(list.get(i));
		    }
		}

		if (Bmp.act_bool) {
		    Intent intent = null;
		    if (wORh.equals("w")) {
			intent = new Intent(ImageGridActivity.this,
				MyWorkAddWork.class);
		    }else{
			intent = new Intent(ImageGridActivity.this,
				MyHonorAddHonor.class);
		    }
		    startActivity(intent);
		    Bmp.act_bool = false;
		    finish();
		}
	    }

	});
    }

    /**
     * 鍒濆鍖杤iew瑙嗗浘
     */
    private void initView() {
	gridView = (GridView) findViewById(R.id.gridview);
	gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
	adapter = new ImageGridAdapter(ImageGridActivity.this, dataList,
		mHandler);
	gridView.setAdapter(adapter);
	adapter.setTextCallback(new TextCallback() {
	    public void onListen(int count) {
		bt.setText("完成" + "(" + count + ")");
	    }
	});

	gridView.setOnItemClickListener(new OnItemClickListener() {

	    @Override
	    public void onItemClick(AdapterView<?> parent, View view,
		    int position, long id) {

		// if(dataList.get(position).isSelected()){
		// dataList.get(position).setSelected(false);
		// }else{
		// dataList.get(position).setSelected(true);
		// }

		adapter.notifyDataSetChanged();
	    }

	});

    }
}
