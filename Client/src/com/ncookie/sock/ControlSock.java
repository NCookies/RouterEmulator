package com.ncookie.sock;

import org.json.simple.JSONObject;

import java.io.*;
import java.net.Socket;


/**
 * Created by ryu on 17. 5. 21.
 */
public class ControlSock {

    private Socket socket;

    private OutputStream os = null;
    private DataOutputStream dos =null;

    private InputStream in;
    private DataInputStream dis;

    public ControlSock(Socket sock) {
        this.socket = sock;

        try {
            dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF("hello");

            DataInputStream in = new DataInputStream(socket.getInputStream());
            System.out.println(in.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createJSONMessage(String msg, String value) {
        try {
            os = socket.getOutputStream();
            dos = new DataOutputStream(os);
//            in = socket.getInputStream();
//            dis = new DataInputStream(in);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", msg);
            jsonObject.put("value", value);

            dos.writeUTF(jsonObject.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* ROUTER state */
    public boolean getPowerState() {

        createJSONMessage(new Object(){}.getClass().getEnclosingMethod().getName(), "");

        try {
            in = socket.getInputStream();
            dis = new DataInputStream(in);

            System.out.println(dis.readUTF());

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setPowerState(boolean powerState) {
        createJSONMessage(new Object(){}.getClass().getEnclosingMethod().getName(), String.valueOf(powerState));

        try {
            in = socket.getInputStream();
            dis = new DataInputStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getRouterName() {
//        return routerName;
        return "FUCK";
    }

    public void setRouterName(String routerName) {
//        this.routerName = routerName;
    }

//    /* AP power state */
//    public boolean getApPowerState() {
//        return apManager.isApState();
//    }
//
//    public void setApPowerState(boolean state) {
//        apManager.setApState(state);
//    }
//
//    public void setPublicAP(boolean isSelected) {
//        apManager.setPublicAP(isSelected);
//    }
//
//
//    /* AP ssid */
//    public String getSSIDName() {
//        return apManager.getSSID();
//    }
//
//    public void setSSIDName(String ssid) {
//        apManager.setSSID(ssid);
//    }
//
//
//    /* AP password */
//    public String getPassword() {
//        return apManager.getPassword();
//    }
//
//    public void setPassword(String password) {
//        apManager.setPassword(password);
//    }
//
//    /* 차단 사이트 설정 */
//    public void addBlockSite(String url) {
//        apManager.setBlockingSite(url);
//    }
//
//
//    /* DHCP power state */
//    public boolean getDhcpPowerState() {
//        return dhcpServer.isServerState();
//    }
//
//    public void setDhcpPowerState(boolean state) {
//        dhcpServer.setServerState(state);
//    }
//
//
//    /* DHCP IP Range */
//    public void setIpRagne(int min, int max) {
//        dhcpServer.setIpRange(min, max);
//    }
//
//    public int[] getIpRange() {
//        return dhcpServer.getIpRange();
//    }
//
//    /* DHCP lease time */
//    public int getDhcpLeaseTime() {
//        return dhcpServer.getIpLeaseTime();
//    }
//
//    public void setDhcpLeaseTime(int time) {
//        dhcpServer.setIpLeaseTime(time);
//    }
//
//    /* 시뮬레이터를 위한 디바이스 생성 */
//    public boolean addDevice(String name, String mac, boolean isWired) {
//        // 새로 연결할 디바이스가 유선이고, 이미 포트가 꽉 차있다면
//        if (!isWired && deviceManager.getCabledDevice() >= cabledLimit) return false;
//        else if (isWired && deviceManager.getWiredDevice() >= wiredLimit) return false;
//
//        // DHCP 서보로부터 남는 IP를 할당받음
//        dhcpServer.leaseIP(deviceManager.createDevice(name, mac, dhcpServer.getIP(), isWired));
//
//        return true;
//    }
//
//    public ArrayList<Device> returnDeviceList() {
//        return deviceManager.getDeviceList();
//    }
//
//    /* 연결된 디바이스의 개수 */
//    public int countWiredDevice() {
//        return deviceManager.getWiredDevice();
//    }
//
//    public int countCabledDevice() {
//        return deviceManager.getCabledDevice();
//    }
}
