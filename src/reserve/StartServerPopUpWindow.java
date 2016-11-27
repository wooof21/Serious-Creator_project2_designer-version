/**
 * 
 */
package reserve;

import tools.Exit;

import com.mkcoming.CommentActivity;
import com.mkcomingd.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class StartServerPopUpWindow extends Activity implements OnClickListener {

    private ImageView back;
    private TextView title;
    private TextView tv1, tv2, tv3;
    private TextView start;

    private String type;

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.start_finish_back_dialog_view);
	Exit.getInstance().addActivity(this);
	prepareView();
	type = getIntent().getExtras().getString("type");

	if (type.equals("start_servive")) {
	} else if (type.equals("end_service")) {
	    tv1.setText("快要完成这一单了，好开心！");
	    tv2.setText("统计服务时长有利于你的等级提升哦！");
	    start.setText("确认结束");
	    final String id = getIntent().getExtras().getString("id");
	    start.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		    // TODO Auto-generated method stub
		    back.setVisibility(View.GONE);
		    title.setText("恭喜你");
		    tv1.setText("成功完成了此单");
		    tv2.setText("别忘了互评哦！");
		    tv3.setVisibility(View.VISIBLE);
		    tv3.setText("附带照片的评论更有说服力呢！");
		    start.setText("评价");
		    start.setBackgroundColor(Color.rgb(255, 153, 0));
		    start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			    // TODO Auto-generated method stub
			    Intent intent = new Intent(StartServerPopUpWindow.this, CommentActivity.class);
			    intent.putExtra("id", id);
			    startActivity(intent);
			    finish();
			}
		    });
		}
	    });
	}else if(type.equals("grab_order")){
	    title.setText("抢单成功");
	    tv1.setText("恭喜你，抢单成功");
	    tv2.setText("用户付款之后记得准时服务哦！");
	    tv3.setText("20分钟后用户未付款，此单自动取消");
	    tv3.setVisibility(View.VISIBLE);
	    start.setText("提醒用户付款");
	    start.setOnClickListener(new OnClickListener() {
	        
	        @Override
	        public void onClick(View v) {
	    	// TODO Auto-generated method stub
	    	Toast.makeText(StartServerPopUpWindow.this, "提醒成功！", Toast.LENGTH_SHORT).show();
	    	finish();
	        }
	    });
	}
    }

    private void prepareView() {
	back = (ImageView) findViewById(R.id.title_back);
	title = (TextView) findViewById(R.id.title_text);
	tv1 = (TextView) findViewById(R.id.popup_tv1);
	tv2 = (TextView) findViewById(R.id.popup_tv2);
	tv3 = (TextView) findViewById(R.id.popup_tv3);
	start = (TextView) findViewById(R.id.popup_start);

	back.setOnClickListener(this);
	start.setOnClickListener(this);
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v) {
	// TODO Auto-generated method stub
	switch (v.getId()) {
	case R.id.title_back:
	    setResult(4444);
	    finish();
	    break;
	case R.id.popup_start:
	    setResult(1000);
	    finish();
	    break;
	default:
	    break;
	}
    }
}
