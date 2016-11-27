/**
 * 
 */
package vipcenter;

import tools.Exit;

import com.mkcomingd.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author Liming Chu
 *
 * @param
 * @return
 */
public class WYHint extends Activity{

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wy_hint);
	Exit.getInstance().addActivity(this);
        
        findViewById(R.id.wyb_ok).setOnClickListener(new OnClickListener() {
	    
	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		finish();
	    }
	});
    }
}
