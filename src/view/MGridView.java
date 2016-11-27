/**
 * 
 */
package view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class MGridView extends GridView {

    public boolean hasScrollBar = true;
    
    /**
     * @param context
     */
    public MGridView(Context context) {
	super(context, null);
	// TODO Auto-generated constructor stub
    }

    public MGridView(Context context, AttributeSet attrs) {
	super(context, attrs, 0);
    }

    public MGridView(Context context, AttributeSet attrs, int defStyle) {
	super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

	 int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,  
	                MeasureSpec.AT_MOST);  
	        super.onMeasure(widthMeasureSpec, expandSpec);  
    }

}
