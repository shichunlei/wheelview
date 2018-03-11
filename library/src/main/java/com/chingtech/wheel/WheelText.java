package com.chingtech.wheel;

import android.view.View;

import com.chingtech.wheelview.R;
import com.chingtech.wheelview.ArrayListWheelAdapter;
import com.chingtech.wheelview.ArrayWheelAdapter;
import com.chingtech.wheelview.OnWheelChangedListener;
import com.chingtech.wheelview.WheelView;

import java.util.ArrayList;

/**
 * 文本滑轮选择器
 *
 * @author 师春雷
 * @date 2015-12-27
 */
public class WheelText {

    private View      view;
    private WheelView textWheel;
    public int textSize = 28;

    private static int index = 0;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public WheelText(View view) {
        super();
        this.view = view;
        setView(view);
    }

    public void initTextPicker(ArrayList<String> items) {
        this.initTextPicker(items, null);
    }

    public void initTextPicker(String[] array) {
        this.initTextPicker(null, array);
    }

    public void initTextPicker(final ArrayList<String> items, final String[] array) {
        textWheel = view.findViewById(R.id.text);

        if (null == array) {
            textWheel.setAdapter(new ArrayListWheelAdapter<>(items));
        } else if (null == items) {
            textWheel.setAdapter(new ArrayWheelAdapter<>(array));
        }
        index = 0;
        textWheel.setCyclic(false);// 不可循环滚动
        textWheel.setCurrentItem(0);// 初始化时显示的数据

        // 添加监听事件
        OnWheelChangedListener wheelListener = new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                index = newValue;
            }
        };

        textWheel.addChangingListener(wheelListener);
    }

    public static int getIndex() {
        return index;
    }
}
