/**
 * 
 */
package vipcenter;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tools.Config;
import tools.CustomProgressDialog;
import tools.Exit;
import tools.Tools;
import upload.Bmp;
import upload.FileUtils;
import upload.PhotoActivity;
import upload.PicBucketListActivity;

import com.mkcomingd.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class MyWorkAddWork extends Activity implements OnClickListener {

    private ImageView back;
    private GridView gv;
    private Spinner select;
    private TextView submit;
    private LinearLayout parent;

    private static final int TAKE_PICTURE = 0x000000;
    private String path = "";
    private BaseAdapter adapter;

    private ArrayList<File> fileList;
    public CustomProgressDialog pd;

    private ArrayList<String> muType;
    private HashMap<String, String> muId;

    private String id;

    /*
     * (non-Javadoc)
     * 
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.my_work_add_work_dialog_view);
	Exit.getInstance().addActivity(this);
	prepareView();
	fileList = new ArrayList<File>();

	new AsyncTask<Void, Void, ArrayList<String>>() {

	    @Override
	    protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pd = CustomProgressDialog.createDialog(MyWorkAddWork.this);
		pd.show();
		muType = new ArrayList<String>();
		muId = new HashMap<String, String>();
	    }

	    @Override
	    protected ArrayList<String> doInBackground(Void... params) {
		// TODO Auto-generated method stub
		String url = Config.MU_TYPE_URL
			+ new Tools().getUserId(MyWorkAddWork.this);
		Log.e("url", url);
		String data = new Tools().getURL(url);
		System.out.println(data);

		try {
		    JSONObject jObject = new JSONObject(data);
		    JSONObject result = jObject.getJSONObject("result");
		    String code = result.getString("code");
		    if (code.equals("1")) {
			JSONArray jArray = jObject.getJSONArray("data");
			for (int i = 0, j = jArray.length(); i < j; i++) {
			    JSONObject job = jArray.optJSONObject(i);
			    muId.put(job.getString("classname"),
				    job.getString("id"));
			    muType.add(job.getString("classname"));

			}
		    }
		} catch (JSONException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		return muType;
	    }

	    @Override
	    protected void onPostExecute(ArrayList<String> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		pd.dismiss();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
			MyWorkAddWork.this,
			android.R.layout.simple_spinner_item, muType);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		select.setAdapter(adapter);
		select.setOnItemSelectedListener(new OnItemSelectedListener() {

		    @Override
		    public void onItemSelected(AdapterView<?> arg0, View arg1,
			    int arg2, long arg3) {
			// TODO Auto-generated method stub
			System.out.println(muType.get(arg2));
			System.out.println(muId.get(muType.get(arg2)));
			id = muId.get(muType.get(arg2));
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		    }
		});
	    }

	}.execute();
    }

    private void prepareView() {
	back = (ImageView) findViewById(R.id.title_back);
	gv = (GridView) findViewById(R.id.add_work_gv);
	select = (Spinner) findViewById(R.id.my_work_add_work_select_type);
	submit = (TextView) findViewById(R.id.my_work_add_work_submit);
	parent = (LinearLayout) findViewById(R.id.add_work_parent);

	back.setOnClickListener(this);
	submit.setOnClickListener(this);

	gv.setSelector(new ColorDrawable(Color.TRANSPARENT));
	adapter = new GridAdapter(this);
	((GridAdapter) adapter).update();
	gv.setAdapter(adapter);
	gv.setOnItemClickListener(new OnItemClickListener() {

	    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		    long arg3) {
		if (arg2 == Bmp.bmp.size()) {
		    new PopupWindows(MyWorkAddWork.this, gv);
		} else {
		    Intent intent = new Intent(MyWorkAddWork.this,
			    PhotoActivity.class);
		    intent.putExtra("ID", arg2);
		    startActivity(intent);
		    finish();
		}
	    }
	});
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
	    finish();
	    break;
	case R.id.my_work_add_work_submit:
	    List<String> list = new ArrayList<String>();
	    for (int i = 0; i < Bmp.drr.size(); i++) {
		String Str = Bmp.drr.get(i).substring(
			Bmp.drr.get(i).lastIndexOf("/") + 1,
			Bmp.drr.get(i).lastIndexOf("."));
		list.add(FileUtils.SDPATH + Str + ".JPEG");
		System.out.println(list.get(i));

		File file = new File(list.get(i));
		fileList.add(file);
	    }
	    new SubmitAsync().execute(fileList);
	    break;
	default:
	    break;
	}
    }

    class SubmitAsync extends AsyncTask<ArrayList<File>, Void, String> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
	    // TODO Auto-generated method stub
	    super.onPreExecute();
	    pd = CustomProgressDialog.createDialog(MyWorkAddWork.this);
	    pd.show();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected String doInBackground(ArrayList<File>... params) {
	    // TODO Auto-generated method stub
	    String url = Config.MY_WORK_ADD_URL
		    + new Tools().getUserId(MyWorkAddWork.this) + "&zrid=" + id;

	    Log.e("url", url);
	    uploadFile(params[0], url);
	    String code = "";
	    String data = new Tools().getURL(url);
	    System.out.println(data);
	    try {
		JSONObject job = new JSONObject(data);
		JSONObject result = job.getJSONObject("result");
		code = result.getString("code");
	    } catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    return code;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(String result) {
	    // TODO Auto-generated method stub
	    super.onPostExecute(result);
	    pd.dismiss();
	    if(result.equals("1")){
		Toast.makeText(MyWorkAddWork.this, "�ϴ��ɹ���", Toast.LENGTH_SHORT).show();
		MyWorkAddWork.this.finish();
	    }
	}

    }

    /**
     * android�ϴ��ļ���������
     * 
     * @param file
     *            ��Ҫ�ϴ����ļ�
     * @param RequestURL
     *            �����rul
     * @return ������Ӧ������
     */
    public static String uploadFile(ArrayList<File> file, String RequestURL) {
	String result = null;
	String BOUNDARY = UUID.randomUUID().toString(); // �߽��ʶ �������
	String PREFIX = "--", LINE_END = "\r\n";
	String CONTENT_TYPE = "multipart/form-data"; // ��������,
						     // ���������˵�������⴫�����ļ������ַ�����

	try {

	    URL url = new URL(RequestURL);
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setReadTimeout(10 * 1000);
	    conn.setConnectTimeout(10 * 1000);
	    conn.setDoInput(true); // ����������
	    conn.setDoOutput(true); // ���������
	    conn.setUseCaches(false); // ������ʹ�û���
	    conn.setRequestMethod("POST"); // ����ʽ
	    conn.setRequestProperty("Charset", "utf-8"); // ���ñ���
	    conn.setRequestProperty("connection", "keep-alive");
	    conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
		    + BOUNDARY);

	    StringBuilder sb = new StringBuilder();
	    for (int i = 0, j = file.size(); i < j; i++) {
		sb.append(PREFIX);
		sb.append(BOUNDARY);
		sb.append(LINE_END);

		sb.append("Content-Disposition: form-data; name=\""
			+ "uploadfile" + "\"" + LINE_END);
		sb.append("Content-Type: text/plain; charset=" + "utf-8"
			+ LINE_END);
		sb.append("Content-Transfer-Encoding: 8bit" + LINE_END);
		sb.append(LINE_END);
		sb.append("uploadfile");
		sb.append(LINE_END);
	    }
	    DataOutputStream outStream = new DataOutputStream(
		    conn.getOutputStream());
	    outStream.write(sb.toString().getBytes());
	    if (file != null) {
		for (int i = 0, j = file.size(); i < j; i++) {
		    StringBuffer sb1 = new StringBuffer();
		    sb1.append(PREFIX);
		    sb1.append(BOUNDARY);
		    sb1.append(LINE_END);
		    /**
		     * �����ص�ע�⣺ name�����ֵΪ����������Ҫkey ֻ�����key �ſ��Եõ���Ӧ���ļ�
		     * filename���ļ������֣�������׺���� ����:abc.png
		     */

		    sb1.append("Content-Disposition: form-data; name=\"uploadfile\"; filename=\""
			    + file.get(i).getName() + "\"" + LINE_END);
		    sb1.append("Content-Type: application/octet-stream; charset="
			    + "utf-8" + LINE_END);
		    sb1.append(LINE_END);
		    outStream.write(sb1.toString().getBytes());
		    InputStream is = new FileInputStream(file.get(i));
		    byte[] bytes = new byte[1024];
		    int len = 0;
		    while ((len = is.read(bytes)) != -1) {
			outStream.write(bytes, 0, len);
		    }
		    is.close();
		    outStream.write(LINE_END.getBytes());
		}
	    }

	    // ���������־
	    byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
		    .getBytes();
	    outStream.write(end_data);
	    outStream.flush();
	    // �õ���Ӧ��
	    int res = conn.getResponseCode();
	    InputStream in = conn.getInputStream();
	    StringBuilder sbResult = new StringBuilder();
	    if (res == 200) {
		int ch;
		while ((ch = in.read()) != -1) {
		    sbResult.append((char) ch);
		}
	    }
	    result = sbResult.toString();
	    outStream.close();
	    conn.disconnect();

	} catch (MalformedURLException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return result;
    }

    class GridAdapter extends BaseAdapter {
	private LayoutInflater inflater; // ��ͼ����
	private int selectedPosition = -1; // ѡ�е�λ��
	private boolean shape;

	public boolean isShape() {
	    return shape;
	}

	public void setShape(boolean shape) {
	    this.shape = shape;
	}

	public GridAdapter(Context context) {
	    inflater = LayoutInflater.from(context);
	}

	public void update() {
	    loading();
	}

	public int getCount() {
	    return (Bmp.bmp.size() + 1);
	}

	public Object getItem(int arg0) {

	    return null;
	}

	public long getItemId(int arg0) {

	    return 0;
	}

	public void setSelectedPosition(int position) {
	    selectedPosition = position;
	}

	public int getSelectedPosition() {
	    return selectedPosition;
	}

	/**
	 * ListView Item����
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
	    final int coord = position;
	    ViewHolder holder = null;
	    if (convertView == null) {

		convertView = inflater.inflate(R.layout.item_published_grida,
			parent, false);
		holder = new ViewHolder();
		holder.image = (ImageView) convertView
			.findViewById(R.id.item_grida_image);
		convertView.setTag(holder);
	    } else {
		holder = (ViewHolder) convertView.getTag();
	    }

	    if (position == Bmp.bmp.size()) {
		holder.image.setImageBitmap(BitmapFactory.decodeResource(
			getResources(), R.drawable.icon_addpic_focused));
		if (position == 1) {
		    holder.image.setVisibility(View.GONE);
		}
	    } else {
		holder.image.setImageBitmap(Bmp.bmp.get(position));
	    }

	    return convertView;
	}

	public class ViewHolder {
	    public ImageView image;
	}

	Handler handler = new Handler() {

	    public void handleMessage(Message msg) {
		switch (msg.what) {
		case 1:
		    adapter.notifyDataSetChanged();
		    break;
		}
		super.handleMessage(msg);
	    }
	};

	public void loading() {
	    new Thread(new Runnable() {
		public void run() {
		    while (true) {
			if (Bmp.max == Bmp.drr.size()) {
			    Message message = new Message();
			    message.what = 1;
			    handler.sendMessage(message);
			    break;
			} else {
			    try {
				String path = Bmp.drr.get(Bmp.max);
				System.out.println(path);
				Bitmap bm = Bmp.revitionImageSize(path);
				Bmp.bmp.add(bm);
				String newStr = path.substring(
					path.lastIndexOf("/") + 1,
					path.lastIndexOf("."));
				FileUtils.saveBitmap(bm, "" + newStr);
				Bmp.max += 1;
				Message message = new Message();
				message.what = 1;
				handler.sendMessage(message);
			    } catch (IOException e) {

				e.printStackTrace();
			    }
			}
		    }
		}
	    }).start();
	}
    }

    class PopupWindows extends PopupWindow {

	public PopupWindows(Context mContext, View parent) {

	    View view = View
		    .inflate(mContext, R.layout.item_popupwindows, null);
	    view.startAnimation(AnimationUtils.loadAnimation(mContext,
		    R.anim.fade_ins));
	    LinearLayout ll_popup = (LinearLayout) view
		    .findViewById(R.id.ll_popup);
	    ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
		    R.anim.push_bottom_in_2));

	    setWidth(LayoutParams.FILL_PARENT);
	    setHeight(LayoutParams.FILL_PARENT);
	    setBackgroundDrawable(new BitmapDrawable());
	    setFocusable(true);
	    setOutsideTouchable(true);
	    setContentView(view);
	    showAtLocation(parent, Gravity.CENTER, 0, 0);
	    update();

	    Button bt1 = (Button) view
		    .findViewById(R.id.item_popupwindows_camera);
	    Button bt2 = (Button) view
		    .findViewById(R.id.item_popupwindows_Photo);
	    Button bt3 = (Button) view
		    .findViewById(R.id.item_popupwindows_cancel);
	    bt1.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
		    photo();
		    dismiss();
		}
	    });
	    bt2.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
		    Intent intent = new Intent(MyWorkAddWork.this,
			    PicBucketListActivity.class);
		    intent.putExtra("wORh", "w");
		    startActivity(intent);
		    dismiss();
		    finish();
		}
	    });
	    bt3.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
		    dismiss();
		}
	    });

	}
    }

    public void photo() {
	Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	openCameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

	StringBuffer sDir = new StringBuffer();
	if (hasSDcard()) {
	    sDir.append(Environment.getExternalStorageDirectory()
		    + "/mkcoming/");
	} else {
	    String dataPath = Environment.getRootDirectory().getPath();
	    sDir.append(dataPath + "/mkcoming/");
	}

	File fileDir = new File(sDir.toString());
	if (!fileDir.exists()) {
	    fileDir.mkdirs();
	}
	File file = new File(fileDir,
		String.valueOf(System.currentTimeMillis()) + ".jpg");

	path = file.getPath();
	Uri imageUri = Uri.fromFile(file);
	openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
	startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    public static boolean hasSDcard() {
	String status = Environment.getExternalStorageState();
	if (status.equals(Environment.MEDIA_MOUNTED)) {
	    return true;
	} else {
	    return false;
	}
    }

    public String getString(String s) {
	String path = null;
	if (s == null)
	    return "";
	for (int i = s.length() - 1; i > 0; i++) {
	    s.charAt(i);
	}
	return path;
    }

    protected void onRestart() {
	// ((GridAdapter) adapter).update();
	super.onRestart();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	switch (requestCode) {
	case TAKE_PICTURE:
	    if (Bmp.drr.size() < 1 && resultCode == -1) {
		Bmp.drr.add(path);
	    }
	    break;
	}
    }
}
