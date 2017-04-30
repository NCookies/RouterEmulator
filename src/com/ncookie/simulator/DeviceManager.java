package com.ncookie.simulator;

import java.util.ArrayList;

/**
 * Created by ryu on 17. 4. 25.
 */
public class DeviceManager {
    private ArrayList<Device> deviceList;
    private int lastID = 0;

    public void createDevice(String name, String mac, String ip, boolean d) {
        Device newDevice = new Device(lastID, name, mac, ip, d);
        lastID++;
    }

    public void deleteDevice(int id) {

    }

    public ArrayList<Device> getDeviceList() {
        return deviceList;
    }
}
