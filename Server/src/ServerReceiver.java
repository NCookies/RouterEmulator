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

    ServerReceiver(Socket socket) {
        this.socket = socket;

    }

    public void run() {
        while (true) {
            try {
                in = new DataInputStream(socket.getInputStream());
//                System.out.println(in.readUTF());

//                out = new DataOutputStream(socket.getOutputStream());

                while(in.available() == 0)
                {
                    Thread.sleep(10);
                }

                System.out.println(in.readUTF());

                Object obj = parser.parse(in.readUTF());
                JSONObject jsonObject =(JSONObject) obj;

                switch (jsonObject.get("msg").toString()) {
                    case "getPowerState":
                        System.out.println("됐다!!");
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
