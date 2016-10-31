package com.chingtech.wheel;


import android.content.Context;
import android.view.View;

import com.chingtech.wheelview.R;
import com.chingtech.adapter.CountryAdapter;
import com.chingtech.wheelcity.OnWheelChangedListener;
import com.chingtech.wheelcity.WheelCityView;
import com.chingtech.wheelcity.adapters.ArrayWheelAdapter;

/**
 * 城市滑轮选择器
 * 
 * @author 师春雷
 * @date 2015-12-27
 * 
 */
public class WheelCity {

	private Context context;

	private WheelCityView provinceView;
	private WheelCityView cityView;
	private WheelCityView districtView;

	private String provinceArray[];
	private String cityArray[][];
	private String districtArray[][][];

	private View view;

	private static String cityTxt;

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public WheelCity(View view, Context context) {
		super();
		this.context = context;
		this.view = view;
		setView(view);
	}

	public void initCityPicker(String[] provinceArray,
			final String[][] cityArray, final String[][][] districtArray) {

		this.provinceArray = provinceArray;
		this.cityArray = cityArray;
		this.districtArray = districtArray;

		provinceView = (WheelCityView) view
				.findViewById(R.id.wheelcity_country);

		provinceView.setCyclic(false);// 不循环
		provinceView.setVisibleItems(3);
		provinceView.setViewAdapter(new CountryAdapter(context, provinceArray));

		cityView = (WheelCityView) view.findViewById(R.id.wheelcity_city);
		cityView.setCyclic(false);// 不循环
		cityView.setVisibleItems(0);

		districtView = (WheelCityView) view.findViewById(R.id.wheelcity_ccity);
		districtView.setCyclic(false);// 不循环
		districtView.setVisibleItems(0);// 不限城市

		// 添加"省"监听
		OnWheelChangedListener provinceListener = new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelCityView wheel, int oldValue,
					int newValue) {
				updateCities(cityView, cityArray, newValue);
				setTxt(provinceView.getCurrentItem(),
						cityView.getCurrentItem(),
						districtView.getCurrentItem());
			}

		};

		// 添加"市"监听
		OnWheelChangedListener cityListener = new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelCityView wheel, int oldValue,
					int newValue) {
				updatecCities(districtView, districtArray,
						provinceView.getCurrentItem(), newValue);
				setTxt(provinceView.getCurrentItem(),
						cityView.getCurrentItem(),
						districtView.getCurrentItem());
			}
		};

		// 添加"区"监听
		OnWheelChangedListener districtListener = new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelCityView wheel, int oldValue,
					int newValue) {
				setTxt(provinceView.getCurrentItem(),
						cityView.getCurrentItem(),
						districtView.getCurrentItem());
			}
		};

		provinceView
				.addChangingListener((OnWheelChangedListener) provinceListener);
		cityView.addChangingListener((OnWheelChangedListener) cityListener);
		districtView
				.addChangingListener((OnWheelChangedListener) districtListener);

		provinceView.setCurrentItem(1);// 设置北京
		cityView.setCurrentItem(1);
		districtView.setCurrentItem(1);
	}

	/**
	 * Updates the city wheel
	 */
	private void updateCities(WheelCityView wheelView, String cityArray[][],
			int index) {
		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(
				context, cityArray[index]);
		adapter.setTextSize(18);
		wheelView.setViewAdapter(adapter);
		wheelView.setCurrentItem(0);
	}

	/**
	 * Updates the ccity wheel
	 */
	private void updatecCities(WheelCityView wheelView,
			String districtArray[][][], int index, int index2) {
		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(
				context, districtArray[index][index2]);
		adapter.setTextSize(18);
		wheelView.setViewAdapter(adapter);
		wheelView.setCurrentItem(0);
	}

	public static String getCityTxt() {
		return cityTxt;
	}

	public void setCityTxt(String cityTxt) {
		WheelCity.cityTxt = cityTxt;
	}

	public void setTxt(int country, int city, int ccity) {
		if (country == 0) {
			cityTxt = "";
		} else if (city == 0) {
			cityTxt = provinceArray[country];
		} else if (ccity == 0) {
			cityTxt = provinceArray[country] + " | " + cityArray[country][city];
		} else {
			cityTxt = provinceArray[country] + " | " + cityArray[country][city]
					+ " | " + districtArray[country][city][ccity];
		}
	}
}
