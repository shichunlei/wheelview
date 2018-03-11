package com.chingtech.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import com.chingtech.wheelview.R;
import com.chingtech.wheel.WheelTime;
import com.chingtech.wheelview.ScreenInfo;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * PopupWindow滑轮时间选择器
 *
 * @author 师春雷
 * @date 2016-1-22
 */
public class PopupWindowTimer extends PopupWindow implements OnClickListener {

    public enum Type {
        ALL, YEAR_MONTH_DAY, HOURS_MINS, MONTH_DAY_HOUR_MIN, YEAR_MONTH, YEAR
    }// 四种选择模式，年月日时分，年月日，时分，月日时分

    private View rootView; // 总的布局

    private WheelTime wheelTime;

    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";

    private OnTimeSelectListener selectTimeListener;

    private Date date = null;

    @SuppressWarnings("deprecation")
    public PopupWindowTimer(Context context, Type type) {
        super(context);
        this.setWidth(LayoutParams.FILL_PARENT);
        this.setHeight(LayoutParams.FILL_PARENT);
        this.setBackgroundDrawable(new BitmapDrawable());// 这样设置才能点击屏幕外dismiss窗口
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.timepopwindow_anim_style);

        LayoutInflater mLayoutInflater = LayoutInflater.from(context);

        ScreenInfo screenInfo = new ScreenInfo((Activity) context);

        rootView = mLayoutInflater.inflate(R.layout.popup_window_time, null);
        // ----时间转轮
        View pickerview = rootView.findViewById(R.id.timepicker);
        wheelTime = new WheelTime(pickerview, type);
        wheelTime.screenheight = screenInfo.getHeight();

        // 默认选中当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year   = calendar.get(Calendar.YEAR);
        int month  = calendar.get(Calendar.MONTH);
        int day    = calendar.get(Calendar.DAY_OF_MONTH);
        int hours  = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        wheelTime.setPicker(year, month, day, hours, minute);

        // -----确定和取消按钮
        View btnSubmit = rootView.findViewById(R.id.btnSubmit);
        btnSubmit.setTag(TAG_SUBMIT);
        View btnCancel = rootView.findViewById(R.id.btnCancel);
        btnCancel.setTag(TAG_CANCEL);
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        setContentView(rootView);
    }

    /**
     * 设置可以选择的时间范围
     *
     * @param START_YEAR
     * @param END_YEAR
     */
    public void setRange(int START_YEAR, int END_YEAR) {
        wheelTime.setSTART_YEAR(START_YEAR);
        wheelTime.setEND_YEAR(END_YEAR);
    }

    /**
     * 设置选中时间
     *
     * @param date
     */
    public void setTime(Date date) {
        this.date = date;
    }

    /**
     * 指定选中的时间，显示选择器
     */
    public void show() {
        Calendar calendar = Calendar.getInstance();
        if (date == null) {
            calendar.setTime(new Date());
        } else {
            calendar.setTime(date);
        }
        int year   = calendar.get(Calendar.YEAR);
        int month  = calendar.get(Calendar.MONTH);
        int day    = calendar.get(Calendar.DAY_OF_MONTH);
        int hours  = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        wheelTime.setPicker(year, month, day, hours, minute);
        update();
        super.showAtLocation(rootView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        wheelTime.setCyclic(cyclic);
    }

    /**
     * 设置是否显示汉字
     *
     * @param isShowHanzi
     */
    public void setShowHanzi(boolean isShowHanzi) {
        wheelTime.setShowHanzi(isShowHanzi);
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_CANCEL)) {
            dismiss();
            return;
        } else {
            if (selectTimeListener != null) {
                try {
                    Date date = WheelTime.dateFormat.parse(wheelTime.getTime());
                    selectTimeListener.onTimeSelect(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            dismiss();
            return;
        }
    }

    public interface OnTimeSelectListener {
        void onTimeSelect(Date date);
    }

    public void setOnTimeSelectListener(OnTimeSelectListener selectTimeListener) {
        this.selectTimeListener = selectTimeListener;
    }
}
