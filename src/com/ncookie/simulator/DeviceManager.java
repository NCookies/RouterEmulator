package com.ncookie.simulator;

import java.util.ArrayList;

/**
 * Created by ryu on 17. 4. 25.
 */
public class DeviceManager {
    private ArrayList<Device> deviceList;
    private int lastID = 0;
    private boolean isWired;

    public void createDevice(String name, String mac, int ip, boolean isWired) {
        Device newDevice = new Device(lastID, name, mac, ip, isWired);
        lastID++;
    }

    public ArrayList<Device> getDeviceList() {
        return deviceList;
    }
}
