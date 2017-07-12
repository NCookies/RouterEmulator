package com.ncookie.network;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.Socket;


/**
 * Created by ryu on 17. 5. 21.
 */
public class ControlSock {

    private Socket socket;

    private DataOutputStream out;
    private DataInputStream in;

    private JSONParser parser;

    public ControlSock(Socket sock) {
        this.socket = sock;

        out = null;
        in = null;

        parser = new JSONParser();
    }


    public void createJSONMessage(String msg, String value) {
        try {
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osWriter = new OutputStreamWriter(os);
            BufferedWriter buffWriter = new BufferedWriter(osWriter);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", msg);
            jsonObject.put("value", value);

            buffWriter.write(jsonObject.toJSONString() + "\n");
            buffWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String receiveJSONMessage() {
        try {

            InputStream is = socket.getInputStream();
            InputStreamReader isReader = new InputStreamReader(is);
            BufferedReader buffReader = new BufferedReader(isReader);

            String result;

            result = buffReader.readLine();

            Object obj = parser.parse(result);
            JSONObject jsonObject = (JSONObject) obj;

            System.out.println(jsonObject.get("value").toString());

            return jsonObject.get("value").toString();

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /* AP power state */
    public boolean getApPowerState() {
        createJSONMessage(new Object(){}.getClass().getEnclosingMethod().getName(), "");
        return Boolean.valueOf(receiveJSONMessage());
    }

    public void setApPowerState(boolean state) {
        createJSONMessage(new Object(){}.getClass().getEnclosingMethod().getName(), String.valueOf(state));
    }

    public void setPublicAP(boolean isSelected) {
        createJSONMessage(new Object(){}.getClass().getEnclosingMethod().getName(), String.valueOf(isSelected));
    }


    /* AP ssid */
    public String getSSIDName() {
        createJSONMessage(new Object(){}.getClass().getEnclosingMethod().getName(), "");
        return receiveJSONMessage();
    }

    public void setSSIDName(String ssid) {
        createJSONMessage(new Object(){}.getClass().getEnclosingMethod().getName(), ssid);
    }


    /* AP password */
    public String getPassword() {
        createJSONMessage(new Object(){}.getClass().getEnclosingMethod().getName(), "");
        return receiveJSONMessage();
    }

    public void setPassword(String password) {
        createJSONMessage(new Object(){}.getClass().getEnclosingMethod().getName(), password);
    }


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
//    /* 연결된 디바이스의 개수 */
//    public int countWiredDevice() {
//        return deviceManager.getWiredDevice();
//    }
//
//    public int countCabledDevice() {
//        return deviceManager.getCabledDevice();
//    }
}
