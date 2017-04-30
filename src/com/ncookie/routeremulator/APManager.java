package com.ncookie.routeremulator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by ryu on 17. 4. 25.
 */
public class APManager {

    private boolean apState;


    private boolean publicAP;
    private String ssid;
    private String password;
    private int operationTime;
    BlockingManager blockingManager = new BlockingManager();


    public APManager(boolean apState, boolean publicAP, String ssid, String password, int operationTime) {
        this.apState = apState;
        this.publicAP = publicAP;
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

    public void setPublicAP(boolean publicAP) {
        this.publicAP = publicAP;
    }

    public String getSSID() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("ssid.txt"));
            String line = br.readLine();

            if (line == null) {
                return this.ssid;
            }

            br.close();
            return line;

        } catch (IOException err) {
            System.out.println("SSID를 읽어오는 중 에러 발생");
        }

        return this.ssid;
    }

    public void setSSID(String newSSID) {
        try {
            FileWriter ssid = new FileWriter("ssid.txt");

            ssid.write(newSSID);
            ssid.close();

        } catch (IOException err) {
            System.out.println("파일 입출력 에러 발생!!");
        }
        this.ssid = newSSID;
    }

    public String getPassword() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("password.txt"));
            String line = br.readLine();

            if (line == null) {
                return this.password;
            }

            br.close();
            return line;

        } catch (IOException err) {
            System.out.println("PASSWORD를 읽어오는 중 에러 발생");
        }

        return this.password;
    }

    public void setPassword(String newPassword) {
        try {
            FileWriter password = new FileWriter("password.txt");

            password.write(newPassword);
            password.close();

        } catch (IOException err) {
            System.out.println("파일 입출력 에러 발생!!");
        }

        this.password = newPassword;
    }

    public int getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(int operationTime) {
        this.operationTime = operationTime;
    }

    public void setBlockingSite(String url) {
        blockingManager.addBlockingUrl(url);
    }
}
