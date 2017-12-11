package com.chingtech.wheel;

import android.view.View;

import com.chingtech.pop.PopupWindowTimer;
import com.chingtech.wheelview.NumericWheelAdapter;
import com.chingtech.wheelview.OnWheelChangedListener;
import com.chingtech.wheelview.R;
import com.chingtech.wheelview.WheelView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

/**
 * 时间滑轮选择器
 *
 * @author 师春雷
 * @date 2015-12-27
 */
public class WheelTime {

    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private View view;

    private WheelView wv_year;
    private WheelView wv_month;
    private WheelView wv_day;
    private WheelView wv_hours;
    private WheelView wv_mins;

    public int screenheight;
    public boolean isShowHanzi = false;

    private PopupWindowTimer.Type type;
    private static int START_YEAR = 1900, END_YEAR = 2100;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public static int getSTART_YEAR() {
        return START_YEAR;
    }

    public void setSTART_YEAR(int sTART_YEAR) {
        START_YEAR = sTART_YEAR;
    }

    public static int getEND_YEAR() {
        return END_YEAR;
    }

    public void setEND_YEAR(int eND_YEAR) {
        END_YEAR = eND_YEAR;
    }

    public boolean isShowHanzi() {
        return isShowHanzi;
    }

    public void setShowHanzi(boolean isShowHanzi) {
        this.isShowHanzi = isShowHanzi;
    }

    public WheelTime(View view) {
        super();
        this.view = view;
        type = PopupWindowTimer.Type.ALL;
        setView(view);
    }

    public WheelTime(View view, PopupWindowTimer.Type type) {
        super();
        this.view = view;
        this.type = type;
        setView(view);
    }

    public void setPicker(int year, int month, int day) {
        this.setPicker(year, month, day, 0, 0);
    }

    /**
     * @Description: 弹出日期时间选择器
     */
    public void setPicker(int year, int month, int day, int h, int m) {
        // 添加大小月月份并将其转换为list,方便之后的判断
        String[] months_big    = {"1", "3", "5", "7", "8", "10", "12"};
        String[] months_little = {"4", "6", "9", "11"};

        final List<String> list_big    = Arrays.asList(months_big);
        final List<String> list_little = Arrays.asList(months_little);

        // 年
        wv_year = view.findViewById(R.id.year);
        wv_year.setAdapter(new NumericWheelAdapter(START_YEAR, END_YEAR));// 设置"年"的显示数据
        if (isShowHanzi) {
            wv_year.setLabel("年");// 添加文字
        }

        wv_year.setCurrentItem(year - START_YEAR);// 初始化时显示的数据

        // 月
        wv_month = view.findViewById(R.id.month);
        wv_month.setAdapter(new NumericWheelAdapter(1, 12));

        if (isShowHanzi) {
            wv_month.setLabel("月");
        }
        wv_month.setCurrentItem(month);

        // 日
        wv_day = view.findViewById(R.id.day);
        // 判断大小月及是否闰年,用来确定"日"的数据
        if (list_big.contains(String.valueOf(month + 1))) {
            wv_day.setAdapter(new NumericWheelAdapter(1, 31));
        } else if (list_little.contains(String.valueOf(month + 1))) {
            wv_day.setAdapter(new NumericWheelAdapter(1, 30));
        } else {
            // 闰年
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                wv_day.setAdapter(new NumericWheelAdapter(1, 29));
            } else {
                wv_day.setAdapter(new NumericWheelAdapter(1, 28));
            }
        }

        if (isShowHanzi) {
            wv_day.setLabel("日");
        }
        wv_day.setCurrentItem(day - 1);

        wv_hours = view.findViewById(R.id.hour);
        wv_hours.setAdapter(new NumericWheelAdapter(0, 23));

        if (isShowHanzi) {
            wv_hours.setLabel("时");// 添加文字
        }
        wv_hours.setCurrentItem(h);

        wv_mins = view.findViewById(R.id.min);
        wv_mins.setAdapter(new NumericWheelAdapter(0, 59));

        if (isShowHanzi) {
            wv_mins.setLabel("分");// 添加文字
        }
        wv_mins.setCurrentItem(m);

