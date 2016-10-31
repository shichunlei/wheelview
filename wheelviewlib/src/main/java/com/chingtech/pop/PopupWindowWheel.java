package com.chingtech.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import com.chingtech.wheelview.R;
import com.chingtech.wheel.WheelCascade;
import com.chingtech.wheel.WheelCity;
import com.chingtech.wheel.WheelText;
import com.chingtech.wheelview.ScreenInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * PopupWindow滑轮选择器
 * 
 * @author 师春雷
 * @date 2015-12-27
 * 
 */
public class PopupWindowWheel extends PopupWindow implements OnClickListener {

	public enum WheelType {
		CITY, TEXT, CASCADE
	}

	WheelType wheelType;

	private View rootView; // 总的布局

	private WheelText wheelText;
	private WheelCity wheelCity;
	private WheelCascade wheelCascade;

	private View btnSubmit, btnCancel;

	private static final String TAG_SUBMIT = "submit";
	private static final String TAG_CANCEL = "cancel";

	private OnTextSelectListener selectTextListener;
	private OnCitySelectListener selectCityListener;
	private OnCascadeSelectListener selectCascadeListener;

	private View pickerview;

	@SuppressWarnings("deprecation")
	public PopupWindowWheel(Context context, WheelType wheelType) {
		super(context);
		this.setWidth(LayoutParams.FILL_PARENT);
		this.setHeight(LayoutParams.FILL_PARENT);
		this.setBackgroundDrawable(new BitmapDrawable());// 这样设置才能点击屏幕外dismiss窗口
		this.setOutsideTouchable(true);
		this.setAnimationStyle(R.style.timepopwindow_anim_style);

		LayoutInflater mLayoutInflater = LayoutInflater.from(context);

		ScreenInfo screenInfo = new ScreenInfo((Activity) context);

		if (wheelType == WheelType.TEXT) {
			rootView = mLayoutInflater
					.inflate(R.layout.popup_window_text, null);
			pickerview = rootView.findViewById(R.id.wheel_text);
			wheelText = new WheelText(pickerview);
			wheelText.screenheight = screenInfo.getHeight();
		} else if (wheelType == WheelType.CITY) {
			rootView = mLayoutInflater
					.inflate(R.layout.popup_window_city, null);
			pickerview = rootView.findViewById(R.id.wheel_city);
			wheelCity = new WheelCity(pickerview, context);
		} else if (wheelType == WheelType.CASCADE) {
			rootView = mLayoutInflater.inflate(R.layout.popup_window_cascade,
					null);
			pickerview = rootView.findViewById(R.id.wheel_cascade);
			wheelCascade = new WheelCascade(pickerview, context);
		}

		// -----确定和取消按钮
		btnSubmit = rootView.findViewById(R.id.btnSubmit);
		btnSubmit.setTag(TAG_SUBMIT);
		btnCancel = rootView.findViewById(R.id.btnCancel);
		btnCancel.setTag(TAG_CANCEL);
		btnSubmit.setOnClickListener(this);
		btnCancel.setOnClickListener(this);

		setContentView(rootView);
	}

	public void setText(ArrayList<String> items, String[] array) {
		if (null == array) {
			wheelText.initTextPicker(items);
		} else {
			wheelText.initTextPicker(array);
		}
	}

	/**
	 * 指定选中的文本，显示选择器
	 * 
	 * @param parent
	 * @param gravity
	 * @param x
	 * @param y
	 * @param items
	 * @param array
	 */
	public void showTxtLocation(View parent, int gravity, int x, int y,
			ArrayList<String> items, String[] array) {
		if (items != null) {
			wheelText.initTextPicker(items);
		} else if (array != null) {
			wheelText.initTextPicker(array);
		}
		update();
		super.showAtLocation(parent, gravity, x, y);
	}

	/**
	 * 指定选中的城市，显示选择器
	 * 
	 * @param parent
	 * @param gravity
	 * @param x
	 * @param y
	 */
	public void showCityLocation(View parent, int gravity, int x, int y,
			String[] provinceArray, final String[][] cityArray,
			final String[][][] districtArray) {
		wheelCity.initCityPicker(provinceArray, cityArray, districtArray);
		update();
		super.showAtLocation(parent, gravity, x, y);
	}

	/**
	 * 指定选中的分类，显示选择器
	 * 
	 * @param parent
	 * @param gravity
	 * @param x
	 * @param y
	 */
	public void showCascadeLocation(View parent, int gravity, int x, int y,
			String[] first, final String[][] second) {
		wheelCascade.initCascadePicker(first, second);
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
			if (selectTextListener != null) {
				int index = WheelText.getIndex();
				selectTextListener.onTextSelect(index);
			} else if (selectCityListener != null) {
				String city = WheelCity.getCityTxt();
				selectCityListener.onCitySelect(city);
			} else if (selectCascadeListener != null) {
				// String cascade = WheelCascade.getCascadeTxt();

				int index = WheelCascade.getFirstIndex();
				int index1 = WheelCascade.getSecondIndex();

				List<Integer> a = new ArrayList<Integer>();
				a.add(index);
				a.add(index1);

				selectCascadeListener.onCascadeSelect(a);
			}
			dismiss();
			return;
		}
	}

	public interface OnTextSelectListener {
		public void onTextSelect(int index);
	}

	public void setOnTextSelectListener(OnTextSelectListener selectTextListener) {
		this.selectTextListener = selectTextListener;
	}

	public interface OnCitySelectListener {
		public void onCitySelect(String s);
	}

	public void setOnCitySelectListener(OnCitySelectListener selectCityListener) {
		this.selectCityListener = selectCityListener;
	}

	public interface OnCascadeSelectListener {
		public void onCascadeSelect(List<Integer> s);
	}

	public void setOnCascadeSelectListener(
			OnCascadeSelectListener selectCascadeListener) {
		this.selectCascadeListener = selectCascadeListener;
	}

}
