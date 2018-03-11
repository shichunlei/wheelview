package com.chingtech.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.chingtech.model.*;
import java.util.ArrayList;
import java.util.List;

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
 * JDAddressSelector
 * Package com.chingtech.jdaddressselector
 * Description:
 * Created by 师春雷
 * Created at 18/3/10 上午8:36
 */
public class DbUtils {

    private static final String DB_NAME = "area.db";

    private static final String T_PROVINCE_NAME = "Province";
    private static final String T_CITY_NAME     = "City";
    private static final String T_COUNTY_NAME   = "County";
    private static final String T_STREET_NAME   = "Street";

    private SQLiteDatabase db;

    public DbUtils(Context context) {
        // 初始化，只需要调用一次
        DBManager.initManager(context);
        // 获取管理对象，因为数据库需要通过管理对象才能够获取
        DBManager mg = DBManager.getManager();
        // 通过管理对象获取数据库
        db = mg.getDatabase(DB_NAME);
    }

    /**
     * 从本地数据库获取省份列表
     *
     * @return
     */
    public List<Province> getProvinceList() {
        List<Province> proList = new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery("select * from " + T_PROVINCE_NAME, null);
            if (null != cursor) {
                while (cursor.moveToNext()) {
                    int    id   = cursor.getInt(cursor.getColumnIndex("id"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));

                    Province bean = new Province();
                    bean.setId(id);
                    bean.setName(name);

                    proList.add(bean);
                }
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != db) {
                // db.close();
            }
        }
        Log.i("TAG", "getProvinceList: " + proList.toString());
        return proList;
    }

    /**
     * 根据身份ID获取城市列表
     *
     * @return
     */
    public List<City> getCityList(long proId) {
        List<City> proList = new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery(
                    "select * from " + T_CITY_NAME + " where province_id = " + proId, null);
            if (null != cursor) {
                while (cursor.moveToNext()) {
                    int    id   = cursor.getInt(cursor.getColumnIndex("id"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));

                    City bean = new City();
                    bean.setId(id);
                    bean.setName(name);

                    proList.add(bean);
                }
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != db) {
                // db.close();
            }
        }
        Log.i("TAG", "getCityList: " + proList.toString());
        return proList;
    }

    /**
     * 根据城市ID获取区县列表
     *
     * @return
     */
    public List<County> getCountyList(long cityId) {
        List<County> proList = new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery(
                    "select * from " + T_COUNTY_NAME + " where city_id = " + cityId, null);
            if (null != cursor) {
                while (cursor.moveToNext()) {
                    int    id   = cursor.getInt(cursor.getColumnIndex("id"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));

                    County bean = new County();
                    bean.setId(id);
                    bean.setName(name);

                    proList.add(bean);
                }
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != db) {
                // db.close();
            }
        }
        Log.i("TAG", "getCountyList: " + proList.toString());
        return proList;
    }

    /**
     * 根据区县ID获取乡道列表
     *
     * @return
     */
    public List<Street> getStreetList(long countyId) {
        List<Street> proList = new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery(
                    "select * from " + T_STREET_NAME + " where county_id = " + countyId, null);
            if (null != cursor) {
                while (cursor.moveToNext()) {
                    int    id   = cursor.getInt(cursor.getColumnIndex("id"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));

                    Street bean = new Street();
                    bean.setId(id);
                    bean.setName(name);

                    proList.add(bean);
                }
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != db) {
                // db.close();
            }
        }
        Log.i("TAG", "getStreetList: " + proList.toString());
        return proList;
    }
}
