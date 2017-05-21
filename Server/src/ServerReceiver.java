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

    ServerReceiver(Socket socket) {
        this.socket = socket;
//        try {
//            DataInputStream in = new DataInputStream(socket.getInputStream());
//            System.out.println(in.readUTF());
//
//
//            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
//            out.writeUTF("fuck");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void run() {
        super.run();
        try {
            in = new DataInputStream(socket.getInputStream());
            System.out.println(in.readUTF());

            out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF("fuck");

            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // run
} // ReceiverThread
