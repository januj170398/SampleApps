package com.example.samplegooglemaps.events;

// Delivers address string in case of success
public class AddressFetchSuccessEvent {

    private String mAddress;

    public AddressFetchSuccessEvent(String address) {
        mAddress = address;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }
}
