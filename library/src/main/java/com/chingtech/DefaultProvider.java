package com.chingtech;

import android.content.Context;
import com.chingtech.db.DbUtils;
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
 * library
 * Package com.chingtech
 * Description:
 * Created by 师春雷
 * Created at 18/3/10 上午8:35
 */
public class DefaultProvider implements AddressProvider {

    private DbUtils dbUtils;

    public DefaultProvider(Context context) {
        dbUtils = new DbUtils(context);
    }

    @Override
    public void provideProvinces(AddressReceiver<Province> addressReceiver) {
        List<Province> provinceQueryList = dbUtils.getProvinceList();
        addressReceiver.send(new ArrayList<>(provinceQueryList));
    }

    @Override
    public void provideCitiesWith(long provinceId, AddressReceiver<City> addressReceiver) {
        List<City> cityQueryList = dbUtils.getCityList(provinceId);
        addressReceiver.send(new ArrayList<>(cityQueryList));
    }

    @Override
    public void provideCountiesWith(long cityId, AddressReceiver<County> addressReceiver) {
        List<County> countyQueryList = dbUtils.getCountyList(cityId);
        addressReceiver.send(new ArrayList<>(countyQueryList));
    }

    @Override
    public void provideStreetsWith(long countyId, AddressReceiver<Street> addressReceiver) {
        List<Street> streetQueryList = dbUtils.getStreetList(countyId);
        addressReceiver.send(new ArrayList<>(streetQueryList));
    }
}
