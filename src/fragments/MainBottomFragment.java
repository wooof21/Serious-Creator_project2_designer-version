/**
 * 
 */
package fragments;

import login.LoginActivity;
import reserve.ReserveActivity;
import tools.Tools;
import vipcenter.PersonalCenterMain;

import com.mkcoming.MainActivity;
import com.mkcomingd.R;

import account.AccountActivity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 底部4个导航控件fragment
 * 
 * @author Liming Chu
 * @param
 * @return
 */

public class MainBottomFragment extends Fragment implements OnClickListener {

    private LinearLayout ll1;
    private LinearLayout ll2;
    private LinearLayout ll3;
    private LinearLayout ll4;

    /** 底部导航4个按钮 */
    private ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;
    private ImageView iv4;

    private TextView underLine1;
    private TextView underLine2;
    private TextView underLine3;
    private TextView underLine4;

    private String currentActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);

	System.out.println(getActivity().getClass().getName().toString());
	System.out.println(getActivity().getClass().getSimpleName().toString());
	currentActivity = getActivity().getClass().getSimpleName().toString();

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
	    @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
	// TODO Auto-generated method stub

	View view = inflater
		.inflate(R.layout.bottom_fragment, container, false);
	ll1 = (LinearLayout) view.findViewById(R.id.bottom_fragment_ll1);
	ll2 = (LinearLayout) view.findViewById(R.id.bottom_fragment_ll2);
	ll3 = (LinearLayout) view.findViewById(R.id.bottom_fragment_ll3);
	ll4 = (LinearLayout) view.findViewById(R.id.bottom_fragment_ll4);

	iv1 = (ImageView) view.findViewById(R.id.bottom_fragment_iv1);
	iv2 = (ImageView) view.findViewById(R.id.bottom_fragment_iv2);
	iv3 = (ImageView) view.findViewById(R.id.bottom_fragment_iv3);
	iv4 = (ImageView) view.findViewById(R.id.bottom_fragment_iv4);

	underLine1 = (TextView) view
		.findViewById(R.id.bottom_fragment_qiangdang_underline);
	underLine2 = (TextView) view
		.findViewById(R.id.bottom_fragment_yuyue_underline);
	underLine3 = (TextView) view
		.findViewById(R.id.bottom_fragment_zhangdan_underline);
	underLine4 = (TextView) view
		.findViewById(R.id.bottom_fragment_mine_underline);
	String className = getActivity().getClass().getSimpleName().toString();
	if (className.equals("MainActivity")) {
	    underLine1.setVisibility(View.VISIBLE);
	    underLine2.setVisibility(View.INVISIBLE);
	    underLine3.setVisibility(View.INVISIBLE);
	    underLine4.setVisibility(View.INVISIBLE);
	    ll1.setClickable(false);
	    ll2.setOnClickListener(this);
	    ll3.setOnClickListener(this);
	    ll4.setOnClickListener(this);
	} else if (className.equals("ReserveActivity")) {
	    underLine1.setVisibility(View.INVISIBLE);
	    underLine2.setVisibility(View.VISIBLE);
	    underLine3.setVisibility(View.INVISIBLE);
	    underLine4.setVisibility(View.INVISIBLE);
	    ll2.setClickable(false);
	    ll1.setOnClickListener(this);
	    ll3.setOnClickListener(this);
	    ll4.setOnClickListener(this);
	} else if (className.equals("AccountActivity")) {
	    underLine1.setVisibility(View.INVISIBLE);
	    underLine2.setVisibility(View.INVISIBLE);
	    underLine3.setVisibility(View.VISIBLE);
	    underLine4.setVisibility(View.INVISIBLE);
	    ll3.setClickable(false);
	    ll2.setOnClickListener(this);
	    ll1.setOnClickListener(this);
	    ll4.setOnClickListener(this);
	} else if (className.equals("PersonalCenterMain")) {
	    underLine1.setVisibility(View.INVISIBLE);
	    underLine2.setVisibility(View.INVISIBLE);
	    underLine3.setVisibility(View.INVISIBLE);
	    underLine4.setVisibility(View.VISIBLE);
	    ll4.setClickable(false);
	    ll2.setOnClickListener(this);
	    ll3.setOnClickListener(this);
	    ll1.setOnClickListener(this);
	} else {
	    ll4.setOnClickListener(this);
	    ll2.setOnClickListener(this);
	    ll3.setOnClickListener(this);
	    ll1.setOnClickListener(this);
	}
	return view;
    }

    /*
     * (non-Javadoc)
     * 
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
    @Override
    public void onClick(View v) {
	// TODO Auto-generated method stub
	Intent intent = null;
	switch (v.getId()) {
	case R.id.bottom_fragment_ll1:
	    intent = new Intent(getActivity(), MainActivity.class);
	    break;
	case R.id.bottom_fragment_ll2:
	    intent = new Intent(getActivity(), ReserveActivity.class);
	    break;
	case R.id.bottom_fragment_ll3:
	    intent = new Intent(getActivity(), AccountActivity.class);
	    // String[] tags =
	    // Tags.getInstance().getTags();
	    // System.out.println("2 " + tags);
	    // intent.putExtra("tags", tags);
	    break;
	case R.id.bottom_fragment_ll4:
	    intent = new Intent(getActivity(), PersonalCenterMain.class);
	    break;
	default:
	    break;
	}
//	if(new Tools().isUserLogin(getActivity())){
	    startActivity(intent);
//	}else{
//	    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
//	    startActivity(new Intent(getActivity(), LoginActivity.class));
//	}
	
	if (currentActivity.equals("MainActivity")) {

	} else {
	    getActivity().finish();
	}
    }

    @Override
    public void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
    }

}
