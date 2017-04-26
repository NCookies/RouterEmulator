/**
 * Created by ryu on 17. 4. 25.
 */
package com.ncookie.routeremulator;

import com.ncookie.origin.Equipment;
import jdk.nashorn.internal.ir.Block;

import javax.swing.*;


public class Router extends Equipment {

    APManager apManager;
    DHCPServer dhcpServer;

    public Router() {
        apManager = new APManager(false, "G0170HS",
                "123456", 100);

        dhcpServer = new DHCPServer();
    }

    /* power state getter/setter */
    public boolean getApPowerState() {
        return apManager.isApState();
    }

    public void setApPowerState(boolean state) {
        apManager.setApState(state);
    }


    /* ap ssid getter/setter */
    public String getSSIDName() {
        return apManager.getSSID();
    }

    public void setSSIDName(String ssid) {
        apManager.setSSID(ssid);
    }


    /* ap password getter/setter */
    public String getPassword() {
        return apManager.getPassword();
    }

    public void setPassword(String password) {
        apManager.setPassword(password);
    }
}
