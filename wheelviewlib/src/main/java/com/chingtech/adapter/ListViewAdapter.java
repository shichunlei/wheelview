package com.chingtech.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.chingtech.wheelview.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListViewAdapter extends BaseAdapter {

	ViewHolder holder;

	private String[] items;

	HashMap<Integer, View> map = new HashMap<>();

	// 用来控制CheckBox的选中状况
	public List<Boolean> mChecked;

	public class ViewHolder {
		public TextView tvName;
		public CheckBox selected;
	}

	public ListViewAdapter(Context context, String[] mList) {
		this.items = mList;
		mChecked = new ArrayList<Boolean>();

		for (int i = 0; i < mList.length; i++) {
			mChecked.add(false);
		}
	}

	@Override
	public int getCount() {
		return items.length;
	}

	@Override
	public Object getItem(int position) {
		return items[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View view, ViewGroup parent) {
		if (map.get(position) == null) {
			LayoutInflater layoutInflator = LayoutInflater.from(parent
					.getContext());
			view = layoutInflator.inflate(R.layout.choice_list_item_layout,
					null);
			holder = new ViewHolder();
			holder.selected = (CheckBox) view.findViewById(R.id.checkBox);
			holder.tvName = (TextView) view.findViewById(R.id.textView);
			map.put(position, view);
			holder.selected.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					CheckBox cb = (CheckBox) v;
					mChecked.set(position, cb.isChecked());
				}
			});

			view.setTag(holder);
		} else {
			view = map.get(position);
			holder = (ViewHolder) view.getTag();
		}
		holder.tvName.setText(items[position]);
		return view;
	}
}
