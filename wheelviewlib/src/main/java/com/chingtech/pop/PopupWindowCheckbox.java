package com.chingtech.pop;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.chingtech.wheelview.R;
import com.chingtech.adapter.ListViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class PopupWindowCheckbox extends PopupWindow implements
		OnClickListener, OnItemClickListener {

	private View rootView; // 总的布局
	private View btnSubmit, btnCancel;
	private static final String TAG_SUBMIT = "submit";
	private static final String TAG_CANCEL = "cancel";

	protected String[] mList;

	Context context;

	private ListView listView;
	private ListViewAdapter adapter;

	private OnSelectListener selectListener;

	List<Integer> listItemID = new ArrayList<Integer>();

	@SuppressWarnings("deprecation")
	public PopupWindowCheckbox(Context context, String[] items) {
		super(context);
		this.context = context;
		mList = items;
		this.setWidth(LayoutParams.FILL_PARENT);
		this.setHeight(LayoutParams.FILL_PARENT);
		this.setBackgroundDrawable(new BitmapDrawable());// 这样设置才能点击屏幕外dismiss窗口
		this.setOutsideTouchable(true);
		this.setAnimationStyle(R.style.timepopwindow_anim_style);

		LayoutInflater mLayoutInflater = LayoutInflater.from(context);
		rootView = mLayoutInflater.inflate(R.layout.pw_checkbox, null);

		listView = (ListView) rootView.findViewById(R.id.checkbox);

		listView.setOnItemClickListener(this);

		// -----确定和取消按钮
		btnSubmit = rootView.findViewById(R.id.btnSubmit);
		btnSubmit.setTag(TAG_SUBMIT);
		btnCancel = rootView.findViewById(R.id.btnCancel);
		btnCancel.setTag(TAG_CANCEL);
		btnSubmit.setOnClickListener(this);
		btnCancel.setOnClickListener(this);

		setContentView(rootView);
	}

	public void showLocation(View parent, int gravity, int x, int y) {
		adapter = new ListViewAdapter(context, mList);
		listView.setAdapter(adapter);
		update();
		super.showAtLocation(parent, gravity, x, y);
	}

	@Override
	public void onClick(View v) {
		String tag = (String) v.getTag();
		if (tag.equals(TAG_CANCEL)) {
			dismiss();
			return;
		} else {
			if (null != listItemID)
				listItemID.clear();
			for (int i = 0; i < adapter.mChecked.size(); i++) {
				if (adapter.mChecked.get(i)) {
					listItemID.add(i);
				}
			}
			String string = "";
			if (listItemID.size() == 0) {
				string = "请选择";
			} else {
				for (int i = 0; i < listItemID.size(); i++) {
					string += mList[listItemID.get(i)];
					if (i != listItemID.size() - 1) {
						string = string + " | ";
					}
				}
			}
			selectListener.onSelect(string);
			dismiss();
			return;
		}
	}

	public interface OnSelectListener {
		public void onSelect(String s);
	}

	public void setOnSelectListener(OnSelectListener selectListener) {
		this.selectListener = selectListener;
	}

	@Override
	public void onItemClick(AdapterView<?> listView, View itemLayout,
			int position, long id) {
		if (itemLayout.getTag() instanceof ListViewAdapter.ViewHolder) {
			ListViewAdapter.ViewHolder holder = (ListViewAdapter.ViewHolder) itemLayout.getTag();
			// 会自动出发CheckBox的checked事件
			holder.selected.toggle();
			adapter.mChecked.set(position, holder.selected.isChecked());
		}
	}

}