        // 添加"年"监听
        OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                int year_num = newValue + START_YEAR;
                // 判断大小月及是否闰年,用来确定"日"的数据
                int maxItem;
                if (list_big.contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 31));
                    maxItem = 31;
                } else if (list_little.contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 30));
                    maxItem = 30;
                } else {
                    if ((year_num % 4 == 0 && year_num % 100 != 0) || year_num % 400 == 0) {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 29));
                        maxItem = 29;
                    } else {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 28));
                        maxItem = 28;
                    }
                }
                if (wv_day.getCurrentItem() > maxItem - 1) {
                    wv_day.setCurrentItem(maxItem - 1);
                }
            }
        };

        // 添加"月"监听
        OnWheelChangedListener wheelListener_month = new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                int month_num = newValue + 1;
                int maxItem   = 30;
                // 判断大小月及是否闰年,用来确定"日"的数据
                if (list_big.contains(String.valueOf(month_num))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 31));
                    maxItem = 31;
                } else if (list_little.contains(String.valueOf(month_num))) {
                    wv_day.setAdapter(new NumericWheelAdapter(1, 30));
                    maxItem = 30;
                } else {
                    if (((wv_year.getCurrentItem() + START_YEAR) % 4 == 0
                            && (wv_year.getCurrentItem() + START_YEAR) % 100 != 0)
                            || (wv_year.getCurrentItem() + START_YEAR) % 400 == 0) {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 29));
                        maxItem = 29;
                    } else {
                        wv_day.setAdapter(new NumericWheelAdapter(1, 28));
                        maxItem = 28;
                    }
                }
                if (wv_day.getCurrentItem() > maxItem - 1) {
                    wv_day.setCurrentItem(maxItem - 1);
                }

            }
        };
        wv_year.addChangingListener(wheelListener_year);
        wv_month.addChangingListener(wheelListener_month);

        // 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
        int textSize = 0;
        switch (type) {
            case ALL:
                textSize = (screenheight / 100) * 3;
                break;
            case YEAR_MONTH_DAY:
                textSize = (screenheight / 100) * 4;
                wv_hours.setVisibility(View.GONE);
                wv_mins.setVisibility(View.GONE);
                break;
            case HOURS_MINS:
                textSize = (screenheight / 100) * 4;
                wv_year.setVisibility(View.GONE);
                wv_month.setVisibility(View.GONE);
                wv_day.setVisibility(View.GONE);
                break;
            case MONTH_DAY_HOUR_MIN:
                textSize = (screenheight / 100) * 3;
                wv_year.setVisibility(View.GONE);
                break;
            case YEAR_MONTH:
                textSize = (screenheight / 100) * 4;
                wv_day.setVisibility(View.GONE);
                wv_hours.setVisibility(View.GONE);
                wv_mins.setVisibility(View.GONE);
                break;
            case YEAR:
                textSize = (screenheight / 100) * 4;
                wv_month.setVisibility(View.GONE);
                wv_day.setVisibility(View.GONE);
                wv_hours.setVisibility(View.GONE);
                wv_mins.setVisibility(View.GONE);
                break;
            default:
                break;
        }

        wv_day.TEXT_SIZE = textSize;
        wv_month.TEXT_SIZE = textSize;
        wv_year.TEXT_SIZE = textSize;
        wv_hours.TEXT_SIZE = textSize;
        wv_mins.TEXT_SIZE = textSize;
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        wv_year.setCyclic(cyclic);
        wv_month.setCyclic(cyclic);
        wv_day.setCyclic(cyclic);
        wv_hours.setCyclic(cyclic);
        wv_mins.setCyclic(cyclic);
    }

    public String getTime() {
        StringBuffer sb = new StringBuffer();
        sb.append((wv_year.getCurrentItem() + START_YEAR))
          .append("-")
          .append((wv_month.getCurrentItem() + 1))
          .append("-")
          .append((wv_day.getCurrentItem() + 1))
          .append(" ")
          .append(wv_hours.getCurrentItem())
          .append(":")
          .append(wv_mins.getCurrentItem());
        return sb.toString();
    }
}
