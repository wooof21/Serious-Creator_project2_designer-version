/**
 * 
 */
package tools;

import java.lang.reflect.Field;

import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * @author Liming Chu
 * 
 * @param
 * @return
 */
public class ScrollListView {
    public static void setListViewHeightBasedOnChildren(ListView listView) {
	ListAdapter listAdapter = listView.getAdapter();
	if (listAdapter == null) {
	    // pre-condition
	    return;
	}

	int totalHeight = 0;
	for (int i = 0; i < listAdapter.getCount(); i++) {
	    View listItem = listAdapter.getView(i, null, listView);
	    listItem.measure(0, 0);
	    totalHeight += listItem.getMeasuredHeight();
	}

	ViewGroup.LayoutParams params = listView.getLayoutParams();
	params.height = totalHeight
		+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
	listView.setLayoutParams(params);

	// // ��ȡListView��Ӧ��Adapter
	// ListAdapter listAdapter = listView.getAdapter();
	// if (listAdapter == null) {
	// // pre-condition
	// return;
	// }
	//
	// int totalHeight = 0;
	// for (int i = 0, len = listAdapter.getCount(); i < len; i++) { //
	// listAdapter.getCount()�������������Ŀ
	// View listItem = listAdapter.getView(i, null, listView);
	// listItem.measure(0, 0); // ��������View �Ŀ��
	// totalHeight += listItem.getMeasuredHeight(); // ͳ������������ܸ߶�
	// }
	//
	// ViewGroup.LayoutParams params = listView.getLayoutParams();
	// params.height = totalHeight
	// + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
	// // listView.getDividerHeight()��ȡ�����ָ���ռ�õĸ߶�
	// // params.height���õ�����ListView������ʾ��Ҫ�ĸ߶�
	// listView.setLayoutParams(params);
    }

    public static void setGridViewHeightBasedOnChildren(GridView gridView) {
	// ��ȡGridView��Ӧ��Adapter
	ListAdapter listAdapter = gridView.getAdapter();
	if (listAdapter == null) {
	    return;
	}
	int rows;
	int columns = 0;
	int horizontalBorderHeight = 0;
	Class<?> clazz = gridView.getClass();
	try {
	    // ���÷��䣬ȡ��ÿ����ʾ�ĸ���
	    Field column = clazz.getDeclaredField("mRequestedNumColumns");
	    column.setAccessible(true);
	    columns = (Integer) column.get(gridView);
	    // ���÷��䣬ȡ�ú���ָ��߸߶�
	    Field horizontalSpacing = clazz
		    .getDeclaredField("mRequestedHorizontalSpacing");
	    horizontalSpacing.setAccessible(true);
	    horizontalBorderHeight = (Integer) horizontalSpacing.get(gridView);
	} catch (Exception e) {
	    // TODO: handle exception
	    e.printStackTrace();
	}
	// �ж�������������ÿ�и����Ƿ��������������������ж��࣬��Ҫ��һ��
	if (listAdapter.getCount() % columns > 0) {
	    rows = listAdapter.getCount() / columns + 1;
	} else {
	    rows = listAdapter.getCount() / columns;
	}
	int totalHeight = 0;
	for (int i = 0; i < rows; i++) { // ֻ����ÿ��߶�*����
	    View listItem = listAdapter.getView(i, null, gridView);
	    listItem.measure(0, 0); // ��������View �Ŀ��
	    totalHeight += listItem.getMeasuredHeight(); // ͳ������������ܸ߶�
	}
	ViewGroup.LayoutParams params = gridView.getLayoutParams();
	params.height = totalHeight + 10 * (rows - 1);// �����Ϸָ����ܸ߶�
	gridView.setLayoutParams(params);
    }
}
