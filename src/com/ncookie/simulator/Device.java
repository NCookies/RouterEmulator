package com.ncookie.simulator;

import java.util.ArrayList;

/**
 * Created by ryu on 17. 4. 25.
 */
public class Device {
    private int deviceID;
    private String pcName;
    private String macAddress;
    private int ipAddress;
    private boolean isWired;


    public Device(int deviceID, String pcName, String macAddress, int ip, boolean isWired) {
        this.deviceID = deviceID;
        this.pcName = pcName;
        this.macAddress = macAddress;
        this.ipAddress = ip;
        this.isWired = isWired;
    }

    public String[] getDeviceInfo() {
        String[] deviceInfo = {pcName, Integer.toString(ipAddress), macAddress};
        return deviceInfo;
    }

    public int getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(int deviceID) {
        this.deviceID = deviceID;
    }

    public String getPcName() {
        return pcName;
    }

    public void setPcName(String pcName) {
        this.pcName = pcName;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }


}
