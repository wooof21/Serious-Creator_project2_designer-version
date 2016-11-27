/**
 * 
 */
package com.mkcoming;

import tools.Exit;

import com.igexin.sdk.PushManager;
import com.mkcomingd.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class GuidePage extends Activity {

    private TextView tv1, tv2;

    private int width = 180;
    private boolean isRun = true;

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.start_page);
	Exit.getInstance().addActivity(this);
//	tv1 = (TextView) findViewById(R.id.progressBar1);
//	tv2 = (TextView) findViewById(R.id.progressBar2);
//	tv1.setWidth(width);
//	tv2.setWidth(width);
//	ImageView iv = (ImageView) findViewById(R.id.start_shadow);
//	AnimationSet set = new AnimationSet(true);
//	ScaleAnimation sa1 = new ScaleAnimation(1, 0.7f, 1, 0.7f,
//		Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
//		0.5f);
//	ScaleAnimation sa2 = new ScaleAnimation(1, 1.3f, 1, 1.3f,
//		Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
//		0.5f);
//	
//	sa1.setDuration(600);
//	sa2.setDuration(600);
//	sa1.setRepeatCount(1000);
//	sa2.setRepeatCount(1000);
//	sa1.setFillAfter(false);
//	sa2.setFillAfter(false);
//	set.addAnimation(sa1);
//	set.addAnimation(sa2);
//	iv.startAnimation(set);
//	
//	startRun();
	
	PushManager.getInstance().initialize(this.getApplicationContext());
	if(!PushManager.getInstance().isPushTurnedOn(this.getApplicationContext())){
	    PushManager.getInstance().turnOnPush(this.getApplicationContext());
	}
	
	new Handler().postDelayed(new Runnable() {

	    @Override
	    public void run() {
		// TODO Auto-generated method stub
		 startActivity(new Intent(GuidePage.this,
		 MainActivity.class));
		 GuidePage.this.finish();
	    }
	}, 3000);
    }

    private void startRun() {
	new Thread(new Runnable() {

	    @Override
	    public void run() {
		// TODO Auto-generated method stub
		while (isRun) {
		    try {
			Thread.sleep(50); // sleep 1000ms
			width -= 1;
			Message message = Message.obtain();
			message.what = 1;
			message.obj = width;
			timeHandler.sendMessage(message);
			if (width < 0) {
			    isRun = false;
			}
		    } catch (Exception e) {
			e.printStackTrace();
		    }
		}
	    }
	}).start();
    }

    private Handler timeHandler = new Handler() {

	@Override
	public void handleMessage(Message msg) {
	    super.handleMessage(msg);
	    switch (msg.what) {
	    case 1:
		int wid = (Integer) msg.obj;
		tv1.setWidth(wid);
		tv2.setWidth(wid);
		break;
	    default:
		break;
	    }
	}
    };

}
