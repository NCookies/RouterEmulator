import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by ryu on 17. 5. 21.
 */
class ServerReceiver extends Thread {
    Socket socket;

    DataInputStream in;
    DataOutputStream out;

    JSONParser parser = new JSONParser();

    Emulator emulator;

    ServerReceiver(Socket socket) {
        this.socket = socket;
        emulator = new Emulator();

        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createJSONMessage(String msg, String value) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", msg);
            jsonObject.put("value", value);

            out.writeUTF(jsonObject.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            try {

                while(in.available() == 0)  // 데이터를 받아올 때까지 대기
                {
                    Thread.sleep(10);
                }

                // DataInputStream 에서 데이터를 가져오고 스트림을 비우는 시점은 in.read 인 듯


                Object obj = parser.parse(in.readUTF());
                JSONObject jsonObject =(JSONObject) obj;

                String message = jsonObject.get("message").toString();
                String value = jsonObject.get("value").toString();

                // {"message":"getPowerState","value":""}
                switch (message) {
                    case "getPowerState":
                        createJSONMessage("getPowerState", String.valueOf(emulator.getPowerState()));
                        break;

                    case "setPowerState":
                        emulator.setPowerState(Boolean.valueOf(value));
                        break;

                    case "getRouterName":
                        createJSONMessage("getRouterName", String.valueOf(emulator.getRouterName()));
                        break;

                    case "setRouterName":
                        emulator.setRouterName(message);
                        break;

                    default:
                        in.close();
                        out.close();
                        socket.close();

                        return;
                }

            } catch (IOException | ParseException | InterruptedException e) {
                e.printStackTrace();
            }
        }

//        try {
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
