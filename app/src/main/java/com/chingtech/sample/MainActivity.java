package com.chingtech.sample;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.Toast;
import com.chingtech.AddressSelector;
import com.chingtech.data.CitiesData;
import com.chingtech.model.City;
import com.chingtech.model.County;
import com.chingtech.model.Province;
import com.chingtech.model.Street;
import com.chingtech.pop.PopupWindowCheckbox;
import com.chingtech.pop.PopupWindowTimer;
import com.chingtech.pop.PopupWindowWheel;

import com.chingtech.widget.BottomDialog;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * *    ***********    ***********    **
 * *    ***********    ***********    **
 * *    **             **             **
 * *    **             **             **
 * *    **             **             **
 * *    ***********    **             **
 * *    ***********    **             **
 * *             **    **             **
 * *             **    **             **
 * *             **    **             **
 * *    ***********    ***********    ***********
 * *    ***********    ***********    ***********
 * </p>
 * wheelview
 * Package com.chingtech.sample
 * Description:
 * Created by 师春雷
 * Created at 2016/10/28 上午8:03
 */
public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, AddressSelector.OnAddressSelectedListener {

    private PopupWindowCheckbox popCheckBox;

    private PopupWindowWheel popText;

    private PopupWindowTimer popTime;//时间:年|月|日

    private String provinceArray[] = CitiesData.PROVINCES;
    private String cityArray[][]   = CitiesData.CITIES;
    private String areaArray[][][] = CitiesData.DISTRICTS;

    private PopupWindowWheel pwCity;

    private PopupWindowWheel pwDouble;

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

        pwDouble = new PopupWindowWheel(this, PopupWindowWheel.WheelType.CASCADE);// 设置CASCADE格式
        pwDouble.setOnCascadeSelectListener(new PopupWindowWheel.OnCascadeSelectListener() {
            @Override
            public void onCascadeSelect(int first, int second) {
                showToast(provinceArray[first] + "[[[[[[[[[[[[[[" + cityArray[first][second]);
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

            case R.id.btn_double:
                pwDouble.showCascade(provinceArray, cityArray); // 显示时间选择器
                break;

            case R.id.btn_address:
                BottomDialog dialog = new BottomDialog(MainActivity.this);
                dialog.setOnAddressSelectedListener(MainActivity.this);
                dialog.show();
                break;
        }
    }

    public static String getYMD(Date date) {
        return formater.get().format(date);
    }

    public final static ThreadLocal<SimpleDateFormat> formater
            = new ThreadLocal<SimpleDateFormat>() {
        @SuppressLint("SimpleDateFormat")
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy年MM月dd日");
        }
    };

    private void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddressSelected(Province province, City city, County county, Street street) {
        String s = (province == null ? "" : province.getName()) + (city == null ? "" :
                "\n" + city.getName()) + (county == null ? "" : "\n" + county.getName()) + (
                street == null ? "" : "\n" + street.getName());

        showToast(s);
    }
}
