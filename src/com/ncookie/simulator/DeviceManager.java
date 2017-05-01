package com.ncookie.simulator;

import java.util.ArrayList;

/**
 * Created by ryu on 17. 4. 25.
 */
public class DeviceManager {
    private ArrayList<Device> deviceList = new ArrayList<Device>();
    private int lastID = 0;
    private boolean isWired;

    private static int cabledDevice = 0;    // 공유기에 무선으로 연결된 디바이스의 수
    private static int wiredDevice = 0;     // 공유기에 유선으로 연결된 디바이스의 수

    public void createDevice(String name, String mac, int ip, boolean isWired) {
        Device newDevice = new Device(lastID, name, mac, ip, isWired);
        deviceList.add(newDevice);

        if (isWired) {
            wiredDevice++;
        } else {
            cabledDevice++;
        }
        lastID++;   // 기기의 ID를 할당하기 위해서 사용
    }

    public ArrayList<Device> getDeviceList() {
        return deviceList;
    }

    public int getWiredDevice() {
        return wiredDevice;
    }

    public int getCabledDevice() {
        return cabledDevice;
    }
}
