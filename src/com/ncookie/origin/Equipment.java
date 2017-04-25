package com.ncookie.origin;

/**
 * Created by ryu on 17. 4. 25.
 */
public class Equipment {
    private boolean powerState = false;
    private String routerName = "my-router";

    public boolean getPowerState() {
        return powerState;
    }

    public void setPowerState(boolean powerState) {
        this.powerState = powerState;
    }

    public String getRouterName() {
        return routerName;
    }

    public void setRouterName(String routerName) {
        this.routerName = routerName;
    }
}
