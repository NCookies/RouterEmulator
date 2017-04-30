/**
 * Created by ryu on 17. 4. 25.
 */
package com.ncookie.routeremulator;

import com.ncookie.origin.Equipment;
import com.ncookie.simulator.Device;
import com.ncookie.simulator.DeviceManager;

import java.util.ArrayList;
import java.util.Arrays;


public class Router extends Equipment {

    APManager apManager;
    DHCPServer dhcpServer;
    DeviceManager deviceManager;

    public Router() {
        apManager = new APManager(false, true, "G0170HS",
                "123456", 100);

        dhcpServer = new DHCPServer(false,1, 254,8000 );

        deviceManager = new DeviceManager();
    }

    /* AP power state */
    public boolean getApPowerState() {
        return apManager.isApState();
    }

    public void setApPowerState(boolean state) {
        apManager.setApState(state);
    }

    public void setPublicAP(boolean isSelected) {
        apManager.setPublicAP(isSelected);
    }


    /* AP ssid */
    public String getSSIDName() {
        return apManager.getSSID();
    }

    public void setSSIDName(String ssid) {
        apManager.setSSID(ssid);
    }


    /* AP password */
    public String getPassword() {
        return apManager.getPassword();
    }

    public void setPassword(String password) {
        apManager.setPassword(password);
    }

    /* 차단 사이트 설정 */
    public void addBlockSite(String url) {
        apManager.setBlockingSite(url);
    }


    /* DHCP power state */
    public boolean getDhcpPowerState() {
        return dhcpServer.isServerState();
    }

    public void setDhcpPowerState(boolean state) {
        dhcpServer.setServerState(state);
    }


    /* DHCP IP Range */
    public void setIpRagne(int min, int max) {
        dhcpServer.setIpRange(min, max);
    }

    public int[] getIpRange() {
        return dhcpServer.getIpRange();
    }

    /* DHCP lease time */
    public int getDhcpLeaseTime() {
        return dhcpServer.getIpLeaseTime();
    }

    public void setDhcpLeaseTime(int time) {
        dhcpServer.setIpLeaseTime(time);
    }

    /* */
    public void addDevice(String name, String mac, boolean isWired) {
        deviceManager.createDevice(name, mac, dhcpServer.getIP(), isWired);
    }

    public ArrayList<Device> returnDeviceList() {
        return deviceManager.getDeviceList();
    }
}
