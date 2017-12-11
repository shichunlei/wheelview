package com.chingtech.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.chingtech.wheelview.R;
import com.chingtech.wheelcity.adapters.AbstractWheelTextAdapter;

public class CountryAdapter extends AbstractWheelTextAdapter {

	// Countries names
	private String array[];

	/**
	 * Constructor
	 */
	public CountryAdapter(Context context, String[] array) {
		super(context, R.layout.wheel_first_layout, NO_RESOURCE);
		this.array = array;
		setItemTextResource(R.id.wheel_first_name);
	}

	@Override
	public View getItem(int index, View cachedView, ViewGroup parent) {
		View view = super.getItem(index, cachedView, parent);
		return view;
	}

	@Override
	public int getItemsCount() {
		return array.length;
	}

	@Override
	protected CharSequence getItemText(int index) {
		return array[index];
	}

}
