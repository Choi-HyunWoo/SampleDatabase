package com.hw.corcow.sampledatabase;

/**
 * Created by Tacademy on 2015-10-21.
 */
public class AddressItem {
    long _id = -1;
    String name;
    String address;
    String phone;
    String office;

    @Override
    public String toString() {
        return name + "(" + address + ")";
    }
}
