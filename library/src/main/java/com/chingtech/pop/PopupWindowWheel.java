package com.chingtech.pop;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import com.chingtech.data.CitiesData;
import com.chingtech.wheelview.R;
import com.chingtech.wheel.WheelCascade;
import com.chingtech.wheel.WheelCity;
import com.chingtech.wheel.WheelText;

import java.util.ArrayList;
import java.util.List;

/**
 * PopupWindow滑轮选择器
 *
 * @author 师春雷
 * @date 2015-12-27
 */
public class PopupWindowWheel extends PopupWindow implements OnClickListener {

    public enum WheelType {
        CITY, TEXT, CASCADE
    }

    private View rootView; // 总的布局

    private WheelText    wheelText;
    private WheelCity    wheelCity;
    private WheelCascade wheelCascade;

    private ArrayList<String> items;
    private String[]          array;

    private String provinceArray[] = CitiesData.PROVINCES;
    private String cityArray[][]   = CitiesData.CITIES;
    private String areaArray[][][] = CitiesData.DISTRICTS;

    private boolean isData = false;

    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";

    private OnTextSelectListener    selectTextListener;
    private OnCitySelectListener    selectCityListener;
    private OnCascadeSelectListener selectCascadeListener;

    @SuppressWarnings("deprecation")
    public PopupWindowWheel(Context context, WheelType wheelType) {
        super(context);
        this.setWidth(LayoutParams.FILL_PARENT);
        this.setHeight(LayoutParams.FILL_PARENT);
        this.setBackgroundDrawable(new BitmapDrawable());// 这样设置才能点击屏幕外dismiss窗口
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.timepopwindow_anim_style);

        LayoutInflater mLayoutInflater = LayoutInflater.from(context);

        View pickerview;
        if (wheelType == WheelType.TEXT) {
            rootView = mLayoutInflater.inflate(R.layout.popup_window_text, null);
            pickerview = rootView.findViewById(R.id.wheel_text);
            wheelText = new WheelText(pickerview);
        } else if (wheelType == WheelType.CITY) {
            rootView = mLayoutInflater.inflate(R.layout.popup_window_city, null);
            pickerview = rootView.findViewById(R.id.wheel_city);
            wheelCity = new WheelCity(pickerview, context);
        } else if (wheelType == WheelType.CASCADE) {
            rootView = mLayoutInflater.inflate(R.layout.popup_window_cascade, null);
            pickerview = rootView.findViewById(R.id.wheel_cascade);
            wheelCascade = new WheelCascade(pickerview, context);
        }

        // -----确定和取消按钮
        View btnSubmit = rootView.findViewById(R.id.btnSubmit);
        btnSubmit.setTag(TAG_SUBMIT);
        View btnCancel = rootView.findViewById(R.id.btnCancel);
        btnCancel.setTag(TAG_CANCEL);
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        setContentView(rootView);
    }

    public void setText(ArrayList<String> items) {
        this.items = items;
        setText(items, null);
    }

    public void setText(String[] array) {
        this.array = array;
        setText(null, array);
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
     */
    public void showText() {
        if (this.items != null) {
            wheelText.initTextPicker(items);
        } else if (this.array != null) {
            wheelText.initTextPicker(array);
        }
        update();
        super.showAtLocation(rootView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    public void setCityData(String provinceArray[], String cityArray[][], String areaArray[][][]) {
        wheelCity.initCityPicker(provinceArray, cityArray, areaArray);
        isData = true;
    }

    /**
     * 指定选中的城市，显示选择器
     */
    public void showCity() {
        if (!isData) {
            wheelCity.initCityPicker(provinceArray, cityArray, areaArray);
        }
        update();
        super.showAtLocation(rootView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 指定选中的分类，显示选择器
     */
    public void showCascade(String[] first, final String[][] second) {
        wheelCascade.initCascadePicker(first, second);
        update();
        super.showAtLocation(rootView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
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

                int firstIndex  = WheelCascade.getFirstIndex();
                int secondIndex = WheelCascade.getSecondIndex();

                selectCascadeListener.onCascadeSelect(firstIndex, secondIndex);
            }
            dismiss();
            return;
        }
    }

    public interface OnTextSelectListener {
        void onTextSelect(int index);
    }

    public void setOnTextSelectListener(OnTextSelectListener selectTextListener) {
        this.selectTextListener = selectTextListener;
    }

    public interface OnCitySelectListener {
        void onCitySelect(String s);
    }

    public void setOnCitySelectListener(OnCitySelectListener selectCityListener) {
        this.selectCityListener = selectCityListener;
    }

    public interface OnCascadeSelectListener {
        void onCascadeSelect(int first, int second);
    }

    public void setOnCascadeSelectListener(OnCascadeSelectListener selectCascadeListener) {
        this.selectCascadeListener = selectCascadeListener;
    }
}
