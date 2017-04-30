package com.ncookie.simulator;

/**
 * Created by ryu on 17. 4. 25.
 */
public class Device {
    private int deviceID;
    private String pcName;
    private String macAddress;
    private String ipAddress;
    private boolean dynamicIP;

    public Device(int deviceID, String pcName, String macAddress, String ipAddress, boolean dynamicIP) {
        this.deviceID = deviceID;
        this.pcName = pcName;
        this.macAddress = macAddress;
        this.ipAddress = ipAddress;
        this.dynamicIP = dynamicIP;
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

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public boolean isDynamicIP() {
        return dynamicIP;
    }

    public void setDynamicIP(boolean dynamicIP) {
        this.dynamicIP = dynamicIP;
    }
}
