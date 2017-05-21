/**
 * Created by ryu on 17. 4. 25.
 */
package com.ncookie.routeremulator;

import com.ncookie.origin.Equipment;
import com.ncookie.simulator.Device;
import com.ncookie.simulator.DeviceManager;

import java.util.ArrayList;


public class Router extends Equipment {

    APManager apManager;
    DHCPServer dhcpServer;
    DeviceManager deviceManager;

    private final int cabledLimit = 4;
    private final int wiredLimit = 8;

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

    /* 시뮬레이터를 위한 디바이스 생성 */
    public boolean addDevice(String name, String mac, boolean isWired) {
        // 새로 연결할 디바이스가 유선이고, 이미 포트가 꽉 차있다면
        if (!isWired && deviceManager.getCabledDevice() >= cabledLimit) return false;
        else if (isWired && deviceManager.getWiredDevice() >= wiredLimit) return false;

        // DHCP 서보로부터 남는 IP를 할당받음
        dhcpServer.leaseIP(deviceManager.createDevice(name, mac, dhcpServer.getIP(), isWired));

        return true;
    }

    public ArrayList<Device> returnDeviceList() {
        return deviceManager.getDeviceList();
    }

    /* 연결된 디바이스의 개수 */
    public int countWiredDevice() {
        return deviceManager.getWiredDevice();
    }

    public int countCabledDevice() {
        return deviceManager.getCabledDevice();
    }
}
