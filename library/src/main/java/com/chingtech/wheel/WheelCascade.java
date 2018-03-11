package com.chingtech.wheel;

import android.content.Context;
import android.view.View;

import com.chingtech.wheelview.R;
import com.chingtech.adapter.CascadeAdapter;
import com.chingtech.wheelcity.OnWheelChangedListener;
import com.chingtech.wheelcity.WheelCityView;
import com.chingtech.wheelcity.adapters.ArrayWheelAdapter;


/**
 * 二级级联滚轮
 */
public class WheelCascade {

    private Context context;

    private WheelCityView firstView;
    private WheelCityView secondView;

    private String[]   firstArray;
    private String[][] secondArray;

    private static int index  = 0;
    private static int index1 = 0;

    private View view;

    private static String cascadeTxt;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public WheelCascade(View view, Context context) {
        super();
        this.context = context;
        this.view = view;
        setView(view);
    }

    public void initCascadePicker(String[] firstArray, final String[][] secondArray) {
        this.firstArray = firstArray;
        this.secondArray = secondArray;
        index = 0;
        index1 = 0;
        firstView = view.findViewById(R.id.wheelcascade_first);
        firstView.setCyclic(false);// 不循环
        firstView.setVisibleItems(3);
        firstView.setViewAdapter(new CascadeAdapter(context, firstArray));

        secondView = view.findViewById(R.id.wheelcascade_second);
        secondView.setCyclic(false);// 不循环
        secondView.setVisibleItems(0);

        // 添加"一级"监听
        OnWheelChangedListener wheelListener_first = new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelCityView wheel, int oldValue, int newValue) {
                index = firstView.getCurrentItem();
                index1 = secondView.getCurrentItem();
                updateCascade(secondView, newValue, secondArray);
                setTxt(firstView.getCurrentItem(), secondView.getCurrentItem());
            }
        };

        // 添加"二级"监听
        OnWheelChangedListener wheelListener_second = new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelCityView wheel, int oldValue, int newValue) {
                index = firstView.getCurrentItem();
                index1 = secondView.getCurrentItem();
                setTxt(firstView.getCurrentItem(), secondView.getCurrentItem());
            }
        };

        firstView.addChangingListener(wheelListener_first);
        secondView.addChangingListener(wheelListener_second);

        firstView.setCurrentItem(1);
        secondView.setCurrentItem(1);
    }

    /**
     * Updates the cascade wheel
     */
    private void updateCascade(WheelCityView wheelView, int index, String[][] secondArray) {
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<>(context,
                                                                          secondArray[index]);
        adapter.setTextSize(18);
        wheelView.setViewAdapter(adapter);
        wheelView.setCurrentItem(0);
    }

    public static String getCascadeTxt() {
        return cascadeTxt;
    }

    public void setCascadeTxt(String cascadeTxt) {
        WheelCascade.cascadeTxt = cascadeTxt;
    }

    public void setTxt(int first, int second) {
        if (second == 0) {
            cascadeTxt = "";
        } else {
            cascadeTxt = firstArray[first] + " | " + secondArray[first][second];
        }
    }

    public static int getFirstIndex() {
        return index;
    }

    public static int getSecondIndex() {
        return index1;
    }

}
