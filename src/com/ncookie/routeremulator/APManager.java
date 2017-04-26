package com.ncookie.routeremulator;

/**
 * Created by ryu on 17. 4. 25.
 */
public class APManager {

    private boolean apState;
    private String ssid;
    private String password;
    private int operationTime;
    BlockingManager blockingManager = new BlockingManager();


    public APManager(boolean apState, String ssid, String password, int operationTime) {
        this.apState = apState;
        this.ssid = ssid;
        this.password = password;
        this.operationTime = operationTime;
    }

    public boolean isApState() {
        return apState;
    }

    public void setApState(boolean apState) {
        this.apState = apState;
    }

    public String getSSID() {
        return ssid;
    }

    public void setSSID(String ssid) {
        this.ssid = ssid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(int operationTime) {
        this.operationTime = operationTime;
    }
}
