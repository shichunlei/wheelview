package com.chingtech.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;

import android.widget.Toast;
import com.chingtech.data.CitiesData;
import com.chingtech.pop.PopupWindowCheckbox;
import com.chingtech.pop.PopupWindowTimer;
import com.chingtech.pop.PopupWindowWheel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by leo on 2016/10/28.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private PopupWindowCheckbox popCheckBox;

    private PopupWindowWheel popText;

    private PopupWindowTimer popTime;//时间:年|月|日

    private String provinceArray[] = CitiesData.PROVINCES;
    private String cityArray[][]   = CitiesData.CITIES;
    private String areaArray[][][] = CitiesData.DISTRICTS;

    private PopupWindowWheel pwCity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        popCheckBox = new PopupWindowCheckbox(this, provinceArray);
        popCheckBox.setSeparator(",");
        popCheckBox.setOnSelectListener(new PopupWindowCheckbox.OnSelectListener() {

            @Override
            public void onSelect(String text) {
                showToast(text);
            }
        });

        popText = new PopupWindowWheel(this, PopupWindowWheel.WheelType.TEXT);// 设置TEXT格式
        popText.setText(provinceArray);
        popText.setOnTextSelectListener(new PopupWindowWheel.OnTextSelectListener() {
            @Override
            public void onTextSelect(int index) {
                showToast(provinceArray[index]);
            }
        });

        popTime = new PopupWindowTimer(this, PopupWindowTimer.Type.YEAR_MONTH_DAY);// 设置时间格式
        popTime.setShowHanzi(true);// 设置是否显示汉字
        popTime.setCyclic(true);// 设置是否循环
        popTime.setOnTimeSelectListener(new PopupWindowTimer.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                showToast(getYMD(date));
            }
        });

        pwCity = new PopupWindowWheel(this, PopupWindowWheel.WheelType.CITY);// 设置TEXT格式
        pwCity.setCityData(provinceArray, cityArray, areaArray);
        pwCity.setOnCitySelectListener(new PopupWindowWheel.OnCitySelectListener() {
            @Override
            public void onCitySelect(String city) {
                showToast(city.replace(" | ", " "));
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_text:
                popText.showText();
                break;

            case R.id.btn_cb:
                popCheckBox.show();
                break;

            case R.id.btn_city:
                pwCity.showCity();
                break;

            case R.id.btn_time:
                popTime.show(); // 显示时间选择器
                break;
        }
    }

    public static String getYMD(Date date) {
        return formater.get().format(date);
    }

    public final static ThreadLocal<SimpleDateFormat> formater
            = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy年MM月dd日");
        }
    };

    private void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}
