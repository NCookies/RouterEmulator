package com.ncookie.routeremulator;

import com.ncookie.simulator.Device;

import java.util.ArrayList;

/**
 * Created by ryu on 17. 4. 25.
 */
public class DHCPServer {
    private boolean serverState;
    private int minIP;
    private int maxIP;
    private int lastIP;
    private int ipLeaseTime;

    public DHCPServer(boolean serverState, int minIP, int maxIP, int ipLeaseTime) {
        this.serverState = serverState;
        this.minIP = minIP;
        this.maxIP = maxIP;
        this.ipLeaseTime = ipLeaseTime;

        this.lastIP = minIP;
    }

    public boolean isServerState() {
        return serverState;
    }

    public void setServerState(boolean serverState) {
        this.serverState = serverState;
    }

    public void setIpRange(int min, int max) {
        minIP = min;
        maxIP = max;

        lastIP = min;
    }

    public int[] getIpRange() {
        int[] a = {minIP, maxIP};

        return a;
    }

    public int getIpLeaseTime() {
        return ipLeaseTime;
    }

    public void setIpLeaseTime(int ipLeaseTime) {
        this.ipLeaseTime = ipLeaseTime;
    }

    public int getIP() {
        return lastIP++;
    }

    // IP 대여 및 타이머
    public void leaseIP(Device d) {

    }
}
