package getui.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;

import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;

public class PushDemoReceiver extends BroadcastReceiver {

    /**
     * Ӧ��δ����, ���� service�Ѿ�������,�����ڸ�ʱ�����������Ϣ(��ʱ GetuiSdkDemoActivity.tLogView ==
     * null)
     */
    public static StringBuilder payloadData = new StringBuilder();

    @Override
    public void onReceive(Context context, Intent intent) {
	Bundle bundle = intent.getExtras();
	Log.d("GetuiSdkDemo", "onReceive() action=" + bundle.getInt("action"));

	switch (bundle.getInt(PushConsts.CMD_ACTION)) {
	case PushConsts.GET_MSG_DATA:
	    // ��ȡ͸������
	    // String appid = bundle.getString("appid");
	    byte[] payload = bundle.getByteArray("payload");
	    System.out.println("payload --> " + payload.toString());
	    String taskid = bundle.getString("taskid");
	    String messageid = bundle.getString("messageid");//
	    System.out.println("payload --> " + taskid);
	    System.out.println("messageid --> " + messageid);

	    // smartPush��������ִ���ýӿڣ�actionid��ΧΪ90000-90999���ɸ���ҵ�񳡾�ִ��
	    boolean result = PushManager.getInstance().sendFeedbackMessage(
		    context, taskid, messageid, 90001);
	    System.out.println("��������ִ�ӿڵ���" + (result ? "�ɹ�" : "ʧ��"));

	    if (payload != null) {
		String data = new String(payload);

		Log.d("GetuiSdkDemo", "receiver payload : " + data);

		payloadData.append(data);
		payloadData.append("\n");
		System.out
			.println("payloadData ---> " + payloadData.toString());
		
	    }
	    break;

	case PushConsts.GET_CLIENTID:
	    // ��ȡClientID(CID)
	    // ������Ӧ����Ҫ��CID�ϴ��������������������ҽ���ǰ�û��ʺź�CID���й������Ա��պ�ͨ���û��ʺŲ���CID������Ϣ����
	    String cid = bundle.getString("clientid");//
	    System.out.println("cid --> " + cid);
	    SharedPreferences sharedPre = context.getSharedPreferences(
		    "config", Context.MODE_PRIVATE);
	    Editor editor = sharedPre.edit();
	    editor.putString("cid", cid);
	    editor.commit();
	    break;

	case PushConsts.THIRDPART_FEEDBACK:
	    /*
	     * String appid = bundle.getString("appid"); String taskid =
	     * bundle.getString("taskid"); String actionid =
	     * bundle.getString("actionid"); String result =
	     * bundle.getString("result"); long timestamp =
	     * bundle.getLong("timestamp");
	     * 
	     * Log.d("GetuiSdkDemo", "appid = " + appid); Log.d("GetuiSdkDemo",
	     * "taskid = " + taskid); Log.d("GetuiSdkDemo", "actionid = " +
	     * actionid); Log.d("GetuiSdkDemo", "result = " + result);
	     * Log.d("GetuiSdkDemo", "timestamp = " + timestamp);
	     */
	    break;

	default:
	    break;
	}
    }
}
