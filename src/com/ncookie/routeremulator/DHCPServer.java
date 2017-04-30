package com.ncookie.routeremulator;

import java.util.ArrayList;

/**
 * Created by ryu on 17. 4. 25.
 */
public class DHCPServer {
    private boolean serverState;
    private ArrayList<String> addressRange;
    private int ipLeaseTime;

    public DHCPServer(boolean serverState, ArrayList<String> addressRange, int ipLeaseTime) {
        this.serverState = serverState;
        this.addressRange = addressRange;
        this.ipLeaseTime = ipLeaseTime;
    }

    public boolean isServerState() {
        return serverState;
    }

    public void setServerState(boolean serverState) {
        this.serverState = serverState;
    }

    public ArrayList<String> getAddressRange() {
        return addressRange;
    }

    public void setAddressRange(ArrayList<String> addressRange) {
        this.addressRange = addressRange;
    }

    public int getIpLeaseTime() {
        return ipLeaseTime;
    }

    public void setIpLeaseTime(int ipLeaseTime) {
        this.ipLeaseTime = ipLeaseTime;
    }
}
