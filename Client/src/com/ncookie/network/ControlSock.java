package com.ncookie.network;

import org.json.simple.JSONArray;
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


    private void createJSONMessage(String msg, String value) {
        try {
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osWriter = new OutputStreamWriter(os);
            BufferedWriter buffWriter = new BufferedWriter(osWriter);

            JSONObject jsonObject = new JSONObject();

            JSONObject header = new JSONObject();
            header.put("src", "127.0.0.1");
            header.put("dest", "127.0.0.1");
            header.put("type", "req");

            JSONObject seq = new JSONObject();
            seq.put("cur", 0);
            seq.put("end", 0);

            JSONObject body = new JSONObject();
            JSONArray subValues = new JSONArray();
            // true | false 값을 문자열이 아닌 bool 값으로 보내기 위해
            if (value.equals("true") || value.equals("false")) {
                subValues.add(Boolean.valueOf(value));
            } else {
                subValues.add(value);
            }

            body.put("operation", msg);
            body.put("subValues", subValues);

            jsonObject.put("header", header);
            jsonObject.put("seq", seq);
            jsonObject.put("body", body);

            System.out.println("Send data to server : " + jsonObject.toJSONString());

            buffWriter.write(jsonObject.toJSONString() + "\n");
            buffWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // result 가 0(실패)일 때에는 어떻게 처리할 것인가
    // return 값을 int 로 바꿔서 에러 코드로 처리해?
    private String receiveJSONMessage() {
        try {
            InputStream is = socket.getInputStream();
            InputStreamReader isReader = new InputStreamReader(is);
            BufferedReader buffReader = new BufferedReader(isReader);

            String result;

            result = buffReader.readLine();

            // Send data to client : {"body":{"operation":"SET_AP_POWER","result":true},"header":{"dest":"127.0.0.1","src":"127.0.0.1","type":"res"},"seq":{"cur":"0","end":"0"}}
            // 서버에서 데이터는 보냈는데 출력이 되지 않음. readLine 문제인가?
            System.out.println("Received data from server : " + result);

            JSONObject jsonObject = (JSONObject) parser.parse(result);
            JSONObject header = (JSONObject) jsonObject.get("header");
            JSONObject body = (JSONObject) jsonObject.get("body");

            if (!Boolean.valueOf(body.get("result").toString())) {
                System.out.println("Result of response message is false");
                return "fail";
            }

            if (header.get("type").toString().equals("res")) {    // 응답
                String operation = body.get("operation").toString();

                if (operation.substring(0, 3).equals("SET")) {  // SET 이 앞에 붙은 operation 의 res body 값은 result 밖에 없음
                    if (Boolean.valueOf(body.get("result").toString()))  return "success";
                    else    return "fail";
                }

                switch (operation) {
                    case "GET_AP_POWER":
                        JSONArray subValues = (JSONArray) body.get("subValues");
                        return subValues.get(0).toString();
                }
            } else if (header.get("header").toString().equals("rep")) {     // 업데이트

            } else {
                System.out.println("잘못된 응답입니다");
                return "fail";
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return "fail";
        }

        return "fail";
    }

    /* AP power state */
    public boolean getApPowerState() {
        String result;

        // 먼저 요청을 보내고 result 가 0(실패)라면 다시 시도
        do {
            createJSONMessage("GET_AP_POWER", "");
            result = receiveJSONMessage();
        } while (result.equals("fail"));    // 리턴값이 fail 이면 루프

        return Boolean.valueOf(result);
    }

    public void setApPowerState(boolean state) {
        String result;

        do {
            createJSONMessage("SET_AP_POWER", String.valueOf(state));
            result = receiveJSONMessage();
        } while (result.equals("fail"));
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
