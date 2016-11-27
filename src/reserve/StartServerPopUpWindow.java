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
	    tv1.setText("��Ҫ�����һ���ˣ��ÿ��ģ�");
	    tv2.setText("ͳ�Ʒ���ʱ����������ĵȼ�����Ŷ��");
	    start.setText("ȷ�Ͻ���");
	    final String id = getIntent().getExtras().getString("id");
	    start.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		    // TODO Auto-generated method stub
		    back.setVisibility(View.GONE);
		    title.setText("��ϲ��");
		    tv1.setText("�ɹ�����˴˵�");
		    tv2.setText("�����˻���Ŷ��");
		    tv3.setVisibility(View.VISIBLE);
		    tv3.setText("������Ƭ�����۸���˵�����أ�");
		    start.setText("����");
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
	    title.setText("�����ɹ�");
	    tv1.setText("��ϲ�㣬�����ɹ�");
	    tv2.setText("�û�����֮��ǵ�׼ʱ����Ŷ��");
	    tv3.setText("20���Ӻ��û�δ����˵��Զ�ȡ��");
	    tv3.setVisibility(View.VISIBLE);
	    start.setText("�����û�����");
	    start.setOnClickListener(new OnClickListener() {
	        
	        @Override
	        public void onClick(View v) {
	    	// TODO Auto-generated method stub
	    	Toast.makeText(StartServerPopUpWindow.this, "���ѳɹ���", Toast.LENGTH_SHORT).show();
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
