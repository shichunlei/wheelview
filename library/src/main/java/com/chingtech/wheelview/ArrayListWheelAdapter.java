package com.chingtech.wheelview;

import java.util.ArrayList;

/**
 * The simple Array wheel adapter
 * @param <T> the element type
 */
public class ArrayListWheelAdapter<T> implements WheelAdapter {
	
	/** The default items length */
	public static final int DEFAULT_LENGTH = 4;
	
	// items
	private ArrayList<T> items;
	// length
	private int length;

	/**
	 * Constructor
	 * @param items the items
	 * @param length the max items length
	 */
	public ArrayListWheelAdapter(ArrayList<T> items, int length) {
		this.items = items;
		this.length = length;
	}
	
	/**
	 * Contructor
	 * @param items the items
	 */
	public ArrayListWheelAdapter(ArrayList<T> items) {
		this(items, DEFAULT_LENGTH);
	}

	@Override
	public String getItem(int index) {
		if (index >= 0 && index < items.size()) {
			return items.get(index).toString();
		}
		return null;
	}

	@Override
	public int getItemsCount() {
		return items.size();
	}

	@Override
	public int getMaximumLength() {
		return length;
	}

}
