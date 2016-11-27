/**
 * 
 */
package upload;

import java.util.ArrayList;
import java.util.List;

import tools.Exit;

import com.mkcomingd.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class PhotoActivity extends Activity {

    private ArrayList<View> listViews = null;
    private ViewPager pager;
    private MyPageAdapter adapter;
    private int count;

    public List<Bitmap> bmp = new ArrayList<Bitmap>();
    public List<String> drr = new ArrayList<String>();
    public List<String> del = new ArrayList<String>();
    public int max;

    RelativeLayout photo_relativeLayout;

    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_photo);
	Exit.getInstance().addActivity(this);

	photo_relativeLayout = (RelativeLayout) findViewById(R.id.photo_relativeLayout);
	photo_relativeLayout.setBackgroundColor(0x70000000);

	for (int i = 0; i < Bmp.bmp.size(); i++) {
	    bmp.add(Bmp.bmp.get(i));
	}
	for (int i = 0; i < Bmp.drr.size(); i++) {
	    drr.add(Bmp.drr.get(i));
	}
	max = Bmp.max;

	Button photo_bt_exit = (Button) findViewById(R.id.photo_bt_exit);
	photo_bt_exit.setOnClickListener(new View.OnClickListener() {
	    public void onClick(View v) {

		finish();
	    }
	});
	Button photo_bt_del = (Button) findViewById(R.id.photo_bt_del);
	photo_bt_del.setOnClickListener(new View.OnClickListener() {
	    public void onClick(View v) {
		if (listViews.size() == 1) {
		    Bmp.bmp.clear();
		    Bmp.drr.clear();
		    Bmp.max = 0;
		    // FileUtils.deleteDir();
		    adapter.notifyDataSetChanged();
		    finish();
		    Log.e("1111111", "11111111111111");
		} else {
		    String newStr = drr.get(count).substring(
			    drr.get(count).lastIndexOf("/") + 1,
			    drr.get(count).lastIndexOf("."));
		    bmp.remove(count);
		    drr.remove(count);
		    del.add(newStr);
		    max--;
		    pager.removeAllViews();
		    listViews.remove(count);
		    adapter.setListViews(listViews);
		    adapter.notifyDataSetChanged();
		}
	    }
	});
	Button photo_bt_enter = (Button) findViewById(R.id.photo_bt_enter);
	photo_bt_enter.setOnClickListener(new View.OnClickListener() {

	    public void onClick(View v) {

		Bmp.bmp = bmp;
		Bmp.drr = drr;
		Bmp.max = max;
		for (int i = 0; i < del.size(); i++) {
		    FileUtils.delFile(del.get(i) + ".JPEG");
		}
		finish();
	    }
	});

	pager = (ViewPager) findViewById(R.id.viewpager);
	pager.setOnPageChangeListener(pageChangeListener);
	for (int i = 0; i < bmp.size(); i++) {
	    initListViews(bmp.get(i));//
	}

	adapter = new MyPageAdapter(listViews);// ����adapter
	pager.setAdapter(adapter);// ����������
	Intent intent = getIntent();
	int id = intent.getIntExtra("ID", 0);
	pager.setCurrentItem(id);
    }

    private void initListViews(Bitmap bm) {
	if (listViews == null)
	    listViews = new ArrayList<View>();
	ImageView img = new ImageView(this);// ����textView����
	img.setBackgroundColor(0xff000000);
	img.setImageBitmap(bm);
	img.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
		LayoutParams.FILL_PARENT));
	listViews.add(img);// ���view
    }

    private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

	public void onPageSelected(int arg0) {// ҳ��ѡ����Ӧ����
	    count = arg0;
	}

	public void onPageScrolled(int arg0, float arg1, int arg2) {// �����С�����

	}

	public void onPageScrollStateChanged(int arg0) {// ����״̬�ı�

	}
    };

    class MyPageAdapter extends PagerAdapter {

	private ArrayList<View> listViews;// content

	private int size;// ҳ��

	public MyPageAdapter(ArrayList<View> listViews) {// ���캯��
							 // ��ʼ��viewpager��ʱ�����һ��ҳ��
	    this.listViews = listViews;
	    size = listViews == null ? 0 : listViews.size();
	}

	public void setListViews(ArrayList<View> listViews) {// �Լ�д��һ�����������������
	    this.listViews = listViews;
	    size = listViews == null ? 0 : listViews.size();
	}

	public int getCount() {// ��������
	    return size;
	}

	public int getItemPosition(Object object) {
	    return POSITION_NONE;
	}

	public void destroyItem(View arg0, int arg1, Object arg2) {// ����view����
	    ((ViewPager) arg0).removeView(listViews.get(arg1 % size));
	}

	public void finishUpdate(View arg0) {
	}

	public Object instantiateItem(View arg0, int arg1) {// ����view����
	    try {
		((ViewPager) arg0).addView(listViews.get(arg1 % size), 0);

	    } catch (Exception e) {
	    }
	    return listViews.get(arg1 % size);
	}

	public boolean isViewFromObject(View arg0, Object arg1) {
	    return arg0 == arg1;
	}

    }

}
