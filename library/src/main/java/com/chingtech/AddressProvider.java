package com.chingtech;

import com.chingtech.model.*;
import java.util.List;

public interface AddressProvider {
    void provideProvinces(AddressReceiver<Province> addressReceiver);

    void provideCitiesWith(long provinceId, AddressReceiver<City> addressReceiver);

    void provideCountiesWith(long cityId, AddressReceiver<County> addressReceiver);

    void provideStreetsWith(long countyId, AddressReceiver<Street> addressReceiver);

    interface AddressReceiver<T> {
        void send(List<T> data);
    }
}